package com.cos.blog.config.auth;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service    // Bean 등록
public class PrincipalDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // 스프링이 로그인 요청을 가로챌 때, username, password 변수 2개를 가로채는데
    // password 부분 처리는 알아서 함.(비밀번호가 틀렸는지 등)
    // 우리는 username이 DB에 있는지만 확인해 주면 됨.
    // 이 메소드가 있어야 PrincipalDetail 클래스의 필드인 User user에 담아서 저장할 수 있다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User principal = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다.: " + username);
                });

        return new PrincipalDetail(principal);  // 시큐리티 세션에 유저 정보가 저장됨.(UserDetail 타입으로 저장됨)
    }
}
