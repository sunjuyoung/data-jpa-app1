package com.example.demo.repository.search;

import com.example.demo.dto.BoardListAllDto;
import com.example.demo.dto.BoardListReplyCountDto;
import com.example.demo.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardSearch {

    Page<Board> search1(Pageable pageable);

    Page<Board> searchAll(String[] types, String keyword, Pageable pageable);

    Page<BoardListReplyCountDto> searchWithReplyCount(String[] types, String keyword, Pageable pageable);

    Page<BoardListAllDto> searchWithAll(String[] types, String keyword, Pageable pageable);

    Page<BoardListAllDto> searchWithAllByProjection(String[] types, String keyword, Pageable pageable);
}
