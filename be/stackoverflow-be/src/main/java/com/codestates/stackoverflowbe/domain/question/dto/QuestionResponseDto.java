package com.codestates.stackoverflowbe.domain.question.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class QuestionResponseDto {
    private long questionId;
    private String title;
    private String body;
    private long views;
    private String displayName;
    private List<String> voteUp;
    private List<String> voteDown;
    private List<String> tags;
    private LocalDateTime created_at;
    private LocalDateTime modified_at;
}
