package com.example.membership.service;

import com.example.membership.constant.Role;
import com.example.membership.dto.MemberShipDTO;
import com.example.membership.entity.MemberShip;
import com.example.membership.repository.MemberShipRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.lang.model.element.UnknownElementException;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class MemberShipService implements UserDetailsService {

    private final MemberShipRepository memberShipRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    //회원가입 :컨트롤러에서 DTO를 입력받아 Entity로 변환하여 repository의 save를 이용해서 저장
    //반환값은 뭐로 할까? : dto전체를 반환으로 하자
    public MemberShipDTO saveMember(MemberShipDTO memberShipDTO){

        //사용자가 있는지 확인
        //가입하려는 email로 이미 사용자가 가입이 되어있는지 확인
        MemberShip memberShip =
        memberShipRepository.findByEmail(memberShipDTO.getEmail());

        if (memberShip !=null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
        memberShip =
        modelMapper.map(memberShipDTO, MemberShip.class);

        //일반유저
        memberShip.setRole(Role.ADMIN);
        //비밀번호를 암호화 헤사 저장한다.
        memberShip.setPassword(passwordEncoder.encode(memberShipDTO.getPassword()));

        memberShip =
        memberShipRepository.save(memberShip);  //저장

        return modelMapper.map(memberShip, MemberShipDTO.class);

    }

    //로그인 //UserDetailsService 구현해서 사용 //Username = email
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //사용자가 입력한 email을 가지고 db에서 검색
        //email과 password를 포함한 entity를 받을 것이다.
        //로그인은 기본적으로 email과 password를 찾거나 email 검색해서 가져온
        //데이터가 null이 아니라면 그안에 password를 가지고 비교해서 맞다면 로그인 성공!!
        log.info("유저디테일 서비스로 들어온 이메일 :"+email); //안들어오면 input창으로 넣은 값이 도달을 못함
        MemberShip memberShip=
        this.memberShipRepository.findByEmail(email);
        //이메일 검색시 가져온 값이 없다면, 회원가입 실패
        // try-catch문으로 다른 화면으로 멀리던 메시지를 가지고 로그인창으로 보내면 컨트롤러창에서 아러서 해준다
        //미래의 내가
        if(memberShip == null){
            throw new  UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        //예외처리가 되지 않았다면
        log.info("현재 찾은 회원정보"+memberShip);

        //권한 처리
        String role = "";
        if ("ADMIN".equals(memberShip.getRole().name())){
            log.info("관리자");
            role = Role.ADMIN.name();
        }else {
            log.info("일반유저");
            role = Role.user.name();
        }

        return User.builder()
                .username(memberShip.getEmail())
                .password(memberShip.getPassword()) //<input name = "password">
                .roles(role)
                .build();
    }


}
