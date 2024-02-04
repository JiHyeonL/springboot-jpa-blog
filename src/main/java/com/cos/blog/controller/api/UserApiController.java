package com.cos.blog.controller.api;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApiController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> save(@RequestBody User user) {
        System.out.println("UserApiController : save 호출됨");
        // 실제로 db에 insert하고 아래에서 return이 되면 된다.

        user.setRole(RoleType.USER);    // 이건 응답에서 온 데이터로 설정하는게 아니라 직접 설정해 줘야 하기 때문에 따로 코드 작성.
        userService.회원가입(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @PutMapping("/user")    // json 데이터 받으려면 @RequestBody 붙여야 함.
    public ResponseDto<Integer> update(@RequestBody User user) {    // key=value, x-www-form-urlencoded
        userService.회원수정(user);
        // 여기서는 트랜잭션이 종료되기 때문에 db에 있는 값은 변경이 됐음.
        // 그러나 세션값은 변경되지 않은 상태이기 때문에 우리가 직접 세션값을 변경해줄 것임.

        // 세션 등록 -> UserService의 회원수정 메소드가 아닌 이곳에서 해야 하는 이유는 회원등록 메소드가 끝나야
        // 트랜잭션이 종료되면서 db가 update된다. 그 이후에 username과 password를 비교할 수 있는데,
        // 회원수정 메소드에서 세션등록을 해버리면 db가 update되기 전에 세션을 등록하는 것이기 때문에 세션변경이 안된다.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }


}
