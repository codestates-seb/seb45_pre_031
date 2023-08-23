package com.codestates.stackoverflowbe.domain.comment.repository;

import com.codestates.stackoverflowbe.domain.comment.entity.QuestionComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionCommentRepository extends JpaRepository<QuestionComment, Long> {

    Page<QuestionComment> findByQuestion_QuestionId(Long questionId, Pageable pageable);
}
