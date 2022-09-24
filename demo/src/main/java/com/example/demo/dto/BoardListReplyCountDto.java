package com.example.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class BoardListReplyCountDto {

    private Long boardId;

    @NotEmpty
    @Size(min = 3,max = 100)
    private String title;

    @NotEmpty
    private String username;

    private Long replyCount;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;
}
