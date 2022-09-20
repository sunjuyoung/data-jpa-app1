package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "board")
@Builder
@Getter
@ToString(exclude = "imageSet")
@EqualsAndHashCode(exclude = "imageSet")
@AllArgsConstructor
@NoArgsConstructor
public class Board extends BaseTime{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 2000, nullable = false)
    private String content;

    @Column(length = 100, nullable = false)
    private String username;


    @OneToMany(mappedBy = "board",
                cascade = {CascadeType.ALL},
                orphanRemoval = true)
    @Builder.Default
    private Set<BoardImage> imageSet = new HashSet<>();

    public void change(String title, String content){
        this.title  = title;
        this.content = content;
    }

    public void addImage(String uuid, String fileName){
        BoardImage boardImage = BoardImage.builder()
                .fileName(fileName)
                .uuid(uuid)
                .board(this)
                .ord(imageSet.size())
                .build();
        imageSet.add(boardImage);
    }

    public void clearImage(){
        imageSet.forEach(boardImage -> boardImage.changeBoard(null));
        this.imageSet.clear();
    }

}
