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

//park_heartbeat

@Repository("park_heartbeatDao")
public class Park_heartbeatDao extends BaseDao{

    Logger log = Logger.getLogger(Park_heartbeatDao.class);



    public  String TABLE = "park_heartbeat";

    public  String TABLENAME = "park_heartbeat";

    public  String TABLEMM(){
        return TABLE + sdfMm.format(new java.util.Date());
    }

    public  String TABLEDD(){
        return TABLE + sdfDd.format(new java.util.Date());
    }

    public javax.sql.DataSource ds;

    public  String[] carrays ={"id","pi_id","area_code","num","total","ctime","ctime_num","is_rent","note","carport_yet","carport_space","carport_total","moth_car_num","moth_car_num_space","time_car_num","time_car_num_space","expect_car_num"};
    public  String coulmns ="id,pi_id,area_code,num,total,ctime,ctime_num,is_rent,note,carport_yet,carport_space,carport_total,moth_car_num,moth_car_num_space,time_car_num,time_car_num_space,expect_car_num";
    public  String coulmns2 ="pi_id,area_code,num,total,ctime,ctime_num,is_rent,note,carport_yet,carport_space,carport_total,moth_car_num,moth_car_num_space,time_car_num,time_car_num_space,expect_car_num";

    //添加数据
    public int insert(Park_heartbeat bean) throws SQLException{
        return insert(bean, TABLENAME);
    }

    //添加数据
    public int insert(Park_heartbeat bean, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "INSERT INTO "+TABLENAME2+" (pi_id,area_code,num,total,ctime,ctime_num,is_rent,note,carport_yet,carport_space,carport_total,moth_car_num,moth_car_num_space,time_car_num,time_car_num_space,expect_car_num) VALUES (:pi_id,:area_code,:num,:total,:ctime,:ctime_num,:is_rent,:note,:carport_yet,:carport_space,:carport_total,:moth_car_num,:moth_car_num_space,:time_car_num,:time_car_num_space,:expect_car_num)";
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
    public int insert2(Park_heartbeat bean) throws SQLException{
        return insert2(bean, TABLENAME);
    }

    //添加数据
    public int insert2(Park_heartbeat bean, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "INSERT INTO "+TABLENAME2+" (id,pi_id,area_code,num,total,ctime,ctime_num,is_rent,note,carport_yet,carport_space,carport_total,moth_car_num,moth_car_num_space,time_car_num,time_car_num_space,expect_car_num) VALUES (:id,:pi_id,:area_code,:num,:total,:ctime,:ctime_num,:is_rent,:note,:carport_yet,:carport_space,:carport_total,:moth_car_num,:moth_car_num_space,:time_car_num,:time_car_num_space,:expect_car_num)";
            SqlParameterSource ps = new BeanPropertySqlParameterSource(bean);
            return _np.update(sql, ps);
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            throw new SQLException("insert2 is error", e);
        }
    }

    //批量添加数据
    public int[] insert(List<Park_heartbeat> beans) throws SQLException{
        return insert(beans, TABLENAME);
    }

    //批量添加数据
    public int[] insert(final List<Park_heartbeat> beans, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "INSERT INTO "+TABLENAME2+" (pi_id,area_code,num,total,ctime,ctime_num,is_rent,note,carport_yet,carport_space,carport_total,moth_car_num,moth_car_num_space,time_car_num,time_car_num_space,expect_car_num) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            return _np.getJdbcOperations().batchUpdate(sql, new BatchPreparedStatementSetter() {
                //@Override
                public int getBatchSize() {
                    return beans.size();
                }
                //@Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Park_heartbeat bean = beans.get(i);
                    ps.setLong(1, bean.pi_id);
                    ps.setString(2, bean.area_code);
                    ps.setInt(3, bean.num);
                    ps.setInt(4, bean.total);
                    ps.setTimestamp(5, new Timestamp(bean.ctime.getTime()));
                    ps.setLong(6, bean.ctime_num);
                    ps.setInt(7, bean.is_rent);
                    ps.setString(8, bean.note);
                    ps.setInt(9, bean.carport_yet);
                    ps.setInt(10, bean.carport_space);
                    ps.setInt(11, bean.carport_total);
                    ps.setInt(12, bean.moth_car_num);
                    ps.setInt(13, bean.moth_car_num_space);
                    ps.setInt(14, bean.time_car_num);
                    ps.setInt(15, bean.time_car_num_space);
                    ps.setInt(16, bean.expect_car_num);
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
    public List<Park_heartbeat> selectAll() {
        return selectAll(TABLENAME);
    }

    //查询所有数据
    public List<Park_heartbeat> selectAll(String TABLENAME2) {
        String sql;
        try{
            sql = "SELECT id,pi_id,area_code,num,total,ctime,ctime_num,is_rent,note,carport_yet,carport_space,carport_total,moth_car_num,moth_car_num_space,time_car_num,time_car_num_space,expect_car_num FROM "+TABLENAME2+" ORDER BY id";
            return _np.getJdbcOperations().query(sql, new BeanPropertyRowMapper<Park_heartbeat>(Park_heartbeat.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<Park_heartbeat>();
        }
    }

    //查询最新数据
    public List<Park_heartbeat> selectLast(int num) {
        return selectLast(num, TABLENAME);
    }

    //查询所有数据
    public List<Park_heartbeat> selectLast(int num ,String TABLENAME2) {
        String sql;
        try{
            sql = "SELECT id,pi_id,area_code,num,total,ctime,ctime_num,is_rent,note,carport_yet,carport_space,carport_total,moth_car_num,moth_car_num_space,time_car_num,time_car_num_space,expect_car_num FROM "+TABLENAME2+" ORDER BY id DESC LIMIT "+num+"" ;
            return _np.getJdbcOperations().query(sql, new BeanPropertyRowMapper<Park_heartbeat>(Park_heartbeat.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<Park_heartbeat>();
        }
    }

    //根据主键查询
    public List<Park_heartbeat> selectGtKey(long id) {
        return selectGtKey(id, TABLENAME);
    }

    //根据主键查询
    public List<Park_heartbeat> selectGtKey(long id, String TABLENAME2) {
        String sql;
        try{
            sql="SELECT id,pi_id,area_code,num,total,ctime,ctime_num,is_rent,note,carport_yet,carport_space,carport_total,moth_car_num,moth_car_num_space,time_car_num,time_car_num_space,expect_car_num FROM "+TABLENAME2+" WHERE id>:id";
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("id", id);
            return _np.query(sql, param, new BeanPropertyRowMapper<Park_heartbeat>(Park_heartbeat.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<Park_heartbeat>();
        }
    }

    //根据主键查询
    public Park_heartbeat selectByKey(long id) {
        return selectByKey(id, TABLENAME);
    }

    //根据主键查询
    public Park_heartbeat selectByKey(long id, String TABLENAME2) {
        String sql;
        try{
            sql="SELECT id,pi_id,area_code,num,total,ctime,ctime_num,is_rent,note,carport_yet,carport_space,carport_total,moth_car_num,moth_car_num_space,time_car_num,time_car_num_space,expect_car_num FROM "+TABLENAME2+" WHERE id=:id";
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("id", id);
            return (Park_heartbeat)_np.queryForObject(sql, param, new BeanPropertyRowMapper<Park_heartbeat>(Park_heartbeat.class));
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
    public List<Park_heartbeat> selectByPage(int begin, int num) {
        return selectByPage(begin, num, TABLENAME);
    }

    //分页查询
    public List<Park_heartbeat> selectByPage(int begin, int num, String TABLENAME2) {
        try{
            String sql;
            sql = "SELECT id,pi_id,area_code,num,total,ctime,ctime_num,is_rent,note,carport_yet,carport_space,carport_total,moth_car_num,moth_car_num_space,time_car_num,time_car_num_space,expect_car_num FROM "+TABLENAME2+" LIMIT "+begin+", "+num+"";
            return _np.getJdbcOperations().query(sql,new BeanPropertyRowMapper<Park_heartbeat>(Park_heartbeat.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<Park_heartbeat>();
        }
    }

    //修改数据
    public int updateByKey(Park_heartbeat bean) {
        return updateByKey(bean, TABLENAME);
    }

    //修改数据
    public int updateByKey(Park_heartbeat bean, String TABLENAME2) {
        try{
            String sql;
            sql = "UPDATE "+TABLENAME2+" SET pi_id=:pi_id,area_code=:area_code,num=:num,total=:total,ctime=:ctime,ctime_num=:ctime_num,is_rent=:is_rent,note=:note,carport_yet=:carport_yet,carport_space=:carport_space,carport_total=:carport_total,moth_car_num=:moth_car_num,moth_car_num_space=:moth_car_num_space,time_car_num=:time_car_num,time_car_num_space=:time_car_num_space,expect_car_num=:expect_car_num WHERE id=:id";
            SqlParameterSource ps = new BeanPropertySqlParameterSource(bean);
            return _np.update(sql, ps);
        }catch(Exception e){
            log.error(e);
            return 0;
        }
    }

    //批量修改数据
    public int[] updateByKey (final List<Park_heartbeat> beans) throws SQLException{
        return updateByKey(beans, TABLENAME);
    }

    //批量修改数据
    public int[] updateByKey (final List<Park_heartbeat> beans, String TABLENAME2) throws SQLException{
        try{
            String sql;
            sql = "UPDATE "+TABLENAME2+" SET pi_id=?,area_code=?,num=?,total=?,ctime=?,ctime_num=?,is_rent=?,note=?,carport_yet=?,carport_space=?,carport_total=?,moth_car_num=?,moth_car_num_space=?,time_car_num=?,time_car_num_space=?,expect_car_num=? WHERE id=?";
            return _np.getJdbcOperations().batchUpdate(sql, new BatchPreparedStatementSetter() {
                //@Override
                public int getBatchSize() {
                    return beans.size();
                }
                //@Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Park_heartbeat bean = beans.get(i);
                    ps.setLong(1, bean.pi_id);
                    ps.setString(2, bean.area_code);
                    ps.setInt(3, bean.num);
                    ps.setInt(4, bean.total);
                    ps.setTimestamp(5, new Timestamp(bean.ctime.getTime()));
                    ps.setLong(6, bean.ctime_num);
                    ps.setInt(7, bean.is_rent);
                    ps.setString(8, bean.note);
                    ps.setInt(9, bean.carport_yet);
                    ps.setInt(10, bean.carport_space);
                    ps.setInt(11, bean.carport_total);
                    ps.setInt(12, bean.moth_car_num);
                    ps.setInt(13, bean.moth_car_num_space);
                    ps.setInt(14, bean.time_car_num);
                    ps.setInt(15, bean.time_car_num_space);
                    ps.setInt(16, bean.expect_car_num);
                    ps.setLong(17, bean.id);
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
                 "	`pi_id`  BIGINT(20)," +
                 "	`area_code`  VARCHAR(30)," +
                 "	`num`  INT(11)," +
                 "	`total`  INT(11)," +
                 "	`ctime`  DATETIME," +
                 "	`ctime_num`  BIGINT(20)," +
                 "	`is_rent`  INT(11)," +
                 "	`note`  VARCHAR(60)," +
                 "	`carport_yet`  INT(11)," +
                 "	`carport_space`  INT(11)," +
                 "	`carport_total`  INT(11)," +
                 "	`moth_car_num`  INT(11)," +
                 "	`moth_car_num_space`  INT(11)," +
                 "	`time_car_num`  INT(11)," +
                 "	`time_car_num_space`  INT(11)," +
                 "	`expect_car_num`  INT(11)," +
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