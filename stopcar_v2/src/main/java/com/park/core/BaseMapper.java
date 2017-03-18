package com.park.core;

import java.io.Serializable;
import java.util.List;

public interface BaseMapper<T> {
	public void save(T t) throws Exception;

	public void delete(long id) throws Exception;

	public void update(T t) throws Exception;

	public List<T> getAll() throws Exception;

	public T getOne(Serializable id) throws Exception;
}
