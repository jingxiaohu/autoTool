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

//user_info

@Repository("user_infoDao")
public class User_infoDao extends BaseDao{

    Logger log = Logger.getLogger(User_infoDao.class);



    public  String TABLE = "user_info";

    public  String TABLENAME = "user_info";

    public  String TABLEMM(){
        return TABLE + sdfMm.format(new java.util.Date());
    }

    public  String TABLEDD(){
        return TABLE + sdfDd.format(new java.util.Date());
    }

    public javax.sql.DataSource ds;

    public  String[] carrays ={"ui_id","uuid","ui_tel","ui_password","ui_nickname","ui_avatar","ui_sex","driving_licence","ui_name","ui_address","bind_tel","ui_vc","ui_rmb","coupon_num","ui_state","ui_autopay","pay_source","ui_level","ui_score","ui_mood","ui_intro","ui_age","ui_token","ui_email","ui_high","ui_degree","ctime","utime","ui_flag","ui_online","ui_integrity","note"};
    public  String coulmns ="ui_id,uuid,ui_tel,ui_password,ui_nickname,ui_avatar,ui_sex,driving_licence,ui_name,ui_address,bind_tel,ui_vc,ui_rmb,coupon_num,ui_state,ui_autopay,pay_source,ui_level,ui_score,ui_mood,ui_intro,ui_age,ui_token,ui_email,ui_high,ui_degree,ctime,utime,ui_flag,ui_online,ui_integrity,note";
    public  String coulmns2 ="uuid,ui_tel,ui_password,ui_nickname,ui_avatar,ui_sex,driving_licence,ui_name,ui_address,bind_tel,ui_vc,ui_rmb,coupon_num,ui_state,ui_autopay,pay_source,ui_level,ui_score,ui_mood,ui_intro,ui_age,ui_token,ui_email,ui_high,ui_degree,ctime,utime,ui_flag,ui_online,ui_integrity,note";

    //添加数据
    public int insert(User_info bean) throws SQLException{
        return insert(bean, TABLENAME);
    }

    //添加数据
    public int insert(User_info bean, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "INSERT INTO "+TABLENAME2+" (uuid,ui_tel,ui_password,ui_nickname,ui_avatar,ui_sex,driving_licence,ui_name,ui_address,bind_tel,ui_vc,ui_rmb,coupon_num,ui_state,ui_autopay,pay_source,ui_level,ui_score,ui_mood,ui_intro,ui_age,ui_token,ui_email,ui_high,ui_degree,ctime,utime,ui_flag,ui_online,ui_integrity,note) VALUES (:uuid,:ui_tel,:ui_password,:ui_nickname,:ui_avatar,:ui_sex,:driving_licence,:ui_name,:ui_address,:bind_tel,:ui_vc,:ui_rmb,:coupon_num,:ui_state,:ui_autopay,:pay_source,:ui_level,:ui_score,:ui_mood,:ui_intro,:ui_age,:ui_token,:ui_email,:ui_high,:ui_degree,:ctime,:utime,:ui_flag,:ui_online,:ui_integrity,:note)";
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
    public int insert2(User_info bean) throws SQLException{
        return insert2(bean, TABLENAME);
    }

    //添加数据
    public int insert2(User_info bean, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "INSERT INTO "+TABLENAME2+" (ui_id,uuid,ui_tel,ui_password,ui_nickname,ui_avatar,ui_sex,driving_licence,ui_name,ui_address,bind_tel,ui_vc,ui_rmb,coupon_num,ui_state,ui_autopay,pay_source,ui_level,ui_score,ui_mood,ui_intro,ui_age,ui_token,ui_email,ui_high,ui_degree,ctime,utime,ui_flag,ui_online,ui_integrity,note) VALUES (:ui_id,:uuid,:ui_tel,:ui_password,:ui_nickname,:ui_avatar,:ui_sex,:driving_licence,:ui_name,:ui_address,:bind_tel,:ui_vc,:ui_rmb,:coupon_num,:ui_state,:ui_autopay,:pay_source,:ui_level,:ui_score,:ui_mood,:ui_intro,:ui_age,:ui_token,:ui_email,:ui_high,:ui_degree,:ctime,:utime,:ui_flag,:ui_online,:ui_integrity,:note)";
            SqlParameterSource ps = new BeanPropertySqlParameterSource(bean);
            return _np.update(sql, ps);
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            throw new SQLException("insert2 is error", e);
        }
    }

    //批量添加数据
    public int[] insert(List<User_info> beans) throws SQLException{
        return insert(beans, TABLENAME);
    }

    //批量添加数据
    public int[] insert(final List<User_info> beans, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "INSERT INTO "+TABLENAME2+" (uuid,ui_tel,ui_password,ui_nickname,ui_avatar,ui_sex,driving_licence,ui_name,ui_address,bind_tel,ui_vc,ui_rmb,coupon_num,ui_state,ui_autopay,pay_source,ui_level,ui_score,ui_mood,ui_intro,ui_age,ui_token,ui_email,ui_high,ui_degree,ctime,utime,ui_flag,ui_online,ui_integrity,note) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            return _np.getJdbcOperations().batchUpdate(sql, new BatchPreparedStatementSetter() {
                //@Override
                public int getBatchSize() {
                    return beans.size();
                }
                //@Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    User_info bean = beans.get(i);
                    ps.setString(1, bean.uuid);
                    ps.setString(2, bean.ui_tel);
                    ps.setString(3, bean.ui_password);
                    ps.setString(4, bean.ui_nickname);
                    ps.setString(5, bean.ui_avatar);
                    ps.setString(6, bean.ui_sex);
                    ps.setString(7, bean.driving_licence);
                    ps.setString(8, bean.ui_name);
                    ps.setString(9, bean.ui_address);
                    ps.setString(10, bean.bind_tel);
                    ps.setLong(11, bean.ui_vc);
                    ps.setLong(12, bean.ui_rmb);
                    ps.setLong(13, bean.coupon_num);
                    ps.setInt(14, bean.ui_state);
                    ps.setInt(15, bean.ui_autopay);
                    ps.setInt(16, bean.pay_source);
                    ps.setInt(17, bean.ui_level);
                    ps.setLong(18, bean.ui_score);
                    ps.setString(19, bean.ui_mood);
                    ps.setString(20, bean.ui_intro);
                    ps.setInt(21, bean.ui_age);
                    ps.setString(22, bean.ui_token);
                    ps.setString(23, bean.ui_email);
                    ps.setInt(24, bean.ui_high);
                    ps.setString(25, bean.ui_degree);
                    ps.setTimestamp(26, new Timestamp(bean.ctime.getTime()));
                    ps.setTimestamp(27, new Timestamp(bean.utime.getTime()));
                    ps.setInt(28, bean.ui_flag);
                    ps.setLong(29, bean.ui_online);
                    ps.setInt(30, bean.ui_integrity);
                    ps.setString(31, bean.note);
                }
            });
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            throw new SQLException("insert is error", e);
        }
    }

    //删除单条数据
    public int deleteByKey(long ui_id) throws SQLException{
        return deleteByKey(ui_id, TABLENAME);
    }

    //删除单条数据
    public int deleteByKey(long ui_id, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "DELETE FROM "+TABLENAME2+" WHERE ui_id=:ui_id";
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("ui_id", ui_id);
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
            sql = "DELETE FROM "+TABLENAME2+" WHERE ui_id=?";
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
    public List<User_info> selectAll() {
        return selectAll(TABLENAME);
    }

    //查询所有数据
    public List<User_info> selectAll(String TABLENAME2) {
        String sql;
        try{
            sql = "SELECT ui_id,uuid,ui_tel,ui_password,ui_nickname,ui_avatar,ui_sex,driving_licence,ui_name,ui_address,bind_tel,ui_vc,ui_rmb,coupon_num,ui_state,ui_autopay,pay_source,ui_level,ui_score,ui_mood,ui_intro,ui_age,ui_token,ui_email,ui_high,ui_degree,ctime,utime,ui_flag,ui_online,ui_integrity,note FROM "+TABLENAME2+" ORDER BY ui_id";
            return _np.getJdbcOperations().query(sql, new BeanPropertyRowMapper<User_info>(User_info.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<User_info>();
        }
    }

    //查询最新数据
    public List<User_info> selectLast(int num) {
        return selectLast(num, TABLENAME);
    }

    //查询所有数据
    public List<User_info> selectLast(int num ,String TABLENAME2) {
        String sql;
        try{
            sql = "SELECT ui_id,uuid,ui_tel,ui_password,ui_nickname,ui_avatar,ui_sex,driving_licence,ui_name,ui_address,bind_tel,ui_vc,ui_rmb,coupon_num,ui_state,ui_autopay,pay_source,ui_level,ui_score,ui_mood,ui_intro,ui_age,ui_token,ui_email,ui_high,ui_degree,ctime,utime,ui_flag,ui_online,ui_integrity,note FROM "+TABLENAME2+" ORDER BY ui_id DESC LIMIT "+num+"" ;
            return _np.getJdbcOperations().query(sql, new BeanPropertyRowMapper<User_info>(User_info.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<User_info>();
        }
    }

    //根据主键查询
    public List<User_info> selectGtKey(long ui_id) {
        return selectGtKey(ui_id, TABLENAME);
    }

    //根据主键查询
    public List<User_info> selectGtKey(long ui_id, String TABLENAME2) {
        String sql;
        try{
            sql="SELECT ui_id,uuid,ui_tel,ui_password,ui_nickname,ui_avatar,ui_sex,driving_licence,ui_name,ui_address,bind_tel,ui_vc,ui_rmb,coupon_num,ui_state,ui_autopay,pay_source,ui_level,ui_score,ui_mood,ui_intro,ui_age,ui_token,ui_email,ui_high,ui_degree,ctime,utime,ui_flag,ui_online,ui_integrity,note FROM "+TABLENAME2+" WHERE ui_id>:ui_id";
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("ui_id", ui_id);
            return _np.query(sql, param, new BeanPropertyRowMapper<User_info>(User_info.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<User_info>();
        }
    }

    //根据主键查询
    public User_info selectByKey(long ui_id) {
        return selectByKey(ui_id, TABLENAME);
    }

    //根据主键查询
    public User_info selectByKey(long ui_id, String TABLENAME2) {
        String sql;
        try{
            sql="SELECT ui_id,uuid,ui_tel,ui_password,ui_nickname,ui_avatar,ui_sex,driving_licence,ui_name,ui_address,bind_tel,ui_vc,ui_rmb,coupon_num,ui_state,ui_autopay,pay_source,ui_level,ui_score,ui_mood,ui_intro,ui_age,ui_token,ui_email,ui_high,ui_degree,ctime,utime,ui_flag,ui_online,ui_integrity,note FROM "+TABLENAME2+" WHERE ui_id=:ui_id";
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("ui_id", ui_id);
            return (User_info)_np.queryForObject(sql, param, new BeanPropertyRowMapper<User_info>(User_info.class));
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
    public List<User_info> selectByPage(int begin, int num) {
        return selectByPage(begin, num, TABLENAME);
    }

    //分页查询
    public List<User_info> selectByPage(int begin, int num, String TABLENAME2) {
        try{
            String sql;
            sql = "SELECT ui_id,uuid,ui_tel,ui_password,ui_nickname,ui_avatar,ui_sex,driving_licence,ui_name,ui_address,bind_tel,ui_vc,ui_rmb,coupon_num,ui_state,ui_autopay,pay_source,ui_level,ui_score,ui_mood,ui_intro,ui_age,ui_token,ui_email,ui_high,ui_degree,ctime,utime,ui_flag,ui_online,ui_integrity,note FROM "+TABLENAME2+" LIMIT "+begin+", "+num+"";
            return _np.getJdbcOperations().query(sql,new BeanPropertyRowMapper<User_info>(User_info.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<User_info>();
        }
    }

    //修改数据
    public int updateByKey(User_info bean) {
        return updateByKey(bean, TABLENAME);
    }

    //修改数据
    public int updateByKey(User_info bean, String TABLENAME2) {
        try{
            String sql;
            sql = "UPDATE "+TABLENAME2+" SET uuid=:uuid,ui_tel=:ui_tel,ui_password=:ui_password,ui_nickname=:ui_nickname,ui_avatar=:ui_avatar,ui_sex=:ui_sex,driving_licence=:driving_licence,ui_name=:ui_name,ui_address=:ui_address,bind_tel=:bind_tel,ui_vc=:ui_vc,ui_rmb=:ui_rmb,coupon_num=:coupon_num,ui_state=:ui_state,ui_autopay=:ui_autopay,pay_source=:pay_source,ui_level=:ui_level,ui_score=:ui_score,ui_mood=:ui_mood,ui_intro=:ui_intro,ui_age=:ui_age,ui_token=:ui_token,ui_email=:ui_email,ui_high=:ui_high,ui_degree=:ui_degree,ctime=:ctime,utime=:utime,ui_flag=:ui_flag,ui_online=:ui_online,ui_integrity=:ui_integrity,note=:note WHERE ui_id=:ui_id";
            SqlParameterSource ps = new BeanPropertySqlParameterSource(bean);
            return _np.update(sql, ps);
        }catch(Exception e){
            log.error(e);
            return 0;
        }
    }

    //批量修改数据
    public int[] updateByKey (final List<User_info> beans) throws SQLException{
        return updateByKey(beans, TABLENAME);
    }

    //批量修改数据
    public int[] updateByKey (final List<User_info> beans, String TABLENAME2) throws SQLException{
        try{
            String sql;
            sql = "UPDATE "+TABLENAME2+" SET uuid=?,ui_tel=?,ui_password=?,ui_nickname=?,ui_avatar=?,ui_sex=?,driving_licence=?,ui_name=?,ui_address=?,bind_tel=?,ui_vc=?,ui_rmb=?,coupon_num=?,ui_state=?,ui_autopay=?,pay_source=?,ui_level=?,ui_score=?,ui_mood=?,ui_intro=?,ui_age=?,ui_token=?,ui_email=?,ui_high=?,ui_degree=?,ctime=?,utime=?,ui_flag=?,ui_online=?,ui_integrity=?,note=? WHERE ui_id=?";
            return _np.getJdbcOperations().batchUpdate(sql, new BatchPreparedStatementSetter() {
                //@Override
                public int getBatchSize() {
                    return beans.size();
                }
                //@Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    User_info bean = beans.get(i);
                    ps.setString(1, bean.uuid);
                    ps.setString(2, bean.ui_tel);
                    ps.setString(3, bean.ui_password);
                    ps.setString(4, bean.ui_nickname);
                    ps.setString(5, bean.ui_avatar);
                    ps.setString(6, bean.ui_sex);
                    ps.setString(7, bean.driving_licence);
                    ps.setString(8, bean.ui_name);
                    ps.setString(9, bean.ui_address);
                    ps.setString(10, bean.bind_tel);
                    ps.setLong(11, bean.ui_vc);
                    ps.setLong(12, bean.ui_rmb);
                    ps.setLong(13, bean.coupon_num);
                    ps.setInt(14, bean.ui_state);
                    ps.setInt(15, bean.ui_autopay);
                    ps.setInt(16, bean.pay_source);
                    ps.setInt(17, bean.ui_level);
                    ps.setLong(18, bean.ui_score);
                    ps.setString(19, bean.ui_mood);
                    ps.setString(20, bean.ui_intro);
                    ps.setInt(21, bean.ui_age);
                    ps.setString(22, bean.ui_token);
                    ps.setString(23, bean.ui_email);
                    ps.setInt(24, bean.ui_high);
                    ps.setString(25, bean.ui_degree);
                    ps.setTimestamp(26, new Timestamp(bean.ctime.getTime()));
                    ps.setTimestamp(27, new Timestamp(bean.utime.getTime()));
                    ps.setInt(28, bean.ui_flag);
                    ps.setLong(29, bean.ui_online);
                    ps.setInt(30, bean.ui_integrity);
                    ps.setString(31, bean.note);
                    ps.setLong(32, bean.ui_id);
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
                 "	`ui_id`  BIGINT(20) NOT NULL AUTO_INCREMENT," +
                 "	`uuid`  VARCHAR(65)," +
                 "	`ui_tel`  VARCHAR(11)," +
                 "	`ui_password`  VARCHAR(80)," +
                 "	`ui_nickname`  VARCHAR(30)," +
                 "	`ui_avatar`  VARCHAR(200)," +
                 "	`ui_sex`  VARCHAR(30)," +
                 "	`driving_licence`  TINYTEXT," +
                 "	`ui_name`  VARCHAR(60)," +
                 "	`ui_address`  VARCHAR(100)," +
                 "	`bind_tel`  VARCHAR(20)," +
                 "	`ui_vc`  BIGINT(20)," +
                 "	`ui_rmb`  BIGINT(20)," +
                 "	`coupon_num`  BIGINT(20)," +
                 "	`ui_state`  INT(11)," +
                 "	`ui_autopay`  INT(11)," +
                 "	`pay_source`  INT(11)," +
                 "	`ui_level`  INT(11)," +
                 "	`ui_score`  BIGINT(20)," +
                 "	`ui_mood`  VARCHAR(100)," +
                 "	`ui_intro`  TINYTEXT," +
                 "	`ui_age`  INT(11)," +
                 "	`ui_token`  VARCHAR(60)," +
                 "	`ui_email`  VARCHAR(60)," +
                 "	`ui_high`  INT(11)," +
                 "	`ui_degree`  VARCHAR(60)," +
                 "	`ctime`  DATETIME," +
                 "	`utime`  DATETIME," +
                 "	`ui_flag`  INT(11)," +
                 "	`ui_online`  BIGINT(20)," +
                 "	`ui_integrity`  INT(11)," +
                 "	`note`  VARCHAR(100)," +
                 "	PRIMARY KEY (`ui_id`)" +
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
