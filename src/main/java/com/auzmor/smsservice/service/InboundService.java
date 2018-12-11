package com.auzmor.smsservice.service;

import com.auzmor.smsservice.models.Account;
import com.auzmor.smsservice.models.dto.InboundRequestDTO;
import com.auzmor.smsservice.models.dto.InboundResponse;

public interface InboundService {
    public InboundResponse processInboundMessage(InboundRequestDTO inboundRequestDTO, Account account);
}
