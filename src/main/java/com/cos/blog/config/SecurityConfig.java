package com.cos.blog.config;

import com.cos.blog.config.auth.PrincipalDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// 빈 등록 = 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것
@Configuration  // 스프링에 빈이 등록되게 함(Ioc로 관리됨)
public class SecurityConfig{
    @Bean   // IoC 된다.
    public BCryptPasswordEncoder encodePWD() {
        return new BCryptPasswordEncoder();
    }

    // 시큐리티가 대신 로그인해주는데 password를 가로채기를 한다.
    // 해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야
    // 같은 해쉬로 암호화해서 db에 있는 해쉬랑 비교할 수 있다.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // 1. csrf 비활성화(테스트시 걸어두는게 좋음)
        http.csrf().disable();

        // 2. 인증 주소 설정
        http.authorizeRequests(
                authorize -> authorize.antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**", "/dummy/**").permitAll() // 위의 링크는 접근 허용
                        .anyRequest().authenticated() // 그 외 링크는 인증받아야 허용
        );

        // 3. 로그인 처리 프로세스 설정
        http.formLogin(f -> f.loginPage("/auth/loginForm") // 모든 로그인은 이 페이지로 감.
                .loginProcessingUrl("/auth/loginProc") // 스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인 해준다.
                .defaultSuccessUrl("/") // 정상적으로 요청이 완료되면 이 링크로 감.
        );

        return http.build();

    }

}
