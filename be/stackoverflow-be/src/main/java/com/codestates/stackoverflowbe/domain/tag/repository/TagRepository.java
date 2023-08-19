package com.codestates.stackoverflowbe.domain.tag.repository;

import com.codestates.stackoverflowbe.domain.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByQuestion(long questionId);
}
