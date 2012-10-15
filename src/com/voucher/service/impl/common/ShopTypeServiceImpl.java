package com.voucher.service.impl.common;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.voucher.dao.common.ShopTypeDao;
import com.voucher.entity.common.ShopType;
import com.voucher.pojo.ExtPager;
import com.voucher.service.common.ShopTypeService;
import com.voucher.service.impl.BaseServiceImpl;

public class ShopTypeServiceImpl extends BaseServiceImpl<ShopType> implements ShopTypeService {
	private ShopTypeDao shopTypeDao;

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
		final List<ShopType> shopTypes = shopTypeDao.findAll(ShopType.class);
		
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
	public void update(ShopType shopType) {
		ShopType oldShopType = this.findById(ShopType.class, shopType.getId());
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
	public void deleteShopTypeById(int id) {
		this.deleteById(ShopType.class, id);
	}

	@Override
	public ShopType findShopTypeById(int id) {
		return this.findById(ShopType.class, id);
	}
	
}
