function regist() {
    window.location.href= "/MyBlog/regist.do";
}

function verifyPwd() {
    console.log("verifyPwd");
    var name = document.getElementById("name");
    var pwd = document.getElementById("pwd");

    if(name.value == ""){
        alert("账号不能为空");
        // showMsg("账号不能为空");
        name.focus();
        return false;
    }

    if(pwd.value == ""){
        alert("密码不能为空");
        pwd.focus();
        return false;
    }

    var validateUrl = "/MyBlog/validateLogin.do";
    console.log(name.value+" - "+pwd.value);
    $.ajax({
        url: validateUrl,
        type:"post",
        data:{"name":name.value, "pwd":pwd.value},
        dataType:"json",
        success:function (result) {
            console.log(result);
            if(result.resultCode=='0000'){
                window.location.href = "/MyBlog/home.do?userId="+result.userId;
                /*setTimeout(function (){ //做延时，以便显示登录状态
                    showMsg("正在登录中...");
                    /!*alert("成功");*!/
                    window.location.href = "/MyBlog/home.do?userId="+result.userId;
                }, 100)*/
            }else{
                // alert(result.resultMsg);
                showMsg(result.resultMsg);
                return false;
            }
        }
    });
}

function showMsg(msg){
    $("#checkMsg").text(msg);
}