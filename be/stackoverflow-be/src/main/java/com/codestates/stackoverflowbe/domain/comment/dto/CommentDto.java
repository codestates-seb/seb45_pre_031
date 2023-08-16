package com.codestates.stackoverflowbe.domain.comment.dto;

import com.codestates.stackoverflowbe.domain.account.dto.AccountDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long commentId;
    private String content;
    private AccountDto.Response author;
    private LocalDateTime createdAt;
    // ... 추가 필요한 정보들

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long commentId;
        private String content;
        private AccountDto.Response author;
        private LocalDateTime createdAt;
        // ... 추가 필요한 정보들
    }
}



