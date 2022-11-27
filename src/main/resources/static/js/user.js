let index = {
    init: function () {
        //function(){}, ()=>{} this를 바인딩하기 위해서 함
        $("#btn-save").on("click", () => {
            this.save();
        });
    },
    save: function () {
        //alert("user.js의 save 함수 호출");
        let data = {
            username: $("#username").val(),
            password: $("#password").val(),
            emailAddress: $("#emailAddress").val()
        };
        //console.log(data);
        //ajax 호출 시 default가 비동기 호출
        $.ajax({
            type: "POST",
            url: "/blog/api/user",
            data: JSON.stringify(data), //http body 데이터
            contentType: "application/json; charset=utf-8", //body 데이터가 어떤 타입인지 (MIME)
            dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴 게 json이라면) => javascript오브젝트로 변경
        }).done(function (resp) {
            alert("회원가입이 완료되었습니다.");
            console.log(resp);
            location.href = "/blog";
        }).fail(function (error) {
            alert(JSON.stringify(error));
        }); //ajax 통신을 이용해서 3개의 데이터를 json을 변경하여 insert 요청
    }
}


index.init();