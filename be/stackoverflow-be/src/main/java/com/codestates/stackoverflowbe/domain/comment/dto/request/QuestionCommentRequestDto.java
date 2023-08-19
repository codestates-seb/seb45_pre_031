package com.codestates.stackoverflowbe.domain.comment.dto.request;

import com.codestates.stackoverflowbe.domain.account.entity.Account;
import com.codestates.stackoverflowbe.domain.comment.entity.QuestionComment;
import lombok.Getter;


public class QuestionCommentRequestDto {
    @Getter
    public static class Post {
        private String contents;
        private Long accountId;
        private Long questionId;
    }

    @Getter
    public static class Patch {
        private String contents;
    }

    @Getter
    public static class Get {
        private long questionId;
    }
}