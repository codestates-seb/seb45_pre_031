package com.codestates.stackoverflowbe.domain.question.service;

import com.codestates.stackoverflowbe.domain.account.entity.Account;
import com.codestates.stackoverflowbe.domain.answer.service.AnswerService;
import com.codestates.stackoverflowbe.domain.question.dto.QuestionResponseDto;
import com.codestates.stackoverflowbe.domain.question.dto.QuestionUpdateDto;
import com.codestates.stackoverflowbe.domain.question.entity.Question;
import com.codestates.stackoverflowbe.domain.question.repository.QuestionRepository;
import com.codestates.stackoverflowbe.domain.tag.entity.Tag;
import com.codestates.stackoverflowbe.domain.tag.repository.TagRepository;
import com.codestates.stackoverflowbe.domain.vote.service.VoteService;
import com.codestates.stackoverflowbe.global.exception.BusinessLogicException;
import com.codestates.stackoverflowbe.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final VoteService voteService;
    private final AnswerService answerService;

    // 새로운 질문 생성
    public Question createQuestion(QuestionUpdateDto questionDto, Account account) {
        // 새로운 질문 엔티티를 생성하고 정보를 설정합니다.
        Question newQuestion = new Question();
        newQuestion.setTitle(questionDto.getTitle());
        newQuestion.setBody(questionDto.getBody());
        newQuestion.setAccount(account);
        newQuestion.setViewCount(0L);

        // 데이터베이스에 질문을 저장하고 반환합니다.
        return questionRepository.save(newQuestion);
    }

    // 모든 질문 목록 조회
    public Page<QuestionResponseDto> getAllQuestions(int page, int size) {
        return questionRepository.findAll(PageRequest.of(page, size))
                .map(this::getQuestionQuestionResponseDto);
    }

    // 특정 ID에 해당하는 질문 조회
    public Question findQuestionById(Long questionId) {
        return questionRepository.findById(questionId).orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
    }

    // 질문 저장
    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }

    // 질문 삭제
    public void deleteQuestion(Question question) {
        question.getAnswers()
                .forEach(answer -> answerService.deleteAnswer(answer.getAnswerId()));
        questionRepository.delete(question);
    }

    // 특정 사용자가 작성한 질문 조회
    public Page<QuestionResponseDto> getQuestionsByUser(int page, int size, Account account) {
        // 해당 사용자가 작성한 질문을 조회합니다.
        return questionRepository.findByAccount(account, PageRequest.of(page, size))
                .map(this::getQuestionQuestionResponseDto);
    }

    // 최신 질문 조회
    public Page<QuestionResponseDto> getNewestQuestions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return questionRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(this::getQuestionQuestionResponseDto);
    }

    // 질문 목록을 Active: 수정 시간 최신순으로 가져오는 메서드
    public Page<QuestionResponseDto> getActiveQuestions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("modifiedAt").descending());
        return questionRepository.findAllByOrderByModifiedAtDesc(pageable)
                .map(this::getQuestionQuestionResponseDto);
    }

    // 답변이 없는 질문 목록을 가져오는 메서드
    public Page<QuestionResponseDto> getUnansweredQuestions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("questionId").descending());
        return questionRepository.findAllByAnswersIsEmpty(pageable)
                .map(this::getQuestionQuestionResponseDto);
    }

    //     질문 목록을 Score: Score 순으로 가져오는 메서드
    public Page<QuestionResponseDto> getScoreQuestions(int page, int size) {
        return questionRepository.findAll(PageRequest.of(page, size, Sort.by("questionId").descending()))
                .map(this::getQuestionQuestionResponseDto);
    }

    public List<QuestionResponseDto> getScoreQuestionsList(Page<QuestionResponseDto> questionResponseDtos) {
        return questionResponseDtos.stream()
                .sorted(Comparator.comparing(questionResponseDto ->
                        questionResponseDto.getVoteDown().size() - questionResponseDto.getVoteUp().size()))
                .collect(Collectors.toList());
    }

    // 지난 주 동안 가장 많이 본 질문 조회
    public Page<QuestionResponseDto> getWeekQuestions(int page, int size) {
        LocalDateTime weekAgo = LocalDateTime.now().minus(7, ChronoUnit.DAYS);
        Pageable pageable = PageRequest.of(page, size, Sort.by("viewCount").descending().and(Sort.by("questionId").descending()));
        return questionRepository.findByCreatedAtAfterOrderByViewCountDesc(weekAgo, pageable)
                .map(this::getQuestionQuestionResponseDto);
    }

    // 지난 달 동안 가장 많이 본 질문 조회
    public Page<QuestionResponseDto> getMonthQuestions(int page, int size) {
        LocalDateTime monthAgo = LocalDateTime.now().minus(30, ChronoUnit.DAYS);
        Pageable pageable = PageRequest.of(page, size, Sort.by("viewCount").descending().and(Sort.by("questionId").descending()));
        return questionRepository.findByCreatedAtAfterOrderByViewCountDesc(monthAgo, pageable)
                .map(this::getQuestionQuestionResponseDto);
    }

    // 인기 질문 조회
    public Page<QuestionResponseDto> getHotQuestions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("viewCount").descending());
        return questionRepository.findAllByOrderByViewCountDesc(pageable)
                .map(this::getQuestionQuestionResponseDto);
    }

    public QuestionResponseDto getQuestionQuestionResponseDto(Question question) {
        return QuestionResponseDto.builder()
                .questionId(question.getQuestionId())
                .title(question.getTitle())
                .body(question.getBody())
                .answersCount(question.getAnswers().size())
                .views(question.getViewCount())
                .displayName(question.getAccount().getDisplayName())
                .voteUp(voteService.getUpVoteAccounts(question.getVotes()))
                .voteDown(voteService.getDownVoteAccounts(question.getVotes()))
                .tags(question.getTags().stream()
                        .map(tag -> tag.getTagName())
                        .collect(Collectors.toList()))
                .created_at(question.getCreatedAt())
                .modified_at(question.getModifiedAt())
                .build();
    }
}





