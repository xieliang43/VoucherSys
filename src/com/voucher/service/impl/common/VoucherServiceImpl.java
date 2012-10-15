package com.voucher.service.impl.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.voucher.constants.WebConstants;
import com.voucher.dao.common.UserVoucherDao;
import com.voucher.dao.common.VoucherDao;
import com.voucher.dao.common.VoucherInstanceDao;
import com.voucher.entity.common.Shop;
import com.voucher.entity.common.User;
import com.voucher.entity.common.UserVoucher;
import com.voucher.entity.common.Voucher;
import com.voucher.entity.common.VoucherInstance;
import com.voucher.entity.sys.SysUser;
import com.voucher.exception.DataExistException;
import com.voucher.exception.DataNotFoundException;
import com.voucher.exception.PersistenceConcurrentException;
import com.voucher.exception.ServiceConcurrentException;
import com.voucher.pojo.ExtPager;
import com.voucher.service.common.UserService;
import com.voucher.service.common.VoucherService;
import com.voucher.service.impl.BaseServiceImpl;
import com.voucher.util.DateUtil;
import com.voucher.util.NumberUtil;
import com.voucher.util.PropertiesLoader;
import com.voucher.vo.ShopVoucherInstanceVO;
import com.voucher.vo.ShopVoucherVO;
import com.voucher.vo.VchInstVO;
import com.voucher.vo.VoucherInstanceVO;
import com.voucher.vo.VoucherVO;

public class VoucherServiceImpl extends BaseServiceImpl<Voucher> implements VoucherService {

	private VoucherDao voucherDao;
	private VoucherInstanceDao voucherInstanceDao;
	private UserService userService;
	private UserVoucherDao userVoucherDao;

	@Override
	public List<VoucherVO> getCurrentMerchantVouchersByShopName(ExtPager pager,
			SysUser merchant, String shopName) {
		List<VoucherVO> list = new ArrayList<VoucherVO>();
		List<Voucher> vouchers = null;
		if (StringUtils.isBlank(shopName)) {
			vouchers = voucherDao.getCurrentMerchantVouchers(pager, merchant);
		} else {
			vouchers = voucherDao.getCurrentMerchantVouchersByShopName(pager,
					merchant, shopName);
		}
		if (vouchers != null && !vouchers.isEmpty()) {
			for (Voucher voucher : vouchers) {
				int restQty = voucherInstanceDao
						.getActiveCountByVoucher(voucher.getId());
				VoucherVO vchVO = new VoucherVO(voucher.getId(),
						voucher.getName(), voucher.getPrice(),
						voucher.getQuantity(), restQty, DateUtil.getInstance()
								.getStringDateShort(voucher.getStartDate()),
						DateUtil.getInstance().getStringDateShort(
								voucher.getEndDate()), voucher.getUseRule(),
						voucher.getVchKey(), voucher.getEnabled(),
						voucher.getImage(), voucher.getDescription(),
						voucher.getCreateDate(), voucher.getShop().getId());
				list.add(vchVO);
			}
		}
		return list;
	}

	/**
	 * @return the voucherDao
	 */
	public VoucherDao getVoucherDao() {
		return voucherDao;
	}

	/**
	 * @param voucherDao
	 *            the voucherDao to set
	 */
	public void setVoucherDao(VoucherDao voucherDao) {
		this.voucherDao = voucherDao;
	}

	/**
	 * @return the voucherInstanceDao
	 */
	public VoucherInstanceDao getVoucherInstanceDao() {
		return voucherInstanceDao;
	}

	/**
	 * @param voucherInstanceDao
	 *            the voucherInstanceDao to set
	 */
	public void setVoucherInstanceDao(VoucherInstanceDao voucherInstanceDao) {
		this.voucherInstanceDao = voucherInstanceDao;
	}

	@Override
	public int getCurrentMerchantTotalCount(SysUser merchant) {
		List<Voucher> vouchers = voucherDao
				.getCurrentMerchantVouchers(null, merchant);
		if (vouchers != null) {
			return vouchers.size();
		}
		return 0;
	}

	@Override
	public void save(Voucher voucher) {
		int quantity = voucher.getQuantity();

		List<VoucherInstance> instances = new ArrayList<VoucherInstance>();
		for (int i = 0; i < quantity; i++) {
			VoucherInstance vi = new VoucherInstance();
			vi.setIsBought((short) 0);
			vi.setVoucher(voucher);
			vi.setVchKey(voucher.getVchKey()
					+ NumberUtil.getInstance().getDateline(
							voucher.getEndDate() + ""));

			instances.add(vi);
			voucherInstanceDao.save(vi);
		}
		voucherDao.save(voucher);
	}

	@Override
	public void deleteVoucherById(int id) {
		deleteVoucherInstancesByVoucher(id);
		voucherDao.deleteById(Voucher.class, id);
	}

	@Override
	public List<Voucher> getEnabledVouchersLessThanCurrentDate() {
		List<Voucher> list = voucherDao.findAllEnabledVouchers();
		if (list != null && !list.isEmpty()) {
			Date currentDate = new Date();
			Iterator<Voucher> itr = list.iterator();
			while (itr.hasNext()) {
				Voucher vch = itr.next();
				if (vch.getEndDate().compareTo(currentDate) > 0) {
					itr.remove();
				}
			}
		}
		return list;
	}

	@Override
	public VchInstVO getEnabledVouchersByShop(int shopId) {
		List<Voucher> vouchers = voucherDao.getEnabledVouchersByShop(shopId);
		List<VoucherInstance> viList = new ArrayList<VoucherInstance>();
		List<VoucherInstanceVO> viVOList = new ArrayList<VoucherInstanceVO>();
		int totalVis = 0;
		int totalActive = 0;
		for (Voucher vch : vouchers) {
			totalVis += voucherInstanceDao.getVoucherInstanceCountByVoucher(vch
					.getId());
			totalActive += voucherInstanceDao.getActiveCountByVoucher(vch
					.getId());
			viList.addAll(voucherInstanceDao.getVoucherInstancesByVoucher(vch
					.getId()));
		}

		Collections.sort(viList, new Comparator<VoucherInstance>() {

			@Override
			public int compare(VoucherInstance o1, VoucherInstance o2) {
				return o1.getIsBought() - o2.getIsBought();
			}
		});

		if (viList != null && !viList.isEmpty()) {
			for (VoucherInstance vi : viList) {
				String baseVoucherImagePath = PropertiesLoader.getInstance()
						.getVoucherImageBaseUrl();
				baseVoucherImagePath = baseVoucherImagePath
						+ vi.getVoucher().getShop().getMerchant().getAccount()
						+ WebConstants.FORWARD_SLASH;
				VoucherInstanceVO viVO = new VoucherInstanceVO(vi.getId(),
						baseVoucherImagePath + vi.getVoucher().getImage(),
						vi.getVchKey() + String.format("%04d", vi.getId()), vi
								.getVoucher().getUseRule(), DateUtil
								.getInstance().getStringDateShort(
										vi.getVoucher().getEndDate()), vi
								.getVoucher().getPrice(), vi.getIsBought());
				viVOList.add(viVO);
			}
		}

		VchInstVO vchInstVO = new VchInstVO(totalVis, totalActive, viVOList);
		return vchInstVO;
	}

	@Override
	public void purchaseVoucher(int userId, int viId)
			throws ServiceConcurrentException, DataExistException,
			DataNotFoundException {
		User user = userService.findById(User.class, userId);
		VoucherInstance vchInst = voucherInstanceDao.findById(VoucherInstance.class, viId);
		List<UserVoucher> existUserVchInsts = userVoucherDao
				.findUserInstancese(userId);
		if (vchInst == null) {
			throw new DataNotFoundException();
		} else {
			if (vchInst.getIsBought() == 1) {
				throw new ServiceConcurrentException();
			}
		}
		if (existUserVchInsts != null && !existUserVchInsts.isEmpty()) {
			for (UserVoucher userVoucher : existUserVchInsts) {
				if (userVoucher.getVoucherInstance().getVoucher().getId() == vchInst
						.getVoucher().getId()) {
					throw new DataExistException();
				}
			}
		}

		try {
			vchInst.setIsBought((short) 1);
			voucherInstanceDao.update(vchInst);
			UserVoucher newUserVch = new UserVoucher(user, vchInst, new Date(),
					(short) 0, (short) 1);
			userVoucherDao.save(newUserVch);
		} catch (PersistenceConcurrentException e) {
			throw new ServiceConcurrentException();
		}
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * @param userService
	 *            the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * @return the userVoucherDao
	 */
	public UserVoucherDao getUserVoucherDao() {
		return userVoucherDao;
	}

	/**
	 * @param userVoucherDao
	 *            the userVoucherDao to set
	 */
	public void setUserVoucherDao(UserVoucherDao userVoucherDao) {
		this.userVoucherDao = userVoucherDao;
	}

	@Override
	public Voucher findVoucherById(int id) {
		return voucherDao.findById(Voucher.class, id);
	}

	@Override
	public int getCurrentMerchantTotalCountByShopName(SysUser merchant,
			String shopName) {
		List<Voucher> vouchers = voucherDao
				.getCurrentMerchantVouchersByShopName(merchant, shopName);
		if (vouchers != null) {
			return vouchers.size();
		}
		return 0;
	}

	@Override
	public List<ShopVoucherInstanceVO> getEnabledShopVouchersByShop(int shopId) {
		List<ShopVoucherInstanceVO> instances = new ArrayList<ShopVoucherInstanceVO>();
		List<Voucher> vouchers = voucherDao.getEnabledVouchersByShop(shopId);
		if (vouchers != null && !vouchers.isEmpty()) {
			for (Voucher vch : vouchers) {
				int total = voucherInstanceDao
						.getVoucherInstanceCountByVoucher(vch.getId());
				int rest = voucherInstanceDao.getActiveCountByVoucher(vch
						.getId());
				String baseVoucherImagePath = PropertiesLoader.getInstance()
						.getVoucherImageBaseUrl() + vch.getShop().getMerchant().getAccount() + WebConstants.FORWARD_SLASH;
				ShopVoucherVO shopVoucherVO = new ShopVoucherVO(vch.getId(),
						baseVoucherImagePath + vch.getImage(), vch.getUseRule(), DateUtil
								.getInstance().getStringDateShort(
										vch.getEndDate()), vch.getPrice(),
						vch.getDescription());
				ShopVoucherInstanceVO instance = new ShopVoucherInstanceVO(total, rest, shopVoucherVO);
				instances.add(instance);
			}
		}

		return instances;
	}

	@Override
	public void deleteByShop(Shop shop) {
		List<Voucher> vouchers = voucherDao.getVouchersByShopId(shop.getId());
		if(vouchers != null && !vouchers.isEmpty()) {
			for(Voucher v : vouchers) {
				deleteVoucherInstancesByVoucher(v.getId());
				voucherDao.delete(v);
			}
		}
	}

	@Override
	public void deleteVoucherInstancesByVoucher(int vchId) {
		List<VoucherInstance> vis = voucherInstanceDao.getVoucherInstancesByVoucher(vchId);
		if(vis != null && !vis.isEmpty()) {
			for(VoucherInstance vi : vis) {
				UserVoucher uv = userVoucherDao.findUserVoucherByVoucherInstanceId(vi.getId());
				if(uv != null) {
					userVoucherDao.delete(uv);
				}
				voucherInstanceDao.delete(vi);
			}
		}
	}

}
