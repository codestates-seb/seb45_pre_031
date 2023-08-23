package com.codestates.stackoverflowbe.domain.comment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerCommentResponseDto {

    private Long commentId;

    private String contents;

    private Long accountId;

    private String displayName;

    private String email;

    private Long answerId;

    private LocalDateTime createAt;

    private LocalDateTime modifiedAt;
}