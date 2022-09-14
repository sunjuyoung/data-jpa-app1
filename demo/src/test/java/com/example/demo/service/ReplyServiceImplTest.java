package com.example.demo.service;

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
    public void test() throws Exception{
        //given
        ReplyDto replyDto = ReplyDto.builder()
                .replyText("test...3")
                .board_id(17L)
                .replyer("user1")
                .build();
        Long register = replyService.register(replyDto);
        //when

        //then
    }
}