package com.codestates.stackoverflowbe.domain.answer.controller;

import com.codestates.stackoverflowbe.domain.account.entity.Account;
import com.codestates.stackoverflowbe.domain.answer.dto.AnswerDto;
import com.codestates.stackoverflowbe.domain.answer.service.AnswerService;
import com.codestates.stackoverflowbe.global.constants.HttpStatusCode;
import com.codestates.stackoverflowbe.global.response.MultiResponseDto;
import com.codestates.stackoverflowbe.global.response.SingleResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;

@RestController
@RequestMapping("/v1/answer")
@Tag(name = "Answer", description = "답변 기능")
public class AnswerController {
    private final static String ANSWER_DEFAULT_URL = "/v1/answer";
    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @Operation(summary = "Create Answer API", description = "답변 생성 기능")
    @PostMapping

    public ResponseEntity<HttpStatus> postAnswer(@RequestParam long questionId,
                                                 @RequestParam String body) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AnswerDto.Response responseDto = answerService.createAnswer(questionId, body, principal);

        URI location = UriComponentsBuilder
                .newInstance()
                .path(ANSWER_DEFAULT_URL + "/{answerId}")
                .buildAndExpand(responseDto.getAnswerId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Update Answer API", description = "답변 수정 기능")
    @PatchMapping("/{answer-id}")
    public ResponseEntity<HttpStatus> patchAnswer(@PathVariable("answer-id") @Positive long answerId,
                                                  @Valid @RequestBody AnswerDto.Request requestDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        answerService.updateAnswer(answerId, requestDto, principal);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get Answers API", description = "전체 답변 조회 기능")
    @GetMapping
    public ResponseEntity<MultiResponseDto<AnswerDto.Response>> getAnswers(@Positive @RequestParam(defaultValue = "1") int page,
                                                                           @Positive @RequestParam(defaultValue = "15") int size,
                                                                           @RequestParam int questionId) {
        Page<AnswerDto.Response> pageResponseDtos = answerService.findAnswers(page - 1, size, questionId);

        return ResponseEntity.ok(MultiResponseDto.<AnswerDto.Response>builder().status(HttpStatusCode.OK.getStatusCode()).message(HttpStatusCode.OK.getMessage()).data(pageResponseDtos.getContent()).page(pageResponseDtos).build());
    }

    @Operation(summary = "Delete Answers API", description = "답변 삭제 기능")
    @DeleteMapping("/{answer-id}")
    public ResponseEntity<HttpStatus> deleteAnswer(@PathVariable("answer-id") @Positive long answerId) {
        answerService.deleteAnswer(answerId);

        return ResponseEntity.noContent().build();
    }
}
