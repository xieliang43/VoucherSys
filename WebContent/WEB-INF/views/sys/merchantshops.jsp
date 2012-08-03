<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<div id="${param.id}"></div>

<script type="text/javascript">
Ext.ns("Ext.Authority.merchantshops"); // 自定义一个命名空间
merchantshops = Ext.Authority.merchantshops; // 定义命名空间的别名
merchantshops = {
	all : ctx + '/sysShopAction!loadAll.action',// 加载所有
	save : ctx + "/sysShopAction!save.action",//保存
	del : ctx + "/sysShopAction!delete.action",//删除
	loadArea: ctx + "/sysShopAction!loadAreaByCity.action",
	SHOPTYPE : eval('(${shopTypeMap})'),
	AREAMAP : eval('(${shopAreaMap})'),
	CITYMAP : eval('(${shopCityMap})'),
	pageSize : 20, // 每页显示的记录数
};

/** 改变页的combo */
merchantshops.pageSizeCombo = new Share.pageSizeCombo({
			value : '20',
			listeners : {
				select : function(comboBox) {
					merchantshops.pageSize = parseInt(comboBox.getValue());
					merchantshops.bbar.pageSize = parseInt(comboBox.getValue());
					merchantshops.store.baseParams.limit = merchantshops.pageSize;
					merchantshops.store.baseParams.start = 0;
					merchantshops.store.load();
				}
			}
		});
// 覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
merchantshops.pageSize = parseInt(merchantshops.pageSizeCombo.getValue());
/** 基本信息-数据源 */
merchantshops.store = new Ext.data.Store({
			autoLoad : true,
			remoteSort : true,
			baseParams : {
				start : 0,
				limit : merchantshops.pageSize,
				shopName: null
			},
			proxy : new Ext.data.HttpProxy({// 获取数据的方式
				method : 'POST',
				url : merchantshops.all
			}),
			reader : new Ext.data.JsonReader({// 数据读取器
				totalProperty : 'results', // 记录总数
				root : 'rows' // Json中的列表数据根节点
			}, ['id', 'shopName', 'shopAddress', 'telNo', 'shopTypeId', 'image',
					'description', 'cityId', 'areaId', 'createDate']),
			listeners : {
				'load' : function(store, records, options) {
					merchantshops.alwaysFun();
				}
			}
		});
merchantshops.shopTypeCombo = new Ext.form.ComboBox({
	emptyText : '请选择类型...',
	fieldLabel : '类型',
	hiddenName : 'shopTypeId',
	name : 'shopTypeId',
	triggerAction : 'all',
	mode : 'local',
	store : new Ext.data.ArrayStore({
				fields : ['v', 't'],
				data : Share.map2Ary(merchantshops.SHOPTYPE)
			}),
	valueField : 'v',
	displayField : 't',
	allowBlank : false,
	editable : false,
	anchor : '99%'
});

merchantshops.cityCombo = new Ext.form.ComboBox({
	emptyText : '请选择城市...',
	id : 'city_id',
	fieldLabel : '城市',
	hiddenName : 'cityId',
	name : 'cityId',
	triggerAction : 'all',
	mode : 'local',
	store : new Ext.data.ArrayStore({
				fields : ['v', 't'],
				data : Share.map2Ary(merchantshops.CITYMAP)
			}),
	valueField : 'v',
	displayField : 't',
	allowBlank : false,
	editable : false,
	anchor : '99%',
	listeners: {
         select : function(combo, record, index){
        	 merchantshops.areaCombo.clearValue();
        	 merchantshops.areaCombo.store.removeAll();
        	 merchantshops.areaStore.proxy = new Ext.data.HttpProxy({
                 url : merchantshops.loadArea + '?cityId=' + this.getValue()
             });
        	 merchantshops.areaStore.reload();
        	 merchantshops.areaCombo.store = merchantshops.areaStore;
         }
    }
});

merchantshops.areaStore = new Ext.data.JsonStore({
    url: merchantshops.loadArea,
    root:'rows',
    id:'id',
    totalProperty:'total',
    fields:['id', 'name'],
    remoteSort:true
});

merchantshops.areaStore.on('beforeload', function() {
    Ext.apply(this.baseParams);
});

merchantshops.areaCombo = new Ext.form.ComboBox({
	emptyText : '请选择区...',
	id: 'area_id',
	fieldLabel : '区',
	triggerAction : 'all',
	mode : 'local',
	name: 'areaId',
    hiddenName: 'areaId',
	store : new Ext.data.ArrayStore({
		fields : ['id', 'name'],
		data : Share.map2Ary(merchantshops.AREAMAP)
	}),
	valueField : 'id',
	displayField : 'name',
	allowBlank : false,
	editable : false,
	anchor : '99%'
});
/** 基本信息-选择模式 */
merchantshops.selModel = new Ext.grid.CheckboxSelectionModel({
			singleSelect : true,
			listeners : {
				'rowselect' : function(selectionModel, rowIndex, record) {
					merchantshops.deleteAction.enable();
					merchantshops.editAction.enable();
				},
				'rowdeselect' : function(selectionModel, rowIndex, record) {
					merchantshops.alwaysFun();
				}
			}
		});
/** 基本信息-数据列 */
merchantshops.colModel = new Ext.grid.ColumnModel({
			defaults : {
				sortable : true,
				width : 140
			},
			columns : [merchantshops.selModel, {
						hidden : true,
						header : '编号',
						dataIndex : 'id'
					}, {
						header : '商店名称',
						dataIndex : 'shopName'
					}, {
						header : '类型',
						dataIndex : 'shopTypeId',
						renderer : function(v) {
							return Share.map(v, merchantshops.SHOPTYPE, '');
						}
					}, {
						header : '联系电话',
						dataIndex : 'telNo'
					}, {
						header : '城市',
						dataIndex : 'cityId',
						renderer : function(v) {
							return Share.map(v, merchantshops.CITYMAP, '');
						}
					}, {
						header : '区',
						dataIndex : 'areaId',
						renderer : function(v) {
							return Share.map(v, merchantshops.AREAMAP, '');
						}
					}, {
						header : '地址',
						dataIndex : 'shopAddress'
					}, {
						header : '图片',
						dataIndex : 'image'
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
merchantshops.addAction = new Ext.Action({
			text : '新建',
			iconCls : 'module_add',
			handler : function() {
				merchantshops.addWindow.setIconClass('module_add'); // 设置窗口的样式
				merchantshops.addWindow.setTitle('新建商店'); // 设置窗口的名称
				merchantshops.addWindow.show().center(); // 显示窗口
				merchantshops.formPanel.getForm().reset(); // 清空表单里面的元素的值.
				merchantshops.shopTypeCombo.clearValue();
				merchantshops.cityCombo.clearValue();
				merchantshops.areaCombo.clearValue();
	        	merchantshops.areaCombo.store.removeAll();
	        	merchantshops.areaStore.reload();
	        	merchantshops.areaCombo.store = merchantshops.areaStore;
			}
		});
/** 编辑 */
merchantshops.editAction = new Ext.Action({
			text : '编辑',
			iconCls : 'module_edit',
			disabled : true,
			handler : function() {
				var record = merchantshops.grid.getSelectionModel().getSelected();
				merchantshops.addWindow.setIconClass('module_edit'); // 设置窗口的样式
				merchantshops.addWindow.setTitle('编辑商店'); // 设置窗口的名称
				merchantshops.addWindow.show().center();
				merchantshops.formPanel.getForm().reset();
				merchantshops.formPanel.getForm().loadRecord(record);
				merchantshops.areaCombo.clearValue();
	        	merchantshops.areaCombo.store.removeAll();
	        	merchantshops.areaStore.proxy = new Ext.data.HttpProxy({
	                 url : merchantshops.loadArea + '?cityId=' + record.data.cityId
	             });
	        	merchantshops.areaStore.reload();
	        	merchantshops.areaCombo.store = merchantshops.areaStore;
			}
		});
/** 删除 */
merchantshops.deleteAction = new Ext.Action({
			text : '删除',
			iconCls : 'module_delete',
			disabled : true,
			handler : function() {
				merchantshops.delFun();
			}
		});
/** 查询 */
merchantshops.searchField = new Ext.ux.form.SearchField({
			store : merchantshops.store,
			paramName : 'shopName',
			emptyText : '请输入商店名称',
			style : 'margin-left: 5px;'
		});
/** 顶部工具栏 */
merchantshops.tbar = [merchantshops.addAction, '-', merchantshops.editAction, '-',
		merchantshops.deleteAction, '-', merchantshops.searchField];
/** 底部工具条 */
merchantshops.bbar = new Ext.PagingToolbar({
			pageSize : merchantshops.pageSize,
			store : merchantshops.store,
			displayInfo : true,
			// plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
			items : ['-', '&nbsp;', merchantshops.pageSizeCombo]
		});
/** 基本信息-表格 */
merchantshops.grid = new Ext.grid.GridPanel({
			// title : '模块列表',
			store : merchantshops.store,
			colModel : merchantshops.colModel,
			selModel : merchantshops.selModel,
			tbar : merchantshops.tbar,
			bbar : merchantshops.bbar,
			autoScroll : 'auto',
			region : 'center',
			loadMask : true,
			stripeRows : true,
			listeners : {},
			viewConfig : {}
		});
merchantshops.tipLabel =  new Ext.form.Label({
    text:"推荐配置：图片大小: 480 x 480, 大小限制：5M"
});
/** 基本信息-详细信息的form */
merchantshops.formPanel = new Ext.form.FormPanel({
			frame : false,
			title : '商店信息',
			bodyStyle : 'padding:10px;border:0px',
			labelwidth : 50,
			defaultType : 'textfield',
			fileUpload : true,
			items : [{
						xtype : 'hidden',
						fieldLabel : 'ID',
						name : 'id',
						anchor : '99%'
					}, {
						fieldLabel : '商店名称',
						maxLength : 64,
						allowBlank : false,
						name : 'shopName',
						anchor : '99%'
					}, merchantshops.shopTypeCombo, {
						fieldLabel : '联系电话',
						maxLength : 512,
						allowBlank : false,
						name : 'telNo',
						anchor : '99%'
					}, merchantshops.cityCombo, merchantshops.areaCombo, {
						fieldLabel : '地址',
						maxLength : 64,
						allowBlank : false,
						name : 'shopAddress',
						blankText:'请准确填写您的商铺地址',
						anchor : '99%'
					}, {
						xtype : 'textarea',
						fieldLabel : '描述',
						maxLength : 512,
						allowBlank : false,
						name : 'description',
						anchor : '99%'
					}, {
						id : 'upload',
						name : 'upload',
						fieldLabel : "图片",
						inputType : "file",
						xtype : "field"
					}, merchantshops.tipLabel]
		});
/** 编辑新建窗口 */
merchantshops.addWindow = new Ext.Window({
			layout : 'fit',
			width : 500,
			height : 420,
			closeAction : 'hide',
			plain : true,
			modal : true,
			resizable : true,
			items : [merchantshops.formPanel],
			buttons : [{
						text : '保存',
						handler : function() {
							merchantshops.saveFun();
						}
					}, {
						text : '重置',
						handler : function() {
							var form = merchantshops.formPanel.getForm();
							var id = form.findField("id").getValue();
							form.reset();
							if (id != '')
								form.findField("id").setValue(id);
						}
					}]
		});
merchantshops.alwaysFun = function() {
	Share.resetGrid(merchantshops.grid);
	merchantshops.deleteAction.disable();
	merchantshops.editAction.disable();
};
merchantshops.saveFun = function() {
	var form = merchantshops.formPanel.getForm();
	if (!form.isValid()) {
		return;
	}
	form.submit({
		url : merchantshops.save,
		method : "POST",
		success : function(form, action) {
			merchantshops.addWindow.hide();
		},
		failure : function() {
			merchantshops.addWindow.hide();
			merchantshops.alwaysFun();
			merchantshops.store.reload();
	    }
	});
};
merchantshops.delFun = function() {
	var record = merchantshops.grid.getSelectionModel().getSelected();
	Ext.Msg.confirm('提示', '你真的要删除选中的商店吗?', function(btn, text) {
				if (btn == 'yes') {
					// 发送请求
					Share.AjaxRequest({
								url : merchantshops.del + "?id=" + record.data.id,
								callback : function(json) {
									merchantshops.alwaysFun();
									merchantshops.store.reload();
								}
							});
				}
			});
};
merchantshops.myPanel = new Ext.Panel({
			id : '${param.id}' + '_panel',
			renderTo : '${param.id}',
			layout : 'border',
			boder : false,
			height : index.tabPanel.getInnerHeight() - 1,
			items : [merchantshops.grid]
		});
</script>
