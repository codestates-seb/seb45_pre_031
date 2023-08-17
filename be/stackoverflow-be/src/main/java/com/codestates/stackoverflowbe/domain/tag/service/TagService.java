package com.codestates.stackoverflowbe.domain.tag.service;

import com.codestates.stackoverflowbe.domain.tag.dto.TagDto;
import com.codestates.stackoverflowbe.domain.tag.entity.Tag;
import com.codestates.stackoverflowbe.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagService {
    private TagRepository tagRepository;

    public Tag createTag(TagDto tagDto) {
        Tag savedTag = Tag.builder()
                .tagName(tagDto.getTagName())
                .build();

        return tagRepository.save(savedTag);
    }

}
