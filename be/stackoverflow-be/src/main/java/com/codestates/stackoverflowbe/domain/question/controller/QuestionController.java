package com.codestates.stackoverflowbe.domain.question.controller;

import com.codestates.stackoverflowbe.domain.account.entity.Account;
import com.codestates.stackoverflowbe.domain.account.service.AccountService;
import com.codestates.stackoverflowbe.domain.question.dto.QuestionDto;
import com.codestates.stackoverflowbe.domain.question.entity.Question;
import com.codestates.stackoverflowbe.domain.question.service.QuestionService;
import com.codestates.stackoverflowbe.global.constants.HttpStatusCode;
import com.codestates.stackoverflowbe.global.response.SingleResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Validated
@RequestMapping("/questions")
public class QuestionController {
    // 질문을 관리하는 컨트롤러 클래스

    private final QuestionService questionService;
    private final AccountService accountService; // AccountService 주입

    public QuestionController(QuestionService questionService, AccountService accountService) {
        this.questionService = questionService;
        this.accountService = accountService; // AccountService 주입
    }

    @PostMapping
    public ResponseEntity createQuestion(@RequestBody QuestionDto questionDto, @AuthenticationPrincipal UserDetails userDetails) {
        // 질문을 생성하는 API 엔드포인트

        // 사용자 정보를 이용하여 Account 객체를 가져옴
        Account account = accountService.findByEmail(userDetails.getUsername());

        // QuestionService를 이용하여 새로운 질문을 생성
        Question createdQuestion = questionService.createQuestion(questionDto, account);

        // 생성된 질문을 포함한 응답 엔티티 반환
        return new ResponseEntity(
                new SingleResponseDto<>(HttpStatusCode.CREATED, "question created!", createdQuestion),
                HttpStatus.CREATED
        );
    }
}