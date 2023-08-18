package com.codestates.stackoverflowbe.global.auth.handler;

import com.codestates.stackoverflowbe.domain.account.dto.AccountDto;
import com.codestates.stackoverflowbe.domain.account.entity.Account;
import com.codestates.stackoverflowbe.domain.account.repository.AccountRepository;
import com.codestates.stackoverflowbe.global.exception.BusinessLogicException;
import com.codestates.stackoverflowbe.global.exception.ExceptionCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
public class AccountAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

//    private final AccountRepository accountRepository;
//
//    public AccountAuthenticationSuccessHandler(AccountRepository accountRepository) {
//        this.accountRepository = accountRepository;
//    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        log.info("# Authenticated successfully !");
//        String username = authentication.getName();
//        Optional<Account> optionalAccount = accountRepository.findByEmail(username);
//        Account account = optionalAccount.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
//        String accountId = String.valueOf(account.getAccountId());

//        Gson gson = new Gson();
//        String result = gson.toJson(AccountDto.Response);

//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        response.getWriter().write(accountId);
    }
}
