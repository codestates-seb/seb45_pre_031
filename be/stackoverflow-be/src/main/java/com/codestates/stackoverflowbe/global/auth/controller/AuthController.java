package com.codestates.stackoverflowbe.global.auth.controller;

import com.codestates.stackoverflowbe.domain.account.service.AccountService;
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
        // 시큐리티 컨텍스트 홀더에 저장되어 있는 인증 정보 가져오기
        Authentication authentication =
        Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication()).orElseThrow(()
                -> new BusinessLogicException(ExceptionCode.NOT_AUTHENTICATED));

        //인증 주체 가져와 OAuth2User로 형변환하기
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // 인증된 OAuth2User로부터 속성값들 가져오기
        Map<String, Object> attributes = oAuth2User.getAttributes();


        // 공급자가 google일 경우
        if(attributes.get("provider").equals("google")) {
            // displayName 찾기
            String displayName = (String) attributes.get("name");
            return ResponseEntity.ok(new LoginResponseDto(displayName));
        }

        // ContextHolder에 저장된 정보가 없다면 예외 던지기
        throw new BusinessLogicException(ExceptionCode.NOT_AUTHENTICATED);
    }
}
