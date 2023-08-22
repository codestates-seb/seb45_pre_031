package com.codestates.stackoverflowbe.domain.comment.service;


import com.codestates.stackoverflowbe.domain.account.entity.Account;
import com.codestates.stackoverflowbe.domain.account.repository.AccountRepository;
import com.codestates.stackoverflowbe.domain.answer.entity.Answer;
import com.codestates.stackoverflowbe.domain.answer.repository.AnswerRepository;
import com.codestates.stackoverflowbe.domain.comment.dto.request.AnswerCommentRequestDto;
import com.codestates.stackoverflowbe.domain.comment.dto.response.AnswerCommentResponseDto;
import com.codestates.stackoverflowbe.domain.comment.entity.AnswerComment;
import com.codestates.stackoverflowbe.domain.comment.repository.AnswerCommentRepository;
import com.codestates.stackoverflowbe.global.exception.BusinessLogicException;
import com.codestates.stackoverflowbe.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerCommentService {

    private final AnswerCommentRepository answerCommentRepository;
    private final AccountRepository accountRepository;
    private final AnswerRepository answerRepository;

    @Transactional
    public AnswerCommentResponseDto saveComment(AnswerCommentRequestDto.Post requestCommentDto, Object accountEmail) {

        Account account = verifyExistsAccount(accountEmail);

        Answer answer = answerRepository.findById(requestCommentDto.getAnswerId())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.ANSWER_NOT_FOUND));

        AnswerComment answerComment = AnswerComment.builder()
                .contents(requestCommentDto.getContents())
                .account(account)
                .answer(answer)
                .build();


        AnswerComment savedComment = answerCommentRepository.save(answerComment);
        account.update(savedComment);
        answer.addAnswerComment(savedComment);

        return AnswerCommentResponseDto.builder()
                .commentId(answerComment.getCommentId())
                .build();
    }

    @Transactional
    public void updateComment(Long answerCommentId, AnswerCommentRequestDto.Patch requestCommentDto, Object accountEmail) {
        Account account = verifyExistsAccount(accountEmail);

        boolean hasComment = account.getAnswerComments().stream().anyMatch(comment -> Objects.equals(comment.getCommentId(), answerCommentId));

        // 사용자에 대한 댓글을 찾을 수 없다면
        if (!hasComment) throw new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND);

        AnswerComment answerComment = verifyExistsComment(answerCommentId);
        answerComment.update(Optional.ofNullable(requestCommentDto.getContents()).orElse(answerComment.getContents()));
    }


    @Transactional
    public void deleteComment(Long answerCommentId, Object accountEmail) {
        Account account = verifyExistsAccount(accountEmail);
        AnswerComment answerComment = verifyExistsComment(answerCommentId);
        boolean hasComment = account.getAnswerComments().stream().anyMatch(comment -> Objects.equals(comment.getCommentId(), answerCommentId));

        if (!hasComment) throw new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND);


        answerCommentRepository.delete(answerComment);
    }

    @Transactional(readOnly = true)
    public Account verifyExistsAccount(Object principal) {
        return accountRepository.findByEmail((String) principal)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public AnswerComment verifyExistsComment(Long id) {
        return answerCommentRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Page<AnswerCommentResponseDto> findcomments(int page, int size, Long answerId) {
        Answer answer = answerRepository.findById(answerId).orElseThrow(() -> new IllegalArgumentException("not found"));

            Page<AnswerCommentResponseDto> pageComments = answerCommentRepository.findByAnswer_AnswerId(answerId, PageRequest.of(page, size))
                .map(comment -> AnswerCommentResponseDto.builder()
                        .commentId(comment.getCommentId())
                        .accountId(comment.getAccount().getAccountId())
                        .answerId(answer.getAnswerId())
                        .contents(comment.getContents())
                        .displayName(comment.getAccount().getDisplayName())
                        .email(comment.getAccount().getEmail())
                        .createAt(comment.getCreatedAt())
                        .modifiedAt(comment.getModifiedAt())
                        .build());

        return pageComments;
    }
}
