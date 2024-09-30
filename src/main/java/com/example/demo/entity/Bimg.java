package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity //엔티티임을 명시
@Getter
@Setter
@ToString
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모든필드값을 가지고 있는 생성자
public class Bimg extends BaseEntity {
    //pk
    //제목 내용 작성자 작성날짜

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bino;   //사진번호 pk

    private String imgname;     //사진의 이름        //경로를 알고 있고 경로/uuid_사진의오리지널이름.jpg

    private String oriimgname;  //사진의 오리지널 name

    private String img_url;     //사진의 경로    /upload/board  게시글의 사진 저장경로
                                //  /upload/item 게시글의 사진 저장경로

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "bno")       //본문 어디에 달리는 사진인가요? 어떤 아이템에 종속된 사진인가요?
    private Board board;            //어떤 행사의 사진인가요? 어느회원의 프로필 사진인가요?


    //등록일자 혹은 만든이 기타등등이 들어감


}
