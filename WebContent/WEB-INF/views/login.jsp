<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<title><fmt:message key="login.title" /></title>
<%@ include file="/WEB-INF/views/commons/yepnope.jsp"%>
<script type="text/javascript">
	yepnope("${ctx}/resources/js/login.js");
</script>
</head>
<body class="x-border-layout-ct" style="position: static; overflow: hidden;">
	<table id="logo-table" style="margin-top: 6%;">
		<tr>
			<td align="center" height="65"><a href="${ctx}/"><img src="${ctx}/resources/images/login_header.jpg"></img> </a>
			</td>
		</tr>
	</table>
	<div id="login-win-div"></div>
	<c:set var="fields" value="${applicationScope.fields}" />
	<c:set var="cityMap" value="${applicationScope.cityMap}" />
	<div id="sexMap" style="display:none"><c:out value="${fields.sex }"></c:out></div>
	<div id="cityMap" style="display:none"><c:out value="${cityMap }"></c:out></div>
</body>
</html>
