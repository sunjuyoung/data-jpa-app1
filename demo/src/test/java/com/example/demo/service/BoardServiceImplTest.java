package com.example.demo.service;

import com.example.demo.dto.BoardDto;
import com.example.demo.dto.PageRequestDto;
import com.example.demo.dto.PageResponseDto;
import com.example.demo.entity.Board;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class BoardServiceImplTest {

    @Autowired
    BoardService boardService;

    @Transactional
    @Rollback(value = false)
    @Test
    public void modifyWithImage() throws Exception{
        //given
        BoardDto boardDto = BoardDto.builder()
                .content("test image save222222")
                .title("test image save22222222")
                .username("user2")
                .boardId(45L)
                .build();

        boardDto.setFileName(
                Arrays.asList(
                        UUID.randomUUID()+"_aaa.jpg"

                )
        );


        boardService.modify(boardDto);


        //when

        //then
    }

    @Transactional
    @Test
    public void testRead() throws Exception{
        //given
        Long id = 45L;

        BoardDto boardDto = boardService.readOne(id);
        for(String file : boardDto.getFileName()){
            log.info(file);
        }

        //when

        //then
    }

    @Test
    public void registerWithImage() throws Exception{
        //given
        BoardDto boardDto = BoardDto.builder()
                .content("test image save")
                .title("test image save")
                .username("user2")
                .build();

        boardDto.setFileName(
                Arrays.asList(
                        UUID.randomUUID()+"_aaa.jpg",
                        UUID.randomUUID()+"_bbb.jpg",
                        UUID.randomUUID()+"_ccc.jpg"
                )
        );
        Long register = boardService.register(boardDto);
        log.info(register);


        //when

        //then
    }


    @Test
    public void test() throws Exception{
        //given
        PageRequestDto pageRequestDto = PageRequestDto.builder()
        .type("tcw")
        .keyword("1")
                .page(1)
                .size(5)
                .build();

        PageResponseDto<BoardDto> list = boardService.list(pageRequestDto);
        log.info(list);
        //when

        //then
    }

    @Test
    public void modify() throws Exception{
        //given
        BoardDto board = BoardDto.builder()
                .boardId(5L)
                .title("modify title...")
                .content("modify content...")
                .username("user2")
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
                .username("user2")
                .build();
        Long id = boardService.register(board);

        System.out.println(id);
    }
}