package com.codestates.stackoverflowbe.domain.tag.repository;

import com.codestates.stackoverflowbe.domain.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
