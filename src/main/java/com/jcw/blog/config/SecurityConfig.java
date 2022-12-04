package com.jcw.blog.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@EnableWebSecurity // 시큐리티 필터로 등록
@EnableGlobalMethodSecurity(prePostEnabled = true) //특정주소로 접근을 하면 권한 및 인증 미리 체크
public class SecurityConfig{

    @Bean // ioc
    public BCryptPasswordEncoder encoderPWD(){
        return new BCryptPasswordEncoder();
    }

    // 빈 등록: 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .csrf().disable() // csrf 토큰 비활성화 (테스트시 걸어두는 게 좋음)
                .authorizeRequests()
                    .antMatchers("/auth/**", "/js/**", "/css/**", "/image/**", "/")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                .and()
                    .formLogin()
                    .loginPage("/auth/loginForm")
                .and()
                    .build();

    }


}
