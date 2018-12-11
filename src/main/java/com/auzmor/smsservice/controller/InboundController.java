package com.auzmor.smsservice.controller;

import com.auzmor.smsservice.constants.SmsServiceConstants;
import com.auzmor.smsservice.models.Account;
import com.auzmor.smsservice.models.dto.InboundRequestDTO;
import com.auzmor.smsservice.service.InboundService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/auzmor")
@Slf4j
public class InboundController {

    @Autowired
    private InboundService inboundService;

    @PostMapping("inbound/sms")
    public ResponseEntity inBound(@NotBlank @RequestHeader(SmsServiceConstants.AUTH) String password,
                                  @NotBlank @RequestHeader(SmsServiceConstants.USERNAME) String userName,
                                  @ApiIgnore @RequestAttribute(SmsServiceConstants.ACCOUNT) Account tenant,
                                  @Valid @RequestBody InboundRequestDTO inboundRequestDTO){

        return ResponseEntity.ok(inboundService.processInboundMessage(inboundRequestDTO,tenant));
    }

}
