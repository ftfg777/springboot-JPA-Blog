let index = {
    init: function () {
        //function(){}, ()=>{} this를 바인딩하기 위해서 함
        $("#btn-save").on("click", () => {
            this.save();
        });
        $("#btn-update").on("click", () => {
            this.update();
        });
        // $("#btn-login").on("click", () => {
        //     this.login();
        // });
    },
    save: function () {
        let data = {
            username: $("#username").val(),
            password: $("#password").val(),
            emailAddress: $("#emailAddress").val()
        };
        $.ajax({
            type: "POST",
            url: "/auth/joinProc",
            data: JSON.stringify(data), //http body 데이터
            contentType: "application/json; charset=utf-8", //body 데이터가 어떤 타입인지 (MIME)
            dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴 게 json이라면) => javascript오브젝트로 변경
        }).done(function (resp) {
            if (resp.statusCode === 500) {
                alert("회원가입이 실패했습니다.");
            } else {
                alert("회원가입이 완료되었습니다.");
                location.href = "/";
            }
        }).fail(function (error) {
            alert(error.errorMessage);
        });
    },
    update: function () {
        let data = {
            id: $("#id").val(),
            username: $("#username").val(),
            password: $("#password").val(),
            emailAddress: $("#emailAddress").val()
        };
        $.ajax({
            type: "PUT",
            url: "/user",
            data: JSON.stringify(data), //http body 데이터
            contentType: "application/json; charset=utf-8", //body 데이터가 어떤 타입인지 (MIME)
            dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴 게 json이라면) => javascript오브젝트로 변경
        }).done(function (resp) {
            alert("수정이 완료되었습니다.");
            console.log(resp);
            location.href = "/";
        }).fail(function (error) {
            alert(JSON.stringify(error));
        }); //ajax 통신을 이용해서 3개의 데이터를 json을 변경하여 insert 요청
    }

    // login: function () {
    //     let data = {
    //         username: $("#username").val(),
    //         password: $("#password").val()
    //     };

    //     $.ajax({
    //         type: "POST",
    //         url: "/api/user/login",
    //         data: JSON.stringify(data),
    //         contentType: "application/json; charset=utf-8",
    //         dataType: "json"
    //     }).done(function (resp) {
    //         alert("로그인이 완료되었습니다.");
    //         console.log(resp);
    //         location.href = "/";
    //     }).fail(function (error) {
    //         alert(JSON.stringify(error));
    //     });
    // }
}


index.init();