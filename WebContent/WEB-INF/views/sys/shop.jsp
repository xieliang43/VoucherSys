<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<div id="${param.id}"></div>

<script type="text/javascript">
Ext.ns("Ext.Authority.shop"); // 自定义一个命名空间
shop = Ext.Authority.shop; // 定义命名空间的别名
shop = {
	all : ctx + '/merchantShopAction!loadAll.action',// 加载所有
	save : ctx + "/merchantShopAction!save.action",//保存
	del : ctx + "/merchantShopAction!delete.action",//删除
	loadArea: ctx + "/merchantShopAction!loadAreaByCity.action",
	SHOPTYPE : eval('(${shopTypeMap})'),
	AREAMAP : eval('(${shopAreaMap})'),
	CITYMAP : eval('(${shopCityMap})'),
	pageSize : 20, // 每页显示的记录数
};

/** 改变页的combo */
shop.pageSizeCombo = new Share.pageSizeCombo({
			value : '20',
			listeners : {
				select : function(comboBox) {
					shop.pageSize = parseInt(comboBox.getValue());
					shop.bbar.pageSize = parseInt(comboBox.getValue());
					shop.store.baseParams.limit = shop.pageSize;
					shop.store.baseParams.start = 0;
					shop.store.load();
				}
			}
		});
// 覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
shop.pageSize = parseInt(shop.pageSizeCombo.getValue());
/** 基本信息-数据源 */
shop.store = new Ext.data.Store({
			autoLoad : true,
			remoteSort : true,
			baseParams : {
				start : 0,
				limit : shop.pageSize,
				shopName: null
			},
			proxy : new Ext.data.HttpProxy({// 获取数据的方式
				method : 'POST',
				url : shop.all
			}),
			reader : new Ext.data.JsonReader({// 数据读取器
				totalProperty : 'results', // 记录总数
				root : 'rows' // Json中的列表数据根节点
			}, ['id', 'shopName', 'shopAddress', 'telNo', 'shopTypeId', 'image',
					'description', 'cityId', 'areaId', 'createDate']),
			listeners : {
				'load' : function(store, records, options) {
					shop.alwaysFun();
				}
			}
		});
shop.shopTypeCombo = new Ext.form.ComboBox({
	emptyText : '请选择类型...',
	fieldLabel : '类型',
	hiddenName : 'shopTypeId',
	name : 'shopTypeId',
	triggerAction : 'all',
	mode : 'local',
	store : new Ext.data.ArrayStore({
				fields : ['v', 't'],
				data : Share.map2Ary(shop.SHOPTYPE)
			}),
	valueField : 'v',
	displayField : 't',
	allowBlank : false,
	editable : false,
	anchor : '99%'
});

shop.cityCombo = new Ext.form.ComboBox({
	emptyText : '请选择城市...',
	id : 'city_id',
	fieldLabel : '城市',
	hiddenName : 'cityId',
	name : 'cityId',
	triggerAction : 'all',
	mode : 'local',
	store : new Ext.data.ArrayStore({
				fields : ['v', 't'],
				data : Share.map2Ary(shop.CITYMAP)
			}),
	valueField : 'v',
	displayField : 't',
	allowBlank : false,
	editable : false,
	anchor : '99%',
	listeners: {
         select : function(combo, record, index){
        	 shop.areaCombo.clearValue();
        	 shop.areaCombo.store.removeAll();
        	 shop.areaStore.proxy = new Ext.data.HttpProxy({
                 url : shop.loadArea + '?cityId=' + this.getValue()
             });
        	 shop.areaStore.reload();
        	 shop.areaCombo.store = shop.areaStore;
         }
    }
});

shop.areaStore = new Ext.data.JsonStore({
    url: shop.loadArea,
    root:'rows',
    id:'id',
    totalProperty:'total',
    fields:['id', 'name'],
    remoteSort:true
});

shop.areaStore.on('beforeload', function() {
    Ext.apply(this.baseParams);
});

shop.areaCombo = new Ext.form.ComboBox({
	emptyText : '请选择区...',
	id: 'area_id',
	fieldLabel : '区',
	triggerAction : 'all',
	mode : 'local',
	name: 'areaId',
    hiddenName: 'areaId',
	store : new Ext.data.ArrayStore({
		fields : ['id', 'name'],
		data : Share.map2Ary(shop.AREAMAP)
	}),
	valueField : 'id',
	displayField : 'name',
	allowBlank : false,
	editable : false,
	anchor : '99%'
});
/** 基本信息-选择模式 */
shop.selModel = new Ext.grid.CheckboxSelectionModel({
			singleSelect : true,
			listeners : {
				'rowselect' : function(selectionModel, rowIndex, record) {
					shop.deleteAction.enable();
					shop.editAction.enable();
				},
				'rowdeselect' : function(selectionModel, rowIndex, record) {
					shop.alwaysFun();
				}
			}
		});
/** 基本信息-数据列 */
shop.colModel = new Ext.grid.ColumnModel({
			defaults : {
				sortable : true,
				width : 140
			},
			columns : [shop.selModel, {
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
							return Share.map(v, shop.SHOPTYPE, '');
						}
					}, {
						header : '联系电话',
						dataIndex : 'telNo'
					}, {
						header : '城市',
						dataIndex : 'cityId',
						renderer : function(v) {
							return Share.map(v, shop.CITYMAP, '');
						}
					}, {
						header : '区',
						dataIndex : 'areaId',
						renderer : function(v) {
							return Share.map(v, shop.AREAMAP, '');
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
shop.addAction = new Ext.Action({
			text : '新建',
			iconCls : 'module_add',
			handler : function() {
				shop.addWindow.setIconClass('module_add'); // 设置窗口的样式
				shop.addWindow.setTitle('新建商店'); // 设置窗口的名称
				shop.addWindow.show().center(); // 显示窗口
				shop.formPanel.getForm().reset(); // 清空表单里面的元素的值.
				shop.shopTypeCombo.clearValue();
				shop.cityCombo.clearValue();
				shop.areaCombo.clearValue();
	        	shop.areaCombo.store.removeAll();
	        	shop.areaStore.reload();
	        	shop.areaCombo.store = shop.areaStore;
			}
		});
/** 编辑 */
shop.editAction = new Ext.Action({
			text : '编辑',
			iconCls : 'module_edit',
			disabled : true,
			handler : function() {
				var record = shop.grid.getSelectionModel().getSelected();
				shop.addWindow.setIconClass('module_edit'); // 设置窗口的样式
				shop.addWindow.setTitle('编辑商店'); // 设置窗口的名称
				shop.addWindow.show().center();
				shop.formPanel.getForm().reset();
				shop.formPanel.getForm().loadRecord(record);
				shop.areaCombo.clearValue();
	        	shop.areaCombo.store.removeAll();
	        	shop.areaStore.proxy = new Ext.data.HttpProxy({
	                 url : shop.loadArea + '?cityId=' + record.data.cityId
	             });
	        	shop.areaStore.reload();
	        	shop.areaCombo.store = shop.areaStore;
			}
		});
/** 删除 */
shop.deleteAction = new Ext.Action({
			text : '删除',
			iconCls : 'module_delete',
			disabled : true,
			handler : function() {
				shop.delFun();
			}
		});
/** 查询 */
shop.searchField = new Ext.ux.form.SearchField({
			store : shop.store,
			paramName : 'shopName',
			emptyText : '请输入商店名称',
			style : 'margin-left: 5px;'
		});
/** 顶部工具栏 */
shop.tbar = [shop.addAction, '-', shop.editAction, '-',
		shop.deleteAction, '-', shop.searchField];
/** 底部工具条 */
shop.bbar = new Ext.PagingToolbar({
			pageSize : shop.pageSize,
			store : shop.store,
			displayInfo : true,
			// plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
			items : ['-', '&nbsp;', shop.pageSizeCombo]
		});
/** 基本信息-表格 */
shop.grid = new Ext.grid.GridPanel({
			// title : '模块列表',
			store : shop.store,
			colModel : shop.colModel,
			selModel : shop.selModel,
			tbar : shop.tbar,
			bbar : shop.bbar,
			autoScroll : 'auto',
			region : 'center',
			loadMask : true,
			stripeRows : true,
			listeners : {},
			viewConfig : {}
		});
/** 基本信息-详细信息的form */
shop.formPanel = new Ext.form.FormPanel({
			frame : false,
			title : '商店信息',
			bodyStyle : 'padding:10px;border:0px',
			labelwidth : 50,
			defaultType : 'textfield',
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
					}, shop.shopTypeCombo, {
						fieldLabel : '联系电话',
						maxLength : 512,
						allowBlank : false,
						name : 'telNo',
						anchor : '99%'
					}, shop.cityCombo, shop.areaCombo, {
						fieldLabel : '地址',
						maxLength : 64,
						allowBlank : false,
						name : 'shopAddress',
						blankText:'请准确填写您的商铺地址',
						anchor : '99%'
					}, {
						fieldLabel : '图片',
						maxLength : 64,
						allowBlank : false,
						name : 'image',
						anchor : '99%'
					}, {
						fieldLabel : '描述',
						maxLength : 512,
						allowBlank : false,
						name : 'description',
						anchor : '99%'
					}]
		});
/** 编辑新建窗口 */
shop.addWindow = new Ext.Window({
			layout : 'fit',
			width : 500,
			height : 420,
			closeAction : 'hide',
			plain : true,
			modal : true,
			resizable : true,
			items : [shop.formPanel],
			buttons : [{
						text : '保存',
						handler : function() {
							shop.saveFun();
						}
					}, {
						text : '重置',
						handler : function() {
							var form = shop.formPanel.getForm();
							var id = form.findField("id").getValue();
							form.reset();
							if (id != '')
								form.findField("id").setValue(id);
						}
					}]
		});
shop.alwaysFun = function() {
	Share.resetGrid(shop.grid);
	shop.deleteAction.disable();
	shop.editAction.disable();
};
shop.saveFun = function() {
	var form = shop.formPanel.getForm();
	if (!form.isValid()) {
		return;
	}
	// 发送请求
	Share.AjaxRequest({
				url : shop.save,
				params : form.getValues(),
				callback : function(json) {
					shop.addWindow.hide();
					shop.alwaysFun();
					shop.store.reload();
				}
			});
};
shop.delFun = function() {
	var record = shop.grid.getSelectionModel().getSelected();
	Ext.Msg.confirm('提示', '你真的要删除选中的商店吗?', function(btn, text) {
				if (btn == 'yes') {
					// 发送请求
					Share.AjaxRequest({
								url : shop.del + "?id=" + record.data.id,
								callback : function(json) {
									shop.alwaysFun();
									shop.store.reload();
								}
							});
				}
			});
};
shop.myPanel = new Ext.Panel({
			id : '${param.id}' + '_panel',
			renderTo : '${param.id}',
			layout : 'border',
			boder : false,
			height : index.tabPanel.getInnerHeight() - 1,
			items : [shop.grid]
		});
</script>
