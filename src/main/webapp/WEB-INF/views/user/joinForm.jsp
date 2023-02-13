<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp" %>
<link rel="stylesheet" href="/css/join.css">

<div class="container">

    <form>
      <div class="form-group">
        <label for="username">Username:</label>
        <input type="text" class="form-control" placeholder="Enter username" id="username" required>
        <span id="usernameMsg"></span>
      </div>
      <div class="form-group">
        <label for="password">Password:</label>
        <input type="password" class="form-control" placeholder="Enter password" id="password" required>
        <span id="passwordMsg"></span>
      </div>
      <div class="form-group">
        <label for="emailAddress">Email address:</label>
        <input type="email" class="form-control" placeholder="Enter email" id="emailAddress">
        <span id="emailAddressMsg"></span>
      </div>
    </form>

    <button id="btn-save" class="btn btn-primary">회원가입</button>

</div>

<script src="/js/user.js"></script>
<script>
    let usernameMsg = document.querySelector("#usernameMsg");
    let passwordMsg = document.querySelector("#passwordMsg");
    let emailAddressMsg = document.querySelector("#emailAddressMsg");

  $("#username").blur(function(){
    let username = $("#username").val();
      $.ajax({
            type: "POST",
            url: "/api/user/check",
            data: username,
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (resp) {
            if(resp == true){
              usernameMsg.classList.remove('red');
              usernameMsg.classList.add('green');
              usernameMsg.innerHTML = "사용 가능한 아이디입니다.";
            }else{
              usernameMsg.classList.remove('green');
              usernameMsg.classList.add('red');
              usernameMsg.innerHTML = "사용할 수 없는 아이디입니다.";
            }
            console.log(resp);
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
  });

  $("#password").blur(function(){
    let password = $("#password").val();
    
    if(password.length < 8){
      passwordMsg.classList.remove('green');
      passwordMsg.classList.add('red');
      passwordMsg.innerHTML = "8글자 이상 입력해주세요.";
    }else{
      passwordMsg.classList.remove('red');
      passwordMsg.classList.add('green');
      passwordMsg.innerHTML = "사용 가능한 패스워드입니다.";
    }
  });

  $("#emailAddress").blur(function(){
    let emailAddress = $("#emailAddress").val();
    let checkEmail = /^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/;
    
    if(!checkEmail.test(emailAddress)){
      emailAddressMsg.classList.remove('green');
      emailAddressMsg.classList.add('red');
      emailAddressMsg.innerHTML = "올바른 이메일 형식이 아닙니다.";
    }else{
      emailAddressMsg.classList.remove('red');
      emailAddressMsg.classList.add('green');
      emailAddressMsg.innerHTML = "사용 가능한 이메일입니다.";
    }
  });





</script>
<%@ include file="../layout/footer.jsp" %>





