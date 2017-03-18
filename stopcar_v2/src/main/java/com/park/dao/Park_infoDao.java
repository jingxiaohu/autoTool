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

//park_info

@Repository("park_infoDao")
public class Park_infoDao extends BaseDao{

    Logger log = Logger.getLogger(Park_infoDao.class);



    public  String TABLE = "park_info";

    public  String TABLENAME = "park_info";

    public  String TABLEMM(){
        return TABLE + sdfMm.format(new java.util.Date());
    }

    public  String TABLEDD(){
        return TABLE + sdfDd.format(new java.util.Date());
    }

    public javax.sql.DataSource ds;

    public  String[] carrays ={"pi_id","area_code","pi_name","address_name","lng","lat","linkman_name","linkman_tel","copy_linkman_name","copy_linkman_tel","pi_phone","department","enter_num","exit_num","hlc_enter_num","hlc_exit_num","enter_camera_num","exit_camera_num","camera_info","ctime","utime","park_type","is_expect","expect_money","allow_revoke_time","allow_expect_time","timeout_info","rent_info","month_price","is_rent","money","loginname","password","mac","is_fault","pi_state","roadside_type","pu_id","pu_nd","note","carport_yet","carport_space","carport_total","moth_car_num","moth_car_num_space","time_car_num","time_car_num_space","expect_car_num"};
    public  String coulmns ="pi_id,area_code,pi_name,address_name,lng,lat,linkman_name,linkman_tel,copy_linkman_name,copy_linkman_tel,pi_phone,department,enter_num,exit_num,hlc_enter_num,hlc_exit_num,enter_camera_num,exit_camera_num,camera_info,ctime,utime,park_type,is_expect,expect_money,allow_revoke_time,allow_expect_time,timeout_info,rent_info,month_price,is_rent,money,loginname,password,mac,is_fault,pi_state,roadside_type,pu_id,pu_nd,note,carport_yet,carport_space,carport_total,moth_car_num,moth_car_num_space,time_car_num,time_car_num_space,expect_car_num";
    public  String coulmns2 ="area_code,pi_name,address_name,lng,lat,linkman_name,linkman_tel,copy_linkman_name,copy_linkman_tel,pi_phone,department,enter_num,exit_num,hlc_enter_num,hlc_exit_num,enter_camera_num,exit_camera_num,camera_info,ctime,utime,park_type,is_expect,expect_money,allow_revoke_time,allow_expect_time,timeout_info,rent_info,month_price,is_rent,money,loginname,password,mac,is_fault,pi_state,roadside_type,pu_id,pu_nd,note,carport_yet,carport_space,carport_total,moth_car_num,moth_car_num_space,time_car_num,time_car_num_space,expect_car_num";

    //添加数据
    public int insert(Park_info bean) throws SQLException{
        return insert(bean, TABLENAME);
    }

    //添加数据
    public int insert(Park_info bean, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "INSERT INTO "+TABLENAME2+" (area_code,pi_name,address_name,lng,lat,linkman_name,linkman_tel,copy_linkman_name,copy_linkman_tel,pi_phone,department,enter_num,exit_num,hlc_enter_num,hlc_exit_num,enter_camera_num,exit_camera_num,camera_info,ctime,utime,park_type,is_expect,expect_money,allow_revoke_time,allow_expect_time,timeout_info,rent_info,month_price,is_rent,money,loginname,password,mac,is_fault,pi_state,roadside_type,pu_id,pu_nd,note,carport_yet,carport_space,carport_total,moth_car_num,moth_car_num_space,time_car_num,time_car_num_space,expect_car_num) VALUES (:area_code,:pi_name,:address_name,:lng,:lat,:linkman_name,:linkman_tel,:copy_linkman_name,:copy_linkman_tel,:pi_phone,:department,:enter_num,:exit_num,:hlc_enter_num,:hlc_exit_num,:enter_camera_num,:exit_camera_num,:camera_info,:ctime,:utime,:park_type,:is_expect,:expect_money,:allow_revoke_time,:allow_expect_time,:timeout_info,:rent_info,:month_price,:is_rent,:money,:loginname,:password,:mac,:is_fault,:pi_state,:roadside_type,:pu_id,:pu_nd,:note,:carport_yet,:carport_space,:carport_total,:moth_car_num,:moth_car_num_space,:time_car_num,:time_car_num_space,:expect_car_num)";
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
    public int insert2(Park_info bean) throws SQLException{
        return insert2(bean, TABLENAME);
    }

    //添加数据
    public int insert2(Park_info bean, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "INSERT INTO "+TABLENAME2+" (pi_id,area_code,pi_name,address_name,lng,lat,linkman_name,linkman_tel,copy_linkman_name,copy_linkman_tel,pi_phone,department,enter_num,exit_num,hlc_enter_num,hlc_exit_num,enter_camera_num,exit_camera_num,camera_info,ctime,utime,park_type,is_expect,expect_money,allow_revoke_time,allow_expect_time,timeout_info,rent_info,month_price,is_rent,money,loginname,password,mac,is_fault,pi_state,roadside_type,pu_id,pu_nd,note,carport_yet,carport_space,carport_total,moth_car_num,moth_car_num_space,time_car_num,time_car_num_space,expect_car_num) VALUES (:pi_id,:area_code,:pi_name,:address_name,:lng,:lat,:linkman_name,:linkman_tel,:copy_linkman_name,:copy_linkman_tel,:pi_phone,:department,:enter_num,:exit_num,:hlc_enter_num,:hlc_exit_num,:enter_camera_num,:exit_camera_num,:camera_info,:ctime,:utime,:park_type,:is_expect,:expect_money,:allow_revoke_time,:allow_expect_time,:timeout_info,:rent_info,:month_price,:is_rent,:money,:loginname,:password,:mac,:is_fault,:pi_state,:roadside_type,:pu_id,:pu_nd,:note,:carport_yet,:carport_space,:carport_total,:moth_car_num,:moth_car_num_space,:time_car_num,:time_car_num_space,:expect_car_num)";
            SqlParameterSource ps = new BeanPropertySqlParameterSource(bean);
            return _np.update(sql, ps);
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            throw new SQLException("insert2 is error", e);
        }
    }

    //批量添加数据
    public int[] insert(List<Park_info> beans) throws SQLException{
        return insert(beans, TABLENAME);
    }

    //批量添加数据
    public int[] insert(final List<Park_info> beans, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "INSERT INTO "+TABLENAME2+" (area_code,pi_name,address_name,lng,lat,linkman_name,linkman_tel,copy_linkman_name,copy_linkman_tel,pi_phone,department,enter_num,exit_num,hlc_enter_num,hlc_exit_num,enter_camera_num,exit_camera_num,camera_info,ctime,utime,park_type,is_expect,expect_money,allow_revoke_time,allow_expect_time,timeout_info,rent_info,month_price,is_rent,money,loginname,password,mac,is_fault,pi_state,roadside_type,pu_id,pu_nd,note,carport_yet,carport_space,carport_total,moth_car_num,moth_car_num_space,time_car_num,time_car_num_space,expect_car_num) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            return _np.getJdbcOperations().batchUpdate(sql, new BatchPreparedStatementSetter() {
                //@Override
                public int getBatchSize() {
                    return beans.size();
                }
                //@Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Park_info bean = beans.get(i);
                    ps.setString(1, bean.area_code);
                    ps.setString(2, bean.pi_name);
                    ps.setString(3, bean.address_name);
                    ps.setDouble(4, bean.lng);
                    ps.setDouble(5, bean.lat);
                    ps.setString(6, bean.linkman_name);
                    ps.setString(7, bean.linkman_tel);
                    ps.setString(8, bean.copy_linkman_name);
                    ps.setString(9, bean.copy_linkman_tel);
                    ps.setString(10, bean.pi_phone);
                    ps.setString(11, bean.department);
                    ps.setInt(12, bean.enter_num);
                    ps.setInt(13, bean.exit_num);
                    ps.setInt(14, bean.hlc_enter_num);
                    ps.setInt(15, bean.hlc_exit_num);
                    ps.setInt(16, bean.enter_camera_num);
                    ps.setInt(17, bean.exit_camera_num);
                    ps.setString(18, bean.camera_info);
                    ps.setTimestamp(19, new Timestamp(bean.ctime.getTime()));
                    ps.setTimestamp(20, new Timestamp(bean.utime.getTime()));
                    ps.setInt(21, bean.park_type);
                    ps.setInt(22, bean.is_expect);
                    ps.setInt(23, bean.expect_money);
                    ps.setInt(24, bean.allow_revoke_time);
                    ps.setInt(25, bean.allow_expect_time);
                    ps.setString(26, bean.timeout_info);
                    ps.setString(27, bean.rent_info);
                    ps.setInt(28, bean.month_price);
                    ps.setInt(29, bean.is_rent);
                    ps.setInt(30, bean.money);
                    ps.setString(31, bean.loginname);
                    ps.setString(32, bean.password);
                    ps.setString(33, bean.mac);
                    ps.setInt(34, bean.is_fault);
                    ps.setInt(35, bean.pi_state);
                    ps.setInt(36, bean.roadside_type);
                    ps.setLong(37, bean.pu_id);
                    ps.setString(38, bean.pu_nd);
                    ps.setString(39, bean.note);
                    ps.setInt(40, bean.carport_yet);
                    ps.setInt(41, bean.carport_space);
                    ps.setInt(42, bean.carport_total);
                    ps.setInt(43, bean.moth_car_num);
                    ps.setInt(44, bean.moth_car_num_space);
                    ps.setInt(45, bean.time_car_num);
                    ps.setInt(46, bean.time_car_num_space);
                    ps.setInt(47, bean.expect_car_num);
                }
            });
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            throw new SQLException("insert is error", e);
        }
    }

    //删除单条数据
    public int deleteByKey(long pi_id) throws SQLException{
        return deleteByKey(pi_id, TABLENAME);
    }

    //删除单条数据
    public int deleteByKey(long pi_id, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "DELETE FROM "+TABLENAME2+" WHERE pi_id=:pi_id";
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("pi_id", pi_id);
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
            sql = "DELETE FROM "+TABLENAME2+" WHERE pi_id=?";
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
    public List<Park_info> selectAll() {
        return selectAll(TABLENAME);
    }

    //查询所有数据
    public List<Park_info> selectAll(String TABLENAME2) {
        String sql;
        try{
            sql = "SELECT pi_id,area_code,pi_name,address_name,lng,lat,linkman_name,linkman_tel,copy_linkman_name,copy_linkman_tel,pi_phone,department,enter_num,exit_num,hlc_enter_num,hlc_exit_num,enter_camera_num,exit_camera_num,camera_info,ctime,utime,park_type,is_expect,expect_money,allow_revoke_time,allow_expect_time,timeout_info,rent_info,month_price,is_rent,money,loginname,password,mac,is_fault,pi_state,roadside_type,pu_id,pu_nd,note,carport_yet,carport_space,carport_total,moth_car_num,moth_car_num_space,time_car_num,time_car_num_space,expect_car_num FROM "+TABLENAME2+" ORDER BY pi_id";
            return _np.getJdbcOperations().query(sql, new BeanPropertyRowMapper<Park_info>(Park_info.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<Park_info>();
        }
    }

    //查询最新数据
    public List<Park_info> selectLast(int num) {
        return selectLast(num, TABLENAME);
    }

    //查询所有数据
    public List<Park_info> selectLast(int num ,String TABLENAME2) {
        String sql;
        try{
            sql = "SELECT pi_id,area_code,pi_name,address_name,lng,lat,linkman_name,linkman_tel,copy_linkman_name,copy_linkman_tel,pi_phone,department,enter_num,exit_num,hlc_enter_num,hlc_exit_num,enter_camera_num,exit_camera_num,camera_info,ctime,utime,park_type,is_expect,expect_money,allow_revoke_time,allow_expect_time,timeout_info,rent_info,month_price,is_rent,money,loginname,password,mac,is_fault,pi_state,roadside_type,pu_id,pu_nd,note,carport_yet,carport_space,carport_total,moth_car_num,moth_car_num_space,time_car_num,time_car_num_space,expect_car_num FROM "+TABLENAME2+" ORDER BY pi_id DESC LIMIT "+num+"" ;
            return _np.getJdbcOperations().query(sql, new BeanPropertyRowMapper<Park_info>(Park_info.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<Park_info>();
        }
    }

    //根据主键查询
    public List<Park_info> selectGtKey(long pi_id) {
        return selectGtKey(pi_id, TABLENAME);
    }

    //根据主键查询
    public List<Park_info> selectGtKey(long pi_id, String TABLENAME2) {
        String sql;
        try{
            sql="SELECT pi_id,area_code,pi_name,address_name,lng,lat,linkman_name,linkman_tel,copy_linkman_name,copy_linkman_tel,pi_phone,department,enter_num,exit_num,hlc_enter_num,hlc_exit_num,enter_camera_num,exit_camera_num,camera_info,ctime,utime,park_type,is_expect,expect_money,allow_revoke_time,allow_expect_time,timeout_info,rent_info,month_price,is_rent,money,loginname,password,mac,is_fault,pi_state,roadside_type,pu_id,pu_nd,note,carport_yet,carport_space,carport_total,moth_car_num,moth_car_num_space,time_car_num,time_car_num_space,expect_car_num FROM "+TABLENAME2+" WHERE pi_id>:pi_id";
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("pi_id", pi_id);
            return _np.query(sql, param, new BeanPropertyRowMapper<Park_info>(Park_info.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<Park_info>();
        }
    }

    //根据主键查询
    public Park_info selectByKey(long pi_id) {
        return selectByKey(pi_id, TABLENAME);
    }

    //根据主键查询
    public Park_info selectByKey(long pi_id, String TABLENAME2) {
        String sql;
        try{
            sql="SELECT pi_id,area_code,pi_name,address_name,lng,lat,linkman_name,linkman_tel,copy_linkman_name,copy_linkman_tel,pi_phone,department,enter_num,exit_num,hlc_enter_num,hlc_exit_num,enter_camera_num,exit_camera_num,camera_info,ctime,utime,park_type,is_expect,expect_money,allow_revoke_time,allow_expect_time,timeout_info,rent_info,month_price,is_rent,money,loginname,password,mac,is_fault,pi_state,roadside_type,pu_id,pu_nd,note,carport_yet,carport_space,carport_total,moth_car_num,moth_car_num_space,time_car_num,time_car_num_space,expect_car_num FROM "+TABLENAME2+" WHERE pi_id=:pi_id";
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("pi_id", pi_id);
            return (Park_info)_np.queryForObject(sql, param, new BeanPropertyRowMapper<Park_info>(Park_info.class));
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
    public List<Park_info> selectByPage(int begin, int num) {
        return selectByPage(begin, num, TABLENAME);
    }

    //分页查询
    public List<Park_info> selectByPage(int begin, int num, String TABLENAME2) {
        try{
            String sql;
            sql = "SELECT pi_id,area_code,pi_name,address_name,lng,lat,linkman_name,linkman_tel,copy_linkman_name,copy_linkman_tel,pi_phone,department,enter_num,exit_num,hlc_enter_num,hlc_exit_num,enter_camera_num,exit_camera_num,camera_info,ctime,utime,park_type,is_expect,expect_money,allow_revoke_time,allow_expect_time,timeout_info,rent_info,month_price,is_rent,money,loginname,password,mac,is_fault,pi_state,roadside_type,pu_id,pu_nd,note,carport_yet,carport_space,carport_total,moth_car_num,moth_car_num_space,time_car_num,time_car_num_space,expect_car_num FROM "+TABLENAME2+" LIMIT "+begin+", "+num+"";
            return _np.getJdbcOperations().query(sql,new BeanPropertyRowMapper<Park_info>(Park_info.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<Park_info>();
        }
    }

    //修改数据
    public int updateByKey(Park_info bean) {
        return updateByKey(bean, TABLENAME);
    }

    //修改数据
    public int updateByKey(Park_info bean, String TABLENAME2) {
        try{
            String sql;
            sql = "UPDATE "+TABLENAME2+" SET area_code=:area_code,pi_name=:pi_name,address_name=:address_name,lng=:lng,lat=:lat,linkman_name=:linkman_name,linkman_tel=:linkman_tel,copy_linkman_name=:copy_linkman_name,copy_linkman_tel=:copy_linkman_tel,pi_phone=:pi_phone,department=:department,enter_num=:enter_num,exit_num=:exit_num,hlc_enter_num=:hlc_enter_num,hlc_exit_num=:hlc_exit_num,enter_camera_num=:enter_camera_num,exit_camera_num=:exit_camera_num,camera_info=:camera_info,ctime=:ctime,utime=:utime,park_type=:park_type,is_expect=:is_expect,expect_money=:expect_money,allow_revoke_time=:allow_revoke_time,allow_expect_time=:allow_expect_time,timeout_info=:timeout_info,rent_info=:rent_info,month_price=:month_price,is_rent=:is_rent,money=:money,loginname=:loginname,password=:password,mac=:mac,is_fault=:is_fault,pi_state=:pi_state,roadside_type=:roadside_type,pu_id=:pu_id,pu_nd=:pu_nd,note=:note,carport_yet=:carport_yet,carport_space=:carport_space,carport_total=:carport_total,moth_car_num=:moth_car_num,moth_car_num_space=:moth_car_num_space,time_car_num=:time_car_num,time_car_num_space=:time_car_num_space,expect_car_num=:expect_car_num WHERE pi_id=:pi_id";
            SqlParameterSource ps = new BeanPropertySqlParameterSource(bean);
            return _np.update(sql, ps);
        }catch(Exception e){
            log.error(e);
            return 0;
        }
    }

    //批量修改数据
    public int[] updateByKey (final List<Park_info> beans) throws SQLException{
        return updateByKey(beans, TABLENAME);
    }

    //批量修改数据
    public int[] updateByKey (final List<Park_info> beans, String TABLENAME2) throws SQLException{
        try{
            String sql;
            sql = "UPDATE "+TABLENAME2+" SET area_code=?,pi_name=?,address_name=?,lng=?,lat=?,linkman_name=?,linkman_tel=?,copy_linkman_name=?,copy_linkman_tel=?,pi_phone=?,department=?,enter_num=?,exit_num=?,hlc_enter_num=?,hlc_exit_num=?,enter_camera_num=?,exit_camera_num=?,camera_info=?,ctime=?,utime=?,park_type=?,is_expect=?,expect_money=?,allow_revoke_time=?,allow_expect_time=?,timeout_info=?,rent_info=?,month_price=?,is_rent=?,money=?,loginname=?,password=?,mac=?,is_fault=?,pi_state=?,roadside_type=?,pu_id=?,pu_nd=?,note=?,carport_yet=?,carport_space=?,carport_total=?,moth_car_num=?,moth_car_num_space=?,time_car_num=?,time_car_num_space=?,expect_car_num=? WHERE pi_id=?";
            return _np.getJdbcOperations().batchUpdate(sql, new BatchPreparedStatementSetter() {
                //@Override
                public int getBatchSize() {
                    return beans.size();
                }
                //@Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Park_info bean = beans.get(i);
                    ps.setString(1, bean.area_code);
                    ps.setString(2, bean.pi_name);
                    ps.setString(3, bean.address_name);
                    ps.setDouble(4, bean.lng);
                    ps.setDouble(5, bean.lat);
                    ps.setString(6, bean.linkman_name);
                    ps.setString(7, bean.linkman_tel);
                    ps.setString(8, bean.copy_linkman_name);
                    ps.setString(9, bean.copy_linkman_tel);
                    ps.setString(10, bean.pi_phone);
                    ps.setString(11, bean.department);
                    ps.setInt(12, bean.enter_num);
                    ps.setInt(13, bean.exit_num);
                    ps.setInt(14, bean.hlc_enter_num);
                    ps.setInt(15, bean.hlc_exit_num);
                    ps.setInt(16, bean.enter_camera_num);
                    ps.setInt(17, bean.exit_camera_num);
                    ps.setString(18, bean.camera_info);
                    ps.setTimestamp(19, new Timestamp(bean.ctime.getTime()));
                    ps.setTimestamp(20, new Timestamp(bean.utime.getTime()));
                    ps.setInt(21, bean.park_type);
                    ps.setInt(22, bean.is_expect);
                    ps.setInt(23, bean.expect_money);
                    ps.setInt(24, bean.allow_revoke_time);
                    ps.setInt(25, bean.allow_expect_time);
                    ps.setString(26, bean.timeout_info);
                    ps.setString(27, bean.rent_info);
                    ps.setInt(28, bean.month_price);
                    ps.setInt(29, bean.is_rent);
                    ps.setInt(30, bean.money);
                    ps.setString(31, bean.loginname);
                    ps.setString(32, bean.password);
                    ps.setString(33, bean.mac);
                    ps.setInt(34, bean.is_fault);
                    ps.setInt(35, bean.pi_state);
                    ps.setInt(36, bean.roadside_type);
                    ps.setLong(37, bean.pu_id);
                    ps.setString(38, bean.pu_nd);
                    ps.setString(39, bean.note);
                    ps.setInt(40, bean.carport_yet);
                    ps.setInt(41, bean.carport_space);
                    ps.setInt(42, bean.carport_total);
                    ps.setInt(43, bean.moth_car_num);
                    ps.setInt(44, bean.moth_car_num_space);
                    ps.setInt(45, bean.time_car_num);
                    ps.setInt(46, bean.time_car_num_space);
                    ps.setInt(47, bean.expect_car_num);
                    ps.setLong(48, bean.pi_id);
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
                 "	`pi_id`  BIGINT(20) NOT NULL AUTO_INCREMENT," +
                 "	`area_code`  VARCHAR(60)," +
                 "	`pi_name`  VARCHAR(150)," +
                 "	`address_name`  VARCHAR(150)," +
                 "	`lng`  DOUBLE," +
                 "	`lat`  DOUBLE," +
                 "	`linkman_name`  VARCHAR(30)," +
                 "	`linkman_tel`  VARCHAR(11)," +
                 "	`copy_linkman_name`  VARCHAR(30)," +
                 "	`copy_linkman_tel`  VARCHAR(11)," +
                 "	`pi_phone`  VARCHAR(20)," +
                 "	`department`  VARCHAR(150)," +
                 "	`enter_num`  INT(11)," +
                 "	`exit_num`  INT(11)," +
                 "	`hlc_enter_num`  INT(11)," +
                 "	`hlc_exit_num`  INT(11)," +
                 "	`enter_camera_num`  INT(11)," +
                 "	`exit_camera_num`  INT(11)," +
                 "	`camera_info`  VARCHAR(150)," +
                 "	`ctime`  DATETIME," +
                 "	`utime`  DATETIME," +
                 "	`park_type`  INT(11)," +
                 "	`is_expect`  INT(11)," +
                 "	`expect_money`  INT(11)," +
                 "	`allow_revoke_time`  INT(11)," +
                 "	`allow_expect_time`  INT(11)," +
                 "	`timeout_info`  VARCHAR(150)," +
                 "	`rent_info`  VARCHAR(150)," +
                 "	`month_price`  INT(11)," +
                 "	`is_rent`  INT(11)," +
                 "	`money`  INT(11)," +
                 "	`loginname`  VARCHAR(60)," +
                 "	`password`  VARCHAR(60)," +
                 "	`mac`  TINYTEXT," +
                 "	`is_fault`  INT(11)," +
                 "	`pi_state`  INT(11)," +
                 "	`roadside_type`  INT(11)," +
                 "	`pu_id`  BIGINT(20)," +
                 "	`pu_nd`  VARCHAR(100)," +
                 "	`note`  VARCHAR(100)," +
                 "	`carport_yet`  INT(11)," +
                 "	`carport_space`  INT(11)," +
                 "	`carport_total`  INT(11)," +
                 "	`moth_car_num`  INT(11)," +
                 "	`moth_car_num_space`  INT(11)," +
                 "	`time_car_num`  INT(11)," +
                 "	`time_car_num_space`  INT(11)," +
                 "	`expect_car_num`  INT(11)," +
                 "	PRIMARY KEY (`pi_id`)" +
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
