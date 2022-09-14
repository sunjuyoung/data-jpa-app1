package com.example.demo.service;

import com.example.demo.dto.PageRequestDto;
import com.example.demo.dto.PageResponseDto;
import com.example.demo.dto.ReplyDto;
import com.example.demo.entity.Board;
import com.example.demo.entity.Reply;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{

    private final ReplyRepository replyRepository;
    private final ModelMapper modelMapper;
    private final BoardRepository boardRepository;


    @Override
    public Long register(ReplyDto replyDto) {
        Reply reply = modelMapper.map(replyDto, Reply.class);
        Long reply_id = replyRepository.save(reply).getReply_id();
        return reply_id;
    }

    @Transactional
    @Override
    public Long modify(ReplyDto replyDto) {
        Reply reply = replyRepository.findById(replyDto.getReply_id()).orElseThrow();
        reply.changeText(replyDto.getReplyText());
        return reply.getReply_id();
    }

    @Override
    public void remove(Long reply_id) {
        replyRepository.deleteById(reply_id);
    }

    @Override
    public PageResponseDto<ReplyDto> listOfBoard(Long board_id, PageRequestDto pageRequestDto) {
       // Board board = boardRepository.findById(board_id).orElseThrow();
        Pageable pageable = pageRequestDto.getPageable("reply_id");
        List<ReplyDto> listOfBoard = replyRepository.getListOfBoard(board_id,pageable);
        return PageResponseDto.<ReplyDto>withAll()
                .pageRequestDto(pageRequestDto)
                .dtoList(listOfBoard)
                .build();
    }
}
