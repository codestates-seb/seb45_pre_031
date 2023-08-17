package com.codestates.stackoverflowbe.domain.question.controller;

import com.codestates.stackoverflowbe.domain.account.entity.Account;
import com.codestates.stackoverflowbe.domain.account.service.AccountService;
import com.codestates.stackoverflowbe.domain.question.dto.QuestionListResponseDto;
import com.codestates.stackoverflowbe.domain.question.dto.QuestionUpdateDto;
import com.codestates.stackoverflowbe.domain.question.dto.QuestionUpdateRequestDto;
import com.codestates.stackoverflowbe.domain.question.entity.Question;
import com.codestates.stackoverflowbe.domain.question.service.QuestionService;
import com.codestates.stackoverflowbe.global.response.SingleResponseDto;
import com.codestates.stackoverflowbe.global.constants.HttpStatusCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Question", description = "질문 기능")
@Slf4j
@RestController
@Validated
@RequestMapping("/v1/questions")
public class QuestionController {
    private final QuestionService questionService;
    private final AccountService accountService;

    public QuestionController(QuestionService questionService, AccountService accountService) {
        this.questionService = questionService;
        this.accountService = accountService;
    }

    // 질문 생성 요청을 처리하는 메서드
    @Operation(summary = "Post Question", description = "질문 생성 기능")
    @PostMapping
    public ResponseEntity<SingleResponseDto<Question>> createQuestion(@RequestBody QuestionUpdateDto questionDto,
                                                                      @AuthenticationPrincipal UserDetails userDetails) {
        // 요청을 보낸 사용자의 정보를 가져옵니다.
        Account account = accountService.findByEmail(userDetails.getUsername());
        // QuestionService를 통해 새로운 질문을 생성하고 저장합니다.
        Question createdQuestion = questionService.createQuestion(questionDto, account);
        // 생성된 질문을 담은 응답 객체를 생성하여 반환합니다.
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SingleResponseDto<>(HttpStatusCode.CREATED.getStatusCode(), "Question created!", createdQuestion));
    }

    // 질문 목록 조회 요청을 처리하는 메서드
    @Operation(summary = "Get All Question", description = "전체 질문 조회 기능")
    @GetMapping
    public ResponseEntity<QuestionListResponseDto> getQuestions() {
        // QuestionService를 통해 모든 질문 목록을 가져옵니다.
        List<Question> questions = questionService.getAllQuestions();
        // 질문 목록과 함께 응답 객체를 생성하여 반환합니다.
        return ResponseEntity.ok(new QuestionListResponseDto(HttpStatusCode.OK, "Questions retrieved!", questions));
    }
    // 질문 수정 요청을 처리하는 메서드
    @Operation(summary = "Put Question", description = "질문 수정 기능")
    @PutMapping("/{questionId}")
    public ResponseEntity<SingleResponseDto<Question>> updateQuestion(
            @PathVariable Long questionId,
            @RequestBody QuestionUpdateRequestDto updateDto,
            @AuthenticationPrincipal UserDetails userDetails) {
        // 질문을 찾습니다.
        Question question = questionService.findQuestionById(questionId);
        if (question == null) {
            return ResponseEntity.notFound().build();
        }
        // 사용자 정보를 가져옵니다.
        Account account = accountService.findByEmail(userDetails.getUsername());
        // 질문을 수정합니다.
        question.updateQuestion(updateDto);
        questionService.saveQuestion(question);
        // 수정된 질문을 반환합니다.
        return ResponseEntity.ok(new SingleResponseDto<>(HttpStatusCode.OK.getStatusCode(), "Question updated!", question));
    }
    // 질문 삭제 요청을 처리하는 메서드
    @Operation(summary = "Delete Question", description = "질문 삭제 기능")
    @DeleteMapping("/{questionId}")
    public ResponseEntity<SingleResponseDto<String>> deleteQuestion(
            @PathVariable Long questionId,
            @AuthenticationPrincipal UserDetails userDetails) {
        // 질문을 찾습니다.
        Question question = questionService.findQuestionById(questionId);
        if (question == null) {
            return ResponseEntity.notFound().build();
        }
        // 사용자 정보를 가져옵니다.
        Account account = accountService.findByEmail(userDetails.getUsername());
        // 질문 작성자와 현재 로그인한 사용자가 일치하는지 확인합니다.
        if (!question.getAccount().equals(account)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new SingleResponseDto<>(HttpStatusCode.FORBIDDEN.getStatusCode(), "You don't have permission to delete this question.", null));
        }
        // 질문을 삭제합니다.
        questionService.deleteQuestion(question);
        // 삭제 성공 응답을 반환합니다.
        return ResponseEntity.ok(new SingleResponseDto<>(HttpStatusCode.OK.getStatusCode(), "Question deleted!", null));
    }
    // 질문의 상세 정보를 조회하는 메서드
    @GetMapping("/{questionId}")
    public ResponseEntity<SingleResponseDto<Question>> getQuestionDetail(@PathVariable Long questionId) {
        // QuestionService를 통해 특정 ID에 해당하는 질문을 조회합니다.
        Question question = questionService.findQuestionById(questionId);
        if (question == null) {
            return ResponseEntity.notFound().build();
        }
        // 조회한 질문을 반환합니다.
        return ResponseEntity.ok(new SingleResponseDto<>(HttpStatusCode.OK, "Question retrieved!", question));
    }
    // 사용자별 질문 조회 요청을 처리하는 메서드
    @Operation(summary = "Get User's Question", description = "사용자 별 질문 조회 기능")
    @GetMapping("/user")
    public ResponseEntity<List<Question>> getUserQuestions(@AuthenticationPrincipal UserDetails userDetails) {
        // 현재 로그인한 사용자의 정보를 가져옵니다.
        Account account = accountService.findByEmail(userDetails.getUsername());
        // 사용자별 작성한 질문 목록을 조회합니다.
        List<Question> userQuestions = questionService.getQuestionsByUser(account);
        // 조회한 질문 목록을 반환합니다.
        return ResponseEntity.ok(userQuestions);
    }
    // 최신 질문 조회 요청을 처리하는 메서드
    @Operation(summary = "Get Question Sorted By CreatedAt", description = "최신순 질문 조회 기능")
    @GetMapping("/newest")
    public ResponseEntity<Page<Question>> getNewestQuestions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        // QuestionService를 통해 최신 질문 목록을 가져옵니다.
        Page<Question> newestQuestions = questionService.getNewestQuestions(page, size);
        // 최신 질문 페이지를 반환합니다.
        return ResponseEntity.ok(newestQuestions);
    }
    // 인기 질문 조회 요청을 처리하는 메서드
    @Operation(summary = "Get Question Sorted By Views", description = "조회순 질문 조회 기능")
    @GetMapping("/hot")
    public ResponseEntity<Page<Question>> getHotQuestions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        // QuestionService를 통해 인기 질문 목록을 가져옵니다.
        Page<Question> hotQuestions = questionService.getHotQuestions(page, size);
        // 인기 질문 페이지를 반환합니다.
        return ResponseEntity.ok(hotQuestions);
    }
    // 지난 주 동안 가장 많이 본 질문 조회 요청을 처리하는 메서드
    @Operation(summary = "Get Question Sorted By CreatedAt For Last Week", description = "지난주동안 작성된 질문 최신순 조회 기능")
    @GetMapping("/week")
    public ResponseEntity<Page<Question>> getWeekQuestions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        // QuestionService를 통해 지난 주 동안 가장 많이 본 질문 목록을 가져옵니다.
        Page<Question> weekQuestions = questionService.getWeekQuestions(page, size);
        // 지난 주의 가장 많이 본 질문 페이지를 반환합니다.
        return ResponseEntity.ok(weekQuestions);
    }
    // 지난 달 동안 가장 많이 본 질문 조회 요청을 처리하는 메서드
    @Operation(summary = "Get Question Sorted By Views For Last Month", description = "지난달동안 작성된 질문 조회순 조회 기능")
    @GetMapping("/month")
    public ResponseEntity<Page<Question>> getMonthQuestions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        // QuestionService를 통해 지난 달 동안 가장 많이 본 질문 목록을 가져옵니다.
        Page<Question> monthQuestions = questionService.getMonthQuestions(page, size);
        // 지난 달의 가장 많이 본 질문 페이지를 반환합니다.
        return ResponseEntity.ok(monthQuestions);
    }
    // 질문 목록 조회 요청을 처리하는 메서드 (Active: 수정 시간 최신순으로)
    @GetMapping("/active")
    public ResponseEntity<QuestionListResponseDto> getActiveQuestions() {
        List<Question> activeQuestions = questionService.getActiveQuestions();
        return ResponseEntity.ok(new QuestionListResponseDto(HttpStatusCode.OK, "Active questions retrieved!", activeQuestions));
    }

    // 질문 목록 조회 요청을 처리하는 메서드 (Score: Score 순으로)
//    @GetMapping("/score")
//    public ResponseEntity<QuestionListResponseDto> getScoreQuestions() {
//        List<Question> scoreQuestions = questionService.getScoreQuestions();
//        return ResponseEntity.ok(new QuestionListResponseDto(HttpStatusCode.OK, "Scored questions retrieved!", scoreQuestions));
//    }

    // 질문 목록 조회 요청을 처리하는 메서드 (Unanswered: 답변이 없는 질문 필터)
    @GetMapping("/unanswered")
    public ResponseEntity<QuestionListResponseDto> getUnansweredQuestions() {
        List<Question> unansweredQuestions = questionService.getUnansweredQuestions();
        return ResponseEntity.ok(new QuestionListResponseDto(HttpStatusCode.OK, "Unanswered questions retrieved!", unansweredQuestions));
    }
}