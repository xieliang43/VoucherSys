﻿Ext.ns("Ext.Authority.index"); // �Զ���һ�������ռ�
index = Ext.Authority.index; // ���������ռ�ı���
index = {
    welcome: ctx + "/welcome.action",
    header: ctx + '/header.action',
    treeMenu: ctx + "/sysLoginAction!treeMenu.action"
};
// ��������
Share.swapStyle();
// ͷ��
index.headerPanel = new Ext.Panel({
    region: 'north',
    height: 65,
    border: false,
    margins: '0 0 0 0',
    collapseMode: 'mini',
    collapsible: true,
    bodyStyle: 'background-color:transparent;',
    autoLoad: {
        url: index.header,
        scripts: true,
        nocache: true
    }
});
index.menuTree = new Ext.tree.TreePanel({
    useArrows: true,
    // ����Ϊtrue��������ʹ��Vista-style�ļ�ͷ
    autoScroll: true,
    animate: true,
    // ����Ϊtrue������չ��/�۵�ʱ�Ķ���Ч��
    containerScroll: true,
    // ����Ϊtrue��ScrollManagerע�������
    border: false,
    rootVisible: false,
    // ����Ϊfalse������root�ڵ�
    margins: '2 2 0 0',
    loader: new Ext.tree.TreeLoader({
        dataUrl: index.treeMenu,
        clearOnLoad: true
    }),
    root: {
        expanded: true,
        id: '0'
    },
    listeners: {
        'click': function (node, e) { // ����¼�
            if (node.attributes.url) { // ��������� node.isLeaf()
                Share.openTab(node, ctx + node.attributes.url);
            } else {
                e.stopEvent();
            }
        }
    }
});
// �˵����
index.menuPanel = new Ext.Panel({
    region: 'west',
    title: '���˵�',
    iconCls: 'computer',
    margins: '0 2 0 0',
    layout: 'fit',
    width: 180,
    minSize: 100,
    maxSize: 300,
    split: true,
    collapsible: true,
    tools: [{
        id: 'refresh',
        handler: function () {
            index.menuTree.root.reload();
        }
    }],
    items: [index.menuTree]
});

// tab�����
index.tabPanel = new Ext.TabPanel({
    id: 'mainTabPanel',
    region: 'center',
    activeTab: 0,
    deferredRender: false,
    enableTabScroll: true,
    // bodyStyle:'height:100%',
    defaults: {
        layout: 'fit',
        autoScroll: true
    },
    plugins: new Ext.ux.TabCloseMenu({
        closeTabText: '�رձ�ǩҳ',
        closeOtherTabsText: '�ر�������ǩҳ',
        closeAllTabsText: '�ر����б�ǩҳ'
    }),
    items: [{
        id: 'home',
        title: '�ҵ���ҳ',
        iconCls: 'home',
        closable: false,
        autoScroll: true,
        autoLoad: {
            url: index.welcome,
            scripts: true,
            nocache: true
        }
    }],
    listeners: {
        'bodyresize': function (panel, neww, newh) {
            // �Զ�����tab�����panel�Ĵ�С
            var tab = panel.getActiveTab();
            var centerpanel = Ext.getCmp(tab.id + "_div_panel");
            if (centerpanel) {
                centerpanel.setHeight(newh - 2);
                centerpanel.setWidth(neww - 2);
            }
        }
    }
});

index.msgArea = new Ext.form.TextArea({
    autoScroll: true,
    readOnly: true,
    region: 'center'
});

index.msgPanel = new Ext.Panel({
    layout: 'border',
    title: '��Ϣ����',
    region: 'east',
    collapseMode: 'mini',
    width: 200,
    minSize: 100,
    maxSize: 300,
    // True����ʹpanel�۵����һ��Զ���չ��/�۵�
    // (expand/collapse)��ť��Ⱦ���������߰�ť����
    collapsible: true,
    collapsed: true,
    // �����ǰpanel���۵�Ϊtrue
    split: true,
    tbar: [{
        xtype: 'button',
        text: '����',
        iconCls: 'cancel',
        handler: function () {
            index.msgArea.reset();
        }
    }],
    items: [index.msgArea]
});
// ���ڻ�ҳ��Layout
index.viewport = new Ext.Viewport({
    layout: 'border',
    items: [index.headerPanel, index.menuPanel, index.tabPanel, index.msgPanel]
});