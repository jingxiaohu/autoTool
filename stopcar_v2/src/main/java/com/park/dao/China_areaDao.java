package com.park.dao;

import org.apache.log4j.Logger;
import java.util.*;

import java.sql.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.jdbc.support.*;
import com.park.bean.*;
import org.springframework.stereotype.Repository;
import com.highbeauty.text.EasyTemplate;

//china_area

@Repository("china_areaDao")
public class China_areaDao extends BaseDao{

    Logger log = Logger.getLogger(China_areaDao.class);



    public  String TABLE = "china_area";

    public  String TABLENAME = "china_area";

    public  String TABLEMM(){
        return TABLE + sdfMm.format(new java.util.Date());
    }

    public  String TABLEDD(){
        return TABLE + sdfDd.format(new java.util.Date());
    }

    public javax.sql.DataSource ds;

    public  String[] carrays ={"id","area_code","area_name","province_code","province_name","city_code","city_name","father"};
    public  String coulmns ="id,area_code,area_name,province_code,province_name,city_code,city_name,father";
    public  String coulmns2 ="area_code,area_name,province_code,province_name,city_code,city_name,father";

    //添加数据
    public int insert(China_area bean) throws SQLException{
        return insert(bean, TABLENAME);
    }

    //添加数据
    public int insert(China_area bean, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "INSERT INTO "+TABLENAME2+" (area_code,area_name,province_code,province_name,city_code,city_name,father) VALUES (:area_code,:area_name,:province_code,:province_name,:city_code,:city_name,:father)";
            SqlParameterSource ps = new BeanPropertySqlParameterSource(bean);
            KeyHolder keyholder = new GeneratedKeyHolder();
            _np.update(sql, ps, keyholder);
            return keyholder.getKey().intValue();
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            throw new SQLException("insert is error", e);
        }
    }

    //添加数据
    public int insert2(China_area bean) throws SQLException{
        return insert2(bean, TABLENAME);
    }

    //添加数据
    public int insert2(China_area bean, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "INSERT INTO "+TABLENAME2+" (id,area_code,area_name,province_code,province_name,city_code,city_name,father) VALUES (:id,:area_code,:area_name,:province_code,:province_name,:city_code,:city_name,:father)";
            SqlParameterSource ps = new BeanPropertySqlParameterSource(bean);
            return _np.update(sql, ps);
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            throw new SQLException("insert2 is error", e);
        }
    }

    //批量添加数据
    public int[] insert(List<China_area> beans) throws SQLException{
        return insert(beans, TABLENAME);
    }

    //批量添加数据
    public int[] insert(final List<China_area> beans, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "INSERT INTO "+TABLENAME2+" (area_code,area_name,province_code,province_name,city_code,city_name,father) VALUES (?,?,?,?,?,?,?)";
            return _np.getJdbcOperations().batchUpdate(sql, new BatchPreparedStatementSetter() {
                //@Override
                public int getBatchSize() {
                    return beans.size();
                }
                //@Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    China_area bean = beans.get(i);
                    ps.setString(1, bean.area_code);
                    ps.setString(2, bean.area_name);
                    ps.setString(3, bean.province_code);
                    ps.setString(4, bean.province_name);
                    ps.setString(5, bean.city_code);
                    ps.setString(6, bean.city_name);
                    ps.setString(7, bean.father);
                }
            });
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            throw new SQLException("insert is error", e);
        }
    }

    //删除单条数据
    public int deleteByKey(int id) throws SQLException{
        return deleteByKey(id, TABLENAME);
    }

    //删除单条数据
    public int deleteByKey(int id, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "DELETE FROM "+TABLENAME2+" WHERE id=:id";
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("id", id);
            return _np.update(sql, param);
        }catch(Exception e){
            log.error(e);
            throw new SQLException("deleteByKey is error", e);
        }
    }

    //批量删除数据
    public int[] deleteByKey(int[] keys) throws SQLException{
        return deleteByKey(keys, TABLENAME);
    }

    //批量删除数据
    public int[] deleteByKey(final int[] keys, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "DELETE FROM "+TABLENAME2+" WHERE id=?";
            return _np.getJdbcOperations().batchUpdate(sql, new BatchPreparedStatementSetter() {
                //@Override
                public int getBatchSize() {
                    return keys.length;
                }
                //@Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setInt(1 , keys[i]);
                }
            });
        }catch(Exception e){
            log.error(e);
            throw new SQLException("deleteByKey is error", e);
        }
    }

    //查询所有数据
    public List<China_area> selectAll() {
        return selectAll(TABLENAME);
    }

    //查询所有数据
    public List<China_area> selectAll(String TABLENAME2) {
        String sql;
        try{
            sql = "SELECT id,area_code,area_name,province_code,province_name,city_code,city_name,father FROM "+TABLENAME2+" ORDER BY id";
            return _np.getJdbcOperations().query(sql, new BeanPropertyRowMapper<China_area>(China_area.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<China_area>();
        }
    }

    //查询最新数据
    public List<China_area> selectLast(int num) {
        return selectLast(num, TABLENAME);
    }

    //查询所有数据
    public List<China_area> selectLast(int num ,String TABLENAME2) {
        String sql;
        try{
            sql = "SELECT id,area_code,area_name,province_code,province_name,city_code,city_name,father FROM "+TABLENAME2+" ORDER BY id DESC LIMIT "+num+"" ;
            return _np.getJdbcOperations().query(sql, new BeanPropertyRowMapper<China_area>(China_area.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<China_area>();
        }
    }

    //根据主键查询
    public List<China_area> selectGtKey(int id) {
        return selectGtKey(id, TABLENAME);
    }

    //根据主键查询
    public List<China_area> selectGtKey(int id, String TABLENAME2) {
        String sql;
        try{
            sql="SELECT id,area_code,area_name,province_code,province_name,city_code,city_name,father FROM "+TABLENAME2+" WHERE id>:id";
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("id", id);
            return _np.query(sql, param, new BeanPropertyRowMapper<China_area>(China_area.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<China_area>();
        }
    }

    //根据主键查询
    public China_area selectByKey(int id) {
        return selectByKey(id, TABLENAME);
    }

    //根据主键查询
    public China_area selectByKey(int id, String TABLENAME2) {
        String sql;
        try{
            sql="SELECT id,area_code,area_name,province_code,province_name,city_code,city_name,father FROM "+TABLENAME2+" WHERE id=:id";
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("id", id);
            return (China_area)_np.queryForObject(sql, param, new BeanPropertyRowMapper<China_area>(China_area.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return null;
        }
    }

    //所有数据总数
    public int count() {
        return count(TABLENAME);
    }

    //所有数据总数
    @SuppressWarnings("deprecation")
    public int count(String TABLENAME2) {
        String sql;
        try{
            sql="SELECT COUNT(*) FROM "+TABLENAME2+"";
            return _np.getJdbcOperations().queryForInt(sql);
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return 0;
        }
    }

    //分页查询
    public List<China_area> selectByPage(int begin, int num) {
        return selectByPage(begin, num, TABLENAME);
    }

    //分页查询
    public List<China_area> selectByPage(int begin, int num, String TABLENAME2) {
        try{
            String sql;
            sql = "SELECT id,area_code,area_name,province_code,province_name,city_code,city_name,father FROM "+TABLENAME2+" LIMIT "+begin+", "+num+"";
            return _np.getJdbcOperations().query(sql,new BeanPropertyRowMapper<China_area>(China_area.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<China_area>();
        }
    }

    //修改数据
    public int updateByKey(China_area bean) {
        return updateByKey(bean, TABLENAME);
    }

    //修改数据
    public int updateByKey(China_area bean, String TABLENAME2) {
        try{
            String sql;
            sql = "UPDATE "+TABLENAME2+" SET area_code=:area_code,area_name=:area_name,province_code=:province_code,province_name=:province_name,city_code=:city_code,city_name=:city_name,father=:father WHERE id=:id";
            SqlParameterSource ps = new BeanPropertySqlParameterSource(bean);
            return _np.update(sql, ps);
        }catch(Exception e){
            log.error(e);
            return 0;
        }
    }

    //批量修改数据
    public int[] updateByKey (final List<China_area> beans) throws SQLException{
        return updateByKey(beans, TABLENAME);
    }

    //批量修改数据
    public int[] updateByKey (final List<China_area> beans, String TABLENAME2) throws SQLException{
        try{
            String sql;
            sql = "UPDATE "+TABLENAME2+" SET area_code=?,area_name=?,province_code=?,province_name=?,city_code=?,city_name=?,father=? WHERE id=?";
            return _np.getJdbcOperations().batchUpdate(sql, new BatchPreparedStatementSetter() {
                //@Override
                public int getBatchSize() {
                    return beans.size();
                }
                //@Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    China_area bean = beans.get(i);
                    ps.setString(1, bean.area_code);
                    ps.setString(2, bean.area_name);
                    ps.setString(3, bean.province_code);
                    ps.setString(4, bean.province_name);
                    ps.setString(5, bean.city_code);
                    ps.setString(6, bean.city_name);
                    ps.setString(7, bean.father);
                    ps.setInt(8, bean.id);
                }
            });
        }catch(Exception e){
            log.error(e);
            throw new SQLException("updateByKey is error", e);
        }
    }

    //创建表
    public void createTable(String TABLENAME2) throws SQLException{
        try{
            String sql;
            sql = "CREATE TABLE IF NOT EXISTS `${TABLENAME}` (" +
                 "	`id`  INT(11) NOT NULL AUTO_INCREMENT," +
                 "	`area_code`  VARCHAR(50)," +
                 "	`area_name`  VARCHAR(60)," +
                 "	`province_code`  VARCHAR(50)," +
                 "	`province_name`  VARCHAR(60)," +
                 "	`city_code`  VARCHAR(50)," +
                 "	`city_name`  VARCHAR(60)," +
                 "	`father`  VARCHAR(6)," +
                 "	PRIMARY KEY (`id`)" +
                 ") ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;";
            Map<String,String> params = new HashMap<String,String>();
            params.put("TABLENAME", TABLENAME2);
            sql  = EasyTemplate.make(sql, params);
            _np.getJdbcOperations().execute(sql);
        }catch(Exception e){
            log.error(e);
            throw new SQLException("createTable is error", e);
        }
    }

    //清空表
    public void truncate() throws SQLException{
        truncate(TABLENAME);
    }

    //清空表
    public void truncate(String TABLENAME2) throws SQLException{
        try{
            String sql;
            sql="TRUNCATE TABLE "+TABLENAME2+"";
            _np.getJdbcOperations().execute(sql);
        }catch(Exception e){
            log.error(e);
            throw new SQLException("truncate is error", e);
        }
    }

    //修复表
    public void repair(){
        repair(TABLENAME);
    }

    //修复表
    public void repair(String TABLENAME2){
        try{
            String sql;
            sql="REPAIR TABLE "+TABLENAME2+"";
            _np.getJdbcOperations().execute(sql);
        }catch(Exception e){
            log.error(e);
        }
    }

    //优化表
    public void optimize(){
        optimize(TABLENAME);
    }

    //优化表
    public void optimize(String TABLENAME2){
        try{
            String sql;
            sql="OPTIMIZE TABLE "+TABLENAME2+"";
            _np.getJdbcOperations().execute(sql);
        }catch(Exception e){
            log.error(e);
        }
    }

    //执行sql
    public void execute(String sql) throws SQLException{
        try{
            _np.getJdbcOperations().execute(sql);
        }catch(Exception e){
            log.error(e);
            throw new SQLException("execute is error", e);
        }
    }

}
