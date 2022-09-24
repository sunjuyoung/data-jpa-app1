package com.example.demo.repository;

import com.example.demo.dto.BoardListAllDto;
import com.example.demo.dto.BoardListReplyCountDto;
import com.example.demo.entity.Board;
import com.example.demo.entity.Member;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
@SpringBootTest
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ReplyRepository replyRepository;



    @Test
    @Transactional
    public void test3() throws Exception{
        //given
        Pageable pageable = PageRequest.of(0,5,Sort.by("id").descending());
        //Page<BoardListAllDto> boardListAllDtos = boardRepository.searchWithAll(null, null, pageable);

        Page<BoardListAllDto> boardListAllDtos = boardRepository.searchWithAllByProjection(null, null, pageable);

        log.info(boardListAllDtos.getTotalElements());

        boardListAllDtos.getContent().forEach(boardListAllDto -> log.info(boardListAllDto));


        //when

        //then
    }

    @Test
    @Transactional
    @Commit
    public void deleteReply() throws Exception{
        //given
        Long board_id = 18L;
        replyRepository.deleteByBoardId(board_id);
        boardRepository.deleteById(board_id);

        //when

        //then
    }



    @Transactional
    @Rollback(value = false)
    @Test
    public void getWithImage() throws Exception{
        //given

        Optional<Board> byBoard_idWithImage = boardRepository.findByIdWithImages(19L);
        Board board = byBoard_idWithImage.orElseThrow();

        //board.clearImage();

        for(int i=1; i<5; i++){
            board.addImage(UUID.randomUUID().toString(),"tsetFile_"+i+".jpg");

        }
        boardRepository.save(board);

        //when

        //then
    }

    @Test
    public void testImage() throws Exception{
        //given
        Board board = Board.builder()
                .content("content....")
                .title("test image")
                .username("user1")
                .build();

        board.addImage(UUID.randomUUID().toString(),"tsetFile_1.jpg");

        boardRepository.save(board);

        //when

        //then
    }


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

        IntStream.rangeClosed(1,44).forEach(i->{
            Board board = Board.builder()
                    .content("content...."+i)
                    .title("title..."+i)
                    .username("user"+i)
                    .build();

            board.addImage(UUID.randomUUID().toString(),"testImage_"+i+".jpg");
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