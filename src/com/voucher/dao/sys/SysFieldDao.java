package com.voucher.dao.sys;

import java.util.List;

import com.voucher.entity.sys.SysField;
import com.voucher.pojo.ExtPager;

public interface SysFieldDao {
	List<SysField> getAllEnabledSysFields();
	List<SysField> getSysFieldsByFieldName(ExtPager pager, String fieldName);
	List<SysField> getAllSysFields(ExtPager pager);
	int getTotalCount();
	void save(SysField field);
	void update(SysField field);
	void deleteById(int id);
	SysField findById(int id);
}
