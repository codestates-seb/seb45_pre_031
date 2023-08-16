package com.codestates.stackoverflowbe.domain.vote.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class VoteDto {
    @Getter
    public static class QuestionRequest {
        private long accountId;
        private long questionId;
        private boolean upVote;
        private boolean downVote;
    }

    @Getter
    public static class AnswerRequest {
        private long accountId;
        private long answerId;
        private boolean upVote;
        private boolean downVote;
    }

    @Getter
    public static class Response {
        private long voteId;
        private boolean upVote;
        private boolean downVote;

        @Builder
        public Response(long voteId, boolean upVote, boolean downVote) {
            this.voteId = voteId;
            this.upVote = upVote;
            this.downVote = downVote;
        }
    }

    @Getter
    public static class QuestionResponse {
        private long questionId;
        private List<String> upVotes;
        private List<String> downVotes;

        @Builder
        public QuestionResponse(long questionId, List<String> upVotes, List<String> downVotes) {
            this.questionId = questionId;
            this.upVotes = upVotes;
            this.downVotes = downVotes;
        }
    }

    @Getter
    public static class AnswerResponse {
        private long answerId;
        private List<String> upVotes;
        private List<String> downVotes;

        @Builder
        public AnswerResponse(long answerId, List<String> upVotes, List<String> downVotes) {
            this.answerId = answerId;
            this.upVotes = upVotes;
            this.downVotes = downVotes;
        }
    }
}
