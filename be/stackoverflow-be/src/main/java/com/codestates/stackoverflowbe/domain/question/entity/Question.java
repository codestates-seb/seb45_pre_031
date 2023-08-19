package com.codestates.stackoverflowbe.domain.question.entity;

import com.codestates.stackoverflowbe.domain.account.entity.Account;
import com.codestates.stackoverflowbe.domain.answer.entity.Answer;
import com.codestates.stackoverflowbe.domain.comment.entity.QuestionComment;
import com.codestates.stackoverflowbe.domain.question.dto.QuestionUpdateRequestDto;
import com.codestates.stackoverflowbe.domain.tag.entity.Tag;
import com.codestates.stackoverflowbe.domain.vote.entity.Vote;
import com.codestates.stackoverflowbe.global.audit.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Question extends BaseTimeEntity {
    // JPA 어노테이션을 사용한 질문 엔티티 클래스

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @Column
    private String title;

    @Column
    private String body;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

    @OneToMany(mappedBy = "question")
    private List<QuestionComment> questionComments = new ArrayList<>();

    @OneToMany(mappedBy = "question")
    private List<Answer> answers = new ArrayList<>();



    @OneToMany(mappedBy = "question")
    private List<Tag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "question")
    private List<Vote> votes = new ArrayList<>();

    // 추가한 필드 및 메서드

    @Column
    private Long viewCount;

    @Column(columnDefinition = "TEXT")
    private String expectContents;

    public void setExpectContents(String expectContents) {
        this.expectContents = expectContents;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setBody(String body) {
        this.body = body;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }
    public void addVote(Vote vote) {
        votes.add(vote);
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    // 질문을 업데이트하는 메서드
    public void updateQuestion(QuestionUpdateRequestDto updateDto) {
        this.setTitle(updateDto.getTitle());
        this.setBody(updateDto.getBody());
    }
}