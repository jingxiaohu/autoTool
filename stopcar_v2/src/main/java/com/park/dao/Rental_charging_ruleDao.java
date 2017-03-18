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

//rental_charging_rule

@Repository("rental_charging_ruleDao")
public class Rental_charging_ruleDao extends BaseDao{

    Logger log = Logger.getLogger(Rental_charging_ruleDao.class);



    public  String TABLE = "rental_charging_rule";

    public  String TABLENAME = "rental_charging_rule";

    public  String TABLEMM(){
        return TABLE + sdfMm.format(new java.util.Date());
    }

    public  String TABLEDD(){
        return TABLE + sdfDd.format(new java.util.Date());
    }

    public javax.sql.DataSource ds;

    public  String[] carrays ={"rcr_id","pi_id","start_price","start_time","charging","charging_time","month_price","month_time","permit_time","timeout_info","rcr_type","rcr_state","rcr_discount","car_displacement","car_type","car_code_color","is_time_bucket","time_bucket","ctime","utime","rcr_md5","is_default","area_code","note","free_minute","is_free_minute"};
    public  String coulmns ="rcr_id,pi_id,start_price,start_time,charging,charging_time,month_price,month_time,permit_time,timeout_info,rcr_type,rcr_state,rcr_discount,car_displacement,car_type,car_code_color,is_time_bucket,time_bucket,ctime,utime,rcr_md5,is_default,area_code,note,free_minute,is_free_minute";
    public  String coulmns2 ="pi_id,start_price,start_time,charging,charging_time,month_price,month_time,permit_time,timeout_info,rcr_type,rcr_state,rcr_discount,car_displacement,car_type,car_code_color,is_time_bucket,time_bucket,ctime,utime,rcr_md5,is_default,area_code,note,free_minute,is_free_minute";

    //添加数据
    public int insert(Rental_charging_rule bean) throws SQLException{
        return insert(bean, TABLENAME);
    }

    //添加数据
    public int insert(Rental_charging_rule bean, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "INSERT INTO "+TABLENAME2+" (pi_id,start_price,start_time,charging,charging_time,month_price,month_time,permit_time,timeout_info,rcr_type,rcr_state,rcr_discount,car_displacement,car_type,car_code_color,is_time_bucket,time_bucket,ctime,utime,rcr_md5,is_default,area_code,note,free_minute,is_free_minute) VALUES (:pi_id,:start_price,:start_time,:charging,:charging_time,:month_price,:month_time,:permit_time,:timeout_info,:rcr_type,:rcr_state,:rcr_discount,:car_displacement,:car_type,:car_code_color,:is_time_bucket,:time_bucket,:ctime,:utime,:rcr_md5,:is_default,:area_code,:note,:free_minute,:is_free_minute)";
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
    public int insert2(Rental_charging_rule bean) throws SQLException{
        return insert2(bean, TABLENAME);
    }

    //添加数据
    public int insert2(Rental_charging_rule bean, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "INSERT INTO "+TABLENAME2+" (rcr_id,pi_id,start_price,start_time,charging,charging_time,month_price,month_time,permit_time,timeout_info,rcr_type,rcr_state,rcr_discount,car_displacement,car_type,car_code_color,is_time_bucket,time_bucket,ctime,utime,rcr_md5,is_default,area_code,note,free_minute,is_free_minute) VALUES (:rcr_id,:pi_id,:start_price,:start_time,:charging,:charging_time,:month_price,:month_time,:permit_time,:timeout_info,:rcr_type,:rcr_state,:rcr_discount,:car_displacement,:car_type,:car_code_color,:is_time_bucket,:time_bucket,:ctime,:utime,:rcr_md5,:is_default,:area_code,:note,:free_minute,:is_free_minute)";
            SqlParameterSource ps = new BeanPropertySqlParameterSource(bean);
            return _np.update(sql, ps);
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            throw new SQLException("insert2 is error", e);
        }
    }

    //批量添加数据
    public int[] insert(List<Rental_charging_rule> beans) throws SQLException{
        return insert(beans, TABLENAME);
    }

    //批量添加数据
    public int[] insert(final List<Rental_charging_rule> beans, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "INSERT INTO "+TABLENAME2+" (pi_id,start_price,start_time,charging,charging_time,month_price,month_time,permit_time,timeout_info,rcr_type,rcr_state,rcr_discount,car_displacement,car_type,car_code_color,is_time_bucket,time_bucket,ctime,utime,rcr_md5,is_default,area_code,note,free_minute,is_free_minute) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            return _np.getJdbcOperations().batchUpdate(sql, new BatchPreparedStatementSetter() {
                //@Override
                public int getBatchSize() {
                    return beans.size();
                }
                //@Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Rental_charging_rule bean = beans.get(i);
                    ps.setLong(1, bean.pi_id);
                    ps.setInt(2, bean.start_price);
                    ps.setInt(3, bean.start_time);
                    ps.setInt(4, bean.charging);
                    ps.setInt(5, bean.charging_time);
                    ps.setInt(6, bean.month_price);
                    ps.setInt(7, bean.month_time);
                    ps.setString(8, bean.permit_time);
                    ps.setString(9, bean.timeout_info);
                    ps.setInt(10, bean.rcr_type);
                    ps.setInt(11, bean.rcr_state);
                    ps.setInt(12, bean.rcr_discount);
                    ps.setString(13, bean.car_displacement);
                    ps.setInt(14, bean.car_type);
                    ps.setInt(15, bean.car_code_color);
                    ps.setInt(16, bean.is_time_bucket);
                    ps.setString(17, bean.time_bucket);
                    ps.setTimestamp(18, new Timestamp(bean.ctime.getTime()));
                    ps.setTimestamp(19, new Timestamp(bean.utime.getTime()));
                    ps.setString(20, bean.rcr_md5);
                    ps.setInt(21, bean.is_default);
                    ps.setString(22, bean.area_code);
                    ps.setString(23, bean.note);
                    ps.setInt(24, bean.free_minute);
                    ps.setInt(25, bean.is_free_minute);
                }
            });
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            throw new SQLException("insert is error", e);
        }
    }

    //删除单条数据
    public int deleteByKey(long rcr_id) throws SQLException{
        return deleteByKey(rcr_id, TABLENAME);
    }

    //删除单条数据
    public int deleteByKey(long rcr_id, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "DELETE FROM "+TABLENAME2+" WHERE rcr_id=:rcr_id";
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("rcr_id", rcr_id);
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
            sql = "DELETE FROM "+TABLENAME2+" WHERE rcr_id=?";
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
    public List<Rental_charging_rule> selectAll() {
        return selectAll(TABLENAME);
    }

    //查询所有数据
    public List<Rental_charging_rule> selectAll(String TABLENAME2) {
        String sql;
        try{
            sql = "SELECT rcr_id,pi_id,start_price,start_time,charging,charging_time,month_price,month_time,permit_time,timeout_info,rcr_type,rcr_state,rcr_discount,car_displacement,car_type,car_code_color,is_time_bucket,time_bucket,ctime,utime,rcr_md5,is_default,area_code,note,free_minute,is_free_minute FROM "+TABLENAME2+" ORDER BY rcr_id";
            return _np.getJdbcOperations().query(sql, new BeanPropertyRowMapper<Rental_charging_rule>(Rental_charging_rule.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<Rental_charging_rule>();
        }
    }

    //查询最新数据
    public List<Rental_charging_rule> selectLast(int num) {
        return selectLast(num, TABLENAME);
    }

    //查询所有数据
    public List<Rental_charging_rule> selectLast(int num ,String TABLENAME2) {
        String sql;
        try{
            sql = "SELECT rcr_id,pi_id,start_price,start_time,charging,charging_time,month_price,month_time,permit_time,timeout_info,rcr_type,rcr_state,rcr_discount,car_displacement,car_type,car_code_color,is_time_bucket,time_bucket,ctime,utime,rcr_md5,is_default,area_code,note,free_minute,is_free_minute FROM "+TABLENAME2+" ORDER BY rcr_id DESC LIMIT "+num+"" ;
            return _np.getJdbcOperations().query(sql, new BeanPropertyRowMapper<Rental_charging_rule>(Rental_charging_rule.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<Rental_charging_rule>();
        }
    }

    //根据主键查询
    public List<Rental_charging_rule> selectGtKey(long rcr_id) {
        return selectGtKey(rcr_id, TABLENAME);
    }

    //根据主键查询
    public List<Rental_charging_rule> selectGtKey(long rcr_id, String TABLENAME2) {
        String sql;
        try{
            sql="SELECT rcr_id,pi_id,start_price,start_time,charging,charging_time,month_price,month_time,permit_time,timeout_info,rcr_type,rcr_state,rcr_discount,car_displacement,car_type,car_code_color,is_time_bucket,time_bucket,ctime,utime,rcr_md5,is_default,area_code,note,free_minute,is_free_minute FROM "+TABLENAME2+" WHERE rcr_id>:rcr_id";
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("rcr_id", rcr_id);
            return _np.query(sql, param, new BeanPropertyRowMapper<Rental_charging_rule>(Rental_charging_rule.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<Rental_charging_rule>();
        }
    }

    //根据主键查询
    public Rental_charging_rule selectByKey(long rcr_id) {
        return selectByKey(rcr_id, TABLENAME);
    }

    //根据主键查询
    public Rental_charging_rule selectByKey(long rcr_id, String TABLENAME2) {
        String sql;
        try{
            sql="SELECT rcr_id,pi_id,start_price,start_time,charging,charging_time,month_price,month_time,permit_time,timeout_info,rcr_type,rcr_state,rcr_discount,car_displacement,car_type,car_code_color,is_time_bucket,time_bucket,ctime,utime,rcr_md5,is_default,area_code,note,free_minute,is_free_minute FROM "+TABLENAME2+" WHERE rcr_id=:rcr_id";
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("rcr_id", rcr_id);
            return (Rental_charging_rule)_np.queryForObject(sql, param, new BeanPropertyRowMapper<Rental_charging_rule>(Rental_charging_rule.class));
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
    public List<Rental_charging_rule> selectByPage(int begin, int num) {
        return selectByPage(begin, num, TABLENAME);
    }

    //分页查询
    public List<Rental_charging_rule> selectByPage(int begin, int num, String TABLENAME2) {
        try{
            String sql;
            sql = "SELECT rcr_id,pi_id,start_price,start_time,charging,charging_time,month_price,month_time,permit_time,timeout_info,rcr_type,rcr_state,rcr_discount,car_displacement,car_type,car_code_color,is_time_bucket,time_bucket,ctime,utime,rcr_md5,is_default,area_code,note,free_minute,is_free_minute FROM "+TABLENAME2+" LIMIT "+begin+", "+num+"";
            return _np.getJdbcOperations().query(sql,new BeanPropertyRowMapper<Rental_charging_rule>(Rental_charging_rule.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<Rental_charging_rule>();
        }
    }

    //修改数据
    public int updateByKey(Rental_charging_rule bean) {
        return updateByKey(bean, TABLENAME);
    }

    //修改数据
    public int updateByKey(Rental_charging_rule bean, String TABLENAME2) {
        try{
            String sql;
            sql = "UPDATE "+TABLENAME2+" SET pi_id=:pi_id,start_price=:start_price,start_time=:start_time,charging=:charging,charging_time=:charging_time,month_price=:month_price,month_time=:month_time,permit_time=:permit_time,timeout_info=:timeout_info,rcr_type=:rcr_type,rcr_state=:rcr_state,rcr_discount=:rcr_discount,car_displacement=:car_displacement,car_type=:car_type,car_code_color=:car_code_color,is_time_bucket=:is_time_bucket,time_bucket=:time_bucket,ctime=:ctime,utime=:utime,rcr_md5=:rcr_md5,is_default=:is_default,area_code=:area_code,note=:note,free_minute=:free_minute,is_free_minute=:is_free_minute WHERE rcr_id=:rcr_id";
            SqlParameterSource ps = new BeanPropertySqlParameterSource(bean);
            return _np.update(sql, ps);
        }catch(Exception e){
            log.error(e);
            return 0;
        }
    }

    //批量修改数据
    public int[] updateByKey (final List<Rental_charging_rule> beans) throws SQLException{
        return updateByKey(beans, TABLENAME);
    }

    //批量修改数据
    public int[] updateByKey (final List<Rental_charging_rule> beans, String TABLENAME2) throws SQLException{
        try{
            String sql;
            sql = "UPDATE "+TABLENAME2+" SET pi_id=?,start_price=?,start_time=?,charging=?,charging_time=?,month_price=?,month_time=?,permit_time=?,timeout_info=?,rcr_type=?,rcr_state=?,rcr_discount=?,car_displacement=?,car_type=?,car_code_color=?,is_time_bucket=?,time_bucket=?,ctime=?,utime=?,rcr_md5=?,is_default=?,area_code=?,note=?,free_minute=?,is_free_minute=? WHERE rcr_id=?";
            return _np.getJdbcOperations().batchUpdate(sql, new BatchPreparedStatementSetter() {
                //@Override
                public int getBatchSize() {
                    return beans.size();
                }
                //@Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Rental_charging_rule bean = beans.get(i);
                    ps.setLong(1, bean.pi_id);
                    ps.setInt(2, bean.start_price);
                    ps.setInt(3, bean.start_time);
                    ps.setInt(4, bean.charging);
                    ps.setInt(5, bean.charging_time);
                    ps.setInt(6, bean.month_price);
                    ps.setInt(7, bean.month_time);
                    ps.setString(8, bean.permit_time);
                    ps.setString(9, bean.timeout_info);
                    ps.setInt(10, bean.rcr_type);
                    ps.setInt(11, bean.rcr_state);
                    ps.setInt(12, bean.rcr_discount);
                    ps.setString(13, bean.car_displacement);
                    ps.setInt(14, bean.car_type);
                    ps.setInt(15, bean.car_code_color);
                    ps.setInt(16, bean.is_time_bucket);
                    ps.setString(17, bean.time_bucket);
                    ps.setTimestamp(18, new Timestamp(bean.ctime.getTime()));
                    ps.setTimestamp(19, new Timestamp(bean.utime.getTime()));
                    ps.setString(20, bean.rcr_md5);
                    ps.setInt(21, bean.is_default);
                    ps.setString(22, bean.area_code);
                    ps.setString(23, bean.note);
                    ps.setInt(24, bean.free_minute);
                    ps.setInt(25, bean.is_free_minute);
                    ps.setLong(26, bean.rcr_id);
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
                 "	`rcr_id`  BIGINT(20) NOT NULL AUTO_INCREMENT," +
                 "	`pi_id`  BIGINT(20)," +
                 "	`start_price`  INT(11)," +
                 "	`start_time`  INT(11)," +
                 "	`charging`  INT(11)," +
                 "	`charging_time`  INT(11)," +
                 "	`month_price`  INT(11)," +
                 "	`month_time`  INT(11)," +
                 "	`permit_time`  VARCHAR(100)," +
                 "	`timeout_info`  VARCHAR(150)," +
                 "	`rcr_type`  INT(11)," +
                 "	`rcr_state`  INT(11)," +
                 "	`rcr_discount`  INT(11)," +
                 "	`car_displacement`  VARCHAR(60)," +
                 "	`car_type`  INT(11)," +
                 "	`car_code_color`  INT(11)," +
                 "	`is_time_bucket`  INT(11)," +
                 "	`time_bucket`  VARCHAR(100)," +
                 "	`ctime`  DATETIME," +
                 "	`utime`  DATETIME," +
                 "	`rcr_md5`  VARCHAR(80)," +
                 "	`is_default`  INT(11)," +
                 "	`area_code`  VARCHAR(20)," +
                 "	`note`  VARCHAR(100)," +
                 "	`free_minute`  INT(11)," +
                 "	`is_free_minute`  INT(11)," +
                 "	PRIMARY KEY (`rcr_id`)" +
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
