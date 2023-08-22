package com.codestates.stackoverflowbe.global.auth.handler;

import com.codestates.stackoverflowbe.domain.account.entity.Account;
import com.codestates.stackoverflowbe.domain.account.service.AccountService;
import com.codestates.stackoverflowbe.global.auth.login.dto.LoginResponseDto;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class AccountAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final AccountService accountService;

    public AccountAuthenticationSuccessHandler(AccountService accountService) {
        this.accountService = accountService;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        log.info("# Authenticated successfully !");
        authentication.getDetails();
        String username = authentication.getName();
        Account account = accountService.findByEmail(username);
        String displayName = account.getDisplayName();
        LoginResponseDto loginResponseDto = new LoginResponseDto(displayName);

        Gson gson = new Gson();
        String result = gson.toJson(loginResponseDto);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(result);
    }
}
