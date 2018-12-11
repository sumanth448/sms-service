package com.auzmor.smsservice.service;

import com.auzmor.smsservice.models.Account;
import com.auzmor.smsservice.models.PhoneNumber;
import com.auzmor.smsservice.models.dto.InboundRequestDTO;
import com.auzmor.smsservice.models.dto.InboundResponse;
import com.auzmor.smsservice.repository.AccountRepository;
import com.auzmor.smsservice.repository.PhoneRepository;
import com.auzmor.smsservice.service.impl.InboundServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(value= SpringRunner.class)
public class InboundServiceTest {

    @InjectMocks
    private InboundService service = new InboundServiceImpl();

    @Mock
    private PhoneRepository phoneRepository;

    @Mock
    private AccountRepository accountRepository;


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
    }

    @Test
    public void processInboundMessage() throws Exception {
        inboundRequestDTO = new InboundRequestDTO();
        inboundRequestDTO.setFrom("234424425");
        inboundRequestDTO.setTo("1234567");
        inboundRequestDTO.setText("Hi");
        inboundResponse = new InboundResponse();
        inboundResponse.setMessage("inbound message ok");
        assertEquals(service.processInboundMessage(inboundRequestDTO,account),inboundResponse);
    }


}