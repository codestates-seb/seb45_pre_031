package com.codestates.stackoverflowbe.domain.comment.controller;

import com.codestates.stackoverflowbe.domain.comment.dto.request.AnswerCommentRequestDto;
import com.codestates.stackoverflowbe.domain.comment.dto.request.QuestionCommentRequestDto;
import com.codestates.stackoverflowbe.domain.comment.dto.response.AnswerCommentResponseDto;
import com.codestates.stackoverflowbe.domain.comment.dto.response.QuestionCommentResponseDto;
import com.codestates.stackoverflowbe.domain.comment.entity.AnswerComment;
import com.codestates.stackoverflowbe.domain.comment.entity.QuestionComment;
import com.codestates.stackoverflowbe.domain.comment.service.AnswerCommentService;
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


@Tag(name = "Answer Comment API", description = "답변 댓글 기능")
@RestController
@RequestMapping("/v1/comments/answer")
@RequiredArgsConstructor
public class AnswerCommentController {
    private final static String COMMENT_DEFAULT_URL = "/v1/comments/answer";

    private final AnswerCommentService answerCommentService;

    @Operation(summary = "Create Answer Comment API", description = "답변 댓글 저장 기능")
    @PostMapping
    public ResponseEntity<SingleResponseDto<?>> postComment(@RequestBody AnswerCommentRequestDto.Post requestCommentDto) {
        AnswerComment answerComment = answerCommentService.saveComment(requestCommentDto);

        URI location = UriComponentsBuilder
                .newInstance()
                .path(COMMENT_DEFAULT_URL + "/{commentId}")
                .buildAndExpand(answerComment.getCommentId())
                .toUri();

        return ResponseEntity.created(location)
                .body(SingleResponseDto.builder()
                        .status(HttpStatusCode.CREATED.getStatusCode())
                        .message(HttpStatusCode.CREATED.getMessage())
                        .build());
    }

    @Operation(summary = "Update Answer Comment API", description = "답변 댓글 수정 기능")
    @PatchMapping("/{id}")
    public ResponseEntity<Void> patchComment(@PathVariable("id") Long id,
                                                             @RequestBody AnswerCommentRequestDto.Patch requestCommentDto) {
        answerCommentService.updateComment(id, requestCommentDto);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get Answer Comments API", description = "전체 답변 댓글 조회 기능")
    @GetMapping
    public ResponseEntity<MultiResponseDto<AnswerCommentResponseDto>> getAnswers(@Positive @RequestParam(defaultValue = "1") int page,
                                                                                 @Positive @RequestParam(defaultValue = "15") int size,
                                                                                 @RequestBody AnswerCommentRequestDto.Get requestDto) {
        Page<AnswerCommentResponseDto> pageResponseDtos = answerCommentService.findcomments(page - 1, size, requestDto.getAnswerId());

        return ResponseEntity.ok(MultiResponseDto.<AnswerCommentResponseDto>builder()
                .status(HttpStatusCode.OK.getStatusCode())
                .message(HttpStatusCode.OK.getMessage())
                .data(pageResponseDtos.getContent())
                .page(pageResponseDtos)
                .build());
    }

    @Operation(summary = "Delete Answer Comment API", description = "답변 댓글 삭제 기능")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable("id") Long id) {
        answerCommentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
