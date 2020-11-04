<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@include file="/static/tags/taglibs.jsp"%>
<html>
    <head>
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">
        <link rel="stylesheet" type="text/css" href="${ctx}/static/css/style.css"/>
        <script type="text/javascript" src="${ctx}/static/js/blog.js"></script>
        <script type="text/javascript" src="${ctx}/static/js/comment.js"></script>
        <script type="text/javascript">
            $(document).ready(function(){
                //判断是否显示关注按钮
                <c:if test="${fn:length(friendsForMe)!=0}">
                    <c:forEach items="${friendsForMe}" var="friend">
                        <c:if test="${friend.id == sessionScope.loginUser.id}">
                            //已经关注了，不显示关注按钮
                            $("#guanzhu").hide();
                        </c:if>
                    </c:forEach>
                </c:if>
            });

        </script>
        <title>${User.name}的尚微博</title>
    </head>

    <body bgcolor="#f8f8ff">
        <div id="wrapper">
            <div style="width: 1000px; height: 108px; margin: 0 auto; background-color: #D8E7FE">
                <div id="logo">
                    <h1><img id="photo" src="${ctx}/static/images/face/${User.photo}">
                        <a href="/MyBlog/user/detail.do?userId=${User.id}"><font color="yellow">${User.name}</font></a>的尚微博</h1>
                        <c:if test="${not empty sessionScope.loginUser}" >
                            <c:if test="${User.id != sessionScope.loginUser.id}">
                                <div style="clear:both;margin-left:75px; font-family:微软雅黑">
                                    粉丝:<font color="#8a2be2">${fn:length(friendsForMe)}</font>
                                    <a id="guanzhu" href="javascript:addFriend('${sessionScope.loginUser.id}','${User.id}');">关注TA</a>
                                </div>
                           </c:if>
                        </c:if>
                </div>
                <p style="right:0px; margin:0;"><a href="/MyBlog/user/game.do">玩个小游戏？</a></p>
            </div>
            <div id="search">
                <form action="/MyBlog/blog/list.do" method="post">
                    <input name="search" style="height:28px;font-size:16px;font-family:微软雅黑" value=""/>
                    <input type="hidden" name="userId" value="${User.id}" />
                    <input type="image" src="${ctx}/static/images/find.png" align="middle"/>
                </form>
            </div>

            <input type="hidden" id="loginUserId" value="${sessionScope.loginUser.id}" />

            <!-- end #header -->
            <div id="page">
                <div id="page-bgtop">
                    <div id="page-bgbtm">
                        <table width="100%">
                            <tr valign="top">
                                <td width="700px">
                                    <div id="content">
                                        <c:if test="${not empty sessionScope.loginUser}" >
                                            <c:if test="${User.id eq sessionScope.loginUser.id}">
                                                <c:if test="${fn:length(Blogs)==0}">
                                                    快快发表您的第一篇微博吧！
                                                </c:if>
                                                <span id="blogLength">您还可以发布<span style="font-size:1.5em; color:blue">140</span></span>个字
                                                <form onsubmit="return addBlog('${User.id}')" method="post">
                                                    <textarea name="content" rows="4" cols="80" onkeyup="checkLength(this.value)" onchange="checkLength(this.value)"></textarea>
                                                    <br/>
                                                    <input type="hidden" name="owner" value="${User.id}"/>
                                                    <input type="submit" value="发布"/>
                                                </form>
                                            </c:if>
                                        </c:if>

                                        <c:if test="${fn:length(Blogs)!=0}">
                                            <c:forEach items="${Blogs}" var="blog">

                                                <div class="post">
                                                    <p class="meta"><span class="date"><fmt:formatDate value="${blog.pubTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span></p>
                                                    <%--<div style="clear: both;">&nbsp;</div>--%>
                                                    <div class="entry">
                                                        <p>${blog.content}</p>
                                                        <p class="links">
                                                            <a href="#"><img src="${ctx}/static/images/transmit.png"/>转发</a>
                                                            &nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;
                                                            <a href="javascript:showComm('${blog.id}', 'comment${blog.id}', '${sessionScope.loginUser.id}')"><img src="${ctx}/static/images/comment.png"/>查看评论(${blog.hot})</a>
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
                                        </c:if>
                                    </div>
                                    <!-- div#content结束 -->
                                    <!-- 查看用户微博 -->
                                    <div id="more">
                                        <c:if test="${fn:length(Blogs)>=2}">
                                            <a href="/MyBlog/blog/list.do?userId=${User.id}">查看更多</a>
                                        </c:if>
                                    </div>
                                </td>
                                <td >
                                    <div style="clear:none;width:200px;border:1px #00f solid;text-align:center;padding:0px">
                                        <div style="background-color: #ddd;margin:0px;padding:0px"><h3 style="font-size:18px">${User.name}关注的人</h3></div>
                                        <c:if test="${fn:length(friendsForMy)==0}">
                                            （无）
                                        </c:if>
                                        <c:if test="${fn:length(friendsForMy)!=0}">
                                            <ul style="clear:left;margin:5px;padding:0px;">
                                                <c:forEach items="${friendsForMy}" var="friend">
                                                    <li style="  float:left;list-style-type:none;display:block;margin:0px;padding:0px;width:60px;">
                                                        <a href="/MyBlog/home.do?userId=${friend.id}">
                                                            <img border="0" src="${ctx}/static/images/face/${friend.photo}"/>
                                                        </a>
                                                        <br/><a href="/MyBlog/home.do?userId=${friend.id}">${friend.name}</a>
                                                    </li>
                                                </c:forEach>
                                            </ul>
                                        </c:if>
                                    </div>
                                    <br>
                                    <br>
                                    <P>&nbsp;</P>
                                    <div style="clear:none;width:200px;border:1px #00f solid;text-align:center;padding:0px">
                                        <div style="background-color: #ddd;margin:0px;padding:0px"><h3 style="font-size:18px">关注${User.name}的人</h3></div>
                                            <c:if test="${fn:length(friendsForMe)==0}">
                                                （无）
                                            </c:if>
                                            <c:if test="${fn:length(friendsForMe)!=0}">
                                                <ul style="clear:left;margin:5px;padding:0px;">
                                                    <c:forEach items="${friendsForMe}" var="friend">
                                                        <li style="  float:left;list-style-type:none;display:block;margin:0px;padding:0px;width:60px;">
                                                            <a href="/MyBlog/home.do?userId=${friend.id}">
                                                                <img border="0" src="${ctx}/static/images/face/${friend.photo}"/>
                                                            </a>
                                                            <br/><a href="/MyBlog/home.do?userId=${friend.id}">${friend.name}</a>
                                                        </li>
                                                    </c:forEach>
                                                </ul>
                                            </c:if>
                                    </div>

                                </td>
                            </tr>
                        </table>

                    </div>
                </div>
            </div>
            <!-- end #page -->
        </div>

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
