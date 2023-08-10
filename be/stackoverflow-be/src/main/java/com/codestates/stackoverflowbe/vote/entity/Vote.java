package com.codestates.stackoverflowbe.vote.entity;

import com.codestates.stackoverflowbe.answer.entity.Answer;
import com.codestates.stackoverflowbe.audit.BaseTimeEntity;
import com.codestates.stackoverflowbe.question.entity.Question;
import com.codestates.stackoverflowbe.user.entity.User;

import javax.persistence.*;

@Entity
public class Vote extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vote_id;

    private int amount;


    @OneToOne
    private Answer answer;

    @OneToOne
    private Question question;



}
