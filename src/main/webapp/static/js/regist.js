function back() {
    window.location.href = "/MyBlog/login";
}
function regist() {
    // console.log("regist");
    var name = document.getElementsByName("name")[0];
    if(name.value == ""){
        var ername = document.getElementById("ername");
        ername.innerHTML = "账号不能为空";
        return false;
    }
    var pwd = document.getElementsByName("pwd")[0];
    if(pwd.value == ""){
        var erpwd = document.getElementById("erpwd");
        erpwd.innerHTML = "密码不能为空";
        return false;
    }
    var pwd2 = document.getElementsByName("pwd2")[0];
    if(pwd2.value == ""){
        var erpwd2 = document.getElementById("erpwd2");
        erpwd2.innerHTML = "请重输入密码";
        return false;
    }
    if(pwd2.value == pwd.value){
        //console.log("验证完成");
        return true;
    }else{
        var erpwd2 = document.getElementById("erpwd2");
        erpwd2.innerHTML = "密码输入错误";
        return false;
    }
    return true;
}

/**
 * 验证用户名是否重复，如重复则不能注册
 */
function verifyName() {
    var name = document.getElementsByName("name")[0];
    if(name.value == ""){
        var ername = document.getElementById("ername");
        ername.innerHTML = "账号不能为空";
        return false;
    }
    var queryUrl="/MyBlog/queryUser.do";
    // console.log("准备发送"+queryUrl)
    console.log(name.value)
    /*$.post(url, name, verifyBack);*/
    $.ajax({
        url:queryUrl,
        type:"post",
        data:{"name":name.value},
        dataType:'json',
        success:function (result) {
            console.log(result);
            var ername = document.getElementById("ername");
            if(result.resultCode=="0000"){
                ername.innerHTML = "验证通过";
                ername.color="green";
            }else{
                ername.innerHTML = result.resultMsg;
            }
        }
    });
}

function setPhoto(obj, name) {
    document.getElementById('photoImg').src = obj.src;
    document.getElementById('photo').value= name;
}