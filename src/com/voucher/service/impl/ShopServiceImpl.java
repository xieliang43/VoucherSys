package com.voucher.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.voucher.dao.ShopDao;
import com.voucher.entity.Position;
import com.voucher.entity.Shop;
import com.voucher.service.ShopService;

public class ShopServiceImpl implements ShopService {
	private ShopDao shopDao;

	@Override
	public Shop findShopById(int shopId) {
		return shopDao.findShopById(shopId);
	}
	
	public Map<Integer, Shop> getShopsByPosition(int cityId, double longitude, double lattitude) {
		Map<Integer, Shop> map = new HashMap<Integer, Shop>();
		List<Shop> shops = shopDao.getShopsByCityId(cityId);
		for(Shop shop : shops) {
			Position p = shop.getPosition();
			double distance = Math.sqrt(Math.pow(p.getLattitude() - longitude, 2) +  Math.pow(p.getLattitude() - lattitude, 2));
			map.put(Integer.valueOf((int)distance), shop);
		}
		return map;
	}

	/**
	 * @return the shopDao
	 */
	public ShopDao getShopDao() {
		return shopDao;
	}

	/**
	 * @param shopDao the shopDao to set
	 */
	public void setShopDao(ShopDao shopDao) {
		this.shopDao = shopDao;
	}

	@Override
	public void update(Shop shop) {
		shopDao.update(shop);
	}

	@Override
	public void save(Shop shop) {
		shopDao.create(shop);
	}

	@Override
	public List<Shop> getShops() {
		return shopDao.getShops();
	}
}
