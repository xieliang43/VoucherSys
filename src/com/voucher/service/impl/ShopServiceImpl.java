package com.voucher.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.voucher.constants.WebConstants;
import com.voucher.dao.PositionDao;
import com.voucher.dao.ShopDao;
import com.voucher.entity.Position;
import com.voucher.entity.Shop;
import com.voucher.entity.sys.SysUser;
import com.voucher.pojo.ExtPager;
import com.voucher.pojo.ShopVO;
import com.voucher.service.ShopService;
import com.voucher.util.DistanceUtil;
import com.voucher.util.GoogleMapUtil;

public class ShopServiceImpl implements ShopService {
	private ShopDao shopDao;
	private PositionDao positionDao;

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
		Shop oldShop = this.findShopById(shop.getId());
		positionDao.delete(oldShop.getPosition());

		shop.setCreateDate(oldShop.getCreateDate());
		shop.setMerchant(oldShop.getMerchant());
		
		shopDao.update(shop);
		
		Map<String, Double> latLng = GoogleMapUtil.getInstance().getLatLng(shop.getShopAddress());
		if(latLng != null && !latLng.isEmpty()) {
			double lat = latLng.get(WebConstants.LAT);
			double lng = latLng.get(WebConstants.LNG);
			Position pos = new Position();
			pos.setShop(shop);
			pos.setCreateDate(new Date());
			pos.setLattitude(lat);
			pos.setLongitude(lng);
			positionDao.save(pos);
		}
	}

	@Override
	public void save(Shop shop) {
		shopDao.create(shop);
		Map<String, Double> latLng = GoogleMapUtil.getInstance().getLatLng(shop.getShopAddress());
		if(latLng != null && !latLng.isEmpty()) {
			double lat = latLng.get(WebConstants.LAT);
			double lng = latLng.get(WebConstants.LNG);
			Position pos = new Position();
			pos.setShop(shop);
			pos.setCreateDate(new Date());
			pos.setLattitude(lat);
			pos.setLongitude(lng);
			positionDao.save(pos);
		}
	}

	@Override
	public List<ShopVO> getShops() {
		List<ShopVO> shopVOList = new ArrayList<ShopVO>();
		
		List<Shop> shopList = shopDao.getShops();
		if(shopList != null && !shopList.isEmpty()) {
			for(Shop s: shopList) {
				ShopVO svo = new ShopVO(s.getId(), s.getShopName(), s.getShopAddress(), s.getImage(), s.getDescription(), s.getShopType().getId(), 0, s.getCreateDate());
				shopVOList.add(svo);
			}
		}
		
		return shopVOList;
	}

	@Override
	public List<ShopVO> getCurrentMerchantShopsByShopName(ExtPager pager, SysUser merchant, String shopName) {
		List<ShopVO> shopVOList = new ArrayList<ShopVO>();
		List<Shop> shopList = null;
		if(StringUtils.isBlank(shopName)) {
			shopList = shopDao.getAllShops(pager, merchant);
		} else {
			shopList = shopDao.getAllShopsByShopName(pager, merchant, shopName);
		}
		if(shopList != null && !shopList.isEmpty()) {
			for(Shop s: shopList) {
				ShopVO svo = new ShopVO(s.getId(), s.getShopName(), s.getShopAddress(), s.getImage(), s.getDescription(), s.getShopType().getId(), 0, s.getCreateDate());
				shopVOList.add(svo);
			}
		}
		return shopVOList;
	}

	@Override
	public int getTotalCount() {
		return shopDao.getShops().size();
	}

	@Override
	public int getCurrentMerchantTotalCount(SysUser merchant) {
		return shopDao.getCurrentMerchantShops(merchant).size();
	}

	@Override
	public Map<String, Object> getAllEnabledShopsByCurrentMerchant(
			SysUser merchant) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Shop> shops = shopDao.getCurrentMerchantShops(merchant);
		if(shops != null && !shops.isEmpty()) {
			for(Shop shop : shops) {
				map.put(String.valueOf(shop.getId()), shop.getShopName());
			}
		}
		return map;
	}

	/**
	 * @return the positionDao
	 */
	public PositionDao getPositionDao() {
		return positionDao;
	}

	/**
	 * @param positionDao the positionDao to set
	 */
	public void setPositionDao(PositionDao positionDao) {
		this.positionDao = positionDao;
	}

	@Override
	public List<ShopVO> getNearbyShops(int cityId, int distance,
			double lattitude, double longitude) {
		List<ShopVO> list = new ArrayList<ShopVO>();
		List<Shop> cityShops = shopDao.getShopsByCityId(cityId);
		if(cityShops != null && !cityShops.isEmpty()) {
			for(Shop shop : cityShops) {
				double distanceOfShop = DistanceUtil.getDistance(lattitude, longitude, shop.getPosition().getLattitude(), shop.getPosition().getLongitude());
				if((int)distanceOfShop < distance) {
					ShopVO svo = new ShopVO(shop.getId(), shop.getShopName(), shop.getShopAddress(), shop.getImage(), shop.getDescription(), shop.getShopType().getId(), (int)distanceOfShop, shop.getCreateDate());
					list.add(svo);
				}
			}
		}
		return list;
	}
}
