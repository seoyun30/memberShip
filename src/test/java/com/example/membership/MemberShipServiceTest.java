package com.example.membership;

import com.example.membership.dto.MemberShipDTO;
import com.example.membership.service.MemberShipService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberShipServiceTest {

    @Autowired
    MemberShipService memberShipService;

    @Test
    public void signupTest(){
        //회원가입을 위해 필요한 dto
        //ID: 자동증감
        //email : sin@a.a
        //name : 신짱구
        //pw : 1234
        //add : 경남 사이타마현

        MemberShipDTO memberShipDTO
                =MemberShipDTO.builder()
                .email("sinA@a.a").name("신짱아").password("5678911").address("경남 사이타마현").build();
        try {
        MemberShipDTO memberShipDTO1=
                memberShipService.saveMember(memberShipDTO);
        System.out.println(memberShipDTO1);
        } catch (IllegalStateException e) {
            System.out.println("zzzzz");
            System.out.println("zzzzz");
            System.out.println("zzzzz");
            System.out.println("zzzzz");
            System.out.println(e.getMessage());
            System.out.println(e.getMessage());
            System.out.println(e.getMessage());
            System.out.println(e.getMessage());
            System.out.println(e.getMessage());
        }

    }

}
