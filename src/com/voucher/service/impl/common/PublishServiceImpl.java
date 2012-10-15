package com.voucher.service.impl.common;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.voucher.dao.common.PublishDao;
import com.voucher.entity.common.Publish;
import com.voucher.pojo.ExtPager;
import com.voucher.service.common.PublishService;
import com.voucher.service.impl.BaseServiceImpl;

public class PublishServiceImpl extends BaseServiceImpl<Publish> implements PublishService {
	private PublishDao publishDao;

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
		return publishDao.getTotalCount(Publish.class);
	}

	@Override
	public void deletePublishById(int id) {
		this.deleteById(Publish.class, id);
	}

}
