package com.auzmor.smsservice.service;

import com.auzmor.smsservice.models.Account;
import com.auzmor.smsservice.models.PhoneNumber;
import com.auzmor.smsservice.models.dto.InboundRequestDTO;
import com.auzmor.smsservice.models.dto.InboundResponse;
import com.auzmor.smsservice.repository.AccountRepository;
import com.auzmor.smsservice.repository.PhoneRepository;
import com.auzmor.smsservice.service.impl.OutBoundServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;

@RunWith(value= SpringRunner.class)
public class OutBoundServiceTest {

    @InjectMocks
    private OutBoundService service = new OutBoundServiceImpl();

    @Mock
    private PhoneRepository phoneRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private RedisTemplate<String,String> redisTemplate;

    private PhoneNumber phoneNumber;

    private Account account;

    private InboundRequestDTO inboundRequestDTO;

    private InboundResponse inboundResponse;

    @Before
    public void init(){
        account = new Account();
        account.setUserName("abc");
        account.setAuthId("xyz");
        account.setId(1L);
        phoneNumber = new PhoneNumber();
        phoneNumber.setNumber("1234567");
        phoneNumber.setAccount(account);

        Mockito.when(accountRepository.findByUserNameAndAuthId("abc","xyz")).thenReturn(Optional.of(account));
        Mockito.when(phoneRepository.findByNumberAndAccountId("1234567",1L)).thenReturn(Optional.of(phoneNumber));
        Mockito.when(redisTemplate.hasKey(any())).thenReturn(true);
    }

    @Test
    public void processOutboundMessage() throws Exception {
        inboundRequestDTO = new InboundRequestDTO();
        inboundRequestDTO.setFrom("1234567");
        inboundRequestDTO.setTo("2425262672");
        inboundRequestDTO.setText("Hi");
        inboundResponse = new InboundResponse();
        inboundResponse.setError("sms from 1234567 to 2425262672 blocked by STOP request");
        assertEquals(service.processOutboundMessage(inboundRequestDTO,account),inboundResponse);
    }

    @Test
    public void testFrom() throws Exception {
        inboundRequestDTO = new InboundRequestDTO();
        inboundRequestDTO.setFrom("36636363");
        inboundRequestDTO.setTo("2425262672");
        inboundRequestDTO.setText("Hi");
        inboundResponse = new InboundResponse();
        inboundResponse.setError("from parameter not found");
        assertEquals(service.processOutboundMessage(inboundRequestDTO,account),inboundResponse);
    }

}