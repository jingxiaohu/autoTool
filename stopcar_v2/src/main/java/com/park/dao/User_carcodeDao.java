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

//user_carcode

@Repository("user_carcodeDao")
public class User_carcodeDao extends BaseDao{

    Logger log = Logger.getLogger(User_carcodeDao.class);



    public  String TABLE = "user_carcode";

    public  String TABLENAME = "user_carcode";

    public  String TABLEMM(){
        return TABLE + sdfMm.format(new java.util.Date());
    }

    public  String TABLEDD(){
        return TABLE + sdfDd.format(new java.util.Date());
    }

    public javax.sql.DataSource ds;

    public  String[] carrays ={"uc_id","ui_id","car_code","ctime","utime","run_url","is_default","car_brand","uc_color","note"};
    public  String coulmns ="uc_id,ui_id,car_code,ctime,utime,run_url,is_default,car_brand,uc_color,note";
    public  String coulmns2 ="ui_id,car_code,ctime,utime,run_url,is_default,car_brand,uc_color,note";

    //添加数据
    public int insert(User_carcode bean) throws SQLException{
        return insert(bean, TABLENAME);
    }

    //添加数据
    public int insert(User_carcode bean, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "INSERT INTO "+TABLENAME2+" (ui_id,car_code,ctime,utime,run_url,is_default,car_brand,uc_color,note) VALUES (:ui_id,:car_code,:ctime,:utime,:run_url,:is_default,:car_brand,:uc_color,:note)";
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
    public int insert2(User_carcode bean) throws SQLException{
        return insert2(bean, TABLENAME);
    }

    //添加数据
    public int insert2(User_carcode bean, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "INSERT INTO "+TABLENAME2+" (uc_id,ui_id,car_code,ctime,utime,run_url,is_default,car_brand,uc_color,note) VALUES (:uc_id,:ui_id,:car_code,:ctime,:utime,:run_url,:is_default,:car_brand,:uc_color,:note)";
            SqlParameterSource ps = new BeanPropertySqlParameterSource(bean);
            return _np.update(sql, ps);
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            throw new SQLException("insert2 is error", e);
        }
    }

    //批量添加数据
    public int[] insert(List<User_carcode> beans) throws SQLException{
        return insert(beans, TABLENAME);
    }

    //批量添加数据
    public int[] insert(final List<User_carcode> beans, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "INSERT INTO "+TABLENAME2+" (ui_id,car_code,ctime,utime,run_url,is_default,car_brand,uc_color,note) VALUES (?,?,?,?,?,?,?,?,?)";
            return _np.getJdbcOperations().batchUpdate(sql, new BatchPreparedStatementSetter() {
                //@Override
                public int getBatchSize() {
                    return beans.size();
                }
                //@Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    User_carcode bean = beans.get(i);
                    ps.setLong(1, bean.ui_id);
                    ps.setString(2, bean.car_code);
                    ps.setTimestamp(3, new Timestamp(bean.ctime.getTime()));
                    ps.setTimestamp(4, new Timestamp(bean.utime.getTime()));
                    ps.setString(5, bean.run_url);
                    ps.setInt(6, bean.is_default);
                    ps.setString(7, bean.car_brand);
                    ps.setInt(8, bean.uc_color);
                    ps.setString(9, bean.note);
                }
            });
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            throw new SQLException("insert is error", e);
        }
    }

    //删除单条数据
    public int deleteByKey(long uc_id) throws SQLException{
        return deleteByKey(uc_id, TABLENAME);
    }

    //删除单条数据
    public int deleteByKey(long uc_id, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "DELETE FROM "+TABLENAME2+" WHERE uc_id=:uc_id";
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("uc_id", uc_id);
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
            sql = "DELETE FROM "+TABLENAME2+" WHERE uc_id=?";
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
    public List<User_carcode> selectAll() {
        return selectAll(TABLENAME);
    }

    //查询所有数据
    public List<User_carcode> selectAll(String TABLENAME2) {
        String sql;
        try{
            sql = "SELECT uc_id,ui_id,car_code,ctime,utime,run_url,is_default,car_brand,uc_color,note FROM "+TABLENAME2+" ORDER BY uc_id";
            return _np.getJdbcOperations().query(sql, new BeanPropertyRowMapper<User_carcode>(User_carcode.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<User_carcode>();
        }
    }

    //查询最新数据
    public List<User_carcode> selectLast(int num) {
        return selectLast(num, TABLENAME);
    }

    //查询所有数据
    public List<User_carcode> selectLast(int num ,String TABLENAME2) {
        String sql;
        try{
            sql = "SELECT uc_id,ui_id,car_code,ctime,utime,run_url,is_default,car_brand,uc_color,note FROM "+TABLENAME2+" ORDER BY uc_id DESC LIMIT "+num+"" ;
            return _np.getJdbcOperations().query(sql, new BeanPropertyRowMapper<User_carcode>(User_carcode.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<User_carcode>();
        }
    }

    //根据主键查询
    public List<User_carcode> selectGtKey(long uc_id) {
        return selectGtKey(uc_id, TABLENAME);
    }

    //根据主键查询
    public List<User_carcode> selectGtKey(long uc_id, String TABLENAME2) {
        String sql;
        try{
            sql="SELECT uc_id,ui_id,car_code,ctime,utime,run_url,is_default,car_brand,uc_color,note FROM "+TABLENAME2+" WHERE uc_id>:uc_id";
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("uc_id", uc_id);
            return _np.query(sql, param, new BeanPropertyRowMapper<User_carcode>(User_carcode.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<User_carcode>();
        }
    }

    //根据主键查询
    public User_carcode selectByKey(long uc_id) {
        return selectByKey(uc_id, TABLENAME);
    }

    //根据主键查询
    public User_carcode selectByKey(long uc_id, String TABLENAME2) {
        String sql;
        try{
            sql="SELECT uc_id,ui_id,car_code,ctime,utime,run_url,is_default,car_brand,uc_color,note FROM "+TABLENAME2+" WHERE uc_id=:uc_id";
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("uc_id", uc_id);
            return (User_carcode)_np.queryForObject(sql, param, new BeanPropertyRowMapper<User_carcode>(User_carcode.class));
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
    public List<User_carcode> selectByPage(int begin, int num) {
        return selectByPage(begin, num, TABLENAME);
    }

    //分页查询
    public List<User_carcode> selectByPage(int begin, int num, String TABLENAME2) {
        try{
            String sql;
            sql = "SELECT uc_id,ui_id,car_code,ctime,utime,run_url,is_default,car_brand,uc_color,note FROM "+TABLENAME2+" LIMIT "+begin+", "+num+"";
            return _np.getJdbcOperations().query(sql,new BeanPropertyRowMapper<User_carcode>(User_carcode.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<User_carcode>();
        }
    }

    //修改数据
    public int updateByKey(User_carcode bean) {
        return updateByKey(bean, TABLENAME);
    }

    //修改数据
    public int updateByKey(User_carcode bean, String TABLENAME2) {
        try{
            String sql;
            sql = "UPDATE "+TABLENAME2+" SET ui_id=:ui_id,car_code=:car_code,ctime=:ctime,utime=:utime,run_url=:run_url,is_default=:is_default,car_brand=:car_brand,uc_color=:uc_color,note=:note WHERE uc_id=:uc_id";
            SqlParameterSource ps = new BeanPropertySqlParameterSource(bean);
            return _np.update(sql, ps);
        }catch(Exception e){
            log.error(e);
            return 0;
        }
    }

    //批量修改数据
    public int[] updateByKey (final List<User_carcode> beans) throws SQLException{
        return updateByKey(beans, TABLENAME);
    }

    //批量修改数据
    public int[] updateByKey (final List<User_carcode> beans, String TABLENAME2) throws SQLException{
        try{
            String sql;
            sql = "UPDATE "+TABLENAME2+" SET ui_id=?,car_code=?,ctime=?,utime=?,run_url=?,is_default=?,car_brand=?,uc_color=?,note=? WHERE uc_id=?";
            return _np.getJdbcOperations().batchUpdate(sql, new BatchPreparedStatementSetter() {
                //@Override
                public int getBatchSize() {
                    return beans.size();
                }
                //@Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    User_carcode bean = beans.get(i);
                    ps.setLong(1, bean.ui_id);
                    ps.setString(2, bean.car_code);
                    ps.setTimestamp(3, new Timestamp(bean.ctime.getTime()));
                    ps.setTimestamp(4, new Timestamp(bean.utime.getTime()));
                    ps.setString(5, bean.run_url);
                    ps.setInt(6, bean.is_default);
                    ps.setString(7, bean.car_brand);
                    ps.setInt(8, bean.uc_color);
                    ps.setString(9, bean.note);
                    ps.setLong(10, bean.uc_id);
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
                 "	`uc_id`  BIGINT(20) NOT NULL AUTO_INCREMENT," +
                 "	`ui_id`  BIGINT(20)," +
                 "	`car_code`  VARCHAR(80)," +
                 "	`ctime`  DATETIME," +
                 "	`utime`  DATETIME," +
                 "	`run_url`  VARCHAR(200)," +
                 "	`is_default`  INT(11)," +
                 "	`car_brand`  VARCHAR(100)," +
                 "	`uc_color`  INT(11)," +
                 "	`note`  VARCHAR(100)," +
                 "	PRIMARY KEY (`uc_id`)" +
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
