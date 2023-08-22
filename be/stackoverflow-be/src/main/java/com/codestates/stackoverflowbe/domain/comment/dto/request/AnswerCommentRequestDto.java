package com.codestates.stackoverflowbe.domain.comment.dto.request;

import com.codestates.stackoverflowbe.domain.account.entity.Account;
import com.codestates.stackoverflowbe.domain.answer.entity.Answer;
import com.codestates.stackoverflowbe.domain.comment.entity.AnswerComment;
import lombok.Builder;
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

//        public AnswerComment toEntity() {
//            return AnswerComment.builder()
//                    .contents(contents)
//                    .build();
//        }
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