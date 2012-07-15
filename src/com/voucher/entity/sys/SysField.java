package com.voucher.entity.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="T_SYS_FIELD")
public class SysField {

	/**
	 * 字段ID
	 */
	@Id
	@GeneratedValue
	private int id;

	/**
	 * 字段
	 */
	private String field;

	/**
	 * 字段名称
	 */
	@Column(name="FIELD_NAME")
	private String fieldName;

	/**
	 * 字段值
	 */
	@Column(name="VALUE_FIELD")
	private String valueField;

	/**
	 * 字段显示值
	 */
	@Column(name="DISPLAY_FIELD")
	private String displayField;

	/**
	 * 是否启用
	 */
	private Short enabled;

	/**
	 * 排序
	 */
	@Column(name="SORT_ORDER")
	private Short sortOrder;
	
	public SysField(){
		
	}

	/**
	 * @param field
	 * @param fieldName
	 * @param valueField
	 * @param displayField
	 * @param enabled
	 * @param sort
	 */
	public SysField(String field, String fieldName, String valueField,
			String displayField, Short enabled, Short sortOrder) {
		this.field = field;
		this.fieldName = fieldName;
		this.valueField = valueField;
		this.displayField = displayField;
		this.enabled = enabled;
		this.sortOrder = sortOrder;
	}

	/**
	 * @return 字段ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param fieldId
	 *            字段ID
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return 字段
	 */
	public String getField() {
		return field;
	}

	/**
	 * @param field
	 *            字段
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * @return 字段名称
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @param fieldName
	 *            字段名称
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * @return 字段值
	 */
	public String getValueField() {
		return valueField;
	}

	/**
	 * @param valueField
	 *            字段值
	 */
	public void setValueField(String valueField) {
		this.valueField = valueField;
	}

	/**
	 * @return 字段显示值
	 */
	public String getDisplayField() {
		return displayField;
	}

	/**
	 * @param displayField
	 *            字段显示值
	 */
	public void setDisplayField(String displayField) {
		this.displayField = displayField;
	}

	/**
	 * @return 是否启用
	 */
	public Short getEnabled() {
		return enabled;
	}

	/**
	 * @param enabled
	 *            是否启用
	 */
	public void setEnabled(Short enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return 排序
	 */
	public Short getSortOrder() {
		return sortOrder;
	}

	/**
	 * @param sort
	 *            排序
	 */
	public void setSortOrder(Short sortOrder) {
		this.sortOrder = sortOrder;
	}

}
