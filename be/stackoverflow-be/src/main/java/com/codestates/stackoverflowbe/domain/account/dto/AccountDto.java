package com.codestates.stackoverflowbe.domain.account.dto;

import com.codestates.stackoverflowbe.domain.account.entity.Account;
import com.codestates.stackoverflowbe.domain.answer.entity.Answer;
import com.codestates.stackoverflowbe.domain.vote.entity.Vote;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


public class AccountDto {

    @Getter
    @Setter
    @AllArgsConstructor
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
//        private Long accountId;
        private String displayName;
        private String location;
        private String title;
        private String aboutMe;
        private String websiteLink;
        private String twitterLink;
        private String githubLink;

        public Account toEntity() {
            return Account.builder()
                    .displayName(displayName)
                    .location(location)
                    .title(title)
                    .aboutMe(aboutMe)
                    .websiteLink(websiteLink)
                    .twitterLink(twitterLink)
                    .gitHubLink(githubLink)
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Response {
        private Long account_id;

        private String displayName;
        private String email;

//        private List<String> roles;
//        private List<Answer> answers;
//        private Vote vote;
    }
}
