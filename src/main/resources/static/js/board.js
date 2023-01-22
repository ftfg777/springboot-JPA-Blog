let index = {
    init: function () {
        $("#btn-board-save").on("click", () => {
            this.save();
        });
    },
    save: function () {
        let data = {
            title: $("#title").val(),
            content: $("#content").val()
        };
        $.ajax({
            type: "POST",
            url: "/api/board",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (resp) {
            alert("글작성이 완료되었습니다.");
            console.log(resp);
            location.href = "/";
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
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