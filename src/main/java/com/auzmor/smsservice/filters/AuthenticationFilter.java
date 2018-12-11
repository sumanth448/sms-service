package com.auzmor.smsservice.filters;

import com.auzmor.smsservice.constants.SmsServiceConstants;
import com.auzmor.smsservice.exception.AuthorizationException;
import com.auzmor.smsservice.models.Account;
import com.auzmor.smsservice.service.AccountService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order
public class AuthenticationFilter implements Filter {

    @Autowired
    private AccountService accountService;


    public AuthenticationFilter(AccountService accountService) {
        this.accountService = accountService;
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        final HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        if(!httpRequest.getRequestURI().contains("swagger") && !httpRequest.getRequestURI().contains("api-docs") ) {
            String userName = httpRequest.getHeader(SmsServiceConstants.USERNAME);
            String password = httpRequest.getHeader(SmsServiceConstants.AUTH);
            final HttpServletResponse response = (HttpServletResponse) servletResponse;
            try{
                Account account = accountService.getUserName(userName, password);
                httpRequest.setAttribute(SmsServiceConstants.ACCOUNT, account);
            }catch(AuthorizationException ex){
                response.setStatus(HttpStatus.FORBIDDEN.value());
                JSONObject obj = new JSONObject();
                try {
                    obj.put("error_code", ex.getErrorCode());
                    obj.put("error_message", ex.getErrorMessage());
                    response.getOutputStream().print(obj.toString());
                    return;
                } catch (JSONException e) {
                    throw e;
                }
            }

        }
        filterChain.doFilter(httpRequest,servletResponse);
    }


    @Override
    public void destroy() {

    }
}
