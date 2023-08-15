package com.codestates.stackoverflowbe.domain.answer.dto;

import com.codestates.stackoverflowbe.domain.answer.entity.Answer;
import com.codestates.stackoverflowbe.domain.vote.entity.Vote;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class AnswerDto {
    @Getter
    public static class Request {
        @NotBlank(message = "답변은 공백이 아니어야 합니다.")
        private String body;

        public Answer toEntity() {
            return Answer.builder()
                    .body(body)
                    .build();
        }
    }

    @Getter
    public static class Response {
        private long answerId;
        private String body;
        private Vote vote;

        @Builder
        public Response(long answerId, String body, Vote vote) {
            this.answerId = answerId;
            this.body = body;
            this.vote = vote;
        }

        // why????????? 위 response에서 vote가 vote.amount로 나옴
        public int getVote() {
            return vote.getAmount();
        }
    }
}
