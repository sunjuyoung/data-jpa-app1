package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "board")
@Builder
@Getter @ToString
@AllArgsConstructor @NoArgsConstructor
public class Board extends BaseTime{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 2000, nullable = false)
    private String content;

    @Column(length = 100, nullable = false)
    private String writer;


    public void change(String title, String content){
        this.title  = title;
        this.content = content;
    }
}
