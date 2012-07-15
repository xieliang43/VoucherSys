$(document).ready(function () {
    Ext.namespace("Ext.Authority.header"); // 自定义一个命名空间
    header = Ext.Authority.header; // 定义命名空间的别名
    header = {
        logout: ctx + "/sysLoginAction!logout.action",
        changepwd: ctx + "/changepwd.action",
        edituser: ctx + '/myinfo.action'
    };
    $("#sayHelloSpan").text(Share.sayHello());
    $("#logout").click(function () {
        Ext.Msg.confirm("警告", "确定要退出吗？", function (btn) {
            if (btn == "yes") {
                // 发送请求
                Share.AjaxRequest({
                    url: header.logout,
                    showMsg: false,
                    callback: function (json) {
                        Ext.state.Manager.set('Cxjava_autoLogin', false);
                        Share.getWin().location = ctx;
                    }
                });
            }
        });
    });
    $("#editPassword").click(function () {
        header.changePwdWindow = new Ext.Window({
            title: '修改密码',
            width: 300,
            height: 148,
            modal: true,
            maximizable: false,
            resizable: false,
            layout: 'fit',
            plain: true,
            autoLoad: {
                url: header.changepwd,
                scripts: true,
                nocache: true
            }
        }).show();
    });
    $("#editUser").click(function () {
        header.myInfoWindow = new Ext.Window({
            title: '账户信息',
            width: 350,
            height: 330,
            modal: true,
            maximizable: false,
            resizable: false,
            layout: 'fit',
            plain: true,
            autoLoad: {
                url: header.edituser,
                scripts: true,
                nocache: true
            }
        }).show();
    });
});