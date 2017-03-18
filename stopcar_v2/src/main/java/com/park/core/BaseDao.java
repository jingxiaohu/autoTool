package com.park.core;

import java.io.Serializable;
import java.util.List;

import com.park.exception.QzException;

public interface BaseDao<T> {
	public void save(T t) throws QzException;

	public void delete(long id) throws QzException;

	public void update(T t) throws QzException;

	public List<T> getAll() throws QzException;

	public T getOne(Serializable id) throws QzException;

}
