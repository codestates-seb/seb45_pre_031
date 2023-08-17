package com.codestates.stackoverflowbe.domain.comment.dto.request;

import com.codestates.stackoverflowbe.domain.comment.entity.Comment;
import lombok.Getter;

@Getter
public class RequestCommentDto {

    private String content;
    private Long accountId;
    private Long answerId;
    private Long questionId;

    public Comment toEntity() {
        return Comment.builder()
                .content(content)
                .build();
    }
}