package com.cos.blog.service;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Transactional  // 회원가입 함수를 트랜잭션화 함. 아래 전체 과정이 성공하면 commit, 실패하면 rollback
    public void 회원가입(User user) {
        String rawPassword = user.getPassword();    // 원 비밀번호(예:1234)
        String encPassword = encoder.encode(rawPassword);   // 비밀번호 해쉬

        user.setPassword(encPassword);
        user.setRole(RoleType.USER);
        userRepository.save(user);
    }

}
