package com.example.demo.service;

import com.example.demo.dto.BoardDTO;
import com.example.demo.dto.PageRequestDTO;
import com.example.demo.dto.PageResponesDTO;
import com.example.demo.entity.Board;

import java.util.List;

public interface BoardService {

    public void register(BoardDTO boardDTO);

    public List<BoardDTO> selectAll();

    public BoardDTO read(Long bno);

    public void update(BoardDTO boardDTO);

    public void delete(Long bno);

    //페이징처리, 검색처리 한 목록

    public PageResponesDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);



}
