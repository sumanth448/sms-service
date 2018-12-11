package com.auzmor.smsservice.config;

import com.auzmor.smsservice.exception.AuthorizationException;
import com.auzmor.smsservice.exception.SmsServiceException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;

import javax.validation.ValidationException;

@ControllerAdvice
@Slf4j
public class SmsServiceExceptionHandler {
    @ExceptionHandler({SmsServiceException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleSmsServiceException(SmsServiceException ex) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("error_code", ex.getErrorCode());
            obj.put("error_message", ex.getErrorMessage());
            log.info("Formatting Exception response {}", obj.toString());
        } catch (JSONException e) {
            log.error("Error creating json obj {}", e);
        }
        return obj.toString();
    }

    @ExceptionHandler({AuthorizationException.class})
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ResponseBody
    public String handleSmsServiceAuthException(AuthorizationException ex) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("error_code", ex.getErrorCode());
            obj.put("error_message", ex.getErrorMessage());
            log.info("Formatting Exception response {}", obj.toString());
        } catch (JSONException e) {
            log.error("Error creating json obj {}", e);
        }
        return obj.toString();
    }

    @ExceptionHandler({WebExchangeBindException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleSmsServiceException(WebExchangeBindException ex) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("error_code", "TXN-400");
            obj.put("error_message", ex.getFieldError().getField() + " " + ex.getFieldError().getDefaultMessage());
            log.info("Formatting Exception response {}", obj.toString());
        } catch (JSONException e) {
            log.error("Error creating json obj {}", e);
        }
        return obj.toString();
    }

    @ExceptionHandler({ ValidationException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleValidationException(ValidationException ex) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("error_code", "TXN-400");
            obj.put("message", ex.getMessage());
            log.info("Formatting Exception response {}", obj.toString());
        } catch (JSONException e) {
            log.error("Error creating json obj {}", e);
        }
        return obj.toString();
    }
}
