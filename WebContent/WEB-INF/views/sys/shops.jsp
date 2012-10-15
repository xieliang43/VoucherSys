<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<div id="${param.id}"></div>

<script type="text/javascript">
Ext.ns("Ext.Authority.shops"); // 自定义一个命名空间
shops = Ext.Authority.shops; // 定义命名空间的别名
shops = {
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
shops.pageSizeCombo = new Share.pageSizeCombo({
			value : '20',
			listeners : {
				select : function(comboBox) {
					shops.pageSize = parseInt(comboBox.getValue());
					shops.bbar.pageSize = parseInt(comboBox.getValue());
					shops.store.baseParams.limit = shops.pageSize;
					shops.store.baseParams.start = 0;
					shops.store.load();
				}
			}
		});
// 覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
shops.pageSize = parseInt(shops.pageSizeCombo.getValue());
/** 基本信息-数据源 */
shops.store = new Ext.data.Store({
			autoLoad : true,
			remoteSort : true,
			baseParams : {
				start : 0,
				limit : shops.pageSize,
				shopName: null
			},
			proxy : new Ext.data.HttpProxy({// 获取数据的方式
				method : 'POST',
				url : shops.all
			}),
			reader : new Ext.data.JsonReader({// 数据读取器
				totalProperty : 'results', // 记录总数
				root : 'rows' // Json中的列表数据根节点
			}, ['id', 'shopName', 'merchantName', 'shopAddress', 'telNo', 'shopTypeId', 'image',
					'description', 'cityId', 'areaId', 'createDate']),
			listeners : {
				'load' : function(store, records, options) {
					shops.alwaysFun();
				}
			}
		});
shops.shopTypeCombo = new Ext.form.ComboBox({
	emptyText : '请选择类型...',
	fieldLabel : '类型',
	hiddenName : 'shopTypeId',
	name : 'shopTypeId',
	triggerAction : 'all',
	mode : 'local',
	store : new Ext.data.ArrayStore({
				fields : ['v', 't'],
				data : Share.map2Ary(shops.SHOPTYPE)
			}),
	valueField : 'v',
	displayField : 't',
	allowBlank : false,
	editable : false,
	anchor : '99%'
});

shops.cityCombo = new Ext.form.ComboBox({
	emptyText : '请选择城市...',
	id : 'city_id',
	fieldLabel : '城市',
	hiddenName : 'cityId',
	name : 'cityId',
	triggerAction : 'all',
	mode : 'local',
	store : new Ext.data.ArrayStore({
				fields : ['v', 't'],
				data : Share.map2Ary(shops.CITYMAP)
			}),
	valueField : 'v',
	displayField : 't',
	allowBlank : false,
	editable : false,
	anchor : '99%',
	listeners: {
         select : function(combo, record, index){
        	 shops.areaCombo.clearValue();
        	 shops.areaCombo.store.removeAll();
        	 shops.areaStore.proxy = new Ext.data.HttpProxy({
                 url : shops.loadArea + '?cityId=' + this.getValue()
             });
        	 shops.areaStore.reload();
        	 shops.areaCombo.store = shops.areaStore;
         }
    }
});

shops.areaStore = new Ext.data.JsonStore({
    url: shops.loadArea,
    root:'rows',
    id:'id',
    totalProperty:'total',
    fields:['id', 'name'],
    remoteSort:true
});

shops.areaStore.on('beforeload', function() {
    Ext.apply(this.baseParams);
});

shops.areaCombo = new Ext.form.ComboBox({
	emptyText : '请选择区...',
	id: 'area_id',
	fieldLabel : '区',
	triggerAction : 'all',
	mode : 'local',
	name: 'areaId',
    hiddenName: 'areaId',
	store : new Ext.data.ArrayStore({
		fields : ['id', 'name'],
		data : Share.map2Ary(shops.AREAMAP)
	}),
	valueField : 'id',
	displayField : 'name',
	editable : false,
	anchor : '99%'
});
/** 基本信息-选择模式 */
shops.selModel = new Ext.grid.CheckboxSelectionModel({
			singleSelect : false,
			listeners : {
				'rowselect' : function(selectionModel, rowIndex, record) {
					shops.deleteAction.enable();
					shops.editAction.enable();
				},
				'rowdeselect' : function(selectionModel, rowIndex, record) {
					shops.alwaysFun();
				}
			}
		});
/** 基本信息-数据列 */
shops.colModel = new Ext.grid.ColumnModel({
			defaults : {
				sortable : true,
				width : 140
			},
			columns : [shops.selModel, {
						hidden : true,
						header : '编号',
						dataIndex : 'id'
					}, {
						header : '商店名称',
						dataIndex : 'shopName'
					}, {
						header : '商家',
						dataIndex : 'merchantName'
					}, {
						header : '类型',
						dataIndex : 'shopTypeId',
						renderer : function(v) {
							return Share.map(v, shops.SHOPTYPE, '');
						}
					}, {
						header : '联系电话',
						dataIndex : 'telNo'
					}, {
						header : '城市',
						dataIndex : 'cityId',
						renderer : function(v) {
							return Share.map(v, shops.CITYMAP, '');
						}
					}, {
						header : '区',
						dataIndex : 'areaId',
						renderer : function(v) {
							return Share.map(v, shops.AREAMAP, '');
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
shops.addAction = new Ext.Action({
			text : '新建',
			iconCls : 'module_add',
			handler : function() {
				shops.addWindow.setIconClass('module_add'); // 设置窗口的样式
				shops.addWindow.setTitle('新建商店'); // 设置窗口的名称
				shops.addWindow.show().center(); // 显示窗口
				shops.formPanel.getForm().reset(); // 清空表单里面的元素的值.
				shops.shopTypeCombo.clearValue();
				shops.cityCombo.clearValue();
				shops.areaCombo.clearValue();
	        	shops.areaCombo.store.removeAll();
	        	shops.areaStore.reload();
	        	shops.areaCombo.store = shops.areaStore;
	        	
	        	var form = shops.formPanel.getForm();
				var isAddField = form.findField("isAdd");
				if(isAddField.getValue() == undefined || isAddField.getValue() == "false" || isAddField.getValue().length == 0) {
					isAddField.setValue("true");
				}
			}
		});
/** 编辑 */
shops.editAction = new Ext.Action({
			text : '编辑',
			iconCls : 'module_edit',
			disabled : true,
			handler : function() {
				var selections = shops.grid.getSelectionModel().getSelections();
				var ids = [];
				for ( var i = 0; i < selections.length; i++) {
					ids.push(selections[i].data.id);
				}
				if(ids.length > 1) {
					Ext.Msg.alert("提示", "请选择一个商店进行编辑!");
					return;
				}
				
				var record = shops.grid.getSelectionModel().getSelected();
				shops.addWindow.setIconClass('module_edit'); // 设置窗口的样式
				shops.addWindow.setTitle('编辑商店'); // 设置窗口的名称
				shops.addWindow.show().center();
				shops.formPanel.getForm().reset();
				shops.formPanel.getForm().loadRecord(record);
				shops.areaCombo.clearValue();
	        	shops.areaCombo.store.removeAll();
	        	shops.areaStore.proxy = new Ext.data.HttpProxy({
	                 url : shops.loadArea + '?cityId=' + record.data.cityId
	             });
	        	shops.areaStore.reload();
	        	shops.areaCombo.store = shops.areaStore;
	        	
	        	var form = shops.formPanel.getForm();
				var isAddField = form.findField("isAdd");
				if(isAddField.getValue() == undefined || isAddField.getValue() == "true" || isAddField.getValue().length == 0) {
					isAddField.setValue("false");
				}
			}
		});
/** 删除 */
shops.deleteAction = new Ext.Action({
			text : '删除',
			iconCls : 'module_delete',
			disabled : true,
			handler : function() {
				shops.delFun();
			}
		});
/** 查询 */
shops.searchField = new Ext.ux.form.SearchField({
			store : shops.store,
			paramName : 'shopName',
			emptyText : '请输入商店名称',
			style : 'margin-left: 5px;'
		});
/** 顶部工具栏 */
shops.tbar = [shops.addAction, '-', shops.editAction, '-',
		shops.deleteAction, '-', shops.searchField];
/** 底部工具条 */
shops.bbar = new Ext.PagingToolbar({
			pageSize : shops.pageSize,
			store : shops.store,
			displayInfo : true,
			// plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
			items : ['-', '&nbsp;', shops.pageSizeCombo]
		});
/** 基本信息-表格 */
shops.grid = new Ext.grid.GridPanel({
			// title : '模块列表',
			store : shops.store,
			colModel : shops.colModel,
			selModel : shops.selModel,
			tbar : shops.tbar,
			bbar : shops.bbar,
			autoScroll : 'auto',
			region : 'center',
			loadMask : true,
			stripeRows : true,
			listeners : {},
			viewConfig : {}
		});
shops.tipLabel =  new Ext.form.Label({
    text:"推荐配置：图片大小: 480 x 480, 大小限制：200K"
});
/** 基本信息-详细信息的form */
shops.formPanel = new Ext.form.FormPanel({
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
						xtype : 'hidden',
						name : 'isAdd',
						anchor : '99%'
					}, {
						fieldLabel : '商店名称',
						maxLength : 64,
						allowBlank : false,
						name : 'shopName',
						anchor : '99%'
					}, shops.shopTypeCombo, {
						fieldLabel : '联系电话',
						maxLength : 16,
						allowBlank : false,
						name : 'telNo',
						anchor : '99%',
						listeners: {
					        change: function() {
					           var telField = shops.formPanel.getForm().findField("telNo");
					           var tel = telField.getValue();
					           if(tel.length == 0) {
					        	   Ext.Msg.alert('提示', '请输入电话号码');
					           }
					           for(var i=0; i<tel.length; i++) {
					        	   if(isNaN(tel.charAt(i))) {
					        		   Ext.Msg.alert('提示', '输入电话号码有误');
					        		   telField.setValue("");
					        		   return;
					        	   }
					           }
					        }
					    }
					}, shops.cityCombo, shops.areaCombo, {
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
					}, shops.tipLabel]
		});
/** 编辑新建窗口 */
shops.addWindow = new Ext.Window({
			layout : 'fit',
			width : 500,
			height : 420,
			closeAction : 'hide',
			plain : true,
			modal : true,
			resizable : true,
			items : [shops.formPanel],
			buttons : [{
						text : '保存',
						handler : function() {
							var form = shops.formPanel.getForm();
							var isAdd = form.findField("isAdd").getValue();
							if(isAdd == "true") {
								var upload = form.findField("upload").getValue();
								if(upload == null || upload == "") {
									Ext.Msg.alert('提示', '请添加图片！');
					        		   return;
								}
							}
							shops.saveFun();
						}
					}, {
						text : '重置',
						handler : function() {
							var form = shops.formPanel.getForm();
							var id = form.findField("id").getValue();
							form.reset();
							if (id != '')
								form.findField("id").setValue(id);
						}
					}]
		});
shops.alwaysFun = function() {
	Share.resetGrid(shops.grid);
	shops.deleteAction.disable();
	shops.editAction.disable();
};
shops.saveFun = function() {
	var form = shops.formPanel.getForm();
	if (!form.isValid()) {
		return;
	}
	form.submit({
		url : shops.save,
		waitMsg: "保存中，请稍等...",
		method : "POST",
		success : function(form, action) {
			shops.addWindow.hide();
		},
		failure : function() {
			shops.addWindow.hide();
			shops.alwaysFun();
			shops.store.reload();
	    }
	});
};
shops.delFun = function() {
	var selections = shops.grid.getSelectionModel().getSelections();
	var ids = [];
	for ( var i = 0; i < selections.length; i++) {
		ids.push(selections[i].data.id);
	}
	var params = {
			shopIds : ids
		};
	Ext.Msg.confirm('提示', '你真的要删除选中的商店吗?', function(btn, text) {
				if (btn == 'yes') {
					// 发送请求
					Share.AjaxRequest({
								url : shops.del,
								params : params,
								callback : function(json) {
									shops.alwaysFun();
									shops.store.reload();
								}
							});
				}
			});
};
shops.myPanel = new Ext.Panel({
			id : '${param.id}' + '_panel',
			renderTo : '${param.id}',
			layout : 'border',
			boder : false,
			height : index.tabPanel.getInnerHeight() - 1,
			items : [shops.grid]
		});
</script>
