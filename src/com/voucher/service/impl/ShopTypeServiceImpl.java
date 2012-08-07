package com.voucher.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.voucher.dao.ShopTypeDao;
import com.voucher.entity.ShopType;
import com.voucher.pojo.ExtPager;
import com.voucher.service.ShopTypeService;

public class ShopTypeServiceImpl implements ShopTypeService {
	private ShopTypeDao shopTypeDao;

	@Override
	public ShopType findShopTypeById(int shopTypeId) {
		return shopTypeDao.findShopTypeById(shopTypeId);
	}

	/**
	 * @return the shopTypeDao
	 */
	public ShopTypeDao getShopTypeDao() {
		return shopTypeDao;
	}

	/**
	 * @param shopTypeDao the shopTypeDao to set
	 */
	public void setShopTypeDao(ShopTypeDao shopTypeDao) {
		this.shopTypeDao = shopTypeDao;
	}

	@Override
	public List<ShopType> getShopTypes() {
		final List<ShopType> shopTypes = shopTypeDao.getShopTypes();
		
		ShopType shopType = new ShopType();
		shopType.setId(0);
		shopType.setName("不限");
		shopType.setEnabled((short)0);
		shopType.setDescription("不限");
		shopType.setCreateDate(new Date());
		shopTypes.add(0, shopType);
		
		return shopTypes;
	}

	@Override
	public int getTotalCount() {
		return this.getShopTypes().size();
	}

	@Override
	public List<ShopType> getShopTypesByName(ExtPager pager, String name) {
		List<ShopType> list = null;
		if(StringUtils.isBlank(name)) {
			list = shopTypeDao.getShopTypes(pager);
		} else {
			list = shopTypeDao.getShopTypesByName(pager, name);
		}
		return list;
	}

	@Override
	public void deleteById(int id) {
		shopTypeDao.deleteById(id);
	}

	@Override
	public void saveShopType(ShopType shopType) {
		shopTypeDao.saveShopType(shopType);
	}

	@Override
	public void update(ShopType shopType) {
		ShopType oldShopType = this.findShopTypeById(shopType.getId());
		shopType.setCreateDate(oldShopType.getCreateDate());
		shopTypeDao.update(shopType);
	}

	@Override
	public Map<String, Object> getAllEnabledShopTypes() {
		List<ShopType> list = shopTypeDao.getEnabledShopTypes();
		Map<String, Object> map = new HashMap<String, Object>();
		if(list != null && !list.isEmpty()) {
			for(ShopType st : list) {
				map.put(String.valueOf(st.getId()), st.getName());
			}
		}
		return map;
	}

	@Override
	public ShopType getShopTypeById(int id) {
		return shopTypeDao.findShopTypeById(id);
	}
	
}
