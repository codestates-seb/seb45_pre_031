package com.codestates.stackoverflowbe.domain.comment.service;


import com.codestates.stackoverflowbe.domain.comment.dto.request.RequestCommentDto;
import com.codestates.stackoverflowbe.domain.comment.dto.response.ResponseCommentDto;
import com.codestates.stackoverflowbe.domain.comment.entity.Comment;
import com.codestates.stackoverflowbe.domain.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public ResponseCommentDto saveComment(RequestCommentDto requestCommentDto) {
        Comment savedComment = commentRepository.save(requestCommentDto.toEntity());


        ResponseCommentDto responseCommentDto = ResponseCommentDto.builder()
                .commentId(savedComment.getCommentId())
                .content(savedComment.getContent())
                .createAt(savedComment.getCreatedAt())
                .modifiedAt(savedComment.getModifiedAt())
                .build();

        return responseCommentDto;
    }

    public ResponseCommentDto updateComment(Long id, RequestCommentDto requestCommentDto) {
        Comment savedComment = verifyExistsComment(id);

        savedComment.update(requestCommentDto.getContent());

        ResponseCommentDto responseCommentDto = ResponseCommentDto.builder()
                .commentId(savedComment.getCommentId())
                .content(savedComment.getContent())
                .createAt(savedComment.getCreatedAt())
                .modifiedAt(savedComment.getModifiedAt())
                .build();

        return responseCommentDto;
    }


    public void deleteComment(Long id) {
        Comment comment = verifyExistsComment(id);

        commentRepository.delete(comment);
    }

    // Id를 기준으로 찾은 Comment 검증
    private Comment verifyExistsComment(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);

        return comment.orElseThrow(IllegalAccessError::new);
    }
}
