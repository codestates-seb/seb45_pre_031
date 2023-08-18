package com.codestates.stackoverflowbe.domain.tag.controller;

import com.codestates.stackoverflowbe.domain.answer.dto.AnswerDto;
import com.codestates.stackoverflowbe.domain.tag.dto.TagDto;
import com.codestates.stackoverflowbe.domain.tag.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;


@RestController
@RequestMapping("/v1/tag")
@Tag(name = "Tag", description = "태그 기능")
public class TagController {
    private final static String TAG_DEFAULT_URL = "/v1/tag";
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @Operation(summary = "Create Tag API", description = "태그 생성 기능")
    @PostMapping
    public ResponseEntity<HttpStatus> postAnswer(@Valid @RequestBody TagDto requestDto) {
        com.codestates.stackoverflowbe.domain.tag.entity.Tag tag = tagService.createTag(requestDto);

        URI location = UriComponentsBuilder
                .newInstance()
                .path(TAG_DEFAULT_URL + "/{answerId}")
                .buildAndExpand(tag.getTagId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

}
