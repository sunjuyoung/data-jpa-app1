package com.example.demo.service;

import com.example.demo.dto.BoardDto;
import com.example.demo.dto.BoardListReplyCountDto;
import com.example.demo.dto.PageRequestDto;
import com.example.demo.dto.PageResponseDto;

public interface BoardService {

    Long register(BoardDto boardDto);

    BoardDto readOne(Long id);

    void modify(BoardDto boardDto);

    void remove(Long id);

    PageResponseDto<BoardDto> list(PageRequestDto pageRequestDto);

    PageResponseDto<BoardListReplyCountDto> listWithReplyCount(PageRequestDto pageRequestDto);


}
