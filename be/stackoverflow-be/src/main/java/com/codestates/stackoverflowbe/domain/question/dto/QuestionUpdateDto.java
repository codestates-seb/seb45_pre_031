package com.codestates.stackoverflowbe.domain.question.dto;

import com.codestates.stackoverflowbe.domain.tag.entity.Tag;
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
}
