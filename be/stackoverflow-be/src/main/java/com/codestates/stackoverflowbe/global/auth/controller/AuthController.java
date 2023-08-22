package com.codestates.stackoverflowbe.global.auth.controller;

import com.codestates.stackoverflowbe.domain.account.service.AccountService;
import com.codestates.stackoverflowbe.global.auth.login.dto.LoginDto;
import com.codestates.stackoverflowbe.global.auth.login.dto.LoginResponseDto;
import com.codestates.stackoverflowbe.global.exception.BusinessLogicException;
import com.codestates.stackoverflowbe.global.exception.ExceptionCode;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v1/auth")
@Tag(name = "Auth", description = "인증 기능")
public class AuthController {

    private final AccountService accountService;

    public AuthController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/oauth")
    public ResponseEntity<LoginResponseDto> getOAuth2UserDisplayName() {
        /*
            accountService :
         */

        // 시큐리티 컨텍스트 홀더에 저장되어 있는 인증 정보 가져오기
        Authentication authentication =
        Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication()).orElseThrow(()
                -> new BusinessLogicException(ExceptionCode.NOT_AUTHENTICATED));

        Map<String, Object> claims = (Map<String, Object>) authentication.getPrincipal();
        LoginResponseDto loginResponseDto = new LoginResponseDto((String) claims.get("displayName"));
        return ResponseEntity.ok(loginResponseDto);
    }
}
