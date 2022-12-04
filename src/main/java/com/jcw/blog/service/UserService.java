package com.jcw.blog.service;

import com.jcw.blog.model.User;
import com.jcw.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Transactional(readOnly = true) //select 할 때 트랜잭션 시작, 서비스 종료시에 트랜잭셩 종료(정합성 유지)
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void 회원가입(User user){
        userRepository.save(user);
    }


}
