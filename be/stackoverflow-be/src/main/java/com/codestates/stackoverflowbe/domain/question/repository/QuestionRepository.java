package com.codestates.stackoverflowbe.domain.question.repository;

import com.codestates.stackoverflowbe.domain.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    // 추가적인 쿼리 메서드나 기능을 구현할 수 있습니다.
}
