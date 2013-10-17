<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	var editType;
	var editRow;
	var treegrid;
	var iconData;
	var dataTest;
	$(function() {
		treegrid = $('#qxgl_treegrid').treegrid({
			toolbar : [ {
				text : '展开',
				iconCls : 'icon-redo',
				handler : function() {
					var node = treegrid.treegrid('getSelected');
					if (node) {
						treegrid.treegrid('expandAll', node.id);
					} else {
						treegrid.treegrid('expandAll');
					}
				}
			}, '-', {
				text : '折叠',
				iconCls : 'icon-undo',
				handler : function() {
					var node = treegrid.treegrid('getSelected');
					if (node) {
						treegrid.treegrid('collapseAll', node.id);
					} else {
						treegrid.treegrid('collapseAll');
					}
				}
			}, '-', {
				text : '刷新',
				iconCls : 'icon-reload',
				handler : function() {
					editRow = undefined;
					editType = undefined;
					treegrid.treegrid('reload');
				}
			}, '-', {
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
						treegrid.treegrid('endEdit', editRow.id);
					}
				}
			}, '-', {
				text : '取消编辑',
				iconCls : 'icon-undo',
				handler : function() {
					if (editRow) {
						treegrid.treegrid('cancelEdit', editRow.id);
						var p = treegrid.treegrid('getParent', editRow.id);
						if (p) {
							treegrid.treegrid('reload', p.id);
						} else {
							treegrid.treegrid('reload');
						}
						editRow = undefined;
						editType = undefined;
					}
				}
			}, '-', {
				text : '取消选中',
				iconCls : 'icon-undo',
				handler : function() {
					treegrid.treegrid('unselectAll');
				}
			} ],
			url : 'authAction!treegrid.action',
			title : '',
			iconCls : 'icon-save',
			fit : true,
			fitColumns : false,
			nowrap : false,
			animate : false,
			border : false,
			idField : 'id',
			treeField : 'name',
			frozenColumns : [ [ {
				title : 'id',
				field : 'id',
				width : 150,
				hidden : true
			}, {
				field : 'name',
				title : '权限名称',
				width : 180,
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
				title : '权限地址',
				field : 'url',
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
				title : '描述',
				field : 'authDescribe',
				width : 200,
				editor : {
					type : 'text'
				},
				formatter : function(value) {
					if (value) {
						return wl.fs('<span title="{0}">{1}</span>', value, value);
					}
				}
			}, {
				title : '排序',
				field : 'seq',
				width : 150,
				editor : {
					type : 'numberbox',
					options : {
						min : 0,
						max : 999,
						required : true
					}
				}
			}, {
				title : '上级权限',
				field : 'pname',
				width : 150,
				editor : {
					type : 'combotree',
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
				}
			} ] ],
			onDblClickRow : function(row) {

				if (editRow != undefined) {
					treegrid.treegrid('endEdit', editRow.id);
				}
				if (editRow == undefined) {
					treegrid.treegrid('beginEdit', row.id);
					editRow = row;
					editType = 'edit';
					treegrid.treegrid('unselectAll');
				}
			},
			onAfterEdit : function(row, changes) {
				if (editType == undefined) {
					editRow = undefined;
					treegrid.treegrid('unselectAll');
					return;
				}
				//判断是否 更改了 上级菜单
				if (changes.ptext != undefined) {
					row.pid = row.pname;
				}
				var url = '';
				if (editType == 'add') {
					url = 'authAction!add.action';
				} else if (editType == 'edit') {
					url = 'authAction!add.action';
				}
				$.ajax({
					type : 'POST',
					url : url,
					dataType : 'json',
					data : row,
					success : function(data) {
						if (data.success) {
							treegrid.treegrid('reload');
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							editRow = undefined;
							editType = undefined;
						} else {
							treegrid.treegrid('beginEdit', editRow.id);
							$.messager.alert('错误', data.msg, 'error');
						}
						treegrid.treegrid('unselectAll');
					}
				});
			},
			onContextMenu : function(e, row) {
				e.preventDefault();
				$(this).treegrid('unselectAll');
				$(this).treegrid('select', row.id);
				$('#admin_qxgl_menu').menu('show', {
					left : e.pageX,
					top : e.pageY
				});
			},
			onExpand : function(row) {
				treegrid.treegrid('unselectAll');
			},
			onCollapse : function(row) {
				treegrid.treegrid('unselectAll');
			}
		});
	});
	function edit() {
		var node = treegrid.treegrid('getSelected');
		if (node && node.id) {
			if (editRow != undefined) {
				treegrid.treegrid('endEdit', editRow.id);
			}
			if (editRow == undefined) {
				treegrid.treegrid('beginEdit', node.id);

				editRow = node;
				editType = 'edit';
			}
		}
	}
	function remove() {
		var node = treegrid.treegrid('getSelected');
		if (node) {
			$.messager.confirm('提示', '您确定要删除【' + node.text + '】？', function(r) {
				if (r) {
					$.ajax({
						type : 'POST',
						url : 'authAction!delete.action',
						dataType : 'json',
						data : node,
						success : function(data) {
							if (data.success) {
								treegrid.treegrid('remove', data.obj);
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
		console.log(editRow);
		if (editRow != undefined) {
			treegrid.treegrid('endEdit', editRow.id);
		}
		if (editRow == undefined) {
			var node = treegrid.treegrid('getSelected');
			var row = [ {
				id : wl.UUID(),
				name : '菜单名称',
				url : '',
				pid : node == null ? '' : node.id,
				seq : 10
			} ];
			treegrid.treegrid('append', {
				parent : node == null ? '' : node.id,
				data : row
			});
			editRow = row[0];
			editType = 'add';
			treegrid.treegrid('select', editRow.id);
			treegrid.treegrid('beginEdit', editRow.id);
		}
	}
</script>
<div class="easyui-layout" data-options="fit:true,border:false,">
	<div region="center" border="false" style="overflow: hidden;">
		<table id="qxgl_treegrid"></table>
	</div>

	<div id="admin_qxgl_menu" class="easyui-menu" style="width:120px;display: none;">
		<div onclick="append();" iconCls="icon-add">增加</div>
		<div onclick="remove();" iconCls="icon-remove">删除</div>
		<div onclick="edit();" iconCls="icon-edit">编辑</div>
	</div>

</div>

