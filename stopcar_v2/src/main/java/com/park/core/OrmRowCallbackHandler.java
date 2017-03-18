package com.park.core;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowCallbackHandler;
public class OrmRowCallbackHandler<T> implements RowCallbackHandler {

	  private Class<T> cla;
	  private List<T> data = new ArrayList<T>();
	  public List<T> getData() {
		if(data.size() == 0){
			return null;
		}  
	    return this.data;
	  }

	  public OrmRowCallbackHandler(Class<T> cla) {
	    this.cla = cla;
	  }

	public void processRow(ResultSet rs) throws SQLException {
		try {
			if (rs != null) {
					T obj = this.cla.newInstance();
					for (Field f : this.cla.getDeclaredFields()) {
						f.setAccessible(true);
						try {
							f.set(obj, rs.getObject(f.getName()));
						} catch (Exception e) {
						}
					}
					data.add(obj);
			}
		} catch (Exception e) {
			throw new SQLException(e);
		}
	}

}
