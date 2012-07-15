package com.voucher.service.impl;

import java.util.List;

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
		return shopTypeDao.getShopTypes();
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
		shopTypeDao.update(shopType);
	}
	
}
