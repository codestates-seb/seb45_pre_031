package com.codestates.stackoverflowbe.domain.tag.entity;

import com.codestates.stackoverflowbe.domain.question.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    @Column
    private String tagName;

    @ManyToOne
    @JoinColumn(name = "QUESTION_ID")
    private Question question;

    public Tag(String tagName) {
        this.tagName = tagName;
    }
}
