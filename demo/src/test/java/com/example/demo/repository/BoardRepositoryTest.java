package com.example.demo.repository;

import com.example.demo.dto.BoardListReplyCountDto;
import com.example.demo.entity.Board;
import com.example.demo.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void querydslDtoReplyCount() throws Exception{
        //given

        String[] types  = {"t","c"};
        Pageable pageable = PageRequest.of(0,5,Sort.by("id").descending());
        String keyword = "1";
        Page<BoardListReplyCountDto> result = boardRepository.searchWithReplyCount(types, keyword, pageable);

        result.getContent().forEach(board ->{
            System.out.println(board);
        });

    }

    @Test
    public void querydslWhere() throws Exception{
        //given

        String[] types  = {"t","c"};
        Pageable pageable = PageRequest.of(0,5,Sort.by("id").descending());
        Page<Board> result = boardRepository.searchAll(types, "1", pageable);


        System.out.println(result.getTotalPages());
        System.out.println(result.getSize());
        System.out.println(result.hasNext());
        System.out.println(result.hasPrevious());
        for (Board board : result.getContent()) {
            System.out.println(board);
        }
    }

    @Test
    public void querydsltest() throws Exception{
        //given
        Pageable pageable = PageRequest.of(0,5,Sort.by("id").descending());
        boardRepository.search1(pageable);

        //when

        //then
    }

    @Test
    public void test1() throws Exception{
        //given
/*        Pageable pageable = PageRequest.of(0,5,Sort.by("id").descending());
        Page<Board> change = boardRepository.findByContentContainingOrderByBoard_idDesc("change", pageable);
       // Page<Board> change = boardRepository.findContentKeyword("change", pageable);

        List<Board> content = change.getContent();
        for(Board board : content){
            System.out.println(board.toString());
        }*/
    }


    @Test
    public void testpage() throws Exception{
        //given
        Pageable pageable = PageRequest.of(0,5,Sort.by("id").descending());
        Page<Board> boardPage = boardRepository.findAll(pageable);
    }

    @Rollback(value = false)
    @Transactional
    @Test
    public void testb() throws Exception{
        //given
        Long bId = 10L;
        Board board = boardRepository.findById(bId).orElseThrow();
        board.change("change..title..","change content...");

        System.out.println(board.toString());
    }

    @Test
    public void tes1t() throws Exception{
        //given

        IntStream.rangeClosed(1,20).forEach(i->{
            Board board = Board.builder()
                    .content("content...."+i)
                    .title("title..."+i)
                    .username("user"+i)
                    .build();

            boardRepository.save(board);
        });


    }


    @Rollback(value = false)
    @Test
    public void test() throws Exception{
        //given
/*        for(int i=3; i<10; i++){
            String email = "test"+i+"@test.com";
            String password = "12341234";
            String address = "testAddress"+i;

            Member member = new Member(email,password,address,passwordEncoder);
            memberRepository.save(member);
        }*/

        String email = "syseoz@naver.com";
        String password = "12341234";

        String address = "testAddress";

        Member member = new Member(email, "user1",password,address,passwordEncoder);
        member.changeRoleByAdmin();
        memberRepository.save(member);

    }

}