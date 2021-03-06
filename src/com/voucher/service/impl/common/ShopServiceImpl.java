package com.voucher.service.impl.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.voucher.constants.WebConstants;
import com.voucher.dao.common.PositionDao;
import com.voucher.dao.common.ShopDao;
import com.voucher.entity.common.Position;
import com.voucher.entity.common.Region;
import com.voucher.entity.common.Shop;
import com.voucher.entity.sys.SysUser;
import com.voucher.pojo.ExtPager;
import com.voucher.pojo.ShopPager;
import com.voucher.service.common.ShopService;
import com.voucher.service.common.VoucherService;
import com.voucher.service.impl.BaseServiceImpl;
import com.voucher.util.BaiduMapUtil;
import com.voucher.util.DistanceUtil;
import com.voucher.util.PropertiesLoader;
import com.voucher.vo.ExtShopVO;
import com.voucher.vo.ShopVO;

public class ShopServiceImpl extends BaseServiceImpl<Shop> implements ShopService {
	private static final Logger logger = Logger.getLogger(ShopServiceImpl.class);
	
	private ShopDao shopDao;
	private PositionDao positionDao;
	private VoucherService voucherService;

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
		Shop oldShop = this.findById(Shop.class, shop.getId());
		final Position position = oldShop.getPosition();
		if(position != null) {
			positionDao.delete(position);
		}

		shop.setCreateDate(oldShop.getCreateDate());
		shop.setMerchant(oldShop.getMerchant());

		shopDao.update(shop);
		String areaName = "";
		if(shop.getArea() != null) {
			areaName = shop.getArea().getName();
		}
		Map<String, Double> latLng = BaiduMapUtil.getInstance().getLatLng(
				shop.getCity().getName(), areaName + shop.getShopAddress());
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
		shopDao.save(shop);
		String areaName = "";
		if(shop.getArea() != null) {
			areaName = shop.getArea().getName();
		}
		Map<String, Double> latLng = BaiduMapUtil.getInstance().getLatLng(
				shop.getCity().getName(), areaName + shop.getShopAddress());
		
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

		List<Shop> shopList = shopDao.findAll(Shop.class);
		if (shopList != null && !shopList.isEmpty()) {
			for (Shop s : shopList) {
				int areaId = 0;
				Region area = s.getArea();
				if(area != null) {
					areaId = area.getId();
				}
				ShopVO svo = new ShopVO(s.getId(), s.getShopName(), s.getMerchant().getAccount(),
						s.getShopAddress(), s.getTelNo(), s.getImage(), s.getDescription(), s
								.getShopType().getId(), 0, s.getCity().getId(),
						areaId, s.getCreateDate());
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
				int areaId = 0;
				Region area = s.getArea();
				if(area != null) {
					areaId = area.getId();
				}
				ShopVO svo = new ShopVO(s.getId(), s.getShopName(), s.getMerchant().getAccount(),
						s.getShopAddress(), s.getTelNo(), s.getImage(), s.getDescription(), s
								.getShopType().getId(), 0, s.getCity().getId(),
								areaId, s.getCreateDate());
				shopVOList.add(svo);
			}
		}
		return shopVOList;
	}

	@Override
	public int getTotalCount() {
		return shopDao.getTotalCount(Shop.class);
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
					Region area = shop.getArea();
					String areaName = "";
					int areaId = 0;
					if(area != null) {
						areaName = area.getName();
						areaId = area.getId();
					}
					String cityArea = shop.getCity().getName() + areaName;
					ShopVO svo = new ShopVO(shop.getId(), shop.getShopName(), shop.getMerchant().getAccount(),
							cityArea + shop.getShopAddress(), shop.getTelNo(), baseImgPath
									+ shop.getImage(), shop.getDescription(),
							shop.getShopType().getId(), (int) distanceOfShop,
							shop.getCity().getId(), areaId,
							shop.getCreateDate());
					list.add(svo);
				}
			}
		}
		int total = list.size();
		Collections.sort(list, new Comparator<ShopVO>(){
			@Override
			public int compare(ShopVO o1, ShopVO o2) {
				return o1.getDistance() - o2.getDistance();
			}
		});
		ExtShopVO extShopVO = new ExtShopVO(total, list);
		return extShopVO;
	}

	@Override
	public void deleteShopById(int id) {
		Shop shop = shopDao.findById(Shop.class, id);
		if(shop != null) {
			voucherService.deleteByShop(shop);
			Position pos = shop.getPosition();
			if(pos != null) {
				positionDao.delete(pos);
			}
			shopDao.deleteById(Shop.class, id);
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
				int areaId = 0;
				if(s.getArea() != null) {
					areaId = s.getArea().getId();
				}
				ShopVO svo = new ShopVO(s.getId(), s.getShopName(), s.getMerchant().getAccount(),
						s.getShopAddress(), s.getTelNo(), s.getImage(), s.getDescription(), s
								.getShopType().getId(), 0, s.getCity().getId(),
						areaId, s.getCreateDate());
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

	@Override
	public List<Shop> getShopsByRegion(int rId) {
		List<Shop> shops = shopDao.getShopsByCityId(rId);
		if(shops == null || shops.isEmpty()) {
			shops = shopDao.getShopsByAreaId(rId);
		}
		return shops;
	}

	@Override
	public Shop findShopById(int id) {
		return this.findById(Shop.class, id);
	}
}
