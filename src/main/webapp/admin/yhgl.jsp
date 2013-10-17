<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	var datagrid;
	var editRow;
	var modifyUserRoleCombobox;
	var modifyUserRoleForm;
	var modifyUserRoleDialog;
	var modifyPwdDialog;
	var modifyPwdForm;
	var searchForm
	$(function() {
		searchForm = $('#searchForm').form();
		datagrid = $('#admin_yhgl_datagrid').datagrid({
			url : 'userAction!dataGird.action',
			fit : true,
			border : false,
			pagination : true,
			idField : 'id',
			fitColumns : true,
			sortName : 'name',
			sortOrder : 'desc',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40 ],
			frozenColumns : [ [ {
				field : 'id',
				title : '编号',
				width : 150,
				checkbox : true,
			//hidden : true
			}, {
				field : 'name',
				title : '登录名称',
				width : 150,
				sortable : true,
				editor : {
					type : 'validatebox',
					options : {
						required : true
					}
				}
			} ] ],
			columns : [ [ {
				field : 'pwd',
				title : '密码',
				width : 150,
				formatter : function(value, row, index) {
					return '******';
				}
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
			}, {
				field : 'roleIds',
				title : '所属角色',
				width : 150,
				formatter : function(value, rowData, rowIndex) {
					if (rowData.roleNames) {
						return wl.fs('<span title="{0}">{1}</span>', rowData.roleNames, rowData.roleNames);
					}
				},
				editor : {
					type : 'multiplecombobox',
					options : {
						url : 'roleAction!roleCombobox.action',
						valueField : 'id',
						textField : 'name'
					}
				}
			}, {
				field : 'roleNames',
				title : '所属角色',
				width : 150,
				hidden : true
			} ] ],

			toolbar : [ {
				iconCls : 'icon-add',
				text : '增加',
				handler : function() {
					add();
				}
			}, '-', {
				iconCls : 'icon-remove',
				text : '删除',
				handler : function() {
					remove();
				}
			}, '-', {
				iconCls : 'icon-edit',
				text : '编辑',
				handler : function() {
					editFun();
				}
			}, '-', {
				iconCls : 'icon-save',
				text : '保存',
				handler : function() {
					editFun();
				}
			}, '-', {
				iconCls : 'icon-undo',
				text : '取消编辑',
				handler : function() {
					datagrid.datagrid('unselectAll');
					datagrid.datagrid('rejectChanges');
					editRow = undefined;
				}
			}, '-', {
				iconCls : 'icon-undo',
				text : '取消选中',
				handler : function() {
					datagrid.datagrid('unselectAll');
				}
			}, '-', {
				iconCls : 'icon-edit',
				text : '修改密码',
				handler : function() {
					var rows = datagrid.datagrid('getSelections');
					var ids = [];
					if (rows.length > 0) {
						for ( var i = 0; i < rows.length; i++) {
							ids.push(rows[i].id);
						}
						modifyPwdForm.find('input[name=ids]').val(ids.join(','));
						modifyPwdDialog.dialog('open');
					}
				}
			}, '-', {
				iconCls : 'icon-edit',
				text : '批量修改角色',
				handler : function() {
					var rows = datagrid.datagrid('getSelections');
					var ids = [];
					if (rows.length > 0) {
						for ( var i = 0; i < rows.length; i++) {
							ids.push(rows[i].id);
						}
						modifyUserRoleForm.find('input[name=ids]').val(ids.join(','));
						modifyUserRoleCombobox.combobox({
							url : 'roleAction!roleCombobox.action'
						});
						modifyUserRoleDialog.dialog('open');
					}
				}
			} ],
			onDblClickRow : function(rowIndex, rowData) {

				if (editRow != undefined) {
					datagrid.datagrid('endEdit', editRow);
				}
				if (editRow == undefined) {
					datagrid.datagrid('beginEdit', rowIndex);
					editRow = rowIndex;
					datagrid.datagrid('unselectAll');
				}
			},
			onAfterEdit : function(rowIndex, rowData, changes) {
				var inserted = datagrid.datagrid('getChanges', 'inserted');
				var updated = datagrid.datagrid('getChanges', 'updated');
				if (inserted.length < 1 && updated.length < 1) {
					editRow = undefined;
					datagrid.datagrid('unselectAll');
					return;
				}
				var url = '';
				if (inserted.length > 0) {
					url = 'userAction!add.action';
				}
				if (updated.length > 0) {
					url = 'userAcion!edit.action';
				}
				$.ajax({
					type : 'POST',
					url : url,
					dataType : 'JSON',
					data : rowData,
					success : function(data) {
						if (data.success) {
							datagrid.datagrid('acceptChanges');
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							editRow = undefined;
							datagrid.datagrid('reload');
						} else {
							datagrid.datagrid('beginEdit', editRow);
							$.messager.alert('错误', data.msg, 'error');
						}
						datagrid.datagrid('unselectAll');
					}
				});
			},
			onRowContextMenu : function(e, rowIndex, rowData) {
				e.preventDefault();
				$(this).datagrid('unselectAll');
				$(this).datagrid('selectRow', rowIndex);
				$('#admin_yhgl_menu').menu('show', {
					left : e.pageX,
					top : e.pageY
				});
			}
		});
		modifyUserRoleCombobox = $('#modifyUserRoleCombobox').combobox({
			valueField : 'id',
			textField : 'name',
			multiple : true,
			editable : false,
			panelHeight : 'auto'
		});

		modifyUserRoleForm = $('#modifyUserRoleForm').form({
			url : 'userAction!modifyUserRole.action',
			success : function(data) {
				var json = $.parseJSON(data);
				if (json.success) {
					datagrid.datagrid('reload');
					modifyPwdDialog.dialog('close');
					datagrid.datagrid('unselectAll');
				}
				$.messager.show({
					msg : json.msg,
					title : '提示'
				});
			}
		});
		modifyPwdForm = $('#modifyPwdForm').form({
			url : 'userAction!modifyUserPwd.action',
			success : function(data) {
				var json = $.parseJSON(data);
				if (json.success) {
					datagrid.datagrid('reload');
					modifyPwdDialog.dialog('close');
					datagrid.datagrid('unselectAll');
				}
				$.messager.show({
					msg : json.msg,
					title : '提示'
				});
			}
		});
		modifyUserRoleDialog = $('#modifyUserRoleDialog').dialog({
			modal : true,
			title : '批量修改角色',
			buttons : [ {
				text : '修改',
				handler : function() {
					modifyUserRoleForm.submit();
				}
			} ]
		}).dialog('close');
		modifyPwdDialog = $('#modifyPwdDialog').show().dialog({
			modal : true,
			title : '批量修改密码',
			buttons : [ {
				text : '修改',
				handler : function() {
					modifyPwdForm.submit();
				}
			} ]
		}).dialog('close');
		$('#pwd').validatebox({
			required : true,
			missingMessage : '请填写新密码'
		});
		$('#repwd').validatebox({
			validType : 'eqPwd["#pwd"]',
			required : true,
			missingMessage : '请再次填写新密码'
		});

	});
	function searchFun() {
		datagrid.datagrid('load', serializeObject(searchForm));
	}

	function clearFun() {
		datagrid.datagrid('load', {});
		searchForm.find('input').val('');
	}
	function remove() {
		var rows = datagrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			$.messager.confirm('提示', '确定要删除这些用户?', function(r) {
				if (r) {

					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					$.ajax({
						type : "POST",
						url : "userAction!removeAction.action",
						data : {
							ids : ids.join(','),
						},
						dataType : "JSON",
						success : function(data) {
							if (data.success) {
								datagrid.datagrid('load');
								$.messager.show({
									title : '提示',
									msg : data.msg,
								});
							}
						}
					});
				}
			});
		} else {
			$.messager.alert('提示', '请选中要删除的数据');
		}
	}
	function add() {
		if (editRow != undefined) {
			datagrid.datagrid('endEdit', editRow);
		}
		if (editRow == undefined) {
			datagrid.datagrid('unselectAll');
			//添加行时,改变editor
			changeEditorAddRow();
			var row = {
				id : wl.UUID()
			};
			datagrid.datagrid('appendRow', row);
			editRow = datagrid.datagrid('getRows').length - 1;
			datagrid.datagrid('selectRow', editRow);
			datagrid.datagrid('beginEdit', editRow);
		}
	}
	function edit() {
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			if (editRow != undefined) {
				datagrid.datagrid('endEdit', editRow);
			}
			if (editRow == undefined) {
				changeEditorEditRow();
				editRow = datagrid.datagrid('gerRowIndex', rows[0]);
				datagrid.datagrid('beginEdit', editRow);
				datagrid.datagrid('unselectAll');
			}
		} else {
			$.messager.show({
				msg : '请选择一项进行修改!',
				title : '提示'
			});
		}
	}
	function changeEditorAddRow() {
		datagrid.datagrid('addEditor', {
			field : 'pwd',
			editor : {
				type : 'validatebox',
				options : {
					required : true
				}
			}
		});
		datagrid.datagrid('removeEditor', [ 'createDateTime', 'modifyDateTime' ]);

	}
	function changeEditorEditRow() {
		datagrid.datagrid('removeEditor', 'pwd');
		datagrid.datagrid('addEditor', [ {
			field : 'createDateTime',
			editor : {
				type : 'datetimebox',
				options : {
					editable : false
				}
			}
		}, {
			field : 'modifyDateTime',
			editor : {
				type : 'datetimebox',
				options : {
					editable : false
				}
			}
		} ]);
	}
	function editFun() {
		var rows = datagrid.datagrid('getChecked');
		if (rows.length == 1) {
			$('<div id="admin_yhgl_editDialog"></div>').dialog({
				width : 500,
				height : 150,
				href : 'admin/yjglEdit.jsp',
				modal : true,
				title : '编辑用户',
				buttons : [ {
					text : '编辑',
					iconCls : 'icon-ok',
					handler : function() {
						$('#admin_yhglEdit_form').form('submit', {
							url : 'userAction!editAction.action',
							success : function(data) {
								var obj = jQuery.parseJSON(data);
								if (obj.success) {
									datagrid.datagrid('updateRow', {
										index : datagrid.datagrid('getRowIndex', rows[0]),
										row : obj.obj
									});
									$('#admin_yhgl_editDialog').dialog('close');
								}
								$.messager.show({
									title : '提示',
									msg : obj.msg
								});
							}
						});
					}
				}, {
					text : '取消',
					iconCls : 'icon-cancel',
					handler : function() {
						$('#admin_yhgl_editDialog').dialog('close');
					}
				} ],
				onClose : function() {
					$(this).dialog('destroy');
				},
				onLoad : function() {
					$('#admin_yhglEdit_form').form('load', rows[0]);
				}
			});
		} else {
			$.messager.alert('提示', '只能选择一条数据');
		}
	}

	$('#admin_yhgl_addForm').form({
		url : 'userAction!addAction.action',
		success : function(data) {
			var obj = jQuery.parseJSON(data);
			if (obj.success) {
				//$('#admin_yhgl_datagrid').datagrid('load');
				//$('#admin_yhgl_datagrid').datagrid('appendRow', obj.obj);
				datagrid.datagrid('insertRow', {
					index : 0,
					row : obj.obj
				});
				$('#admin_yhgl_addDialog').dialog('close');
			}
			$.messager.show({
				title : '提示',
				msg : obj.msg,
			});
		}
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false,">
	<div data-options="region:'north',title:'查询条件',border:false" style="height:110px; " align="left">
		<form id="searchForm">
			<table class="tableForm datagrid-toolbar" style="width: 100%;height: 100%;">
				<tr>
					<th>用户名</th>
					<td><input name="name" style="width: 315px;" /></td>
				</tr>
				<tr>
					<th>创建时间</th>
					<td><input name="createDateTimeStart" class="easyui-datetimebox" editable="false" style="width:155px; " />至<input name="createDateTimeEnd" class="easyui-datetimebox" editable="false" style="width:155px; " />
					</td>
				</tr>
				<tr>
					<th>最后修改时间</th>
					<td><input name="modifyDateTimeStart" class="easyui-datetimebox" editable="false" style="width:155px; " />至<input name="modifyDateTimeEnd" class="easyui-datetimebox" editable="false" style="width:155px; " /> <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun()">查询</a> <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="clearFun()">清空</a>
					</td>
				</tr>
				</form>
			</table>
	</div>
	<div data-options="region:'center',border:false">
		<table id="admin_yhgl_datagrid"></table>
	</div>
	<div id="admin_yhgl_menu" class="easyui-menu" style="width:120px;display: none;">
		<div onclick="add();" iconCls="icon-add">增加</div>
		<div onclick="remove();" iconCls="icon-remove">删除</div>
		<div onclick="edit();" iconCls="icon-edit">编辑</div>
	</div>
	<div id="modifyUserRoleDialog">
		<form id="modifyUserRoleForm">
			<input name="ids" type="hidden" />
			<table class="tableForm">
				<tr>
					<th>角色</th>
					<td><input name="roleIds" id="modifyUserRoleCombobox" style="width:150px;" />
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="modifyPwdDialog" style="padding: 5px;" align="center">
		<form id="modifyPwdForm">
			<input name="ids" type="hidden" />
			<table class="tableForm" method="post">
				<tr>
					<th>新密码</th>
					<td><input id="pwd" name="pwd" type="password" />
					</td>
				</tr>
				<tr>
					<th>重复密码</th>
					<td><input id="repwd" name="repwd" type="password" />
					</td>
				</tr>
			</table>
		</form>
	</div>

</div>
