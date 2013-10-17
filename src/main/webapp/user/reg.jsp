<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function() {
		$('#user_reg_regForm').form({
			url : 'userAction!regAction.action',
			success : function(data) {
				var obj = jQuery.parseJSON(data);
				if (obj.success) {
					$('#user_reg_regDialog').dialog('close');
				}
				$.messager.show({
					title : '提示',
					msg : obj.msg,
				});
			}
		});
		$('#user_reg_regForm input').bind('keyup', function(event) {
			if (event.keyCode == '13') {
				$('#user_reg_regForm').submit();
			}
		});
	});
</script>
<!-- 隐藏的注册页面 -->
<!-- 
		if($('#index_regForm').form('validate')){
						$.ajax({
 							 type: 'POST',
 							 url : 'userAction!regAction.action',
 							 dataType: 'json',
 							 data: $('#index_regForm').serialize(),
 							 success: function(data){
								if(data.success){
									$('#index_regDialog').dialog('close');
								}
								$.messager.show({
									title:'提示',
									msg:data.msg,
								});
							}
						
								}
								-->
<div id="user_reg_regDialog" style="width: 250px;" class="easyui-dialog" data-options="title:'注册',modal:true,closed:true,
		buttons:[{
					text:'注册',
					handler:function(){
						$('#user_reg_regForm').submit();
					}
				}]">

	<form id="user_reg_regForm" method="post">
		<table class="tableForm" style="margin: 5px;">
			<tr>
				<th>登录名</th>
				<td><input name="name" class="easyui-validatebox" data-options="required:true,missingMessage:'登陆名称必填'" />
				</td>
			</tr>
			<tr>
				<th>密码</th>
				<td><input name="pwd" class="easyui-validatebox" type="password" data-options="required:true" />
				</td>
			</tr>
			<tr>
				<th>重复密码</th>
				<td><input name="rePwd" class="easyui-validatebox" type="password" data-options="required:true" validType='eqPwd["#user_reg_regForm input[name=pwd]"]' />
				</td>
			</tr>
		</table>
	</form>
</div>
