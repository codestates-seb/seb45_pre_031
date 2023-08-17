package com.codestates.stackoverflowbe.domain.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagDto {
    private String tagName;
    // ... 추가 필요한 정보들

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long tagId;
        private String name;
        // ... 추가 필요한 정보들
    }
}