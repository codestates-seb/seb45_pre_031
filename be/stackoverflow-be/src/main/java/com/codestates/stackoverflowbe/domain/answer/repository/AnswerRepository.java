package com.codestates.stackoverflowbe.domain.answer.repository;

import com.codestates.stackoverflowbe.domain.answer.entity.Answer;
import com.codestates.stackoverflowbe.domain.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Page<Answer> findByQuestion(Question question, Pageable pageable);
}