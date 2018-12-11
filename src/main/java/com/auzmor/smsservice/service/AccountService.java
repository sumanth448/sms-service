package com.auzmor.smsservice.service;

import com.auzmor.smsservice.models.Account;

public interface AccountService {
    public Account getUserName(String userName, String password);
}
