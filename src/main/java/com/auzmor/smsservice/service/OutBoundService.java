package com.auzmor.smsservice.service;

import com.auzmor.smsservice.models.Account;
import com.auzmor.smsservice.models.dto.InboundRequestDTO;
import com.auzmor.smsservice.models.dto.InboundResponse;

public interface OutBoundService {
    public InboundResponse processOutboundMessage(InboundRequestDTO inboundRequestDTO, Account account);
}
