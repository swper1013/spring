package com.example.demo.service;

import com.example.demo.dto.BoardDTO;
import com.example.demo.dto.PageRequestDTO;
import com.example.demo.dto.PageResponesDTO;
import com.example.demo.entity.Board;
import com.example.demo.repository.BoardRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class BoardServiceImpl implements BoardService {


    @Value("${boardImgLocation}")
    private String boardImgLocation;        //boardImgLocation의 값은 C:/upload/board


    private final BoardRepository boardRepository;
    private ModelMapper mapper = new ModelMapper();

    private final BimgSerivce bimgSerivce;  //이안에서 db에 저장하기전에 file을 저장
    
    

    @Override
    public void register(BoardDTO boardDTO, MultipartFile multipartFile) {
        log.info("서비스로 들어온 dto : " + boardDTO);
        //등록
        Board board = mapper.map(boardDTO, Board.class).setMemberShip(boardDTO.getMemberShipDTO());
        log.info("서비스에서 변환된  dto > entity : " + board);
        board =  boardRepository.save(board);

         //사진 추가
         //사진의 이름
        //사진의 저장경로  if만약에 동일한 경로에 같은 사진이름 
        //가령 너도 4달라사진 나도 4달라사진 덮어쓰기
        //그래서 해결책 동일한 사진이름이라하더라도
        //매번 다른 문자열이 생성되는 uuid라는 객체를 이용
        //afasdfas_4달라.jpg  qwrwqerwqe_4달라.jpg 파일로 구분
        //사진의 경로, item 사진, board 사진, member 사진 경로를 bimgService 같이 줍니다.
        //사용은 bimgService에서 받은 경로를 FileService 에 던져서 FileService를 재활용합니다.
        
        bimgSerivce.Bimgregister(board, multipartFile, boardImgLocation);//사진과 경로를 줍니다.

    }

    @Override
    public List<BoardDTO> selectAll() {
        List<Board>  boardList = boardRepository.findAll();

        boardList.forEach(board -> log.info(board));

        List<BoardDTO> boardDTOList =
        boardList.stream().map(abc -> mapper.map(abc, BoardDTO.class)
                .setMemberShipDTO(abc.getMemberShip())).collect(Collectors.toList());

        return boardDTOList;
    }

    @Override
    public BoardDTO read(Long bno) {

        Board board =
        boardRepository.findById(bno)
                .orElseThrow(EntityNotFoundException::new); //select * from board  where bno = 파라미터

        BoardDTO boardDTO =  mapper.map(board, BoardDTO.class);
        return boardDTO;
    }

    @Override
    public void update(BoardDTO boardDTO) {

        //트랜잭션이 적용이 되어있어서 엔티티매니져가 관리한다.

        Board board = boardRepository
                .findById(boardDTO.getBno())
                .orElseThrow(EntityNotFoundException::new);

        board.setContent(boardDTO.getContent());

    }

    @Override
    public void delete(Long bno) {
        boardRepository.deleteById(bno);

    }
                                                //size , page, keyword , types

    @Override
    public PageResponesDTO<BoardDTO> list(PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();
        log.info("서비스에서 변환된 :  " + types);

        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("bno");

        Page<Board> boardpage = boardRepository.searchAll(types, keyword, pageable);

        log.info("레포지토리에서 값은 서비스로 받았니?");
        boardpage.getContent().forEach(board ->  log.info(board));
        //보드타입의 리스트가 >>> 보드DTO타입의 리스트로 변환
        List<BoardDTO> boardDTOList =
                boardpage.getContent().stream().map(  board -> mapper.map( board, BoardDTO.class   ).setMemberShipDTO(board.getMemberShip()) ).collect(Collectors.toList());


        log.info("레포지토리받은값 변경은? ");
        boardDTOList.forEach(board ->  log.info(board));

        return PageResponesDTO.<BoardDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(boardDTOList)
                .total(  (int)  boardpage.getTotalElements())
                .build();

    }



}
