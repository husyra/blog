<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@include file="/static/tags/taglibs.jsp"%>
<html>
    <head>
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">
        <title>尚微博</title>
        <link href="${ctx}/static/css/common.css" rel="stylesheet" type="text/css"/>
        <link href="${ctx}/static/css/login.css" rel="stylesheet" type="text/css"/>
        <script type="text/javascript" src="${ctx}/static/js/login.js"></script>
        <script type="text/javascript">
            window.onload =function () {
                //监听回车键，跳转登录
                document.onkeydown=function(event){
                    if(event.keyCode==13){
                        verifyPwd();
                    }
                }
            }
        </script>
    </head>
    <body>
        <div id="header">
            <div id="logo">
                <h1>
                    <a href="#">尚微博 </a>
                </h1>
                <p>
                    设计：***
                </p>
            </div>
            <p style="right:0px; margin:0;"><a href="/MyBlog/user/game.do">玩个小游戏？</a></p>
        </div>
        <hr width="1000px"/>
        <div class="content">
            <div class="login-wrap">
                <form id="login">
                    <h3>登录</h3>
                    <input class="name" id="name" type="text" placeholder="请输入账号">
                    <input class="pwd" id="pwd" type="password" placeholder="请输入密码">
                    <div id="checkMsg" class="msg"></div>
                    <div class="btn">
                        <input type="button" id="submit" class="submit" value="登录" onclick="verifyPwd()">
                        <%--<input type="reset" id="reset" class="reset" value="重置">--%>
                        <input type="button" class="reg" value="注册" onclick="return regist()">
                    </div>
                </form>
            </div>
        </div>
        <div id="footer">
            <p>
                版权所有 (c) 2020 husyra@163.com 设计
                <a href="http://husyra@163.com">husyra</a>.
            </p>
        </div>
    </body>
</html>
