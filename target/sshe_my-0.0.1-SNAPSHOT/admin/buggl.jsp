<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	var datagrid;
	var bugAddDialog;
	var bugAddForm;
	var cdescAdd;
	var cdescEdit;
	var showBugDescribeDialog;
	var bugEditDialog;
	var bugEditForm;
	$(function() {
		datagrid = $('#datagrid').datagrid({
			url : 'bugAction!datagrid.action',
			title : 'BUG列表',
			pagination : true,
			pagePosition : 'bottom',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40 ],
			fit : true,
			fitColumns : true,
			nowrap : false,
			border : false,
			idField : 'id',
			sortName : 'createDateTime',
			sortOrder : 'desc',
			frozenColumns : [ [ {
				title : '编号',
				field : 'id',
				width : 150,
				sortable : true,
				checkbox : true
			}, {
				title : 'BUG名称',
				field : 'name',
				width : 150,
				sortable : true
			} ] ],
			columns : [ [ {
				title : 'BUG创建时间',
				field : 'createDateTime',
				sortable : true,
				width : 150
			}, {
				title : 'BUG描述',
				field : 'bugDescribe',
				width : 150,
				formatter : function(value, rowData, rowIndex) {
					return '<span class="icon-search" style="display:inline-block;vertical-align:middle;width:16px;height:16px;"></span><a href="javascript:void(0);" onclick="showBugDescribe(' + rowIndex + ');">查看详细</a>';
				}
			} ] ],
			toolbar : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					add();
				}
			}, '-', {
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
					del();
				}
			}, '-', {
				text : '修改',
				iconCls : 'icon-edit',
				handler : function() {
					edit();
				}
			}, '-', {
				text : '取消选中',
				iconCls : 'icon-undo',
				handler : function() {
					datagrid.datagrid('unselectAll');
				}
			}, '-' ],
			onRowContextMenu : function(e, rowIndex, rowData) {
				e.preventDefault();
				$(this).datagrid('unselectAll');
				$(this).datagrid('selectRow', rowIndex);
				$('#menu').menu('show', {
					left : e.pageX,
					top : e.pageY
				});
			}
		});
		bugAddForm = $('#bugAddForm').form({
			url : 'bugAction!saveOrUpdate.action',
			success : function(data) {
				var json = $.parseJSON(data);
				if (json && json.success) {
					datagrid.datagrid('reload');
					bugAddDialog.dialog('close');
				}
				$.messager.show({
					title : "提示",
					msg : json.msg
				});
			}
		});
		bugEditForm = $('#bugEditForm').form({
			url : 'bugAction!saveOrUpdate.action',
			success : function(data) {
				var json = $.parseJSON(data);
				if (json.success) {
					datagrid.datagrid('reload');
					bugEditDialog.dialog('close');
				}
				$.messager.show({
					title : "提示",
					msg : json.msg
				});
			}
		});
		bugAddDialog = $('#bugAddDialog').show().dialog({
			title : '添加BUG',
			closed : true,
			modal : true,
			maximizable : true,
			buttons : [ {
				text : '添加',
				handler : function() {
					bugAddForm.submit();
				}
			} ]
		});
		bugEditDialog = $('#bugEditDialog').show().dialog({
			title : '添加BUG',
			closed : true,
			modal : true,
			maximizable : true,
			buttons : [ {
				text : '编辑',
				handler : function() {
					bugEditForm.submit();
				}
			} ]
		});
		cdescAdd = $('#cdescAdd').xheditor({
			tools : 'mini',
			html5Upload : true,
			upMultiple : 4,
			upLinkUrl : 'bugAction!upload.action',
			upLinkExt : 'zip,rar,txt,doc,docx,xls,xlsx',
			upImgUrl : 'bugAction!upload.action',
			upImgExt : 'jpg,jpeg,gif,png'
		});
		cdescEdit = $('#cdescEdit').xheditor({
			tools : 'mini',
			html5Upload : true,
			upMultiple : 4,
			upLinkUrl : 'bugAction!upload.action',
			upLinkExt : 'zip,rar,txt,doc,docx,xls,xlsx',
			upImgUrl : 'bugAction!upload.action',
			upImgExt : 'jpg,jpeg,gif,png'
		});
		$('#createTime').datetimebox({
			editable : false,
		});
		$('#bugName').validatebox({
			required : true,
		});
		showBugDescribeDialog = $('#showBugDescribeDialog').show().dialog({
			title : 'BUG描述',
			closed : true,
			modal : true,
			maximizable : true,
		});
	});
	function showBugDescribe(index) {
		var rows = datagrid.datagrid('getRows');
		var row = rows[index];
		$.messager.progress({
			title : '数据加载中...',
			interval : 100
		});
		$.ajax({
			type : 'POST',
			url : 'bugAction!showBugDescribeDialog.action',
			dataType : 'JSON',
			data : {
				id : row.id
			},
			success : function(json) {
				if (json && json.bugDescribe) {
					showBugDescribeDialog.find('div[name=bugDescribe]').html(json.bugDescribe);
					showBugDescribeDialog.dialog('open');
				} else {
					$.messager.alert('提示', '没有BUG描述', 'error');
				}
				$.messager.progress('close');
			}
		});
		datagrid.datagrid('unselectAll');
	}
	function add() {
		bugAddForm.find('input,textarea').val('');
		$('div.validatebox-tip').remove();
		bugAddDialog.dialog('open');
	}
	function del() {
		var rows = datagrid.datagrid('getSelections');
		var ids = [];
		console.log(rows.length);
		console.log(rows);
		if (rows.length > 0) {
			$.messager.confirm('提示', '您要删除当前所选的项目？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id)
					}
					$.ajax({
						type : 'POST',
						url : 'bugAction!delete.action',
						dataType : 'JSON',
						data : {
							ids : ids.join(",")
						},
						success : function(json) {
							datagrid.datagrid('load');
							datagrid.datagrid('unselectAll');
							$.messager.show({
								title : '提示',
								msg : json.msg
							});
						}
					});
				}
			});
		} else {
			$.messager.alert('提示', '请选择要删除的记录', 'error')
		}
	}
	function edit() {
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			$.messager.progress({
				text : '数据加载中....',
				interval : 100
			});
			$.ajax({
				type : 'POST',
				url : 'bugAction!showBugDescribeDialog.action',
				dataType : 'JSON',
				data : {
					id : rows[0].id
				},
				success : function(json) {
					bugEditForm.form('load', json);
					$('div.validatebox-tip').remove();
					bugEditDialog.dialog('open');
					$.messager.progress('close');
				}
			});

		} else {
			$.messager.alert('提示', '请选择一项要编辑的记录！', 'error');
		}
	}
</script>

<div id="admin_buggl_layout" class="easyui-layout" data-options="fit:true,border:false,">
	<div data-options="region:'center',border:false">
		<table id="datagrid"></table>
	</div>
	<div id="menu" class="easyui-menu" style="width:120px;display: none;">
		<div onclick="add();" iconCls="icon-add">增加</div>
		<div onclick="del();" iconCls="icon-remove">删除</div>
		<div onclick="edit();" iconCls="icon-edit">编辑</div>
	</div>

	<div id="bugAddDialog" style="display: none;width: 500px;height: 300px;" align="center">
		<form id="bugAddForm" method="post">
			<table class="tableForm">
				<tr>
					<th>BUG名称</th>
					<td><input id="bugName" name="name" missingMessage="请填写BUG名称" /></td>
					<th>创建时间</th>
					<td><input id="createTime" name="createDateTime" style="width: 155px;" />
					</td>
				</tr>
				<tr>
					<th>BUG描述</th>
					<td colspan="3"><textarea name="bugDescribe" id="cdescAdd"></textarea></td>
				</tr>
			</table>
		</form>
	</div>
	<div id="bugEditDialog" style="display: none;width: 500px;height: 300px;" align="center">
		<form id="bugEditForm" method="post">
			<input type="hidden" name="id" />
			<table class="tableForm">
				<tr>
					<th>BUG名称</th>
					<td><input id="bugName" name="name" missingMessage="请填写BUG名称" /></td>
					<th>创建时间</th>
					<td><input id="createTime" name="createDateTime" style="width: 155px;" />
					</td>
				</tr>
				<tr>
					<th>BUG描述</th>
					<td colspan="3"><textarea name="bugDescribe" id="cdescEdit"></textarea></td>
				</tr>
			</table>
		</form>
	</div>
	<div id="showBugDescribeDialog" style="display: none;overflow: auto;width: 500px;height: 400px;">
		<div name="bugDescribe"></div>
	</div>
</div>
