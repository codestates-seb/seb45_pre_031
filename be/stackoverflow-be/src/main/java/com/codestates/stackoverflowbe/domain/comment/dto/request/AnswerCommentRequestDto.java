package com.codestates.stackoverflowbe.domain.comment.dto.request;

import lombok.Getter;

@Getter
public class AnswerCommentRequestDto {

    @Getter
    public static class Post {
        private String contents;
        private Long accountId;
        private Long answerId;
    }

    @Getter
    public static class Patch {
        private String contents;
    }

    @Getter
    public static class Get {
        private Long answerId;
    }
}