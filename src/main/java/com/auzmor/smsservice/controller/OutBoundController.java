package com.auzmor.smsservice.controller;

import com.auzmor.smsservice.constants.SmsServiceConstants;
import com.auzmor.smsservice.models.Account;
import com.auzmor.smsservice.models.dto.InboundRequestDTO;
import com.auzmor.smsservice.service.InboundService;
import com.auzmor.smsservice.service.OutBoundService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/auzmor")
@Slf4j
public class OutBoundController {
    @Autowired
    private OutBoundService outBoundService;

    @PostMapping("outbound/sms")
    public ResponseEntity outBound(@NotBlank @RequestHeader(SmsServiceConstants.AUTH) String password,
                                  @NotBlank @RequestHeader(SmsServiceConstants.USERNAME) String userName,
                                  @ApiIgnore @RequestAttribute(SmsServiceConstants.ACCOUNT) Account tenant,
                                  @Valid @RequestBody InboundRequestDTO inboundRequestDTO){

        return ResponseEntity.ok(outBoundService.processOutboundMessage(inboundRequestDTO,tenant));
    }
}
