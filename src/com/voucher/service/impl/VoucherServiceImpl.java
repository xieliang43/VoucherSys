package com.voucher.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.voucher.dao.VoucherDao;
import com.voucher.dao.VoucherInstanceDao;
import com.voucher.entity.Voucher;
import com.voucher.entity.VoucherInstance;
import com.voucher.entity.sys.SysUser;
import com.voucher.pojo.ExtPager;
import com.voucher.pojo.VoucherVO;
import com.voucher.service.VoucherService;

public class VoucherServiceImpl implements VoucherService {

	private VoucherDao voucherDao;
	private VoucherInstanceDao voucherInstanceDao;

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
				int restQty = voucherInstanceDao.getActiveCountByVoucher(voucher.getId());
				VoucherVO vchVO = new VoucherVO(voucher.getId(),
						voucher.getName(), voucher.getPrice(),
						voucher.getQuantity(), restQty,
						voucher.getStartDate(), voucher.getEndDate(),
						voucher.getDeadTime(), voucher.getEnabled(),
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
	 * @param voucherInstanceDao the voucherInstanceDao to set
	 */
	public void setVoucherInstanceDao(VoucherInstanceDao voucherInstanceDao) {
		this.voucherInstanceDao = voucherInstanceDao;
	}

	@Override
	public int getCurrentMerchantTotalCount(SysUser merchant) {
		List<Voucher> vouchers = voucherDao.getCurrentMerchantVouchers(merchant);
		if(vouchers != null) {
			return vouchers.size();
		}
		return 0;
	}

	@Override
	public void saveVoucher(Voucher voucher) {
		int quantity = voucher.getQuantity();
		
		List<VoucherInstance> instances = new ArrayList<VoucherInstance>();
		for(int i=0; i<quantity; i++){
			VoucherInstance vi = new VoucherInstance();
			vi.setIsBought((short)0);
			vi.setVoucher(voucher);
			vi.setVchKey(UUID.randomUUID().toString());
			
			instances.add(vi);
			voucherInstanceDao.save(vi);
		}
		voucherDao.save(voucher);
	}

	@Override
	public void updateVoucher(Voucher voucher) {
		Voucher oldVoucher = voucherDao.findVoucherById(voucher.getId());
		
		voucher.setCreateDate(oldVoucher.getCreateDate());
		voucherDao.update(voucher);
	}

	@Override
	public void deleteById(int id) {
		voucherDao.deleteById(id);
	}

}
