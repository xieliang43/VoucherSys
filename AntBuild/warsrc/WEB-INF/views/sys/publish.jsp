<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<div id="${param.id}"></div>

<script type="text/javascript">
Ext.ns("Ext.Authority.publish"); // 自定义一个命名空间
publish = Ext.Authority.publish; // 定义命名空间的别名
publish = {
	all : ctx + '/publishAction!loadAll.action',//加载所有
	del : ctx + "/publishAction!delete.action",//删除
	pageSize : 20 // 每页显示的记录数
};

/** 改变页的combo */
publish.pageSizeCombo = new Share.pageSizeCombo({
			value : '20',
			listeners : {
				select : function(comboBox) {
					publish.pageSize = parseInt(comboBox.getValue());
					publish.bbar.pageSize = parseInt(comboBox.getValue());
					publish.store.baseParams.limit = publish.pageSize;
					publish.store.baseParams.start = 0;
					publish.store.load();
				}
			}
		});
// 覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
publish.pageSize = parseInt(publish.pageSizeCombo.getValue());
/** 基本信息-数据源 */
publish.store = new Ext.data.Store({
			autoLoad : true,
			remoteSort : true,
			baseParams : {
				start : 0,
				limit : publish.pageSize,
				phoneNo: null
			},
			proxy : new Ext.data.HttpProxy({// 获取数据的方式
				method : 'POST',
				url : publish.all
			}),
			reader : new Ext.data.JsonReader({// 数据读取器
				totalProperty : 'results', // 记录总数
				root : 'rows' // Json中的列表数据根节点
			}, ['id', 'phoneNo', 'msg', 'createDate']),
			listeners : {
				'load' : function(store, records, options) {
					publish.alwaysFun();
				}
			}
		});
/** 基本信息-选择模式 */
publish.selModel = new Ext.grid.CheckboxSelectionModel({
			singleSelect : true,
			listeners : {
				'rowselect' : function(selectionModel, rowIndex, record) {
					publish.deleteAction.enable();
					publish.editAction.enable();
				},
				'rowdeselect' : function(selectionModel, rowIndex, record) {
					publish.alwaysFun();
				}
			}
		});
/** 基本信息-数据列 */
publish.colModel = new Ext.grid.ColumnModel({
			defaults : {
				sortable : true,
				width : 140
			},
			columns : [publish.selModel, {
						hidden : true,
						header : '编号',
						dataIndex : 'id'
					},  {
						header : '手机号码',
						dataIndex : 'phoneNo'
					}, {
						header : '消息',
						dataIndex : 'msg'
					}, {
						header : '创建日期',
						dataIndex : 'createDate',
						renderer: Ext.util.Format.dateRenderer('Y-m-d')
					}]
		});
/** 编辑 */
publish.editAction = new Ext.Action({
			text : '查看',
			iconCls : 'module_edit',
			disabled : true,
			handler : function() {
				var record = publish.grid.getSelectionModel().getSelected();
				publish.addWindow.setIconClass('module_edit'); // 设置窗口的样式
				publish.addWindow.setTitle('查看消息'); // 设置窗口的名称
				publish.addWindow.show().center();
				publish.formPanel.getForm().reset();
				publish.formPanel.getForm().loadRecord(record);
			}
		});
/** 删除 */
publish.deleteAction = new Ext.Action({
			text : '删除',
			iconCls : 'module_delete',
			disabled : true,
			handler : function() {
				publish.delFun();
			}
		});
/** 查询 */
publish.searchField = new Ext.ux.form.SearchField({
			store : publish.store,
			paramName : 'phoneNo',
			emptyText : '请输入手机',
			style : 'margin-left: 5px;'
		});
/** 顶部工具栏 */
publish.tbar = [publish.editAction, '-',
		publish.deleteAction, '-', publish.searchField];
/** 底部工具条 */
publish.bbar = new Ext.PagingToolbar({
			pageSize : publish.pageSize,
			store : publish.store,
			displayInfo : true,
			items : ['-', '&nbsp;', publish.pageSizeCombo]
		});
/** 基本信息-表格 */
publish.grid = new Ext.grid.GridPanel({
			// title : '模块列表',
			store : publish.store,
			colModel : publish.colModel,
			selModel : publish.selModel,
			tbar : publish.tbar,
			bbar : publish.bbar,
			autoScroll : 'auto',
			region : 'center',
			loadMask : true,
			stripeRows : true,
			listeners : {},
			viewConfig : {}
		});
/** 基本信息-详细信息的form */
publish.formPanel = new Ext.form.FormPanel({
			frame : false,
			title : '消息',
			bodyStyle : 'padding:10px;border:0px',
			labelwidth : 50,
			defaultType : 'textfield',
			items : [{
						xtype : 'hidden',
						fieldLabel : 'ID',
						name : 'id',
						anchor : '99%'
					}, {
						fieldLabel : '手机号码',
						maxLength : 24,
						allowBlank : false,
						name : 'phoneNo',
						anchor : '99%'
					}, {
						xtype : 'textarea',
						fieldLabel : '消息',
						maxLength : 512,
						height : 100,
						name : 'msg',
						anchor : '99%'
					}]
		});
/** 编辑新建窗口 */
publish.addWindow = new Ext.Window({
			layout : 'fit',
			width : 500,
			height : 240,
			closeAction : 'hide',
			plain : true,
			modal : true,
			resizable : true,
			items : [publish.formPanel],
			buttons : [{
						text : '关闭',
						handler : function() {
							publish.addWindow.hide();
							publish.alwaysFun();
							publish.store.reload();
						}
					}]
		});
publish.alwaysFun = function() {
	Share.resetGrid(publish.grid);
	publish.deleteAction.disable();
	publish.editAction.disable();
};
publish.saveFun = function() {
	var form = publish.formPanel.getForm();
	if (!form.isValid()) {
		return;
	}
	// 发送请求
	Share.AjaxRequest({
				url : publish.save,
				params : form.getValues(),
				callback : function(json) {
					publish.addWindow.hide();
					publish.alwaysFun();
					publish.store.reload();
				}
			});
};
publish.delFun = function() {
	var record = publish.grid.getSelectionModel().getSelected();
	Ext.Msg.confirm('提示', '你真的要删除选中的建议吗?', function(btn, text) {
				if (btn == 'yes') {
					// 发送请求
					Share.AjaxRequest({
								url : publish.del + "?id=" + record.data.id,
								callback : function(json) {
									publish.alwaysFun();
									publish.store.reload();
								}
							});
				}
			});
};
publish.myPanel = new Ext.Panel({
			id : '${param.id}' + '_panel',
			renderTo : '${param.id}',
			layout : 'border',
			boder : false,
			height : index.tabPanel.getInnerHeight() - 1,
			items : [publish.grid]
		});
</script>
