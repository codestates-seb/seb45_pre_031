package com.codestates.stackoverflowbe.answer.entity;

import com.codestates.stackoverflowbe.audit.BaseTimeEntity;
import com.codestates.stackoverflowbe.user.entity.User;

import javax.persistence.*;

@Entity
public class Answer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answer_id;
    private String body;




    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user_id;
    private Question question_id;
}
