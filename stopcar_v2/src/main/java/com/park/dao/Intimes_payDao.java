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

//intimes_pay

@Repository("intimes_payDao")
public class Intimes_payDao extends BaseDao{

    Logger log = Logger.getLogger(Intimes_payDao.class);



    public  String TABLE = "intimes_pay";

    public  String TABLENAME = "intimes_pay";

    public  String TABLEMM(){
        return TABLE + sdfMm.format(new java.util.Date());
    }

    public  String TABLEDD(){
        return TABLE + sdfDd.format(new java.util.Date());
    }

    public javax.sql.DataSource ds;

    public  String[] carrays ={"ip_id","ip_order_no","ip_alipay_no","ip_source","ip_source_str","ip_version","ip_terminal_type","ip_return_url","ii_id","ip_type","ip_price","ip_createtime","ip_createtime_str","ip_endtime","ip_endtime_str","ip_updatetime","ip_updatetime_str","ip_state","ip_ip","ip_imei","ip_item_type","ip_prop_name","pi_id","ci_id","gg_id","ip_ssid","cd_id","bank_account","ip_note"};
    public  String coulmns ="ip_id,ip_order_no,ip_alipay_no,ip_source,ip_source_str,ip_version,ip_terminal_type,ip_return_url,ii_id,ip_type,ip_price,ip_createtime,ip_createtime_str,ip_endtime,ip_endtime_str,ip_updatetime,ip_updatetime_str,ip_state,ip_ip,ip_imei,ip_item_type,ip_prop_name,pi_id,ci_id,gg_id,ip_ssid,cd_id,bank_account,ip_note";
    public  String coulmns2 ="ip_order_no,ip_alipay_no,ip_source,ip_source_str,ip_version,ip_terminal_type,ip_return_url,ii_id,ip_type,ip_price,ip_createtime,ip_createtime_str,ip_endtime,ip_endtime_str,ip_updatetime,ip_updatetime_str,ip_state,ip_ip,ip_imei,ip_item_type,ip_prop_name,pi_id,ci_id,gg_id,ip_ssid,cd_id,bank_account,ip_note";

    //添加数据
    public int insert(Intimes_pay bean) throws SQLException{
        return insert(bean, TABLENAME);
    }

    //添加数据
    public int insert(Intimes_pay bean, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "INSERT INTO "+TABLENAME2+" (ip_order_no,ip_alipay_no,ip_source,ip_source_str,ip_version,ip_terminal_type,ip_return_url,ii_id,ip_type,ip_price,ip_createtime,ip_createtime_str,ip_endtime,ip_endtime_str,ip_updatetime,ip_updatetime_str,ip_state,ip_ip,ip_imei,ip_item_type,ip_prop_name,pi_id,ci_id,gg_id,ip_ssid,cd_id,bank_account,ip_note) VALUES (:ip_order_no,:ip_alipay_no,:ip_source,:ip_source_str,:ip_version,:ip_terminal_type,:ip_return_url,:ii_id,:ip_type,:ip_price,:ip_createtime,:ip_createtime_str,:ip_endtime,:ip_endtime_str,:ip_updatetime,:ip_updatetime_str,:ip_state,:ip_ip,:ip_imei,:ip_item_type,:ip_prop_name,:pi_id,:ci_id,:gg_id,:ip_ssid,:cd_id,:bank_account,:ip_note)";
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
    public int insert2(Intimes_pay bean) throws SQLException{
        return insert2(bean, TABLENAME);
    }

    //添加数据
    public int insert2(Intimes_pay bean, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "INSERT INTO "+TABLENAME2+" (ip_id,ip_order_no,ip_alipay_no,ip_source,ip_source_str,ip_version,ip_terminal_type,ip_return_url,ii_id,ip_type,ip_price,ip_createtime,ip_createtime_str,ip_endtime,ip_endtime_str,ip_updatetime,ip_updatetime_str,ip_state,ip_ip,ip_imei,ip_item_type,ip_prop_name,pi_id,ci_id,gg_id,ip_ssid,cd_id,bank_account,ip_note) VALUES (:ip_id,:ip_order_no,:ip_alipay_no,:ip_source,:ip_source_str,:ip_version,:ip_terminal_type,:ip_return_url,:ii_id,:ip_type,:ip_price,:ip_createtime,:ip_createtime_str,:ip_endtime,:ip_endtime_str,:ip_updatetime,:ip_updatetime_str,:ip_state,:ip_ip,:ip_imei,:ip_item_type,:ip_prop_name,:pi_id,:ci_id,:gg_id,:ip_ssid,:cd_id,:bank_account,:ip_note)";
            SqlParameterSource ps = new BeanPropertySqlParameterSource(bean);
            return _np.update(sql, ps);
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            throw new SQLException("insert2 is error", e);
        }
    }

    //批量添加数据
    public int[] insert(List<Intimes_pay> beans) throws SQLException{
        return insert(beans, TABLENAME);
    }

    //批量添加数据
    public int[] insert(final List<Intimes_pay> beans, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "INSERT INTO "+TABLENAME2+" (ip_order_no,ip_alipay_no,ip_source,ip_source_str,ip_version,ip_terminal_type,ip_return_url,ii_id,ip_type,ip_price,ip_createtime,ip_createtime_str,ip_endtime,ip_endtime_str,ip_updatetime,ip_updatetime_str,ip_state,ip_ip,ip_imei,ip_item_type,ip_prop_name,pi_id,ci_id,gg_id,ip_ssid,cd_id,bank_account,ip_note) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            return _np.getJdbcOperations().batchUpdate(sql, new BatchPreparedStatementSetter() {
                //@Override
                public int getBatchSize() {
                    return beans.size();
                }
                //@Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Intimes_pay bean = beans.get(i);
                    ps.setString(1, bean.ip_order_no);
                    ps.setString(2, bean.ip_alipay_no);
                    ps.setInt(3, bean.ip_source);
                    ps.setString(4, bean.ip_source_str);
                    ps.setString(5, bean.ip_version);
                    ps.setInt(6, bean.ip_terminal_type);
                    ps.setString(7, bean.ip_return_url);
                    ps.setLong(8, bean.ii_id);
                    ps.setInt(9, bean.ip_type);
                    ps.setDouble(10, bean.ip_price);
                    ps.setLong(11, bean.ip_createtime);
                    ps.setString(12, bean.ip_createtime_str);
                    ps.setLong(13, bean.ip_endtime);
                    ps.setString(14, bean.ip_endtime_str);
                    ps.setLong(15, bean.ip_updatetime);
                    ps.setString(16, bean.ip_updatetime_str);
                    ps.setInt(17, bean.ip_state);
                    ps.setString(18, bean.ip_ip);
                    ps.setString(19, bean.ip_imei);
                    ps.setInt(20, bean.ip_item_type);
                    ps.setString(21, bean.ip_prop_name);
                    ps.setLong(22, bean.pi_id);
                    ps.setLong(23, bean.ci_id);
                    ps.setLong(24, bean.gg_id);
                    ps.setString(25, bean.ip_ssid);
                    ps.setLong(26, bean.cd_id);
                    ps.setInt(27, bean.bank_account);
                    ps.setString(28, bean.ip_note);
                }
            });
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            throw new SQLException("insert is error", e);
        }
    }

    //删除单条数据
    public int deleteByKey(long ip_id) throws SQLException{
        return deleteByKey(ip_id, TABLENAME);
    }

    //删除单条数据
    public int deleteByKey(long ip_id, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "DELETE FROM "+TABLENAME2+" WHERE ip_id=:ip_id";
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("ip_id", ip_id);
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
            sql = "DELETE FROM "+TABLENAME2+" WHERE ip_id=?";
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
    public List<Intimes_pay> selectAll() {
        return selectAll(TABLENAME);
    }

    //查询所有数据
    public List<Intimes_pay> selectAll(String TABLENAME2) {
        String sql;
        try{
            sql = "SELECT ip_id,ip_order_no,ip_alipay_no,ip_source,ip_source_str,ip_version,ip_terminal_type,ip_return_url,ii_id,ip_type,ip_price,ip_createtime,ip_createtime_str,ip_endtime,ip_endtime_str,ip_updatetime,ip_updatetime_str,ip_state,ip_ip,ip_imei,ip_item_type,ip_prop_name,pi_id,ci_id,gg_id,ip_ssid,cd_id,bank_account,ip_note FROM "+TABLENAME2+" ORDER BY ip_id";
            return _np.getJdbcOperations().query(sql, new BeanPropertyRowMapper<Intimes_pay>(Intimes_pay.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<Intimes_pay>();
        }
    }

    //查询最新数据
    public List<Intimes_pay> selectLast(int num) {
        return selectLast(num, TABLENAME);
    }

    //查询所有数据
    public List<Intimes_pay> selectLast(int num ,String TABLENAME2) {
        String sql;
        try{
            sql = "SELECT ip_id,ip_order_no,ip_alipay_no,ip_source,ip_source_str,ip_version,ip_terminal_type,ip_return_url,ii_id,ip_type,ip_price,ip_createtime,ip_createtime_str,ip_endtime,ip_endtime_str,ip_updatetime,ip_updatetime_str,ip_state,ip_ip,ip_imei,ip_item_type,ip_prop_name,pi_id,ci_id,gg_id,ip_ssid,cd_id,bank_account,ip_note FROM "+TABLENAME2+" ORDER BY ip_id DESC LIMIT "+num+"" ;
            return _np.getJdbcOperations().query(sql, new BeanPropertyRowMapper<Intimes_pay>(Intimes_pay.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<Intimes_pay>();
        }
    }

    //根据主键查询
    public List<Intimes_pay> selectGtKey(long ip_id) {
        return selectGtKey(ip_id, TABLENAME);
    }

    //根据主键查询
    public List<Intimes_pay> selectGtKey(long ip_id, String TABLENAME2) {
        String sql;
        try{
            sql="SELECT ip_id,ip_order_no,ip_alipay_no,ip_source,ip_source_str,ip_version,ip_terminal_type,ip_return_url,ii_id,ip_type,ip_price,ip_createtime,ip_createtime_str,ip_endtime,ip_endtime_str,ip_updatetime,ip_updatetime_str,ip_state,ip_ip,ip_imei,ip_item_type,ip_prop_name,pi_id,ci_id,gg_id,ip_ssid,cd_id,bank_account,ip_note FROM "+TABLENAME2+" WHERE ip_id>:ip_id";
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("ip_id", ip_id);
            return _np.query(sql, param, new BeanPropertyRowMapper<Intimes_pay>(Intimes_pay.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<Intimes_pay>();
        }
    }

    //根据主键查询
    public Intimes_pay selectByKey(long ip_id) {
        return selectByKey(ip_id, TABLENAME);
    }

    //根据主键查询
    public Intimes_pay selectByKey(long ip_id, String TABLENAME2) {
        String sql;
        try{
            sql="SELECT ip_id,ip_order_no,ip_alipay_no,ip_source,ip_source_str,ip_version,ip_terminal_type,ip_return_url,ii_id,ip_type,ip_price,ip_createtime,ip_createtime_str,ip_endtime,ip_endtime_str,ip_updatetime,ip_updatetime_str,ip_state,ip_ip,ip_imei,ip_item_type,ip_prop_name,pi_id,ci_id,gg_id,ip_ssid,cd_id,bank_account,ip_note FROM "+TABLENAME2+" WHERE ip_id=:ip_id";
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("ip_id", ip_id);
            return (Intimes_pay)_np.queryForObject(sql, param, new BeanPropertyRowMapper<Intimes_pay>(Intimes_pay.class));
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
    public List<Intimes_pay> selectByPage(int begin, int num) {
        return selectByPage(begin, num, TABLENAME);
    }

    //分页查询
    public List<Intimes_pay> selectByPage(int begin, int num, String TABLENAME2) {
        try{
            String sql;
            sql = "SELECT ip_id,ip_order_no,ip_alipay_no,ip_source,ip_source_str,ip_version,ip_terminal_type,ip_return_url,ii_id,ip_type,ip_price,ip_createtime,ip_createtime_str,ip_endtime,ip_endtime_str,ip_updatetime,ip_updatetime_str,ip_state,ip_ip,ip_imei,ip_item_type,ip_prop_name,pi_id,ci_id,gg_id,ip_ssid,cd_id,bank_account,ip_note FROM "+TABLENAME2+" LIMIT "+begin+", "+num+"";
            return _np.getJdbcOperations().query(sql,new BeanPropertyRowMapper<Intimes_pay>(Intimes_pay.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<Intimes_pay>();
        }
    }

    //修改数据
    public int updateByKey(Intimes_pay bean) {
        return updateByKey(bean, TABLENAME);
    }

    //修改数据
    public int updateByKey(Intimes_pay bean, String TABLENAME2) {
        try{
            String sql;
            sql = "UPDATE "+TABLENAME2+" SET ip_order_no=:ip_order_no,ip_alipay_no=:ip_alipay_no,ip_source=:ip_source,ip_source_str=:ip_source_str,ip_version=:ip_version,ip_terminal_type=:ip_terminal_type,ip_return_url=:ip_return_url,ii_id=:ii_id,ip_type=:ip_type,ip_price=:ip_price,ip_createtime=:ip_createtime,ip_createtime_str=:ip_createtime_str,ip_endtime=:ip_endtime,ip_endtime_str=:ip_endtime_str,ip_updatetime=:ip_updatetime,ip_updatetime_str=:ip_updatetime_str,ip_state=:ip_state,ip_ip=:ip_ip,ip_imei=:ip_imei,ip_item_type=:ip_item_type,ip_prop_name=:ip_prop_name,pi_id=:pi_id,ci_id=:ci_id,gg_id=:gg_id,ip_ssid=:ip_ssid,cd_id=:cd_id,bank_account=:bank_account,ip_note=:ip_note WHERE ip_id=:ip_id";
            SqlParameterSource ps = new BeanPropertySqlParameterSource(bean);
            return _np.update(sql, ps);
        }catch(Exception e){
            log.error(e);
            return 0;
        }
    }

    //批量修改数据
    public int[] updateByKey (final List<Intimes_pay> beans) throws SQLException{
        return updateByKey(beans, TABLENAME);
    }

    //批量修改数据
    public int[] updateByKey (final List<Intimes_pay> beans, String TABLENAME2) throws SQLException{
        try{
            String sql;
            sql = "UPDATE "+TABLENAME2+" SET ip_order_no=?,ip_alipay_no=?,ip_source=?,ip_source_str=?,ip_version=?,ip_terminal_type=?,ip_return_url=?,ii_id=?,ip_type=?,ip_price=?,ip_createtime=?,ip_createtime_str=?,ip_endtime=?,ip_endtime_str=?,ip_updatetime=?,ip_updatetime_str=?,ip_state=?,ip_ip=?,ip_imei=?,ip_item_type=?,ip_prop_name=?,pi_id=?,ci_id=?,gg_id=?,ip_ssid=?,cd_id=?,bank_account=?,ip_note=? WHERE ip_id=?";
            return _np.getJdbcOperations().batchUpdate(sql, new BatchPreparedStatementSetter() {
                //@Override
                public int getBatchSize() {
                    return beans.size();
                }
                //@Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Intimes_pay bean = beans.get(i);
                    ps.setString(1, bean.ip_order_no);
                    ps.setString(2, bean.ip_alipay_no);
                    ps.setInt(3, bean.ip_source);
                    ps.setString(4, bean.ip_source_str);
                    ps.setString(5, bean.ip_version);
                    ps.setInt(6, bean.ip_terminal_type);
                    ps.setString(7, bean.ip_return_url);
                    ps.setLong(8, bean.ii_id);
                    ps.setInt(9, bean.ip_type);
                    ps.setDouble(10, bean.ip_price);
                    ps.setLong(11, bean.ip_createtime);
                    ps.setString(12, bean.ip_createtime_str);
                    ps.setLong(13, bean.ip_endtime);
                    ps.setString(14, bean.ip_endtime_str);
                    ps.setLong(15, bean.ip_updatetime);
                    ps.setString(16, bean.ip_updatetime_str);
                    ps.setInt(17, bean.ip_state);
                    ps.setString(18, bean.ip_ip);
                    ps.setString(19, bean.ip_imei);
                    ps.setInt(20, bean.ip_item_type);
                    ps.setString(21, bean.ip_prop_name);
                    ps.setLong(22, bean.pi_id);
                    ps.setLong(23, bean.ci_id);
                    ps.setLong(24, bean.gg_id);
                    ps.setString(25, bean.ip_ssid);
                    ps.setLong(26, bean.cd_id);
                    ps.setInt(27, bean.bank_account);
                    ps.setString(28, bean.ip_note);
                    ps.setLong(29, bean.ip_id);
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
                 "	`ip_id`  BIGINT(20) NOT NULL AUTO_INCREMENT," +
                 "	`ip_order_no`  VARCHAR(100)," +
                 "	`ip_alipay_no`  VARCHAR(50)," +
                 "	`ip_source`  INT(11)," +
                 "	`ip_source_str`  VARCHAR(60)," +
                 "	`ip_version`  VARCHAR(30)," +
                 "	`ip_terminal_type`  INT(11)," +
                 "	`ip_return_url`  VARCHAR(60)," +
                 "	`ii_id`  BIGINT(20)," +
                 "	`ip_type`  INT(11)," +
                 "	`ip_price`  DOUBLE," +
                 "	`ip_createtime`  BIGINT(20)," +
                 "	`ip_createtime_str`  VARCHAR(30)," +
                 "	`ip_endtime`  BIGINT(20)," +
                 "	`ip_endtime_str`  VARCHAR(32)," +
                 "	`ip_updatetime`  BIGINT(20)," +
                 "	`ip_updatetime_str`  VARCHAR(30)," +
                 "	`ip_state`  INT(11)," +
                 "	`ip_ip`  VARCHAR(60)," +
                 "	`ip_imei`  VARCHAR(30)," +
                 "	`ip_item_type`  INT(11)," +
                 "	`ip_prop_name`  VARCHAR(60)," +
                 "	`pi_id`  BIGINT(20)," +
                 "	`ci_id`  BIGINT(20)," +
                 "	`gg_id`  BIGINT(20)," +
                 "	`ip_ssid`  VARCHAR(100)," +
                 "	`cd_id`  BIGINT(20)," +
                 "	`bank_account`  INT(11)," +
                 "	`ip_note`  VARCHAR(50)," +
                 "	PRIMARY KEY (`ip_id`)" +
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
