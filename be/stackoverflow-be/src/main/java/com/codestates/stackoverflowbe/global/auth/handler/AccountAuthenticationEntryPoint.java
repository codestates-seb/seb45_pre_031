package com.codestates.stackoverflowbe.global.auth.handler;

import com.codestates.stackoverflowbe.global.response.ErrorResponder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AccountAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override // 인증 실패시 (AuthenticationException 발생) commence 메서드 동작
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        Exception exception = (Exception) request.getAttribute("exception");
        ErrorResponder.sendErrorResponse(response, HttpStatus.UNAUTHORIZED); // 인증 실패를 나타내는 FORBIDDEN

        logExceptionMessage(authException, exception);

//        response.sendRedirect("/accounts/login"); // 다시 로그인 폼으로 리다이렉션
    }

    private void logExceptionMessage(AuthenticationException authException, Exception exception) {
        String message = exception != null ? exception.getMessage() : authException.getMessage();
        log.warn("Unauthorized error happend : {}", message);
    }
}
