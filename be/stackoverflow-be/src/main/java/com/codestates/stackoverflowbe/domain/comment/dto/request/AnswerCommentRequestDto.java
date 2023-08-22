package com.codestates.stackoverflowbe.domain.comment.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AnswerCommentRequestDto {

    @Getter
    public static class Post {

        @NotBlank
        private String contents;

        @NotNull
        private Long answerId;
    }

    @Getter
    public static class Patch {
        @NotBlank
        private String contents;
    }

    @Getter
    public static class Get {
        @NotNull
        private Long answerId;
    }
}