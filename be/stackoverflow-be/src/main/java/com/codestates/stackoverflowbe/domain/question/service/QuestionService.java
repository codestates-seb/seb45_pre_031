package com.codestates.stackoverflowbe.domain.question.service;

import com.codestates.stackoverflowbe.domain.account.entity.Account;
import com.codestates.stackoverflowbe.domain.question.dto.QuestionDto;
import com.codestates.stackoverflowbe.domain.question.entity.Question;
import com.codestates.stackoverflowbe.domain.question.repository.QuestionRepository;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    // 질문 관련 작업을 처리하는 서비스 클래스

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question createQuestion(QuestionDto questionDto, Account account) {
        // 새로운 질문을 생성하고 데이터베이스에 저장

        Question newQuestion = new Question();
        newQuestion.setTitle(questionDto.getTitle());
        newQuestion.setBody(questionDto.getBody());
        newQuestion.setAccount(account);

        // 다른 Question 엔티티의 필드를 설정할 경우 여기에 추가

        return questionRepository.save(newQuestion);
    }

    // 기타 로직 메서드는 여기에 추가 가능
}