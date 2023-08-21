package com.codestates.stackoverflowbe.domain.answer.service;

import com.codestates.stackoverflowbe.domain.account.entity.Account;
import com.codestates.stackoverflowbe.domain.account.service.AccountService;
import com.codestates.stackoverflowbe.domain.answer.dto.AnswerDto;
import com.codestates.stackoverflowbe.domain.answer.entity.Answer;
import com.codestates.stackoverflowbe.domain.answer.repository.AnswerRepository;
import com.codestates.stackoverflowbe.domain.comment.repository.AnswerCommentRepository;
import com.codestates.stackoverflowbe.domain.question.entity.Question;
import com.codestates.stackoverflowbe.domain.question.repository.QuestionRepository;
import com.codestates.stackoverflowbe.domain.vote.repository.VoteRepository;
import com.codestates.stackoverflowbe.domain.vote.service.VoteService;
import com.codestates.stackoverflowbe.global.exception.BusinessLogicException;
import com.codestates.stackoverflowbe.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final AccountService accountService;
    private final QuestionRepository questionRepository;
    private final AnswerCommentRepository answerCommentRepository;
    private final VoteRepository voteRepository;
    private final VoteService voteService;

    public AnswerDto.Response createAnswer(AnswerDto.Request requestDto, Object principal) {
        // 요청을 보낸 사용자의 정보를 가져옵니다.
        Account account = accountService.findByEmail((String) principal);
        Question findQuestion = questionRepository.findById(requestDto.getQuestionId()).orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));


        Answer answer = requestDto.toEntity().toBuilder()
                .account(account)
                .votes(new ArrayList<>())
                .question(findQuestion)
                .build();

        Answer savedAnswer = answerRepository.save(answer);
        findQuestion.addAnswer(savedAnswer);
        account.addAnswer(savedAnswer);

        return getAnswerResponseDto(account, savedAnswer);
    }

    public void updateAnswer(long answerId, AnswerDto.Request requestDto, Object principal) {
        Account account = accountService.findByEmail((String) principal);

        if (account.getAnswers().stream().noneMatch(answer -> answer.getAnswerId() == answerId))
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_ALLOW);

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
        Question findQuestion = questionRepository.findById(questionId).orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));

        Page<AnswerDto.Response> pageAnswers = answerRepository.findByQuestion(findQuestion, PageRequest.of(page, size))
                .map(answer -> getAnswerResponseDto(answer.getAccount(), answer));

        return pageAnswers;
    }

    public void deleteAnswer(long answerId) {
        Answer findAnswer = findVerifiedAnswer(answerId);
        voteRepository.deleteAll(findAnswer.getVotes());
        answerCommentRepository.deleteAll(findAnswer.getAnswerComments());
        answerRepository.delete(findAnswer);
    }

    @Transactional(readOnly = true)
    public Answer findVerifiedAnswer(long answerId) {
        Optional<Answer> optionalAnswer = answerRepository.findById(answerId);

        return optionalAnswer.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.ANSWER_NOT_FOUND));
    }

    private AnswerDto.Response getAnswerResponseDto(Account account, Answer answer) {
        return AnswerDto.Response.builder()
                .answerId(answer.getAnswerId())
                .body(answer.getBody())
                .voteUp(voteService.getUpVoteAccounts(answer.getVotes()))
                .voteDown(voteService.getDownVoteAccounts(answer.getVotes()))
                .displayName(account.getDisplayName())
                .created_at(answer.getCreatedAt())
                .modified_at(answer.getModifiedAt())
                .build();
    }
}
