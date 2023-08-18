package com.codestates.stackoverflowbe.domain.question.service;

import com.codestates.stackoverflowbe.domain.account.entity.Account;
import com.codestates.stackoverflowbe.domain.question.dto.QuestionUpdateDto;
import com.codestates.stackoverflowbe.domain.question.entity.Question;
import com.codestates.stackoverflowbe.domain.question.repository.QuestionRepository;
import com.codestates.stackoverflowbe.domain.tag.entity.Tag;
import com.codestates.stackoverflowbe.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final TagRepository tagRepository;

    // 새로운 질문 생성
    public Question createQuestion(QuestionUpdateDto questionDto, Account account) {
        // 새로운 질문 엔티티를 생성하고 정보를 설정합니다.

        List<Tag> tags = new ArrayList<>();
        tags.add(Tag.builder()
                .tagName("tag1")
                .build());

        tags.add(Tag.builder()
                .tagName("tag2")
                .build());

        Question newQuestion = new Question();
        newQuestion.setTitle(questionDto.getTitle());
        newQuestion.setBody(questionDto.getBody());
        newQuestion.setExpectContents(questionDto.getExpectContents());
        newQuestion.setAccount(account);
        newQuestion.setViewCount(0L);
        newQuestion.setTags(tags);


        // 데이터베이스에 질문을 저장하고 반환합니다.
        return questionRepository.save(newQuestion);
    }

    // 모든 질문 목록 조회
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    // 특정 ID에 해당하는 질문 조회
    public Question findQuestionById(Long questionId) {
        return questionRepository.findById(questionId).orElse(null);
    }

    // 질문 저장
    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }

    // 질문 삭제
    public void deleteQuestion(Question question) {
        questionRepository.delete(question);
    }

    // 특정 사용자가 작성한 질문 조회
    public List<Question> getQuestionsByUser(Account account) {
        // 해당 사용자가 작성한 질문을 조회합니다.
        return questionRepository.findByAccount(account);
    }

    // 최신 질문 조회
    public Page<Question> getNewestQuestions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return questionRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    // 인기 질문 조회
    public Page<Question> getHotQuestions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("viewCount").descending());
        return questionRepository.findAllByOrderByViewCountDesc(pageable);
    }

    // 지난 주 동안 가장 많이 본 질문 조회
    public Page<Question> getWeekQuestions(int page, int size) {
        LocalDateTime weekAgo = LocalDateTime.now().minus(7, ChronoUnit.DAYS);
        Pageable pageable = PageRequest.of(page, size, Sort.by("viewCount").descending());
        return questionRepository.findByCreatedAtAfterOrderByViewCountDesc(weekAgo, pageable);
    }

    // 지난 달 동안 가장 많이 본 질문 조회
    public Page<Question> getMonthQuestions(int page, int size) {
        LocalDateTime monthAgo = LocalDateTime.now().minus(30, ChronoUnit.DAYS);
        Pageable pageable = PageRequest.of(page, size, Sort.by("viewCount").descending());
        return questionRepository.findByCreatedAtAfterOrderByViewCountDesc(monthAgo, pageable);
    }

    // 질문 목록을 Active: 수정 시간 최신순으로 가져오는 메서드
    public List<Question> getActiveQuestions() {
        List<Question> questions = questionRepository.findAllByOrderByModifiedAtDesc();
        return questions;
    }

    // 질문 목록을 Score: Score 순으로 가져오는 메서드
//    public List<Question> getScoreQuestions() {
//        List<Question> questions = questionRepository.findAllByOrderByScoreDesc();
//        return questions;
//    }

    // 답변이 없는 질문 목록을 가져오는 메서드
//    public List<Question> getUnansweredQuestions() {
//        List<Question> questions = questionRepository.findAllByAnswersIsEmpty();
//        return questions;
//    }
}





