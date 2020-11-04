<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/static/tags/taglibs.jsp"%>
<%@ page isELIgnored="false" %>
<%@page import="java.io.File"%>
<%@page import="java.io.FileFilter"%>
<html>

	<head>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<title>注册我的尚微博</title>
		<link type="text/css" rel="stylesheet" href="${ctx}/static/css/common.css"/>
		<script type="text/javascript" src="${ctx}/static/js/regist.js"></script>
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
		<div id="wrapper">
			<!-- end #header -->
			<div id="page">
				<div id="page-bgtop">
					<div id="page-bgbtm">
						<div id="content">
							<table width="100%">
								<tr valign="top">
									<td width="40%">
										<h1>注册我的尚微博</h1>
										<form action="${ctx}/user/add.do" onsubmit="return regist()" id="reg" method="post">
											<p>帐号：<input name="name" id="name" onblur="verifyName()"/> *  <font id="ername" color="red" ></font></p>
											<p>密码：<input name="pwd" type="password"/> *  <font id="erpwd" color="red" ></font></p>
											<p>重复：<input name="pwd2" type="password"/>   <font id="erpwd2" color="red" ></font>*</p>
											<p>性别：
												<input type="radio" name="sex" value="1" checked/><img border="0" src="${ctx}/static/images/man.png"/>
												<input type="radio" name="sex" value="2"/><img border="0" src="${ctx}/static/images/woman.png"/>
											</p>
											<p>生日：<input name="birthday" type="date" value="1990-01-01" width="120px"/> *</p>
											<p>手机：<input name="phone"/></p>
											<p>地址：<input name="address" type="text" height="20px"/></p>
											<%--<p>邮箱：<input name="email" required/></p>--%>
											<p>邮箱：<input name="email"/></p>
											<p>头像：<img id="photoImg" src="${ctx}/static/images/face.gif"/></p>
											<input type="hidden" name="photo" id="photo"/>
											<input type="submit" value="注册" />&nbsp;&nbsp;&nbsp;
											<input type="button" value="返回" onclick="back()"/>
										</form>
									</td>
									<td width="60%">
										<p>请选择头像：</p>
										<%
											File file = new File("D:\\Java\\Workspaces\\MyBlog\\src\\main\\webapp\\static\\images\\face");
											File[] files = file.listFiles(new FileFilter() {
												public boolean accept(File pathname){
													String name = pathname.getName();
													return name.endsWith(".jpg") || name.endsWith(".gif");
												}
											});
											for (File f : files) {%>
										<img value="<%=f.getName()%>" src="${ctx}/static/images/face/<%=f.getName()%>" style="cursor:pointer" onclick="setPhoto(this, '<%=f.getName()%>')">
										<%
											}
										%>
									</td>
								</tr>
							</table>
							
						</div>
					
					</div>
				</div>
			</div>
			<!-- end #page -->
		</div>
		<div id="footer">
            <p>
                版权所有 (c) 2020 husyra@163.com 设计
                <a href="http://husyra@163.com">husyra</a>.
            </p>
		</div>

	</body>

</html>
