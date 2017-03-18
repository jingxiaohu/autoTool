package com.park.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.park.DataSource.DynamicDataSource;
import com.park.dao.BaseDao;

@Service("MySelfService")
public class MySelfService {
	Logger log = Logger.getLogger(MySelfService.class);
	@Autowired
	private BaseDao baseDao;
	
	public BaseDao getBaseDao() {
		return baseDao;
	}
	/*public BasicDataSource getDs() {
		return getBaseDao().getDs();
	}*/
	public DynamicDataSource getDs() {
		return getBaseDao().getDs();
	}
	/**
	 * String sql="select s_name as sname from stu where s_name=:sname"; 
	 * 泛型查询
	 */
	public <T> T getdatas(Class<T> beanclass, String sql){
		try {
			return getBaseDao().getdatas(beanclass,  sql);
		} catch (DataAccessException e) {
			log.error("MySelfService getdatas", e);
		}
		return null;
	}
	
	/**
	 * 
	* @Title: queryForList
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param @param sql
	* @param @param paramMap
	* @param @return    设定文件
	* @return List<Map<String,Object>>    返回类型
	* @throws
	 */
	public List<Map<String, Object>> queryForList(String sql,Map<String,?> paramMap) { 
		try {
			return  getBaseDao().queryForList(sql,paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("MySelfService queryForList", e);
			return null;
		}
	}
	
	 
	public Map<String, Object> queryForMap(String sql,Map<String,?> paramMap) { 
		try {
			return  getBaseDao().queryForMap(sql,paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("MySelfService queryForMap", e);
			return null;
		}
	}
	/**
	 * 不靠主键的查询
	 * 
	 * @param classbean 对应的BEAN
	 * @param KeyValue 键值
	 * @param KeyName 键名
	 * @param selectByOther_SQL
	 * @return
	 */
	public List<Map<String, Object>> selectByOther(Object KeyValue, String KeyName,String selectByOther_SQL) { 
		try {
			return getBaseDao().selectByOther( KeyValue,  KeyName, selectByOther_SQL);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("MySelfService selectByOther", e);
			return null;
		}
	}
	 /**
	 * 根据字段查询记录条数
	 * 
	 * @param TABLENAME
	 * @param FieldName 字段名
	 * @param FieldValue 字段对应的值
	 * @return
	 */
	public int countByField(String TABLENAME, String FieldName,Object FieldValue) {
		try {
			return getBaseDao().countByField( TABLENAME,  FieldName, FieldValue);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("MySelfService countByField", e);
			return 0;
		}
	}
	/**
	 * 根据条件查询记录条数
	 * 
	 * @param TABLENAME
	 * @param condition 条件
	 * @return
	 */
	
	public int countByCondition(String sql) {
		try {
			return getBaseDao().countByCondition(sql);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("MySelfService countByCondition", e);
			return 0;
		}
	}
	/**
	 * 依靠 参数进行更新
	 * 
	 * @param sql
	 * @param paramMap
	 * @return
	 */
	public int updateBySQL(String sql, Map<String, Object> paramMap)throws Exception {
		return getBaseDao().updateBySQL(sql, paramMap);
	}
	/**
	 * 直接写SQL查询的语句
	 * @param <T>
	 * @param sql
	 * @param cls
	 * @return
	 */
	public <T> List<T> executeQuery(String sql, Class<T> cls,Object...args) {
		try {
			return getBaseDao().executeQuery( sql,  cls,args);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("MySelfService executeQuery", e);
			return null;
		}
	}
	
	/**
	 * 直接写SQL查询的语句
	 * @param <T>
	 * @param sql
	 * @param cls
	 * @return
	 */
	public <T> T executeQueryUniqueT(String sql, Class<T> cls,Object...args) {
		try {
			return getBaseDao().executeQueryUniqueT( sql,  cls,args);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("MySelfService executeQuery", e);
			return null;
		}
	}
	
	
	/**
	 * 直接SQL执行
	 * 
	 * @param sql
	 */
	public void execute(String sql) throws Exception {
		getBaseDao().execute(sql);
	}
	/************************扩展DAO*****************************/
	/**
	 * 获取多个对象 T
	 * @param sql  select * from user where id=?
	 * @param cla User.class
	 * @param args new Object[]{1}
	 * @return list<T>
	 */
	public <T> List<T> queryListT(String sql, Class<T> cla, Object... args) throws Exception {
		return getBaseDao().queryListT(sql, cla, args);
	}
	
	public <T> List<T> queryListT(String sql, Class<T> cla) throws Exception {
		return getBaseDao().queryListT(sql, cla);
	}
	/**
	 * 获取唯一对象 T
	 * @param <T>
	 * @param sql  select * from user where id=?
	 * @param cla User.class
	 * @param args new Object[]{1}
	 * @return
	 * @throws Exception
	 */
	public <T> T queryUniqueT(String sql, Class<T> cla, Object... args) throws Exception {
		return getBaseDao().queryUniqueT(sql, cla, args);
	}
	
	public <T> T queryUniqueT(String sql, Class<T> cla) throws Exception {
		return getBaseDao().queryUniqueT(sql, cla);
	}
}
