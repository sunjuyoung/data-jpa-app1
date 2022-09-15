package com.example.demo.service;

import com.example.demo.dto.PageRequestDto;
import com.example.demo.dto.PageResponseDto;
import com.example.demo.dto.ReplyDto;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class ReplyServiceImplTest {

    @Autowired
    ReplyService replyService;

    @Test
    public void tes2t() throws Exception{
        //given
        PageRequestDto pageRequestDto = PageRequestDto.builder()

                .page(1)
                .size(5)
                .build();
        Long board_id = 19L;
        PageResponseDto<ReplyDto> replyDtoPageResponseDto = replyService.listOfBoard(board_id, pageRequestDto);
        //when

        //then
    }

    @Test
    public void listTest() throws Exception{
        //given
        PageRequestDto pageRequestDto = PageRequestDto.builder()

                .page(1)
                .size(5)
                .build();
        Long board_id = 19L;
        PageResponseDto<ReplyDto> replyDtoPageResponseDto = replyService.listOfBoard(board_id, pageRequestDto);


        //when

        //then
    }

    @Test
    public void test() throws Exception{
        //given
        ReplyDto replyDto = ReplyDto.builder()
                .replyText("test...3")
                .board_id(17L)
                .username("user1")
                .build();
        Long register = replyService.register(replyDto);
        //when

        //then
    }
}