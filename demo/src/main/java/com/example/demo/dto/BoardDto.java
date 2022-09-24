package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {

    private Long boardId;

    @NotEmpty
    @Size(min = 3,max = 100)
    private String title;

    @NotEmpty
    @Size(min = 3,max = 2000)
    private String content;

    @NotEmpty
    private String username;

    private List<String> fileName;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;
}
