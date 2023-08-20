package com.codestates.stackoverflowbe.global.auth.config;

import com.codestates.stackoverflowbe.domain.account.repository.AccountRepository;
import com.codestates.stackoverflowbe.domain.account.service.AccountService;
import com.codestates.stackoverflowbe.global.auth.filter.JwtVerificationFilter;
import com.codestates.stackoverflowbe.global.auth.handler.*;
import com.codestates.stackoverflowbe.global.auth.filter.JwtAuthenticationFilter;
import com.codestates.stackoverflowbe.global.auth.jwt.JwtTokenizer;
import com.codestates.stackoverflowbe.global.auth.utils.CustomAuthorityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final AccountService accountService;

    private final SecurityCorsConfig corsConfig;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .headers().frameOptions().sameOrigin() // (해당 옵션 유효한 경우 h2사용가능) SOP 정책 유지, 다른 도메인에서 iframe 로드 방지
                .and()
//                .cors(Customizer.withDefaults()) //CORS 처리하는 가장 쉬운 방법인 CorsFilter 사용, CorsConfigurationSource Bean을 제공
//                .cors(configuration -> configuration
//                        .configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()))
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 정보 저장X
                .and()
                .formLogin().disable() // CSR 방식을 사용하기 때문에 formLogin 방식 사용하지 않음
                .httpBasic().disable() // UsernamePasswordAuthenticationFilter, BasicAuthenticationFilter 등 비활성화
                .exceptionHandling() // 예외처리 기능
                    .authenticationEntryPoint(new AccountAuthenticationEntryPoint()) // 인증 실패시 처리 (UserAuthenticationEntryPoint 동작)
                    .accessDeniedHandler(new AccountAccessDeniedHandler()) //인가 거부시 UserAccessDeniedHandler가 처리되도록 설계
                .and()
                .apply(new CustomFilterConfigurer()) // 커스터마이징한 필터 추가
                .and() // 허용되는 HttpMethod와 역할 설정
                .authorizeHttpRequests( authorize -> authorize
                        .antMatchers(HttpMethod.GET, "/v1/accounts/**").hasRole("ADMIN")
                        .antMatchers(HttpMethod.POST, "/v1/accounts/**").permitAll()
                        .anyRequest().permitAll()
                )
                .oauth2Login(
                        oauth2 -> oauth2
                                .successHandler(new OAuth2AccountSuccessHandler(jwtTokenizer, authorityUtils, accountService))
                );


        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        // 자격증명 (예: 쿠키, 인증 헤더 등)을 허용
//        configuration.setAllowCredentials(true);
//        //모든 출처(Origin)에 대해 스크립트 기반 HTTP 통신 허용 (단, configuration.setAllowCredentials(true)와 함께 적용 불가!)
////        configuration.setAllowedOrigins(Arrays.asList("*"));
//        configuration.setAllowedOrigins(Arrays.asList("http://localhost:80",
//                "http://localhost:8080",
//                "http://localhost:3000",
//                "http://seveneleven-stackoverflow-s3.s3-website.ap-northeast-2.amazonaws.com",
//                "3.36.128.133:8080",
//                "3.36.128.133:80"));
//
//        //4가지 HTTP Method에 대한 HTTP 통신 허용
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE", "OPTIONS"));
//
//        // CorsConfigurationSource 인터페이스 구현 클래스인 UrlBasedCorsConfigurationSource 클래스 객체 생성
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//
//        // 모든 URL에 상기 CORS 정책 적용
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {

        @Override
        public void configure(HttpSecurity builder) throws Exception {
            // authenticationManager : 사용자가 로그인 요청시 입력한 아이디와 패스워드를 해당 객체로 전달하여 인증 수행하며, 결과에 따라 로직 처리
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class); // AuthenticationManager 객체얻기

            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer); // JwtAuthenticationFilter 객체 생성하며 DI하기

            // AbstractAuthenticationProcessingFilter에서 상속받은 filterProcessurl을 설정 (설정하지 않으면 default 값인 /Login)
            jwtAuthenticationFilter.setFilterProcessesUrl("/v1/accounts/authenticate");
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new AccountAuthenticationSuccessHandler());
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new AccountAuthenticationFailureHandler());

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authorityUtils);

            // Spring Security FilterChain에 추가
            builder
                    .addFilter(corsConfig.corsFilter())
                    .addFilter(jwtAuthenticationFilter)
                    // OAuth2LoginAuthenticationFilter : OAuth2.0 권한 부여 응답 처리 클래스 뒤에 jwtVerificationFilter 추가 (Oauth)
                    .addFilterAfter(jwtVerificationFilter, OAuth2LoginAuthenticationFilter.class);
        }
    }



}
