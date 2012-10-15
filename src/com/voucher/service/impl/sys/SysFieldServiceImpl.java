package com.voucher.service.impl.sys;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.voucher.dao.sys.SysFieldDao;
import com.voucher.entity.sys.SysField;
import com.voucher.pojo.ExtPager;
import com.voucher.service.impl.BaseServiceImpl;
import com.voucher.service.sys.SysFieldService;
import com.voucher.util.JackJson;

public class SysFieldServiceImpl extends BaseServiceImpl<SysField> implements SysFieldService {
	private SysFieldDao sysFieldDao;
	
	@Override
	public SysField selectByPrimaryKey(String fieldId) {
		return sysFieldDao.findById(SysField.class, Integer.valueOf(fieldId));
	}

	@Override
	public Map<String, String> getAllEnabledSysFields() {
		List<SysField> list = sysFieldDao.getAllEnabledSysFields();
		HashMap<String, LinkedHashMap<String, String>> all = new HashMap<String, LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> part = null;
		for (int i = 0; i < list.size(); i++) {
			SysField baseFields = list.get(i);
			String key = baseFields.getField();
			if (all.containsKey(key)) {
				// 如果包含这个field，就加入它的值
				part = all.get(key);
				part.put(baseFields.getValueField(), baseFields.getDisplayField());
			} else {
				part = new LinkedHashMap<String, String>();
				part.put(baseFields.getValueField(), baseFields.getDisplayField());
				// 没有这个fiel，则新加入这个filed
				all.put(key, part);
			}
		}
		part = new LinkedHashMap<String, String>();
		for (Entry<String, LinkedHashMap<String, String>> entry : all.entrySet()) {
			String key = entry.getKey();
			HashMap<String, String> value = entry.getValue();
			// 为了eval('(${applicationScope.fields.sex})')这个单引号使用,替换所有的'，为\'
			String val = JackJson.fromObjectToJson(value).replaceAll("\\'", "\\\\'");
			part.put(key, val);
		}
		return part;
	}

	/**
	 * @return the sysFieldDao
	 */
	public SysFieldDao getSysFieldDao() {
		return sysFieldDao;
	}

	/**
	 * @param sysFieldDao the sysFieldDao to set
	 */
	public void setSysFieldDao(SysFieldDao sysFieldDao) {
		this.sysFieldDao = sysFieldDao;
	}

	@Override
	public List<SysField> getSysFieldsByFieldName(ExtPager pager, String fieldName) {
		List<SysField> list = null;
		if(StringUtils.isBlank(fieldName)) {
			list = sysFieldDao.getAllSysFields(pager);
		} else {
			list = sysFieldDao.getSysFieldsByFieldName(pager, fieldName);
		}
		return list;
	}

	@Override
	public int getTotalCount() {
		return sysFieldDao.getTotalCount(SysField.class);
	}

	@Override
	public void deleteFieldById(int id) {
		this.deleteById(SysField.class, id);
	}

}
