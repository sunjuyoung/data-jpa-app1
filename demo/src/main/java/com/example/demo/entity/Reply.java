package com.example.demo.entity;

import com.example.demo.dto.ReplyDto;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Entity
@Table(name = "reply",indexes = {
        @Index(name = "idx_reply_board_id", columnList = "board_id")
})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "board")
public class Reply extends BaseTime{

    @Column(name = "reply_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reply_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private String replyText;

    private String username;

    public void changeText(String replyText){
        this.replyText = replyText;
    }


}
