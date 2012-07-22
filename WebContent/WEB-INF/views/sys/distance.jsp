<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<div id="${param.id}"></div>

<script type="text/javascript">
Ext.ns("Ext.Authority.distance"); // 自定义一个命名空间
distance = Ext.Authority.distance; // 定义命名空间的别名
distance = {
	all : ctx + '/distanceAction!loadAll.action',// 加载所有
	save : ctx + "/distanceAction!save.action",//保存
	del : ctx + "/distanceAction!delete.action",//删除
	pageSize : 20, // 每页显示的记录数
	ENABLED : eval('(${fields.enabled==null?"{}":fields.enabled})')
};

/** 改变页的combo */
distance.pageSizeCombo = new Share.pageSizeCombo({
			value : '20',
			listeners : {
				select : function(comboBox) {
					distance.pageSize = parseInt(comboBox.getValue());
					distance.bbar.pageSize = parseInt(comboBox.getValue());
					distance.store.baseParams.limit = distance.pageSize;
					distance.store.baseParams.start = 0;
					distance.store.load();
				}
			}
		});
// 覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
distance.pageSize = parseInt(distance.pageSizeCombo.getValue());
/** 基本信息-数据源 */
distance.store = new Ext.data.Store({
			autoLoad : true,
			remoteSort : true,
			baseParams : {
				start : 0,
				limit : distance.pageSize,
				name: null
			},
			proxy : new Ext.data.HttpProxy({// 获取数据的方式
				method : 'POST',
				url : distance.all
			}),
			reader : new Ext.data.JsonReader({// 数据读取器
				totalProperty : 'results', // 记录总数
				root : 'rows' // Json中的列表数据根节点
			}, ['id', 'name', 'enabled', 'createDate']),
			listeners : {
				'load' : function(store, records, options) {
					distance.alwaysFun();
				}
			}
		});
/** 基本信息-选择模式 */
distance.selModel = new Ext.grid.CheckboxSelectionModel({
			singleSelect : true,
			listeners : {
				'rowselect' : function(selectionModel, rowIndex, record) {
					distance.deleteAction.enable();
					distance.editAction.enable();
				},
				'rowdeselect' : function(selectionModel, rowIndex, record) {
					distance.alwaysFun();
				}
			}
		});
/** 基本信息-数据列 */
distance.colModel = new Ext.grid.ColumnModel({
			defaults : {
				sortable : true,
				width : 140
			},
			columns : [distance.selModel, {
						hidden : true,
						header : '编号',
						dataIndex : 'id'
					}, {
						header : '距离',
						dataIndex : 'name'
					}, {
						header : '是否启用',
						dataIndex : 'enabled',
						renderer : function(v) {
							return Share.map(v, distance.ENABLED, '');
						}
					}, {
						header : '创建日期',
						dataIndex : 'createDate',
						renderer: Ext.util.Format.dateRenderer('Y-m-d')
					}]
		});
/** 新建 */
distance.addAction = new Ext.Action({
			text : '新建',
			iconCls : 'module_add',
			handler : function() {
				distance.addWindow.setIconClass('module_add'); // 设置窗口的样式
				distance.addWindow.setTitle('新建距离'); // 设置窗口的名称
				distance.addWindow.show().center(); // 显示窗口
				distance.formPanel.getForm().reset(); // 清空表单里面的元素的值.
				distance.comboxParent.clearValue();
			}
		});
/** 编辑 */
distance.editAction = new Ext.Action({
			text : '编辑',
			iconCls : 'module_edit',
			disabled : true,
			handler : function() {
				var record = distance.grid.getSelectionModel().getSelected();
				distance.addWindow.setIconClass('module_edit'); // 设置窗口的样式
				distance.addWindow.setTitle('编辑距离'); // 设置窗口的名称
				distance.addWindow.show().center();
				distance.formPanel.getForm().reset();
				distance.formPanel.getForm().loadRecord(record);
			}
		});
/** 删除 */
distance.deleteAction = new Ext.Action({
			text : '删除',
			iconCls : 'module_delete',
			disabled : true,
			handler : function() {
				distance.delFun();
			}
		});
/** 查询 */
distance.searchField = new Ext.ux.form.SearchField({
			store : distance.store,
			paramName : 'name',
			emptyText : '请输入距离',
			style : 'margin-left: 5px;'
		});
/** 顶部工具栏 */
distance.tbar = [distance.addAction, '-', distance.editAction, '-',
		distance.deleteAction, '-', distance.searchField];
/** 底部工具条 */
distance.bbar = new Ext.PagingToolbar({
			pageSize : distance.pageSize,
			store : distance.store,
			displayInfo : true,
			items : ['-', '&nbsp;', distance.pageSizeCombo]
		});
/** 基本信息-表格 */
distance.grid = new Ext.grid.GridPanel({
			// title : '模块列表',
			store : distance.store,
			colModel : distance.colModel,
			selModel : distance.selModel,
			tbar : distance.tbar,
			bbar : distance.bbar,
			autoScroll : 'auto',
			region : 'center',
			loadMask : true,
			stripeRows : true,
			listeners : {},
			viewConfig : {}
		});
distance.enabledCombo = new Ext.form.ComboBox({
	fieldLabel : '是否启用',
	hiddenName : 'enabled',
	name : 'enabled',
	triggerAction : 'all',
	mode : 'local',
	store : new Ext.data.ArrayStore({
				fields : ['v', 't'],
				data : Share.map2Ary(distance.ENABLED)
			}),
	valueField : 'v',
	displayField : 't',
	allowBlank : false,
	editable : false,
	anchor : '99%'
});
/** 基本信息-详细信息的form */
distance.formPanel = new Ext.form.FormPanel({
			frame : false,
			title : '距离信息',
			bodyStyle : 'padding:10px;border:0px',
			labelwidth : 50,
			defaultType : 'textfield',
			items : [{
						xtype : 'hidden',
						fieldLabel : 'ID',
						name : 'id',
						anchor : '99%'
					}, {
						fieldLabel : '距离',
						maxLength : 64,
						allowBlank : false,
						name : 'name',
						anchor : '99%'
					},distance.enabledCombo]
		});
/** 编辑新建窗口 */
distance.addWindow = new Ext.Window({
			layout : 'fit',
			width : 500,
			height : 420,
			closeAction : 'hide',
			plain : true,
			modal : true,
			resizable : true,
			items : [distance.formPanel],
			buttons : [{
						text : '保存',
						handler : function() {
							distance.saveFun();
						}
					}, {
						text : '重置',
						handler : function() {
							var form = distance.formPanel.getForm();
							var id = form.findField("id").getValue();
							form.reset();
							if (id != '')
								form.findField("id").setValue(id);
						}
					}]
		});
distance.alwaysFun = function() {
	Share.resetGrid(distance.grid);
	distance.deleteAction.disable();
	distance.editAction.disable();
};
distance.saveFun = function() {
	var form = distance.formPanel.getForm();
	if (!form.isValid()) {
		return;
	}
	// 发送请求
	Share.AjaxRequest({
				url : distance.save,
				params : form.getValues(),
				callback : function(json) {
					distance.addWindow.hide();
					distance.alwaysFun();
					distance.store.reload();
				}
			});
};
distance.delFun = function() {
	var record = distance.grid.getSelectionModel().getSelected();
	Ext.Msg.confirm('提示', '你真的要删除吗?', function(btn, text) {
				if (btn == 'yes') {
					// 发送请求
					Share.AjaxRequest({
								url : distance.del + "?id=" + record.data.id,
								callback : function(json) {
									distance.alwaysFun();
									distance.store.reload();
								}
							});
				}
			});
};
distance.myPanel = new Ext.Panel({
			id : '${param.id}' + '_panel',
			renderTo : '${param.id}',
			layout : 'border',
			boder : false,
			height : index.tabPanel.getInnerHeight() - 1,
			items : [distance.grid]
		});
</script>
