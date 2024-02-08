let index = {
    init: function() {
        // on 함수: 첫번째 파라미터에는 발생하는 이벤트, 두번째 파라미터는 그 이벤트가 발생시 무엇을 할지 명시
        $("#btn-save").on("click", ()=>{    // function(){} 이 아니라 () => {} 를 쓴 이유는 this를 바인딩하기 위해서이다.
            this.save();
        });
        $("#btn-update").on("click", ()=>{
            this.update();
        });
    },

    // 클릭하면 이 함수도 실행
    save: function() {
        //alert('user의 save함수 호출됨');
        let data = {
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val(),
        };


        // ajax 호출시 default가 비동기 호출
        $.ajax({
            // 회원가입 수행 요청 (100초 가정)
            type: "POST",
            url: "/auth/joinProc",
            data: JSON.stringify(data),  // http 바디 데이터. 위의 data 자스 오브젝트를 이해할 수 있게 json으로 변환
            contentType: "application/json; charset=utf-8", // body 데이터가 어떤 타입인지(MIME)
            dataType: "json"    // 요쳥을 서버로해서 응답이 왔을 때 기본적으로 버퍼로 오기 때문에 문자열이다. 생긴게 JSON이라면 JS 오브젝트로 변환해준다.
        }).done(function(response){
            if (response.status === 500) {
                alert("회원가입에 실패하였습니다.");
            } else {
                alert("회원가입이 완료되었습니다.");
            }
            location.href = "/";  // 회원가입 완료 후 이동할 위치
        }).fail(function(error){
            alert(JSON.stringify(error));
        }); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청!
    },

    update: function() {
//        alert('user의 update함수 호출됨');
        let data = {    // username은 수정x
            id: $("#id").val(),
            username: $("#username").val(), // 이렇게 정의해줘야 @RequestBody User user로 받을 때 값이 넘어간다.
            password: $("#password").val(), // let data에 정의해주지 않으면 null 값이 넘어간다.
            email: $("#email").val()
        };

//         console.log(data)

        // ajax 호출시 default가 비동기 호출
        $.ajax({
            // 회원가입 수행 요청 (100초 가정)
            type: "PUT",
            url: "/user",
            data: JSON.stringify(data),  // http 바디 데이터. 위의 data 자스 오브젝트를 이해할 수 있게 json으로 변환
            contentType: "application/json; charset=utf-8", // body 데이터가 어떤 타입인지(MIME)
            dataType: "json"    // 요쳥을 서버로해서 응답이 왔을 때 기본적으로 버퍼로 오기 때문에 문자열이다. 생긴게 JSON이라면 JS 오브젝트로 변환해준다.
        }).done(function(response){
            alert("회원 수정이 완료되었습니다.");
            console.log(response);
            location.href = "/";  // 회원가입 완료 후 이동할 위치
        }).fail(function(error){
            alert(JSON.stringify(error));
        }); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청!
    },

}

// 위 함수가 실행된다.
index.init();