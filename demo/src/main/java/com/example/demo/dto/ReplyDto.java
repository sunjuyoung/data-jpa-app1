package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDto {

    private Long id;

    private Long board_id;

    @NotEmpty
    private String replyText;
    @NotEmpty
    private String replyer;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

}
