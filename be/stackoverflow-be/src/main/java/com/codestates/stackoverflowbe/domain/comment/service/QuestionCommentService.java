package com.codestates.stackoverflowbe.domain.comment.service;


import com.codestates.stackoverflowbe.domain.account.entity.Account;
import com.codestates.stackoverflowbe.domain.account.repository.AccountRepository;
import com.codestates.stackoverflowbe.domain.answer.dto.AnswerDto;
import com.codestates.stackoverflowbe.domain.comment.dto.request.QuestionCommentRequestDto;
import com.codestates.stackoverflowbe.domain.comment.dto.response.QuestionCommentResponseDto;
import com.codestates.stackoverflowbe.domain.comment.entity.QuestionComment;
import com.codestates.stackoverflowbe.domain.comment.repository.QuestionCommentRepository;
import com.codestates.stackoverflowbe.domain.question.entity.Question;
import com.codestates.stackoverflowbe.domain.question.repository.QuestionRepository;
import com.codestates.stackoverflowbe.domain.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionCommentService {

    private final QuestionCommentRepository questionCommentRepository;
    private final AccountRepository accountRepository;
    private final QuestionRepository questionRepository;
    private final QuestionService questionService;

    @Transactional
    public QuestionComment saveComment(QuestionCommentRequestDto.Post requestCommentDto) {

        Account account = accountRepository.findById(requestCommentDto.getAccountId())
                .orElseThrow(() -> new EntityNotFoundException("not found account"));

        Question question = questionRepository.findById(requestCommentDto.getQuestionId())
                .orElseThrow(() -> new EntityNotFoundException("not found question"));

        QuestionComment questionComment = QuestionComment.builder()
                .contents(requestCommentDto.getContents())
                .account(account)
                .question(question)
                .build();

        return questionCommentRepository.save(questionComment);
    }

    public void updateComment(Long id, QuestionCommentRequestDto.Patch requestCommentDto) {
        QuestionComment savedQuestionComment = verifyExistsComment(id);
        savedQuestionComment.update(requestCommentDto.getContents());
    }


    public void deleteComment(Long id) {
        QuestionComment questionComment = verifyExistsComment(id);

        questionCommentRepository.delete(questionComment);
    }

    private QuestionComment verifyExistsComment(Long id) {
        Optional<QuestionComment> comment = questionCommentRepository.findById(id);

        return comment.orElseThrow(IllegalArgumentException::new);
    }

    @Transactional(readOnly = true)
    public Page<QuestionCommentResponseDto> findcomments(int page, int size, long questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new IllegalArgumentException("not found"));
//        Question question1 = questionService.findQuestionById(questionId);

            Page<QuestionCommentResponseDto> pageComments = questionCommentRepository.findByQuestion_QuestionId(questionId, PageRequest.of(page, size))
                .map(comment -> QuestionCommentResponseDto.builder()
                        .content(comment.getContents())
                        .questionId(question.getQuestionId())
                        .displayName(comment.getAccount().getDisplayName())
                        .createAt(comment.getCreatedAt())
                        .modifiedAt(comment.getModifiedAt())
                        .build());

        return pageComments;
    }
}
