<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	var calendar;
	var onlinePanel;
	var onlineDatagrid;
	var userOnlineInfoDataGrid;
	var userOnlineInfoDialog;
	$(function() {
		calendar = $('#calendar').calendar({
			current : new Date(),
			fit : true,
			border : false,
			onSelect : function(date) {
				$(this).calendar('moveTo', new Date());
			}
		});
		onlinePanel = $('#onlinePanel').panel({
			tools : [ {
				iconCls : 'icon-reload',
				handler : function() {
					if (onlineDatagrid.datagrid('options').url) {
						onlineDatagrid.datagrid('load');
					}
				}
			} ]
		});
		userOnlineInfoDataGrid = $('#userOnlineInfoDataGrid').datagrid({
			showHeader : false,
			fit : true,
			fitColumns : true,
			scrollbarSize : 0,
			border : false,
			columns : [ [ {
				title : '',
				width : 100,
				field : 'value'
			}, {
				title : '',
				width : 150,
				field : 'name'
			} ] ]
		});
		userOnlineInfoDialog = $('#userOnlineInfoDialog').dialog({
			modal : true,
			closed : true,
			title : '明细'
		});
		onlineDatagrid = $('#onlineDatagrid').datagrid({
			/*url : 'onlineAction!datagrid.action',*/
			title : '',
			iconCls : '',
			fit : true,
			fitColumns : true,
			pagination : false,
			pageSize : 10,
			pageList : [ 10 ],
			nowarp : false,
			border : false,
			idField : 'id',
			sortName : 'datetime',
			sortOrder : 'desc',
			frozenColumns : [ [ {
				title : '编号',
				field : 'id',
				width : 150,
				hidden : true
			} ] ],
			columns : [ [ {
				title : '登录名',
				field : 'name',
				width : 150,
				sortable : true,
				formatter : function(value, rowData, rowIndex) {
					return wl.fs('<span title="{0}">{1}</span>', value, value);
				}
			}, {
				title : 'IP',
				field : 'ip',
				width : 150,
				sortable : true,
				formatter : function(value, rowData, rowIndex) {
					return wl.fs('<span title="{0}">{1}</span>', value, value);
				}
			}, {
				title : '登录时间',
				field : 'datetime',
				width : 150,
				sortable : true,
				formatter : function(value, rowData, rowIndex) {
					return wl.fs('<span title="{0}">{1}</span>', value, value);
				}
			} ] ],
			onClickRow : function(rowIndex, rowData) {
				userOnlineInfoDataGrid.datagrid('loadData', [ {
					value : '登录名',
					name : rowData.name
				}, {
					value : 'IP',
					name : rowData.ip
				}, {
					value : '时间',
					name : rowData.datetime
				} ]);
				userOnlineInfoDialog.dialog('open');
				$(this).datagrid('unselectAll');
			},
			onLoadSuccess : function(data) {
				onlinePanel.panel('setTitle', '(' + data.total + ')人在线');
			}
		});
	});
</script>

<div class="easyui-layout" fit="true" border="false">
	<div region="north" border="false" style="height:180px;overflow: hidden;">
		<div id="calendar"></div>
	</div>
	<div region="center" border="false" style="overflow: hidden;">
		<div id="onlinePanel" fit="true" border="false" title="用户在线列表">
			<table id="onlineDatagrid"></table>
		</div>
	</div>

	<div id="userOnlineInfoDialog" style="width: 250px;height: 130px;">
		<table id="userOnlineInfoDataGrid"></table>
	</div>
</div>

