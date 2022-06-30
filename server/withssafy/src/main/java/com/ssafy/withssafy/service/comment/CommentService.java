package com.ssafy.withssafy.service.comment;

import com.ssafy.withssafy.dto.comment.CommentDto;
import com.ssafy.withssafy.dto.comment.CommentResDto;

import java.util.List;

public interface CommentService {
    List<CommentResDto> findAll();
    List<CommentResDto> findByBoardId(Long BoardId);
    List<CommentResDto> findByUserId(Long UserId);
    CommentResDto insert(CommentDto commentDto);
    CommentResDto update(CommentDto commentDto);
    boolean delete(Long id);
    CommentDto findById(Long id);
}
