package com.example.membership.dto;

import com.example.membership.constant.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberShipDTO {


    private Long num;       //회원읽기등을 볼때 pk값 가지고 있어야함
                            //등록시에는 필요없으니까 notnull같은거 안씀
    @NotBlank(message = "이름을 작성 하시오.")
    @Size(min = 2, max = 10, message = "2글자 에서 10글자 이내로 작성하시오.ex)홍길동")
    private String name; //이름  //낫 블링크 최소 2, 최대 10

    @Email(message = "올바른 형식이 아닙니다.")
    @NotBlank(message = "빈문자, 공백, 빈값을 허용하지 않습니다.")
    @Size(max = 50, message = "최대 50글자를 넘지 마세요")
    private String email; //이메일  //email, 최대값은 20, 글자: 메시지: 최대50글자 이내로 작성

    @NotBlank
    @Size(min = 10, max = 16, message = "최소 10에서 최대 16이내로 비밀번호를 작성하시오")
    private String password; //비밀번호  //낫 블링크 //최소 : 10, 최대 16 메시지 : 최소 8에서 최대 16 이내로 비밀번호 작성하세요

    @NotBlank(message = "주소를 작성 하시오.")
    private String address; //주소  //낫 블링크

    //권한
    private Role role;  //이건 어차피 유저디테일서비스에서 직접 넣어볼예정 //필요에 따라서 어드민 가입페이와
                        //일반유저가 가입페이지가 분리될 수 있음

}
