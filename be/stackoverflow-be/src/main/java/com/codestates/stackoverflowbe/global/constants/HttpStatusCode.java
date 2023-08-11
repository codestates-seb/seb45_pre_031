package com.codestates.stackoverflowbe.global.constants;

import lombok.Getter;

public enum HttpStatusCode {
    OK(200, "OK"),
    BAD_REQUEST(400, "BAD_REQUEST"),
    UNAUTHORIZED(401, "UNAUTHORIZED"),
    FORBIDDEN(403, "FORBIDDEN"),
    NOT_FOUND(404, "NOT_FOUND"),
    CONFLICT(409, "CONFLICT"),
    INTERNAL_SERER_ERROR(500, "INTERNAL_SERVER_ERROR"),
    NOT_IMPLEMETED(501, "NOT IMPLEMETED");

    @Getter
    private final int statusCode;
    @Getter
    private final String message;

    HttpStatusCode(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
