<%@ page language="java" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>

<title>SSHE</title>
<jsp:include page="inc.jsp"></jsp:include>
</head>

<body id="indexLayout" class="easyui-layout">
	<div region="north" class="logo" style="height:60px;overflow: hidden;" href="layout/north.jsp"></div>
	<div region="east" title="当前日期" split="true" style="width:200px;overflow: hidden;" href="layout/east.jsp"></div>

	<div region="center" title="欢迎使用SSHE系统" style="overflow: hidden;" href="layout/center.jsp"></div>

	<div region="west" title="功能导航" split="false" style="width:200px;overflow: hidden;" href="layout/west.jsp"></div>
	<div region="south" style="height:20px;overflow: hidden;" href="layout/south.jsp"></div>

	<jsp:include page="user/login.jsp" />
	<jsp:include page="user/reg.jsp" />

</body>
</html>
