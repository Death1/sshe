<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	
</script>
<form id="admin_yhglEdit_form" method="post">
	<table class="tableForm">
		<tr>
			<th>登录名</th>
			<td><input name="name" class="easyui-validatebox" data-options="required:true,missingMessage:'登陆名称必填'" />
			</td>
			<th>密码</th>
			<td><input name="pwd" class="easyui-validatebox" type="password" data-options="required:true" />
			</td>
		</tr>
		<tr>
			<th>创建时间</th>
			<td><input name="createDateTime" />
			</td>
			<th>最后修改时间</th>
			<td><input name="modifyDateTime" />
			</td>
		</tr>
		<tr>
			<td>
			<td><input name="id" type="hidden"></td>
			</td>

		</tr>
	</table>
</form>
