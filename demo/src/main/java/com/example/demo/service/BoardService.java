package com.example.demo.service;

import com.example.demo.dto.BoardDto;

public interface BoardService {

    Long register(BoardDto boardDto);

    BoardDto readOne(Long id);

    void modify(BoardDto boardDto);

    void remove(Long id);
}
