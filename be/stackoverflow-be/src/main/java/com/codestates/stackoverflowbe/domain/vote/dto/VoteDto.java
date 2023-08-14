package com.codestates.stackoverflowbe.domain.vote.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoteDto {
    private Long voteId;
    private Integer voteValue;
    // ... 추가 필요한 정보들

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long voteId;
        private Integer voteValue;
        // ... 추가 필요한 정보들
    }
}