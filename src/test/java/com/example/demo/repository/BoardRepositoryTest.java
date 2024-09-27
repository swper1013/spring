package com.example.demo.repository;

import com.example.demo.entity.Board;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;


    @Test
    public void aa(){
        //등록

        Board board = new Board();
        board.setContent("ss");
        board.setWriter("ss");
        board.setTitle("ss");

        boardRepository.save(board);
        boardRepository.findById(1L);
    }

}