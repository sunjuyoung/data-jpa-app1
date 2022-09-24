package com.example.demo.repository;

import com.example.demo.dto.ReplyDto;
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
    public void tes3() throws Exception{
        //given

        //when

        //then
    }

    @Test
    public void listOfBoard() throws Exception{
        //given
        Sort reply_id = Sort.by("createdDate").descending();
        Pageable pageable = PageRequest.of(0,4,reply_id);
        Long board_id = 20L;
        Page<ReplyDto> listOfBoard = replyRepository.getListOfBoard(board_id, pageable);
        List<ReplyDto> content = listOfBoard.getContent();

        for(ReplyDto dto : content){
            System.out.println(dto);
        }
        //when

        //then
    }

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
        Long boardId = 44L;

        Board board = Board.builder().boardId(boardId).build();

        for(int i=1; i<20; i++){
            String username = "user"+i;
            Reply reply = Reply.builder()
                    .board(board)
                    .replyText("test...reply"+i)
                    .username(username)
                    .build();
            Reply save = replyRepository.save(reply);
        }


        //when

        //then
    }
}