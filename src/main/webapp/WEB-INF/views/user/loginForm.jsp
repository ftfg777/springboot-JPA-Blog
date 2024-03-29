<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp" %>


<div class="container">

    <form action="/auth/loginProc" method="POST">
      <div class="form-group">
        <label for="username">Username:</label>
        <input type="text" name="username" class="form-control" placeholder="Enter username" id="username">
      </div>
      <div class="form-group">
        <label for="password">Password:</label>
        <input type="password" name="password" class="form-control" placeholder="Enter password" id="password">
      </div>
      <button id="btn-login" class="btn btn-primary">로그인</button>
      <a href="https://kauth.kakao.com/oauth/authorize?client_id=ec69805aba73b1f7c3e273ae2cea8f94&redirect_uri=http://localhost:8000/auth/kakao/callback&response_type=code">
        <img src="/image/kakao_login_btn.png" height="38px"/>
      </a>
    </form>

    <p style="margin-top: 1rem;">회원이 아니신가요?&nbsp;<a href="/auth/joinForm">가입하기</a></p>
    

</div>

<script src="/js/user.js"></script>

<%@ include file="../layout/footer.jsp" %>





