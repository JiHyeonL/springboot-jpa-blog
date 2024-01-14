package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// 컨트롤러 어노테이션 -> 파일을 리턴
// restController -> 문자열 그 자체를 리턴
@Controller
public class TempControllerTest {

    // http://localhost:8000/blog/temp/home
    @GetMapping("/temp/home")
    public String tempHome() {
        System.out.println("tempHome()");
        // 파일 리턴 기본경로" src/main/resources/static에 있는 파일을 리턴
        // 그래서 리턴명을 home.html이 아닌 /home.html 이라고 해야 인식한다.
        // 풀 경로: src/main/resources/static/home.html
        return "/home.html";
    }

    @GetMapping("/temp/img")
    public String tempImg() {
        return "static/a.png";
    }

}
