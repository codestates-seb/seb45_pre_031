package com.codestates.stackoverflowbe.domain.question.dto;

import com.codestates.stackoverflowbe.global.constants.HttpStatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class QuestionListResponseDto<T> {
    private int statusCode;
    private String message;
    private List<T> data;

    public QuestionListResponseDto(HttpStatusCode httpStatusCode, String message, List<T> data) {
        this.statusCode = httpStatusCode.getStatusCode(); // 열거형의 상태 코드 값을 직접 사용
        this.message = message;
        this.data = data;
    }
}