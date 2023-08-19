package com.codestates.stackoverflowbe.domain.tag.dto;

import lombok.*;

@Getter
public class TagDto {
    @Getter
    public static class Request {
        private Long questionId;
        private String tagName;
    }

    @Getter
    public static class Response {
        private Long tagId;
        private String tagName;

        @Builder
        public Response(Long tagId, String tagName) {
            this.tagId = tagId;
            this.tagName = tagName;
        }
    }
}