package com.voucher.service.sys;

import java.util.List;
import java.util.Map;

import com.voucher.entity.sys.SysField;
import com.voucher.pojo.ExtPager;

public interface SysFieldService {

	SysField selectByPrimaryKey(String fieldId);

	void saveField(SysField field);

	void delete(SysField field);
	
	void deleteById(int id);
	
	void update(SysField field);
	
	Map<String, String> getAllEnabledSysFields();
	
	List<SysField> getSysFieldsByFieldName(ExtPager pager, String fieldName);
	
	int getTotalCount();
}