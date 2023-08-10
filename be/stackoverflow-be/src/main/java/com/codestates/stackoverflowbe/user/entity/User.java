package com.codestates.stackoverflowbe.user.entity;

import com.codestates.stackoverflowbe.answer.entity.Answer;
import com.codestates.stackoverflowbe.audit.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    private String username;
    private String password;

    @OneToMany(mappedBy = "answer")
    private List<Answer> answers = new ArrayList<>();
}
