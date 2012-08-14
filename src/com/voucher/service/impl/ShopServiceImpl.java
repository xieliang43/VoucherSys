package com.voucher.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.voucher.comparator.ShopVOComparator;
import com.voucher.constants.WebConstants;
import com.voucher.dao.PositionDao;
import com.voucher.dao.ShopDao;
import com.voucher.entity.Position;
import com.voucher.entity.Shop;
import com.voucher.entity.sys.SysUser;
import com.voucher.pojo.ExtPager;
import com.voucher.pojo.ExtShopVO;
import com.voucher.pojo.ShopPager;
import com.voucher.pojo.ShopVO;
import com.voucher.service.ShopService;
import com.voucher.service.VoucherService;
import com.voucher.util.BaiduMapUtil;
import com.voucher.util.DistanceUtil;
import com.voucher.util.PropertiesLoader;

public class ShopServiceImpl implements ShopService {
	private static final Logger logger = Logger.getLogger(ShopServiceImpl.class);
	
	private ShopDao shopDao;
	private PositionDao positionDao;
	private VoucherService voucherService;

	@Override
	public Shop findShopById(int shopId) {
		return shopDao.findShopById(shopId);
	}

	public Map<Integer, Shop> getShopsByPosition(int cityId, double longitude,
			double lattitude) {
		Map<Integer, Shop> map = new HashMap<Integer, Shop>();
		List<Shop> shops = shopDao.getShopsByCityId(cityId);
		for (Shop shop : shops) {
			Position p = shop.getPosition();
			double distance = Math
					.sqrt(Math.pow(p.getLatitude() - longitude, 2)
							+ Math.pow(p.getLatitude() - lattitude, 2));
			map.put(Integer.valueOf((int) distance), shop);
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
	 * @param shopDao
	 *            the shopDao to set
	 */
	public void setShopDao(ShopDao shopDao) {
		this.shopDao = shopDao;
	}

	@Override
	public void update(Shop shop) {
		Shop oldShop = this.findShopById(shop.getId());
		final Position position = oldShop.getPosition();
		if(position != null) {
			positionDao.delete(position);
		}

		shop.setCreateDate(oldShop.getCreateDate());
		shop.setMerchant(oldShop.getMerchant());

		shopDao.update(shop);

		Map<String, Double> latLng = BaiduMapUtil.getInstance().getLatLng(
				shop.getCity().getName(), shop.getArea().getName() + shop.getShopAddress());
		if (latLng != null) {
			Double lat = latLng.get(WebConstants.LAT);
			Double lng = latLng.get(WebConstants.LNG);
			Position pos = shop.getPosition();
			if(pos == null) {
				pos = new Position();
				pos.setCreateDate(new Date());
				pos.setShop(shop);
			}
			if(lat != null && lng != null) {
				pos.setLatitude(lat);
				pos.setLongitude(lng);
			} else {
				pos.setLatitude(0.0);
				pos.setLongitude(0.0);
			}
			positionDao.update(pos);
		}
	}

	@Override
	public void save(Shop shop) {
		shopDao.create(shop);
		Map<String, Double> latLng = BaiduMapUtil.getInstance().getLatLng(
				shop.getCity().getName(), shop.getArea().getName() + shop.getShopAddress());
		
		Position pos = new Position();
		pos.setShop(shop);
		pos.setCreateDate(new Date());
		if (latLng != null && !latLng.isEmpty()) {
			double lat = latLng.get(WebConstants.LAT);
			double lng = latLng.get(WebConstants.LNG);
			logger.info("Get Position, latitude: " + lat + ", longitude: " + lng);
			pos.setLatitude(lat);
			pos.setLongitude(lng);
		} else {
			pos.setLatitude(.0);
			pos.setLongitude(.0);
		}
		positionDao.save(pos);
	}

	@Override
	public List<ShopVO> getShops() {
		List<ShopVO> shopVOList = new ArrayList<ShopVO>();

		List<Shop> shopList = shopDao.getShops();
		if (shopList != null && !shopList.isEmpty()) {
			for (Shop s : shopList) {
				ShopVO svo = new ShopVO(s.getId(), s.getShopName(), s.getMerchant().getAccount(),
						s.getShopAddress(), s.getTelNo(), s.getImage(), s.getDescription(), s
								.getShopType().getId(), 0, s.getCity().getId(),
						s.getArea().getId(), s.getCreateDate());
				shopVOList.add(svo);
			}
		}

		return shopVOList;
	}

	@Override
	public List<ShopVO> getCurrentMerchantShopsByShopName(ExtPager pager,
			SysUser merchant, String shopName) {
		List<ShopVO> shopVOList = new ArrayList<ShopVO>();
		List<Shop> shopList = null;
		if (StringUtils.isBlank(shopName)) {
			shopList = shopDao.getAllShops(pager, merchant);
		} else {
			shopList = shopDao.getAllShopsByShopName(pager, merchant, shopName);
		}
		if (shopList != null && !shopList.isEmpty()) {
			for (Shop s : shopList) {
				ShopVO svo = new ShopVO(s.getId(), s.getShopName(), s.getMerchant().getAccount(),
						s.getShopAddress(), s.getTelNo(), s.getImage(), s.getDescription(), s
								.getShopType().getId(), 0, s.getCity().getId(),
						s.getArea().getId(), s.getCreateDate());
				shopVOList.add(svo);
			}
		}
		return shopVOList;
	}

	@Override
	public int getTotalCount() {
		return shopDao.getShops() != null ? shopDao.getShops().size() : 0;
	}

	@Override
	public int getCurrentMerchantTotalCount(SysUser merchant) {
		List<Shop> shops = shopDao.getCurrentMerchantShops(merchant);
		if(shops != null) {
			return shops.size();
		}
		return 0;
	}

	@Override
	public Map<String, Object> getAllEnabledShopsByCurrentMerchant(
			SysUser merchant) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Shop> shops = shopDao.getCurrentMerchantShops(merchant);
		if (shops != null && !shops.isEmpty()) {
			for (Shop shop : shops) {
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
	 * @param positionDao
	 *            the positionDao to set
	 */
	public void setPositionDao(PositionDao positionDao) {
		this.positionDao = positionDao;
	}

	@Override
	public ExtShopVO getNearbyShops(ShopPager shopPager) {
		List<ShopVO> list = new ArrayList<ShopVO>();
		List<Shop> cityShops = shopDao.getShopsByShopPager(shopPager);
		if (shopPager.getShopTypeId() != null) {
			Iterator<Shop> itr = cityShops.iterator();
			while (itr.hasNext()) {
				Shop shop = itr.next();
				if (shop.getShopType().getId() != shopPager.getShopTypeId()) {
					itr.remove();
				}
			}
		}
		if (shopPager.getDistance() == null) {
			shopPager.setDistance(Integer.MAX_VALUE);
		}
		if (cityShops != null && !cityShops.isEmpty()) {
			for (Shop shop : cityShops) {
				double distanceOfShop;
				if (shopPager.getLatitude() == null
						|| shopPager.getLongitude() == null) {
					distanceOfShop = 0;
				} else {
					Position pos = shop.getPosition();
					double latitude = 0.0;
					double longitude = 0.0;
					if(pos != null) {
						latitude = pos.getLatitude();
						longitude = pos.getLongitude();
					}
					distanceOfShop = DistanceUtil.getDistance(shopPager
							.getLatitude(), shopPager.getLongitude(), latitude, longitude);
				}
				if ((int) distanceOfShop < shopPager.getDistance()) {
					String baseImgPath = PropertiesLoader.getInstance()
							.getShopImageBaseUrl() + shop.getMerchant().getAccount() + WebConstants.FORWARD_SLASH;
					String cityArea = shop.getCity().getName() + shop.getArea().getName();
					ShopVO svo = new ShopVO(shop.getId(), shop.getShopName(), shop.getMerchant().getAccount(),
							cityArea + shop.getShopAddress(), shop.getTelNo(), baseImgPath
									+ shop.getImage(), shop.getDescription(),
							shop.getShopType().getId(), (int) distanceOfShop,
							shop.getCity().getId(), shop.getArea().getId(),
							shop.getCreateDate());
					list.add(svo);
				}
			}
		}
		int total = list.size(); //shopDao.getTotalEnabledShops(shopPager);
		Collections.sort(list, new ShopVOComparator());
		ExtShopVO extShopVO = new ExtShopVO(total, list);
		return extShopVO;
	}

	@Override
	public void deleteById(int id) {
		Shop shop = shopDao.findShopById(id);
		if(shop != null) {
			voucherService.deleteByShop(shop);
			Position pos = shop.getPosition();
			if(pos != null) {
				positionDao.delete(pos);
			}
			shopDao.deleteById(id);
		}
	}

	@Override
	public int getCurrentMerchantTotalCountByShopName(SysUser merchant,
			String shopName) {
		List<Shop> shops = shopDao.getCurrentMerchantShopsByShopName(merchant, shopName);
		if(shops != null) {
			return shops.size();
		}
		return 0;
	}

	@Override
	public List<ShopVO> getShopsByShopName(ExtPager pager, String shopName) {
		List<ShopVO> shopVOList = new ArrayList<ShopVO>();
		List<Shop> shops = null;
		if(StringUtils.isBlank(shopName)) {
			shops = shopDao.getShopsByPager(pager);
		} else {
			shops = shopDao.getShopsByShopName(pager, shopName);
		}
		if(shops != null && !shops.isEmpty()) {
			for (Shop s : shops) {
				ShopVO svo = new ShopVO(s.getId(), s.getShopName(), s.getMerchant().getAccount(),
						s.getShopAddress(), s.getTelNo(), s.getImage(), s.getDescription(), s
								.getShopType().getId(), 0, s.getCity().getId(),
						s.getArea().getId(), s.getCreateDate());
				shopVOList.add(svo);
			}
		}
		return shopVOList;
	}

	@Override
	public int getTotalCountByShopName(String shopName) {
		List<Shop> shops = shopDao.getTotalCountByShopName(shopName);
		if(shops != null) {
			return shops.size();
		}
		return 0;
	}

	/**
	 * @return the voucherService
	 */
	public VoucherService getVoucherService() {
		return voucherService;
	}

	/**
	 * @param voucherService the voucherService to set
	 */
	public void setVoucherService(VoucherService voucherService) {
		this.voucherService = voucherService;
	}
}
