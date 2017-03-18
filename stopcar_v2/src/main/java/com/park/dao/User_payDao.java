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

//user_pay

@Repository("user_payDao")
public class User_payDao extends BaseDao{

    Logger log = Logger.getLogger(User_payDao.class);



    public  String TABLE = "user_pay";

    public  String TABLENAME = "user_pay";

    public  String TABLEMM(){
        return TABLE + sdfMm.format(new java.util.Date());
    }

    public  String TABLEDD(){
        return TABLE + sdfDd.format(new java.util.Date());
    }

    public javax.sql.DataSource ds;

    public  String[] carrays ={"id","order_id","transaction_id","type","version_code","system_type","return_url","ui_id","ui_nd","money","act_type","ctime","utime","etime","state","ip","referer","subject","note","car_order_id"};
    public  String coulmns ="id,order_id,transaction_id,type,version_code,system_type,return_url,ui_id,ui_nd,money,act_type,ctime,utime,etime,state,ip,referer,subject,note,car_order_id";
    public  String coulmns2 ="order_id,transaction_id,type,version_code,system_type,return_url,ui_id,ui_nd,money,act_type,ctime,utime,etime,state,ip,referer,subject,note,car_order_id";

    //添加数据
    public int insert(User_pay bean) throws SQLException{
        return insert(bean, TABLENAME);
    }

    //添加数据
    public int insert(User_pay bean, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "INSERT INTO "+TABLENAME2+" (order_id,transaction_id,type,version_code,system_type,return_url,ui_id,ui_nd,money,act_type,ctime,utime,etime,state,ip,referer,subject,note,car_order_id) VALUES (:order_id,:transaction_id,:type,:version_code,:system_type,:return_url,:ui_id,:ui_nd,:money,:act_type,:ctime,:utime,:etime,:state,:ip,:referer,:subject,:note,:car_order_id)";
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
    public int insert2(User_pay bean) throws SQLException{
        return insert2(bean, TABLENAME);
    }

    //添加数据
    public int insert2(User_pay bean, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "INSERT INTO "+TABLENAME2+" (id,order_id,transaction_id,type,version_code,system_type,return_url,ui_id,ui_nd,money,act_type,ctime,utime,etime,state,ip,referer,subject,note,car_order_id) VALUES (:id,:order_id,:transaction_id,:type,:version_code,:system_type,:return_url,:ui_id,:ui_nd,:money,:act_type,:ctime,:utime,:etime,:state,:ip,:referer,:subject,:note,:car_order_id)";
            SqlParameterSource ps = new BeanPropertySqlParameterSource(bean);
            return _np.update(sql, ps);
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            throw new SQLException("insert2 is error", e);
        }
    }

    //批量添加数据
    public int[] insert(List<User_pay> beans) throws SQLException{
        return insert(beans, TABLENAME);
    }

    //批量添加数据
    public int[] insert(final List<User_pay> beans, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "INSERT INTO "+TABLENAME2+" (order_id,transaction_id,type,version_code,system_type,return_url,ui_id,ui_nd,money,act_type,ctime,utime,etime,state,ip,referer,subject,note,car_order_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            return _np.getJdbcOperations().batchUpdate(sql, new BatchPreparedStatementSetter() {
                //@Override
                public int getBatchSize() {
                    return beans.size();
                }
                //@Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    User_pay bean = beans.get(i);
                    ps.setString(1, bean.order_id);
                    ps.setString(2, bean.transaction_id);
                    ps.setInt(3, bean.type);
                    ps.setInt(4, bean.version_code);
                    ps.setInt(5, bean.system_type);
                    ps.setString(6, bean.return_url);
                    ps.setLong(7, bean.ui_id);
                    ps.setString(8, bean.ui_nd);
                    ps.setLong(9, bean.money);
                    ps.setInt(10, bean.act_type);
                    ps.setTimestamp(11, new Timestamp(bean.ctime.getTime()));
                    ps.setTimestamp(12, new Timestamp(bean.utime.getTime()));
                    ps.setTimestamp(13, new Timestamp(bean.etime.getTime()));
                    ps.setInt(14, bean.state);
                    ps.setString(15, bean.ip);
                    ps.setString(16, bean.referer);
                    ps.setString(17, bean.subject);
                    ps.setString(18, bean.note);
                    ps.setString(19, bean.car_order_id);
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
    public List<User_pay> selectAll() {
        return selectAll(TABLENAME);
    }

    //查询所有数据
    public List<User_pay> selectAll(String TABLENAME2) {
        String sql;
        try{
            sql = "SELECT id,order_id,transaction_id,type,version_code,system_type,return_url,ui_id,ui_nd,money,act_type,ctime,utime,etime,state,ip,referer,subject,note,car_order_id FROM "+TABLENAME2+" ORDER BY id";
            return _np.getJdbcOperations().query(sql, new BeanPropertyRowMapper<User_pay>(User_pay.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<User_pay>();
        }
    }

    //查询最新数据
    public List<User_pay> selectLast(int num) {
        return selectLast(num, TABLENAME);
    }

    //查询所有数据
    public List<User_pay> selectLast(int num ,String TABLENAME2) {
        String sql;
        try{
            sql = "SELECT id,order_id,transaction_id,type,version_code,system_type,return_url,ui_id,ui_nd,money,act_type,ctime,utime,etime,state,ip,referer,subject,note,car_order_id FROM "+TABLENAME2+" ORDER BY id DESC LIMIT "+num+"" ;
            return _np.getJdbcOperations().query(sql, new BeanPropertyRowMapper<User_pay>(User_pay.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<User_pay>();
        }
    }

    //根据主键查询
    public List<User_pay> selectGtKey(long id) {
        return selectGtKey(id, TABLENAME);
    }

    //根据主键查询
    public List<User_pay> selectGtKey(long id, String TABLENAME2) {
        String sql;
        try{
            sql="SELECT id,order_id,transaction_id,type,version_code,system_type,return_url,ui_id,ui_nd,money,act_type,ctime,utime,etime,state,ip,referer,subject,note,car_order_id FROM "+TABLENAME2+" WHERE id>:id";
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("id", id);
            return _np.query(sql, param, new BeanPropertyRowMapper<User_pay>(User_pay.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<User_pay>();
        }
    }

    //根据主键查询
    public User_pay selectByKey(long id) {
        return selectByKey(id, TABLENAME);
    }

    //根据主键查询
    public User_pay selectByKey(long id, String TABLENAME2) {
        String sql;
        try{
            sql="SELECT id,order_id,transaction_id,type,version_code,system_type,return_url,ui_id,ui_nd,money,act_type,ctime,utime,etime,state,ip,referer,subject,note,car_order_id FROM "+TABLENAME2+" WHERE id=:id";
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("id", id);
            return (User_pay)_np.queryForObject(sql, param, new BeanPropertyRowMapper<User_pay>(User_pay.class));
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
    public List<User_pay> selectByPage(int begin, int num) {
        return selectByPage(begin, num, TABLENAME);
    }

    //分页查询
    public List<User_pay> selectByPage(int begin, int num, String TABLENAME2) {
        try{
            String sql;
            sql = "SELECT id,order_id,transaction_id,type,version_code,system_type,return_url,ui_id,ui_nd,money,act_type,ctime,utime,etime,state,ip,referer,subject,note,car_order_id FROM "+TABLENAME2+" LIMIT "+begin+", "+num+"";
            return _np.getJdbcOperations().query(sql,new BeanPropertyRowMapper<User_pay>(User_pay.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<User_pay>();
        }
    }

    //修改数据
    public int updateByKey(User_pay bean) {
        return updateByKey(bean, TABLENAME);
    }

    //修改数据
    public int updateByKey(User_pay bean, String TABLENAME2) {
        try{
            String sql;
            sql = "UPDATE "+TABLENAME2+" SET order_id=:order_id,transaction_id=:transaction_id,type=:type,version_code=:version_code,system_type=:system_type,return_url=:return_url,ui_id=:ui_id,ui_nd=:ui_nd,money=:money,act_type=:act_type,ctime=:ctime,utime=:utime,etime=:etime,state=:state,ip=:ip,referer=:referer,subject=:subject,note=:note,car_order_id=:car_order_id WHERE id=:id";
            SqlParameterSource ps = new BeanPropertySqlParameterSource(bean);
            return _np.update(sql, ps);
        }catch(Exception e){
            log.error(e);
            return 0;
        }
    }

    //批量修改数据
    public int[] updateByKey (final List<User_pay> beans) throws SQLException{
        return updateByKey(beans, TABLENAME);
    }

    //批量修改数据
    public int[] updateByKey (final List<User_pay> beans, String TABLENAME2) throws SQLException{
        try{
            String sql;
            sql = "UPDATE "+TABLENAME2+" SET order_id=?,transaction_id=?,type=?,version_code=?,system_type=?,return_url=?,ui_id=?,ui_nd=?,money=?,act_type=?,ctime=?,utime=?,etime=?,state=?,ip=?,referer=?,subject=?,note=?,car_order_id=? WHERE id=?";
            return _np.getJdbcOperations().batchUpdate(sql, new BatchPreparedStatementSetter() {
                //@Override
                public int getBatchSize() {
                    return beans.size();
                }
                //@Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    User_pay bean = beans.get(i);
                    ps.setString(1, bean.order_id);
                    ps.setString(2, bean.transaction_id);
                    ps.setInt(3, bean.type);
                    ps.setInt(4, bean.version_code);
                    ps.setInt(5, bean.system_type);
                    ps.setString(6, bean.return_url);
                    ps.setLong(7, bean.ui_id);
                    ps.setString(8, bean.ui_nd);
                    ps.setLong(9, bean.money);
                    ps.setInt(10, bean.act_type);
                    ps.setTimestamp(11, new Timestamp(bean.ctime.getTime()));
                    ps.setTimestamp(12, new Timestamp(bean.utime.getTime()));
                    ps.setTimestamp(13, new Timestamp(bean.etime.getTime()));
                    ps.setInt(14, bean.state);
                    ps.setString(15, bean.ip);
                    ps.setString(16, bean.referer);
                    ps.setString(17, bean.subject);
                    ps.setString(18, bean.note);
                    ps.setString(19, bean.car_order_id);
                    ps.setLong(20, bean.id);
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
                 "	`order_id`  VARCHAR(100)," +
                 "	`transaction_id`  VARCHAR(100)," +
                 "	`type`  INT(11)," +
                 "	`version_code`  INT(11)," +
                 "	`system_type`  INT(11)," +
                 "	`return_url`  TINYTEXT," +
                 "	`ui_id`  BIGINT(20)," +
                 "	`ui_nd`  VARCHAR(100)," +
                 "	`money`  BIGINT(20)," +
                 "	`act_type`  INT(11)," +
                 "	`ctime`  DATETIME," +
                 "	`utime`  DATETIME," +
                 "	`etime`  DATETIME," +
                 "	`state`  INT(11)," +
                 "	`ip`  VARCHAR(100)," +
                 "	`referer`  TINYTEXT," +
                 "	`subject`  VARCHAR(200)," +
                 "	`note`  VARCHAR(100)," +
                 "	`car_order_id`  VARCHAR(100)," +
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
