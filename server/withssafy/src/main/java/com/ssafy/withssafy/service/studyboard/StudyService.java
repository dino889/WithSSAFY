package com.ssafy.withssafy.service.studyboard;

import com.ssafy.withssafy.dto.studyboard.StudyBoardRequest;
import com.ssafy.withssafy.dto.studyboard.StudyBoardResponse;
import com.ssafy.withssafy.dto.studyboard.StudyMemberRequest;
import com.ssafy.withssafy.entity.StudyBoard;
import com.ssafy.withssafy.entity.StudyMember;
import com.ssafy.withssafy.errorcode.ErrorCode;
import com.ssafy.withssafy.exception.InvalidRequestException;
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

    private final ModelMapper modelMapper;

    @Transactional
    public void addStudyBoard(StudyBoardRequest studyBoardRequest, MultipartFile file) {
        if (!file.isEmpty()) {
            String filename = FileManager.save(file, studyBoardRequest.getUserId());
            studyBoardRequest.setPhotoPath(filename);
        }

        StudyBoard studyBoard = modelMapper.map(studyBoardRequest, StudyBoard.class);
        studyBoardRepository.save(studyBoard);

    }

    @Transactional
    public void modifyStudyBoard(StudyBoardRequest studyBoardRequest, Long id) {
        Optional<StudyBoard> studyBoard = studyBoardRepository.findById(id);
        if (studyBoard.isPresent()) {
            studyBoard.get().updateStudyBoard(studyBoardRequest);
        } else {
            throw new InvalidRequestException(ErrorCode.INVALID_REQUEST);
        }
    }

    public List<StudyBoardResponse> getStudyBoards() {
        List<StudyBoard> studyBoards = studyBoardRepository.findAll();

        List<StudyBoardResponse> studyBoardResponses = studyBoards.stream().map(studyBoard -> modelMapper.map(studyBoard, StudyBoardResponse.class))
                .collect(Collectors.toList());

        for (StudyBoardResponse studyBoardResponse : studyBoardResponses) {
            String file = FileManager.getFile(studyBoardResponse.getPhotoPath());
            studyBoardResponse.setPhotoFile(file);
        }

        return studyBoardResponses;
    }

    public StudyBoardResponse getStudyBoardById(Long id) {
        Optional<StudyBoard> studyBoard = studyBoardRepository.findById(id);

        if (studyBoard.isPresent()) {
            StudyBoardResponse studyBoardResponse = modelMapper.map(studyBoard.get(), StudyBoardResponse.class);
            String file = FileManager.getFile(studyBoardResponse.getPhotoPath());
            studyBoardResponse.setPhotoFile(file);

            return studyBoardResponse;
        } else {
            return null;
        }

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
}
