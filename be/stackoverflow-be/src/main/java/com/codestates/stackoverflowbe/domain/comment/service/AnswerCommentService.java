package com.codestates.stackoverflowbe.domain.comment.service;


import com.codestates.stackoverflowbe.domain.account.entity.Account;
import com.codestates.stackoverflowbe.domain.account.repository.AccountRepository;
import com.codestates.stackoverflowbe.domain.answer.entity.Answer;
import com.codestates.stackoverflowbe.domain.answer.repository.AnswerRepository;
import com.codestates.stackoverflowbe.domain.comment.dto.request.AnswerCommentRequestDto;
import com.codestates.stackoverflowbe.domain.comment.dto.request.QuestionCommentRequestDto;
import com.codestates.stackoverflowbe.domain.comment.dto.response.AnswerCommentResponseDto;
import com.codestates.stackoverflowbe.domain.comment.dto.response.QuestionCommentResponseDto;
import com.codestates.stackoverflowbe.domain.comment.entity.AnswerComment;
import com.codestates.stackoverflowbe.domain.comment.entity.QuestionComment;
import com.codestates.stackoverflowbe.domain.comment.repository.AnswerCommentRepository;
import com.codestates.stackoverflowbe.domain.question.entity.Question;
import com.codestates.stackoverflowbe.global.exception.BusinessLogicException;
import com.codestates.stackoverflowbe.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerCommentService {

    private final AnswerCommentRepository answerCommentRepository;
    private final AccountRepository accountRepository;
    private final AnswerRepository answerRepository;

    @Transactional
    public AnswerComment saveComment(AnswerCommentRequestDto.Post requestCommentDto) {

        Account account = accountRepository.findById(requestCommentDto.getAccountId())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        Answer answer = answerRepository.findById(requestCommentDto.getAnswerId())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.ANSWER_NOT_FOUND));

        AnswerComment answerComment = AnswerComment.builder()
                .contents(requestCommentDto.getContents())
                .account(account)
                .answer(answer)
                .build();

        return answerCommentRepository.save(answerComment);
    }

    @Transactional
    public void updateComment(Long id, AnswerCommentRequestDto.Patch requestCommentDto) {
        AnswerComment answerComment = verifyExistsComment(id);
        answerComment.update(requestCommentDto.getContents());
    }


    @Transactional
    public void deleteComment(Long id) {
        AnswerComment answerComment = verifyExistsComment(id);

        answerCommentRepository.delete(answerComment);
    }

    private AnswerComment verifyExistsComment(Long id) {
        Optional<AnswerComment> comment = answerCommentRepository.findById(id);

        return comment.orElseThrow(IllegalArgumentException::new);
    }

    @Transactional(readOnly = true)
    public Page<AnswerCommentResponseDto> findcomments(int page, int size, Long answerId) {
        Answer answer = answerRepository.findById(answerId).orElseThrow(() -> new IllegalArgumentException("not found"));

            Page<AnswerCommentResponseDto> pageComments = answerCommentRepository.findByAnswer_AnswerId(answerId, PageRequest.of(page, size))
                .map(comment -> AnswerCommentResponseDto.builder()
                        .content(comment.getContents())
                        .answerId(answer.getAnswerId())
                        .displayName(comment.getAccount().getDisplayName())
                        .createAt(comment.getCreatedAt())
                        .modifiedAt(comment.getModifiedAt())
                        .build());

        return pageComments;
    }
}
