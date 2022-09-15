package com.example.demo.service;

import com.example.demo.dto.PageRequestDto;
import com.example.demo.dto.PageResponseDto;
import com.example.demo.dto.ReplyDto;

public interface ReplyService {

    Long register(ReplyDto replyDto);

    Long modify(ReplyDto replyDto,Long reply_id);

    void remove(Long reply_id);

    PageResponseDto<ReplyDto> listOfBoard(Long board_id, PageRequestDto pageRequestDto);

    ReplyDto getReply(Long reply_id);

}
