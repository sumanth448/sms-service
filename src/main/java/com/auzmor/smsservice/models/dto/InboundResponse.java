package com.auzmor.smsservice.models.dto;

import lombok.Data;

@Data
public class InboundResponse {
    private String message;
    private String error;
}
