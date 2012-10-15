<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<div id="${param.id}"></div>

<script type="text/javascript">
Ext.ns("Ext.Authority.cmnuser"); // 自定义一个命名空间
cmnuser = Ext.Authority.cmnuser; // 定义命名空间的别名
cmnuser = {
	all : ctx + '/sysCmnUserAction!loadAll.action',// 加载所有
	save : ctx + "/sysCmnUserAction!save.action",//保存
	del : ctx + "/sysCmnUserAction!delete.action",//删除
	AREAMAP :eval('(${userAreaMap})'),
	pageSize : 20, // 每页显示的记录数
	SEX: eval('(${fields.sex==null?"{}":fields.sex})')//注意括号
};

/** 改变页的combo */
cmnuser.pageSizeCombo = new Share.pageSizeCombo({
			value : '20',
			listeners : {
				select : function(comboBox) {
					cmnuser.pageSize = parseInt(comboBox.getValue());
					cmnuser.bbar.pageSize = parseInt(comboBox.getValue());
					cmnuser.store.baseParams.limit = cmnuser.pageSize;
					cmnuser.store.baseParams.start = 0;
					cmnuser.store.load();
				}
			}
		});
// 覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
cmnuser.pageSize = parseInt(cmnuser.pageSizeCombo.getValue());
/** 基本信息-数据源 */
cmnuser.store = new Ext.data.Store({
			autoLoad : true,
			remoteSort : true,
			baseParams : {
				start : 0,
				limit : cmnuser.pageSize,
				phoneNo: null
			},
			proxy : new Ext.data.HttpProxy({// 获取数据的方式
				method : 'POST',
				url : cmnuser.all
			}),
			reader : new Ext.data.JsonReader({// 数据读取器
				totalProperty : 'results', // 记录总数
				root : 'rows' // Json中的列表数据根节点
			}, ['id', 'name', 'phoneNo', 'cityId', 'sex', 'email',
					'createDate']),
			listeners : {
				'load' : function(store, records, options) {
					cmnuser.alwaysFun();
				}
			}
		});

cmnuser.cityCombo = new Ext.form.ComboBox({
	emptyText : '请选择城市...',
	fieldLabel : '城市',
	hiddenName : 'cityId',
	name : 'cityId',
	triggerAction : 'all',
	mode : 'local',
	store : new Ext.data.ArrayStore({
				fields : ['v', 't'],
				data : Share.map2Ary(cmnuser.AREAMAP)
			}),
	valueField : 'v',
	displayField : 't',
	allowBlank : false,
	editable : false,
	anchor : '99%'
});

/** 基本信息-选择模式 */
cmnuser.selModel = new Ext.grid.CheckboxSelectionModel({
			singleSelect : true,
			listeners : {
				'rowselect' : function(selectionModel, rowIndex, record) {
					cmnuser.deleteAction.enable();
					cmnuser.editAction.enable();
				},
				'rowdeselect' : function(selectionModel, rowIndex, record) {
					cmnuser.alwaysFun();
				}
			}
		});
/** 基本信息-数据列 */
cmnuser.colModel = new Ext.grid.ColumnModel({
			defaults : {
				sortable : true,
				width : 140
			},
			columns : [cmnuser.selModel, {
						hidden : true,
						header : 'ID',
						dataIndex : 'id'
					}, {
						header : '手机号码',
						dataIndex : 'phoneNo'
					}, {
						header : '用户名',
						dataIndex : 'name'
					}, {
						header : '城市',
						dataIndex : 'cityId',
						renderer : function(v) {
							return Share.map(v, cmnuser.AREAMAP, '');
						}
					}, {
						header : '性别',
						dataIndex : 'sex',
						renderer : function(v) {
							return Share.map(v, cmnuser.SEX, '其他');
						}
					}, {
						header : '电子邮件',
						dataIndex : 'email',
						renderer : function(value) {
							return '<span title="点击给 ' + value + ' 发邮件"><a href="mailto:' + value + '">' + value + '</a></span>';
						}
					}, {
						header : '创建日期',
						dataIndex : 'createDate',
						renderer: Ext.util.Format.dateRenderer('Y-m-d')
					}]
		});

/** 编辑 */
cmnuser.editAction = new Ext.Action({
			text : '查看',
			iconCls : 'module_edit',
			disabled : true,
			handler : function() {
				var record = cmnuser.grid.getSelectionModel().getSelected();
				cmnuser.addWindow.setIconClass('module_edit'); // 设置窗口的样式
				cmnuser.addWindow.setTitle('查看用户'); // 设置窗口的名称
				cmnuser.addWindow.show().center();
				cmnuser.formPanel.getForm().reset();
				cmnuser.formPanel.getForm().loadRecord(record);
			}
		});
/** 删除 */
cmnuser.deleteAction = new Ext.Action({
			text : '删除',
			iconCls : 'module_delete',
			disabled : true,
			handler : function() {
				cmnuser.delFun();
			}
		});
/** 查询 */
cmnuser.searchField = new Ext.ux.form.SearchField({
			store : cmnuser.store,
			paramName : 'phoneNo',
			emptyText : '请输手机号码',
			style : 'margin-left: 5px;'
		});
/** 顶部工具栏 */
cmnuser.tbar = [cmnuser.editAction, '-',
		cmnuser.deleteAction, '-', cmnuser.searchField];
/** 底部工具条 */
cmnuser.bbar = new Ext.PagingToolbar({
			pageSize : cmnuser.pageSize,
			store : cmnuser.store,
			displayInfo : true,
			// plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
			items : ['-', '&nbsp;', cmnuser.pageSizeCombo]
		});
/** 基本信息-表格 */
cmnuser.grid = new Ext.grid.GridPanel({
			// title : '模块列表',
			store : cmnuser.store,
			colModel : cmnuser.colModel,
			selModel : cmnuser.selModel,
			tbar : cmnuser.tbar,
			bbar : cmnuser.bbar,
			autoScroll : 'auto',
			region : 'center',
			loadMask : true,
			// autoExpandColumn :'moduleDesc',
			stripeRows : true,
			listeners : {},
			viewConfig : {}
		});
		
cmnuser.sexCombo = new Ext.form.ComboBox({
	fieldLabel : '性别',
	hiddenName : 'sex',
	name : 'sex',
	triggerAction : 'all',
	mode : 'local',
	store : new Ext.data.ArrayStore({
				fields : ['v', 't'],
				data : Share.map2Ary(cmnuser.SEX)
			}),
	valueField : 'v',
	displayField : 't',
	allowBlank : false,
	editable : false,
	anchor : '99%'
});

/** 基本信息-详细信息的form */
cmnuser.formPanel = new Ext.form.FormPanel({
			frame : false,
			title : '用户信息',
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
						fieldLabel : '用户名',
						maxLength : 24,
						allowBlank : false,
						name : 'name',
						anchor : '99%'
					}, cmnuser.cityCombo, {
						fieldLabel : '地区编码',
						maxLength : 10,
						allowBlank : false,
						name : 'regionPrefix',
						anchor : '99%'
					}, cmnuser.sexCombo, {
						fieldLabel : '电子邮件',
						maxLength : 64,
						allowBlank : false,
						regex : /^[a-zA-Z0-9_\.\-]+\@([a-zA-Z0-9\-]+\.)+[a-zA-Z0-9]{2,4}$/,
						regexText : '请输入有效的邮箱地址',
						name : 'email',
						anchor : '99%'
					}]
		});
/** 编辑新建窗口 */
cmnuser.addWindow = new Ext.Window({
			layout : 'fit',
			width : 500,
			height : 320,
			closeAction : 'hide',
			plain : true,
			modal : true,
			resizable : true,
			items : [cmnuser.formPanel],
			buttons : [{
						text : '关闭',
						handler : function() {
							cmnuser.addWindow.hide();
							cmnuser.alwaysFun();
							cmnuser.store.reload();
						}
					}]
		});
		
cmnuser.alwaysFun = function() {
	Share.resetGrid(cmnuser.grid);
	cmnuser.deleteAction.disable();
	cmnuser.editAction.disable();
};

cmnuser.saveFun = function() {
	var form = cmnuser.formPanel.getForm();
	if (!form.isValid()) {
		return;
	}
	// 发送请求
	Share.AjaxRequest({
				url : cmnuser.save,
				params : form.getValues(),
				callback : function(json) {
					cmnuser.addWindow.hide();
					cmnuser.alwaysFun();
					cmnuser.store.reload();
				}
			});
};
cmnuser.delFun = function() {
	var record = cmnuser.grid.getSelectionModel().getSelected();
	Ext.Msg.confirm('提示', '你真的要删除选中地区吗?', function(btn, text) {
				if (btn == 'yes') {
					// 发送请求
					Share.AjaxRequest({
								url : cmnuser.del + "?id=" + record.data.id,
								callback : function(json) {
									cmnuser.alwaysFun();
									cmnuser.store.reload();
								}
							});
				}
			});
};
cmnuser.myPanel = new Ext.Panel({
			id : '${param.id}' + '_panel',
			renderTo : '${param.id}',
			layout : 'border',
			boder : false,
			height : index.tabPanel.getInnerHeight() - 1,
			items : [cmnuser.grid]
		});
</script>
