package com.ssafy.withssafy.service.studyboard;

import com.ssafy.withssafy.dto.studyboard.StudyBoardRequest;
import com.ssafy.withssafy.dto.studyboard.StudyBoardResponse;
import com.ssafy.withssafy.dto.studyboard.StudyMemberRequest;
import com.ssafy.withssafy.entity.ClassRoom;
import com.ssafy.withssafy.entity.StudyBoard;
import com.ssafy.withssafy.entity.StudyMember;
import com.ssafy.withssafy.entity.User;
import com.ssafy.withssafy.errorcode.ErrorCode;
import com.ssafy.withssafy.exception.InvalidRequestException;
import com.ssafy.withssafy.repository.ClassRoomRepository;
import com.ssafy.withssafy.repository.StudyBoardRepository;
import com.ssafy.withssafy.repository.StudyMemberRepository;
import com.ssafy.withssafy.util.FileManager;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudyService {

    private final StudyBoardRepository studyBoardRepository;

    private final StudyMemberRepository studyMemberRepository;

    private final ClassRoomRepository classRoomRepository;

    private final ModelMapper modelMapper;

    @Transactional
    public void addStudyBoard(StudyBoardRequest studyBoardRequest) {
        studyBoardRequest.setWriteDateTime();
        StudyBoard studyBoard = modelMapper.map(studyBoardRequest, StudyBoard.class);
        studyBoardRepository.save(studyBoard);

        StudyMember studyMember = StudyMember.builder()
                .user(
                        User.builder().id(studyBoardRequest.getUserId()).build()
                )
                .studyBoard(
                        StudyBoard.builder().id(studyBoard.getId()).build()
                )
                .build();

        studyMemberRepository.save(studyMember);
    }

    @Transactional
    public void modifyStudyBoard(Long id, StudyBoardRequest studyBoardRequest) {
        Optional<StudyBoard> studyBoard = studyBoardRepository.findById(id);
        if (studyBoard.isPresent()) {
            studyBoard.get().updateStudyBoard(studyBoardRequest);
        } else {
            throw new InvalidRequestException(ErrorCode.INVALID_REQUEST);
        }
    }

    public List<StudyBoardResponse> getStudyBoards() {
        List<StudyBoard> studyBoards = studyBoardRepository.findAll();

        return studyBoards.stream().map(studyBoard -> modelMapper.map(studyBoard, StudyBoardResponse.class))
                .collect(Collectors.toList());
    }

    public StudyBoardResponse getStudyBoardById(Long id) {
        Optional<StudyBoard> studyBoard = studyBoardRepository.findById(id);

        return studyBoard.map(board -> modelMapper.map(board, StudyBoardResponse.class)).orElse(null);

    }

    @Transactional
    public void removeStudyBoardById(Long id) {
        studyBoardRepository.deleteById(id);
    }

    @Transactional
    public void joinStudy(Long studyId, StudyMemberRequest studyMemberRequest) {
        studyMemberRequest.setStudyBoardId(studyId);

        if (!studyBoardRepository.findById(studyId).isPresent()) {
            throw new InvalidRequestException(ErrorCode.INVALID_REQUEST);
        }

        if (studyMemberRepository.findBySbIdAndUserId(studyMemberRequest).isPresent()) {
            throw new InvalidRequestException(ErrorCode.JOINED_STUDY_USER);
        }

        StudyMember studyMember = modelMapper.map(studyMemberRequest, StudyMember.class);
        studyMemberRepository.save(studyMember);
    }

    @Transactional
    public void leaveStudy(Long studyId, StudyMemberRequest studyMemberRequest) {
        studyMemberRequest.setStudyBoardId(studyId);
        studyMemberRepository.deleteBySbIdAndUserId(studyMemberRequest);
    }

    public List<StudyBoardResponse> findByType(int type) {
        List<StudyBoard> studyBoards = studyBoardRepository.findByType(type);
        return studyBoards.stream().map(studyBoard -> modelMapper.map(studyBoard, StudyBoardResponse.class))
                .collect(Collectors.toList());
    }

    public List<StudyBoardResponse> findByArea(Long classRoomId) {
        if(!classRoomRepository.findById(classRoomId).isPresent()){
            throw new InvalidRequestException(ErrorCode.DOESNT_EXIST);
        }

        ClassRoom cr = classRoomRepository.findById(classRoomId).get();
        List<StudyBoard> studyBoards = studyBoardRepository.findByAreaAndType(cr.getArea(), 1);
        return studyBoards.stream().map(studyBoard -> modelMapper.map(studyBoard, StudyBoardResponse.class))
                .collect(Collectors.toList());
    }
}
