package com.codestates.stackoverflowbe.domain.question.controller;

import com.codestates.stackoverflowbe.domain.account.entity.Account;
import com.codestates.stackoverflowbe.domain.account.service.AccountService;
import com.codestates.stackoverflowbe.domain.question.dto.QuestionListResponseDto;
import com.codestates.stackoverflowbe.domain.question.dto.QuestionResponseDto;
import com.codestates.stackoverflowbe.domain.question.dto.QuestionUpdateDto;
import com.codestates.stackoverflowbe.domain.question.dto.QuestionUpdateRequestDto;
import com.codestates.stackoverflowbe.domain.question.entity.Question;
import com.codestates.stackoverflowbe.domain.question.service.QuestionService;
import com.codestates.stackoverflowbe.domain.vote.service.VoteService;
import com.codestates.stackoverflowbe.global.response.MultiResponseDto;
import com.codestates.stackoverflowbe.global.response.SingleResponseDto;
import com.codestates.stackoverflowbe.global.constants.HttpStatusCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Question", description = "질문 기능")
@Slf4j
@RestController
@Validated
@RequestMapping("/v1/questions")
public class QuestionController {
    private final static String QUESTION_DEFAULT_URL = "/v1/questions";
    private final QuestionService questionService;
    private final AccountService accountService;
    private final VoteService voteService;

    public QuestionController(QuestionService questionService, AccountService accountService, VoteService voteService) {
        this.questionService = questionService;
        this.accountService = accountService;
        this.voteService = voteService;
    }

    // 질문 생성 요청을 처리하는 메서드
    @Operation(summary = "Post Question", description = "질문 생성 기능")
    @PostMapping
    public ResponseEntity<HttpStatus> createQuestion(@RequestBody QuestionUpdateDto questionDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 요청을 보낸 사용자의 정보를 가져옵니다.
        Account account = accountService.findByEmail((String) principal);
        // QuestionService를 통해 새로운 질문을 생성하고 저장합니다.
        Question createdQuestion = questionService.createQuestion(questionDto, account);

        URI location = UriComponentsBuilder
                .newInstance()
                .path(QUESTION_DEFAULT_URL + "/{questionId}")
                .buildAndExpand(createdQuestion.getQuestionId())
                .toUri();

        // 생성된 질문을 담은 응답 객체를 생성하여 반환합니다.
        return ResponseEntity.created(location).build();
    }

    // 질문의 상세 정보를 조회하는 메서드
    @GetMapping("/{questionId}")
    public ResponseEntity<SingleResponseDto<QuestionResponseDto>> getQuestionDetail(@PathVariable Long questionId) {
        // QuestionService를 통해 특정 ID에 해당하는 질문을 조회합니다.
        Question question = questionService.findQuestionById(questionId);
        question.setViewCount(question.getViewCount() + 1);

        QuestionResponseDto responseDto = questionService.getQuestionQuestionResponseDto(question);
        // 조회한 질문을 반환합니다.
        return ResponseEntity.ok(new SingleResponseDto<>(HttpStatusCode.OK.getStatusCode(), HttpStatusCode.OK.getMessage(), responseDto));
    }

    // 질문 목록 조회 요청을 처리하는 메서드
    @Operation(summary = "Get All Question", description = "전체 질문 조회 기능")
    @GetMapping
    public ResponseEntity<MultiResponseDto<QuestionResponseDto>> getQuestions() {
        // QuestionService를 통해 모든 질문 목록을 가져옵니다.
        Page<QuestionResponseDto> pageQuestionDtos = questionService.getAllQuestions();

        // 질문 목록과 함께 응답 객체를 생성하여 반환합니다.
        return ResponseEntity.ok(MultiResponseDto.<QuestionResponseDto>builder()
                        .status(HttpStatusCode.OK.getStatusCode())
                        .message(HttpStatusCode.OK.getMessage())
                        .data(pageQuestionDtos.getContent())
                        .page(pageQuestionDtos)
                        .build());
    }

    // 질문 수정 요청을 처리하는 메서드
    @Operation(summary = "Put Question", description = "질문 수정 기능")
    @PatchMapping("/{questionId}")
    public ResponseEntity<SingleResponseDto<Question>> updateQuestion(@PathVariable Long questionId,
                                                                      @RequestBody QuestionUpdateRequestDto updateDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 요청을 보낸 사용자의 정보를 가져옵니다.
        Account account = accountService.findByEmail((String) principal);

        // 질문을 찾습니다.
        Question question = questionService.findQuestionById(questionId);

        // 질문을 수정합니다.
        question.updateQuestion(updateDto);
        questionService.saveQuestion(question);
        // 수정된 질문을 반환합니다.
        return ResponseEntity.ok().build();
    }

    // 질문 삭제 요청을 처리하는 메서드
    @Operation(summary = "Delete Question", description = "질문 삭제 기능")
    @DeleteMapping("/{questionId}")
    public ResponseEntity<SingleResponseDto<String>> deleteQuestion(@PathVariable Long questionId) {
        // 질문을 찾습니다.
        Question question = questionService.findQuestionById(questionId);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 요청을 보낸 사용자의 정보를 가져옵니다.
        Account account = accountService.findByEmail((String) principal);

        // 질문 작성자와 현재 로그인한 사용자가 일치하는지 확인합니다.
        if (!question.getAccount().equals(account)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new SingleResponseDto<>(HttpStatusCode.FORBIDDEN.getStatusCode(), "You don't have permission to delete this question.", null));
        }
        // 질문을 삭제합니다.
        questionService.deleteQuestion(question);
        // 삭제 성공 응답을 반환합니다.
        return ResponseEntity.noContent().build();
    }

    // 사용자별 질문 조회 요청을 처리하는 메서드
    @Operation(summary = "Get User's Question", description = "사용자 별 질문 조회 기능")
    @GetMapping("/user")
    public ResponseEntity<MultiResponseDto<QuestionResponseDto>> getUserQuestions() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 요청을 보낸 사용자의 정보를 가져옵니다.
        Account account = accountService.findByEmail((String) principal);

        // 사용자별 작성한 질문 목록을 조회합니다.
        Page<QuestionResponseDto> pageQuestionDtos = questionService.getQuestionsByUser(account);

        // 조회한 질문 목록을 반환합니다.
        return ResponseEntity.ok(MultiResponseDto.<QuestionResponseDto>builder()
                        .status(HttpStatusCode.OK.getStatusCode())
                        .message(HttpStatusCode.OK.getMessage())
                        .data(pageQuestionDtos.getContent())
                        .page(pageQuestionDtos)
                        .build());
    }




    // 최신 질문 조회 요청을 처리하는 메서드
    @Operation(summary = "Get Question Sorted By CreatedAt", description = "최신순 질문 조회 기능")
    @GetMapping("/newest")
    public ResponseEntity<MultiResponseDto<QuestionResponseDto>> getNewestQuestions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        // QuestionService를 통해 최신 질문 목록을 가져옵니다.
        Page<QuestionResponseDto> newestQuestions = questionService.getNewestQuestions(page, size);
        // 최신 질문 페이지를 반환합니다.
        return ResponseEntity.ok(MultiResponseDto.<QuestionResponseDto>builder()
                        .status(HttpStatusCode.OK.getStatusCode())
                        .message(HttpStatusCode.OK.getMessage())
                        .data(newestQuestions.getContent())
                        .page(newestQuestions)
                        .build());
    }

    // 인기 질문 조회 요청을 처리하는 메서드
    @Operation(summary = "Get Question Sorted By Views", description = "조회순 질문 조회 기능")
    @GetMapping("/hot")
    public ResponseEntity<MultiResponseDto<QuestionResponseDto>> getHotQuestions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        // QuestionService를 통해 인기 질문 목록을 가져옵니다.
        Page<QuestionResponseDto> hotQuestions = questionService.getHotQuestions(page, size);
        // 인기 질문 페이지를 반환합니다.
        return ResponseEntity.ok(MultiResponseDto.<QuestionResponseDto>builder()
                .status(HttpStatusCode.OK.getStatusCode())
                .message(HttpStatusCode.OK.getMessage())
                .data(hotQuestions.getContent())
                .page(hotQuestions)
                .build());
    }
    // 지난 주 동안 가장 많이 본 질문 조회 요청을 처리하는 메서드
    @Operation(summary = "Get Question Sorted By CreatedAt For Last Week", description = "지난주동안 작성된 질문 최신순 조회 기능")
    @GetMapping("/week")
    public ResponseEntity<MultiResponseDto<QuestionResponseDto>> getWeekQuestions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        // QuestionService를 통해 지난 주 동안 가장 많이 본 질문 목록을 가져옵니다.
        Page<QuestionResponseDto> weekQuestions = questionService.getWeekQuestions(page, size);
        // 지난 주의 가장 많이 본 질문 페이지를 반환합니다.
        return ResponseEntity.ok(MultiResponseDto.<QuestionResponseDto>builder()
                .status(HttpStatusCode.OK.getStatusCode())
                .message(HttpStatusCode.OK.getMessage())
                .data(weekQuestions.getContent())
                .page(weekQuestions)
                .build());
    }
    // 지난 달 동안 가장 많이 본 질문 조회 요청을 처리하는 메서드
    @Operation(summary = "Get Question Sorted By Views For Last Month", description = "지난달동안 작성된 질문 조회순 조회 기능")
    @GetMapping("/month")
    public ResponseEntity<MultiResponseDto<QuestionResponseDto>> getMonthQuestions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        // QuestionService를 통해 지난 달 동안 가장 많이 본 질문 목록을 가져옵니다.
        Page<QuestionResponseDto> monthQuestions = questionService.getMonthQuestions(page, size);
        // 지난 달의 가장 많이 본 질문 페이지를 반환합니다.
        return ResponseEntity.ok(MultiResponseDto.<QuestionResponseDto>builder()
                .status(HttpStatusCode.OK.getStatusCode())
                .message(HttpStatusCode.OK.getMessage())
                .data(monthQuestions.getContent())
                .page(monthQuestions)
                .build());
    }

    // 질문 목록 조회 요청을 처리하는 메서드 (Active: 수정 시간 최신순으로)
    @GetMapping("/active")
    public ResponseEntity<SingleResponseDto<List<QuestionResponseDto>>> getActiveQuestions() {
        List<QuestionResponseDto> activeQuestions = questionService.getActiveQuestions().stream()
                .map(questionService::getQuestionQuestionResponseDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new SingleResponseDto<>(HttpStatusCode.OK.getStatusCode(), HttpStatusCode.OK.getMessage(), activeQuestions));
    }

    // 질문 목록 조회 요청을 처리하는 메서드 (Score: Vote Score 순으로)
    @GetMapping("/score")
    public ResponseEntity<List<QuestionResponseDto>> getScoreQuestions() {
        List<QuestionResponseDto> scoreQuestions = questionService.getScoreQuestions();
        return ResponseEntity.ok(scoreQuestions);
    }

    // 질문 목록 조회 요청을 처리하는 메서드 (Unanswered: 답변이 없는 질문 필터)
    @GetMapping("/unanswered")
    public ResponseEntity<List<QuestionResponseDto>> getUnansweredQuestions() {
        List<QuestionResponseDto> unansweredQuestions = questionService.getUnansweredQuestions();
        return ResponseEntity.ok(unansweredQuestions);
    }
}