package com.auzmor.smsservice.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;

import javax.ws.rs.core.Response;
import java.util.function.Supplier;

@Getter
@ToString
@Slf4j
public class AuthorizationException extends RuntimeException {
    private final String errorCode;

    private final String errorMessage;

    public AuthorizationException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

//    @ExceptionHandler({ WebExchangeBindException.class })
//    @ResponseStatus(value = HttpStatus.FORBIDDEN)
//    @ResponseBody
//    public String handleSmsServiceException(WebExchangeBindException ex) {
//        JSONObject obj = new JSONObject();
//        try {
//            obj.put("error_code", "TXN-400");
//            obj.put("error_message", ex.getFieldError().getField() + " " + ex.getFieldError().getDefaultMessage());
//            log.info("Formatting Exception response {}", obj.toString());
//        } catch (JSONException e) {
//            log.error("Error creating json obj {}", e);
//        }
//        return obj.toString();
//    }

}
