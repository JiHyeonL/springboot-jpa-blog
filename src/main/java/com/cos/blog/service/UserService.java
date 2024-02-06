package com.cos.blog.service;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 스프링이 컴포넌트 스캔을 통해 bean에 등록해준다. IoC를 해준다는 뜻.
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional(readOnly = true)
    public User 회원찾기(String username) {
        User user = userRepository.findByUsername(username).orElseGet(() -> {
            return new User();
        });
        return user;
    }

    @Transactional  // 회원가입 함수를 트랜잭션화 함. 아래 전체 과정이 성공하면 commit, 실패하면 rollback
    public void 회원가입(User user) {
        String rawPassword = user.getPassword();    // 원 비밀번호(예:1234)
        String encPassword = encoder.encode(rawPassword);   // 비밀번호 해쉬

        user.setPassword(encPassword);
        user.setRole(RoleType.USER);
        userRepository.save(user);
    }

    @Transactional
    public void 회원수정(User user) {
        // 수정시에는 영속성 컨텍스트에 User 오브젝트를 영속화시키고, 영속화된 User 오브젝트를 수정한다.
        // select를 해서 user 오브젝트를 DB로부터 가져오는 이유는 영속화를 하기 위해서!
        // 영속화된 오브젝트를 변경하면 자동으로 DB에 update문을 날려준다.
        User persistance = userRepository.findById(user.getId()).orElseThrow(() -> {
            System.out.println(user.getUsername());
            return new IllegalArgumentException("회원 찾기 실패");    // 유저 못찾을 수도 있으니까.
        });

        // validation 체크(Oauth(카카오 로그인)가 없으면 비밀번호, 이메일 수정 가능 - 카카오 로그인은 cos1234로 비밀번호 고정)
        if (persistance.getOauth() == null || persistance.getOauth().equals("")) {
            String rawPassword = user.getPassword();
            String encPassword = encoder.encode(rawPassword);   // 비밀번호 해쉬
            persistance.setPassword(encPassword);
            persistance.setEmail(user.getEmail());
        }

        // 회원수정 함수 종료시 = 서비스 종료 = 트랜잭션 종료 = commit이 자동으로 된다.
        // commit이 자동으로 된다는 뜻은 영속화된 persistance 객체의 변화가 감지되면 더티체킹이 되어 update문을 날려줌.
    }

}
