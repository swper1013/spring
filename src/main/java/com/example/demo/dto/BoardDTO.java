package com.example.demo.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;


@Getter
@Setter
@ToString
public class BoardDTO {
    private Long bno;   //글번호 pk


    @NotNull
    @Size(min = 2, max = 50)
    private String title;   //제목


    @NotEmpty
    private String content;


    private String writer;

    private LocalDate regidate;
    private LocalDate modDate;


}
