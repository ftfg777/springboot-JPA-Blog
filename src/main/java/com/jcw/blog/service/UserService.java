package com.jcw.blog.service;

import com.jcw.blog.model.User;
import com.jcw.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void 회원가입(User user){
        userRepository.save(user);
    }
}
