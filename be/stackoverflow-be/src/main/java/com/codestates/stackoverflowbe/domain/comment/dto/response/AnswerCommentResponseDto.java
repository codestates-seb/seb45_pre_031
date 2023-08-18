package com.codestates.stackoverflowbe.domain.comment.dto.response;

import com.codestates.stackoverflowbe.domain.account.entity.Account;
import com.codestates.stackoverflowbe.domain.answer.entity.Answer;
import com.codestates.stackoverflowbe.domain.question.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerCommentResponseDto {

    private String content;

    private Account accountId;

    private Account displayName;

    private Account email;

    private Answer answerId;

    private LocalDateTime createAt;

    private LocalDateTime modifiedAt;
}
