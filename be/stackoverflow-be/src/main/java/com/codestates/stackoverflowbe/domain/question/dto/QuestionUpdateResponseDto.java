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
    private String title;
    private String user_id;
    private String[] tags;
    private String bodyHTML;
    // 기타 필요한 정보들 추가 가능
}
