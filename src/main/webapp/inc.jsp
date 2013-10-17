<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<script type="text/javascript" src="<%=basePath%>/jslib/jquery-easyui-1.3/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/jslib/jquery.cookie.js"></script>

<%
	String easyuiThemeName = "gray";
	Cookie cookies[] = request.getCookies();
	for (Cookie cookie : cookies)
	{
		if (cookie.getName().equals("easyuiThemeName"))
		{
			easyuiThemeName = cookie.getValue();
			break;
		}
	}
%>
<link id="easyuiTheme" rel="stylesheet" href="<%=basePath%>/jslib/jquery-easyui-1.3/themes/<%=easyuiThemeName%>/easyui.css" type="text/css"></link>
<link rel="stylesheet" href="<%=basePath%>/jslib/jquery-easyui-1.3/themes/icon.css" type="text/css"></link>
<script type="text/javascript" src="<%=basePath%>/jslib/jquery-easyui-1.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/jslib/jquery-easyui-1.3/locale/easyui-lang-zh_CN.js"></script>

<link rel="stylesheet" href="<%=basePath%>/jslib/jquery-easyui-portal/portal.css" type="text/css"></link>
<script type="text/javascript" src="<%=basePath%>/jslib/jquery-easyui-portal/jquery.portal.js"></script>

<link rel="stylesheet" href="css/wlCss.css" type="text/css"></link>
<script type="text/javascript" src="jslib/wlUtil.js"></script>

<script type="text/javascript" src="jslib/xheditor-1.1.14/xheditor-1.1.14-zh-cn.min.js"></script>
