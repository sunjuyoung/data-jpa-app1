package com.example.demo.controller;

import com.example.demo.dto.PageRequestDto;
import com.example.demo.dto.PageResponseDto;
import com.example.demo.dto.ReplyDto;
import com.example.demo.service.ReplyService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/replies")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @ApiOperation(value = "Replies POST",notes = "POST 방식으로 댓글 등록")
    @PostMapping(value = "/",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Long>> register(@Valid @RequestBody ReplyDto replyDto, BindingResult bindingResult)
    throws BindException {
        if(bindingResult.hasErrors()){
            throw new BindException(bindingResult);
        }
        Long id = replyService.register(replyDto);
        Map<String ,Long> result = new HashMap<>();
        result.put("reply_id",id);
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "Replies of Board",notes = "GET 방식으로 게시글 댓글 조회")
    @GetMapping(value = "/list/{board_id}")
    public ResponseEntity<PageResponseDto<ReplyDto>> getList(@PathVariable("board_id")Long board_id,
                                                    PageRequestDto pageRequestDto){
        PageResponseDto<ReplyDto> responseDto = replyService.listOfBoard(board_id, pageRequestDto);
        return ResponseEntity.ok().body(responseDto);
    }

    @ApiOperation(value = "Replies Get",notes = "GET 방식으로 특정 댓글 조회")
    @GetMapping(value = "/{reply_id}")
    public ResponseEntity<ReplyDto> getReply(@PathVariable("reply_id")Long reply_id,
                                                             PageRequestDto pageRequestDto){
        ReplyDto replyDto = replyService.getReply(reply_id);
        return ResponseEntity.ok().body(replyDto);
    }

    @ApiOperation(value = "Replies Delete",notes = "DELETE 방식으로 특정 댓글 삭제")
    @DeleteMapping(value = "/{reply_id}")
    public ResponseEntity<Map<String,Long>> deleteReply(@PathVariable("reply_id")Long reply_id,
                                             PageRequestDto pageRequestDto){
         replyService.remove(reply_id);
        Map<String ,Long> result = new HashMap<>();
        result.put("reply_id",reply_id);
        return ResponseEntity.ok().body(result);
    }


    @ApiOperation(value = "Replies modify",notes = "PUT 방식으로 특정 댓글 수정")
    @PutMapping(value = "/{reply_id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Long>> getReply(@Valid @RequestBody ReplyDto replyDto, BindingResult bindingResult,
                                             PageRequestDto pageRequestDto,@PathVariable("reply_id")Long reply_id)
                                                throws BindException {

        if(bindingResult.hasErrors()){
            throw new BindException(bindingResult);
        }
        Long id = replyService.modify(replyDto,reply_id);
        Map<String ,Long> result = new HashMap<>();
        result.put("reply_id",reply_id);

        return ResponseEntity.ok().body(result);
    }




}
