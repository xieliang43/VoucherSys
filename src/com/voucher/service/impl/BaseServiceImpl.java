package com.voucher.service.impl;

import com.voucher.dao.BaseDao;
import com.voucher.service.BaseService;

public abstract class BaseServiceImpl<T> implements BaseService<T> {
	private BaseDao<T> baseDao;

	@Override
	public void save(T entity) {
		getBaseDao().save(entity);
	}

	@Override
	public void update(T entity) {
		getBaseDao().update(entity);
	}

	@Override
	public void delete(T entity) {
		getBaseDao().delete(entity);
	}

	@Override
	public T findById(Class<T> clazz, int id) {
		return (T) getBaseDao().findById(clazz, id);
	}
	
	@Override
	public void deleteById(Class<T> clazz, int id) {
		getBaseDao().deleteById(clazz, id);
	}

	public BaseDao<T> getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}

}
