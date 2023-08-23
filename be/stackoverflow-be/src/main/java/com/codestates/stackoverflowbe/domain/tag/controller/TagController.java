package com.codestates.stackoverflowbe.domain.tag.controller;

import com.codestates.stackoverflowbe.domain.tag.dto.TagDto;
import com.codestates.stackoverflowbe.domain.tag.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

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
    public ResponseEntity<HttpStatus> postTag(@Valid @RequestBody TagDto.Request requestDto) {
        TagDto.Response responseDto = tagService.createTag(requestDto);

        URI location = UriComponentsBuilder
                .newInstance()
                .path(TAG_DEFAULT_URL + "/{tagId}")
                .buildAndExpand(responseDto.getTagId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Get All Tags API", description = "질문의 모든 태그 조회 기능")
    @GetMapping
    public ResponseEntity<List<TagDto.Response>> getTags(@Valid @RequestBody TagDto.Request requestDto) {
        List<TagDto.Response> responseDtos = tagService.findTags(requestDto);

        return ResponseEntity.ok(responseDtos);
    }

}
