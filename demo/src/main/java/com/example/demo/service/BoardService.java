package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.Board;

import java.util.List;
import java.util.stream.Collectors;

public interface BoardService {

    Long register(BoardDto boardDto);

    BoardDto readOne(Long id);

    void modify(BoardDto boardDto);

    void remove(Long id);

    PageResponseDto<BoardDto> list(PageRequestDto pageRequestDto);

    PageResponseDto<BoardListReplyCountDto> listWithReplyCount(PageRequestDto pageRequestDto);

    PageResponseDto<BoardListAllDto> listWithAll(PageRequestDto pageRequestDto);


    default Board dtoToEntity(BoardDto boardDto){
        Board board = Board.builder()
                .content(boardDto.getContent())
                .title(boardDto.getTitle())
                .username(boardDto.getUsername())
                .build();

        if(boardDto.getFileName() != null){
            boardDto.getFileName().forEach(fileName->{
                String [] arr = fileName.split("_");
                board.addImage(arr[0],arr[1]);

            });
        }
        return board;
    }
    default BoardDto entityToDto(Board board){
        BoardDto boardDto = BoardDto.builder()
                .boardId(board.getBoardId())
                .username(board.getUsername())
                .title(board.getTitle())
                .content(board.getContent())
                .createdDate(board.getCreatedDate())
                .modifiedDate(board.getModifiedDate())
                .build();

        List<String> fileNames = board.getImageSet().stream().sorted().map(boardImage -> {
            return boardImage.getUuid() + "_" + boardImage.getFileName();
        }).collect(Collectors.toList());
        boardDto.setFileName(fileNames);

        return boardDto;

    }
}
