package com.jcw.blog.controller.api;

import com.jcw.blog.dto.ResponseDto;
import com.jcw.blog.model.User;
import com.jcw.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
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

        return new ResponseDto<>(HttpStatus.OK.value(), HttpStatus.OK, 1);
    }




}
