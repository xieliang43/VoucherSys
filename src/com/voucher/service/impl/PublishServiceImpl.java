package com.voucher.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.voucher.dao.PublishDao;
import com.voucher.entity.Publish;
import com.voucher.pojo.ExtPager;
import com.voucher.service.PublishService;

public class PublishServiceImpl implements PublishService {
	private PublishDao publishDao;

	@Override
	public void savePublish(Publish publish) {
		publishDao.save(publish);
	}

	/**
	 * @return the publishDao
	 */
	public PublishDao getPublishDao() {
		return publishDao;
	}

	/**
	 * @param publishDao the publishDao to set
	 */
	public void setPublishDao(PublishDao publishDao) {
		this.publishDao = publishDao;
	}

	@Override
	public List<Publish> findPublishByMsg(ExtPager pager, String phoneNo) {
		List<Publish> list = null;
		if(StringUtils.isBlank(phoneNo)) {
			list = publishDao.findPublishes(pager);
		} else {
			list = publishDao.findPublishesByPhoneNo(pager, phoneNo);
		}
		return list;
	}

	@Override
	public int getTotalCount() {
		return publishDao.getTotalCount();
	}

	@Override
	public void deleteById(int id) {
		publishDao.deleteById(id);
	}

}
