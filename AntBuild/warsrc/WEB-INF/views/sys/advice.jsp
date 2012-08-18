<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<div id="${param.id}"></div>

<script type="text/javascript">
Ext.ns("Ext.Authority.advice"); // 自定义一个命名空间
advice = Ext.Authority.advice; // 定义命名空间的别名
advice = {
	all : ctx + '/adviceAction!loadAll.action',// 加载所有
	del : ctx + "/adviceAction!delete.action",//删除
	pageSize : 20 // 每页显示的记录数
};

/** 改变页的combo */
advice.pageSizeCombo = new Share.pageSizeCombo({
			value : '20',
			listeners : {
				select : function(comboBox) {
					advice.pageSize = parseInt(comboBox.getValue());
					advice.bbar.pageSize = parseInt(comboBox.getValue());
					advice.store.baseParams.limit = advice.pageSize;
					advice.store.baseParams.start = 0;
					advice.store.load();
				}
			}
		});
// 覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
advice.pageSize = parseInt(advice.pageSizeCombo.getValue());
/** 基本信息-数据源 */
advice.store = new Ext.data.Store({
			autoLoad : true,
			remoteSort : true,
			baseParams : {
				start : 0,
				limit : advice.pageSize,
				msg: null
			},
			proxy : new Ext.data.HttpProxy({// 获取数据的方式
				method : 'POST',
				url : advice.all
			}),
			reader : new Ext.data.JsonReader({// 数据读取器
				totalProperty : 'results', // 记录总数
				root : 'rows' // Json中的列表数据根节点
			}, ['id', 'msg', 'createDate']),
			listeners : {
				'load' : function(store, records, options) {
					advice.alwaysFun();
				}
			}
		});
/** 基本信息-选择模式 */
advice.selModel = new Ext.grid.CheckboxSelectionModel({
			singleSelect : true,
			listeners : {
				'rowselect' : function(selectionModel, rowIndex, record) {
					advice.deleteAction.enable();
					advice.editAction.enable();
				},
				'rowdeselect' : function(selectionModel, rowIndex, record) {
					advice.alwaysFun();
				}
			}
		});
/** 基本信息-数据列 */
advice.colModel = new Ext.grid.ColumnModel({
			defaults : {
				sortable : true,
				width : 140
			},
			columns : [advice.selModel, {
						hidden : true,
						header : '编号',
						dataIndex : 'id'
					}, {
						header : '建议',
						dataIndex : 'msg'
					}, {
						header : '创建日期',
						dataIndex : 'createDate',
						renderer: Ext.util.Format.dateRenderer('Y-m-d')
					}]
		});
/** 编辑 */
advice.editAction = new Ext.Action({
			text : '查看',
			iconCls : 'module_edit',
			disabled : true,
			handler : function() {
				var record = advice.grid.getSelectionModel().getSelected();
				advice.addWindow.setIconClass('module_edit'); // 设置窗口的样式
				advice.addWindow.setTitle('查看建议'); // 设置窗口的名称
				advice.addWindow.show().center();
				advice.formPanel.getForm().reset();
				advice.formPanel.getForm().loadRecord(record);
			}
		});
/** 删除 */
advice.deleteAction = new Ext.Action({
			text : '删除',
			iconCls : 'module_delete',
			disabled : true,
			handler : function() {
				advice.delFun();
			}
		});
/** 查询 */
advice.searchField = new Ext.ux.form.SearchField({
			store : advice.store,
			paramName : 'msg',
			emptyText : '请输入内容',
			style : 'margin-left: 5px;'
		});
/** 顶部工具栏 */
advice.tbar = [advice.editAction, '-',
		advice.deleteAction, '-', advice.searchField];
/** 底部工具条 */
advice.bbar = new Ext.PagingToolbar({
			pageSize : advice.pageSize,
			store : advice.store,
			displayInfo : true,
			// plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
			items : ['-', '&nbsp;', advice.pageSizeCombo]
		});
/** 基本信息-表格 */
advice.grid = new Ext.grid.GridPanel({
			// title : '模块列表',
			store : advice.store,
			colModel : advice.colModel,
			selModel : advice.selModel,
			tbar : advice.tbar,
			bbar : advice.bbar,
			autoScroll : 'auto',
			region : 'center',
			loadMask : true,
			stripeRows : true,
			listeners : {},
			viewConfig : {}
		});
/** 基本信息-详细信息的form */
advice.formPanel = new Ext.form.FormPanel({
			frame : false,
			title : '建议信息',
			bodyStyle : 'padding:10px;border:0px',
			labelwidth : 50,
			defaultType : 'textfield',
			items : [{
						xtype : 'hidden',
						fieldLabel : 'ID',
						name : 'id',
						anchor : '99%'
					}, {
						xtype : 'textarea',
						fieldLabel : '建议',
						maxLength : 128,
						height : 100,
						name : 'msg',
						anchor : '99%'
					}]
		});
/** 编辑新建窗口 */
advice.addWindow = new Ext.Window({
			layout : 'fit',
			width : 500,
			height : 240,
			closeAction : 'hide',
			plain : true,
			modal : true,
			resizable : true,
			items : [advice.formPanel],
			buttons : [{
						text : '关闭',
						handler : function() {
							advice.addWindow.hide();
							advice.alwaysFun();
							advice.store.reload();
						}
					}]
		});
advice.alwaysFun = function() {
	Share.resetGrid(advice.grid);
	advice.deleteAction.disable();
	advice.editAction.disable();
};
advice.saveFun = function() {
	var form = advice.formPanel.getForm();
	if (!form.isValid()) {
		return;
	}
	// 发送请求
	Share.AjaxRequest({
				url : advice.save,
				params : form.getValues(),
				callback : function(json) {
					advice.addWindow.hide();
					advice.alwaysFun();
					advice.store.reload();
				}
			});
};
advice.delFun = function() {
	var record = advice.grid.getSelectionModel().getSelected();
	Ext.Msg.confirm('提示', '你真的要删除选中的建议吗?', function(btn, text) {
				if (btn == 'yes') {
					// 发送请求
					Share.AjaxRequest({
								url : advice.del + "?id=" + record.data.id,
								callback : function(json) {
									advice.alwaysFun();
									advice.store.reload();
								}
							});
				}
			});
};
advice.myPanel = new Ext.Panel({
			id : '${param.id}' + '_panel',
			renderTo : '${param.id}',
			layout : 'border',
			boder : false,
			height : index.tabPanel.getInnerHeight() - 1,
			items : [advice.grid]
		});
</script>
