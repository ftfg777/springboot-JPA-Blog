package com.jcw.blog.service;

import com.jcw.blog.model.User;
import com.jcw.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true) //select 할 때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료(정합성 유지)
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void 회원가입(User user){
        if(user.getUsername().equals("") || user.getUsername().length() == 0 ||
                user.getPassword().equals("") || user.getPassword().length() == 0){
            throw new IllegalStateException("Invalid username or password");
        }

        System.out.println("UserService.회원가입 시작");
        String rawPassword = user.getPassword(); //원문
        String encPassword = passwordEncoder.encode(rawPassword); //해쉬
        user.setPassword(encPassword);
        userRepository.save(user);
        System.out.println("UserService.회원가입 끝");

    }


    @Transactional
    public void 회원수정(User user) {
        System.out.println("UserService.회원수정 시작");
        User userPS = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalStateException("회원 수정 실패: 회원을 찾을 수 없습니다"));

        // oauth에 값이 없으면 수정 가능
        if(userPS != null && userPS.getOauth() == null || userPS.getOauth().equals("")){
            String rawPassword = user.getPassword(); //원문
            String encPassword = passwordEncoder.encode(rawPassword); //해쉬

            userPS.setPassword(encPassword);
            userPS.setEmailAddress(user.getEmailAddress());
        }
        System.out.println("UserService.회원수정 끝"); // 함수 종료 = 서비스 종료 = 트랜잭션 종료 = 자동 커밋(변화감지 -> 더티체킹)
    }

    public User 회원찾기(String username) {
        return userRepository.findByUsername(username).orElseGet(User::new);
    }


    public Boolean 아이디체크(String username) {
        if (username.length() <= 2){
            return false;
        }
        User user = userRepository.findByUsername(username).orElseGet(User::new);
        return user.getUsername() == null || user.getUsername().length() == 0;
    }

    public User 회원검색(Long id) {
        System.out.println("회원검색 id" + id);
        return userRepository.findById(id).orElseThrow(()->new UsernameNotFoundException("Not found id : " + id));
    }
}
