package com.codestates.stackoverflowbe.domain.answer.controller;

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
    public ResponseEntity<HttpStatus> postAnswer(@Valid @RequestBody AnswerDto.Request requestDto) {
        AnswerDto.Response responseDto = answerService.createAnswer(requestDto);

        URI location = UriComponentsBuilder
                .newInstance()
                .path(ANSWER_DEFAULT_URL + "/{answerId}")
                .buildAndExpand(responseDto.getAnswerId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Update Answer API", description = "답변 수정 기능")
    @PatchMapping("/{answer-id}")
    public ResponseEntity<HttpStatus> patchAnswer(@PathVariable("answer-id") @Positive long answerId, @Valid @RequestBody AnswerDto.Request requestDto) {
        AnswerDto.Response responseDto = answerService.updateAnswer(answerId, requestDto);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get Answer API", description = "답변 조회 기능")
    @GetMapping("/{answer-id}")
    public ResponseEntity<SingleResponseDto<AnswerDto.Response>> getAnswer(@PathVariable("answer-id") @Positive long answerId) {
        AnswerDto.Response responseDto = answerService.findAnswer(answerId);

        return ResponseEntity.ok(SingleResponseDto.<AnswerDto.Response>builder().status(HttpStatusCode.OK.getStatusCode()).message(HttpStatusCode.OK.getMessage()).data(responseDto).build());
    }

    @Operation(summary = "Get Answers API", description = "전체 답변 조회 기능")
    @GetMapping
    public ResponseEntity<MultiResponseDto<AnswerDto.Response>> getAnswers(@Positive @RequestParam int page, @Positive @RequestParam int size, @Positive @RequestParam long questionId) {
//        int size = 15;
        Page<AnswerDto.Response> pageResponseDtos = answerService.findAnswers(page - 1, size, questionId);

        return ResponseEntity.ok(MultiResponseDto.<AnswerDto.Response>builder().status(HttpStatusCode.OK.getStatusCode()).message(HttpStatusCode.OK.getMessage()).data(pageResponseDtos.getContent()).page(pageResponseDtos).build());
    }

    @Operation(summary = "Delete Answers API", description = "답변 삭제 기능")
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAnswer(@PathVariable("answer-id") @Positive long answerId) {
        answerService.deleteAnswer(answerId);

        return ResponseEntity.noContent().build();
    }
}
