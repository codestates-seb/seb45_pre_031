package com.codestates.stackoverflowbe.domain.account.entity;

import com.codestates.stackoverflowbe.domain.answer.entity.Answer;
import com.codestates.stackoverflowbe.global.audit.BaseTimeEntity;
import com.codestates.stackoverflowbe.domain.vote.entity.Vote;
import lombok.*;

import javax.persistence.*;
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
    private long account_id;

    private String displayName;
    private String email;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();


    @OneToMany(mappedBy = "account")
    private List<Answer> answers = new ArrayList<>();

    @OneToOne()
    @JoinColumn(name = "VOTE_ID")
    private Vote vote;

    public Account(long account_id, String email, String password, List<String> roles) {
        this.account_id = account_id;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
}
