package com.codestates.stackoverflowbe.domain.answer.service;

import com.codestates.stackoverflowbe.domain.answer.dto.AnswerDto;
import com.codestates.stackoverflowbe.domain.answer.entity.Answer;
import com.codestates.stackoverflowbe.domain.answer.repository.AnswerRepository;
import com.codestates.stackoverflowbe.domain.vote.service.VoteService;
import com.codestates.stackoverflowbe.global.exception.BusinessLogicException;
import com.codestates.stackoverflowbe.global.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final VoteService voteService;

    public AnswerService(AnswerRepository answerRepository, VoteService voteService) {
        this.answerRepository = answerRepository;
        this.voteService = voteService;
    }

    public AnswerDto.Response createAnswer(AnswerDto.Request requestDto) {
        Answer answer = requestDto.toEntity();
        Answer savedAnswer = answerRepository.save(answer);

        return AnswerDto.Response.builder()
                .answerId(savedAnswer.getAnswerId())
                .body(savedAnswer.getBody())
                .build();
    }

    public AnswerDto.Response updateAnswer(long answerId, AnswerDto.Request requestDto) {
        Answer answer = requestDto.toEntity().toBuilder().answerId(answerId).build();
        Answer findAnswer = findVerifiedAnswer(answer.getAnswerId());
        findAnswer = findAnswer.toBuilder()
                .body(Optional.ofNullable(answer.getBody()).orElse(findAnswer.getBody()))
                .votes(Optional.ofNullable(answer.getVotes()).orElse(findAnswer.getVotes()))
                .build();

        Answer savedAnswer = answerRepository.save(findAnswer);

        return AnswerDto.Response.builder()
                .answerId(savedAnswer.getAnswerId())
                .body(savedAnswer.getBody())
                .upVotes(voteService.getUpVoteAccounts(savedAnswer.getVotes()))
                .downVotes(voteService.getDownVoteAccounts(savedAnswer.getVotes()))
                .build();
    }

    @Transactional(readOnly = true)
    public AnswerDto.Response findAnswer(long answerId) {
        Answer findAnswer = findVerifiedAnswer(answerId);

        return AnswerDto.Response.builder()
                .answerId(findAnswer.getAnswerId())
                .body(findAnswer.getBody())
                .upVotes(voteService.getUpVoteAccounts(findAnswer.getVotes()))
                .downVotes(voteService.getDownVoteAccounts(findAnswer.getVotes()))
                .build();
    }

    @Transactional(readOnly = true)
    public Page<AnswerDto.Response> findAnswers(int page, int size, long questionId) {
//        Page<AnswerDto.Response> pageAnswers = answerRepository.findByQuestion(questionId, PageRequest.of(page, size))
//                .map(answer -> AnswerDto.Response.builder()
//                        .answerId(answer.getAnswerId())
//                        .body(answer.getBody())
//                        .vote(answer.getVote())
//                        .build());

        Page<AnswerDto.Response> pageAnswers = answerRepository.findAll(PageRequest.of(page, size))
                .map(answer -> AnswerDto.Response.builder()
                        .answerId(answer.getAnswerId())
                        .body(answer.getBody())
                        .upVotes(voteService.getUpVoteAccounts(answer.getVotes()))
                        .downVotes(voteService.getDownVoteAccounts(answer.getVotes()))
                        .build());

        return pageAnswers;
    }

    public void deleteAnswer(long answerId) {
        Answer findAnswer = findVerifiedAnswer(answerId);
        answerRepository.delete(findAnswer);
    }

    @Transactional(readOnly = true)
    public Answer findVerifiedAnswer(long answerId) {
        Optional<Answer> optionalAnswer = answerRepository.findById(answerId);
        Answer findAnswer = optionalAnswer.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.ANSWER_NOT_FOUND));

        return findAnswer;
    }
}
