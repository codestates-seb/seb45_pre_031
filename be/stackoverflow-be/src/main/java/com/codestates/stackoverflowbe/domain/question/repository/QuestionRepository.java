package com.codestates.stackoverflowbe.domain.question.repository;

import com.codestates.stackoverflowbe.domain.account.entity.Account;
import com.codestates.stackoverflowbe.domain.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    // 사용자별 질문 조회 메서드
    List<Question> findByAccount(Account account);

    Page<Question> findAllByOrderByCreatedAtDesc(Pageable pageable); // Newest
    Page<Question> findAllByOrderByViewCountDesc(Pageable pageable); // Hot
    Page<Question> findByCreatedAtAfterOrderByViewCountDesc(LocalDateTime createdAt, Pageable pageable); // Week, Month
}
