package com.codestates.stackoverflowbe.domain.answer.dto;

import com.codestates.stackoverflowbe.domain.answer.entity.Answer;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
public class AnswerDto {
    @Getter
    public static class Request {
        private Long questionId;

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
        private List<String> upVotes;
        private List<String> downVotes;

        @Builder
        public Response(long answerId, String body, List<String> upVotes, List<String> downVotes) {
            this.answerId = answerId;
            this.body = body;
            this.upVotes = upVotes;
            this.downVotes = downVotes;
        }
    }
}
