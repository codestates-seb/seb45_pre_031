package com.codestates.stackoverflowbe.domain.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionUpdateDto {
    // 질문 데이터를 나타내는 DTO (데이터 전송 객체)
    private String title;
    private String body;
}
