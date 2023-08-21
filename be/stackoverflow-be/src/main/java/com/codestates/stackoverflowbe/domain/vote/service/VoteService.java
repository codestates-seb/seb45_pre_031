package com.codestates.stackoverflowbe.domain.vote.service;

import com.codestates.stackoverflowbe.domain.account.entity.Account;
import com.codestates.stackoverflowbe.domain.account.repository.AccountRepository;
import com.codestates.stackoverflowbe.domain.answer.entity.Answer;
import com.codestates.stackoverflowbe.domain.answer.repository.AnswerRepository;
import com.codestates.stackoverflowbe.domain.question.entity.Question;
import com.codestates.stackoverflowbe.domain.question.repository.QuestionRepository;
import com.codestates.stackoverflowbe.domain.vote.dto.VoteDto;
import com.codestates.stackoverflowbe.domain.vote.entity.Vote;
import com.codestates.stackoverflowbe.domain.vote.repository.VoteRepository;
import com.codestates.stackoverflowbe.global.exception.BusinessLogicException;
import com.codestates.stackoverflowbe.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class VoteService {
    private final VoteRepository voteRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final AccountRepository accountRepository;

    public void voteWriting(VoteDto.Request requestDto, Object principal) {
        Question findQuestion = null;
        Answer findAnswer = null;
        List<Vote> findVotes;

        if (Optional.ofNullable(requestDto.getQuestionId()).isPresent()) {
            findQuestion = questionRepository.findById(requestDto.getQuestionId()).orElseThrow(() ->
                    new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
            findVotes = findQuestion.getVotes();
        } else {
            findAnswer = answerRepository.findById(requestDto.getAnswerId()).orElseThrow(() ->
                    new BusinessLogicException(ExceptionCode.ANSWER_NOT_FOUND));
            findVotes = findAnswer.getVotes();
        }

        // 요청을 보낸 사용자의 정보를 가져옵니다.
        Account account = accountRepository.findByEmail((String) principal).orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        Vote vote = Vote.builder()
                .account(account)
                .question(findQuestion)
                .answer(findAnswer)
                .upVote(requestDto.getUpVote())
                .downVote(requestDto.getDownVote())
                .build();

        findVotes = findVotes.stream()
                .filter(element -> element.getAccount().getAccountId() == account.getAccountId())
                .collect(Collectors.toList());

        if (findVotes.size() != 0) {
            Vote findVote = findVotes.get(0);
            if ((findVote.isUpVote() && vote.isUpVote()) || (findVote.isDownVote() && vote.isDownVote())) {
                findVotes.remove(findVote);
                deleteVote(findVote.getVoteId());
            } else {
                findVote.setUpVote(!findVote.isUpVote());
                findVote.setDownVote(!findVote.isDownVote());
            }
        } else {
            Vote savedVote = voteRepository.save(vote);
            if (Optional.ofNullable(requestDto.getQuestionId()).isPresent()) findQuestion.addVote(savedVote);
            else findAnswer.addVote(savedVote);
        }
    }

    public VoteDto.Response getVotes(VoteDto.Request requestDto) {
        List<Vote> findVotes = new ArrayList<>();

        if (Optional.ofNullable(requestDto.getQuestionId()).isPresent()) {
            Question findQuestion = questionRepository.findById(requestDto.getQuestionId()).orElseThrow(() ->
                    new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
            findVotes = voteRepository.findByQuestion_QuestionId(findQuestion.getQuestionId());
        }

        if (Optional.ofNullable(requestDto.getAnswerId()).isPresent()) {
            Answer findAnswer = answerRepository.findById(requestDto.getAnswerId()).orElseThrow(() ->
                    new BusinessLogicException(ExceptionCode.ANSWER_NOT_FOUND));
            findVotes = voteRepository.findByAnswer_AnswerId(findAnswer.getAnswerId());
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
