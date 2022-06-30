package com.ssafy.withssafy.service.sbcomment;

import com.ssafy.withssafy.dto.board.BoardResponse;
import com.ssafy.withssafy.dto.comment.CommentDto;
import com.ssafy.withssafy.dto.comment.WriterDto;
import com.ssafy.withssafy.dto.sbcomment.SbCommentDto;
import com.ssafy.withssafy.dto.sbcomment.SbCommentRequest;
import com.ssafy.withssafy.dto.studyboard.StudyBoardResponse;
import com.ssafy.withssafy.dto.user.UserDto;
import com.ssafy.withssafy.entity.SbComment;
import com.ssafy.withssafy.errorcode.ErrorCode;
import com.ssafy.withssafy.exception.InvalidRequestException;
import com.ssafy.withssafy.repository.SbCommentRepository;
import com.ssafy.withssafy.repository.StudyBoardRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SbCommentServiceImpl implements SbCommentService{
    @Autowired
    SbCommentRepository sbCommentRepository;

    @Autowired
    StudyBoardRepository studyBoardRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<SbCommentDto> findByUserId(Long userId) {
        List<SbComment> list = sbCommentRepository.findByUserId(userId);
        return list.stream().map(sbComment -> modelMapper.map(sbComment, SbCommentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<SbCommentDto> findByBoardId(Long boardId) {
        List<SbComment> list = sbCommentRepository.findByBoardId(boardId);
        return list.stream().map(sbComment -> modelMapper.map(sbComment, SbCommentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<SbCommentDto> findAll() {
        List<SbComment> list = sbCommentRepository.findAll();
        return list.stream().map(sbComment -> modelMapper.map(sbComment, SbCommentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Modifying(clearAutomatically = true)
    public boolean delete(Long id) {
        if (!sbCommentRepository.findById(id).isPresent()) return false;
        sbCommentRepository.deleteById(id);
        return !sbCommentRepository.findById(id).isPresent();
    }

    @Override
    @Transactional
    public SbCommentDto update(SbCommentRequest sbCommentRequest) {
        sbCommentRepository.update(sbCommentRequest.getId(), sbCommentRequest.getContent());
        SbComment sbComment = sbCommentRepository.findById(sbCommentRequest.getId()).get();
        SbCommentDto sbCommentDto = modelMapper.map(sbComment, SbCommentDto.class);
        return sbCommentDto;
    }

    @Override
    public SbCommentDto insert(SbCommentRequest sbCommentRequest) {
        if(!studyBoardRepository.findById(sbCommentRequest.getBoardId()).isPresent()){
            throw new InvalidRequestException(ErrorCode.DOESNT_EXIST);
        }

        sbCommentRequest.setWriteDateTime();

        SbComment sbComment = modelMapper.map(sbCommentRequest, SbComment.class);
        sbComment.updateStudyBoard(studyBoardRepository.findById(sbCommentRequest.getBoardId()).get());
        SbComment trans = sbCommentRepository.save(sbComment);
        SbCommentDto result = modelMapper.map(trans, SbCommentDto.class);
        result.setUser(modelMapper.map(trans.getUser(), WriterDto.class));
        result.setBoardId(trans.getStudyBoard().getId());
        return result;
    }
}
