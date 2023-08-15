package com.codestates.stackoverflowbe.domain.vote.entity;

import com.codestates.stackoverflowbe.domain.answer.entity.Answer;
import com.codestates.stackoverflowbe.global.audit.BaseTimeEntity;
import com.codestates.stackoverflowbe.domain.question.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteId;

    @Column
    private int amount;

    @OneToOne(mappedBy = "vote")
    private Answer answer;

    @OneToOne(mappedBy = "vote", cascade = CascadeType.ALL)
    private Question question;

    public void setAnswer(Answer answer) {
        this.answer = answer;
        if (answer.getVote() != this) answer.setVote(this);
    }
}
