package com.example.demo.service;

import com.example.demo.dto.BimgDTO;
import com.example.demo.entity.Bimg;
import com.example.demo.entity.Board;
import com.example.demo.repository.BimgRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class BimgSerivce {


    private final FileService fileService;
    private final BimgRepository bimgRepository;
    private ModelMapper mapper = new ModelMapper();

    public void Bimgregister(Board board, MultipartFile multipartFile,
                             String boardImgLocation){

        //사진과 경로를 입력받습니다.
        //실제 사진을 저장
        UUID uuid = UUID.randomUUID();  //adfadfs-adfsasdf-afsdaf
        String originalFilename =multipartFile.getOriginalFilename();
        String newSaveName = originalFilename
                .substring( originalFilename.lastIndexOf("/")+1);
        String fileUploadFullUrl = boardImgLocation + "/" + uuid.toString()
                + "_" + newSaveName;


        //하드웨어에 저장
        fileService.uploadFile(multipartFile, fileUploadFullUrl);
        
        //db에 경로및 이름 저장
        Bimg bimg = new Bimg();
        bimg.setBoard(board);       //부모 세팅
        bimg.setImgname(uuid.toString()+ "_" + newSaveName);
        bimg.setImg_url(boardImgLocation);
        bimg.setOriimgname(newSaveName);

        bimgRepository.save(bimg);


    }

    public BimgDTO read(Long bno){      //몇번본문에 달린 이미지냐
        // select * from bimg where bno(본문) = bno

        //실제파일을 가져온다. x
        //db의 데이터를 가져온다.
        Bimg bimg = bimgRepository.findfindfindbno(bno);

        log.info("본문에 달린 이미지 : " +bimg);
        BimgDTO bimgDTO = mapper.map(bimg, BimgDTO.class);
        log.info("본문에 달린 이미지 : " +bimgDTO);
        return bimgDTO;
    }
}
