$(document).ready(function () {
    Ext.ns("Ext.Authority.login"); // 自定义一个命名空间
    login = Ext.Authority.login; // 定义命名空间的别名
    login = {
        login: ctx + "/sysLoginAction!login.action",
        main: ctx + "/main.action",
        findpwd: ctx + "/findpwd.action",
        register: ctx + "/register.action"
    };
    var value = $("#cityMap").html();
    alert(value);
    // 设置主题
    Share.swapStyle();
    // 用户登录Form
    login.loginFormPanel = new Ext.FormPanel({
        id: 'loginFormPanel',
        labelWidth: 40,
        border: false,
        buttonAlign: 'center',
        style: 'border-bottom:0px;',
        bodyStyle: 'padding:10px;background-color:transparent;',
        items: [new Ext.form.TextField({ // 账号
            inputType: 'textfiled',
            id: 'account',
            name: 'account',
            fieldLabel: '账号',
            anchor: '98%',
            allowBlank: false,
            maxLength: 32,
            listeners: {
                specialKey: function (field, e) {
                    if (e.getKey() == Ext.EventObject.ENTER) {
                        Ext.getCmp("login-button").handler();
                    }
                }
            }
        }), new Ext.form.TextField({ // 密码
            inputType: 'password',
            id: 'password',
            name: 'password',
            fieldLabel: '密码',
            anchor: '98%',
            allowBlank: false,
            maxLength: 32,
            listeners: {
                specialKey: function (field, e) {
                    if (e.getKey() == Ext.EventObject.ENTER) {
                        Ext.getCmp("login-button").handler();
                    }
                }
            }
        }),
        {
            xtype: 'checkboxgroup',
            itemCls: 'x-check-group-alt',
            columns: 3,
            items: [{
                boxLabel: '记住账号',
                name: 'rememberAccount',
                listeners: {
                    check: function () {
                        if (!this.getValue()) {
                            var form = login.loginFormPanel.getForm();
                            form.findField('autoLogin').setValue(false);
                        }
                    }
                }
            }, {
                boxLabel: '记住密码',
                name: 'rememberPassword',
                listeners: {
                    check: function () {
                        if (!this.getValue()) {
                            var form = login.loginFormPanel.getForm();
                            form.findField('autoLogin').setValue(false);
                        }
                    }
                }
            }, {
                boxLabel: '自动登录',
                name: 'autoLogin',
                listeners: {
                    check: function () {
                        if (this.getValue()) {
                            var form = login.loginFormPanel.getForm();
                            form.findField('rememberAccount').setValue(true);
                            form.findField('rememberPassword').setValue(true);
                        }
                    }
                }
            }]
        }],
        buttons: [{
            id: 'login-button',
            text: '登录',
            handler: function () {
                if (Ext.isIE) {
                    if (!Ext.isIE8 && !Ext.isIE9) {
                        Ext.Msg.confirm('温馨提示', '系统检测到您正在使用基于MSIE内核的浏览器<br>我们强烈建议立即切换到<b>' + '<a href="http://firefox.com.cn/" target="_blank">FireFox</a></b>' + '或者<b><a href="http://www.google.com/chrome/?hl=zh-CN" target="_blank">' + 'GoogleChrome</a></b>浏览器体验飞一般的感觉!' + '<br>如果您还是坚持使用IE,那么请使用基于IE8内核或者更高内核的浏览器登录!', function (btn, text) {
                            if (btn == 'yes') {
                                login.loginFun();
                            }
                        });
                        return;
                    }
                }
                login.loginFun();
            }
        }, {
            text: '取消',
            handler: function () {
                login.loginFormPanel.getForm().reset();
                $("#account").focus();
            }
        }]
    });

    // 状态栏HTMl
    login.bbarHtml = "<a href='javascript:login.resetPassword();'>忘记密码？</a>";
    login.bbarHtml = login.bbarHtml	+ "&nbsp;&nbsp;<a href='javascript:login.register();'>注册</a>";
    // 用户登录窗口
    login.loginWindow = new Ext.Window({
        renderTo: 'login-win-div',
        id: 'loginWindow',
        title: '用户登录',
        width: 300,
        height: 180,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        closable: false,
        draggable: false,
        layout: 'fit',
        plain: true,
        buttonAlign: 'center',
        items: [login.loginFormPanel],
        bbar: new Ext.Panel({
            html: login.bbarHtml,
            border: false,
            bodyStyle: 'background-color:transparent;padding:3px 10px;'
        })
    }).show();
    login.loginFun = function () {
        var form = login.loginFormPanel.getForm();
        var account = $("#account").val();
        var password = $("#password").val();
        if (account == "" || password == "") {
            Ext.Msg.alert('提示', '请输入用户名和密码.');
        } else {
            var cookiePassword = Ext.state.Manager.get('Cxjava_password');
            // 判断cookie中的密码
            password = cookiePassword == password ? password : Ext.MD5(password);
            // 发送请求
            Share.AjaxRequest({
                url: login.login,
                params: {
                    account: account,
                    password: password
                },
                showMsg: false,
                callback: function (json) {
                    // 设置Cookie
                    var rememberAccount = form.findField('rememberAccount').getValue();
                    var rememberPassword = form.findField('rememberPassword').getValue();
                    var autoLogin = form.findField('autoLogin').getValue();
                    if (rememberAccount) {
                        Ext.state.Manager.set('Cxjava_account', account);
                    } else {
                        Ext.state.Manager.set('Cxjava_account', '');
                    }
                    if (rememberPassword) {
                        Ext.state.Manager.set('Cxjava_password', password);
                    } else {
                        Ext.state.Manager.set('Cxjava_password', '');
                    }
                    Ext.state.Manager.set('Cxjava_autoLogin', autoLogin);
                    Ext.state.Manager.set('Cxjava_hasLocked', false);
                    location.href = login.main;
                },
                falseFun : function(json) {//失败后想做的个性化操作函数
						if (json.msg.indexOf('密码错误') > -1) {
							$("#password").focus().val('');
							return;
						}
					}
            });
        }
    };
    // 根据cookie初期化form
    login.initLoginForm = function () {
        // 取得cookie
        var cookieAccount = Ext.state.Manager.get('Cxjava_account');
        var cookiePassword = Ext.state.Manager.get('Cxjava_password');
        var cookieAutoLogin = Ext.state.Manager.get('Cxjava_autoLogin');
        var form = login.loginFormPanel.getForm();
        // 账号
        if (cookieAccount && cookieAccount != '') {
            form.findField('account').setValue(cookieAccount);
            form.findField('rememberAccount').setValue(true);
        }
        // 密码
        if (cookiePassword && cookiePassword != '') {
            form.findField('password').setValue(cookiePassword);
            form.findField('rememberPassword').setValue(true);
        }
        // 自动登录
        form.findField('autoLogin').setValue(cookieAutoLogin);
        if (cookieAutoLogin == true) {
            Ext.getCmp("login-button").handler();
        }
    };
    // 根据cookie初期化form
    login.initLoginForm();

    // 窗口大小改变时，从新设置窗口位置
    window.onresize = function () {
        var left = ($(window).width() - login.loginWindow.getWidth()) / 2;
        login.loginWindow.setPosition(left);
    };
    // 忘记密码
    login.resetPassword = function () {
        // 跳转到忘记密码
        login.findPwdWindow = new Ext.Window({
            title: '系统将发送重置密码链接到你的注册手机！',
            width: 300,
            height: 190,
            modal: true,
            maximizable: false,
            resizable: false,
            layout: 'fit',
            plain: true,
            autoLoad: {
                url: login.findpwd,
                scripts: true,
                nocache: true
            }
        }).show();
    };
    
    login.sexCombo = new Ext.form.ComboBox({
		fieldLabel : '性别',
		name : 'sex',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
					fields : ['v', 't'],
					data : Share.map2Ary(login.SEX)
				}),
		valueField : 'v',
		displayField : 't',
		allowBlank : false,
		editable : false,
		anchor : '99%'
	});
    
    login.cityCombo = new Ext.form.ComboBox({
    	fieldLabel : '城市',
    	name : 'cityId',
    	triggerAction : 'all',
    	mode : 'local',
    	store : new Ext.data.ArrayStore({
    				fields : ['v', 't'],
    				data : Share.map2Ary(login.CITYMAP)
    			}),
    	valueField : 'v',
    	displayField : 't',
    	allowBlank : false,
    	editable : false,
    	anchor : '99%'
    });
    
    login.registerFormPanel = new Ext.form.FormPanel({
		frame : false,
		title : '注册信息',
		bodyStyle : 'padding:10px;border:0px',
		labelwidth : 50,
		defaultType : 'textfield',
		items : [{
					fieldLabel : '用户名',
					maxLength : 24,
					allowBlank : false,
					name : 'account',
					anchor : '99%'
				},{
					inputType: 'password',
					fieldLabel : '密码',
					maxLength : 32,
					allowBlank : false,
					name : 'password',
					anchor : '99%'
				},{
					fieldLabel : '昵称',
					maxLength : 64,
					allowBlank : false,
					name : 'realName',
					anchor : '99%'
				},login.sexCombo,{
					fieldLabel : '手机号码',
					xtype : 'numberfield',
					maxLength : 16,
					allowBlank : false,
					name : 'mobile',
					anchor : '99%'
				},{
					fieldLabel : '电话',
					xtype : 'numberfield',
					maxLength : 16,
					allowBlank : false,
					name : 'officePhone',
					anchor : '99%'
				}, login.cityCombo,{
					fieldLabel : '邮箱',
					maxLength : 36,
					allowBlank : false,
					name : 'displayField',
					anchor : '99%'
				},{
					fieldLabel : 'QQ',
					xtype : 'numberfield',
					maxLength : 15,
					allowBlank : false,
					name : 'field',
					anchor : '99%'
				}]
	});
    // 注册
    login.register = function () {
        // 跳转到注册
        location.registerWindow = new Ext.Window({
			layout : 'fit',
			width : 400,
			height : 360,
			closeAction : 'hide',
			plain : true,
			modal : true,
			resizable : true,
			items : [login.registerFormPanel],
			buttons : [{
						text : '注册',
						handler : function() {
							login.saveFun();
						}
					}, {
						text : '重置',
						handler : function() {
							var form = login.registerFormPanel.getForm();
							form.reset();
						}
					}]
		}).show();
    };
    //监听事件
	var events = "beforecopy beforepaste beforedrag contextmenu selectstart drag paste copy cut dragenter";
	$("#account").bind(events, function(e) {
    	return false;
	});
	$("#password").bind(events, function(e) {
		return false;
	});
});