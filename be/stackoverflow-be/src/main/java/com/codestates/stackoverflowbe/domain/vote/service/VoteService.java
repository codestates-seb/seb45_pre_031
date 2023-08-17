package com.codestates.stackoverflowbe.domain.vote.service;

import com.codestates.stackoverflowbe.domain.answer.entity.Answer;
import com.codestates.stackoverflowbe.domain.answer.repository.AnswerRepository;
import com.codestates.stackoverflowbe.domain.question.entity.Question;
import com.codestates.stackoverflowbe.domain.question.repository.QuestionRepository;
import com.codestates.stackoverflowbe.domain.vote.dto.VoteDto;
import com.codestates.stackoverflowbe.domain.vote.entity.Vote;
import com.codestates.stackoverflowbe.domain.vote.repository.VoteRepository;
import com.codestates.stackoverflowbe.global.exception.BusinessLogicException;
import com.codestates.stackoverflowbe.global.exception.ExceptionCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class VoteService {
    private final VoteRepository voteRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public VoteService(VoteRepository voteRepository, QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.voteRepository = voteRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public void voteWriting(VoteDto.Request requestDto) {
        Question findQuestion = new Question();
        Answer findAnswer = new Answer();
        List<Vote> findVotes = new ArrayList<>();

        if (Optional.ofNullable(requestDto.getQuestionId()).isPresent()) {
            findQuestion = questionRepository.findById(requestDto.getQuestionId()).orElseThrow(() ->
                    new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
            findVotes = findQuestion.getVotes();
        }

        if (Optional.ofNullable(requestDto.getAnswerId()).isPresent()) {
            findAnswer = answerRepository.findById(requestDto.getAnswerId()).orElseThrow(() ->
                    new BusinessLogicException(ExceptionCode.ANSWER_NOT_FOUND));
            findVotes = findAnswer.getVotes();
        }

        Vote vote = Vote.builder()
                .question(findQuestion)
                .answer(findAnswer)
                .upVote(requestDto.getUpVote())
                .downVote(requestDto.getDownVote())
                .build();
        verifyVotes(requestDto.getAccountId(), findVotes, vote);

        Vote savedVote = voteRepository.save(vote);
        findQuestion.addVote(savedVote);
        findAnswer.addVote(savedVote);
    }

    public VoteDto.Response getVotes(VoteDto.Request requestDto) {
        List<Vote> findVotes = new ArrayList<>();

        if (Optional.ofNullable(requestDto.getQuestionId()).isPresent()) {
            Question findQuestion = questionRepository.findById(requestDto.getQuestionId()).orElseThrow(() ->
                    new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
            findVotes = voteRepository.findByQuestion(findQuestion.getQuestionId());
        }

        if (Optional.ofNullable(requestDto.getAnswerId()).isPresent()) {
            Answer findAnswer = answerRepository.findById(requestDto.getAnswerId()).orElseThrow(() ->
                    new BusinessLogicException(ExceptionCode.ANSWER_NOT_FOUND));
            findVotes = voteRepository.findByAnswer(findAnswer.getAnswerId());
        }

        return VoteDto.Response.builder()
                .upVotes(getUpVoteAccounts(findVotes))
                .downVotes(getDownVoteAccounts(findVotes))
                .build();
    }

    public void deleteVote(long voteId) {
        Vote findVote = voteRepository.findById(voteId).orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.VOTE_NOT_FOUND));
        voteRepository.delete(findVote);
    }

    private void verifyVotes(long accountId, List<Vote> votes, Vote vote) {
        if (vote.isUpVote() == vote.isDownVote()) throw new BusinessLogicException(ExceptionCode.VOTE_NOT_ALLOW);

        votes = votes.stream()
                .filter(v -> v.getAccount().getAccountId() == accountId)
                .collect(Collectors.toList());

        if (votes.size() != 0) throw new BusinessLogicException(ExceptionCode.VOTE_NOT_ALLOW);
    }

    public List<String> getUpVoteAccounts(List<Vote> votes) {
        return votes.stream()
                .filter(Vote::isUpVote)
                .map(vote -> vote.getAccount().getDisplayName())
                .collect(Collectors.toList());
    }

    public List<String> getDownVoteAccounts(List<Vote> votes) {
        return votes.stream()
                .filter(Vote::isDownVote)
                .map(vote -> vote.getAccount().getDisplayName())
                .collect(Collectors.toList());
    }
}
