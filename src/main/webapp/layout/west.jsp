<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	var tree;
	var menuPanel;
	$(function() {
		tree = $('#layout_west_tree').tree({
			url : 'menuAction!getTree.action',
			lines : true,
			onClick : function(node) {
				if (node.attributes.url) {
					addTab(node);
				}
			},
			onDblClick : function(node) {
				if (node.state == 'closed') {
					$(this).tree('expand', node.target);
				} else {
					$(this).tree('collapse', node.target);
				}
			}
		});
		menuPanel = $('#menuPanel').panel({
			tools : [ {
				iconCls : 'icon-reload',
				handler : function() {
					tree.tree('reload');
				}
			}, {
				iconCls : 'icon-redo',
				handler : function() {
					var node = tree.tree('getSelected');
					if (node) {
						tree.tree('expandAll', node.target);
					} else {
						tree.tree('expandAll');
					}
				}
			}, {
				iconCls : 'icon-undo',
				handler : function() {
					var node = tree.tree('getSelected');
					if (node) {
						tree.tree('collapseAll', node.target);
					} else {
						tree.tree('collapseAll');
					}
				}
			} ]
		});
	});
</script>

<div class="easyui-accordion" data-options="fit:true,border:false">
	<div title="菜单">
		<div id="menuPanel" fit="true" border="false" title="功能菜单" style="padding: 5px;">
			<ul id="layout_west_tree"></ul>
		</div>
	</div>
	<div title="Title2" data-options="iconCls:'icon-reload'"></div>
</div>

