package com.example.demo.dto;

import com.example.demo.entity.MemberShip;
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
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


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

    private MemberShipDTO memberShipDTO;

    public BoardDTO setMemberShipDTO(MemberShip memberShip) {
        ModelMapper mapper = new ModelMapper();


        this.memberShipDTO = mapper.map(memberShip, MemberShipDTO.class);
        return this;
    }
    public void setMemberShipDTO(MemberShipDTO memberShipDTO) {

        this.memberShipDTO = memberShipDTO;

    }

    private String writer;

    private LocalDate regidate;
    private LocalDate modDate;



}
