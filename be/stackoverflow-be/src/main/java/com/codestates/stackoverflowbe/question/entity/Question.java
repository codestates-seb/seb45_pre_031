package com.codestates.stackoverflowbe.question.entity;

import com.codestates.stackoverflowbe.audit.BaseTimeEntity;
import com.codestates.stackoverflowbe.comment.entity.Comment;
import com.codestates.stackoverflowbe.tag.entity.Tag;
import com.codestates.stackoverflowbe.user.entity.User;
import com.codestates.stackoverflowbe.vote.entity.Vote;

import javax.persistence.*;
import java.util.List;

@Entity
public class Question extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long question_id;

    private String title;
    private String body;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(mappedBy = "comment")
    private List<Comment> comments;

    @OneToMany(mappedBy = "answer")
    private List<Comment> answers;

    @OneToMany(mappedBy = "tag")
    private List<Tag> tags;

    @OneToOne()
    @JoinColumn(name = "VOTE_ID")
    private Vote vote;

}
