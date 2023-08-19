package com.codestates.stackoverflowbe.domain.answer.dto;

import com.codestates.stackoverflowbe.domain.answer.entity.Answer;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
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
        private String displayName;
        private List<String> voteUp;
        private List<String> voteDown;
        LocalDateTime created_at;
        LocalDateTime modified_at;

        @Builder
        public Response(long answerId, String body, String displayName, List<String> voteUp, List<String> voteDown, LocalDateTime created_at, LocalDateTime modified_at) {
            this.answerId = answerId;
            this.body = body;
            this.displayName = displayName;
            this.voteUp = voteUp;
            this.voteDown = voteDown;
            this.created_at = created_at;
            this.modified_at = modified_at;
        }
    }
}
