package com.jcw.blog.test;

import com.jcw.blog.model.User;
import com.jcw.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Slf4j
@RequiredArgsConstructor // 의존성 주입 DI
@RestController
public class DummyControllerTest {

    private final UserRepository userRepository;

    // {id} 주소로 파라미터를 전달 받을 수 있음
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable Long id){
//        람다식
//        return userRepository.findById(id).orElseThrow(() ->
//                new IllegalArgumentException("해당 유저는 없습니다. id : " + id));
        User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
            }
        });
        // 요청 : 웹브라우저
        // user 객체 = 자바 오브젝트
        // 변환 (웹브라우저가 이해할 수 있는 데이터) -> json(Gson 라이브러리)
        // 스프링부트 = MessageConverter라는 애가 응답시에 자동 작동
        // 만약에 자바 오브젝트를 리턴하게 되면 MessageConverterrk Jackson 라이브러리르 호출해서
        // user 오브젝트를 json으로 변환해서 브라우저에게 던져줌
        return user;
    }

    @GetMapping("/dummy/users")
    public List<User> listUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }
    @GetMapping("/dummy/user")
    public List<User> pageListUsers(@PageableDefault(
            size=2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<User> pagingUser = userRepository.findAll(pageable);

        List<User> users = pagingUser.getContent();
        return users;
    }

    @PostMapping("/dummy/join")
    public String join(User user){
        log.info(user.toString());
        userRepository.save(user);
        return "회원가입이 완료되었습니다.";
    }
    @Transactional //함수 종료시에 자동 commit 됨
    @PutMapping("/dummy/user/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User requestUser){
        log.info("updateUser--------------------------------");
        log.info("id: " + id);
        log.info("password: " + requestUser.getPassword());
        log.info("emailAddress: " + requestUser.getEmailAddress());

        User user = userRepository.findById(id).orElseThrow(() -> new IllegalStateException("수정에 실패하였습니다"));
        user.setPassword(requestUser.getPassword());
        user.setEmailAddress(requestUser.getEmailAddress());

        // save 함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해줌
        // save 함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 해줌
        // save 함수는 id를 전달하지 않으면 insert를 해줌

        //더티체킹
        return user;
    }
    @DeleteMapping("/dummy/user/{id}")
    public String deleteUser(@PathVariable Long id){
        try {
            userRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            return "해당 id는 DB에 없습니다.";
        }
        return "삭제되었습니다. id=" + id;
    }


}
