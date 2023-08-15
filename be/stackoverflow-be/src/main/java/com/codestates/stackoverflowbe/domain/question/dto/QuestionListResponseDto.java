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
    private int statusCode; // 응답의 상태 코드
    private String message; // 응답 메시지
    private List<T> data;   // 질문 목록 데이터

    // 생성자에서 HttpStatusCode를 받아서 사용합니다.
    public QuestionListResponseDto(HttpStatusCode httpStatusCode, String message, List<T> data) {
        this.statusCode = httpStatusCode.getStatusCode(); // 열거형의 상태 코드 값을 직접 사용
        this.message = message;
        this.data = data;
    }
}