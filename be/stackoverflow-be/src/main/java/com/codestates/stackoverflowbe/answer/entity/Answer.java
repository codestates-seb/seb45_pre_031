package com.codestates.stackoverflowbe.answer.entity;

import com.codestates.stackoverflowbe.audit.BaseTimeEntity;
import com.codestates.stackoverflowbe.comment.entity.Comment;
import com.codestates.stackoverflowbe.question.entity.Question;
import com.codestates.stackoverflowbe.user.entity.User;
import com.codestates.stackoverflowbe.vote.entity.Vote;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Answer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answer_id;
    private String body;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "QUESTION_ID")
    private Question question;

    @OneToMany(mappedBy = "comment")
    private List<Comment> comments = new ArrayList<>();

    @OneToOne()
    @JoinColumn(name = "VOTE_ID")
    private Vote vote;
}
