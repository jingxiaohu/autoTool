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

//park_userinfo

@Repository("park_userinfoDao")
public class Park_userinfoDao extends BaseDao{

    Logger log = Logger.getLogger(Park_userinfoDao.class);



    public  String TABLE = "park_userinfo";

    public  String TABLENAME = "park_userinfo";

    public  String TABLEMM(){
        return TABLE + sdfMm.format(new java.util.Date());
    }

    public  String TABLEDD(){
        return TABLE + sdfDd.format(new java.util.Date());
    }

    public javax.sql.DataSource ds;

    public  String[] carrays ={"pu_id","nd","name","tel","bank_no","bank_name","bank_sub_name","pda_num","money","ctime","utime","loginname","password","note","money_online","money_offline"};
    public  String coulmns ="pu_id,nd,name,tel,bank_no,bank_name,bank_sub_name,pda_num,money,ctime,utime,loginname,password,note,money_online,money_offline";
    public  String coulmns2 ="nd,name,tel,bank_no,bank_name,bank_sub_name,pda_num,money,ctime,utime,loginname,password,note,money_online,money_offline";

    //添加数据
    public int insert(Park_userinfo bean) throws SQLException{
        return insert(bean, TABLENAME);
    }

    //添加数据
    public int insert(Park_userinfo bean, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "INSERT INTO "+TABLENAME2+" (nd,name,tel,bank_no,bank_name,bank_sub_name,pda_num,money,ctime,utime,loginname,password,note,money_online,money_offline) VALUES (:nd,:name,:tel,:bank_no,:bank_name,:bank_sub_name,:pda_num,:money,:ctime,:utime,:loginname,:password,:note,:money_online,:money_offline)";
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
    public int insert2(Park_userinfo bean) throws SQLException{
        return insert2(bean, TABLENAME);
    }

    //添加数据
    public int insert2(Park_userinfo bean, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "INSERT INTO "+TABLENAME2+" (pu_id,nd,name,tel,bank_no,bank_name,bank_sub_name,pda_num,money,ctime,utime,loginname,password,note,money_online,money_offline) VALUES (:pu_id,:nd,:name,:tel,:bank_no,:bank_name,:bank_sub_name,:pda_num,:money,:ctime,:utime,:loginname,:password,:note,:money_online,:money_offline)";
            SqlParameterSource ps = new BeanPropertySqlParameterSource(bean);
            return _np.update(sql, ps);
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            throw new SQLException("insert2 is error", e);
        }
    }

    //批量添加数据
    public int[] insert(List<Park_userinfo> beans) throws SQLException{
        return insert(beans, TABLENAME);
    }

    //批量添加数据
    public int[] insert(final List<Park_userinfo> beans, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "INSERT INTO "+TABLENAME2+" (nd,name,tel,bank_no,bank_name,bank_sub_name,pda_num,money,ctime,utime,loginname,password,note,money_online,money_offline) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            return _np.getJdbcOperations().batchUpdate(sql, new BatchPreparedStatementSetter() {
                //@Override
                public int getBatchSize() {
                    return beans.size();
                }
                //@Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Park_userinfo bean = beans.get(i);
                    ps.setString(1, bean.nd);
                    ps.setString(2, bean.name);
                    ps.setString(3, bean.tel);
                    ps.setString(4, bean.bank_no);
                    ps.setString(5, bean.bank_name);
                    ps.setString(6, bean.bank_sub_name);
                    ps.setInt(7, bean.pda_num);
                    ps.setInt(8, bean.money);
                    ps.setTimestamp(9, new Timestamp(bean.ctime.getTime()));
                    ps.setTimestamp(10, new Timestamp(bean.utime.getTime()));
                    ps.setString(11, bean.loginname);
                    ps.setString(12, bean.password);
                    ps.setString(13, bean.note);
                    ps.setLong(14, bean.money_online);
                    ps.setLong(15, bean.money_offline);
                }
            });
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            throw new SQLException("insert is error", e);
        }
    }

    //删除单条数据
    public int deleteByKey(long pu_id) throws SQLException{
        return deleteByKey(pu_id, TABLENAME);
    }

    //删除单条数据
    public int deleteByKey(long pu_id, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "DELETE FROM "+TABLENAME2+" WHERE pu_id=:pu_id";
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("pu_id", pu_id);
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
            sql = "DELETE FROM "+TABLENAME2+" WHERE pu_id=?";
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
    public List<Park_userinfo> selectAll() {
        return selectAll(TABLENAME);
    }

    //查询所有数据
    public List<Park_userinfo> selectAll(String TABLENAME2) {
        String sql;
        try{
            sql = "SELECT pu_id,nd,name,tel,bank_no,bank_name,bank_sub_name,pda_num,money,ctime,utime,loginname,password,note,money_online,money_offline FROM "+TABLENAME2+" ORDER BY pu_id";
            return _np.getJdbcOperations().query(sql, new BeanPropertyRowMapper<Park_userinfo>(Park_userinfo.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<Park_userinfo>();
        }
    }

    //查询最新数据
    public List<Park_userinfo> selectLast(int num) {
        return selectLast(num, TABLENAME);
    }

    //查询所有数据
    public List<Park_userinfo> selectLast(int num ,String TABLENAME2) {
        String sql;
        try{
            sql = "SELECT pu_id,nd,name,tel,bank_no,bank_name,bank_sub_name,pda_num,money,ctime,utime,loginname,password,note,money_online,money_offline FROM "+TABLENAME2+" ORDER BY pu_id DESC LIMIT "+num+"" ;
            return _np.getJdbcOperations().query(sql, new BeanPropertyRowMapper<Park_userinfo>(Park_userinfo.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<Park_userinfo>();
        }
    }

    //根据主键查询
    public List<Park_userinfo> selectGtKey(long pu_id) {
        return selectGtKey(pu_id, TABLENAME);
    }

    //根据主键查询
    public List<Park_userinfo> selectGtKey(long pu_id, String TABLENAME2) {
        String sql;
        try{
            sql="SELECT pu_id,nd,name,tel,bank_no,bank_name,bank_sub_name,pda_num,money,ctime,utime,loginname,password,note,money_online,money_offline FROM "+TABLENAME2+" WHERE pu_id>:pu_id";
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("pu_id", pu_id);
            return _np.query(sql, param, new BeanPropertyRowMapper<Park_userinfo>(Park_userinfo.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<Park_userinfo>();
        }
    }

    //根据主键查询
    public Park_userinfo selectByKey(long pu_id) {
        return selectByKey(pu_id, TABLENAME);
    }

    //根据主键查询
    public Park_userinfo selectByKey(long pu_id, String TABLENAME2) {
        String sql;
        try{
            sql="SELECT pu_id,nd,name,tel,bank_no,bank_name,bank_sub_name,pda_num,money,ctime,utime,loginname,password,note,money_online,money_offline FROM "+TABLENAME2+" WHERE pu_id=:pu_id";
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("pu_id", pu_id);
            return (Park_userinfo)_np.queryForObject(sql, param, new BeanPropertyRowMapper<Park_userinfo>(Park_userinfo.class));
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
    public List<Park_userinfo> selectByPage(int begin, int num) {
        return selectByPage(begin, num, TABLENAME);
    }

    //分页查询
    public List<Park_userinfo> selectByPage(int begin, int num, String TABLENAME2) {
        try{
            String sql;
            sql = "SELECT pu_id,nd,name,tel,bank_no,bank_name,bank_sub_name,pda_num,money,ctime,utime,loginname,password,note,money_online,money_offline FROM "+TABLENAME2+" LIMIT "+begin+", "+num+"";
            return _np.getJdbcOperations().query(sql,new BeanPropertyRowMapper<Park_userinfo>(Park_userinfo.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<Park_userinfo>();
        }
    }

    //修改数据
    public int updateByKey(Park_userinfo bean) {
        return updateByKey(bean, TABLENAME);
    }

    //修改数据
    public int updateByKey(Park_userinfo bean, String TABLENAME2) {
        try{
            String sql;
            sql = "UPDATE "+TABLENAME2+" SET nd=:nd,name=:name,tel=:tel,bank_no=:bank_no,bank_name=:bank_name,bank_sub_name=:bank_sub_name,pda_num=:pda_num,money=:money,ctime=:ctime,utime=:utime,loginname=:loginname,password=:password,note=:note,money_online=:money_online,money_offline=:money_offline WHERE pu_id=:pu_id";
            SqlParameterSource ps = new BeanPropertySqlParameterSource(bean);
            return _np.update(sql, ps);
        }catch(Exception e){
            log.error(e);
            return 0;
        }
    }

    //批量修改数据
    public int[] updateByKey (final List<Park_userinfo> beans) throws SQLException{
        return updateByKey(beans, TABLENAME);
    }

    //批量修改数据
    public int[] updateByKey (final List<Park_userinfo> beans, String TABLENAME2) throws SQLException{
        try{
            String sql;
            sql = "UPDATE "+TABLENAME2+" SET nd=?,name=?,tel=?,bank_no=?,bank_name=?,bank_sub_name=?,pda_num=?,money=?,ctime=?,utime=?,loginname=?,password=?,note=?,money_online=?,money_offline=? WHERE pu_id=?";
            return _np.getJdbcOperations().batchUpdate(sql, new BatchPreparedStatementSetter() {
                //@Override
                public int getBatchSize() {
                    return beans.size();
                }
                //@Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Park_userinfo bean = beans.get(i);
                    ps.setString(1, bean.nd);
                    ps.setString(2, bean.name);
                    ps.setString(3, bean.tel);
                    ps.setString(4, bean.bank_no);
                    ps.setString(5, bean.bank_name);
                    ps.setString(6, bean.bank_sub_name);
                    ps.setInt(7, bean.pda_num);
                    ps.setInt(8, bean.money);
                    ps.setTimestamp(9, new Timestamp(bean.ctime.getTime()));
                    ps.setTimestamp(10, new Timestamp(bean.utime.getTime()));
                    ps.setString(11, bean.loginname);
                    ps.setString(12, bean.password);
                    ps.setString(13, bean.note);
                    ps.setLong(14, bean.money_online);
                    ps.setLong(15, bean.money_offline);
                    ps.setLong(16, bean.pu_id);
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
                 "	`pu_id`  BIGINT(20) NOT NULL AUTO_INCREMENT," +
                 "	`nd`  VARCHAR(100)," +
                 "	`name`  VARCHAR(100)," +
                 "	`tel`  VARCHAR(11)," +
                 "	`bank_no`  VARCHAR(60)," +
                 "	`bank_name`  VARCHAR(150)," +
                 "	`bank_sub_name`  TINYTEXT," +
                 "	`pda_num`  INT(11)," +
                 "	`money`  INT(11)," +
                 "	`ctime`  DATETIME," +
                 "	`utime`  DATETIME," +
                 "	`loginname`  VARCHAR(60)," +
                 "	`password`  VARCHAR(60)," +
                 "	`note`  TINYTEXT," +
                 "	`money_online`  BIGINT(20)," +
                 "	`money_offline`  BIGINT(20)," +
                 "	PRIMARY KEY (`pu_id`)" +
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
