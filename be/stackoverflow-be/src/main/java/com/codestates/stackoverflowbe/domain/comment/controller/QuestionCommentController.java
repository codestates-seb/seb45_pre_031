package com.codestates.stackoverflowbe.domain.comment.controller;

import com.codestates.stackoverflowbe.domain.comment.dto.request.QuestionCommentRequestDto;
import com.codestates.stackoverflowbe.domain.comment.dto.response.QuestionCommentResponseDto;
import com.codestates.stackoverflowbe.domain.comment.entity.QuestionComment;
import com.codestates.stackoverflowbe.domain.comment.service.QuestionCommentService;
import com.codestates.stackoverflowbe.global.constants.HttpStatusCode;
import com.codestates.stackoverflowbe.global.response.MultiResponseDto;
import com.codestates.stackoverflowbe.global.response.SingleResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.constraints.Positive;
import java.net.URI;


@Tag(name = "Question Comment API", description = "질문 댓글 기능")
@RestController
@RequestMapping("/v1/comments/question")
@RequiredArgsConstructor
public class QuestionCommentController {
    private final static String COMMENT_DEFAULT_URL = "/v1/comments/question";

    private final QuestionCommentService questionCommentService;

    @Operation(summary = "Create Question Comment API", description = "질문 댓글 저장 기능")
    @PostMapping
    public ResponseEntity<SingleResponseDto<?>> postComment(@RequestBody QuestionCommentRequestDto.Post requestCommentDto) {
        QuestionComment questionComment = questionCommentService.saveComment(requestCommentDto);

        URI location = UriComponentsBuilder
                .newInstance()
                .path(COMMENT_DEFAULT_URL + "/{commentId}")
                .buildAndExpand(questionComment.getCommentId())
                .toUri();

        return ResponseEntity.created(location)
                .body(SingleResponseDto.builder()
                        .status(HttpStatusCode.CREATED.getStatusCode())
                        .message(HttpStatusCode.CREATED.getMessage())
                        .build());
    }

    @Operation(summary = "Update Question Comment API", description = "질문 댓글 수정 기능")
    @PatchMapping("/{id}")
    public ResponseEntity<Void> patchComment(@PathVariable("id") Long id,
                                                             @RequestBody QuestionCommentRequestDto.Patch requestCommentDto) {
        questionCommentService.updateComment(id, requestCommentDto);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get Question Comments API", description = "전체 질문 댓글 조회 기능")
    @GetMapping
    public ResponseEntity<MultiResponseDto<QuestionCommentResponseDto>> getAnswers(@Positive @RequestParam(defaultValue = "1") int page,
                                                                                   @Positive @RequestParam(defaultValue = "15") int size,
                                                                                   @RequestBody QuestionCommentRequestDto.Get requestDto) {
        Page<QuestionCommentResponseDto> pageResponseDtos = questionCommentService.findcomments(page - 1, size, requestDto.getQuestionId());

        return ResponseEntity.ok(MultiResponseDto.<QuestionCommentResponseDto>builder()
                .status(HttpStatusCode.OK.getStatusCode())
                .message(HttpStatusCode.OK.getMessage())
                .data(pageResponseDtos.getContent())
                .page(pageResponseDtos)
                .build());
    }

    @Operation(summary = "Delete Question Comment API", description = "질문 댓글 삭제 기능")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable("id") Long id) {
        questionCommentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
