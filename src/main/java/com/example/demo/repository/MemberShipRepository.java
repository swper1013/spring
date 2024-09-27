package com.example.demo.repository;

import com.example.demo.entity.MemberShip;
import com.example.demo.repository.search.MemberSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberShipRepository extends JpaRepository<MemberShip , Long>, MemberSearch {


    MemberShip findByEmail (String email);


    @Query("select m from MemberShip m where m.email =:email")
    MemberShip ddd (String email);


}
