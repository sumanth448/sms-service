package com.auzmor.smsservice.service.impl;

import com.auzmor.smsservice.models.Account;
import com.auzmor.smsservice.models.PhoneNumber;
import com.auzmor.smsservice.models.dto.InboundRequestDTO;
import com.auzmor.smsservice.models.dto.InboundResponse;
import com.auzmor.smsservice.repository.PhoneRepository;
import com.auzmor.smsservice.service.InboundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class InboundServiceImpl implements InboundService {

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private final static String STOP_TEXT = "STOP";

    @Value("${sms.to.expiry.time.seconds}")
    private int smsExpiryTime;

    @Override
    public InboundResponse processInboundMessage(InboundRequestDTO inboundRequestDTO, Account account) {
        Optional<PhoneNumber> number = phoneRepository.findByNumberAndAccountId(inboundRequestDTO.getTo(),account.getId());
        InboundResponse inboundResponse = new InboundResponse();
        if(number.isPresent()){
            inboundResponse.setMessage("inbound message ok");
            storeInCache(inboundRequestDTO);
        }else{
            inboundResponse.setError("to parameter not found");
        }
        return inboundResponse;
    }

    private void storeInCache(InboundRequestDTO inboundRequestDTO) {
        if(STOP_TEXT.equalsIgnoreCase(inboundRequestDTO.getText().trim())){
            String key = inboundRequestDTO.getFrom() + "_" + inboundRequestDTO.getTo();
            redisTemplate.opsForValue().set(key,inboundRequestDTO.getText());
            redisTemplate.expire(key,smsExpiryTime, TimeUnit.SECONDS);
        }
    }
}
