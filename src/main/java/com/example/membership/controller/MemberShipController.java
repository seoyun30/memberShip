package com.example.membership.controller;

import com.example.membership.dto.MemberShipDTO;
import com.example.membership.entity.MemberShip;
import com.example.membership.service.MemberShipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/user")
public class MemberShipController {

    private final MemberShipService memberShipService;

    @GetMapping("/signup")
    public String signup(MemberShipDTO memberShipDTO){
        //파라미터는 유효성검사를 하면서 다시 보줄거라서

        return "user/signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid MemberShipDTO memberShipDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){
        log.info("컨트롤러로 들어옴"+memberShipDTO);

        if (bindingResult.hasErrors()){
            log.info("컨트롤러_유효성 검사로 들어옴"+memberShipDTO);
            return "user/signup";
        }

        try {
            memberShipDTO=
                    memberShipService.saveMember(memberShipDTO);

        }catch (IllegalStateException e){
            log.info("동일한 이메일이 있습니다.");
            log.info("동일한 이메일이 있습니다.");
            log.info("동일한 이메일이 있습니다.");
            log.info("동일한 이메일이 있습니다.");
            log.info("동일한 이메일이 있습니다.");
            model.addAttribute("msg",e.getMessage());
            return "user/signup";
        }

        redirectAttributes.addFlashAttribute("memberShipDTO",memberShipDTO);
        return "redirect:/user/signup";
    }

    @GetMapping("/login")
    public String login(MemberShipDTO memberShipDTO, Principal principal ){

        if (principal != null){//principal 로그인이 되었을때 값을 가지되 됨
            //현재는 email을 username로 가졌기 때문에 로그인한 email을 가지고 있움.
            log.info("====================================");
            log.info("||"+principal.getName()+"||");
            log.info("||"+principal.getName()+"||");
            log.info("||"+principal.getName()+"||");
            log.info("||"+principal.getName()+"||");
            log.info("====================================");

//            return  "redirect:/board/list";

        }

        return "user/login";
    }

    // @PostMapping("/login") 시큐리티가함



}
