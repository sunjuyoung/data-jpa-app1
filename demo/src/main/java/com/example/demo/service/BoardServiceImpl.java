package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.Board;
import com.example.demo.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper;

    @Override
    public PageResponseDto<BoardDto> list(PageRequestDto pageRequestDto) {
        String[] types = pageRequestDto.getTypes();
        String keyword = pageRequestDto.getKeyword();
        Pageable pageable = pageRequestDto.getPageable("id");

        Page<BoardListReplyCountDto> result = boardRepository.searchWithReplyCount(types, keyword, pageable);

        List<BoardListReplyCountDto> content = result.getContent();


        Page<Board> boardPage = boardRepository.searchAll(types, keyword, pageable);
        List<BoardDto> dtoList = boardPage.getContent().stream()
                .map(board -> modelMapper.map(board, BoardDto.class)).collect(Collectors.toList());


        return  PageResponseDto.<BoardDto>withAll()
                .pageRequestDto(pageRequestDto)
                .dtoList(dtoList)
                .total((int)boardPage.getTotalElements())
                .build();
    }

    @Override
    public PageResponseDto<BoardListReplyCountDto> listWithReplyCount(PageRequestDto pageRequestDto) {
        String[] types = pageRequestDto.getTypes();
        String keyword = pageRequestDto.getKeyword();
        Pageable pageable = pageRequestDto.getPageable("createdDate");

        Page<BoardListReplyCountDto> result = boardRepository.searchWithReplyCount(types, keyword, pageable);

        List<BoardListReplyCountDto> content = result.getContent();


        return  PageResponseDto.<BoardListReplyCountDto>withAll()
                .pageRequestDto(pageRequestDto)
                .dtoList(content)
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public PageResponseDto<BoardListAllDto> listWithAll(PageRequestDto pageRequestDto) {
        return null;
    }

    @Transactional
    @Override
    public void modify(BoardDto boardDto) {
        Board board = boardRepository.findById(boardDto.getId()).orElseThrow();
        board.clearImage();

        if(boardDto.getFileName() != null){
            boardDto.getFileName().forEach(s -> {
                String[] arr = s.split("_");
                board.addImage(arr[0],arr[1]);
            });
        }
        board.change(boardDto.getTitle(),boardDto.getContent());
    }

    @Override
    public void remove(Long id) {
        boardRepository.deleteById(id);
    }

    @Override
    public BoardDto readOne(Long id) {
        Optional<Board> board = boardRepository.findByIdWithImages(id);
        Board board1 = board.orElseThrow();
        BoardDto boardDto = entityToDto(board1);
       // BoardDto boardDto = modelMapper.map(board1, BoardDto.class);
        return boardDto;
    }

    @Override
    public Long register(BoardDto boardDto) {
        //Board board = modelMapper.map(boardDto, Board.class);
        Board board = dtoToEntity(boardDto);
        Board newBoard = boardRepository.save(board);
        return newBoard.getId();
    }
}
