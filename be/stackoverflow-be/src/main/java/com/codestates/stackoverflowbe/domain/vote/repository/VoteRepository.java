package com.codestates.stackoverflowbe.domain.vote.repository;

import com.codestates.stackoverflowbe.domain.vote.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    List<Vote> findByQuestion(long questionId);
    List<Vote> findByAnswer(long answerId);
}
