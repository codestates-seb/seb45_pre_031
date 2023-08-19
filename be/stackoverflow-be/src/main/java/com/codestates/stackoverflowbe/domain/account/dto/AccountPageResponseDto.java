package com.codestates.stackoverflowbe.domain.account.dto;

import com.codestates.stackoverflowbe.domain.answer.entity.Answer;
import com.codestates.stackoverflowbe.domain.question.entity.Question;
import com.codestates.stackoverflowbe.domain.vote.entity.Vote;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class AccountPageResponseDto {

    private long accountId;

    private String displayName;

    private String email;

    private String password;

    //    @JsonIgnore // 직렬화 및 역직렬화 과정상 이 필드 무시
    private List<Question> questions = new ArrayList<>();

    //    @JsonIgnore
    private List<Answer> answers = new ArrayList<>();

    //    @JsonIgnore
    private List<Vote> votes;

    private String location;

    private String title;

    private String aboutMe;

    private String websiteLink;

    private String twitterLink;

    private String gitHubLink;

    private List<String> roles = new ArrayList<>();
}
