package com.codestates.stackoverflowbe.domain.comment.dto.request;

import com.codestates.stackoverflowbe.domain.account.entity.Account;
import com.codestates.stackoverflowbe.domain.comment.entity.QuestionComment;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class QuestionCommentRequestDto {
    @Getter
    public static class Post {

        @NotBlank
        private String contents;

        @NotNull
        private Long accountId;

        @NotNull
        private Long questionId;
    }

    @Getter
    public static class Patch {
        @NotBlank
        private String contents;
    }

    @Getter
    public static class Get {
        @NotNull
        private Long questionId;
    }
}