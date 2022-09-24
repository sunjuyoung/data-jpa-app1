package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardListAllDto {

    private Long boardId;

    @NotEmpty
    @Size(min = 3,max = 100)
    private String title;

    @NotEmpty
    private String username;

    private Long replyCount;

    private List<BoardImageDto> boardImages;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;


}
