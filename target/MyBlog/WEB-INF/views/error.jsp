<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/static/tags/taglibs.jsp"%>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Error</title>
    <link href="${ctx}/static/css/style.css" rel="stylesheet" type="text/css" />
    <style>
        body { background: #fdfdfd; font-family: "Microsoft YaHei", 微软雅黑; }
        .error500 { text-align: center; width: 550px; margin: 75px auto 0; padding-top: 65px; }
        .error500 h1 { font-size: 68px; margin-bottom: 20px; color: #ff6600 }
        .error500 h2 { font-size: 22px; }
        .error500 p { padding-left: 154px; line-height: 35px; color: #717678; }
        .reindex { margin: 0 auto; width: 115px; height: 35px; }
        .reindex a { width: 115px; height: 35px; font-size: 14px; font-weight: bold; color: #fff; background: #3c95c8; display: block; line-height: 35px; text-align: center; margin-top: 20px; text-decoration: none; }
    </style>
</head>
    <body>
        <div class="error500">
            <h1>Error</h1>
<%--            <h2 title="<%=exception%>">非常抱歉，您访问的页面出错了！</h2><br/>--%>
            <h2 style="color: #30476A">${errorMsg}</h2>
            <div class="reindex"><a href="javascript:void(0);" target="_parent" onclick="window.history.back();">返 回</a></div>
        </div>
        <div style="height: 100px"></div>
        <div id="footer">
            <p>
                版权所有 (c) 2020 husyra@163.com 设计
                <a href="http://husyra@163.com">husyra</a>.
            </p>
        </div>
    </body>
</html>
