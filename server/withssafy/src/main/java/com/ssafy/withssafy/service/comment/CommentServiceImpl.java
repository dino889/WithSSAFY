package com.ssafy.withssafy.service.comment;

import com.ssafy.withssafy.dto.comment.CommentDto;
import com.ssafy.withssafy.dto.comment.CommentResDto;
import com.ssafy.withssafy.entity.Comment;
import com.ssafy.withssafy.repository.CommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<CommentResDto> findAll() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream().map(comment -> modelMapper.map(comment, CommentResDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<CommentResDto> findByBoardId(Long boardId) {
        List<Comment> comments = commentRepository.findByBoardId(boardId);
        return comments.stream().map(comment -> modelMapper.map(comment, CommentResDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<CommentResDto> findByUserId(Long userId) {
        List<Comment> comments = commentRepository.findByUserId(userId);
        return comments.stream().map(comment -> modelMapper.map(comment, CommentResDto.class)).collect(Collectors.toList());
    }

    @Override
    public CommentResDto insert(CommentDto commentDto) {
        commentDto.setWriteDateTime();
        Comment comment = modelMapper.map(commentDto, Comment.class);
        Comment result = commentRepository.save(comment);
        return modelMapper.map(result, CommentResDto.class);
    }

    @Override
    @Transactional
    @Modifying(clearAutomatically = true)
    public CommentResDto update(CommentDto commentDto) {
        commentRepository.update(commentDto.getId(), commentDto.getContent());
        CommentResDto result = modelMapper.map(commentRepository.findById(commentDto.getId()), CommentResDto.class);
        return result;
    }

    @Override
    @Modifying(clearAutomatically = true)
    public boolean delete(Long id) {
        if (!commentRepository.findById(id).isPresent()) return false;
        commentRepository.deleteById(id);
        return !commentRepository.findById(id).isPresent();
    }

    @Override
    public CommentDto findById(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);

        return comment.map(value -> modelMapper.map(value, CommentDto.class)).orElse(null);
    }
}
