package com.voucher.action.sys;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.voucher.action.BaseAction;
import com.voucher.constants.WebConstants;
import com.voucher.entity.sys.SysField;
import com.voucher.pojo.ExtGridReturn;
import com.voucher.pojo.ExtPager;
import com.voucher.pojo.ExtReturn;
import com.voucher.service.sys.SysFieldService;

public class SysFieldAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6495412012940544506L;

	
	private SysFieldService sysFieldService;

	private int start;
	private int limit;
	/**
	 * 大写的ASC or DESC
	 */
	private String dir;
	/**
	 * 排序的字段
	 */
	private String sort;
	
	private String id;
	private String field;
	private String fieldName;
	private String valueField;
	private String displayField;
	private String enabled;
	private String sortOrder;
	
	public void loadAll() {
		ExtPager pager = new ExtPager(limit, start, dir, sort);
		List<SysField> list = sysFieldService.getSysFieldsByFieldName(pager, fieldName);
		int total = sysFieldService.getTotalCount();
		
		this.sendExtGridReturn(new ExtGridReturn(total, list));
	}
	
	public void save() {
		if (StringUtils.isBlank(getField())) {
			sendExtReturn(new ExtReturn(false, "字段不能为空！"));
			return;
		}
		if (StringUtils.isBlank(getFieldName())) {
			sendExtReturn(new ExtReturn(false, "字段名称不能为空！"));
			return;
		}
		if (StringUtils.isBlank(getValueField())) {
			sendExtReturn(new ExtReturn(false, "字段值不能为空！"));
			return;
		}
		if (StringUtils.isBlank(getDisplayField())) {
			sendExtReturn(new ExtReturn(false, "字段显示值不能为空！"));
			return;
		}
		SysField sysField = new SysField(field, fieldName, valueField, displayField, Short.valueOf(enabled), Short.valueOf(sortOrder));
		if(StringUtils.isBlank(getId())) {
			sysFieldService.saveField(sysField);
		} else {
			sysField.setId(Integer.valueOf(id));
			sysFieldService.update(sysField);
		}
		sendExtReturn(new ExtReturn(true, "保存成功！"));
	}
	
	public void delete() {
		if (StringUtils.isBlank(id)) {
			sendExtReturn(new ExtReturn(false, "主键不能为空！"));
			return;
		}
		
		sysFieldService.deleteById(Integer.valueOf(id));
		sendExtReturn(new ExtReturn(true, "删除成功！"));
	}
	
	public void synchro() {
		Map<String, String> fieldMap = sysFieldService.getAllEnabledSysFields();
		ServletContext ctx= ServletActionContext.getServletContext();
		ctx.removeAttribute(WebConstants.FIELDS);
		ctx.setAttribute(WebConstants.FIELDS, fieldMap);
		sendExtReturn(new ExtReturn(true, "同步成功！"));
	}

	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @param fieldName the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * @return the sysFieldService
	 */
	public SysFieldService getSysFieldService() {
		return sysFieldService;
	}

	/**
	 * @param sysFieldService the sysFieldService to set
	 */
	public void setSysFieldService(SysFieldService sysFieldService) {
		this.sysFieldService = sysFieldService;
	}

	/**
	 * @return the start
	 */
	public int getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * @return the limit
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * @param limit the limit to set
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * @return the dir
	 */
	public String getDir() {
		return dir;
	}

	/**
	 * @param dir the dir to set
	 */
	public void setDir(String dir) {
		this.dir = dir;
	}

	/**
	 * @return the sort
	 */
	public String getSort() {
		return sort;
	}

	/**
	 * @param sort the sort to set
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}

	/**
	 * @return the fieldId
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param fieldId the fieldId to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}

	/**
	 * @param field the field to set
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * @return the valueField
	 */
	public String getValueField() {
		return valueField;
	}

	/**
	 * @param valueField the valueField to set
	 */
	public void setValueField(String valueField) {
		this.valueField = valueField;
	}

	/**
	 * @return the displayField
	 */
	public String getDisplayField() {
		return displayField;
	}

	/**
	 * @param displayField the displayField to set
	 */
	public void setDisplayField(String displayField) {
		this.displayField = displayField;
	}

	/**
	 * @return the enabled
	 */
	public String getEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the sortOrder
	 */
	public String getSortOrder() {
		return sortOrder;
	}

	/**
	 * @param sortOrder the sortOrder to set
	 */
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
}
