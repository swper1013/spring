package com.example.demo.service;

import com.example.demo.constant.Role;
import com.example.demo.dto.BoardDTO;
import com.example.demo.dto.MemberShipDTO;
import com.example.demo.dto.PageRequestDTO;
import com.example.demo.dto.PageResponesDTO;
import com.example.demo.entity.Board;
import com.example.demo.entity.MemberShip;
import com.example.demo.repository.MemberShipRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class MemberShipService  implements UserDetailsService {

    private  final  MemberShipRepository memberShipRepository;
    private ModelMapper mapper = new ModelMapper();

    private final PasswordEncoder passwordEncoder;


    public MemberShipDTO findbyEmail(String email){
        MemberShip memberShip = memberShipRepository.findByEmail(email);

        //dto변환


        return mapper.map(memberShip, MemberShipDTO.class);

    }

    
    //회원가입
    public void register(MemberShipDTO memberShipDTO){
        //회원가입시 dto로 넘겨준 값을 받는다.
        log.info(memberShipDTO);
        //회원이 있는지 확인
        MemberShip memberShip = memberShipRepository.findByEmail(memberShipDTO.getEmail());

        if(memberShip !=null){
                log.info("이미 가입된 회원");
                throw new IllegalStateException("이미 가입된 회원입니다.");
        }

        log.info("가입된 회원이 없어서 회원가입 가능");

        MemberShip entity = mapper.map(memberShipDTO, MemberShip.class);
        entity.setPassword(passwordEncoder.encode(memberShipDTO.getPassword()));
        entity.setRole(Role.USER);
//        entity.setRole(Role.ADMIN);
        memberShipRepository.save(entity);  //저장 가입

    }
    
    
    
    //로그인
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        //repository에서 이메일로 값을 검색해서 있는 없는지 확인합니다.
        //왜 이메일은 유니크이니까 유일해야하니까 그리고 이미 가입된 회원인지를 확인해야 하니까


        log.info("회원가입서비스로 들어온 : " +email);
        MemberShip memberShip = memberShipRepository.findByEmail(email);

        if(memberShip ==null){
            throw  new UsernameNotFoundException("사용자를 찾을 수 없습니다." + email);
        }

        log.info( " 현재 로그인한사람 권한 " + memberShip.getRole());

        String role = "";
        if("ADMIN".equals(memberShip.getRole().name())){ //관리자라면
            log.info("관리자");
            role = Role.ADMIN.name();
        } else { //일반유저
            log.info("일반유저");
            role = Role.USER.name();
        }

        return User.builder()
                .username(memberShip.getEmail())
                .password(memberShip.getPassword())
                .roles(role)
                .build();
    }

    //목록 //관리자만 볼꺼임
    public PageResponesDTO<MemberShipDTO> list(PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();
        log.info("서비스에서 변환된 :  " + types);

        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("mno");

        Page<MemberShip> memberShipPage = memberShipRepository.searchAll(types, keyword, pageable);

        log.info("레포지토리에서 값은 서비스로 받았니?");
        //memberShipPage.getContent().forEach(board ->  log.info(board));
        //보드타입의 리스트가 >>> 보드DTO타입의 리스트로 변환
        List<MemberShipDTO> memberShipDTOList =
                memberShipPage.getContent().stream().map(  a -> mapper.map( a, MemberShipDTO.class   ) ).collect(Collectors.toList());


        log.info("레포지토리받은값 변경은? ");
        //memberShipDTOList.forEach(board ->  log.info(board));

        return PageResponesDTO.<MemberShipDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(memberShipDTOList)
                .total(  (int)  memberShipPage.getTotalElements())
                .build();

    }
    
    
    
}
