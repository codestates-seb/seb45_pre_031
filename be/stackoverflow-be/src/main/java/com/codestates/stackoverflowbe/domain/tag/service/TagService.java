package com.codestates.stackoverflowbe.domain.tag.service;

import com.codestates.stackoverflowbe.domain.question.entity.Question;
import com.codestates.stackoverflowbe.domain.question.repository.QuestionRepository;
import com.codestates.stackoverflowbe.domain.tag.dto.TagDto;
import com.codestates.stackoverflowbe.domain.tag.entity.Tag;
import com.codestates.stackoverflowbe.domain.tag.repository.TagRepository;
import com.codestates.stackoverflowbe.global.exception.BusinessLogicException;
import com.codestates.stackoverflowbe.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final QuestionRepository questionRepository;

    public TagDto.Response createTag(TagDto.Request requestDto) {
        Tag savedTag = tagRepository.save(Tag.builder()
                .tagName(requestDto.getTagName())
                .build());

        Question findQuestion = questionRepository.findById(requestDto.getQuestionId()).orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
        findQuestion.addTag(savedTag);

        return TagDto.Response.builder()
                .tagId(savedTag.getTagId())
                .build();
    }

    public List<TagDto.Response> findTags(TagDto.Request requestDto) {
        Question findQuestion = questionRepository.findById(requestDto.getQuestionId()).orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));

        List<Tag> tags = findQuestion.getTags();

        return tags.stream()
                .map(tag -> TagDto.Response.builder()
                        .tagId(tag.getTagId())
                        .tagName(tag.getTagName())
                        .build())
                .collect(Collectors.toList());
    }
}
