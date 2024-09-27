package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity //엔티티임을 명시
@Getter
@Setter
@ToString
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모든필드값을 가지고 있는 생성자
public class Board extends BaseEntity {
    //pk
    //제목 내용 작성자 작성날짜

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;   //글번호 pk

    @Column(length = 50, nullable = false)
    private String title;   //제목


//    @Column(name = "con")
    @Lob  //@lob은 일반적인 데이터베이스에서 저장하는
        // 길이인 255개 이상의 문자를 저장하고 싶은때
    @Column(columnDefinition = "text")
    private String content;

    @ManyToOne
    @JoinColumn(name = "mno")
    private MemberShip memberShip;

    private String writer;

    //등록일자 혹은 만든이 기타등등이 들어감


    public Board setTitle(String title) {
        this.title = title;
        return this;
    }
    public Board asdasdad(String title) {
        this.content =  title;
        return this;
    }
}
