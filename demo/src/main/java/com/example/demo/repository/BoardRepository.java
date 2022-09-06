package com.example.demo.repository;

import com.example.demo.entity.Board;
import com.example.demo.repository.search.BoardSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board,Long> , BoardSearch {

    Page<Board> findByContentContainingOrderByIdDesc(String content, Pageable pageable);

    @Query("select b from Board b where b.content like concat('%', :content, '%') ")
    Page<Board> findContentKeyword(String content,Pageable pageable);


}
