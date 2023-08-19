package com.codestates.stackoverflowbe.domain.vote.repository;

import com.codestates.stackoverflowbe.domain.vote.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    List<Vote> findByQuestion_QuestionId(long questionId);
    List<Vote> findByAnswer_AnswerId(long answerId);
    Optional<Vote> findByAccount_AccountId(long accountId);
}
