package com.example.demo.dto.upload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadResponseDto {

    private String uuid;
    private String fileName;
    private boolean img;

    //JSON으로 처리될 때 link라는 속성으로 자동처리
    public String getLink(){
        if(img){
            return "s_"+uuid+"_"+fileName;
        }else {
            return uuid+"_"+fileName;
        }
    }
}
