package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board")
@Builder
@Getter @ToString
@AllArgsConstructor @NoArgsConstructor
public class Board extends BaseTime{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long board_id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 2000, nullable = false)
    private String content;

    @Column(length = 100, nullable = false)
    private String username;



    public void change(String title, String content){
        this.title  = title;
        this.content = content;
    }
}
