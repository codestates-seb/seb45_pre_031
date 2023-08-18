package com.codestates.stackoverflowbe.domain.comment.dto.request;

import lombok.Getter;

@Getter
public class AnswerCommentRequestDto {

    private String content;
    private Long accountId;
    private Long answerId;

//    public Comment toEntity() {
//        return Comment.builder()
//                .content(content)
//                .build();
//    }
}