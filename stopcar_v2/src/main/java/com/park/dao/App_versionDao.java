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

//app_version

@Repository("app_versionDao")
public class App_versionDao extends BaseDao{

    Logger log = Logger.getLogger(App_versionDao.class);



    public  String TABLE = "app_version";

    public  String TABLENAME = "app_version";

    public  String TABLEMM(){
        return TABLE + sdfMm.format(new java.util.Date());
    }

    public  String TABLEDD(){
        return TABLE + sdfDd.format(new java.util.Date());
    }

    public javax.sql.DataSource ds;

    public  String[] carrays ={"id","cav_version","cav_version_external","cav_version_code","ctime","content","cav_md5","android_url","ios_url","note"};
    public  String coulmns ="id,cav_version,cav_version_external,cav_version_code,ctime,content,cav_md5,android_url,ios_url,note";
    public  String coulmns2 ="cav_version,cav_version_external,cav_version_code,ctime,content,cav_md5,android_url,ios_url,note";

    //添加数据
    public int insert(App_version bean) throws SQLException{
        return insert(bean, TABLENAME);
    }

    //添加数据
    public int insert(App_version bean, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "INSERT INTO "+TABLENAME2+" (cav_version,cav_version_external,cav_version_code,ctime,content,cav_md5,android_url,ios_url,note) VALUES (:cav_version,:cav_version_external,:cav_version_code,:ctime,:content,:cav_md5,:android_url,:ios_url,:note)";
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
    public int insert2(App_version bean) throws SQLException{
        return insert2(bean, TABLENAME);
    }

    //添加数据
    public int insert2(App_version bean, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "INSERT INTO "+TABLENAME2+" (id,cav_version,cav_version_external,cav_version_code,ctime,content,cav_md5,android_url,ios_url,note) VALUES (:id,:cav_version,:cav_version_external,:cav_version_code,:ctime,:content,:cav_md5,:android_url,:ios_url,:note)";
            SqlParameterSource ps = new BeanPropertySqlParameterSource(bean);
            return _np.update(sql, ps);
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            throw new SQLException("insert2 is error", e);
        }
    }

    //批量添加数据
    public int[] insert(List<App_version> beans) throws SQLException{
        return insert(beans, TABLENAME);
    }

    //批量添加数据
    public int[] insert(final List<App_version> beans, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "INSERT INTO "+TABLENAME2+" (cav_version,cav_version_external,cav_version_code,ctime,content,cav_md5,android_url,ios_url,note) VALUES (?,?,?,?,?,?,?,?,?)";
            return _np.getJdbcOperations().batchUpdate(sql, new BatchPreparedStatementSetter() {
                //@Override
                public int getBatchSize() {
                    return beans.size();
                }
                //@Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    App_version bean = beans.get(i);
                    ps.setString(1, bean.cav_version);
                    ps.setString(2, bean.cav_version_external);
                    ps.setInt(3, bean.cav_version_code);
                    ps.setTimestamp(4, new Timestamp(bean.ctime.getTime()));
                    ps.setString(5, bean.content);
                    ps.setString(6, bean.cav_md5);
                    ps.setString(7, bean.android_url);
                    ps.setString(8, bean.ios_url);
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
    public List<App_version> selectAll() {
        return selectAll(TABLENAME);
    }

    //查询所有数据
    public List<App_version> selectAll(String TABLENAME2) {
        String sql;
        try{
            sql = "SELECT id,cav_version,cav_version_external,cav_version_code,ctime,content,cav_md5,android_url,ios_url,note FROM "+TABLENAME2+" ORDER BY id";
            return _np.getJdbcOperations().query(sql, new BeanPropertyRowMapper<App_version>(App_version.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<App_version>();
        }
    }

    //查询最新数据
    public List<App_version> selectLast(int num) {
        return selectLast(num, TABLENAME);
    }

    //查询所有数据
    public List<App_version> selectLast(int num ,String TABLENAME2) {
        String sql;
        try{
            sql = "SELECT id,cav_version,cav_version_external,cav_version_code,ctime,content,cav_md5,android_url,ios_url,note FROM "+TABLENAME2+" ORDER BY id DESC LIMIT "+num+"" ;
            return _np.getJdbcOperations().query(sql, new BeanPropertyRowMapper<App_version>(App_version.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<App_version>();
        }
    }

    //根据主键查询
    public List<App_version> selectGtKey(long id) {
        return selectGtKey(id, TABLENAME);
    }

    //根据主键查询
    public List<App_version> selectGtKey(long id, String TABLENAME2) {
        String sql;
        try{
            sql="SELECT id,cav_version,cav_version_external,cav_version_code,ctime,content,cav_md5,android_url,ios_url,note FROM "+TABLENAME2+" WHERE id>:id";
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("id", id);
            return _np.query(sql, param, new BeanPropertyRowMapper<App_version>(App_version.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<App_version>();
        }
    }

    //根据主键查询
    public App_version selectByKey(long id) {
        return selectByKey(id, TABLENAME);
    }

    //根据主键查询
    public App_version selectByKey(long id, String TABLENAME2) {
        String sql;
        try{
            sql="SELECT id,cav_version,cav_version_external,cav_version_code,ctime,content,cav_md5,android_url,ios_url,note FROM "+TABLENAME2+" WHERE id=:id";
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("id", id);
            return (App_version)_np.queryForObject(sql, param, new BeanPropertyRowMapper<App_version>(App_version.class));
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
    public List<App_version> selectByPage(int begin, int num) {
        return selectByPage(begin, num, TABLENAME);
    }

    //分页查询
    public List<App_version> selectByPage(int begin, int num, String TABLENAME2) {
        try{
            String sql;
            sql = "SELECT id,cav_version,cav_version_external,cav_version_code,ctime,content,cav_md5,android_url,ios_url,note FROM "+TABLENAME2+" LIMIT "+begin+", "+num+"";
            return _np.getJdbcOperations().query(sql,new BeanPropertyRowMapper<App_version>(App_version.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<App_version>();
        }
    }

    //修改数据
    public int updateByKey(App_version bean) {
        return updateByKey(bean, TABLENAME);
    }

    //修改数据
    public int updateByKey(App_version bean, String TABLENAME2) {
        try{
            String sql;
            sql = "UPDATE "+TABLENAME2+" SET cav_version=:cav_version,cav_version_external=:cav_version_external,cav_version_code=:cav_version_code,ctime=:ctime,content=:content,cav_md5=:cav_md5,android_url=:android_url,ios_url=:ios_url,note=:note WHERE id=:id";
            SqlParameterSource ps = new BeanPropertySqlParameterSource(bean);
            return _np.update(sql, ps);
        }catch(Exception e){
            log.error(e);
            return 0;
        }
    }

    //批量修改数据
    public int[] updateByKey (final List<App_version> beans) throws SQLException{
        return updateByKey(beans, TABLENAME);
    }

    //批量修改数据
    public int[] updateByKey (final List<App_version> beans, String TABLENAME2) throws SQLException{
        try{
            String sql;
            sql = "UPDATE "+TABLENAME2+" SET cav_version=?,cav_version_external=?,cav_version_code=?,ctime=?,content=?,cav_md5=?,android_url=?,ios_url=?,note=? WHERE id=?";
            return _np.getJdbcOperations().batchUpdate(sql, new BatchPreparedStatementSetter() {
                //@Override
                public int getBatchSize() {
                    return beans.size();
                }
                //@Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    App_version bean = beans.get(i);
                    ps.setString(1, bean.cav_version);
                    ps.setString(2, bean.cav_version_external);
                    ps.setInt(3, bean.cav_version_code);
                    ps.setTimestamp(4, new Timestamp(bean.ctime.getTime()));
                    ps.setString(5, bean.content);
                    ps.setString(6, bean.cav_md5);
                    ps.setString(7, bean.android_url);
                    ps.setString(8, bean.ios_url);
                    ps.setString(9, bean.note);
                    ps.setLong(10, bean.id);
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
                 "	`cav_version`  VARCHAR(100)," +
                 "	`cav_version_external`  VARCHAR(100)," +
                 "	`cav_version_code`  INT(11)," +
                 "	`ctime`  DATETIME," +
                 "	`content`  TEXT," +
                 "	`cav_md5`  VARCHAR(32)," +
                 "	`android_url`  TINYTEXT," +
                 "	`ios_url`  TINYTEXT," +
                 "	`note`  VARCHAR(100)," +
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
