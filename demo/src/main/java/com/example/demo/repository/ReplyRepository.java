package com.example.demo.repository;


import com.example.demo.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReplyRepository extends JpaRepository<Reply,Long>, ReplyCustomRepository {

    @Query("select r from Reply r where r.board.board_id = :board_id")
    Page<Reply> listOfBoard(Long board_id,Pageable pageable);



}
