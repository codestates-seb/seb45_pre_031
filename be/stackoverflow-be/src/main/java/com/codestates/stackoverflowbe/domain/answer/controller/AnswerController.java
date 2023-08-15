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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;

@RestController
@RequestMapping("/v1/answer")
@Tag(name = "Answer", description = "Answer Controller")
public class AnswerController {
    private final static String ANSWER_DEFAULT_URL = "/v1/answer";
    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @Operation(summary = "Create Answer API", description = "Create Answer With Body", responses = {@ApiResponse(responseCode = "201", description = "CREATED")})
    @PostMapping
    public ResponseEntity<SingleResponseDto<AnswerDto.Response>> postAnswer(@Valid @RequestBody AnswerDto.Request requestDto) {
        AnswerDto.Response responseDto = answerService.createAnswer(requestDto);

        URI location = UriComponentsBuilder
                .newInstance()
                .path(ANSWER_DEFAULT_URL + "/{answerId}")
                .buildAndExpand(responseDto.getAnswerId())
                .toUri();

        return ResponseEntity.created(location).body(SingleResponseDto.<AnswerDto.Response>builder().status(HttpStatusCode.CREATED.getStatusCode()).message(HttpStatusCode.CREATED.getMessage()).data(responseDto).build());
    }

    @Operation(summary = "Update Answer API", description = "Update Answer With Answer Id, Body")
    @PatchMapping("/{answer-id}")
    public ResponseEntity<SingleResponseDto<AnswerDto.Response>> patchAnswer(@PathVariable("answer-id") @Positive long answerId, @Valid @RequestBody AnswerDto.Request requestDto) {
        AnswerDto.Response responseDto = answerService.updateAnswer(answerId, requestDto);

        return ResponseEntity.ok(SingleResponseDto.<AnswerDto.Response>builder().status(HttpStatusCode.OK.getStatusCode()).message(HttpStatusCode.OK.getMessage()).data(responseDto).build());
    }

    @Operation(summary = "Get Answer API", description = "Get Answer With Answer Id")
    @GetMapping("/{answer-id}")
    public ResponseEntity<SingleResponseDto<AnswerDto.Response>> getAnswer(@PathVariable("answer-id") @Positive long answerId) {
        AnswerDto.Response responseDto = answerService.findAnswer(answerId);

        return ResponseEntity.ok(SingleResponseDto.<AnswerDto.Response>builder().status(HttpStatusCode.OK.getStatusCode()).message(HttpStatusCode.OK.getMessage()).data(responseDto).build());
    }

    @Operation(summary = "Get Answers API", description = "Get Answers With Answer Id, Page, Size")
    @GetMapping
    public ResponseEntity<MultiResponseDto<AnswerDto.Response>> getAnswers(@Positive @RequestParam int page, @Positive @RequestParam int size, @Positive @RequestParam long questionId) {
//        int size = 15;
        Page<AnswerDto.Response> pageResponseDtos = answerService.findAnswers(page - 1, size, questionId);

        return ResponseEntity.ok(MultiResponseDto.<AnswerDto.Response>builder().status(HttpStatusCode.OK.getStatusCode()).message(HttpStatusCode.OK.getMessage()).data(pageResponseDtos.getContent()).page(pageResponseDtos).build());
    }

    @Operation(summary = "Delete Answers API", description = "Delete Answers With Answer Id")
    @DeleteMapping
    public ResponseEntity deleteAnswer(@PathVariable("answer-id") @Positive long answerId) {
        answerService.deleteAnswer(answerId);

        return ResponseEntity.noContent().build();
    }
}
