package com.voucher.dao;

import java.util.List;


public interface BaseDao<T> {
	public void save(T entity);

	public void delete(T entity);

	public void update(T entity);
	
	public T findById(Class<T> clazz, int id);
	
	public void deleteById(Class<T> clazz, int id);
	
	public List<T> findAll(Class<T> clazz);
	
	public int getTotalCount(Class<T> clazz);
}
