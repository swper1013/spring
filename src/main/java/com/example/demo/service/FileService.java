package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class FileService {

    public String uploadFile(MultipartFile multipartFile ,
                             String fileUploadFullUrl){
        

        Path path = Path.of(fileUploadFullUrl);// 여기에 저장할꺼야
                                            //경로가 있어야한다.

        try {
            multipartFile.transferTo(path);  //저장

        } catch (IOException ioExceptions) {
            System.out.println("너님 뭔가사진에서 잘못된듯");
        }



        return null;

    }


}
