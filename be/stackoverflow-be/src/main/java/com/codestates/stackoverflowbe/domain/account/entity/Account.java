package com.codestates.stackoverflowbe.domain.account.entity;

import com.codestates.stackoverflowbe.domain.account.dto.AccountDto;
import com.codestates.stackoverflowbe.domain.account.dto.AccountPageResponseDto;
import com.codestates.stackoverflowbe.domain.answer.entity.Answer;
import com.codestates.stackoverflowbe.domain.question.entity.Question;
import com.codestates.stackoverflowbe.global.audit.BaseTimeEntity;
import com.codestates.stackoverflowbe.domain.vote.entity.Vote;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Account extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accountId;

    @NotNull
    private String displayName;

    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
    private String password;

//    @JsonIgnore // 직렬화 및 역직렬화 과정상 이 필드 무시
    @OneToMany(mappedBy = "account")
    private List<Question> questions = new ArrayList<>();

//    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private List<Answer> answers = new ArrayList<>();

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

//    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private List<Vote> votes = new ArrayList<>();

    @JsonIgnore
    private String location;

    @JsonIgnore
    private String title;

    private String aboutMe;

    private String websiteLink;

    private String twitterLink;

    private String gitHubLink;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();


    public Account(long accountId, String email, String password, List<String> roles) {
        this.accountId = accountId;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public Account(String email) {
        this.email = email;
    }

    public Account(String displayName, String email, String password, List<String> roles) {
        this.displayName = displayName;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public void update(String displayName, String location, String title,
                       String aboutMe, String websiteLink, String twitterLink, String gitHubLink) {
        this.displayName = displayName;
        this.location = location;
        this.title = title;
        this.aboutMe = aboutMe;
        this.websiteLink = websiteLink;
        this.twitterLink = twitterLink;
        this.gitHubLink = gitHubLink;
    }

    public AccountDto.Response toResponse() {
        return AccountDto.Response.builder()
                .accountId(this.getAccountId())
                .displayName(this.getDisplayName())
                .email(this.getEmail())
                .build();
    }

    public AccountPageResponseDto toPageResponseDto() {
        return AccountPageResponseDto.builder()
                .accountId(this.accountId)
                .displayName(this.displayName)
                .email(this.email)
                .password(this.email)
                .questions(this.questions)
                .answers(this.answers)
                .votes(this.votes)
                .location(this.location)
                .title(this.title)
                .aboutMe(this.aboutMe)
                .websiteLink(this.websiteLink)
                .twitterLink(this.twitterLink)
                .gitHubLink(this.gitHubLink)
                .roles(this.roles)
                .build();
    }
}
