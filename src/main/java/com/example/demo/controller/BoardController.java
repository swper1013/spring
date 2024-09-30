package com.example.demo.controller;


import com.example.demo.dto.*;
import com.example.demo.entity.Board;
import com.example.demo.entity.MemberShip;
import com.example.demo.repository.search.BoardSearch;
import com.example.demo.service.BimgSerivce;
import com.example.demo.service.BoardService;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/board")

public class BoardController {
    //서비스들 가져오기 그리고 필요하다면 user등등 다
    private  final BoardService boardService;
    // 댓글도
    //이미지도
    private final MemberShipService memberShipService;

    private final BimgSerivce bimgSerivce;

    @GetMapping("/register")
    public  void register(BoardDTO boardDTO){
        //html에서 object를 사용하기 위해서 thymeleaf
        log.info("등록get 진입");




    }

    @PostMapping("/register")
    public  String registerPost(@Valid BoardDTO boardDTO, BindingResult bindingResult, Model model ,
                                Principal principal  , MultipartFile multipartFile
                                ){  //파라미터 리다이렉트 쓸때 추가 : RedirectAttributes redirectAttributes

        log.info("파라미터로 입력된 : " +boardDTO);

        if(multipartFile != null){

           // try {
                log.info("사진 이름 : " +multipartFile.getOriginalFilename() );
                log.info("사진 크기 : " +multipartFile.getSize() );
                //log.info("사진의 byte : " + Arrays.toString(multipartFile.getBytes()));
//            } catch (IOException ioException) {
//                System.out.println("너님 사진 오류");
//            }



        }

        if(bindingResult.hasErrors()){ //유효성검사간 에러가 있니?

            log.info("에러");
            log.info("에러");
            log.info("에러");
            log.info("에러");
            log.info(bindingResult.getAllErrors()); //유효성검사에 대한 결과
            //redirectAttributes.addAttribute("errors", bindingResult.getAllErrors());
//            model.addAttribute("boardDTO" , boardDTO);
            return "board/register";

        }

        MemberShipDTO memberShipDTO = memberShipService.findbyEmail(principal.getName());

        boardDTO.setMemberShipDTO(memberShipDTO);

        boardService.register(boardDTO, multipartFile);

        return "redirect:/board/list";
    }

    @GetMapping("/list")
    public String list(Model model, PageRequestDTO pageRequestDTO) {
        log.info("pageR : " + pageRequestDTO);
//
//        List<BoardDTO> boardList = boardService.selectAll();
//
//        model.addAttribute("list", boardList);

     PageResponesDTO<BoardDTO>  boardDTOPageResponesDTO
             =  boardService.list(pageRequestDTO);

     //boardDTOPageResponesDTO.getDtoList().forEach(boardDTO -> log.info(boardDTO));

     model.addAttribute("boardDTOPageResponesDTO", boardDTOPageResponesDTO);

     return  "board/list";
    }

    @GetMapping("/read")
    public String readOne(Long bno, Model model){

        BoardDTO boardDTO =   boardService.read(bno);

        //본문에 달린 이미지를 가져온다.
        BimgDTO bimgDTO = bimgSerivce.read(bno);



        model.addAttribute("boardDTO",boardDTO);
        model.addAttribute("bimgDTO",bimgDTO);




        return "board/read";
    }

    @GetMapping("/modify")
    public String modify(Long bno, Model model){

        //수정할 내용을 찾아서 뿌려줍니다.
        model.addAttribute("boardDTO", boardService.read(bno));


        return "board/modify";
    }

    @PostMapping("/modify")
    public String modify(@Valid BoardDTO boardDTO, BindingResult bindingResult
    ){

        log.info(boardDTO);

        if(bindingResult.hasErrors()) { //유효성검사간 에러가 있니?

            log.info("에러");
            log.info("에러");
            log.info("에러");
            log.info("에러");
            log.info(bindingResult.getAllErrors()); //유효성검사에 대한 결과
            //redirectAttributes.addAttribute("errors", bindingResult.getAllErrors());
//            model.addAttribute("boardDTO" , boardDTO);
            return "board/modify";
        }

            //수정할 내용을 찾아서 뿌려줍니다.

        boardService.update(boardDTO);

        return "redirect:/board/list";
    }












}
