package com.example.demo.dto;

import com.example.demo.entity.Board;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BimgDTO {

    private Long bino;   //사진번호 pk

    private String imgname;     //사진의 이름        //경로를 알고 있고 경로/uuid_사진의오리지널이름.jpg

    private String oriimgname;  //사진의 오리지널 name

    private String img_url;     //사진의 경로    /upload/board  게시글의 사진 저장경로
    //  /upload/item 게시글의 사진 저장경로

    private BoardDTO boardDTO;  //modelMapper를 이용해서 entity로 변환할 예정<->





}
