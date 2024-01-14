package com.cos.blog.test;

import org.springframework.web.bind.annotation.*;

// 사용자가 요청 -> 응답(데이터 응답) : RestController
// 만약 HTML 파일을 응답하는 컨트롤러를 만들고 싶다면 @Controller 사용
@RestController
public class HttpControllerTest {
    private static final String TAG = "HttpControllerTest:";

    @GetMapping("/http/lombok")
    public String lombokTest() {
        // 빌더 패턴 사용.
        Member m = Member.builder().username("ssar").password("1234").email("ssdf@maver.cp,").build();
        System.out.println(TAG + "getter: "+m.getUsername());
        m.setUsername("me");
        System.out.println(TAG + "setter: "+m.getUsername());
        return "lombok test 완료";
    }

    // 인터넷 브라우저 요청은 무조건 get밖에 안된다.
    // get 요청 외에 다른 메소드로 테스트 해 보기 위해 postman을 사용한다.
    // http://localhost:8080/http/get (select)
    // -> 기본. yml 파일에 포트 번호와 서블릿 context-path를 추가했다면
    // localhost:8000/blog/http/lombok 으로 접속해야한다.
    @GetMapping("/http/get")
    public String getTest(Member m) {
        return "get 요청: "+m.getId()+", "+m.getUsername()+", "+m.getPassword()+", "+m.getEmail();
    }

    // http://localhost:8080/http/post (insert)
    // json 타입은 MIME으로 application/json이다.
    // 자동으로 json 타입을 파싱해주는 것이 MessageConverter(스프링부트)이다.
    @PostMapping("/http/post")
    public String postTest(@RequestBody Member m) {
        return "post 요청 " + +m.getId()+", "+m.getUsername()+", "+m.getPassword()+", "+m.getEmail();
    }

    // http://localhost:8080/http/put (update)
    @PutMapping("/http/put")
    public String putTest(@RequestBody Member m) {
        return "put 요청" +  +m.getId()+", "+m.getUsername()+", "+m.getPassword()+", "+m.getEmail();
    }

    // http://localhost:8080/http/delete (delete)
    @DeleteMapping("/http/delete")
    public String deleteTest() {
        return "delete 요청";
    }

}
