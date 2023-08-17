package com.codestates.stackoverflowbe.domain.answer.service;

import com.codestates.stackoverflowbe.domain.answer.dto.AnswerDto;
import com.codestates.stackoverflowbe.domain.answer.entity.Answer;
import com.codestates.stackoverflowbe.domain.answer.repository.AnswerRepository;
import com.codestates.stackoverflowbe.domain.comment.repository.CommentRepository;
import com.codestates.stackoverflowbe.domain.question.entity.Question;
import com.codestates.stackoverflowbe.domain.question.service.QuestionService;
import com.codestates.stackoverflowbe.domain.vote.repository.VoteRepository;
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
    private final QuestionService questionService;
    private final CommentRepository commentRepository;
    private final VoteRepository voteRepository;
    private final VoteService voteService;

    public AnswerService(AnswerRepository answerRepository, QuestionService questionService, CommentRepository commentRepository, VoteRepository voteRepository, VoteService voteService) {
        this.answerRepository = answerRepository;
        this.questionService = questionService;
        this.commentRepository = commentRepository;
        this.voteRepository = voteRepository;
        this.voteService = voteService;
    }

    public AnswerDto.Response createAnswer(AnswerDto.Request requestDto) {
        Question findQuestion = questionService.findQuestionById(requestDto.getQuestionId());

        Answer answer = requestDto.toEntity().toBuilder()
                .question(findQuestion)
                .build();

        Answer savedAnswer = answerRepository.save(answer);

        return AnswerDto.Response.builder()
                .answerId(savedAnswer.getAnswerId())
                .build();
    }

    public void updateAnswer(long answerId, AnswerDto.Request requestDto) {
        Answer answer = requestDto.toEntity().toBuilder()
                .answerId(answerId)
                .build();

        Answer findAnswer = findVerifiedAnswer(answerId);
        findAnswer = findAnswer.toBuilder()
                .body(Optional.ofNullable(answer.getBody()).orElse(findAnswer.getBody()))
                .build();

        Answer savedAnswer = answerRepository.save(findAnswer);
    }

    @Transactional(readOnly = true)
    public Page<AnswerDto.Response> findAnswers(int page, int size, long questionId) {
        Page<AnswerDto.Response> pageAnswers = answerRepository.findByQuestion(questionId, PageRequest.of(page, size))
                .map(answer -> AnswerDto.Response.builder()
                        .answerId(answer.getAnswerId())
                        .body(answer.getBody())
                        .build());

        return pageAnswers;
    }

    public void deleteAnswer(long answerId) {
        Answer findAnswer = findVerifiedAnswer(answerId);
        voteRepository.deleteAll(findAnswer.getVotes());
        commentRepository.deleteAll(findAnswer.getComments());
        answerRepository.delete(findAnswer);
    }

    @Transactional(readOnly = true)
    public Answer findVerifiedAnswer(long answerId) {
        Optional<Answer> optionalAnswer = answerRepository.findById(answerId);

        return optionalAnswer.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.ANSWER_NOT_FOUND));
    }
}
