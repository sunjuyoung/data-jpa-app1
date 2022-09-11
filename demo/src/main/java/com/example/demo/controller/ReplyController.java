package com.example.demo.controller;

import com.example.demo.dto.ReplyDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/replies")
@RequiredArgsConstructor
public class ReplyController {

    @ApiOperation(value = "Replies POST",notes = "POST 방식으로 댓글 등록")
    @PostMapping(value = "/",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Long>> register(@Valid @RequestBody ReplyDto replyDto, BindingResult bindingResult)
    throws BindException {
        if(bindingResult.hasErrors()){
            throw new BindException(bindingResult);
        }
        Map<String ,Long> result = new HashMap<>();
        result.put("reply_id",111L);
        return ResponseEntity.ok(result);
    }

}
