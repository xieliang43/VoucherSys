package com.voucher.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.dao.DataAccessException;

import com.voucher.dao.ShopDao;
import com.voucher.entity.Shop;
import com.voucher.entity.sys.SysUser;
import com.voucher.pojo.ExtPager;
import com.voucher.pojo.ShopPager;

public class ShopDaoImpl extends BaseDaoImpl implements ShopDao {

	@Override
	public Shop findShopById(int shopId) {
		try {
			return this.getJpaTemplate().find(Shop.class, shopId);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void update(Shop shop) {
		try {
			this.getJpaTemplate().merge(shop);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void create(Shop shop) {
		try {
			this.getJpaTemplate().persist(shop);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Shop> getShops() {
		String hql = "from Shop sh";
		try {
			Query query = this.createQuery(hql);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

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
		hql = this.createQueryString(Shop.class, hql, pager);
		try {
			Query query = this.createQuery(hql)
					.setFirstResult(pager.getStart())
					.setMaxResults(pager.getLimit());
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
			Query query = this.createQuery(hql)
					.setFirstResult(pager.getStart())
					.setMaxResults(pager.getLimit());
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
	public void deleteById(int id) {
		try {
			this.getJpaTemplate().remove(this.findShopById(id));
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
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
}
