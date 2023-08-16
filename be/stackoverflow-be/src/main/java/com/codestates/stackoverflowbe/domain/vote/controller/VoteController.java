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
@Tag(name = "Vote", description = "Vote Controller")
public class VoteController {
    private final static String VOTE_DEFAULT_URL = "/v1/vote";
    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @Operation(summary = "Vote Question API", description = "Vote Question With Question Id")
    @PostMapping("/voteQuestion")
    public ResponseEntity<HttpStatus> voteQuestion(@Valid @RequestBody VoteDto.QuestionRequest requestDto) {
        voteService.voteQuestion(requestDto);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Vote Answer API", description = "Vote Answer With Answer Id")
    @PostMapping("/voteAnswer")
    public ResponseEntity<HttpStatus> voteAnswer(@Valid @RequestBody VoteDto.AnswerRequest requestDto) {
        voteService.voteAnswer(requestDto);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get Vote API", description = "Get Vote With Vote Id")
    @GetMapping("/{vote-id}")
    public ResponseEntity<SingleResponseDto<VoteDto.Response>> getVote(@PathVariable("vote-id") @Positive long voteId) {
        VoteDto.Response responseDto = voteService.getVote(voteId);

        return ResponseEntity.ok(SingleResponseDto.<VoteDto.Response>builder().status(HttpStatusCode.OK.getStatusCode()).message(HttpStatusCode.OK.getMessage()).data(responseDto).build());
    }

    @Operation(summary = "Get Question Votes API", description = "Get Question Votes With Question Id")
    @GetMapping("/questionVoteResult")
    public ResponseEntity<SingleResponseDto<VoteDto.QuestionResponse>> getQuestionVotes(@Positive @RequestParam long questionId) {
        VoteDto.QuestionResponse responseDto = voteService.getQuestionVotes(questionId);

        return ResponseEntity.ok(SingleResponseDto.<VoteDto.QuestionResponse>builder().status(HttpStatusCode.OK.getStatusCode()).message(HttpStatusCode.OK.getMessage()).data(responseDto).build());
    }

    @Operation(summary = "Get Answer Votes API", description = "Get Answer Votes With Question Id")
    @GetMapping("/answerVoteResult")
    public ResponseEntity<SingleResponseDto<VoteDto.AnswerResponse>> getAnswerVotes(@Positive @RequestParam long answerId) {
        VoteDto.AnswerResponse responseDto = voteService.getAnswerVotes(answerId);

        return ResponseEntity.ok(SingleResponseDto.<VoteDto.AnswerResponse>builder().status(HttpStatusCode.OK.getStatusCode()).message(HttpStatusCode.OK.getMessage()).data(responseDto).build());
    }

    @Operation(summary = "Cancel Vote API", description = "Cancel Vote With Vote Id")
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAnswer(@PathVariable("vote-id") @Positive long voteId) {
        voteService.deleteVote(voteId);

        return ResponseEntity.noContent().build();
    }
}
