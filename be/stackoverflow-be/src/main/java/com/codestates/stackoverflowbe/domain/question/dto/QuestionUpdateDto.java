package com.codestates.stackoverflowbe.domain.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionUpdateDto {
    private String title;
    private String body;
    private String user_id;
    private List<String> tags;
    private String expectContents;
}
