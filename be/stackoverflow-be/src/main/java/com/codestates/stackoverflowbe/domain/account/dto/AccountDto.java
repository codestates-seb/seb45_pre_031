package com.codestates.stackoverflowbe.domain.account.dto;

import com.codestates.stackoverflowbe.domain.account.entity.Account;
import com.codestates.stackoverflowbe.domain.answer.entity.Answer;
import com.codestates.stackoverflowbe.domain.vote.entity.Vote;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


public class AccountDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post {
        private String displayName;
        private String email;
        private String password;

        public Post(String email) {
            this.email = email;
        }

        public Account toEntity() {
            return Account.builder()
                    .displayName(displayName)
                    .email(email)
                    .password(password)
                    .build();
        }
    }

    @Getter
    public static class Patch {
        private Long account_id;

        private String displayName;
        private String email;
        private String password;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private Long account_id;

        private String displayName;
        private String email;

        private List<String> roles;
        private List<Answer> answers;
        private Vote vote;
    }
}
