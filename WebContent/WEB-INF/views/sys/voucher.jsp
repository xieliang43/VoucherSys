<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<div id="${param.id}"></div>

<script type="text/javascript">
Ext.ns("Ext.Authority.vouchers"); // 自定义一个命名空间
voucher = Ext.Authority.vouchers; // 定义命名空间的别名
voucher = {
	all : ctx + '/voucherAction!loadAll.action',// 加载所有
	save : ctx + "/voucherAction!save.action",//保存
	del : ctx + "/voucherAction!delete.action",//删除
	upload : ctx + "/voucherAction!uploadImage.action",
	SHOPMAP :eval('(${vchShopMap})'),
	pageSize : 20, // 每页显示的记录数
	ENABLED : eval('(${fields.enabled==null?"{}":fields.enabled})'),
};

/** 改变页的combo */
voucher.pageSizeCombo = new Share.pageSizeCombo({
			value : '20',
			listeners : {
				select : function(comboBox) {
					voucher.pageSize = parseInt(comboBox.getValue());
					voucher.bbar.pageSize = parseInt(comboBox.getValue());
					voucher.store.baseParams.limit = voucher.pageSize;
					voucher.store.baseParams.start = 0;
					voucher.store.load();
				}
			}
		});
// 覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
voucher.pageSize = parseInt(voucher.pageSizeCombo.getValue());
/** 基本信息-数据源 */
voucher.store = new Ext.data.Store({
			autoLoad : true,
			remoteSort : true,
			baseParams : {
				start : 0,
				limit : voucher.pageSize,
				shopName: null
			},
			proxy : new Ext.data.HttpProxy({// 获取数据的方式
				method : 'POST',
				url : voucher.all
			}),
			reader : new Ext.data.JsonReader({// 数据读取器
				totalProperty : 'results', // 记录总数
				root : 'rows' // Json中的列表数据根节点
			}, ['id', 'name', 'shopId', 'price', 'quantity',
					'restQty', 'startDate', 'endDate', 'deadTime',
					'enabled', 'image', 'vchKey', 'description', 'createDate']),
			listeners : {
				'load' : function(store, records, options) {
					voucher.alwaysFun();
				}
			}
		});
voucher.shopCombo = new Ext.form.ComboBox({
	emptyText : '请选择商店...',
	fieldLabel : '商店',
	hiddenName : 'shopId',
	name : 'shopId',
	triggerAction : 'all',
	mode : 'local',
	store : new Ext.data.ArrayStore({
				fields : ['v', 't'],
				data : Share.map2Ary(voucher.SHOPMAP)
			}),
	valueField : 'v',
	displayField : 't',
	allowBlank : false,
	editable : false,
	anchor : '99%'
});
/** 基本信息-选择模式 */
voucher.selModel = new Ext.grid.CheckboxSelectionModel({
			singleSelect : true,
			listeners : {
				'rowselect' : function(selectionModel, rowIndex, record) {
					voucher.deleteAction.enable();
					voucher.editAction.enable();
				},
				'rowdeselect' : function(selectionModel, rowIndex, record) {
					voucher.alwaysFun();
				}
			}
		});
/** 基本信息-数据列 */
voucher.colModel = new Ext.grid.ColumnModel({
			defaults : {
				sortable : true,
				width : 140
			},
			columns : [voucher.selModel, {
						hidden : true,
						header : '编号',
						dataIndex : 'id'
					}, {
						header : '名称',
						dataIndex : 'name'
					}, {
						header : '商店',
						dataIndex : 'shopId',
						renderer : function(v) {
							return Share.map(v, voucher.SHOPMAP, '');
						}
					}, {
						header : '价格',
						dataIndex : 'price'
					}, {
						header : '数量',
						dataIndex : 'quantity'
					}, {
						header : '剩余',
						dataIndex : 'restQty'
					}, {
						header : '开始时间',
						dataIndex : 'startDate',
						renderer: Ext.util.Format.dateRenderer('Y-m-d')
					}, {
						header : '结束时间',
						dataIndex : 'endDate',
						renderer: Ext.util.Format.dateRenderer('Y-m-d')
					}, {
						header : '截至提示',
						dataIndex : 'deadTime'
					}, {
						header : '序号前缀',
						dataIndex : 'vchKey'
					}, /*{
						header : '是否启用',
						dataIndex : 'enabled',
						renderer : function(v) {
							return Share.map(v, voucher.ENABLED, '');
						}
					}, */{
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
voucher.addAction = new Ext.Action({
			text : '新建',
			iconCls : 'module_add',
			handler : function() {
				voucher.addWindow.setIconClass('module_add'); // 设置窗口的样式
				voucher.addWindow.setTitle('新建模块'); // 设置窗口的名称
				voucher.addWindow.show().center(); // 显示窗口
				voucher.formPanel.getForm().reset(); // 清空表单里面的元素的值.
				voucher.imageForm.getForm().reset();
				voucher.shopCombo.clearValue();
			}
		});
/** 编辑 */
voucher.editAction = new Ext.Action({
			text : '编辑',
			iconCls : 'module_edit',
			disabled : true,
			handler : function() {
				var record = voucher.grid.getSelectionModel().getSelected();
				voucher.addWindow.setIconClass('module_edit'); // 设置窗口的样式
				voucher.addWindow.setTitle('编辑模块'); // 设置窗口的名称
				voucher.addWindow.show().center();
				voucher.formPanel.getForm().reset();
				voucher.formPanel.getForm().loadRecord(record);
			}
		});
/** 删除 */
voucher.deleteAction = new Ext.Action({
			text : '删除',
			iconCls : 'module_delete',
			disabled : true,
			handler : function() {
				voucher.delFun();
			}
		});
/** 查询 */
voucher.searchField = new Ext.ux.form.SearchField({
			store : voucher.store,
			paramName : 'shopName',
			emptyText : '请输入商店名',
			style : 'margin-left: 5px;'
		});
/** 顶部工具栏 */
voucher.tbar = [voucher.addAction, '-', voucher.editAction, '-',
		voucher.deleteAction, '-', voucher.searchField];
/** 底部工具条 */
voucher.bbar = new Ext.PagingToolbar({
			pageSize : voucher.pageSize,
			store : voucher.store,
			displayInfo : true,
			// plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
			items : ['-', '&nbsp;', voucher.pageSizeCombo]
		});
/** 基本信息-表格 */
voucher.grid = new Ext.grid.GridPanel({
			// title : '模块列表',
			store : voucher.store,
			colModel : voucher.colModel,
			selModel : voucher.selModel,
			tbar : voucher.tbar,
			bbar : voucher.bbar,
			autoScroll : 'auto',
			region : 'center',
			loadMask : true,
			stripeRows : true,
			listeners : {},
			viewConfig : {}
		});

voucher.enabledCombo = new Ext.form.ComboBox({
	fieldLabel : '是否启用',
	hiddenName : 'enabled',
	name : 'enabled',
	triggerAction : 'all',
	mode : 'local',
	store : new Ext.data.ArrayStore({
				fields : ['v', 't'],
				data : Share.map2Ary(voucher.ENABLED)
			}),
	valueField : 'v',
	displayField : 't',
	allowBlank : false,
	editable : false,
	anchor : '99%'
});
voucher.startDate = new Ext.form.DateField({
	fieldLabel : '开始时间',
	name: 'startDate',
	startDateField : 'startDate',
	width: 160,
	allowBlank : false,
	format: 'Y-m-d',
	emptyText: '选择开始日期',
	menuListeners : {
        beforeshow : function() {
            var myDate=new Date();
            myDate.setFullYear(1970,0,1);
            this.menu.picker.setValue(this.getValue()||myDate);  
        },
        select : function(m, d) {
            this.setValue(this.menu.picker.getValue());
        }
    }
});
voucher.endDate = new Ext.form.DateField({
	fieldLabel : '结束时间',
	name: 'endDate',
	startDateField : 'endDate',
	width: 160,
	allowBlank : false,
	format: 'Y-m-d',
	emptyText: '选择结束日期',
	menuListeners : {
        beforeshow : function() {
            var myDate=new Date();
            myDate.setFullYear(1970,0,1);
            this.menu.picker.setValue(this.getValue()||myDate);  
        },
        select : function(m, d) {
            this.setValue(this.menu.picker.getValue());
        }
    }
});
/** 基本信息-详细信息的form */
voucher.formPanel = new Ext.form.FormPanel({
			frame : false,
			title : '代金券信息',
			bodyStyle : 'padding: 10px; border: 0px none;',
			style: "width: 464px; height: 247px;",
			labelwidth : 50,
			defaultType : 'textfield',
			items : [{
						xtype : 'hidden',
						fieldLabel : 'ID',
						name : 'id',
						anchor : '99%'
					}, {
						fieldLabel : '代金券名称',
						maxLength : 64,
						allowBlank : false,
						name : 'name',
						anchor : '99%'
					}, voucher.shopCombo, {
						fieldLabel : '价格',
						xtype : 'numberfield',
						maxLength : 12,
						allowBlank : false,
						name : 'price',
						anchor : '99%'
					}, {
						fieldLabel : '数量',
						xtype : 'numberfield',
						maxLength : 4,
						allowBlank : false,
						name : 'quantity',
						anchor : '99%'
					},voucher.startDate, voucher.endDate, {
							fieldLabel : '截至提示',
							maxLength : 64,
							allowBlank : false,
							name : 'deadTime',
							anchor : '99%'
					}, {
						fieldLabel : '序号前缀',
						maxLength : 64,
						allowBlank : false,
						name : 'vchKey',
						anchor : '99%'
					}, {
						fieldLabel : '描述',
						maxLength : 64,
						allowBlank : false,
						name : 'description',
						anchor : '99%'
					}, {
						fieldLabel : '图片',
						maxLength : 64,
						allowBlank : false,
						name : 'image',
						anchor : '99%'
					}]
		});

voucher.imageForm = new Ext.form.FormPanel({
	   id: 'imageform',
	   labelWidth: 80, 
	   labelAlign : 'right',
	   border : false,
	   fileUpload: true,
	   bodyStyle : 'padding:10px 8px 8px 8px;',
	   items:[{
	    		 xtype: 'fieldset',
	             title: '上传图片',
	             collapsible: true,
	             labelWidth: 69,
	             collapsed: true,
	             layout:'form',
	             items: [{
	            	 id : 'upload',  
	                 name : 'upload',  
	                 inputType : "file",  
	                 fieldLabel : '上传图片',
	                 xtype : 'textfield',
	                 anchor : '96%'
	             },{
	             	 border:false,
	                 layout:'form',
	                 fieldLabel : "预览图片",
	                 items:[{
	                     xtype:'panel',
	                     border:false,
	                     layout:'column',
	                     items:[{
	                       xtype : 'box',
	                       id : 'browseImage',
	                       columnWidth:.35,
	                       bodyStyle:'padding:10px 10px 10px 10px;',
	                       autoEl : {
	                           width : 102,
	                           height : 125,
	                           tag : 'img',
	                           src : ''
	                       }
	                     },{
	                     columnWidth:.5,
	                     labelAlign :'left',
	                     border:false,
	                     buttonAlign:'center',
	                     bodyStyle:'margin-left:10px;padding:5px',
	                     items:[{
				              xtype : 'label',
				              fieldLabel:'',
				              html: '<ul><li>1、图片格式只能是jpg格式。</li></ul><br/>'
				            },{
				              xtype : 'label',
				              fieldLabel:'',
				              html: '<ul><li>2、图片大小不超过300K。</li></ul><br/>'
				            },{
				              xtype : 'label',
				              fieldLabel:'',
				              html: '<ul><li>3、图片默认分辨率为102*125。</li></ul><br/>'
	                     }],
	                     buttons:[{
					         xtype : 'button',
					         fieldLabel:'',
					         text:'上传',
					         handler: function(){
					           var file_path = Ext.getCmp('upload').getValue();
					           var str = file_path.substr(file_path.lastIndexOf('.')+1,file_path.length);
					           if(str!='JPG'&&str!='jpg'){
					            	Ext.Msg.alert('错误', "上传的图像只能是jpg格式！"); 
					            	return false;
					           }
					           var imgForm = voucher.imageForm.getForm();
				               if(imgForm.isValid()){
				            	   imgForm.submit({
		                            url: voucher.upload,
		                            success:function(form, action){  
		                               var isSuc = action.result.success;
		                               var message = action.result.message;   
		                               var image_url = "/SchoolManageSystem";
		                               if(isSuc=='true'){
		                                     Ext.Msg.alert('消息', message);
		                               }else{
		                                     Ext.Msg.alert('错误', message);   
		                               }
		                               Ext.getCmp("browseImage").getEl().dom.src=image_url;
	                                },   
			                        failure:function(form, action){  
			                              var isSuc = action.result.success;
			                              var message = action.result.message;  
			                              if(isSuc=='true'){
			                                   Ext.Msg.alert('消息', message);
			                              }else{
			                                   Ext.Msg.alert('错误', message);   
			                              }
	                                      Ext.getCmp("browseImage").getEl().dom.src=image_url;
	                          		}
	                        		});
	                      		}
	                   		}
	                     }]
	                   }]
              }]
         }]
	   }]
});

/** 编辑新建窗口 */
voucher.addWindow = new Ext.Window({
			layout : 'fit',
			width : 500,
			height : 400,
			closeAction : 'hide',
			plain : true,
			modal : true,
			resizable : true,
			items : [voucher.formPanel, /*voucher.imageForm*/],
			buttons : [{
						text : '保存',
						handler : function() {
							voucher.saveFun();
						}
					}, {
						text : '重置',
						handler : function() {
							var form = voucher.formPanel.getForm();
							var id = form.findField("id").getValue();
							form.reset();
							if (id != '')
								form.findField("id").setValue(id);
						}
					}]
		});
voucher.alwaysFun = function() {
	Share.resetGrid(voucher.grid);
	voucher.deleteAction.disable();
	voucher.editAction.disable();
};
voucher.saveFun = function() {
	var form = voucher.formPanel.getForm();
	if (!form.isValid()) {
		return;
	}
	// 发送请求
	Share.AjaxRequest({
				url : voucher.save,
				method : "POST",
				params : form.getValues(),
				callback : function(json) {
					voucher.addWindow.hide();
					voucher.alwaysFun();
					voucher.store.reload();
				}
			});
};
voucher.delFun = function() {
	var record = voucher.grid.getSelectionModel().getSelected();
	Ext.Msg.confirm('提示', '你真的要删除选中的代金券吗?', function(btn, text) {
				if (btn == 'yes') {
					// 发送请求
					Share.AjaxRequest({
								url : voucher.del + "?id=" + record.data.id,
								callback : function(json) {
									voucher.alwaysFun();
									voucher.store.reload();
								}
							});
				}
			});
};
voucher.myPanel = new Ext.Panel({
			id : '${param.id}' + '_panel',
			renderTo : '${param.id}',
			layout : 'border',
			boder : false,
			height : index.tabPanel.getInnerHeight() - 1,
			items : [voucher.grid]
		});
	
</script>