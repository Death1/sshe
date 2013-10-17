<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript" charset="utf-8">
	var portal;
	var panels;
	$(function() {
		panels = [ {
			id : 'p1',
			title : 'about',
			height : 200,
			collapsible : true,
			href : 'layout/portal/about.jsp'
		}, {
			id : 'p2',
			title : 'link',
			height : 200,
			collapsible : true,
			href : 'layout/portal/link.jsp'
		}, {
			id : 'p3',
			title : '修复数据库',
			height : 200,
			collapsible : true,
			href : 'layout/portal/repair.jsp'
		}, {
			id : 'p4',
			title : '说明',
			height : 200,
			collapsible : true,
			content : '<h1>可以拖动面板的顺序，他会记住的，下次打开的时候，还是你之前调节过的顺序哦</h1>'
		}, {
			id : 'p5',
			title : '说明2',
			height : 200,
			collapsible : true,
			href : 'layout/portal/about2.jsp'
		}, {
			id : 'p6',
			title : 'EasyUI的QQ群',
			height : 200,
			collapsible : true,
			href : 'layout/portal/qun.jsp'
		} ];

		portal = $('#portal').portal({
			border : false,
			fit : true,
			onStateChange : function() {
				$.cookie('portal-state', getPortalState(), {//把portal-state存入 cookie
					expires : 7
				})
			}
		});
		var state = $.cookie('portal-state');
		if (!state) {
			state = 'p1,p2,p3:p4,p5,p6';//冒号代表列，逗号代表行
		}

		addPanels(state);
		portal.portal('resize');

	});
	function getPortalState() {
		var aa = [];
		for ( var columnIndex = 0; columnIndex < 2; columnIndex++) {
			var cc = [];
			var panels = portal.portal('getPanels', columnIndex); //得到一列panels
			for ( var i = 0; i < panels.length; i++) {
				cc.push(panels[i].attr('id')); //存入id
			}
			aa.push(cc.join(','));
		}
		return aa.join(':');
	}

	function addPanels(protalState) {
		var columns = protalState.split(':');
		for ( var columnIndex = 0; columnIndex < columns.length; columnIndex++) {//columnIndex第一列
			var cc = columns[columnIndex].split(',');//得到了[p1,p2,p3]
			for ( var j = 0; j < cc.length; j++) {
				var options = getPanelOptions(cc[j]); //为了获得panels对象
				if (options) {
					var p = $('<div></div>').attr('id', options.id).appendTo('body');
					p.panel(options);
					portal.portal('add', {
						panel : p,
						columnIndex : columnIndex
					});
				}
			}
		}
	}
	function getPanelOptions(id) {
		for ( var i = 0; i < panels.length; i++) {
			if (panels[i].id == id) {
				return panels[i];
			}
		}
		return undefined;
	}
</script>
<div id="portal" style="position:relative">
	<div></div>
	<div></div>
</div>