<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	function logout(b) {
		$.post("userAction!logout.action", function() {
			if (b) {
				if (wl.isLessThanIe8()) {
					loginDialog.dialog('open');
				} else {
					location.replace(wl.bp());
				}
			} else {
				loginDialog.dialog('open');
			}

		});
	}
</script>

<div style="position: absolute; right: 0px; bottom: 0px; ">
	<a href="javascript:void(0);" class="easyui-menubutton" menu="#layout_north_pfMenu" iconCls="icon-ok">更换皮肤</a> <a href="javascript:void(0);" class="easyui-menubutton" menu="#layout_north_kzmbMenu" iconCls="icon-help">控制面板</a> <a href="javascript:void(0);" class="easyui-menubutton" menu="#layout_north_zxMenu" iconCls="icon-back">注销</a>
</div>

<div id="layout_north_pfMenu" style="width: 120px; display: none;">
	<div onclick="wl.changeTheme('default');">default</div>
	<div onclick="wl.changeTheme('dark-hive');">dark-hive</div>
	<div onclick="wl.changeTheme('gray');">gray</div>
	<div onclick="wl.changeTheme('cupertino');">cupertino</div>
	<div onclick="wl.changeTheme('pepper-grinder');">pepper-grinder</div>
	<div onclick="wl.changeTheme('sunny');">sunny</div>
</div>
<div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
	<div onclick="showUserInfo();">个人信息</div>
	<div class="menu-sep"></div>
	<div>
		<span>更换主题</span>
		<div style="width: 120px;">
			<div onclick="wl.changeTheme('default');">default</div>
			<div onclick="wl.changeTheme('dark-hive');">dark-hive</div>
			<div onclick="wl.changeTheme('gray');">gray</div>
			<div onclick="wl.changeTheme('cupertino');">cupertino</div>
			<div onclick="wl.changeTheme('pepper-grinder');">pepper-grinder</div>
			<div onclick="wl.changeTheme('sunny');">sunny</div>
		</div>
	</div>
</div>
<div id="layout_north_zxMenu" style="width: 100px; display: none;">
	<div onclick="loginDialog.dialog('open');">锁定窗口</div>
	<div class="menu-sep"></div>
	<div onclick="logout();">重新登录</div>
	<div onclick="logout(true);">退出系统</div>
</div>