package com.voucher.dao.impl.common;

import java.util.List;

import javax.persistence.Query;

import com.voucher.dao.common.ShopDao;
import com.voucher.dao.impl.BaseDaoImpl;
import com.voucher.entity.common.Shop;
import com.voucher.entity.sys.SysUser;
import com.voucher.pojo.ExtPager;
import com.voucher.pojo.ShopPager;

public class ShopDaoImpl extends BaseDaoImpl<Shop> implements ShopDao {

	@Override
	public List<Shop> getShopsByCityId(int cityId) {
		String hql = "from Shop s where s.merchant.cityId = :cityId";
		try {
			Query query = this.createQuery(hql);
			query.setParameter("cityId", cityId);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Shop> getAllShops(ExtPager pager, SysUser merchant) {
		String hql = "from Shop s where s.merchant.id = :id";
		try {
			Query query = this.createPagerQuery(Shop.class, hql, pager);
			query.setParameter("id", merchant.getId());
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Shop> getAllShopsByShopName(ExtPager pager, SysUser merchant,
			String shopName) {
		String hql = "from Shop s where s.merchant.id = :id and s.shopName like :shopName";
		hql = this.createQueryString(Shop.class, hql, pager);
		try {
			Query query = this.createPagerQuery(Shop.class, hql, pager);
			query.setParameter("id", merchant.getId());
			query.setParameter("shopName", "%" + shopName + "%");
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Shop> getCurrentMerchantShops(SysUser merchant) {
		String hql = "from Shop s where s.merchant.id = :id";
		try {
			Query query = this.createQuery(hql);
			query.setParameter("id", merchant.getId());
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Shop> getShopsByShopPager(ShopPager shopPager) {
		String hql = "from Shop s where";
		if(shopPager.getAreaId() != null) {
			hql = hql + " s.area.id = :areaId";
		} else {
			if(shopPager.getCityId() != null) {
				hql = hql + " s.city.id = :cityId";
			}
		}
		if (shopPager.getKeyword() != null) {
			hql = hql + " and s.shopName like :shopName";
		}
		if (shopPager.getStart() == null) {
			shopPager.setStart(0);
		}
		if (shopPager.getLimit() == null) {
			shopPager.setLimit(15);
		}
		try {
			Query query = this.createQuery(hql)
					.setFirstResult(shopPager.getStart())
					.setMaxResults(shopPager.getLimit());
			if(shopPager.getAreaId() != null) {
				query.setParameter("areaId", shopPager.getAreaId());
			} else {
				if(shopPager.getCityId() != null) {
					query.setParameter("cityId", shopPager.getCityId());
				}
			}
			if (shopPager.getKeyword() != null) {
				query.setParameter("shopName", "%" + shopPager.getKeyword() + "%");
			}
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int getTotalEnabledShops(ShopPager shopPager) {
		List<Shop> list = null;
		String hql = "from Shop s where";
		if(shopPager.getAreaId() != null) {
			hql = hql + " s.area.id = :areaId";
		} else {
			if(shopPager.getCityId() != null) {
				hql = hql + " s.city.id = :cityId";
			}
		}
		if (shopPager.getKeyword() != null) {
			hql = hql + " and s.shopName like :shopName";
		}
		if(shopPager.getShopTypeId() != null) {
			hql = hql + " and s.shopType.id = :shopTypeId";
		}
		if (shopPager.getStart() == null) {
			shopPager.setStart(0);
		}
		if (shopPager.getLimit() == null) {
			shopPager.setLimit(15);
		}
		try {
			Query query = this.createQuery(hql);
			if(shopPager.getAreaId() != null) {
				query.setParameter("areaId", shopPager.getAreaId());
			} else {
				if(shopPager.getCityId() != null) {
					query.setParameter("cityId", shopPager.getCityId());
				}
			}
			if (shopPager.getKeyword() != null) {
				query.setParameter("shopName", "%" + shopPager.getKeyword() + "%");
			}
			if(shopPager.getShopTypeId() != null) {
				query.setParameter("shopTypeId", shopPager.getShopTypeId());
			}
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(list != null) {
			return list.size();
		}
		return 0;
	}

	@Override
	public List<Shop> getCurrentMerchantShopsByShopName(SysUser merchant,
			String shopName) {
		String hql = "from Shop s where s.merchant.id = :id and s.shopName like :shopName";
		try {
			Query query = this.createQuery(hql);
			query.setParameter("id", merchant.getId());
			query.setParameter("shopName", "%" + shopName + "%");
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Shop> getShopsByPager(ExtPager pager) {
		String hql = "from Shop s";
		hql = this.createQueryString(Shop.class, hql, pager);
		try {
			Query query = this.createQuery(hql);
			if(pager != null) {
				query.setFirstResult(pager.getStart()).setMaxResults(pager.getLimit());
			}
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Shop> getShopsByShopName(ExtPager pager, String shopName) {
		String hql = "from Shop s where s.shopName like :shopName";
		hql = this.createQueryString(Shop.class, hql, pager);
		
		try {
			Query query = this.createQuery(hql).setFirstResult(pager.getStart()).setMaxResults(pager.getLimit());
			query.setParameter("shopName", "%" + shopName + "%");
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Shop> getTotalCountByShopName(String shopName) {
		String hql = "from Shop s where s.shopName like :shopName";
		
		try {
			Query query = this.createQuery(hql);
			query.setParameter("shopName", "%" + shopName + "%");
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Shop> getShopsByAreaId(int rId) {
		String hql = "from Shop s where s.area.id = :id";
		try {
			Query query = this.createQuery(hql);
			query.setParameter("id", rId);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
