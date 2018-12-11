package com.auzmor.smsservice.service.impl;

import com.auzmor.smsservice.exception.AuthorizationException;
import com.auzmor.smsservice.models.Account;
import com.auzmor.smsservice.repository.AccountRepository;
import com.auzmor.smsservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;


    @Override
    public Account getUserName(String userName, String password) {
        return accountRepository.findByUserNameAndAuthId(userName,password).orElseThrow(() -> new
                AuthorizationException("SMS-403","Forbidden"));
    }
}
