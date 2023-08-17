package com.codestates.stackoverflowbe.domain.question.entity;

import com.codestates.stackoverflowbe.domain.question.dto.QuestionUpdateRequestDto;
import com.codestates.stackoverflowbe.global.audit.BaseTimeEntity;
import com.codestates.stackoverflowbe.domain.comment.entity.Comment;
import com.codestates.stackoverflowbe.domain.tag.entity.Tag;
import com.codestates.stackoverflowbe.domain.account.entity.Account;
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
    // JPA 어노테이션을 사용한 질문 엔티티 클래스

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long question_id;

    private String title;
    private String body;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

    @OneToMany(mappedBy = "question")
    private List<Comment> comments;

    @OneToMany(mappedBy = "question")
    private List<Comment> answers;

    @OneToMany(mappedBy = "question")
    private List<Tag> tags;

    @OneToOne()
    @JoinColumn(name = "VOTE_ID")
    private Vote vote;

    // 추가한 필드 및 메서드
    private String user_id;
    private Long viewCount;

    @Column(columnDefinition = "TEXT")
    private String bodyHTML;

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public void setBodyHTML(String bodyHTML) {
        this.bodyHTML = bodyHTML;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public void setAccount(Account account) {
        this.account = account;
    }
    // 질문을 업데이트하는 메서드
    public void updateQuestion(QuestionUpdateRequestDto updateDto) {
        this.setTitle(updateDto.getTitle());
        this.setBody(updateDto.getBody());
    }

}