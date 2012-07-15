package com.voucher.pojo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.voucher.entity.sys.SysModule;

/**
 * 菜单类
 * 
 */
public class TreeMenu {

	private List<SysModule> list;
	private SysModule root;

	public TreeMenu(List<SysModule> list) {
		this.list = list;
		this.root = new SysModule();
	}

	/**
	 * 组合json
	 * 
	 * @param list
	 * @param node
	 */
	private Tree getNodeJson(List<SysModule> list, SysModule node) {
		Tree tree = new Tree();
		tree.setId("_authority_" + node.getId());
		tree.setText(node.getModuleName());
		tree.setIconCls(node.getIconCss());
		tree.setChildren(new ArrayList<Tree>());
		if (list == null) {
			// 防止没有权限菜单时
			return tree;
		}
		if (hasChild(list, node)) {
			List<Tree> lt = new ArrayList<Tree>();
			tree.setUrl("");
			tree.setLeaf(node.getLeaf() == 1 ? true : false);
			tree.setExpanded(node.getExpanded() == 1 ? true : false);
			List<SysModule> childList = getChildList(list, node);
			Iterator<SysModule> it = childList.iterator();
			while (it.hasNext()) {
				SysModule modules = (SysModule) it.next();
				// 递归
				lt.add(getNodeJson(list, modules));
			}
			tree.setChildren(lt);
		} else {
			tree.setUrl(node.getModuleUrl());
			tree.setLeaf(node.getLeaf() == 1 ? true : false);
			tree.setExpanded(node.getExpanded() == 1 ? true : false);
		}

		return tree;
	}

	/**
	 * 判断是否有子节点
	 */
	private boolean hasChild(List<SysModule> list, SysModule node) {
		return getChildList(list, node).size() > 0 ? true : false;
	}

	/**
	 * 得到子节点列表
	 */
	private List<SysModule> getChildList(List<SysModule> list, SysModule modules) {
		List<SysModule> li = new ArrayList<SysModule>();
		Iterator<SysModule> it = list.iterator();
		while (it.hasNext()) {
			SysModule temp = (SysModule) it.next();
			if (temp.getParentId() == modules.getId()) {
				li.add(temp);
			}
		}
		return li;
	}

	public Tree getTreeJson() {
		// 父菜单的id为0
		this.root.setId(0);
		this.root.setLeaf((short) 0);
		this.root.setExpanded((short) 0);
		return this.getNodeJson(this.list, this.root);
	}
}
