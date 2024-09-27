package com.example.demo.controller;


import com.example.demo.dto.BoardDTO;
import com.example.demo.dto.MemberShipDTO;
import com.example.demo.dto.PageRequestDTO;
import com.example.demo.dto.PageResponesDTO;
import com.example.demo.service.MemberShipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/user")
public class MembershipController {

    private final MemberShipService memberShipService;

    //회원가입
    //회원가입을 하려면 회원가입 페이지를 사용자에게 보여줘야한다.
    //회원가입페이지를 작성후 돌려주는것은 post로 받는다.

    @GetMapping("/register")
    public String signup(MemberShipDTO memberShipDTO){



        return "user/register";
    }


    @PostMapping("/register")
    public String ass(@Valid  MemberShipDTO memberShipDTO,
                    BindingResult bindingResult, Model model){
        log.info("회원가입get에서 포스트로 넘어온값 : " + memberShipDTO);
        if (bindingResult.hasErrors()) {
            log.info("너님 뭐 잘못함 ");
            log.info(bindingResult.getAllErrors()); //에러도 확인하고

            //아무것도 안해도 html로 빈(membershipDTOㄱ 간다.
            //object를 해주면 됐을껄?
            return "user/register";
        }

        try {
            memberShipService.register(memberShipDTO);
        }catch (IllegalStateException e){

            log.info(e.getMessage());
            model.addAttribute("msg", e.getMessage());
            return "user/register";
        }


        //저장을 합니다.

        return "redirect:/user/login";
    }
    
    //로그인 get   , post 는 지가 알아서

    @GetMapping("/login")
    public String login(){

        return "user/login";
    }
    
    //회원정보보기  내꺼만~~principal
    
    //회원정보보기 관리자용 //회원목록에서 갈꺼
    
    
    //회원정보수정 (나만수정꺼임 , principal)

    
    //회원목록, 관리자만 볼꺼임
    @GetMapping("/list")
    public String list(Model model, PageRequestDTO pageRequestDTO) {
        log.info("pageR : " + pageRequestDTO);


        PageResponesDTO<MemberShipDTO> pageResponesDTO
                =  memberShipService.list(pageRequestDTO);

        //boardDTOPageResponesDTO.getDtoList().forEach(boardDTO -> log.info(boardDTO));

        model.addAttribute("pageResponesDTO", pageResponesDTO);

        return  "user/list";
    }




    //회원탈퇴, 1. 관리자가 강퇴, 2. 내가 탈퇴(principal)
}
