package com.voucher.service;


public interface BaseService<T> {
	public void save(T entity);
	public void update(T entity);
	public void delete(T entity);
	public void deleteById(Class<T> clazz, int id);
	public T findById(Class<T> clazz, int id);
}
