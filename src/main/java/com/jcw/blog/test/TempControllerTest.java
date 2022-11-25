package com.jcw.blog.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class TempControllerTest {

    @GetMapping("/temp/home")
    public String tempHome(){
        log.info("TempController");
        // 파일리턴 기본경로 : src/main/resources/static
        // 리턴명 : /home.html
        // 풀경로 : src/main/resources/static/home.html
        return "/home.html";
    }

    @GetMapping("/temp/jsp")
    public String testJsp(){
        log.info("TempController");
//        prefix: /WEB-INF/views/
//        return: test
//        suffix: .jsp
//        path: /WEB-INF/views/test.jsp
        return "test";
    }
}
