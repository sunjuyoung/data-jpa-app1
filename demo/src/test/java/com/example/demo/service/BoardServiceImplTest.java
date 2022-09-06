package com.example.demo.service;

import com.example.demo.dto.BoardDto;
import com.example.demo.entity.Board;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class BoardServiceImplTest {

    @Autowired
    BoardService boardService;


    @Test
    public void modify() throws Exception{
        //given
        BoardDto board = BoardDto.builder()
                .id(5L)
                .title("modify title...")
                .content("modify content...")
                .writer("user2")
                .build();
        boardService.modify(board);

        //when
        BoardDto boardDto = boardService.readOne(5L);
        assertEquals("modify content...",boardDto.getContent());

        //then
    }

    @Test
    public void register() throws Exception{
        //given
        BoardDto board = BoardDto.builder()
                .title("sample title...")
                .content("sample content...")
                .writer("user2")
                .build();
        Long id = boardService.register(board);

        System.out.println(id);
    }
}