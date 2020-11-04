<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@include file="/static/tags/taglibs.jsp"%>
<html>
    <head>
        <title>用户查询</title>
        <link rel="stylesheet" type="text/css" href="${ctx}/static/css/common.css"/>
        <script type="text/javascript">
            function showPwd(pass) {
                alert(pass);
                return true;
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
        </div>
        <h3 align="center">用户列表</h3>
        <form action="/MyBlog/user/list.do">
            <table style="width: 1000px;margin-left: 50px">
                <tr>
                    <td align="right">用户名：</td>
                    <td align="left"><input type="text" name="name" value="">&nbsp;&nbsp;<input type="submit" value="搜索"></td>
                    <input type="hidden" name="pageNo" value="${PAGE_NO}">
                    <input type="hidden" name="pageSize" value="${PAGE_SIZE}">
                </tr>
            </table>
        </form>
        <div id="list">
            <c:if test="${fn:length(userList)==0}">
                <div align="center">系统当前没有注册的用户哦！</div>
            </c:if>
            <c:if test="${fn:length(userList)!=0}">
                <table class="tab" align="center">
                    <tr>
                        <th>用户名</th>
                        <th>密码</th>
                        <th>头像</th>
                        <th>生日</th>
                        <th>性别</th>
                        <th>手机号</th>
                        <th>邮箱</th>
                        <th>注册时间</th>
                        <th>地址</th>
                    </tr>
                    <c:forEach items="${userList}" var="user">
                        <tr>
                            <td width="" align="center"><a style="text-decoration: none" href="/MyBlog/home.do?userId=${user.id}">${user.name}</a></td>
                            <td width="80" align="center">
                                <a id="hid" style="cursor: pointer" onclick="return showPwd('${user.pwd}')"><img src="${ctx}/static/images/mima_20.png"></a></td>
                            <td width="20" align="center"><img width="30" height="30" src="${ctx}/static/images/face/${user.photo}"/></td>
                            <td width="100" align="center">${user.birthday}</td>
                            <td width="20" align="center">
                                <c:if test="${user.sex==0}">
                                    未知
                                </c:if>
                                <c:if test="${user.sex==1}">
                                    <img border="0" src="${ctx}/static/images/man_s.png"/>
                                </c:if>
                                <c:if test="${user.sex==2}">
                                    <img border="0" src="${ctx}/static/images/woman_s2.png"/>
                                </c:if>
                            </td>
                            <td width="100" align="center">${user.phone}</td>
                            <td width="150" align="center">${user.email}</td>
                            <td width="120" align="center">
                                <fmt:formatDate value="${user.regtime}" pattern="yyyy-MM-dd"/>
                            </td>
                            <td width="150" align="center">${user.address}</td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td class="page" align="center" colspan="9">共${TOTAL_COUNT}条记录，共${PAGE_NUM}页，当前第${PAGE_NO}页
                            <c:if test="${PAGE_NO==1}">
                                首页 上页
                            </c:if>
                            <c:if test="${PAGE_NO!=1}">
                                <a href="${ctx}/user/list.do?name=${name}&pageNo=1&pageSize=${PAGE_SIZE}">首页</a>
                            </c:if>
                            <c:if test="${PAGE_NO!=1}">
                                <a href="${ctx}/user/list.do?name=${name}&pageNo=${PAGE_NO-1}&pageSize=${PAGE_SIZE}">上页</a>
                            </c:if>
                            <c:if test="${PAGE_NO==PAGE_NUM}">
                                下页 末页
                            </c:if>
                            <c:if test="${PAGE_NO!=PAGE_NUM}">
                                <a href="${ctx}/user/list.do?name=${name}&pageNo=${PAGE_NO+1}&pageSize=${PAGE_SIZE}">下页</a>
                                <a href="${ctx}/user/list.do?name=${name}&pageNo=${PAGE_NUM}&pageSize=${PAGE_SIZE}">末页</a>
                            </c:if>
                        </td>
                    </tr>
                </table>
            </c:if>
        </div>
        <br><br>
        <div id="footer">
            <p>
                版权所有 (c) 2020 husyra@163.com 设计
                <a href="http://husyra@163.com">husyra</a>.
            </p>
        </div>
    </body>
</html>
