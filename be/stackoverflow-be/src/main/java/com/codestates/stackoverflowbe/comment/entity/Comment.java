package com.codestates.stackoverflowbe.comment.entity;

import com.codestates.stackoverflowbe.answer.entity.Answer;
import com.codestates.stackoverflowbe.question.entity.Question;
import com.codestates.stackoverflowbe.user.entity.User;

import javax.persistence.*;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comment_id;
    private String body;


    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "QUESTION_ID")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "ANSWER_ID")
    private Answer answer;

}
