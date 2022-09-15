package com.example.demo.repository;

import com.example.demo.dto.ReplyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReplyCustomRepository {

    Page<ReplyDto> getListOfBoard(Long board_id, Pageable pageable);

    Page<ReplyDto> list( Pageable pageable);
}
