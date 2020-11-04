<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@include file="/static/tags/taglibs.jsp"%>
<html>
    <head>
        <title>微博查询</title>
        <link rel="stylesheet" type="text/css" href="${ctx}/static/css/common.css"/>
        <script type="text/javascript" src="${ctx}/static/js/blog.js"></script>
        <script type="text/javascript" src="${ctx}/static/js/comment.js"></script>
    </head>
    <body>
        <div id="header">
            <div id="logo">
                <c:if test="${empty User}">
                    <h1>
                        <a href="#">尚微博 </a>
                    </h1>
                    <p>
                        设计：***
                    </p>
                </c:if>

                <c:if test="${not empty User}">
                    <h1>
                        <img src="${ctx}/static/images/face/${User.photo}">
                        <a style="text-decoration: none" href="/MyBlog/user/detail.do?userId=${User.id}">
                            <font color="yellow">${User.name}</font></a>的尚微博
                    </h1>
                </c:if>

            </div>
        </div>

        <input type="hidden" id="loginUserId" value="${sessionScope.loginUser.id}" />

        <form action="/MyBlog/blog/list.do">
            <table style="width: 1000px;align:left">
                <tr>
                    <td align="center">
                        <input type="text" name="search" value="${search}">&nbsp;&nbsp;<input type="submit" value="搜索"></td>
                        <input type="hidden" name="userId" value="${userId}">
                        <input type="hidden" name="pageNo" value="${PAGE_NO}">
                        <input type="hidden" name="pageSize" value="${PAGE_SIZE}">
                </tr>
            </table>
        </form>
        <c:if test="${fn:length(blogList)==0}">
            <div align="center">没有相关记录哦！</div>
        </c:if>
        <c:if test="${fn:length(blogList)!=0}">
            <c:forEach items="${blogList}" var="blog">

                <div class="post" style="width: 1000px;">
                    <p class="meta">
                        <span class="date"><fmt:formatDate value="${blog.pubTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                        <span class="posted">发表者：<a href="/MyBlog/home.do?userId=${blog.owner}">
                        <e:id2name tableName="user" colId="id" colName="name" idValue="${blog.owner}"/></a></span>
                    </p>
                    <div class="entry">
                        <p>${blog.content}</p>
                        <p class="links">
                            <a href="#"><img src="${ctx}/static/images/transmit.png"/>转发</a>
                            &nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;
                            <a href="javascript:showComm('${blog.id}', 'comment${blog.id}')"><img src="${ctx}/static/images/comment.png"/>查看评论(${blog.hot})</a>
                            &nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;
                            <a href="javascript:addComm('${blog.id}','${sessionScope.loginUser.id}','0');"><img src="${ctx}/static/images/addComment.png"/>发表评论</a>
                            <c:if test="${not empty sessionScope.loginUser}" >
                                <c:if test="${User.id eq sessionScope.loginUser.id}">
                                    &nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;
                                    <a href="javascript:delBlog('${blog.id}');"><img src="${ctx}/static/images/delete_16_red.png"/>删除</a>
                                </c:if>
                            </c:if>
                        </p>
                    </div>
                    <div class="comments" id='comment${blog.id}'></div>
                </div>
            </c:forEach>
            <p class="page" align="center" colspan="9">共${TOTAL_COUNT}条记录，共${PAGE_NUM}页，当前第${PAGE_NO}页
                <c:if test="${PAGE_NO==1}">
                    首页 上页
                </c:if>
                <c:if test="${PAGE_NO!=1}">
                    <a href="${ctx}/blog/list.do?userId=${userId}&search=${search}&pageNo=1&pageSize=${PAGE_SIZE}">首页</a>
                </c:if>
                <c:if test="${PAGE_NO!=1}">
                    <a href="${ctx}/blog/list.do?userId=${userId}&search=${search}&pageNo=${PAGE_NO-1}&pageSize=${PAGE_SIZE}">上页</a>
                </c:if>
                <c:if test="${PAGE_NO==PAGE_NUM}">
                    下页 末页
                </c:if>
                <c:if test="${PAGE_NO!=PAGE_NUM}">
                    <a href="${ctx}/blog/list.do?userId=${userId}&search=${search}&pageNo=${PAGE_NO+1}&pageSize=${PAGE_SIZE}">下页</a>
                    <a href="${ctx}/blog/list.do?userId=${userId}&search=${search}&pageNo=${PAGE_NUM}&pageSize=${PAGE_SIZE}">末页</a>
                </c:if>
            </p>
        </c:if>

        <!-- 发表评论 -->
        <div id="commentDIV" title="发表评论" style="display:none">
            <textarea name="contentComm" rows="8"></textarea>
        </div>

        <div id="footer">
            <p>
                版权所有 (c) 2020 husyra@163.com 设计
                <a href="http://husyra@163.com">husyra</a>.
            </p>
        </div>
    </body>
</html>
