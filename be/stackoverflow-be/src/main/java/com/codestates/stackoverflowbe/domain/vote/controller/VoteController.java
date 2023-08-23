package com.codestates.stackoverflowbe.domain.vote.controller;

import com.codestates.stackoverflowbe.domain.account.entity.Account;
import com.codestates.stackoverflowbe.domain.vote.dto.VoteDto;
import com.codestates.stackoverflowbe.domain.vote.service.VoteService;
import com.codestates.stackoverflowbe.global.constants.HttpStatusCode;
import com.codestates.stackoverflowbe.global.response.SingleResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        voteService.voteWriting(requestDto, principal);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get Question Votes API", description = "질문의 모든 투표 조회 기능")
    @GetMapping("/voteQuestionResult")
    public ResponseEntity<SingleResponseDto<VoteDto.Response>> getQuestionVotes(@Positive @RequestParam long questionId) {
        VoteDto.Response responseDto = voteService.getQuestionVotes(questionId);

        return ResponseEntity.ok(SingleResponseDto.<VoteDto.Response>builder().status(HttpStatusCode.OK.getStatusCode()).message(HttpStatusCode.OK.getMessage()).data(responseDto).build());
    }

    @Operation(summary = "Get Answer Votes API", description = "답변의 모든 투표 조회 기능")
    @GetMapping("/voteAnswerResult")
    public ResponseEntity<SingleResponseDto<VoteDto.Response>> getAnswerVotes(@Positive @RequestParam long answerId) {
        VoteDto.Response responseDto = voteService.getAnswerVotes(answerId);

        return ResponseEntity.ok(SingleResponseDto.<VoteDto.Response>builder().status(HttpStatusCode.OK.getStatusCode()).message(HttpStatusCode.OK.getMessage()).data(responseDto).build());
    }

    @Operation(summary = "Cancel Vote API", description = "투표 취소 기능")
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAnswer(@PathVariable("vote-id") @Positive long voteId) {
        voteService.deleteVote(voteId);

        return ResponseEntity.noContent().build();
    }
}
