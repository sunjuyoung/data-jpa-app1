package com.example.demo.service;

import com.example.demo.dto.BoardDto;
import com.example.demo.entity.Board;
import com.example.demo.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper;


    @Transactional
    @Override
    public void modify(BoardDto boardDto) {
        Board board = boardRepository.findById(boardDto.getId()).orElseThrow();
        board.change(boardDto.getTitle(),boardDto.getContent());
    }

    @Override
    public void remove(Long id) {
        boardRepository.deleteById(id);
    }

    @Override
    public BoardDto readOne(Long id) {
        Optional<Board> board = boardRepository.findById(id);
        Board board1 = board.orElseThrow();
        BoardDto boardDto = modelMapper.map(board1, BoardDto.class);
        return boardDto;
    }

    @Override
    public Long register(BoardDto boardDto) {
        Board board = modelMapper.map(boardDto, Board.class);
        Board newBoard = boardRepository.save(board);
        return newBoard.getId();
    }
}
