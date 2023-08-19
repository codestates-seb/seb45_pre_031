package com.codestates.stackoverflowbe.domain.question.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

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
    private String profileImage;
    private LocalDateTime created_at;
    private LocalDateTime modified_at;
}
