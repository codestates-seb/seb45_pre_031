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

    // 생성자 주입을 통해 QuestionRepository를 주입받습니다.
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

        // 데이터베이스에 저장하고 생성된 질문을 반환합니다.
        return questionRepository.save(newQuestion);
    }

    // 모든 질문 목록 조회 메서드
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    // 기타 로직 메서드는 여기에 추가 가능
}
