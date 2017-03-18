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

//sms_validate

@Repository("sms_validateDao")
public class Sms_validateDao extends BaseDao{

    Logger log = Logger.getLogger(Sms_validateDao.class);



    public  String TABLE = "sms_validate";

    public  String TABLENAME = "sms_validate";

    public  String TABLEMM(){
        return TABLE + sdfMm.format(new java.util.Date());
    }

    public  String TABLEDD(){
        return TABLE + sdfDd.format(new java.util.Date());
    }

    public javax.sql.DataSource ds;

    public  String[] carrays ={"id","v_tel","v_code","v_list","v_class","v_time","v_time_str","note"};
    public  String coulmns ="id,v_tel,v_code,v_list,v_class,v_time,v_time_str,note";
    public  String coulmns2 ="v_tel,v_code,v_list,v_class,v_time,v_time_str,note";

    //添加数据
    public int insert(Sms_validate bean) throws SQLException{
        return insert(bean, TABLENAME);
    }

    //添加数据
    public int insert(Sms_validate bean, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "INSERT INTO "+TABLENAME2+" (v_tel,v_code,v_list,v_class,v_time,v_time_str,note) VALUES (:v_tel,:v_code,:v_list,:v_class,:v_time,:v_time_str,:note)";
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
    public int insert2(Sms_validate bean) throws SQLException{
        return insert2(bean, TABLENAME);
    }

    //添加数据
    public int insert2(Sms_validate bean, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "INSERT INTO "+TABLENAME2+" (id,v_tel,v_code,v_list,v_class,v_time,v_time_str,note) VALUES (:id,:v_tel,:v_code,:v_list,:v_class,:v_time,:v_time_str,:note)";
            SqlParameterSource ps = new BeanPropertySqlParameterSource(bean);
            return _np.update(sql, ps);
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            throw new SQLException("insert2 is error", e);
        }
    }

    //批量添加数据
    public int[] insert(List<Sms_validate> beans) throws SQLException{
        return insert(beans, TABLENAME);
    }

    //批量添加数据
    public int[] insert(final List<Sms_validate> beans, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "INSERT INTO "+TABLENAME2+" (v_tel,v_code,v_list,v_class,v_time,v_time_str,note) VALUES (?,?,?,?,?,?,?)";
            return _np.getJdbcOperations().batchUpdate(sql, new BatchPreparedStatementSetter() {
                //@Override
                public int getBatchSize() {
                    return beans.size();
                }
                //@Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Sms_validate bean = beans.get(i);
                    ps.setString(1, bean.v_tel);
                    ps.setString(2, bean.v_code);
                    ps.setString(3, bean.v_list);
                    ps.setString(4, bean.v_class);
                    ps.setLong(5, bean.v_time);
                    ps.setString(6, bean.v_time_str);
                    ps.setString(7, bean.note);
                }
            });
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            throw new SQLException("insert is error", e);
        }
    }

    //删除单条数据
    public int deleteByKey(long id) throws SQLException{
        return deleteByKey(id, TABLENAME);
    }

    //删除单条数据
    public int deleteByKey(long id, String TABLENAME2) throws SQLException{
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
    public int[] deleteByKey(long[] keys) throws SQLException{
        return deleteByKey(keys, TABLENAME);
    }

    //批量删除数据
    public int[] deleteByKey(final long[] keys, String TABLENAME2) throws SQLException{
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
                    ps.setLong(1 , keys[i]);
                }
            });
        }catch(Exception e){
            log.error(e);
            throw new SQLException("deleteByKey is error", e);
        }
    }

    //查询所有数据
    public List<Sms_validate> selectAll() {
        return selectAll(TABLENAME);
    }

    //查询所有数据
    public List<Sms_validate> selectAll(String TABLENAME2) {
        String sql;
        try{
            sql = "SELECT id,v_tel,v_code,v_list,v_class,v_time,v_time_str,note FROM "+TABLENAME2+" ORDER BY id";
            return _np.getJdbcOperations().query(sql, new BeanPropertyRowMapper<Sms_validate>(Sms_validate.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<Sms_validate>();
        }
    }

    //查询最新数据
    public List<Sms_validate> selectLast(int num) {
        return selectLast(num, TABLENAME);
    }

    //查询所有数据
    public List<Sms_validate> selectLast(int num ,String TABLENAME2) {
        String sql;
        try{
            sql = "SELECT id,v_tel,v_code,v_list,v_class,v_time,v_time_str,note FROM "+TABLENAME2+" ORDER BY id DESC LIMIT "+num+"" ;
            return _np.getJdbcOperations().query(sql, new BeanPropertyRowMapper<Sms_validate>(Sms_validate.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<Sms_validate>();
        }
    }

    //根据主键查询
    public List<Sms_validate> selectGtKey(long id) {
        return selectGtKey(id, TABLENAME);
    }

    //根据主键查询
    public List<Sms_validate> selectGtKey(long id, String TABLENAME2) {
        String sql;
        try{
            sql="SELECT id,v_tel,v_code,v_list,v_class,v_time,v_time_str,note FROM "+TABLENAME2+" WHERE id>:id";
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("id", id);
            return _np.query(sql, param, new BeanPropertyRowMapper<Sms_validate>(Sms_validate.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<Sms_validate>();
        }
    }

    //根据主键查询
    public Sms_validate selectByKey(long id) {
        return selectByKey(id, TABLENAME);
    }

    //根据主键查询
    public Sms_validate selectByKey(long id, String TABLENAME2) {
        String sql;
        try{
            sql="SELECT id,v_tel,v_code,v_list,v_class,v_time,v_time_str,note FROM "+TABLENAME2+" WHERE id=:id";
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("id", id);
            return (Sms_validate)_np.queryForObject(sql, param, new BeanPropertyRowMapper<Sms_validate>(Sms_validate.class));
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
    public List<Sms_validate> selectByPage(int begin, int num) {
        return selectByPage(begin, num, TABLENAME);
    }

    //分页查询
    public List<Sms_validate> selectByPage(int begin, int num, String TABLENAME2) {
        try{
            String sql;
            sql = "SELECT id,v_tel,v_code,v_list,v_class,v_time,v_time_str,note FROM "+TABLENAME2+" LIMIT "+begin+", "+num+"";
            return _np.getJdbcOperations().query(sql,new BeanPropertyRowMapper<Sms_validate>(Sms_validate.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<Sms_validate>();
        }
    }

    //修改数据
    public int updateByKey(Sms_validate bean) {
        return updateByKey(bean, TABLENAME);
    }

    //修改数据
    public int updateByKey(Sms_validate bean, String TABLENAME2) {
        try{
            String sql;
            sql = "UPDATE "+TABLENAME2+" SET v_tel=:v_tel,v_code=:v_code,v_list=:v_list,v_class=:v_class,v_time=:v_time,v_time_str=:v_time_str,note=:note WHERE id=:id";
            SqlParameterSource ps = new BeanPropertySqlParameterSource(bean);
            return _np.update(sql, ps);
        }catch(Exception e){
            log.error(e);
            return 0;
        }
    }

    //批量修改数据
    public int[] updateByKey (final List<Sms_validate> beans) throws SQLException{
        return updateByKey(beans, TABLENAME);
    }

    //批量修改数据
    public int[] updateByKey (final List<Sms_validate> beans, String TABLENAME2) throws SQLException{
        try{
            String sql;
            sql = "UPDATE "+TABLENAME2+" SET v_tel=?,v_code=?,v_list=?,v_class=?,v_time=?,v_time_str=?,note=? WHERE id=?";
            return _np.getJdbcOperations().batchUpdate(sql, new BatchPreparedStatementSetter() {
                //@Override
                public int getBatchSize() {
                    return beans.size();
                }
                //@Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Sms_validate bean = beans.get(i);
                    ps.setString(1, bean.v_tel);
                    ps.setString(2, bean.v_code);
                    ps.setString(3, bean.v_list);
                    ps.setString(4, bean.v_class);
                    ps.setLong(5, bean.v_time);
                    ps.setString(6, bean.v_time_str);
                    ps.setString(7, bean.note);
                    ps.setLong(8, bean.id);
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
                 "	`id`  BIGINT(20) NOT NULL AUTO_INCREMENT," +
                 "	`v_tel`  VARCHAR(40)," +
                 "	`v_code`  VARCHAR(10)," +
                 "	`v_list`  VARCHAR(32)," +
                 "	`v_class`  VARCHAR(40)," +
                 "	`v_time`  BIGINT(20)," +
                 "	`v_time_str`  VARCHAR(40)," +
                 "	`note`  TINYTEXT," +
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
