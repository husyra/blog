<!-- 公用标签页面
 统一在此页面引入字符集，struts标签-->
<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/tags/ronglian_tag" prefix="e"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${ctx}/static/js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-ui-1.8.4.custom.min.js"></script>
<%--<c:set var="uauth" value="${dauth[mid]}"/>--%>
<%--<%@taglib prefix="s" uri="/struts-tags" %>--%>
