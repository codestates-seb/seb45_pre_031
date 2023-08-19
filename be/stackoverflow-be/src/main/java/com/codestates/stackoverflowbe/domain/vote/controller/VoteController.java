package com.codestates.stackoverflowbe.domain.vote.controller;

import com.codestates.stackoverflowbe.domain.vote.dto.VoteDto;
import com.codestates.stackoverflowbe.domain.vote.service.VoteService;
import com.codestates.stackoverflowbe.global.constants.HttpStatusCode;
import com.codestates.stackoverflowbe.global.response.SingleResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/v1/vote")
@Tag(name = "Vote", description = "투표 기능")
public class VoteController {
    private final static String VOTE_DEFAULT_URL = "/v1/vote";
    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @Operation(summary = "Vote Writing API", description = "추천/비추천 투표 기능")
    @PostMapping("/voteWriting")
    public ResponseEntity<HttpStatus> voteWriting(@Valid @RequestBody VoteDto.Request requestDto) {
        voteService.voteWriting(requestDto);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get All Votes API", description = "글의 모든 투표 조회 기능")
    @GetMapping("/votesResult")
    public ResponseEntity<SingleResponseDto<VoteDto.Response>> getQuestionVotes(@Positive @RequestBody VoteDto.Request requestDto) {
        VoteDto.Response responseDto = voteService.getVotes(requestDto);

        return ResponseEntity.ok(SingleResponseDto.<VoteDto.Response>builder().status(HttpStatusCode.OK.getStatusCode()).message(HttpStatusCode.OK.getMessage()).data(responseDto).build());
    }

    @Operation(summary = "Cancel Vote API", description = "투표 취소 기능")
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAnswer(@PathVariable("vote-id") @Positive long voteId) {
        voteService.deleteVote(voteId);

        return ResponseEntity.noContent().build();
    }
}
