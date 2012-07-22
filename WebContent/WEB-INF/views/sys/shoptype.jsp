<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<div id="${param.id}"></div>

<script type="text/javascript">
Ext.ns("Ext.Authority.shoptype"); // 自定义一个命名空间
shoptype = Ext.Authority.shoptype; // 定义命名空间的别名
shoptype = {
	all : ctx + '/shopTypeAction!loadAll.action',// 加载所有
	save : ctx + "/shopTypeAction!save.action",//保存
	del : ctx + "/shopTypeAction!delete.action",//删除
	ENABLED : eval('(${fields.enabled==null?"{}":fields.enabled})'),
	pagesizes:eval('(${fields.pagesizes==null?"{}":fields.pagesizes})'),
	pageSize : 20 // 每页显示的记录数
};
/** 改变页的combo */
shoptype.pageSizeCombo = new Share.pageSizeCombo({
			value : '20',
			listeners : {
				select : function(comboBox) {
					shoptype.pageSize = parseInt(comboBox.getValue());
					shoptype.bbar.pageSize = parseInt(comboBox.getValue());
					shoptype.store.baseParams.limit = shoptype.pageSize;
					shoptype.store.baseParams.start = 0;
					shoptype.store.load();
				}
			}
		});
// 覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
shoptype.pageSize = parseInt(shoptype.pageSizeCombo.getValue());
/** 基本信息-数据源 */
shoptype.store = new Ext.data.Store({
			autoLoad : true,
			remoteSort : true,
			baseParams : {
				start : 0,
				limit : shoptype.pageSize,
				name: null
			},
			proxy : new Ext.data.HttpProxy({// 获取数据的方式
				method : 'POST',
				url : shoptype.all
			}),
			reader : new Ext.data.JsonReader({// 数据读取器
				totalProperty : 'results', // 记录总数
				root : 'rows' // Json中的列表数据根节点
			}, ['id', 'name', 'enabled', 'description', 'createDate']),
			listeners : {
				'load' : function(store, records, options) {
					shoptype.alwaysFun();
				}
			}
		});
/** 基本信息-选择模式 */
shoptype.selModel = new Ext.grid.CheckboxSelectionModel({
			singleSelect : true,
			listeners : {
				'rowselect' : function(selectionModel, rowIndex, record) {
					shoptype.deleteAction.enable();
					shoptype.editAction.enable();
				},
				'rowdeselect' : function(selectionModel, rowIndex, record) {
					shoptype.alwaysFun();
				}
			}
		});
/** 基本信息-数据列 */
shoptype.colModel = new Ext.grid.ColumnModel({
			defaults : {
				sortable : true,
				width : 140
			},
			columns : [shoptype.selModel, {
						hidden : true,
						header : '字段ID',
						dataIndex : 'id'
					}, {
						header : '类型名',
						dataIndex : 'name'
					}, {
						// (0:禁用;1启用)
						header : '是否启用',
						dataIndex : 'enabled',
						renderer : function(v) {
							return Share.map(v,shoptype.ENABLED , '');
						}
					}, {
						header : '描述',
						dataIndex : 'description'
					}, {
						header : '创建日期',
						dataIndex : 'createDate',
						renderer: Ext.util.Format.dateRenderer('Y-m-d')
					}]
		});
/** 新建 */
shoptype.addAction = new Ext.Action({
			text : '新建',
			iconCls : 'field_add',
			handler : function() {
				shoptype.addWindow.setIconClass('field_add'); // 设置窗口的样式
				shoptype.addWindow.setTitle('新建类型'); // 设置窗口的名称
				shoptype.addWindow.show().center(); // 显示窗口
				shoptype.formPanel.getForm().reset(); // 清空表单里面的元素的值.
				shoptype.enabledCombo.clearValue();
			}
		});
/** 编辑 */
shoptype.editAction = new Ext.Action({
			text : '编辑',
			iconCls : 'field_edit',
			disabled : true,
			handler : function() {
				var record = shoptype.grid.getSelectionModel().getSelected();
				shoptype.addWindow.setIconClass('field_edit'); // 设置窗口的样式
				shoptype.addWindow.setTitle('编辑类型'); // 设置窗口的名称
				shoptype.addWindow.show().center();
				shoptype.formPanel.getForm().reset();
				shoptype.formPanel.getForm().loadRecord(record);
			}
		});
/** 删除 */
shoptype.deleteAction = new Ext.Action({
			text : '删除',
			iconCls : 'field_delete',
			disabled : true,
			handler : function() {
				shoptype.delFun();
			}
		});
/** 查询 */
shoptype.searchField = new Ext.ux.form.SearchField({
			store : shoptype.store,
			paramName : 'name',
			emptyText : '请输入类型名称',
			style : 'margin-left: 5px;'
		});

/** 顶部工具栏 */
shoptype.tbar = [shoptype.addAction, '-', shoptype.editAction, '-',
		shoptype.deleteAction, '-', shoptype.searchField];
/** 底部工具条 */
shoptype.bbar = new Ext.PagingToolbar({
			pageSize : shoptype.pageSize,
			store : shoptype.store,
			displayInfo : true,
			// plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
			items : ['-', '&nbsp;', shoptype.pageSizeCombo]
		});
/** 基本信息-表格 */
shoptype.grid = new Ext.grid.GridPanel({
			store : shoptype.store,
			colModel : shoptype.colModel,
			selModel : shoptype.selModel,
			tbar : shoptype.tbar,
			bbar : shoptype.bbar,
			autoScroll : 'auto',
			region : 'center',
			loadMask : true,
			// autoExpandColumn :'fieldDesc',
			stripeRows : true,
			listeners : {},
			viewConfig : {}
		});
shoptype.enabledCombo = new Ext.form.ComboBox({
			fieldLabel : '是否启用',
			hiddenName : 'enabled',
			name : 'enabled',
			triggerAction : 'all',
			mode : 'local',
			store : new Ext.data.ArrayStore({
						fields : ['v', 't'],
						data : Share.map2Ary(shoptype.ENABLED)
					}),
			valueField : 'v',
			displayField : 't',
			allowBlank : false,
			editable : false,
			anchor : '99%'
		});
/** 基本信息-详细信息的form */
shoptype.formPanel = new Ext.form.FormPanel({
			frame : false,
			title : '类型信息',
			bodyStyle : 'padding:10px;border:0px',
			labelwidth : 50,
			defaultType : 'textfield',
			items : [{
						xtype : 'hidden',
						fieldLabel : 'ID',
						name : 'id',
						anchor : '99%'
					}, {
						fieldLabel : '类型名',
						maxLength : 128,
						allowBlank : false,
						name : 'name',
						anchor : '99%'
					},shoptype.enabledCombo, {
						fieldLabel : '描述',
						maxLength : 128,
						allowBlank : false,
						name : 'description',
						anchor : '99%'
					}]
		});
/** 编辑新建窗口 */
shoptype.addWindow = new Ext.Window({
			layout : 'fit',
			width : 400,
			height : 280,
			closeAction : 'hide',
			plain : true,
			modal : true,
			resizable : true,
			items : [shoptype.formPanel],
			buttons : [{
						text : '保存',
						handler : function() {
							shoptype.saveFun();
						}
					}, {
						text : '重置',
						handler : function() {
							var form = shoptype.formPanel.getForm();
							var id = form.findField("id").getValue();
							form.reset();
							if (id != '')
								form.findField("id").setValue(id);
						}
					}]
		});
shoptype.alwaysFun = function() {
	Share.resetGrid(shoptype.grid);
	shoptype.deleteAction.disable();
	shoptype.editAction.disable();
};
shoptype.saveFun = function() {
	var form = shoptype.formPanel.getForm();
	if (!form.isValid()) {
		return;
	}
	// 发送请求
	Share.AjaxRequest({
				url : shoptype.save,
				params : form.getValues(),
				callback : function(json) {
					shoptype.addWindow.hide();
					shoptype.alwaysFun();
					shoptype.store.reload();
				}
			});
};
shoptype.delFun = function() {
	var record = shoptype.grid.getSelectionModel().getSelected();
	Ext.Msg.confirm('提示', '确定要删除这条记录吗?', function(btn, text) {
				if (btn == 'yes') {
					// 发送请求
					Share.AjaxRequest({
								url : shoptype.del + "?id=" + record.data.id,
								callback : function(json) {
									shoptype.alwaysFun();
									shoptype.store.reload();
								}
							});
				}
			});
};
shoptype.myPanel = new Ext.Panel({
			id : '${param.id}' + '_panel',
			renderTo : '${param.id}',
			layout : 'border',
			boder : false,
			height : index.tabPanel.getInnerHeight() - 1,
			items : [shoptype.grid]
		});
</script>
