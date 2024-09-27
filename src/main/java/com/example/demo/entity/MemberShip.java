package com.example.demo.entity;


import com.example.demo.constant.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity //엔티티임을 명시
@Getter
@Setter
@ToString
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모든필드값을 가지고 있는 생성자
public class MemberShip extends BaseEntity {
    //pk
    //제목 내용 작성자 작성날짜

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;   //글번호 pk

    @Column(length = 50, nullable = false, unique = true)
    private String email;   //email //unique // not null


    @Column(length = 10, nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;    //이건 dto머 이런거 할때 그리고 저장할때
                                //로그인할때 등 이게 먼저다 
                                //로그인시 시큐리티는 시큐리티에서 지정한
                                // email 과 패스워드는 password라는 변수명으로 받음


    @Enumerated(EnumType.STRING)
    private Role role;  //권한을 위하여

    // dto add1 add2 add3
    private String address;



}
