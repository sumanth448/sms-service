package com.auzmor.smsservice.service;

import com.auzmor.smsservice.exception.AuthorizationException;
import com.auzmor.smsservice.models.Account;
import com.auzmor.smsservice.repository.AccountRepository;
import com.auzmor.smsservice.service.impl.AccountServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(value= SpringRunner.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountService service = new AccountServiceImpl();

    @Mock
    private AccountRepository accountRepository;

    private Account account;

    @Before
    public void init(){
        account = new Account();
        account.setUserName("abc");
        account.setAuthId("xyz");
        Mockito.when(accountRepository.findByUserNameAndAuthId("abc","xyz")).thenReturn(Optional.of(account));
    }

    @Test
    public void getUserName() throws Exception {
        assertEquals(service.getUserName("abc", "xyz"), account);
    }
    @Test(expected = AuthorizationException.class)
    public void testInvalidUser() throws Exception{
        service.getUserName("aa","bb");
    }

}