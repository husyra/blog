Date.prototype.pattern= function(fmt) {
    var o = {
        "M+" : this.getMonth()+1, //月份
        "d+" : this.getDate(), //日
        "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时
        "H+" : this.getHours(), //小时
        "m+" : this.getMinutes(), //分
        "s+" : this.getSeconds(), //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S" : this.getMilliseconds() //毫秒
    };
    var week = {
        "0" : "/u65e5",
        "1" : "/u4e00",
        "2" : "/u4e8c",
        "3" : "/u4e09",
        "4" : "/u56db",
        "5" : "/u4e94",
        "6" : "/u516d"
    };
    if(/(y+)/.test(fmt)){
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }
    if(/(E+)/.test(fmt)){
        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[this.getDay()+""]);
    }
    for(var k in o){
        if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
    return fmt;
}

//*************评论对话框*************
$(document).ready(function(){
    $("#commentDIV").dialog({
        autoOpen:false,
        modal:false,
        buttons:{
            "发布":function(){
                //取出发布内容
                var ss = $("[name='contentComm']").val();

                $.post("/MyBlog/comm/add.do",{content:ss,'object':blogId, 'owner':userId, 'parent':commParent},function(data){
                    var result = $.parseJSON(data);
                    if(result.resultCode=="0000"){
                        $("#commentDIV").dialog("close");
                        $("[name='contentComm']").val("");	//清空文本框内容
                        alert("发布成功");
                        window.location.reload();
                    }else{
                        alert(result.resultMsg);
                    }
                });
            },
            "取消":function(){
                $("#commentDIV").dialog("close");
            }
        }
    });
});

var blogId;//定义全局变量
var userId;
var commParent;

function addComm(id, user, parent){
    console.log("=addComm="+parent);
    $("#commentDIV").dialog("open");
    blogId = id;
    userId = user;
    commParent = parent;
    return false;
}

function delComm(commId){
    console.log("=delComm="+commId);

    var s = confirm("确定删除此条评论？");
    if(!s){
        return;
    }

    var delUrl = "/MyBlog/comm/del.do";
    $.ajax({
        url:delUrl,
        type: 'post',
        data :{
            "commId": commId
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

function showComm(blogId, showid) {
    console.log("showComm="+blogId);
    var commDiv = document.getElementById(showid);
    if(commDiv.style.display == 'block'){
        commDiv.style.display = 'none';
        return false;
    }

    var loginUserId = $("#loginUserId").val();

    //查询评论
    $.ajax({
        url: "/MyBlog/comm/listJson",
        type: "post",
        data:{
            "blogId":blogId,
            "limit":10
        },
        dataType:"json",
        success:function(result){
            //console.log(result);
            if(result.resultCode=="0000"){
                //循环数据放入到页面中
                commDiv.style.display='block';
                if(result.comments!=null && result.comments.length>0){
                    var html = "";
                    for(var i=0; i<result.comments.length; i++){

                        html +="<table width=\"100%\">";
                        html +="<tr valign=\"top\">";
                        html +="<td width=\"40\"><img width='40' height='40' src=\"/MyBlog/static/images/face/"+result.comments[i].userPhoto+"\"/>";
                        html +="</td>";
                        var time = new Date(result.comments[i].pubTime).pattern("yyyy-MM-dd hh:mm:ss");
                        html +="<td><a href=\"/MyBlog/home.do?userId="+result.comments[i].owner+"\">"+result.comments[i].userName+"</a> (评论于:"+time+")";

                        console.log(loginUserId+"=="+result.comments[i].owner);
                        if(loginUserId == result.comments[i].owner){
                            console.log("删除评论");
                            html += "&nbsp;&nbsp;<a href=\"javascript:delComm('"+result.comments[i].id+"')\">删除</a>";
                        }else{
                            console.log("回复评论");
                            html += "&nbsp;&nbsp;<a href=\"javascript:addComm('"+blogId+"', '"+loginUserId+"', '"+ result.comments[i].id+"')\">回复</a>";
                        }

                        html +="<br/> &nbsp;&nbsp;<font color='#32639A'>"+format(result.comments[i].content, 35)+"</font>";

                        var childComm = result.comments[i].childComm;

                        /*子评论显示*/
                        if(childComm!=null && childComm.length>0){
                            console.log("childComm.size="+childComm.length);

                            for(var j=0; j<childComm.length; j++){
                                console.log("childContent="+childComm[j].content);
                                var tt = new Date(childComm[j].pubTime).pattern("yyyy-MM-dd hh:mm:ss");
                                html +="<br/>&nbsp;&nbsp;<img width='30' height='30' style='padding-top:5px' src=\"/MyBlog/static/images/face/"+childComm[j].userPhoto+"\"/>";
                                html +="<a href=\"/MyBlog/home.do?userId="+childComm[j].owner+"\">"+childComm[j].userName+"</a> (评论于:"+tt+")";

                                if (loginUserId == childComm[j].owner) {
                                    console.log("删除子评论");
                                    html += "&nbsp;&nbsp;<a href=\"javascript:delComm('" + childComm[j].id + "')\">删除</a>";
                                }

                                html +="<br/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color='#32639A'>"+format(childComm[j].content, 30)+"</font>";

                            }
                            //子评论显示完换行
                            html +="<br/>";
                        }
                        html +="</td>";
                        html +="</tr>";
                        html +="</table>";
                    }
                    /*$('#commDiv').html(html);
                    $('#commDiv').show();*/
                    html +="<br/>";
                    commDiv.innerHTML = html;
                }

                return true;
            }
        }
    });

    return false;
}

/**
 * 评论内容换行显示
 */
function format(str, n){
    var len = str.length;   //字符串长度
    var temp = '';
    if(len>n){
        temp = str.substring(0, n);     //截取一行显示的文字
        str = str.substring(n, len);    //截取剩下的文字
        return temp +'<br/>'+format(str, n);
    }else{
        return str;
    }
}