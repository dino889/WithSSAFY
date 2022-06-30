package com.ssafy.withssafy.service.sbcomment;

import com.ssafy.withssafy.dto.comment.CommentDto;
import com.ssafy.withssafy.dto.sbcomment.SbCommentDto;
import com.ssafy.withssafy.dto.sbcomment.SbCommentRequest;

import java.util.List;

public interface SbCommentService {
    List<SbCommentDto> findByUserId(Long userId);
    List<SbCommentDto> findByBoardId(Long boardId);
    List<SbCommentDto> findAll();
    boolean delete(Long id);
    SbCommentDto update(SbCommentRequest sbCommentRequest);
    SbCommentDto insert(SbCommentRequest sbCommentRequest);
}
