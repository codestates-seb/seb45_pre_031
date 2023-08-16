package com.codestates.stackoverflowbe.domain.question.service;

import com.codestates.stackoverflowbe.domain.account.entity.Account;
import com.codestates.stackoverflowbe.domain.question.dto.QuestionUpdateDto;
import com.codestates.stackoverflowbe.domain.question.entity.Question;
import com.codestates.stackoverflowbe.domain.question.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }
    // 질문 생성 메서드
    public Question createQuestion(QuestionUpdateDto questionDto, Account account) {
        // 새로운 질문 객체를 생성하고 정보를 설정합니다.
        Question newQuestion = new Question();
        newQuestion.setTitle(questionDto.getTitle());
        newQuestion.setBody(questionDto.getBody());
        newQuestion.setAccount(account);
        // user_id, tags, bodyHTML도 설정합니다.
        newQuestion.setUser_id(questionDto.getUser_id());
//        newQuestion.setTags(questionDto.getTags());
        newQuestion.setBodyHTML(questionDto.getBodyHTML());

        // 데이터베이스에 저장하고 생성된 질문을 반환합니다.
        return questionRepository.save(newQuestion);
    }
    // 모든 질문 목록 조회 메서드
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }
    // 특정 ID에 해당하는 질문 조회 메서드
    public Question findQuestionById(Long questionId) {
        return questionRepository.findById(questionId).orElse(null);
    }
    // 질문 저장 메서드
    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }
    // 질문 삭제 메서드
    public void deleteQuestion(Question question) {
        questionRepository.delete(question);
    }
    // 사용자별 질문 조회 메서드
    public List<Question> getQuestionsByUser(Account account) {
        // 사용자별로 작성한 질문을 조회합니다.
        return questionRepository.findByAccount(account);
    }
    // 최신 질문 조회 메서드
    public List<Question> getLatestQuestions() {
        // 질문을 최신 생성 순서대로 조회합니다.
        return questionRepository.findAllByOrderByCreatedAtDesc();
    }
    // 질문 제목으로 검색하는 메서드
    public List<Question> searchByTitle(String title) {
        return questionRepository.findByTitleContainingIgnoreCase(title);
    }
}
