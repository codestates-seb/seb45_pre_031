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

    public QuestionController(QuestionService questionService, AccountService accountService) {
        this.questionService = questionService;
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<SingleResponseDto<Question>> createQuestion(
            @RequestBody QuestionUpdateDto questionDto,
            @AuthenticationPrincipal UserDetails userDetails) {
        Account account = accountService.findByEmail(userDetails.getUsername());
        Question createdQuestion = questionService.createQuestion(questionDto, account);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SingleResponseDto<>(HttpStatusCode.CREATED, "Question created!", createdQuestion));
    }

    @GetMapping
    public ResponseEntity<QuestionListResponseDto> getQuestions() {
        List<Question> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(new QuestionListResponseDto(HttpStatusCode.OK, "Questions retrieved!", questions));
    }
}
