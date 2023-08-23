package com.codestates.stackoverflowbe.global.exception;

import lombok.Getter;

public enum ExceptionCode {

    MEMBER_NOT_FOUND(404, "Member not found"),
    MEMBER_NOT_ALLOW(405, "That Member doesn't have authority"),
    MEMBER_EXISTS(409, "Member exists"),
    QUESTION_NOT_FOUND(404, "Question not found"),
    QUESTION_IS_NOT(405, "That answer isn't answer the question."),
    QUESTION_EXISTS(409, "Question exists"),
    ANSWER_NOT_FOUND(404, "Answer not found"),
    ANSWER_EXISTS(409, "Answer exists"),
    VOTE_NOT_FOUND(404, "Vote not found"),
    VOTE_NOT_ALLOW(409, "Vote not allowed"),
    COMMENT_NOT_FOUND(404, "Comment not found"),
    COMMENT_NOT_ALLOW(405, "Comment not allowed"),
    COMMENT_EXITS(409, "Comment exits"),
    TAG_NOT_FOUND(404, "Tag not found"),
    TAG_EXISTS(409, "Tag exists"),
    EMAIL_NOT_FOUND(404, "Email not found"),
    EMAIL_EXISTS(404, "Email exists"),
    NOT_AUTHENTICATED(401, "Not authenticated");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
