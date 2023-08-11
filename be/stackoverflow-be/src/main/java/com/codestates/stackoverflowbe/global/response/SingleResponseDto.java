package com.codestates.stackoverflowbe.global.response;

import com.codestates.stackoverflowbe.global.constants.HttpStatusCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SingleResponseDto<T> extends ApiResponse {
    private T data;

    @Builder
    public SingleResponseDto(HttpStatusCode status, String message, T data) {
        super(status, message);
        this.data = data;
    }
}
