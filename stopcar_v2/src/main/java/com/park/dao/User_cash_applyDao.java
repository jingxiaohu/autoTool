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

//user_cash_apply

@Repository("user_cash_applyDao")
public class User_cash_applyDao extends BaseDao{

    Logger log = Logger.getLogger(User_cash_applyDao.class);



    public  String TABLE = "user_cash_apply";

    public  String TABLENAME = "user_cash_apply";

    public  String TABLEMM(){
        return TABLE + sdfMm.format(new java.util.Date());
    }

    public  String TABLEDD(){
        return TABLE + sdfDd.format(new java.util.Date());
    }

    public javax.sql.DataSource ds;

    public  String[] carrays ={"id","pu_id","money","ctime","utime","note","state","bank_no","bank_name","bank_sub_name","name","tel"};
    public  String coulmns ="id,pu_id,money,ctime,utime,note,state,bank_no,bank_name,bank_sub_name,name,tel";
    public  String coulmns2 ="pu_id,money,ctime,utime,note,state,bank_no,bank_name,bank_sub_name,name,tel";

    //添加数据
    public int insert(User_cash_apply bean) throws SQLException{
        return insert(bean, TABLENAME);
    }

    //添加数据
    public int insert(User_cash_apply bean, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "INSERT INTO "+TABLENAME2+" (pu_id,money,ctime,utime,note,state,bank_no,bank_name,bank_sub_name,name,tel) VALUES (:pu_id,:money,:ctime,:utime,:note,:state,:bank_no,:bank_name,:bank_sub_name,:name,:tel)";
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
    public int insert2(User_cash_apply bean) throws SQLException{
        return insert2(bean, TABLENAME);
    }

    //添加数据
    public int insert2(User_cash_apply bean, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "INSERT INTO "+TABLENAME2+" (id,pu_id,money,ctime,utime,note,state,bank_no,bank_name,bank_sub_name,name,tel) VALUES (:id,:pu_id,:money,:ctime,:utime,:note,:state,:bank_no,:bank_name,:bank_sub_name,:name,:tel)";
            SqlParameterSource ps = new BeanPropertySqlParameterSource(bean);
            return _np.update(sql, ps);
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            throw new SQLException("insert2 is error", e);
        }
    }

    //批量添加数据
    public int[] insert(List<User_cash_apply> beans) throws SQLException{
        return insert(beans, TABLENAME);
    }

    //批量添加数据
    public int[] insert(final List<User_cash_apply> beans, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "INSERT INTO "+TABLENAME2+" (pu_id,money,ctime,utime,note,state,bank_no,bank_name,bank_sub_name,name,tel) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            return _np.getJdbcOperations().batchUpdate(sql, new BatchPreparedStatementSetter() {
                //@Override
                public int getBatchSize() {
                    return beans.size();
                }
                //@Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    User_cash_apply bean = beans.get(i);
                    ps.setLong(1, bean.pu_id);
                    ps.setInt(2, bean.money);
                    ps.setTimestamp(3, new Timestamp(bean.ctime.getTime()));
                    ps.setTimestamp(4, new Timestamp(bean.utime.getTime()));
                    ps.setString(5, bean.note);
                    ps.setInt(6, bean.state);
                    ps.setString(7, bean.bank_no);
                    ps.setString(8, bean.bank_name);
                    ps.setString(9, bean.bank_sub_name);
                    ps.setString(10, bean.name);
                    ps.setString(11, bean.tel);
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
    public List<User_cash_apply> selectAll() {
        return selectAll(TABLENAME);
    }

    //查询所有数据
    public List<User_cash_apply> selectAll(String TABLENAME2) {
        String sql;
        try{
            sql = "SELECT id,pu_id,money,ctime,utime,note,state,bank_no,bank_name,bank_sub_name,name,tel FROM "+TABLENAME2+" ORDER BY id";
            return _np.getJdbcOperations().query(sql, new BeanPropertyRowMapper<User_cash_apply>(User_cash_apply.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<User_cash_apply>();
        }
    }

    //查询最新数据
    public List<User_cash_apply> selectLast(int num) {
        return selectLast(num, TABLENAME);
    }

    //查询所有数据
    public List<User_cash_apply> selectLast(int num ,String TABLENAME2) {
        String sql;
        try{
            sql = "SELECT id,pu_id,money,ctime,utime,note,state,bank_no,bank_name,bank_sub_name,name,tel FROM "+TABLENAME2+" ORDER BY id DESC LIMIT "+num+"" ;
            return _np.getJdbcOperations().query(sql, new BeanPropertyRowMapper<User_cash_apply>(User_cash_apply.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<User_cash_apply>();
        }
    }

    //根据主键查询
    public List<User_cash_apply> selectGtKey(long id) {
        return selectGtKey(id, TABLENAME);
    }

    //根据主键查询
    public List<User_cash_apply> selectGtKey(long id, String TABLENAME2) {
        String sql;
        try{
            sql="SELECT id,pu_id,money,ctime,utime,note,state,bank_no,bank_name,bank_sub_name,name,tel FROM "+TABLENAME2+" WHERE id>:id";
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("id", id);
            return _np.query(sql, param, new BeanPropertyRowMapper<User_cash_apply>(User_cash_apply.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<User_cash_apply>();
        }
    }

    //根据主键查询
    public User_cash_apply selectByKey(long id) {
        return selectByKey(id, TABLENAME);
    }

    //根据主键查询
    public User_cash_apply selectByKey(long id, String TABLENAME2) {
        String sql;
        try{
            sql="SELECT id,pu_id,money,ctime,utime,note,state,bank_no,bank_name,bank_sub_name,name,tel FROM "+TABLENAME2+" WHERE id=:id";
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("id", id);
            return (User_cash_apply)_np.queryForObject(sql, param, new BeanPropertyRowMapper<User_cash_apply>(User_cash_apply.class));
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
    public List<User_cash_apply> selectByPage(int begin, int num) {
        return selectByPage(begin, num, TABLENAME);
    }

    //分页查询
    public List<User_cash_apply> selectByPage(int begin, int num, String TABLENAME2) {
        try{
            String sql;
            sql = "SELECT id,pu_id,money,ctime,utime,note,state,bank_no,bank_name,bank_sub_name,name,tel FROM "+TABLENAME2+" LIMIT "+begin+", "+num+"";
            return _np.getJdbcOperations().query(sql,new BeanPropertyRowMapper<User_cash_apply>(User_cash_apply.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<User_cash_apply>();
        }
    }

    //修改数据
    public int updateByKey(User_cash_apply bean) {
        return updateByKey(bean, TABLENAME);
    }

    //修改数据
    public int updateByKey(User_cash_apply bean, String TABLENAME2) {
        try{
            String sql;
            sql = "UPDATE "+TABLENAME2+" SET pu_id=:pu_id,money=:money,ctime=:ctime,utime=:utime,note=:note,state=:state,bank_no=:bank_no,bank_name=:bank_name,bank_sub_name=:bank_sub_name,name=:name,tel=:tel WHERE id=:id";
            SqlParameterSource ps = new BeanPropertySqlParameterSource(bean);
            return _np.update(sql, ps);
        }catch(Exception e){
            log.error(e);
            return 0;
        }
    }

    //批量修改数据
    public int[] updateByKey (final List<User_cash_apply> beans) throws SQLException{
        return updateByKey(beans, TABLENAME);
    }

    //批量修改数据
    public int[] updateByKey (final List<User_cash_apply> beans, String TABLENAME2) throws SQLException{
        try{
            String sql;
            sql = "UPDATE "+TABLENAME2+" SET pu_id=?,money=?,ctime=?,utime=?,note=?,state=?,bank_no=?,bank_name=?,bank_sub_name=?,name=?,tel=? WHERE id=?";
            return _np.getJdbcOperations().batchUpdate(sql, new BatchPreparedStatementSetter() {
                //@Override
                public int getBatchSize() {
                    return beans.size();
                }
                //@Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    User_cash_apply bean = beans.get(i);
                    ps.setLong(1, bean.pu_id);
                    ps.setInt(2, bean.money);
                    ps.setTimestamp(3, new Timestamp(bean.ctime.getTime()));
                    ps.setTimestamp(4, new Timestamp(bean.utime.getTime()));
                    ps.setString(5, bean.note);
                    ps.setInt(6, bean.state);
                    ps.setString(7, bean.bank_no);
                    ps.setString(8, bean.bank_name);
                    ps.setString(9, bean.bank_sub_name);
                    ps.setString(10, bean.name);
                    ps.setString(11, bean.tel);
                    ps.setLong(12, bean.id);
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
                 "	`pu_id`  BIGINT(20)," +
                 "	`money`  INT(11)," +
                 "	`ctime`  DATETIME," +
                 "	`utime`  DATETIME," +
                 "	`note`  TINYTEXT," +
                 "	`state`  INT(11)," +
                 "	`bank_no`  VARCHAR(80)," +
                 "	`bank_name`  VARCHAR(80)," +
                 "	`bank_sub_name`  VARCHAR(200)," +
                 "	`name`  VARCHAR(100)," +
                 "	`tel`  VARCHAR(11)," +
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
