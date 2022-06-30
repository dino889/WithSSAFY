package com.ssafy.withssafy.service.board;

import com.ssafy.withssafy.dto.board.BoardTypeDto;
import com.ssafy.withssafy.dto.board.BoardTypeRequest;
import com.ssafy.withssafy.entity.BoardType;
import com.ssafy.withssafy.errorcode.ErrorCode;
import com.ssafy.withssafy.exception.InvalidRequestException;
import com.ssafy.withssafy.repository.BoardTypeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardTypeService {

    private final ModelMapper modelMapper;

    private final BoardTypeRepository boardTypeRepository;

    public List<BoardTypeDto> getBoardTypes() {
        List<BoardType> boardTypes = boardTypeRepository.findAll();

        return boardTypes.stream().map(boardType -> modelMapper.map(boardType, BoardTypeDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void addBoardType(BoardTypeRequest boardTypeRequest) {
        BoardType boardType = modelMapper.map(boardTypeRequest, BoardType.class);
        boardTypeRepository.save(boardType);
    }

    @Transactional
    public void modifyBoardTypeById(BoardTypeRequest boardTypeRequest, Long id) {
        Optional<BoardType> boardType = boardTypeRepository.findById(id);

        if (boardType.isPresent()) {
            boardType.get().updateBoardType(boardTypeRequest);
        } else {
            throw new InvalidRequestException(ErrorCode.INVALID_REQUEST);
        }
    }

    @Transactional
    public void removeBoardTypeById(Long id){
        Optional<BoardType> boardType = boardTypeRepository.findById(id);
        boardType.ifPresent(boardTypeRepository::delete);
    }

}
