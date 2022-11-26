package com.jcw.blog.test;

import com.jcw.blog.model.User;
import com.jcw.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor // 의존성 주입 DI
@RestController
public class DummyControllerTest {

    private final UserRepository userRepository;

    @PostMapping("/dummy/join")
    public String join(User user){
        log.info(user.toString());
        userRepository.save(user);
        return "회원가입이 완료되었습니다.";
    }
}
