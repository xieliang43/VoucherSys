package com.voucher.service.impl;

import com.voucher.dao.PublishDao;
import com.voucher.entity.Publish;
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

}
