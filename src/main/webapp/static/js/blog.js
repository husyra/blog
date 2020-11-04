
function checkLength(v) {
    var len = 140 - v.length;
    if (len>=0) {
        document.getElementById("blogLength").innerHTML = '您还可以发布<span style="font-size:1.5em; color:blue">' + len + '</span>';
    } else {
        document.getElementById("blogLength").innerHTML = '您已经超出<span style="font-size:1.5em; color:red">' + (-len) + '</span>';
    }
}

function addBlog(owner) {
    console.log("==publish==");
    var content = document.getElementsByName("content")[0];
    if(content.value == ""){
        return false;
    }

    console.log(owner);
    console.log(content.value);

    var addUrl ="/MyBlog/blog/add.do";
    $.ajax({
        url: addUrl,
        type: "post",
        data:{
            "owner":owner,
            "content":content.value
        },
        dataType:"json",
        success:function(result){
            // console.log(result);
            if(result.resultCode=="0000"){
                window.location.reload();
            }else{
                alert(result.resultMsg);
            }
        }
    });

    return false;
}

function delBlog(id){
    console.log("=delBlog="+id);

    var s = confirm("确定删除此条微博？");
    if(!s){
        return;
    }

    var delUrl = "/MyBlog/blog/del.do";
    $.ajax({
        url:delUrl,
        type: 'post',
        data :{
            "blogId": id
        },
        dataType :'json',
        success: function(result){
            if(result.resultCode=="0000"){
                //先关闭，再打开
                window.location.reload();
            }else{
                alert(result.resultMsg);
            }
        }
    });

    return true;
}

function addFriend(owner, friendId) {

    var addUrl ="/MyBlog/friend/add.do";
    $.ajax({
        url: addUrl,
        type: "post",
        data:{
            "owner":owner,
            "fid":friendId
        },
        dataType:"json",
        success:function(result){
            // console.log(result);
            if(result.resultCode=="0000"){
                //alert(result.resultMsg);
                window.location.reload();
                return true;
            }
        }
    });

    return false;
}