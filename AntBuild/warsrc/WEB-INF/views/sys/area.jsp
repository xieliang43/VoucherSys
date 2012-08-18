<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<div id="${param.id}"></div>

<script type="text/javascript">
Ext.ns("Ext.Authority.areas"); // 自定义一个命名空间
area = Ext.Authority.areas; // 定义命名空间的别名
area = {
	all : ctx + '/regionAction!loadAll.action',// 加载所有
	save : ctx + "/regionAction!save.action",//保存
	del : ctx + "/regionAction!delete.action",//删除
	AREAMAP :eval('(${areaMap})'),
	pageSize : 20, // 每页显示的记录数
	AREATYPE : eval('(${fields.areatype==null?"{}":fields.areatype})'),
	ENABLED : eval('(${fields.enabled==null?"{}":fields.enabled})')
};

/** 改变页的combo */
area.pageSizeCombo = new Share.pageSizeCombo({
			value : '20',
			listeners : {
				select : function(comboBox) {
					area.pageSize = parseInt(comboBox.getValue());
					area.bbar.pageSize = parseInt(comboBox.getValue());
					area.store.baseParams.limit = area.pageSize;
					area.store.baseParams.start = 0;
					area.store.load();
				}
			}
		});
// 覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
area.pageSize = parseInt(area.pageSizeCombo.getValue());
/** 基本信息-数据源 */
area.store = new Ext.data.Store({
			autoLoad : true,
			remoteSort : true,
			baseParams : {
				start : 0,
				limit : area.pageSize,
				name: null
			},
			proxy : new Ext.data.HttpProxy({// 获取数据的方式
				method : 'POST',
				url : area.all
			}),
			reader : new Ext.data.JsonReader({// 数据读取器
				totalProperty : 'results', // 记录总数
				root : 'rows' // Json中的列表数据根节点
			}, ['id', 'name', 'type', 'parentId', 'regionPrefix', 'enabled',
					'createDate']),
			listeners : {
				'load' : function(store, records, options) {
					area.alwaysFun();
				}
			}
		});
area.comboxParent = new Ext.form.ComboBox({
	emptyText : '请选择上级地区...',
	fieldLabel : '上级地区',
	hiddenName : 'parentId',
	name : 'parentId',
	triggerAction : 'all',
	mode : 'local',
	store : new Ext.data.ArrayStore({
				fields : ['v', 't'],
				data : Share.map2Ary(area.AREAMAP)
			}),
	valueField : 'v',
	displayField : 't',
	allowBlank : false,
	editable : false,
	anchor : '99%'
});

/** 基本信息-选择模式 */
area.selModel = new Ext.grid.CheckboxSelectionModel({
			singleSelect : true,
			listeners : {
				'rowselect' : function(selectionModel, rowIndex, record) {
					area.deleteAction.enable();
					area.editAction.enable();
				},
				'rowdeselect' : function(selectionModel, rowIndex, record) {
					area.alwaysFun();
				}
			}
		});
/** 基本信息-数据列 */
area.colModel = new Ext.grid.ColumnModel({
			defaults : {
				sortable : true,
				width : 140
			},
			columns : [area.selModel, {
						hidden : true,
						header : '地区编号',
						dataIndex : 'id'
					}, {
						header : '地区名称',
						dataIndex : 'name'
					}, {
						header : '上级地区',
						dataIndex : 'parentId',
						renderer : function(v) {
							return Share.map(v, area.AREAMAP, '');
						}
					}, {
						header : '地区类型',
						dataIndex : 'type',
						renderer : function(v) {
							return Share.map(v, area.AREATYPE, '');
						}
					}, {
						header : '地区编码',
						dataIndex : 'regionPrefix'
					}, {
						header : '是否启用',
						dataIndex : 'enabled',
						renderer : function(v) {
							return Share.map(v,area.ENABLED , '');
						}
					}, {
						header : '创建日期',
						dataIndex : 'createDate',
						renderer: Ext.util.Format.dateRenderer('Y-m-d')
					}]
		});
/** 新建 */
area.addAction = new Ext.Action({
			text : '新建',
			iconCls : 'module_add',
			handler : function() {
				area.addWindow.setIconClass('module_add'); // 设置窗口的样式
				area.addWindow.setTitle('新建地区'); // 设置窗口的名称
				area.addWindow.show().center(); // 显示窗口
				area.formPanel.getForm().reset(); // 清空表单里面的元素的值.
				area.comboxParent.clearValue();
			}
		});
/** 编辑 */
area.editAction = new Ext.Action({
			text : '编辑',
			iconCls : 'module_edit',
			disabled : true,
			handler : function() {
				var record = area.grid.getSelectionModel().getSelected();
				area.addWindow.setIconClass('module_edit'); // 设置窗口的样式
				area.addWindow.setTitle('编辑地区'); // 设置窗口的名称
				area.addWindow.show().center();
				area.formPanel.getForm().reset();
				area.formPanel.getForm().loadRecord(record);
			}
		});
/** 删除 */
area.deleteAction = new Ext.Action({
			text : '删除',
			iconCls : 'module_delete',
			disabled : true,
			handler : function() {
				area.delFun();
			}
		});
/** 查询 */
area.searchField = new Ext.ux.form.SearchField({
			store : area.store,
			paramName : 'name',
			emptyText : '请输入地区名称',
			style : 'margin-left: 5px;'
		});
/** 顶部工具栏 */
area.tbar = [area.addAction, '-', area.editAction, '-',
		area.deleteAction, '-', area.searchField];
/** 底部工具条 */
area.bbar = new Ext.PagingToolbar({
			pageSize : area.pageSize,
			store : area.store,
			displayInfo : true,
			// plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
			items : ['-', '&nbsp;', area.pageSizeCombo]
		});
/** 基本信息-表格 */
area.grid = new Ext.grid.GridPanel({
			// title : '模块列表',
			store : area.store,
			colModel : area.colModel,
			selModel : area.selModel,
			tbar : area.tbar,
			bbar : area.bbar,
			autoScroll : 'auto',
			region : 'center',
			loadMask : true,
			// autoExpandColumn :'moduleDesc',
			stripeRows : true,
			listeners : {},
			viewConfig : {}
		});
area.typeCombo = new Ext.form.ComboBox({
			fieldLabel : '节点类型',
			hiddenName : 'type',
			name : 'type',
			triggerAction : 'all',
			mode : 'local',
			store : new Ext.data.ArrayStore({
						fields : ['v', 't'],
						data : Share.map2Ary(area.AREATYPE)
					}),
			valueField : 'v',
			displayField : 't',
			allowBlank : false,
			editable : false,
			anchor : '99%'
		});
area.enabledCombo = new Ext.form.ComboBox({
	fieldLabel : '是否启用',
	hiddenName : 'enabled',
	name : 'enabled',
	triggerAction : 'all',
	mode : 'local',
	store : new Ext.data.ArrayStore({
				fields : ['v', 't'],
				data : Share.map2Ary(area.ENABLED)
			}),
	valueField : 'v',
	displayField : 't',
	allowBlank : false,
	editable : false,
	anchor : '99%'
});
/** 基本信息-详细信息的form */
area.formPanel = new Ext.form.FormPanel({
			frame : false,
			title : '地区信息',
			bodyStyle : 'padding:10px;border:0px',
			labelwidth : 50,
			defaultType : 'textfield',
			items : [{
						xtype : 'hidden',
						fieldLabel : 'ID',
						name : 'id',
						anchor : '99%'
					}, {
						fieldLabel : '地区名称',
						maxLength : 64,
						allowBlank : false,
						name : 'name',
						anchor : '99%'
					}, area.comboxParent, area.typeCombo,
					{
						fieldLabel : '地区编码',
						maxLength : 10,
						allowBlank : false,
						name : 'regionPrefix',
						anchor : '99%'
					}, 
					area.enabledCombo]
		});
/** 编辑新建窗口 */
area.addWindow = new Ext.Window({
			layout : 'fit',
			width : 500,
			height : 320,
			closeAction : 'hide',
			plain : true,
			modal : true,
			resizable : true,
			items : [area.formPanel],
			buttons : [{
						text : '保存',
						handler : function() {
							area.saveFun();
						}
					}, {
						text : '重置',
						handler : function() {
							var form = area.formPanel.getForm();
							var id = form.findField("id").getValue();
							form.reset();
							if (id != '')
								form.findField("id").setValue(id);
						}
					}]
		});
area.alwaysFun = function() {
	Share.resetGrid(area.grid);
	area.deleteAction.disable();
	area.editAction.disable();
};
area.saveFun = function() {
	var form = area.formPanel.getForm();
	if (!form.isValid()) {
		return;
	}
	// 发送请求
	Share.AjaxRequest({
				url : area.save,
				params : form.getValues(),
				callback : function(json) {
					area.addWindow.hide();
					area.alwaysFun();
					area.store.reload();
				}
			});
};
area.delFun = function() {
	var record = area.grid.getSelectionModel().getSelected();
	Ext.Msg.confirm('提示', '你真的要删除选中地区吗?', function(btn, text) {
				if (btn == 'yes') {
					// 发送请求
					Share.AjaxRequest({
								url : area.del + "?id=" + record.data.id,
								callback : function(json) {
									area.alwaysFun();
									area.store.reload();
								}
							});
				}
			});
};
area.myPanel = new Ext.Panel({
			id : '${param.id}' + '_panel',
			renderTo : '${param.id}',
			layout : 'border',
			boder : false,
			height : index.tabPanel.getInnerHeight() - 1,
			items : [area.grid]
		});
</script>
