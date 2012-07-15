package com.voucher.service.impl;

import com.voucher.dao.MerchantDao;
import com.voucher.entity.Merchant;
import com.voucher.service.MerchantService;

public class MerchantServiceImpl implements MerchantService {
	private MerchantDao merchantDao;

	@Override
	public void save(Merchant merchant) {
		merchantDao.create(merchant);
	}

	@Override
	public void update(Merchant merchant) {
		merchantDao.update(merchant);
	}

	@Override
	public void delete(Merchant merchant) {
		merchantDao.delete(merchant);
	}

	@Override
	public Merchant findMerchantById(int merchantId) {
		return merchantDao.findMerchantById(merchantId);
	}

	@Override
	public Merchant findMerchantByPhoneNo(String phoneNo) {
		return merchantDao.findMerchantByPhoneNo(phoneNo);
	}

	@Override
	public Merchant findMerchantByPhoneAndPassword(String phoneNo, String password) {
		return merchantDao.findMerchantByPhoneAndPassword(phoneNo, password);
	}

	public MerchantDao getMerchantDao() {
		return merchantDao;
	}

	public void setMerchantDao(MerchantDao merchantDao) {
		this.merchantDao = merchantDao;
	}

}
