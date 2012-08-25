<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<div id="${param.id}"></div>

<script type="text/javascript">
Ext.ns("Ext.Authority.merchant"); // 自定义一个命名空间
merchant = Ext.Authority.merchant; // 定义命名空间的别名
merchant = {
	all : ctx + '/merchantAction!loadMerchant.action', // 所有用户
	save : ctx + "/merchantAction!update.action",// 保存用户
	reset : ctx + "/merchantAction!changeExpPassword.action",// 重置用户密码
	pageSize : 20,// 每页显示的记录数
	AREAMAP :eval('(${mchAreaMap})'),
	SEX: eval('(${fields.sex==null?"{}":fields.sex})')//注意括号
};
/** 改变页的combo*/
merchant.pageSizeCombo = new Share.pageSizeCombo({
	value : '20',
	listeners : {
		select : function(comboBox) {
			merchant.pageSize  = parseInt(comboBox.getValue());
			merchant.bbar.pageSize  = parseInt(comboBox.getValue());
			merchant.store.baseParams.limit = merchant.pageSize;
			merchant.store.baseParams.start = 0;
			merchant.store.load();
		}
	}
});
//覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
merchant.pageSize = parseInt(merchant.pageSizeCombo.getValue());
/** 基本信息-数据源 */
merchant.store = new Ext.data.Store({
	remoteSort : true,
	autoLoad:true,
	baseParams : {
		start : 0,
		limit : merchant.pageSize
	},  
	proxy : new Ext.data.HttpProxy({// 获取数据的方式
		method : 'POST',
		url : merchant.all
	}),
	reader : new Ext.data.JsonReader({// 数据读取器
		totalProperty : 'results', // 记录总数
		root : 'rows' // Json中的列表数据根节点
	}, [ 'id', 'account', 'realName', 'expensePassword', 'sex', 'email', 'mobile', 'officePhone', 'qqNo', 'cityId', 'lastLoginTime', 'lastLoginIp', 'remark' ]),
	listeners : {
		'load' : function(store, records, options) {
			merchant.alwaysFun();
		}
	}
});

/** 基本信息-选择模式 */
merchant.selModel = new Ext.grid.CheckboxSelectionModel({
	singleSelect : true,
	listeners : {
		'rowselect' : function(selectionModel, rowIndex, record) {
			merchant.editAction.enable();
			merchant.resetExpPwdAction.enable();
		},
		'rowdeselect' : function(selectionModel, rowIndex, record) {
			merchant.editAction.disable();
			merchant.resetExpPwdAction.disable();
		}
	}
});
/** 基本信息-数据列 */
merchant.colModel = new Ext.grid.ColumnModel({
	defaults : {
		sortable : true,
		width : 110
	},
	columns : [ merchant.selModel, {
		hidden : true,
		header : '用户ID',
		dataIndex : 'id'
	}, {
		header : '账号',
		dataIndex : 'account'
	}, {
		header : '用户姓名',
		dataIndex : 'realName'
	}, {
		hidden: true,
		header : '消费密码',
		dataIndex : 'expensePassword'
	}, {
		header : '性别',
		dataIndex : 'sex',
		renderer : function(v) {
			return Share.map(v, merchant.SEX, '其他');
		}
	},{
		header : '城市',
		dataIndex : 'cityId',
		renderer : function(v) {
			return Share.map(v, merchant.AREAMAP, '未设置');
		}
	}, {
		header : '电子邮件',
		dataIndex : 'email'
	}, {
		header : '手机',
		dataIndex : 'mobile'
	}, {
		header : '电话',
		dataIndex : 'officePhone'
	}, {
		header : 'QQ',
		dataIndex : 'qqNo'
	}, {
		header : '上次登录时间',
		dataIndex : 'lastLoginTime',
		renderer: Ext.util.Format.dateRenderer('Y-m-d')
	}, {
		header : '上次登录IP地址',
		dataIndex : 'lastLoginIp'
	}, {
		header : '备注',
		dataIndex : 'remark',
		renderer : function(value, metadata, record) {
			if(value){
			metadata.attr = 'ext:qwidth="200" ext:qtitle="备注" ext:qtip="' + value + '"';
			}
			return value;
		}
	} ]
});

/** 编辑 */
merchant.editAction = new Ext.Action({
	text : '编辑',
	iconCls : 'user_edit',
	disabled : true,
	handler : function() {
		var record = merchant.grid.getSelectionModel().getSelected();
		merchant.addWindow.setIconClass('user_edit'); // 设置窗口的样式
		merchant.addWindow.setTitle('编辑商家'); // 设置窗口的名称
		merchant.addWindow.show().center();
		merchant.formPanel.getForm().reset();
		merchant.formPanel.getForm().loadRecord(record);
	}
});
/** 重置密码 */
merchant.resetExpPwdAction = new Ext.Action({
	text : '重置消费密码',
	iconCls : 'reset_pwd',
	disabled : true,
	handler : function() {
		var record = merchant.grid.getSelectionModel().getSelected();
		merchant.resetExpPwdWindow.setIconClass('reset_pwd'); // 设置窗口的样式
		merchant.resetExpPwdWindow.setTitle('重置消费密码'); // 设置窗口的名称
		merchant.resetExpPwdWindow.show().center();
		merchant.resetExpPwdFormPanel.getForm().reset();
		merchant.resetExpPwdFormPanel.getForm().loadRecord(record);
	}
});
/** 顶部工具栏 */
merchant.tbar = [ merchant.editAction, '-', merchant.resetExpPwdAction];
/** 底部工具条 */
merchant.bbar = new Ext.PagingToolbar({
			pageSize : merchant.pageSize,
			store : merchant.store,
			displayInfo : true,
			// plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
			items : ['-', '&nbsp;', merchant.pageSizeCombo]
		});
/** 基本信息-表格 */
merchant.grid = new Ext.grid.GridPanel({
	store : merchant.store,
	colModel : merchant.colModel,
	selModel : merchant.selModel,
	tbar : merchant.tbar,
	bbar : merchant.bbar,
	autoScroll : 'auto',
	region : 'center',
	loadMask : true,
	viewConfig:{},
	stripeRows : true
});
merchant.sexCombo = new Ext.form.ComboBox({
	fieldLabel : '性别',
	hiddenName : 'sex',
	name : 'sex',
	triggerAction : 'all',
	mode : 'local',
	store : new Ext.data.ArrayStore({
				fields : ['v', 't'],
				data : Share.map2Ary(merchant.SEX)
			}),
	valueField : 'v',
	displayField : 't',
	allowBlank : false,
	editable : false,
	anchor : '99%'
});
merchant.cityCombo = new Ext.form.ComboBox({
	emptyText : '请选择城市...',
	fieldLabel : '城市',
	hiddenName : 'cityId',
	name : 'cityId',
	triggerAction : 'all',
	mode : 'local',
	store : new Ext.data.ArrayStore({
				fields : ['v', 't'],
				data : Share.map2Ary(merchant.AREAMAP)
			}),
	valueField : 'v',
	displayField : 't',
	allowBlank : false,
	editable : false,
	anchor : '99%'
});
/** 基本信息-详细信息的form */
merchant.formPanel = new Ext.form.FormPanel({
	autoScroll : true,
	frame : false,
	title : '用户信息',
	bodyStyle : 'padding:10px;border:0px',
	labelwidth : 70,
	defaultType : 'textfield',
	items : [ {
		xtype : 'hidden',
		fieldLabel : 'ID',
		name : 'id'
	}, {
		fieldLabel : '账号',
		maxLength : 64,
		allowBlank : false,
		name : 'account',
		anchor : '99%'
	}, {
		fieldLabel : '用户姓名',
		maxLength : 64,
		allowBlank : false,
		name : 'realName',
		anchor : '99%'
	}, merchant.sexCombo, merchant.cityCombo, {
		fieldLabel : '电子邮件',
		maxLength : 64,
		allowBlank : false,
		regex : /^[a-zA-Z0-9_\.\-]+\@([a-zA-Z0-9\-]+\.)+[a-zA-Z0-9]{2,4}$/,
		regexText : '请输入有效的邮箱地址',
		name : 'email',
		anchor : '99%'
	}, {
		fieldLabel : '手机',
		maxLength : 16,
		allowBlank : false,
		name : 'mobile',
		anchor : '99%'
	}, {
		fieldLabel : '电话',
		maxLength : 14,
		allowBlank : false,
		name : 'officePhone',
		anchor : '99%',
		listeners: {
	        change: function() {
	           var phoneField = merchant.formPanel.getForm().findField("officePhone");
	           var phone = phoneField.getValue();
	           if(phone.length == 0) {
	        	   Ext.Msg.alert('提示', '请输入电话号码');
	           }
	           for(var i=0; i<phone.length; i++) {
	        	   if(isNaN(phone.charAt(i))) {
	        		   Ext.Msg.alert('提示', '输入电话号码有误');
	        		   phoneField.setValue("");
	        		   return;
	        	   }
	           }
	        }
	    }
	}, {
		fieldLabel : 'QQ',
		maxLength : 14,
		allowBlank : false,
		name : 'qqNo',
		anchor : '99%'
	}, {
		xtype : 'textarea',
		fieldLabel : '备注',
		maxLength : 128,
		height : 90,
		name : 'remark',
		anchor : '99%'
	} ]
});

/** 编辑新建窗口 */
merchant.addWindow = new Ext.Window({
	layout : 'fit',
	width : 500,
	height : 450,
	closeAction : 'hide',
	plain : true,
	modal : true,
	resizable : true,
	items : [merchant.formPanel],
	buttons : [{
				text : '保存',
				handler : function() {
					merchant.saveFun();
				}
			}, {
				text : '重置',
				handler : function() {
					var form = merchant.formPanel.getForm();
					var id = form.findField("id").getValue();
					form.reset();
					if (id != '')
						form.findField("id").setValue(id);
				}
			}]
});

merchant.tipLabel =  new Ext.form.Label({
    text:"消费密码为消费者使用时商家需输入的密码，长度为4的数字！"
});

merchant.resetExpPwdFormPanel = new Ext.form.FormPanel({
	autoScroll : true,
	frame : false,
	title : '修改消费密码',
	bodyStyle : 'padding:10px;border:0px',
	labelwidth : 70,
	defaultType : 'textfield',
	items : [{
		inputType: 'password',
		fieldLabel : '新消费密码',
		maxLength : 4,
		allowBlank : false,
		xtype : 'numberfield',
		name : 'newExpensePassword',
		anchor : '99%'
	}, {
		inputType: 'password',
		fieldLabel : '确认消费密码',
		maxLength : 4,
		allowBlank : false,
		xtype : 'numberfield',
		name : 'cmpExpensePassword',
		anchor : '99%'
	}, merchant.tipLabel]
});

merchant.resetExpPwdWindow = new Ext.Window({
	layout : 'fit',
	width : 300,
	height : 200,
	closeAction : 'hide',
	plain : true,
	modal : true,
	resizable : true,
	items : [merchant.resetExpPwdFormPanel],
	buttons : [{
				text : '保存',
				handler : function() {
					merchant.resetExpPwdFun();
				}
			}, {
				text : '重置',
				handler : function() {
					var form = merchant.resetExpPwdFormPanel.getForm();
					form.reset();
				}
			}]
});

merchant.saveFun = function() {
	var form = merchant.formPanel.getForm();
	if (!form.isValid()) {
		return;
	}
	// 发送请求
	Share.AjaxRequest({
				url : merchant.save,
				params : form.getValues(),
				callback : function(json) {
					merchant.addWindow.hide();
					merchant.alwaysFun();
					merchant.store.reload();
				}
	});
};
merchant.resetExpPwdFun = function() {
	var form = merchant.resetExpPwdFormPanel.getForm();
	if (!form.isValid()) {
		return;
	}
	// 发送请求
	Share.AjaxRequest({
				url : merchant.reset,
				params : form.getValues(),
				callback : function(json) {
					merchant.resetExpPwdWindow.hide();
					merchant.alwaysFun();
					merchant.store.reload();
				}
	});
};

merchant.alwaysFun = function() {
	Share.resetGrid(merchant.grid);
	merchant.resetExpPwdAction.disable();
	merchant.editAction.disable();
};

merchant.myPanel = new Ext.Panel({
			id : '${param.id}' + '_panel',
			renderTo : '${param.id}',
			layout : 'border',
			boder : false,
			height : index.tabPanel.getInnerHeight() - 1,
			items : [merchant.grid]
		});
</script>
