package com.jcw.blog.test;

import org.springframework.web.bind.annotation.*;
// 사용자가 요청 -> 응답(HTML 파일)
// @Controller

// 사용자가 요청 -> 응답(Data)
@RestController
public class HttpControllerTest {

    @GetMapping("/http/get")
    public String getTest(Member member){
        return "get 요청 : " + member.toString();
    }

    @PostMapping("/http/post")
    public String postTest(Member member){
        return "post 요청 : " + member.toString();
    }

    @PostMapping("/http/post/text")
    public String postRawTest(@RequestBody String text){
        return "post/raw 요청 : " + text;
    }

    @PostMapping("/http/post/json")
    public String postJsonTest(@RequestBody Member member){
        return "post/json 요청 : " + member.toString();
    }

    @PutMapping("/http/put")
    public String putTest(@RequestBody Member member){
        return "put 요청" + member.toString();
    }

    @DeleteMapping("/http/delete")
    public String deleteTest(){
        return "delete 요청";
    }


}
