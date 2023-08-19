package com.codestates.stackoverflowbe.domain.vote.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class VoteDto {
    @Getter
    public static class Request {
        private Long questionId;
        private Long answerId;
        private Boolean upVote = false;
        private Boolean downVote = false;
    }

    @Getter
    public static class Response {
        private List<String> upVotes;
        private List<String> downVotes;

        @Builder
        public Response(List<String> upVotes, List<String> downVotes) {
            this.upVotes = upVotes;
            this.downVotes = downVotes;
        }
    }
}
