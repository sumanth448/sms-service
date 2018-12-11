package com.auzmor.smsservice.service.impl;

import com.auzmor.smsservice.models.Account;
import com.auzmor.smsservice.models.PhoneNumber;
import com.auzmor.smsservice.models.dto.InboundRequestDTO;
import com.auzmor.smsservice.models.dto.InboundResponse;
import com.auzmor.smsservice.repository.PhoneRepository;
import com.auzmor.smsservice.service.OutBoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class OutBoundServiceImpl implements OutBoundService {

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Value("${sms.limit}")
    private int smsLimit;

    @Value("${sms.reset.counter}")
    private int smsResetCounter;


    @Override
    public InboundResponse processOutboundMessage(InboundRequestDTO inboundRequestDTO, Account account) {

        InboundResponse inboundResponse = new InboundResponse();

        //check for valid from
        if (checkForValidFrom(inboundRequestDTO, account, inboundResponse)) return inboundResponse;
        //check for stop key
        if (checkForStopKey(inboundRequestDTO, inboundResponse)) return inboundResponse;

        //check for valid limit
        if (checkForLimit(inboundRequestDTO, inboundResponse)) return inboundResponse;

        inboundResponse.setMessage("outbound sms ok");
        return inboundResponse;
    }

    private boolean checkForValidFrom(InboundRequestDTO inboundRequestDTO, Account account, InboundResponse inboundResponse) {
        //check for valid from
        Optional<PhoneNumber> number = phoneRepository.findByNumberAndAccountId(inboundRequestDTO.getFrom(),account.getId());
        if(!number.isPresent()){
            inboundResponse.setError("from parameter not found");
            return true;
        }
        return false;
    }

    private boolean checkForStopKey(InboundRequestDTO inboundRequestDTO , InboundResponse inboundResponse) {
        String key = inboundRequestDTO.getFrom() + "_" + inboundRequestDTO.getTo();
        if(redisTemplate.hasKey(key)){
           inboundResponse.setError("sms from " + inboundRequestDTO.getFrom() + " to " +  inboundRequestDTO.getTo()
                   + " blocked by STOP request");
            return true;
        }
        return false;
    }

    private boolean checkForLimit(InboundRequestDTO inboundRequestDTO, InboundResponse inboundResponse) {
        //don't allow more than 50 requests
        String fromKey = inboundRequestDTO.getFrom();
        if(redisTemplate.hasKey(fromKey)){
            String count = redisTemplate.opsForValue().get(fromKey);
            if(Integer.parseInt(count) == smsLimit){
               inboundResponse.setError("limit reached for from "+ fromKey);
                return true;
            }
            redisTemplate.opsForValue().set(fromKey,String.valueOf(Integer.parseInt(count) + 1));
        }else{
            redisTemplate.opsForValue().set(fromKey,"1");
            redisTemplate.expire(fromKey,smsResetCounter, TimeUnit.SECONDS);
        }
        return false;
    }
}
