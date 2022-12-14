package com.example.demo.controller;

import com.example.demo.dto.upload.UploadFileDto;
import com.example.demo.dto.upload.UploadResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@Log4j2
public class UploadController {

    @Value("${upload.path}")
    private String uploadPath;


    @ApiOperation(value = " 파일 삭제")
    @DeleteMapping("/remove/{fileName}")
    public ResponseEntity<Map<String,Boolean>> removeFile(@PathVariable String fileName){

        Resource resource = new FileSystemResource(uploadPath+File.separator + fileName);
        String resourceName = resource.getFilename();
        Map<String,Boolean> resultMap = new HashMap<>();
        boolean removed = false;

        try{
            String contentType =  Files.probeContentType( resource.getFile().toPath());
            removed = resource.getFile().delete();

            //썸네일확인
            if(contentType.startsWith("image")){
                File thumbnailFile = new File(uploadPath+File.separator+"s_"+fileName);
                thumbnailFile.delete();
            }

        } catch(Exception e){
            log.error(e.getMessage());
        }
        resultMap.put("result",removed);
        return ResponseEntity.ok().body(resultMap);
    }

    @ApiOperation(value = "view 파일")
    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGet(@PathVariable String fileName){

        Resource resource = new FileSystemResource(uploadPath+File.separator + fileName);
        String resourceName = resource.getFilename();
        HttpHeaders headers = new HttpHeaders();

        try{
            headers.add("Content-Type", Files.probeContentType( resource.getFile().toPath() ));
        } catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().headers(headers).body(resource);
    }

    @ApiOperation(value = "upload POST")
    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<UploadResponseDto> upload(UploadFileDto uploadFileDto){

        if(uploadFileDto.getFiles() != null){

            List<UploadResponseDto> list = new ArrayList<>();

            uploadFileDto.getFiles().forEach(multipartFile -> {
                String originalName = multipartFile.getOriginalFilename();
                String uuid = UUID.randomUUID().toString();
                Path savePath = Paths.get(uploadPath, uuid+"_"+originalName);
                boolean img = false;
                try {
                    multipartFile.transferTo(savePath);
                    //이미지 파일확인 후 썸네일 생성
                    if(Files.probeContentType(savePath).startsWith("image")){
                        img = true;
                        File thumbFile = new File(uploadPath,"s_"+uuid+"_"+originalName);
                        Thumbnailator.createThumbnail(savePath.toFile(),thumbFile,200,200);
                    }
                    
                }catch (IOException e){
                    e.printStackTrace();
                }
                list.add(UploadResponseDto.builder()
                        .fileName(originalName)
                        .img(img)
                        .uuid(uuid)
                        .build());
            });
            return list;
        }

        return null;
    }


}
