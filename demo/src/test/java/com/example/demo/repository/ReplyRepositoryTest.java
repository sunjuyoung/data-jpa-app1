package com.example.demo.repository;

import com.example.demo.entity.Board;
import com.example.demo.entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReplyRepositoryTest {

    @Autowired
    ReplyRepository replyRepository;


    @Test
    public void test1() throws Exception{
        //given
        Pageable pageable = PageRequest.of(0,4, Sort.by("id").descending());
        Long board_id = 21L;
        Page<Reply> replies = replyRepository.listOfBoard(board_id, pageable);
        List<Reply> content = replies.getContent();
        content.forEach(reply -> System.out.println(reply.toString()));
        //when

        //then
    }

    @Test
    public void test() throws Exception{
        //given
        Long board_id = 21L;
        Board board = Board.builder().id(board_id).build();

        Reply reply = Reply.builder()
                .board(board)
                .replyText("test...reply1")
                .replyer("user2")
                .build();
        Reply save = replyRepository.save(reply);

        assertEquals(save.getReplyer(),"user2");
        //when

        //then
    }
}