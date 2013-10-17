<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	var editRow = undefined;
	var datagrid;
	var iconData;
	var dataTest;
	$(function() {
		datagrid = $('#jsgl_datagrid').datagrid({
			toolbar : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					append();
				}
			}, '-', {
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
					remove();
				}
			}, '-', {
				text : '编辑',
				iconCls : 'icon-edit',
				handler : function() {
					edit();
				}
			}, '-', {
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
					if (editRow != undefined) {
						datagrid.datagrid('endEdit', editRow);
					}
				}
			}, '-', {
				text : '取消编辑',
				iconCls : 'icon-undo',
				handler : function() {
					if (editRow) {
						datagrid.datagrid('cancelEdit', editRow);
						var p = datagrid.datagrid('getParent', editRow);
						if (p) {
							datagrid.datagrid('reload', p.id);
						} else {
							datagrid.datagrid('reload');
						}
						editRow = undefined;
						editType = undefined;
					}
				}
			}, '-', {
				text : '取消选中',
				iconCls : 'icon-undo',
				handler : function() {
					datagrid.datagrid('unselectAll');
				}
			} ],
			url : 'roleAction!datagrid.action',
			title : '',
			iconCls : 'icon-save',
			pagination : true,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40 ],
			fit : true,
			fitColumns : true,
			nowrap : false,
			border : false,
			idField : 'id',
			sortName : 'name',
			sortOrder : 'desc',
			frozenColumns : [ [ {
				title : '编号',
				field : 'id',
				width : 150,
				checkbox : true,
				sortable : true,
			}, {
				field : 'name',
				title : '角色名称',
				width : 150,
				editor : {
					type : 'validatebox',
					options : {
						required : true
					}
				},
				formatter : function(value) {
					if (value) {
						return wl.fs('<span title="{0}">{1}</span>', value, value);
					}
				}
			} ] ],

			columns : [ [ {
				title : '备注',
				field : 'roleRescribe',
				width : 250,
				editor : {
					type : 'text'
				},
				formatter : function(value) {
					if (value) {
						return wl.fs('<span title="{0}">{1}</span>', value, value);
					}
				}
			}, {
				title : '拥有权限',
				field : 'authIds',
				width : 300,
				editor : {
					type : 'combocheckboxtree',
					options : {
						url : 'authAction!menuTreeRecursive.action',
						animate : false,
						lines : !wl.isLessThanIe8(),
						onLoadSuccess : function(row, data) {
							var t = $(this);
							if (data) {
								$(data).each(function(index, d) {
									if (this.state == 'closed') {
										t.tree('expandAll');
									}
								});
							}
						}
					}
				},
				formatter : function(value, rowData, rowIndex) {
					if (rowData.authNames) {
						return wl.fs('<span title="{0}">{1}</span>', rowData.authNames, rowData.authNames);
					}
				}
			}, {
				title : '拥有权限',
				field : 'authNames',
				width : 150,
				hidden : true
			} ] ],
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
				console.log(rowData);
				/**if (inserted.length > 0) {
					url = 'authAction!add.action';
				} else if (updated.length > 0) {
					url = 'authAction!add.action';
				}**/
				$.ajax({
					type : 'POST',
					url : 'roleAction!addOrEdit.action',
					dataType : 'json',
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
				$('#admin_jsgl_menu').menu('show', {
					left : e.pageX,
					top : e.pageY
				});
			}
		});
	});
	function edit() {
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			if (editRow != undefined) {
				datagrid.datagrid('endEdit', editRow);
			}
			if (editRow == undefined) {
				editRow = datagrid.datagrid('getRowIndex', rows[0]);
				datagrid.datagrid('beginEdit', editRow);
				datagrid.datagrid('unselectAll');
			}
		} else {
			$.messager.show({
				msg : '请选择一项进行修改！',
				title : '错误'
			});
		}
	}
	function remove() {
		var rows = datagrid.datagrid('getSelections');
		var ids = [];
		if (rows.length > 0) {
			$.messager.confirm('提示', '您确定要删除所选项目？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					$.ajax({
						type : 'POST',
						url : 'roleAction!delete.action',
						dataType : 'json',
						data : {
							ids : ids.join(',')
						},
						success : function(data) {
							if (data.success) {
								datagrid.datagrid('load');
								datagrid.datagrid('unselectAll');
								editRow = undefined;
							}
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
						}
					});
				}
			});
		}
	}
	function append() {
		if (editRow != undefined) {
			datagrid.datagrid('endEdit', editRow);
		}
		if (editRow == undefined) {
			datagrid.datagrid('unselectAll');
			var row = {
				id : wl.UUID()
			};
			datagrid.datagrid('appendRow', row);
			editRow = datagrid.datagrid('getRows').length - 1;
			datagrid.datagrid('selectRow', editRow);
			datagrid.datagrid('beginEdit', editRow);
		}
	}
</script>
<div class="easyui-layout" data-options="fit:true,border:false,">
	<div region="center" border="false" style="overflow: hidden;">
		<table id="jsgl_datagrid"></table>
	</div>

	<div id="admin_jsgl_menu" class="easyui-menu" style="width:120px;display: none;">
		<div onclick="append();" iconCls="icon-add">增加</div>
		<div onclick="remove();" iconCls="icon-remove">删除</div>
		<div onclick="edit();" iconCls="icon-edit">编辑</div>
	</div>

</div>

