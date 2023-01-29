package com.jcw.blog.controller.api;

import com.jcw.blog.auth.PrincipalDetail;
import com.jcw.blog.dto.ResponseDto;
import com.jcw.blog.model.User;
import com.jcw.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserApiController {
    private final UserService userService;

    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> save(@RequestBody User user){
        System.out.println("UserApiController save 호출");

        userService.회원가입(user);

        System.out.println("UserApiController save 끝");
        return new ResponseDto<>(HttpStatus.OK.value(), HttpStatus.OK, 1);
    }

    @PutMapping("/user")
    public ResponseDto<Integer> update(@RequestBody User user,
                                       HttpSession session){
        System.out.println("UserApiController update 호출" + user.getUsername());

        userService.회원수정(user);

        // 세션 값은 변경되지 않은 상태 (DB는 변경) = 직접 변경해 줘야 함
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);

        System.out.println("UserApiController update 끝");
        return new ResponseDto<>(HttpStatus.OK.value(), HttpStatus.OK, 1);

    }




}
