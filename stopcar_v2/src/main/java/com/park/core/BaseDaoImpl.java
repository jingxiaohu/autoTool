package com.park.core;

import java.io.Serializable;
import java.util.List;

import com.park.exception.QzException;

public abstract class BaseDaoImpl<T> {

	public void delete(long id) throws QzException {
		try {
			getMapper().delete(id);
		} catch (Exception e) {
			throw new QzException("BaseDaoImpl.delete",e); 
		}
	}

	public T getOne(Serializable id) throws QzException {
		T entity = null;
		try {
			entity = (T) getMapper().getOne(id);
		} catch (Exception e) {
			throw new QzException("BaseDaoImpl.getOne",e); 
		}
		return entity;
	}

	public List<T> getAll() throws QzException {
		try {
			return (List<T>) getMapper().getAll();
		} catch (Exception e) {
			throw new QzException("BaseDaoImpl.getAll",e); 
		}
	}

	public void save(T t) throws QzException {
		try {
			getMapper().save(t);
		} catch (Exception e) {
			throw new QzException("BaseDaoImpl.save",e); 
		}
	}

	public void update(T t) throws QzException {
		try {
			getMapper().update(t);
		} catch (Exception e) {
			throw new QzException("BaseDaoImpl.update",e); 
		}
	}

	public abstract BaseMapper<T> getMapper();
}
