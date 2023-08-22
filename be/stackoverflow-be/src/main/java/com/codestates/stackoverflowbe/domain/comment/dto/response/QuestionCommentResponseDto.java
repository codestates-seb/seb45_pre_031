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
public class QuestionCommentResponseDto {
    private Long commentId;
    private Long accountId;
    private Long questionId;
    private String contents;

    private String displayName;
    private String email;

    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
}
