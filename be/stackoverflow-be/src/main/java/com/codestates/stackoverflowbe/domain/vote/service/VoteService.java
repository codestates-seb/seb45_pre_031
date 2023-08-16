package com.codestates.stackoverflowbe.domain.vote.service;

import com.codestates.stackoverflowbe.domain.answer.entity.Answer;
import com.codestates.stackoverflowbe.domain.answer.repository.AnswerRepository;
import com.codestates.stackoverflowbe.domain.vote.dto.VoteDto;
import com.codestates.stackoverflowbe.domain.vote.entity.Vote;
import com.codestates.stackoverflowbe.domain.vote.repository.VoteRepository;
import com.codestates.stackoverflowbe.global.exception.BusinessLogicException;
import com.codestates.stackoverflowbe.global.exception.ExceptionCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class VoteService {
    private final VoteRepository voteRepository;
    // questionRepository 필요
    private final AnswerRepository answerRepository;

    public VoteService(VoteRepository voteRepository, AnswerRepository answerRepository) {
        this.voteRepository = voteRepository;
        this.answerRepository = answerRepository;
    }

    public void voteQuestion(VoteDto.QuestionRequest requestDto) {

    }

    public void voteAnswer(VoteDto.AnswerRequest requestDto) {
        Answer findAnswer = answerRepository.findById(requestDto.getAnswerId()).orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.VOTE_NOT_FOUND));

        List<Vote> votes = findAnswer.getVotes();
        Vote vote = Vote.builder()
                .upVote(requestDto.isUpVote())
                .downVote(requestDto.isDownVote())
                .build();
//        verifyVotes(requestDto.getAccountId(), votes, vote);

        findAnswer.addVote(vote);
        vote.setAnswer(findAnswer);
        voteRepository.save(vote);
    }

    public VoteDto.Response getVote(long voteId) {
        Vote findVote = voteRepository.findById(voteId).orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.VOTE_NOT_FOUND));

        return VoteDto.Response.builder()
                .voteId(findVote.getVoteId())
                .upVote(findVote.isUpVote())
                .downVote(findVote.isDownVote())
                .build();
    }

    public VoteDto.QuestionResponse getQuestionVotes(long questionId) {
        List<Vote> findVotes = voteRepository.findByQuestion(questionId);

        return VoteDto.QuestionResponse.builder()
                .questionId(questionId)
                .upVotes(getUpVoteAccounts(findVotes))
                .downVotes(getDownVoteAccounts(findVotes))
                .build();
    }

    public VoteDto.AnswerResponse getAnswerVotes(long answerId) {
        List<Vote> findVotes = voteRepository.findByAnswer(answerId);

        return VoteDto.AnswerResponse.builder()
                .answerId(answerId)
                .upVotes(getUpVoteAccounts(findVotes))
                .downVotes(getDownVoteAccounts(findVotes))
                .build();
    }

    public void deleteVote(long voteId) {
        Vote findVote = voteRepository.findById(voteId).orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.VOTE_NOT_FOUND));
        voteRepository.delete(findVote);
    }

    private void verifyVotes(long accountId, List<Vote> votes, Vote vote) {
        if (vote.isUpVote() == vote.isDownVote()) throw new BusinessLogicException(ExceptionCode.VOTE_NOT_ALLOW);

        votes = votes.stream()
                .filter(v -> v.getAccount().getAccountId() == accountId)
                .collect(Collectors.toList());

        if (votes.size() != 0) throw new BusinessLogicException(ExceptionCode.VOTE_NOT_ALLOW);
    }

    public List<String> getUpVoteAccounts(List<Vote> votes) {
        return votes.stream()
                .filter(Vote::isUpVote)
                .map(vote -> vote.getAccount().getDisplayName())
                .collect(Collectors.toList());
    }

    public List<String> getDownVoteAccounts(List<Vote> votes) {
        return votes.stream()
                .filter(Vote::isDownVote)
                .map(vote -> vote.getAccount().getDisplayName())
                .collect(Collectors.toList());
    }
}
