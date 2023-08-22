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
import com.codestates.stackoverflowbe.global.exception.BusinessLogicException;
import com.codestates.stackoverflowbe.global.exception.ExceptionCode;
import com.codestates.stackoverflowbe.global.response.MultiResponseDto;
import com.codestates.stackoverflowbe.global.response.SingleResponseDto;
import com.codestates.stackoverflowbe.global.constants.HttpStatusCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.constraints.Positive;
import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Tag(name = "Question", description = "질문 기능")
@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/v1/questions")
public class QuestionController {
    private final static String QUESTION_DEFAULT_URL = "/v1/questions";
    private final QuestionService questionService;
    private final AccountService accountService;

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

    // 질문 수정 요청을 처리하는 메서드
    @Operation(summary = "Patch Question", description = "질문 수정 기능")
    @PatchMapping("/{questionId}")
    public ResponseEntity<HttpStatus> updateQuestion(@PathVariable Long questionId,
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
        return ResponseEntity.noContent().build();
    }

    // 질문 삭제 요청을 처리하는 메서드
    @Operation(summary = "Delete Question", description = "질문 삭제 기능")
    @DeleteMapping("/{questionId}")
    public ResponseEntity<HttpStatus> deleteQuestion(@PathVariable Long questionId) {
        // 질문을 찾습니다.
        Question question = questionService.findQuestionById(questionId);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 요청을 보낸 사용자의 정보를 가져옵니다.
        Account account = accountService.findByEmail((String) principal);

        // 질문 작성자와 현재 로그인한 사용자가 일치하는지 확인합니다.
        if (!question.getAccount().equals(account)) throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_ALLOW);
        // 질문을 삭제합니다.
        questionService.deleteQuestion(question);
        // 삭제 성공 응답을 반환합니다.
        return ResponseEntity.noContent().build();
    }

    // 사용자별 질문 조회 요청을 처리하는 메서드
    @Operation(summary = "Get User's Question", description = "사용자 별 질문 조회 기능")
    @GetMapping("/user")
    public ResponseEntity<MultiResponseDto<QuestionResponseDto>> getUserQuestions(@Positive @RequestParam(defaultValue = "1") int page,
                                                                                  @Positive @RequestParam(defaultValue = "15") int size) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 요청을 보낸 사용자의 정보를 가져옵니다.
        Account account = accountService.findByEmail((String) principal);

        // 사용자별 작성한 질문 목록을 조회합니다.
        Page<QuestionResponseDto> pageQuestionDtos = questionService.getQuestionsByUser(page - 1, size, account);

        // 조회한 질문 목록을 반환합니다.
        return ResponseEntity.ok(MultiResponseDto.<QuestionResponseDto>builder()
                        .status(HttpStatusCode.OK.getStatusCode())
                        .message(HttpStatusCode.OK.getMessage())
                        .data(pageQuestionDtos.getContent())
                        .page(pageQuestionDtos)
                        .build());
    }

    // 탭["Newest", "Active", "Unanswered", "Score", "Pop(week)", "Pop(month)"] 별 질문 조회 요청을 처리하는 메서드
    @Operation(summary = "Get Questions", description = "탭 별 질문 조회 기능")
    @GetMapping("/tabQuestion")
    public ResponseEntity<MultiResponseDto<QuestionResponseDto>> getTabQuestions(@Positive @RequestParam(defaultValue = "1") int page,
                                                                                 @Positive @RequestParam(defaultValue = "15") int size,
                                                                                 @RequestParam String tab) {
        Page<QuestionResponseDto> pageQuestionDtos = new PageImpl<>(new ArrayList<>());

        if (tab.equals("Newest")) pageQuestionDtos = questionService.getNewestQuestions(page - 1, size);
        else if (tab.equals("Active")) pageQuestionDtos = questionService.getActiveQuestions(page - 1, size);
        else if (tab.equals("Unanswered")) pageQuestionDtos = questionService.getUnansweredQuestions(page - 1, size);
        else if (tab.equals("Pop(week)")) pageQuestionDtos = questionService.getWeekQuestions(page - 1, size);
        else if (tab.equals("Pop(month)")) pageQuestionDtos = questionService.getMonthQuestions(page - 1, size);
        else if (tab.equals("Score")) {
            pageQuestionDtos = questionService.getScoreQuestions(page - 1, size);
            return ResponseEntity.ok(MultiResponseDto.<QuestionResponseDto>builder()
                    .status(HttpStatusCode.OK.getStatusCode())
                    .message(HttpStatusCode.OK.getMessage())
                    .data(questionService.getScoreQuestionsList(pageQuestionDtos))
                    .page(pageQuestionDtos)
                    .build());
        }

        // 조회한 질문 목록을 반환합니다.
        return ResponseEntity.ok(MultiResponseDto.<QuestionResponseDto>builder()
                .status(HttpStatusCode.OK.getStatusCode())
                .message(HttpStatusCode.OK.getMessage())
                .data(pageQuestionDtos.getContent())
                .page(pageQuestionDtos)
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
}