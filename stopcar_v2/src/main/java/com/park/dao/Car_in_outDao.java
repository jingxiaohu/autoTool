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

//car_in_out

@Repository("car_in_outDao")
public class Car_in_outDao extends BaseDao{

    Logger log = Logger.getLogger(Car_in_outDao.class);



    public  String TABLE = "car_in_out";

    public  String TABLENAME = "car_in_out";

    public  String TABLEMM(){
        return TABLE + sdfMm.format(new java.util.Date());
    }

    public  String TABLEDD(){
        return TABLE + sdfDd.format(new java.util.Date());
    }

    public javax.sql.DataSource ds;

    public  String[] carrays ={"cio_id","pi_id","pd_id","ui_id","car_code","is_enter","in_out","in_out_code","ctime","utime","area_code","note","car_type","car_code_color","order_id","out_type","is_rent","rent_remain_time","is_local_month"};
    public  String coulmns ="cio_id,pi_id,pd_id,ui_id,car_code,is_enter,in_out,in_out_code,ctime,utime,area_code,note,car_type,car_code_color,order_id,out_type,is_rent,rent_remain_time,is_local_month";
    public  String coulmns2 ="pi_id,pd_id,ui_id,car_code,is_enter,in_out,in_out_code,ctime,utime,area_code,note,car_type,car_code_color,order_id,out_type,is_rent,rent_remain_time,is_local_month";

    //添加数据
    public int insert(Car_in_out bean) throws SQLException{
        return insert(bean, TABLENAME);
    }

    //添加数据
    public int insert(Car_in_out bean, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "INSERT INTO "+TABLENAME2+" (pi_id,pd_id,ui_id,car_code,is_enter,in_out,in_out_code,ctime,utime,area_code,note,car_type,car_code_color,order_id,out_type,is_rent,rent_remain_time,is_local_month) VALUES (:pi_id,:pd_id,:ui_id,:car_code,:is_enter,:in_out,:in_out_code,:ctime,:utime,:area_code,:note,:car_type,:car_code_color,:order_id,:out_type,:is_rent,:rent_remain_time,:is_local_month)";
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
    public int insert2(Car_in_out bean) throws SQLException{
        return insert2(bean, TABLENAME);
    }

    //添加数据
    public int insert2(Car_in_out bean, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "INSERT INTO "+TABLENAME2+" (cio_id,pi_id,pd_id,ui_id,car_code,is_enter,in_out,in_out_code,ctime,utime,area_code,note,car_type,car_code_color,order_id,out_type,is_rent,rent_remain_time,is_local_month) VALUES (:cio_id,:pi_id,:pd_id,:ui_id,:car_code,:is_enter,:in_out,:in_out_code,:ctime,:utime,:area_code,:note,:car_type,:car_code_color,:order_id,:out_type,:is_rent,:rent_remain_time,:is_local_month)";
            SqlParameterSource ps = new BeanPropertySqlParameterSource(bean);
            return _np.update(sql, ps);
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            throw new SQLException("insert2 is error", e);
        }
    }

    //批量添加数据
    public int[] insert(List<Car_in_out> beans) throws SQLException{
        return insert(beans, TABLENAME);
    }

    //批量添加数据
    public int[] insert(final List<Car_in_out> beans, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "INSERT INTO "+TABLENAME2+" (pi_id,pd_id,ui_id,car_code,is_enter,in_out,in_out_code,ctime,utime,area_code,note,car_type,car_code_color,order_id,out_type,is_rent,rent_remain_time,is_local_month) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            return _np.getJdbcOperations().batchUpdate(sql, new BatchPreparedStatementSetter() {
                //@Override
                public int getBatchSize() {
                    return beans.size();
                }
                //@Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Car_in_out bean = beans.get(i);
                    ps.setLong(1, bean.pi_id);
                    ps.setLong(2, bean.pd_id);
                    ps.setLong(3, bean.ui_id);
                    ps.setString(4, bean.car_code);
                    ps.setInt(5, bean.is_enter);
                    ps.setString(6, bean.in_out);
                    ps.setString(7, bean.in_out_code);
                    ps.setTimestamp(8, new Timestamp(bean.ctime.getTime()));
                    ps.setTimestamp(9, new Timestamp(bean.utime.getTime()));
                    ps.setString(10, bean.area_code);
                    ps.setString(11, bean.note);
                    ps.setInt(12, bean.car_type);
                    ps.setInt(13, bean.car_code_color);
                    ps.setString(14, bean.order_id);
                    ps.setInt(15, bean.out_type);
                    ps.setInt(16, bean.is_rent);
                    ps.setLong(17, bean.rent_remain_time);
                    ps.setInt(18, bean.is_local_month);
                }
            });
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            throw new SQLException("insert is error", e);
        }
    }

    //删除单条数据
    public int deleteByKey(long cio_id) throws SQLException{
        return deleteByKey(cio_id, TABLENAME);
    }

    //删除单条数据
    public int deleteByKey(long cio_id, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "DELETE FROM "+TABLENAME2+" WHERE cio_id=:cio_id";
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("cio_id", cio_id);
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
            sql = "DELETE FROM "+TABLENAME2+" WHERE cio_id=?";
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
    public List<Car_in_out> selectAll() {
        return selectAll(TABLENAME);
    }

    //查询所有数据
    public List<Car_in_out> selectAll(String TABLENAME2) {
        String sql;
        try{
            sql = "SELECT cio_id,pi_id,pd_id,ui_id,car_code,is_enter,in_out,in_out_code,ctime,utime,area_code,note,car_type,car_code_color,order_id,out_type,is_rent,rent_remain_time,is_local_month FROM "+TABLENAME2+" ORDER BY cio_id";
            return _np.getJdbcOperations().query(sql, new BeanPropertyRowMapper<Car_in_out>(Car_in_out.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<Car_in_out>();
        }
    }

    //查询最新数据
    public List<Car_in_out> selectLast(int num) {
        return selectLast(num, TABLENAME);
    }

    //查询所有数据
    public List<Car_in_out> selectLast(int num ,String TABLENAME2) {
        String sql;
        try{
            sql = "SELECT cio_id,pi_id,pd_id,ui_id,car_code,is_enter,in_out,in_out_code,ctime,utime,area_code,note,car_type,car_code_color,order_id,out_type,is_rent,rent_remain_time,is_local_month FROM "+TABLENAME2+" ORDER BY cio_id DESC LIMIT "+num+"" ;
            return _np.getJdbcOperations().query(sql, new BeanPropertyRowMapper<Car_in_out>(Car_in_out.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<Car_in_out>();
        }
    }

    //根据主键查询
    public List<Car_in_out> selectGtKey(long cio_id) {
        return selectGtKey(cio_id, TABLENAME);
    }

    //根据主键查询
    public List<Car_in_out> selectGtKey(long cio_id, String TABLENAME2) {
        String sql;
        try{
            sql="SELECT cio_id,pi_id,pd_id,ui_id,car_code,is_enter,in_out,in_out_code,ctime,utime,area_code,note,car_type,car_code_color,order_id,out_type,is_rent,rent_remain_time,is_local_month FROM "+TABLENAME2+" WHERE cio_id>:cio_id";
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("cio_id", cio_id);
            return _np.query(sql, param, new BeanPropertyRowMapper<Car_in_out>(Car_in_out.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<Car_in_out>();
        }
    }

    //根据主键查询
    public Car_in_out selectByKey(long cio_id) {
        return selectByKey(cio_id, TABLENAME);
    }

    //根据主键查询
    public Car_in_out selectByKey(long cio_id, String TABLENAME2) {
        String sql;
        try{
            sql="SELECT cio_id,pi_id,pd_id,ui_id,car_code,is_enter,in_out,in_out_code,ctime,utime,area_code,note,car_type,car_code_color,order_id,out_type,is_rent,rent_remain_time,is_local_month FROM "+TABLENAME2+" WHERE cio_id=:cio_id";
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("cio_id", cio_id);
            return (Car_in_out)_np.queryForObject(sql, param, new BeanPropertyRowMapper<Car_in_out>(Car_in_out.class));
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
    public List<Car_in_out> selectByPage(int begin, int num) {
        return selectByPage(begin, num, TABLENAME);
    }

    //分页查询
    public List<Car_in_out> selectByPage(int begin, int num, String TABLENAME2) {
        try{
            String sql;
            sql = "SELECT cio_id,pi_id,pd_id,ui_id,car_code,is_enter,in_out,in_out_code,ctime,utime,area_code,note,car_type,car_code_color,order_id,out_type,is_rent,rent_remain_time,is_local_month FROM "+TABLENAME2+" LIMIT "+begin+", "+num+"";
            return _np.getJdbcOperations().query(sql,new BeanPropertyRowMapper<Car_in_out>(Car_in_out.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<Car_in_out>();
        }
    }

    //修改数据
    public int updateByKey(Car_in_out bean) {
        return updateByKey(bean, TABLENAME);
    }

    //修改数据
    public int updateByKey(Car_in_out bean, String TABLENAME2) {
        try{
            String sql;
            sql = "UPDATE "+TABLENAME2+" SET pi_id=:pi_id,pd_id=:pd_id,ui_id=:ui_id,car_code=:car_code,is_enter=:is_enter,in_out=:in_out,in_out_code=:in_out_code,ctime=:ctime,utime=:utime,area_code=:area_code,note=:note,car_type=:car_type,car_code_color=:car_code_color,order_id=:order_id,out_type=:out_type,is_rent=:is_rent,rent_remain_time=:rent_remain_time,is_local_month=:is_local_month WHERE cio_id=:cio_id";
            SqlParameterSource ps = new BeanPropertySqlParameterSource(bean);
            return _np.update(sql, ps);
        }catch(Exception e){
            log.error(e);
            return 0;
        }
    }

    //批量修改数据
    public int[] updateByKey (final List<Car_in_out> beans) throws SQLException{
        return updateByKey(beans, TABLENAME);
    }

    //批量修改数据
    public int[] updateByKey (final List<Car_in_out> beans, String TABLENAME2) throws SQLException{
        try{
            String sql;
            sql = "UPDATE "+TABLENAME2+" SET pi_id=?,pd_id=?,ui_id=?,car_code=?,is_enter=?,in_out=?,in_out_code=?,ctime=?,utime=?,area_code=?,note=?,car_type=?,car_code_color=?,order_id=?,out_type=?,is_rent=?,rent_remain_time=?,is_local_month=? WHERE cio_id=?";
            return _np.getJdbcOperations().batchUpdate(sql, new BatchPreparedStatementSetter() {
                //@Override
                public int getBatchSize() {
                    return beans.size();
                }
                //@Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Car_in_out bean = beans.get(i);
                    ps.setLong(1, bean.pi_id);
                    ps.setLong(2, bean.pd_id);
                    ps.setLong(3, bean.ui_id);
                    ps.setString(4, bean.car_code);
                    ps.setInt(5, bean.is_enter);
                    ps.setString(6, bean.in_out);
                    ps.setString(7, bean.in_out_code);
                    ps.setTimestamp(8, new Timestamp(bean.ctime.getTime()));
                    ps.setTimestamp(9, new Timestamp(bean.utime.getTime()));
                    ps.setString(10, bean.area_code);
                    ps.setString(11, bean.note);
                    ps.setInt(12, bean.car_type);
                    ps.setInt(13, bean.car_code_color);
                    ps.setString(14, bean.order_id);
                    ps.setInt(15, bean.out_type);
                    ps.setInt(16, bean.is_rent);
                    ps.setLong(17, bean.rent_remain_time);
                    ps.setInt(18, bean.is_local_month);
                    ps.setLong(19, bean.cio_id);
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
                 "	`cio_id`  BIGINT(20) NOT NULL AUTO_INCREMENT," +
                 "	`pi_id`  BIGINT(20)," +
                 "	`pd_id`  BIGINT(20)," +
                 "	`ui_id`  BIGINT(20)," +
                 "	`car_code`  VARCHAR(60)," +
                 "	`is_enter`  INT(11)," +
                 "	`in_out`  VARCHAR(60)," +
                 "	`in_out_code`  VARCHAR(60)," +
                 "	`ctime`  DATETIME," +
                 "	`utime`  DATETIME," +
                 "	`area_code`  VARCHAR(60)," +
                 "	`note`  VARCHAR(100)," +
                 "	`car_type`  INT(11)," +
                 "	`car_code_color`  INT(11)," +
                 "	`order_id`  VARCHAR(80)," +
                 "	`out_type`  INT(11)," +
                 "	`is_rent`  INT(11)," +
                 "	`rent_remain_time`  BIGINT(20)," +
                 "	`is_local_month`  INT(11)," +
                 "	PRIMARY KEY (`cio_id`)" +
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
