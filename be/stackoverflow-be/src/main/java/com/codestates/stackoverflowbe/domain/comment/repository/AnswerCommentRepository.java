package com.codestates.stackoverflowbe.domain.comment.repository;

import com.codestates.stackoverflowbe.domain.comment.entity.AnswerComment;
import com.codestates.stackoverflowbe.domain.comment.entity.QuestionComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerCommentRepository extends JpaRepository<AnswerComment, Long> {
    Page<AnswerComment> findByAnswer_AnswerId(Long answerId, Pageable pageable);

}
