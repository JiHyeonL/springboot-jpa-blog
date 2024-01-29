package com.cos.blog.controller;

import com.cos.blog.config.auth.PrincipalDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

//    @Autowired
//    private PrincipalDetail principal;

    @GetMapping({"","/"})
    public String index(@AuthenticationPrincipal PrincipalDetail principal) { // @AuthenticationPrincipal PrincipalDetail principal
        System.out.println("로그인 사용자 아이디: " + principal.getUsername());
        return "index";
    }
}
