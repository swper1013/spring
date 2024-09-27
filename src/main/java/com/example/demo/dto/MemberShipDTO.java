package com.example.demo.dto;

import com.example.demo.constant.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberShipDTO {


    private Long mno;

    @Email(message = "이메일형식을 몰라?")
    @NotEmpty(message = "이메일이 없어?")
    private String email;

    @NotEmpty(message = "이름이 없어?")
    @Length(min = 1, max = 20)
    private String name;

    @NotEmpty(message = "패스워드는? 어따팔아먹음?")
    @Length(min = 2, max = 20)
    private String password;

    private Role role;  //권한을 위하여


    private String address;


}
