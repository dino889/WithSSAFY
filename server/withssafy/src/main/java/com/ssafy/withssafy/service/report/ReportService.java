package com.ssafy.withssafy.service.report;

import com.ssafy.withssafy.dto.board.BoardResponse;
import com.ssafy.withssafy.dto.comment.CommentDto;
import com.ssafy.withssafy.dto.report.ReportReqDto;
import com.ssafy.withssafy.dto.report.ReportResDto;
import com.ssafy.withssafy.dto.user.UserDto;
import com.ssafy.withssafy.entity.Board;
import com.ssafy.withssafy.entity.Comment;
import com.ssafy.withssafy.entity.Report;
import com.ssafy.withssafy.entity.User;
import com.ssafy.withssafy.errorcode.ErrorCode;
import com.ssafy.withssafy.exception.InvalidRequestException;
import com.ssafy.withssafy.repository.board.BoardRepository;
import com.ssafy.withssafy.repository.CommentRepository;
import com.ssafy.withssafy.repository.ReportRepository;
import com.ssafy.withssafy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    private final ModelMapper modelMapper;

    public List<ReportResDto> findAll() {
        List<Report> list = reportRepository.findAll();
        List<ReportResDto> result = new ArrayList<>();

        for (Report r : list){
            ReportResDto item = modelMapper.map(r, ReportResDto.class);
            if(r.getUser() != null ) item.setUser(modelMapper.map(r.getUser(), UserDto.class));
            if(r.getBoard() != null) item.setBoard(modelMapper.map(r.getBoard(), BoardResponse.class));
            if(r.getComment() != null) item.setComment(modelMapper.map(r.getComment(), CommentDto.class));
            result.add(item);
        }

        return result;
    }


    @Modifying(clearAutomatically = true)
    public List<ReportResDto> insert(ReportReqDto reportDto) {
        if (!userRepository.findById(reportDto.getUser()).isPresent()){
            throw new InvalidRequestException(ErrorCode.NOT_JOINED_USER_ID);
        }

        if (reportDto.getBoard() != null && !boardRepository.findById(reportDto.getBoard()).isPresent()){
            throw new InvalidRequestException(ErrorCode.DOESNT_EXIST);
        }

        if (reportDto.getComment() != null && !commentRepository.findById(reportDto.getComment()).isPresent()){
            throw new InvalidRequestException(ErrorCode.DOESNT_EXIST);
        }

        reportDto.setWriteDateTime();
        Report report = modelMapper.map(reportDto, Report.class);
        User user = userRepository.findById(reportDto.getUser()).get();
        Board board = null;
        Comment comment = null;

        if (reportDto.getBoard() != null) board = boardRepository.findById(reportDto.getBoard()).get();
        if(reportDto.getComment() != null) comment = commentRepository.findById(reportDto.getComment()).get();

        report.setReport(board, comment, user);
        reportRepository.save(report);

        List<Report> list;
        if(reportDto.getBoard() != null) {
            list = reportRepository.findByBoardId(reportDto.getBoard());
        }else{
            list = reportRepository.findByCommentId(reportDto.getComment());
        }

        List<ReportResDto> result = new ArrayList<>();
        for(Report r : list){
            ReportResDto item = new ReportResDto();
            item.setId(r.getId());
            item.setWrite_dt(r.getWrite_dt());
            item.setContent(r.getContent());
            if(r.getUser() != null ) item.setUser(modelMapper.map(r.getUser(), UserDto.class));
            if(r.getBoard() != null) item.setBoard(modelMapper.map(r.getBoard(), BoardResponse.class));
            if(r.getComment() != null) item.setComment(modelMapper.map(r.getComment(), CommentDto.class));
            result.add(item);
        }
        return result;
    }

    public ReportResDto delete(Long id) {
        if (!reportRepository.findById(id).isPresent()){
            throw new InvalidRequestException(ErrorCode.DOESNT_EXIST);
        }

        Report report = reportRepository.findById(id).get();
        reportRepository.delete(report);

        ReportResDto result = modelMapper.map(report, ReportResDto.class);
        if(report.getComment() != null) result.setComment(modelMapper.map(report.getComment(), CommentDto.class));
        if(report.getBoard() != null) result.setBoard(modelMapper.map(report.getBoard(), BoardResponse.class));
        if(report.getUser() != null) result.setUser(modelMapper.map(report.getUser(), UserDto.class));
        return result;
    }

    public List<Long> findByCommentId(Long commentId) {
        List<Long> result = new ArrayList<>();
        List<Report> list = reportRepository.findByCommentId(commentId);
        for (Report r : list){
            result.add(r.getUser().getId());
        }
        return result;
    }

    public List<Long> findByBoardId(Long boardId) {
        List<Long> result = new ArrayList<>();
        List<Report> list = reportRepository.findByBoardId(boardId);
        for (Report r : list){
            result.add(r.getUser().getId());
        }
        return result;
    }
}
