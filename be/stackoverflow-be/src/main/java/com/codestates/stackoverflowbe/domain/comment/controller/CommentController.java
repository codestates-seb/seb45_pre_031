package com.codestates.stackoverflowbe.domain.comment.controller;

import com.codestates.stackoverflowbe.domain.comment.dto.request.RequestCommentDto;
import com.codestates.stackoverflowbe.domain.comment.dto.response.ResponseCommentDto;
import com.codestates.stackoverflowbe.domain.comment.service.CommentService;
import com.codestates.stackoverflowbe.global.constants.HttpStatusCode;
import com.codestates.stackoverflowbe.global.response.SingleResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@Tag(name = "Comment", description = "댓글 기능")
@RestController
@RequestMapping("/v1/comments")
@RequiredArgsConstructor
public class CommentController {
    private final static String COMMENT_DEFAULT_URL = "/v1/comments";

    private final CommentService commentService;

    @Operation(summary = "Post Comment", description = "댓글 저장 기능")
    @PostMapping
    public ResponseEntity<SingleResponseDto<?>> postComment(@RequestBody RequestCommentDto requestCommentDto) {
        ResponseCommentDto responseCommentDto = commentService.saveComment(requestCommentDto);

        URI location = UriComponentsBuilder
                .newInstance()
                .path(COMMENT_DEFAULT_URL + "/{commentId}")
                .buildAndExpand(responseCommentDto.getCommentId())
                .toUri();


        return ResponseEntity.created(location)
                .body(SingleResponseDto.builder()
                        .status(HttpStatusCode.CREATED.getStatusCode())
                        .message(HttpStatusCode.CREATED.getMessage())
                        .data(responseCommentDto)
                        .build());
    }

    @Operation(summary = "Patch Comment", description = "댓글 수정 기능")
    @PatchMapping("/{id}")
    public ResponseEntity<SingleResponseDto<?>> patchComment(@PathVariable("id") Long id, @RequestBody RequestCommentDto requestCommentDto) {
        ResponseCommentDto responseCommentDto = commentService.updateComment(id, requestCommentDto);

        return ResponseEntity.ok(SingleResponseDto.builder()
                .status(HttpStatusCode.OK.getStatusCode())
                .message(HttpStatusCode.OK.getMessage())
                .data(responseCommentDto)
                .build());
    }

    @Operation(summary = "Delete Comment", description = "댓글 삭제 기능")
    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable("id") Long id) {
        commentService.deleteComment(id);
    }
}
