package com.jcw.blog.config;

import com.jcw.blog.auth.PrincipalDetail;
import com.jcw.blog.auth.PrincipalDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // 시큐리티 필터로 등록
@EnableGlobalMethodSecurity(prePostEnabled = true) //특정주소로 접근을 하면 권한 및 인증 미리 체크
public class SecurityConfig{

    private final PrincipalDetailService principalDetailService;

    @Bean // ioc
    public BCryptPasswordEncoder encoderPWD(){
        return new BCryptPasswordEncoder();
    }

    // 시큐리티가 대신 로그인 해줄 때 password를 가로채기 하는데
    // 해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야
    // 같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있음
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(principalDetailService).passwordEncoder(encoderPWD());
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
                    .loginProcessingUrl("/auth/loginProc") // 스프링 시큐리티가 해당 주소로 요청하는 로그인을 가로채서 대신 로그인 해줌
                    .defaultSuccessUrl("/") //로그인이 정상적으로 되면 이동할 페이지
                .and()
                    .build();

    }


}
