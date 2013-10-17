<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	var loginDialog;
	var loginTabs;
	var loginInputForm;
	var loginDatagridForm;
	var loginComboboxForm;
	var loginComboboxName;
	var loginDatagridName;
	$(function() {
		var formParam = {
			url : 'userAction!loginAction.action',
			success : function(data) {
				var obj = jQuery.parseJSON(data);
				if (obj.success) {
					var user = obj.obj;
					afterLoginSuccess(user.loginName, user.ip);
				}
				$.messager.show({
					title : '提示',
					msg : obj.msg,
				});
			}
		};
		loginInputForm = $('#loginInputForm').form(formParam);
		loginDatagridForm = $('#loginDatagridForm').form(formParam);
		loginComboboxForm = $('#loginComboboxForm').form(formParam);

		$('#loginInputForm input').bind('keyup', function(event) {
			if (event.keyCode == '13') {
				$('#loginInputForm').submit();
			}
		});
		window.setTimeout(function() {
			$('#loginInputForm input[name=name]').focus();
		}, 0);
		loginDialog = $('#loginDialog').show().dialog({
			buttons : [ {
				text : '注册',
				handler : function() {
					$('#user_reg_regForm input').val('');
					$('#user_reg_regDialog').dialog('open');
				}
			}, {
				text : '登录',
				handler : function() {
					var f = loginTabs.tabs('getSelected').find('form');
					f.submit();
				}
			} ]
		});
		loginTabs = $('#loginTabs').tabs({
			onSelect : function(title) {
				if ('输入方式' == title) {
					window.setTimeout(function() {
						$('div.validatebox-tip').remove();
						loginInputForm.find('input[name=name]').focus();
					}, 0);
				} else if ('表格方式' == title) {
					window.setTimeout(function() {
						if (loginDatagridName.combogrid('options').url == '') {
							loginDatagridName.combogrid({
								url : 'userAction!loginDatagrid.action'
							});
						}
						$('div.validatebox-tip').remove();
						loginDatagridName.combogrid('textbox').focus();
					}, 0);
				} else if ('补全方式' == title) {
					window.setTimeout(function() {
						if (loginComboboxName.combobox('options').url == '') {
							loginComboboxName.combobox({
								url : 'userAction!loginCombobox.action'
							});
						}
						$('div.validatebox-tip').remove();
						loginComboboxName.combobox('textbox').focus();
					}, 0);
				}
			}
		});
		loginDatagridName = $('#loginDatagridName').combogrid({
			loadMsg : '数据加载中....',
			panelWidth : 440,
			panelHeight : 180,
			required : true,
			fitColumns : true,
			value : '',
			idField : 'name',
			textField : 'name',
			mode : 'remote',
			url : '',
			pagination : true,
			pageSize : 5,
			pageList : [ 5, 10 ],
			columns : [ [ {
				field : 'id',
				title : '编号',
				width : 60,
				hidden : true
			}, {
				field : 'name',
				title : '登录名',
				width : 100,
				sortable : true
			}, {
				field : 'createDateTime',
				title : '创建时间',
				width : 150,
				sortable : true
			}, {
				field : 'modifyDateTime',
				title : '最后修改时间',
				width : 150,
				sortable : true
			} ] ],
			delay : 500
		});
		loginComboboxName = $('#loginComboboxName').combobox({
			required : true,
			url : '',
			textField : 'name',
			valueField : 'name',
			mode : 'remote',
			panelHeight : 'auto',
			delay : 500,
			value : ''
		});
		var afterLoginSuccess = function(loginName, ip) {
			$('#indexLayout').layout('panel', 'center').panel('setTitle', wl.fs('[{0}],欢迎您[{1}]', loginName, ip));
			$('#loginDialog').dialog('close');
			window.setTimeout(function() {
				if (onlineDatagrid != undefined) {
					onlineDatagrid.datagrid({
						pagination : true,
						url : 'onlineAction!onlineDatagrid.action'
					});
					var p = onlineDatagrid.datagrid('getPager');
					p.pagination({
						showPageList : false,
						showRefresh : false,
						beforePageText : '',
						afterPageText : '/{pages}',
						displayMsg : ''
					});
				}
			}, 3000);

		}
		var sessionInfo_loginName = '${sessionInfo.loginName}';
		var sessionInfo_ip = '${sessionInfo.ip}';
		if (sessionInfo_loginName && sessionInfo_ip) {
			afterLoginSuccess(sessionInfo_loginName, sessionInfo_ip);
		}
	});
</script>
<div id="loginDialog" modal="true" title="系统登录" closable="false" style="width:250px;height:210px;display: none;overflow: hidden;">
	<div id="loginTabs" fit="true" border="false">
		<div title="输入方式" style="overflow: hidden;">
			<div class="info">
				<div class="tip icon-tip"></div>
				<div>用户名密码都是admin</div>
			</div>
			<div align="center">
				<form id="loginInputForm" method="post">
					<table class="tableForm" style="margin: 5px;">
						<tr>
							<th>登录名</th>
							<td><input class="easyui-validatebox" name="name" data-options="required:true,missingMessage:'登陆名称必填'" />
							</td>
						</tr>
						<tr>
							<th>密码</th>
							<td><input class="easyui-validatebox" name="pwd" type="password" data-options="required:true,missingMessage:'密码必填'" />
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<div title="表格方式" style="overflow: hidden;">
			<div class="info">
				<div class="tip icon-tip"></div>
				<div>可模糊检索用户名</div>
			</div>
			<div align="center">
				<form id="loginDatagridForm" method="post">
					<table class="tableForm">
						<tr>
							<th>登录名</th>
							<td><select id="loginDatagridName" name="name" style="display: none;width:157px;" required="true" missingMessage="请检索登录用户名"></select>
							</td>
						</tr>
						<tr>
							<th>密码</th>
							<td><input name="pwd" type="password" class="easyui-validatebox" required="true" missingMessage="请填写登录密码" value="" />
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<div title="补全方式" style="overflow: hidden;">
			<div class="info">
				<div class="tip icon-tip"></div>
				<div>可自动补全用户名</div>
			</div>
			<div align="center">
				<form id="loginComboboxForm" method="post">
					<table class="tableForm">
						<tr>
							<th>登录名</th>
							<td><select id="loginComboboxName" name="name" style="display: none;width:157px;"></select></td>
						</tr>
						<tr>
							<th>密码</th>
							<td><input name="pwd" type="password" class="easyui-validatebox" required="true" value="" /></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
</div>