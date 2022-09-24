package com.example.demo.entity;

import com.example.demo.dto.ReplyDto;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Entity
@Table(name = "reply"
/*        ,indexes = {
        @Index(name = "idx_reply_boardId", columnList = "board_boardId")}*/
        )
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "board")
public class Reply extends BaseTime{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    private String replyText;

    private String username;

    public void changeText(String replyText){
        this.replyText = replyText;
    }


}
