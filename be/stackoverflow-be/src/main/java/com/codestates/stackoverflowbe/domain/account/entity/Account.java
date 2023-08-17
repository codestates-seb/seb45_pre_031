package com.codestates.stackoverflowbe.domain.account.entity;

import com.codestates.stackoverflowbe.domain.answer.entity.Answer;
import com.codestates.stackoverflowbe.global.audit.BaseTimeEntity;
import com.codestates.stackoverflowbe.domain.vote.entity.Vote;
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

    @Column
    private String displayName;

    @Column
    private String email;

    @Column
    @NotNull
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    @OneToMany(mappedBy = "account")
    private List<Answer> answers = new ArrayList<>();

    @OneToMany(mappedBy = "account")
    private List<Vote> votes = new ArrayList<>();
    public Account(long accountId, String email, String password, List<String> roles) {
        this.accountId = accountId;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public Account(String email) {
        this.email = email;
    }
}
