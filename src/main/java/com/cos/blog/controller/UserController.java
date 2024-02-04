package com.cos.blog.controller;

import com.cos.blog.config.auth.PrincipalDetail;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

// 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 로 허용해줌
// 그냥 주소가 / 이면 index.jsp 허용
// static 이하에 있는 /js/**, /cs, /image
@Controller
public class UserController {

    @GetMapping("/auth/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @GetMapping("/auth/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    // ResponseBody를 붙였기 때문에 이 메소드는 Data를 리턴해준다.
    @GetMapping("/auth/kakao/callback")
    public @ResponseBody String kakaoCallback(String code) {    // 쿼리 String 값(예: ?code=어쩌구)은 메소드의 파라미터로 가져올 수 있다.
        // POST 방식으로 key=value 데이터를 요청(카카오쪽으로)
        // Retrofit2(안드로이드에서 자주사용하는 라이브러리)
        // OkHttp
        // RestTemplate(지금 쓰고있는 라이브러리) 가 있다.
        RestTemplate restTemplate = new RestTemplate();
        // 헤더 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        // 헤더에 key=value 타입이라고 알려줌.
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "1828e95a6a08b52ce8b8bac568da4b05");
        params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
        params.add("code", code);

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        // Http 요청하기 - Post 방식으로 & response 변수의 응답 받음.
        ResponseEntity<String> response = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        return "카카오 토큰 요청 완료, 토큰 요청에 대한 응답 : " + response;
    }

    // @AuthenticationPrincipal 을 사용하면 Authentication 객체를 가져온다.
    @GetMapping("/user/updateForm")
    public String updateForm(@AuthenticationPrincipal PrincipalDetail principal) {
        return "user/updateForm";
    }
}
