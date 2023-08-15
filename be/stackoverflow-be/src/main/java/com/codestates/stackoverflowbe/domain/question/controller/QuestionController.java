package com.codestates.stackoverflowbe.domain.question.controller;

import com.codestates.stackoverflowbe.domain.account.entity.Account;
import com.codestates.stackoverflowbe.domain.account.service.AccountService;
import com.codestates.stackoverflowbe.domain.question.dto.QuestionListResponseDto;
import com.codestates.stackoverflowbe.domain.question.dto.QuestionUpdateDto;
import com.codestates.stackoverflowbe.domain.question.entity.Question;
import com.codestates.stackoverflowbe.domain.question.service.QuestionService;
import com.codestates.stackoverflowbe.global.response.SingleResponseDto;
import com.codestates.stackoverflowbe.global.constants.HttpStatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Validated
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionService questionService;
    private final AccountService accountService;

    // 생성자 주입을 통해 QuestionService와 AccountService를 주입받습니다.
    public QuestionController(QuestionService questionService, AccountService accountService) {
        this.questionService = questionService;
        this.accountService = accountService;
    }

    // 질문 생성 요청을 처리하는 메서드
    @PostMapping
    public ResponseEntity<SingleResponseDto<Question>> createQuestion(
            @RequestBody QuestionUpdateDto questionDto,
            @AuthenticationPrincipal UserDetails userDetails) {
        // 요청을 보낸 사용자의 정보를 가져옵니다.
        Account account = accountService.findByEmail(userDetails.getUsername());
        // QuestionService를 통해 새로운 질문을 생성하고 저장합니다.
        Question createdQuestion = questionService.createQuestion(questionDto, account);

        // 생성된 질문을 담은 응답 객체를 생성하여 반환합니다.
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SingleResponseDto<>(HttpStatusCode.CREATED, "Question created!", createdQuestion));
    }

    // 질문 목록 조회 요청을 처리하는 메서드
    @GetMapping
    public ResponseEntity<QuestionListResponseDto> getQuestions() {
        // QuestionService를 통해 모든 질문 목록을 가져옵니다.
        List<Question> questions = questionService.getAllQuestions();

        // 질문 목록과 함께 응답 객체를 생성하여 반환합니다.
        return ResponseEntity.ok(new QuestionListResponseDto(HttpStatusCode.OK, "Questions retrieved!", questions));
    }
}
