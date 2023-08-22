package com.codestates.stackoverflowbe.domain.comment.service;


import com.codestates.stackoverflowbe.domain.account.entity.Account;
import com.codestates.stackoverflowbe.domain.account.repository.AccountRepository;
import com.codestates.stackoverflowbe.domain.comment.dto.request.QuestionCommentRequestDto;
import com.codestates.stackoverflowbe.domain.comment.dto.response.QuestionCommentResponseDto;
import com.codestates.stackoverflowbe.domain.comment.entity.QuestionComment;
import com.codestates.stackoverflowbe.domain.comment.repository.QuestionCommentRepository;
import com.codestates.stackoverflowbe.domain.question.entity.Question;
import com.codestates.stackoverflowbe.domain.question.repository.QuestionRepository;
import com.codestates.stackoverflowbe.global.exception.BusinessLogicException;
import com.codestates.stackoverflowbe.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionCommentService {

    private final QuestionCommentRepository questionCommentRepository;
    private final AccountRepository accountRepository;
    private final QuestionRepository questionRepository;

    @Transactional
    public QuestionCommentResponseDto saveComment(QuestionCommentRequestDto.Post requestCommentDto, Object accountEmail) {

        Account account = verifyExistsAccount(accountEmail);

        Question question = questionRepository.findById(requestCommentDto.getQuestionId())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));

        QuestionComment questionComment = QuestionComment.builder()
                .contents(requestCommentDto.getContents())
                .account(account)
                .question(question)
                .build();

        QuestionComment savedComment = questionCommentRepository.save(questionComment);
        account.update(savedComment);
        question.addQuestionComment(savedComment);

        return QuestionCommentResponseDto.builder()
                .commentId(questionComment.getCommentId())
                .build();
    }

    @Transactional
    public void updateComment(Long questionId, QuestionCommentRequestDto.Patch requestCommentDto, Object accountEmail) {
        Account account = verifyExistsAccount(accountEmail);

        boolean hasComment = account.getQuestionComments().stream().anyMatch(comment -> Objects.equals(comment.getCommentId(), questionId));

        if (!hasComment) throw new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND);


        QuestionComment questionComment = verifyExistsComment(questionId);
        questionComment.update(Optional.ofNullable(requestCommentDto.getContents()).orElse(questionComment.getContents()));
    }


    @Transactional
    public void deleteComment(Long questionCommentId, Object accountEmail) {
        Account account = verifyExistsAccount(accountEmail);
        QuestionComment questionComment = verifyExistsComment(questionCommentId);

        boolean hasComment = account.getAnswerComments().stream().anyMatch(comment -> Objects.equals(comment.getCommentId(), questionCommentId));

        if (!hasComment) throw new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND);

        questionCommentRepository.delete(questionComment);
    }

    private QuestionComment verifyExistsComment(Long id) {
        Optional<QuestionComment> comment = questionCommentRepository.findById(id);

        return comment.orElseThrow(IllegalArgumentException::new);
    }

    @Transactional(readOnly = true)
    public Page<QuestionCommentResponseDto> findcomments(int page, int size, Long questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new IllegalArgumentException("not found"));

            Page<QuestionCommentResponseDto> pageComments = questionCommentRepository.findByQuestion_QuestionId(questionId, PageRequest.of(page, size))
                .map(comment -> QuestionCommentResponseDto.builder()
                        .contents(comment.getContents())
                        .accountId(comment.getAccount().getAccountId())
                        .questionId(question.getQuestionId())
                        .displayName(comment.getAccount().getDisplayName())
                        .email(comment.getAccount().getEmail())
                        .createAt(comment.getCreatedAt())
                        .modifiedAt(comment.getModifiedAt())
                        .build());

        return pageComments;
    }

    @Transactional(readOnly = true)
    public Account verifyExistsAccount(Object principal) {
        return accountRepository.findByEmail((String) principal)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }
}
