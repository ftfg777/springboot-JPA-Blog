package com.jcw.blog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcw.blog.model.KakaoProfile;
import com.jcw.blog.model.OAuthToken;
import com.jcw.blog.model.RoleType;
import com.jcw.blog.model.User;
import com.jcw.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

// 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
// 그냥 주소가 / 이면 index.jsp(메인페이지)만 허용
// static 이하에 있는 /js/**, /css/**, /image/** 도 허용
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Value("${cos.key}")
    private String cosKey;

    @GetMapping("/auth/joinForm")
    public String joinForm(){
        return "user/joinForm";
    }

    @GetMapping("/auth/loginForm")
    public String loginForm(){
        return "user/loginForm";
    }

    @GetMapping("/user/updateForm")
    public String updateForm(){
        return "user/updateForm";
    }

    @GetMapping("/auth/kakao/callback")
    public String kakaoCallback(String code){ // Data를 리턴해주는 컨트롤러 함수
        String grantType = "authorization_code";
        String clientId = "ec69805aba73b1f7c3e273ae2cea8f94";
        String redirectUri = "http://localhost:8000/auth/kakao/callback";
        String contentType = "application/x-www-form-urlencoded;charset=utf-8";

        //POST 방식으로 key=value 데이터를 요청 (카카오쪽으로)
        // Retrofit2, OkHttp, RestTemplate

        RestTemplate rt = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", contentType);

        // HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", grantType);
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        // HttpHeader, HttpBody 를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        // Http 요청하기 - Post 방식으로 - response 변수의 응답 받음
        ResponseEntity<String> response = rt.exchange(
          "https://kauth.kakao.com/oauth/token",
          HttpMethod.POST,
          kakaoTokenRequest,
          String.class
        );

        // Gson, Json Simple, ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken = null;
        try {
            oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println(oAuthToken.getAccess_token());

        RestTemplate rt2 = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer "+oAuthToken.getAccess_token());
        headers2.add("Content-type", contentType);

        // HttpHeader, HttpBody 를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers2);

        // Http 요청하기 - Post 방식으로 - response 변수의 응답 받음
        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        //User 오브젝트 : username, password, email
        User kakaoUser = User.builder()
                .username(kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId())
                .password(cosKey)
                .emailAddress(kakaoProfile.getKakao_account().getEmail())
                .role(RoleType.USER)
                .oauth("kakao")
                .build();

        // 가입자 혹은 비가입자 체크
        User originUser = userService.회원찾기(kakaoUser.getUsername());

        if(originUser.getUsername() == null){
            System.out.println("기존 회원이 아니기에 자동 회원가입을 진행합니다.");
            userService.회원가입(kakaoUser);
        }

        // 로그인 처리
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), cosKey);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        return "redirect:/";
    }
}
