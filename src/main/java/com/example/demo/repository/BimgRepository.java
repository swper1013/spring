package com.example.demo.repository;

import com.example.demo.entity.Bimg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BimgRepository extends JpaRepository<Bimg , Long> {

    @Query("select b from Bimg b where b.board.bno= :bno")
    Bimg findfindfindbno(Long bno);


    Bimg findByBoardBno(Long bno);
}
