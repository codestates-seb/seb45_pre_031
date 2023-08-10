package com.codestates.stackoverflowbe.domain.question.entity;

import com.codestates.stackoverflowbe.global.audit.BaseTimeEntity;
import com.codestates.stackoverflowbe.domain.comment.entity.Comment;
import com.codestates.stackoverflowbe.domain.tag.entity.Tag;
import com.codestates.stackoverflowbe.domain.user.entity.User;
import com.codestates.stackoverflowbe.domain.vote.entity.Vote;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToMany(mappedBy = "question")
    private List<Comment> comments;

    @OneToMany(mappedBy = "question")
    private List<Comment> answers;

    @OneToMany(mappedBy = "question")
    private List<Tag> tags;

    @OneToOne()
    @JoinColumn(name = "VOTE_ID")
    private Vote vote;

}
