package com.codestates.stackoverflowbe.domain.question.repository;

import com.codestates.stackoverflowbe.domain.account.entity.Account;
import com.codestates.stackoverflowbe.domain.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    // 사용자별 질문 조회 메서드
    List<Question> findByAccount(Account account);
    // 최신 질문 조회 메서드
    List<Question> findAllByOrderByCreatedAtDesc();
    // 질문 제목으로 검색하는 메서드
    List<Question> findByTitleContainingIgnoreCase(String title);
}
