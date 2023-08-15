package com.codestates.stackoverflowbe.domain.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionUpdateResponseDto {
    private Long questionId;
    private String title;
    private String body;
    // 기타 필요한 정보들 추가 가능
}
