package com.jcw.blog.controller.api;

import com.jcw.blog.auth.PrincipalDetail;
import com.jcw.blog.dto.JoinValidatorDto;
import com.jcw.blog.dto.ResponseDto;
import com.jcw.blog.exception.UserNotFoundException;
import com.jcw.blog.model.User;
import com.jcw.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserApiController {
    private final UserService userService;

    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> save(@RequestBody @Valid User user){
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

    @GetMapping("/api/user/{id}")
    public User getOneUser(@PathVariable Long id){
        System.out.println("getOneUser id = " + id);
         return userService.회원검색(id);
    }

    @PostMapping("/api/user/check")
    public boolean idCheck(@RequestBody String username){
        return userService.아이디체크(username);
    }


}
