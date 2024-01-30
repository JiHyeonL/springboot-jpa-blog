package com.cos.blog.config.auth;

import com.cos.blog.model.User;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

// user 오브젝트를 저장하기 위해 UserDetails를 implements함.
// implements했기 때문에 UserDetails의 추상메소드를 override해줌.
// 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDetails 타입의 오브젝트를
// 스프링 시큐리티의 고유한 세션저장소에 저장해준다.
@Getter
public class PrincipalDetail implements UserDetails {
    private User user;  //컴포지션(객체를 품고있음)

    public PrincipalDetail(User user) {
        this.user = user;
    }

    @Override

    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 계정이 만료되지 않았는지 리턴한다. (true: 만료안됨)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠겨있지 않았는지 리턴한다.(true: 잠기지 않음)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호가 만료되지 않았는지 리턴한다.(true: 만료안됨)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정이 활성화(사용가능)인지 리턴한다.(true: 활성화)
    @Override
    public boolean isEnabled() {
        return true;
    }

    // GrantedAuthority를 상속한 Collection을 반환해야 한다.
    // 계정이 갖고 있는 권한 목록을 반환한다. (권한이 여러개 있을 수 있어서 루프를 돌어야 하는데 우리는 한개만)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(() -> {
            return "ROLE_" + user.getRole();
        });

        return collectors;
    }

}
