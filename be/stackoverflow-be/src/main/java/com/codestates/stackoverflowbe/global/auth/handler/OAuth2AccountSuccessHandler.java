package com.codestates.stackoverflowbe.global.auth.handler;

import com.codestates.stackoverflowbe.domain.account.dto.AccountDto;
import com.codestates.stackoverflowbe.domain.account.entity.Account;
import com.codestates.stackoverflowbe.domain.account.service.AccountService;
import com.codestates.stackoverflowbe.global.auth.jwt.JwtTokenizer;
import com.codestates.stackoverflowbe.global.auth.login.dto.LoginResponseDto;
import com.codestates.stackoverflowbe.global.auth.utils.CustomAuthorityUtils;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
//OAuth2 인증이 성공한 이후 동작 (SimpleUrlAuthenticationSuccessHandler : 인증 성공했을 때 URL 지정 등 역할 수행)
public class OAuth2AccountSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static String URL_S3_ENDPOINT = "http://se-sof.s3-website.ap-northeast-2.amazonaws.com";

    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final AccountService accountService;

    public OAuth2AccountSuccessHandler(JwtTokenizer jwtTokenizer,
                                       CustomAuthorityUtils authorityUtils,
                                       AccountService accountService) {
        this.jwtTokenizer = jwtTokenizer;
        this.authorityUtils = authorityUtils;
        this.accountService = accountService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //OAuth2인증이 성공
        log.info("# OAuth2AccountSuccessHandler success!");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = (String) oAuth2User.getAttributes().get("email");
        String name = (String) oAuth2User.getAttributes().get("name");
        System.out.println("name: " + name);

        List<String> authorities = authorityUtils.createRoles(email);

        saveAccount(email, name); // email을 DB에 저장하여 관리하며 매핑하기
        redirect(request, response, email, authorities);
//        String profile = (String) oAuth2User.getAttributes().get("profile");
//        Account account = buildOAuth2Account(email, profile);
//        Account saveAccount = accountService.createAccountOAuth2(account);
    }


    private void saveAccount(String email, String displayName) {
        AccountDto.Post accountPostDto = new AccountDto.Post(email);
        accountPostDto.setDisplayName(displayName);
        //OAuth 전용 DB 저장 로직
        accountService.createAccountOAuth2(accountPostDto);
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response,
                          String username, List<String> authorities) throws IOException {

        // accessToken과 refreshToekn 생성
        String accessToken = delegateAccessToken(username, authorities);
        String refreshToken = delegateRefreshToken(username);

        Account account = accountService.findByEmail(username)
        String displayName = account.getDisplayName();

        //FE 애플리케이션 쪽의 URI 생성.
        String uri = createURI(request, accessToken, refreshToken, displayName).toString();

        // username(email)로 계정 찾아와서 Json 직렬화 이후 응답객체의 body에 입력하기
        Account account = accountService.findByEmail(username);
        String displayName = account.getDisplayName();
        LoginResponseDto loginResponseDto = new LoginResponseDto(displayName);

        Gson gson = new Gson();
        String result = gson.toJson(loginResponseDto);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(result);

        //SimpleUrlAuthenticationSuccessHandler에서 제공하는 sendRedirect() 메서드를 이용해 Frontend 애플리케이션 쪽으로 리다이렉트
        getRedirectStrategy().sendRedirect(request, response, uri);

    }


    private String delegateAccessToken(String username, List<String> authorities) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("roles", authorities);

        String subject = username;
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());

        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

        return accessToken;
    }

    private String delegateRefreshToken(String username) {
        String subject = username;
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

        return refreshToken;
    }
    private Object createURI(HttpServletRequest request, String accessToken, String refreshToken, String displayName) {
        // HTTP 요청의 쿼리 파라미터나 헤더를 구성하기 위한 Map
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("access_token", accessToken);
        queryParams.add("refresh_token", refreshToken);
        queryParams.add("displayName", displayName);

        String requestScheme = request.getScheme();
        String requestHost = request.getServerName();
        int requestPort = request.getServerPort();


        //http://localhost/receive-token.html?access_token=XXX&refresh_token=YYY 형식으로 받도록 함.
        return UriComponentsBuilder
                .newInstance()
                .scheme(requestScheme)
//                .host("localhost")
//                .port(80)
//                .path("/receive-token.html")
//                .path("/login") // 이후 URI 수정
                .host("se-sof.s3-website.ap-northeast-2.amazonaws.com") //"http://seveneleven-stackoverflow-s3.s3-website.ap-northeast-2.amazonaws.com"
                .path("/login")
//                .port(requestPort) //S3는 80포트
                .queryParams(queryParams)
                .build()
                .toUri();

    }
}
