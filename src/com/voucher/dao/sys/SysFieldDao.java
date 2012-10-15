package com.voucher.dao.sys;

import java.util.List;

import com.voucher.dao.BaseDao;
import com.voucher.entity.sys.SysField;
import com.voucher.pojo.ExtPager;

public interface SysFieldDao extends BaseDao<SysField> {
	List<SysField> getAllEnabledSysFields();
	List<SysField> getSysFieldsByFieldName(ExtPager pager, String fieldName);
	List<SysField> getAllSysFields(ExtPager pager);
}
