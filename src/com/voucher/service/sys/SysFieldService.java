package com.voucher.service.sys;

import java.util.List;
import java.util.Map;

import com.voucher.entity.sys.SysField;
import com.voucher.pojo.ExtPager;
import com.voucher.service.BaseService;

public interface SysFieldService extends BaseService<SysField> {

	SysField selectByPrimaryKey(String fieldId);
	
	Map<String, String> getAllEnabledSysFields();
	
	List<SysField> getSysFieldsByFieldName(ExtPager pager, String fieldName);
	
	int getTotalCount();
	
	void deleteFieldById(int id);
}