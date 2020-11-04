<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@include file="/static/tags/taglibs.jsp"%>
<html>
    <head>
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">
        <link rel="stylesheet" type="text/css" href="${ctx}/static/css/common.css"/>
        <title>${User.name}的尚微博</title>
        <script type="text/javascript">
            function logout() {
                <c:if test="${not empty sessionScope.loginUser}" >
                    <c:if test="${User.id == sessionScope.loginUser.id}">
                        var s = confirm("退出登录？");
                        if(s) {
                            window.location.href = "/MyBlog/logout";
                        }
                    </c:if>
                </c:if>
                window.location.href="#";
            }
        </script>
    </head>

    <body bgcolor="#f8f8ff">
        <div id="wrapper">
            <div style="width: 1000px; height: 108px; margin: 0 auto; background-color: #D8E7FE">
                <div id="logo">
                    <h1><img src="${ctx}/static/images/face/${User.photo}">
                        <a style="text-decoration: none" href="javascript:logout()"><font color="yellow">${User.name}</font></a>的尚微博</h1>
                </div>
            </div>
        </div>
        <div class="detail" id="left">
            <table>
                <tr>
                    <td align="center">姓名：</td>
                    <td align="left" class="info">${User.name}</td>
                </tr>
                <tr>
                    <td align="center">生日：</td>
                    <td align="left" class="info">${User.birthday}</td>
                </tr>
                <tr>
                    <td align="center">性别：</td>
                    <td align="left" class="info">
                        <c:if test="${User.sex==0}">
                            未知
                        </c:if>
                        <c:if test="${User.sex==1}">
                            <img border="0" src="${ctx}/static/images/man.png"/>
                        </c:if>
                        <c:if test="${User.sex==2}">
                            <img border="0" src="${ctx}/static/images/woman.png"/>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td align="center">手机：</td>
                    <td align="left" class="info">${User.phone}</td>
                </tr>
                <tr>
                    <td align="center">邮箱：</td>
                    <td align="left" class="info">${User.email}</td>
                </tr>
                <tr>
                    <td align="center">地址：</td>
                    <td align="left" class="info">${User.address}</td>
                </tr>
                <tr>
                    <td align="center">状态：</td>
                    <td align="left" class="info">
                        <c:if test="${User.status==1}">
                            正常
                        </c:if>
                        <c:if test="${User.status==2}">
                            锁定
                        </c:if>
                        <c:if test="${User.status==-1}">
                            已注销
                        </c:if>
                    </td>
                </tr>

                <c:if test="${not empty sessionScope.loginUser}" >
                    <c:if test="${User.id eq sessionScope.loginUser.id}">
                        <tr>
                            <td colspan="2" align="right"><input type="button" class="input" value="修改"/></td>
                        </tr>
                    </c:if>
                </c:if>

            </table>
        </div>
        <div class="detail" id="right">
            <div id="guanzhu">关注<br/>
                <font class="info">${fn:length(guanzhu)}</font>
            </div>
            <div id="fensi">粉丝<br/>
                <font class="info">${fn:length(fensi)}</font>
            </div>
            <div id="blog">微博<br/>
                <font class="info">
                    <c:if test="${fn:length(blogs)==0}">0</c:if>
                    <c:if test="${fn:length(blogs)!=0}"><a style="text-decoration: none" href="/MyBlog/blog/list.do?userId=${User.id}">${fn:length(blogs)}</a></c:if>
                </font>
            </div>
            <div id="comment">评论<br/>
                <font class="info">${fn:length(comments)}</font>
            </div>
        </div>
        <br/>
        <br/>
        <div id="footer">
            <p>
                版权所有 (c) 2020 husyra@163.com 设计
                <a href="http://husyra@163.com">husyra</a>.
            </p>
        </div>

    </body>
</html>
