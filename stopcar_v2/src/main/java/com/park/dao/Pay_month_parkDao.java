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

//pay_month_park

@Repository("pay_month_parkDao")
public class Pay_month_parkDao extends BaseDao{

    Logger log = Logger.getLogger(Pay_month_parkDao.class);



    public  String TABLE = "pay_month_park";

    public  String TABLENAME = "pay_month_park";

    public  String TABLEMM(){
        return TABLE + sdfMm.format(new java.util.Date());
    }

    public  String TABLEDD(){
        return TABLE + sdfDd.format(new java.util.Date());
    }

    public javax.sql.DataSource ds;

    public  String[] carrays ={"id","pi_id","ui_id","car_code","money","pp_state","ctime","utime","month_num","pay_source","my_order","other_order","pay_type","pp_versioncode","phone_type","order_type","start_time","end_time","month_info","allege_state","area_code","is_del","cancel_state","upc_id","discount_money","note","discount_name","discount_type","final_time","address_name","base_rate","outtime_rate","outtime_money","outtime_time","outtime_minute","outtime_start_price","outtime_start_minute","outtime_charge_money","outtime_charge_minute","outtime_other_order","outtime_paytime","outtime_paystate","permit_time","is_24hours","is_open","open_time","is_cash","park_type","scan_time","arrive_time","temp_money","pi_name","is_over","pu_id","pu_nd","lng","lat","is_expire"};
    public  String coulmns ="id,pi_id,ui_id,car_code,money,pp_state,ctime,utime,month_num,pay_source,my_order,other_order,pay_type,pp_versioncode,phone_type,order_type,start_time,end_time,month_info,allege_state,area_code,is_del,cancel_state,upc_id,discount_money,note,discount_name,discount_type,final_time,address_name,base_rate,outtime_rate,outtime_money,outtime_time,outtime_minute,outtime_start_price,outtime_start_minute,outtime_charge_money,outtime_charge_minute,outtime_other_order,outtime_paytime,outtime_paystate,permit_time,is_24hours,is_open,open_time,is_cash,park_type,scan_time,arrive_time,temp_money,pi_name,is_over,pu_id,pu_nd,lng,lat,is_expire";
    public  String coulmns2 ="pi_id,ui_id,car_code,money,pp_state,ctime,utime,month_num,pay_source,my_order,other_order,pay_type,pp_versioncode,phone_type,order_type,start_time,end_time,month_info,allege_state,area_code,is_del,cancel_state,upc_id,discount_money,note,discount_name,discount_type,final_time,address_name,base_rate,outtime_rate,outtime_money,outtime_time,outtime_minute,outtime_start_price,outtime_start_minute,outtime_charge_money,outtime_charge_minute,outtime_other_order,outtime_paytime,outtime_paystate,permit_time,is_24hours,is_open,open_time,is_cash,park_type,scan_time,arrive_time,temp_money,pi_name,is_over,pu_id,pu_nd,lng,lat,is_expire";

    //添加数据
    public int insert(Pay_month_park bean) throws SQLException{
        return insert(bean, TABLENAME);
    }

    //添加数据
    public int insert(Pay_month_park bean, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "INSERT INTO "+TABLENAME2+" (pi_id,ui_id,car_code,money,pp_state,ctime,utime,month_num,pay_source,my_order,other_order,pay_type,pp_versioncode,phone_type,order_type,start_time,end_time,month_info,allege_state,area_code,is_del,cancel_state,upc_id,discount_money,note,discount_name,discount_type,final_time,address_name,base_rate,outtime_rate,outtime_money,outtime_time,outtime_minute,outtime_start_price,outtime_start_minute,outtime_charge_money,outtime_charge_minute,outtime_other_order,outtime_paytime,outtime_paystate,permit_time,is_24hours,is_open,open_time,is_cash,park_type,scan_time,arrive_time,temp_money,pi_name,is_over,pu_id,pu_nd,lng,lat,is_expire) VALUES (:pi_id,:ui_id,:car_code,:money,:pp_state,:ctime,:utime,:month_num,:pay_source,:my_order,:other_order,:pay_type,:pp_versioncode,:phone_type,:order_type,:start_time,:end_time,:month_info,:allege_state,:area_code,:is_del,:cancel_state,:upc_id,:discount_money,:note,:discount_name,:discount_type,:final_time,:address_name,:base_rate,:outtime_rate,:outtime_money,:outtime_time,:outtime_minute,:outtime_start_price,:outtime_start_minute,:outtime_charge_money,:outtime_charge_minute,:outtime_other_order,:outtime_paytime,:outtime_paystate,:permit_time,:is_24hours,:is_open,:open_time,:is_cash,:park_type,:scan_time,:arrive_time,:temp_money,:pi_name,:is_over,:pu_id,:pu_nd,:lng,:lat,:is_expire)";
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
    public int insert2(Pay_month_park bean) throws SQLException{
        return insert2(bean, TABLENAME);
    }

    //添加数据
    public int insert2(Pay_month_park bean, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "INSERT INTO "+TABLENAME2+" (id,pi_id,ui_id,car_code,money,pp_state,ctime,utime,month_num,pay_source,my_order,other_order,pay_type,pp_versioncode,phone_type,order_type,start_time,end_time,month_info,allege_state,area_code,is_del,cancel_state,upc_id,discount_money,note,discount_name,discount_type,final_time,address_name,base_rate,outtime_rate,outtime_money,outtime_time,outtime_minute,outtime_start_price,outtime_start_minute,outtime_charge_money,outtime_charge_minute,outtime_other_order,outtime_paytime,outtime_paystate,permit_time,is_24hours,is_open,open_time,is_cash,park_type,scan_time,arrive_time,temp_money,pi_name,is_over,pu_id,pu_nd,lng,lat,is_expire) VALUES (:id,:pi_id,:ui_id,:car_code,:money,:pp_state,:ctime,:utime,:month_num,:pay_source,:my_order,:other_order,:pay_type,:pp_versioncode,:phone_type,:order_type,:start_time,:end_time,:month_info,:allege_state,:area_code,:is_del,:cancel_state,:upc_id,:discount_money,:note,:discount_name,:discount_type,:final_time,:address_name,:base_rate,:outtime_rate,:outtime_money,:outtime_time,:outtime_minute,:outtime_start_price,:outtime_start_minute,:outtime_charge_money,:outtime_charge_minute,:outtime_other_order,:outtime_paytime,:outtime_paystate,:permit_time,:is_24hours,:is_open,:open_time,:is_cash,:park_type,:scan_time,:arrive_time,:temp_money,:pi_name,:is_over,:pu_id,:pu_nd,:lng,:lat,:is_expire)";
            SqlParameterSource ps = new BeanPropertySqlParameterSource(bean);
            return _np.update(sql, ps);
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            throw new SQLException("insert2 is error", e);
        }
    }

    //批量添加数据
    public int[] insert(List<Pay_month_park> beans) throws SQLException{
        return insert(beans, TABLENAME);
    }

    //批量添加数据
    public int[] insert(final List<Pay_month_park> beans, String TABLENAME2) throws SQLException{
        String sql;
        try{
            sql = "INSERT INTO "+TABLENAME2+" (pi_id,ui_id,car_code,money,pp_state,ctime,utime,month_num,pay_source,my_order,other_order,pay_type,pp_versioncode,phone_type,order_type,start_time,end_time,month_info,allege_state,area_code,is_del,cancel_state,upc_id,discount_money,note,discount_name,discount_type,final_time,address_name,base_rate,outtime_rate,outtime_money,outtime_time,outtime_minute,outtime_start_price,outtime_start_minute,outtime_charge_money,outtime_charge_minute,outtime_other_order,outtime_paytime,outtime_paystate,permit_time,is_24hours,is_open,open_time,is_cash,park_type,scan_time,arrive_time,temp_money,pi_name,is_over,pu_id,pu_nd,lng,lat,is_expire) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            return _np.getJdbcOperations().batchUpdate(sql, new BatchPreparedStatementSetter() {
                //@Override
                public int getBatchSize() {
                    return beans.size();
                }
                //@Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Pay_month_park bean = beans.get(i);
                    ps.setLong(1, bean.pi_id);
                    ps.setLong(2, bean.ui_id);
                    ps.setString(3, bean.car_code);
                    ps.setInt(4, bean.money);
                    ps.setInt(5, bean.pp_state);
                    ps.setTimestamp(6, new Timestamp(bean.ctime.getTime()));
                    ps.setTimestamp(7, new Timestamp(bean.utime.getTime()));
                    ps.setInt(8, bean.month_num);
                    ps.setInt(9, bean.pay_source);
                    ps.setString(10, bean.my_order);
                    ps.setString(11, bean.other_order);
                    ps.setInt(12, bean.pay_type);
                    ps.setString(13, bean.pp_versioncode);
                    ps.setInt(14, bean.phone_type);
                    ps.setInt(15, bean.order_type);
                    ps.setTimestamp(16, new Timestamp(bean.start_time.getTime()));
                    ps.setTimestamp(17, new Timestamp(bean.end_time.getTime()));
                    ps.setString(18, bean.month_info);
                    ps.setInt(19, bean.allege_state);
                    ps.setString(20, bean.area_code);
                    ps.setInt(21, bean.is_del);
                    ps.setInt(22, bean.cancel_state);
                    ps.setLong(23, bean.upc_id);
                    ps.setLong(24, bean.discount_money);
                    ps.setString(25, bean.note);
                    ps.setString(26, bean.discount_name);
                    ps.setInt(27, bean.discount_type);
                    ps.setInt(28, bean.final_time);
                    ps.setString(29, bean.address_name);
                    ps.setString(30, bean.base_rate);
                    ps.setString(31, bean.outtime_rate);
                    ps.setInt(32, bean.outtime_money);
                    ps.setTimestamp(33, new Timestamp(bean.outtime_time.getTime()));
                    ps.setInt(34, bean.outtime_minute);
                    ps.setInt(35, bean.outtime_start_price);
                    ps.setInt(36, bean.outtime_start_minute);
                    ps.setInt(37, bean.outtime_charge_money);
                    ps.setInt(38, bean.outtime_charge_minute);
                    ps.setString(39, bean.outtime_other_order);
                    ps.setTimestamp(40, new Timestamp(bean.outtime_paytime.getTime()));
                    ps.setInt(41, bean.outtime_paystate);
                    ps.setString(42, bean.permit_time);
                    ps.setInt(43, bean.is_24hours);
                    ps.setInt(44, bean.is_open);
                    ps.setTimestamp(45, new Timestamp(bean.open_time.getTime()));
                    ps.setInt(46, bean.is_cash);
                    ps.setInt(47, bean.park_type);
                    ps.setTimestamp(48, new Timestamp(bean.scan_time.getTime()));
                    ps.setTimestamp(49, new Timestamp(bean.arrive_time.getTime()));
                    ps.setLong(50, bean.temp_money);
                    ps.setString(51, bean.pi_name);
                    ps.setInt(52, bean.is_over);
                    ps.setLong(53, bean.pu_id);
                    ps.setString(54, bean.pu_nd);
                    ps.setDouble(55, bean.lng);
                    ps.setDouble(56, bean.lat);
                    ps.setInt(57, bean.is_expire);
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
    public List<Pay_month_park> selectAll() {
        return selectAll(TABLENAME);
    }

    //查询所有数据
    public List<Pay_month_park> selectAll(String TABLENAME2) {
        String sql;
        try{
            sql = "SELECT id,pi_id,ui_id,car_code,money,pp_state,ctime,utime,month_num,pay_source,my_order,other_order,pay_type,pp_versioncode,phone_type,order_type,start_time,end_time,month_info,allege_state,area_code,is_del,cancel_state,upc_id,discount_money,note,discount_name,discount_type,final_time,address_name,base_rate,outtime_rate,outtime_money,outtime_time,outtime_minute,outtime_start_price,outtime_start_minute,outtime_charge_money,outtime_charge_minute,outtime_other_order,outtime_paytime,outtime_paystate,permit_time,is_24hours,is_open,open_time,is_cash,park_type,scan_time,arrive_time,temp_money,pi_name,is_over,pu_id,pu_nd,lng,lat,is_expire FROM "+TABLENAME2+" ORDER BY id";
            return _np.getJdbcOperations().query(sql, new BeanPropertyRowMapper<Pay_month_park>(Pay_month_park.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<Pay_month_park>();
        }
    }

    //查询最新数据
    public List<Pay_month_park> selectLast(int num) {
        return selectLast(num, TABLENAME);
    }

    //查询所有数据
    public List<Pay_month_park> selectLast(int num ,String TABLENAME2) {
        String sql;
        try{
            sql = "SELECT id,pi_id,ui_id,car_code,money,pp_state,ctime,utime,month_num,pay_source,my_order,other_order,pay_type,pp_versioncode,phone_type,order_type,start_time,end_time,month_info,allege_state,area_code,is_del,cancel_state,upc_id,discount_money,note,discount_name,discount_type,final_time,address_name,base_rate,outtime_rate,outtime_money,outtime_time,outtime_minute,outtime_start_price,outtime_start_minute,outtime_charge_money,outtime_charge_minute,outtime_other_order,outtime_paytime,outtime_paystate,permit_time,is_24hours,is_open,open_time,is_cash,park_type,scan_time,arrive_time,temp_money,pi_name,is_over,pu_id,pu_nd,lng,lat,is_expire FROM "+TABLENAME2+" ORDER BY id DESC LIMIT "+num+"" ;
            return _np.getJdbcOperations().query(sql, new BeanPropertyRowMapper<Pay_month_park>(Pay_month_park.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<Pay_month_park>();
        }
    }

    //根据主键查询
    public List<Pay_month_park> selectGtKey(long id) {
        return selectGtKey(id, TABLENAME);
    }

    //根据主键查询
    public List<Pay_month_park> selectGtKey(long id, String TABLENAME2) {
        String sql;
        try{
            sql="SELECT id,pi_id,ui_id,car_code,money,pp_state,ctime,utime,month_num,pay_source,my_order,other_order,pay_type,pp_versioncode,phone_type,order_type,start_time,end_time,month_info,allege_state,area_code,is_del,cancel_state,upc_id,discount_money,note,discount_name,discount_type,final_time,address_name,base_rate,outtime_rate,outtime_money,outtime_time,outtime_minute,outtime_start_price,outtime_start_minute,outtime_charge_money,outtime_charge_minute,outtime_other_order,outtime_paytime,outtime_paystate,permit_time,is_24hours,is_open,open_time,is_cash,park_type,scan_time,arrive_time,temp_money,pi_name,is_over,pu_id,pu_nd,lng,lat,is_expire FROM "+TABLENAME2+" WHERE id>:id";
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("id", id);
            return _np.query(sql, param, new BeanPropertyRowMapper<Pay_month_park>(Pay_month_park.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<Pay_month_park>();
        }
    }

    //根据主键查询
    public Pay_month_park selectByKey(long id) {
        return selectByKey(id, TABLENAME);
    }

    //根据主键查询
    public Pay_month_park selectByKey(long id, String TABLENAME2) {
        String sql;
        try{
            sql="SELECT id,pi_id,ui_id,car_code,money,pp_state,ctime,utime,month_num,pay_source,my_order,other_order,pay_type,pp_versioncode,phone_type,order_type,start_time,end_time,month_info,allege_state,area_code,is_del,cancel_state,upc_id,discount_money,note,discount_name,discount_type,final_time,address_name,base_rate,outtime_rate,outtime_money,outtime_time,outtime_minute,outtime_start_price,outtime_start_minute,outtime_charge_money,outtime_charge_minute,outtime_other_order,outtime_paytime,outtime_paystate,permit_time,is_24hours,is_open,open_time,is_cash,park_type,scan_time,arrive_time,temp_money,pi_name,is_over,pu_id,pu_nd,lng,lat,is_expire FROM "+TABLENAME2+" WHERE id=:id";
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("id", id);
            return (Pay_month_park)_np.queryForObject(sql, param, new BeanPropertyRowMapper<Pay_month_park>(Pay_month_park.class));
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
    public List<Pay_month_park> selectByPage(int begin, int num) {
        return selectByPage(begin, num, TABLENAME);
    }

    //分页查询
    public List<Pay_month_park> selectByPage(int begin, int num, String TABLENAME2) {
        try{
            String sql;
            sql = "SELECT id,pi_id,ui_id,car_code,money,pp_state,ctime,utime,month_num,pay_source,my_order,other_order,pay_type,pp_versioncode,phone_type,order_type,start_time,end_time,month_info,allege_state,area_code,is_del,cancel_state,upc_id,discount_money,note,discount_name,discount_type,final_time,address_name,base_rate,outtime_rate,outtime_money,outtime_time,outtime_minute,outtime_start_price,outtime_start_minute,outtime_charge_money,outtime_charge_minute,outtime_other_order,outtime_paytime,outtime_paystate,permit_time,is_24hours,is_open,open_time,is_cash,park_type,scan_time,arrive_time,temp_money,pi_name,is_over,pu_id,pu_nd,lng,lat,is_expire FROM "+TABLENAME2+" LIMIT "+begin+", "+num+"";
            return _np.getJdbcOperations().query(sql,new BeanPropertyRowMapper<Pay_month_park>(Pay_month_park.class));
        }catch(Exception e){
            //createTable(TABLENAME2);
            log.error(e);
            return new ArrayList<Pay_month_park>();
        }
    }

    //修改数据
    public int updateByKey(Pay_month_park bean) {
        return updateByKey(bean, TABLENAME);
    }

    //修改数据
    public int updateByKey(Pay_month_park bean, String TABLENAME2) {
        try{
            String sql;
            sql = "UPDATE "+TABLENAME2+" SET pi_id=:pi_id,ui_id=:ui_id,car_code=:car_code,money=:money,pp_state=:pp_state,ctime=:ctime,utime=:utime,month_num=:month_num,pay_source=:pay_source,my_order=:my_order,other_order=:other_order,pay_type=:pay_type,pp_versioncode=:pp_versioncode,phone_type=:phone_type,order_type=:order_type,start_time=:start_time,end_time=:end_time,month_info=:month_info,allege_state=:allege_state,area_code=:area_code,is_del=:is_del,cancel_state=:cancel_state,upc_id=:upc_id,discount_money=:discount_money,note=:note,discount_name=:discount_name,discount_type=:discount_type,final_time=:final_time,address_name=:address_name,base_rate=:base_rate,outtime_rate=:outtime_rate,outtime_money=:outtime_money,outtime_time=:outtime_time,outtime_minute=:outtime_minute,outtime_start_price=:outtime_start_price,outtime_start_minute=:outtime_start_minute,outtime_charge_money=:outtime_charge_money,outtime_charge_minute=:outtime_charge_minute,outtime_other_order=:outtime_other_order,outtime_paytime=:outtime_paytime,outtime_paystate=:outtime_paystate,permit_time=:permit_time,is_24hours=:is_24hours,is_open=:is_open,open_time=:open_time,is_cash=:is_cash,park_type=:park_type,scan_time=:scan_time,arrive_time=:arrive_time,temp_money=:temp_money,pi_name=:pi_name,is_over=:is_over,pu_id=:pu_id,pu_nd=:pu_nd,lng=:lng,lat=:lat,is_expire=:is_expire WHERE id=:id";
            SqlParameterSource ps = new BeanPropertySqlParameterSource(bean);
            return _np.update(sql, ps);
        }catch(Exception e){
            log.error(e);
            return 0;
        }
    }

    //批量修改数据
    public int[] updateByKey (final List<Pay_month_park> beans) throws SQLException{
        return updateByKey(beans, TABLENAME);
    }

    //批量修改数据
    public int[] updateByKey (final List<Pay_month_park> beans, String TABLENAME2) throws SQLException{
        try{
            String sql;
            sql = "UPDATE "+TABLENAME2+" SET pi_id=?,ui_id=?,car_code=?,money=?,pp_state=?,ctime=?,utime=?,month_num=?,pay_source=?,my_order=?,other_order=?,pay_type=?,pp_versioncode=?,phone_type=?,order_type=?,start_time=?,end_time=?,month_info=?,allege_state=?,area_code=?,is_del=?,cancel_state=?,upc_id=?,discount_money=?,note=?,discount_name=?,discount_type=?,final_time=?,address_name=?,base_rate=?,outtime_rate=?,outtime_money=?,outtime_time=?,outtime_minute=?,outtime_start_price=?,outtime_start_minute=?,outtime_charge_money=?,outtime_charge_minute=?,outtime_other_order=?,outtime_paytime=?,outtime_paystate=?,permit_time=?,is_24hours=?,is_open=?,open_time=?,is_cash=?,park_type=?,scan_time=?,arrive_time=?,temp_money=?,pi_name=?,is_over=?,pu_id=?,pu_nd=?,lng=?,lat=?,is_expire=? WHERE id=?";
            return _np.getJdbcOperations().batchUpdate(sql, new BatchPreparedStatementSetter() {
                //@Override
                public int getBatchSize() {
                    return beans.size();
                }
                //@Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Pay_month_park bean = beans.get(i);
                    ps.setLong(1, bean.pi_id);
                    ps.setLong(2, bean.ui_id);
                    ps.setString(3, bean.car_code);
                    ps.setInt(4, bean.money);
                    ps.setInt(5, bean.pp_state);
                    ps.setTimestamp(6, new Timestamp(bean.ctime.getTime()));
                    ps.setTimestamp(7, new Timestamp(bean.utime.getTime()));
                    ps.setInt(8, bean.month_num);
                    ps.setInt(9, bean.pay_source);
                    ps.setString(10, bean.my_order);
                    ps.setString(11, bean.other_order);
                    ps.setInt(12, bean.pay_type);
                    ps.setString(13, bean.pp_versioncode);
                    ps.setInt(14, bean.phone_type);
                    ps.setInt(15, bean.order_type);
                    ps.setTimestamp(16, new Timestamp(bean.start_time.getTime()));
                    ps.setTimestamp(17, new Timestamp(bean.end_time.getTime()));
                    ps.setString(18, bean.month_info);
                    ps.setInt(19, bean.allege_state);
                    ps.setString(20, bean.area_code);
                    ps.setInt(21, bean.is_del);
                    ps.setInt(22, bean.cancel_state);
                    ps.setLong(23, bean.upc_id);
                    ps.setLong(24, bean.discount_money);
                    ps.setString(25, bean.note);
                    ps.setString(26, bean.discount_name);
                    ps.setInt(27, bean.discount_type);
                    ps.setInt(28, bean.final_time);
                    ps.setString(29, bean.address_name);
                    ps.setString(30, bean.base_rate);
                    ps.setString(31, bean.outtime_rate);
                    ps.setInt(32, bean.outtime_money);
                    ps.setTimestamp(33, new Timestamp(bean.outtime_time.getTime()));
                    ps.setInt(34, bean.outtime_minute);
                    ps.setInt(35, bean.outtime_start_price);
                    ps.setInt(36, bean.outtime_start_minute);
                    ps.setInt(37, bean.outtime_charge_money);
                    ps.setInt(38, bean.outtime_charge_minute);
                    ps.setString(39, bean.outtime_other_order);
                    ps.setTimestamp(40, new Timestamp(bean.outtime_paytime.getTime()));
                    ps.setInt(41, bean.outtime_paystate);
                    ps.setString(42, bean.permit_time);
                    ps.setInt(43, bean.is_24hours);
                    ps.setInt(44, bean.is_open);
                    ps.setTimestamp(45, new Timestamp(bean.open_time.getTime()));
                    ps.setInt(46, bean.is_cash);
                    ps.setInt(47, bean.park_type);
                    ps.setTimestamp(48, new Timestamp(bean.scan_time.getTime()));
                    ps.setTimestamp(49, new Timestamp(bean.arrive_time.getTime()));
                    ps.setLong(50, bean.temp_money);
                    ps.setString(51, bean.pi_name);
                    ps.setInt(52, bean.is_over);
                    ps.setLong(53, bean.pu_id);
                    ps.setString(54, bean.pu_nd);
                    ps.setDouble(55, bean.lng);
                    ps.setDouble(56, bean.lat);
                    ps.setInt(57, bean.is_expire);
                    ps.setLong(58, bean.id);
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
                 "	`ui_id`  BIGINT(20)," +
                 "	`car_code`  VARCHAR(60)," +
                 "	`money`  INT(11)," +
                 "	`pp_state`  INT(11)," +
                 "	`ctime`  DATETIME," +
                 "	`utime`  DATETIME," +
                 "	`month_num`  INT(11)," +
                 "	`pay_source`  INT(11)," +
                 "	`my_order`  VARCHAR(80)," +
                 "	`other_order`  VARCHAR(80)," +
                 "	`pay_type`  INT(11)," +
                 "	`pp_versioncode`  VARCHAR(30)," +
                 "	`phone_type`  INT(11)," +
                 "	`order_type`  INT(11)," +
                 "	`start_time`  DATETIME," +
                 "	`end_time`  DATETIME," +
                 "	`month_info`  TINYTEXT," +
                 "	`allege_state`  INT(11)," +
                 "	`area_code`  VARCHAR(60)," +
                 "	`is_del`  INT(11)," +
                 "	`cancel_state`  INT(11)," +
                 "	`upc_id`  BIGINT(20)," +
                 "	`discount_money`  BIGINT(20)," +
                 "	`note`  VARCHAR(100)," +
                 "	`discount_name`  VARCHAR(150)," +
                 "	`discount_type`  INT(11)," +
                 "	`final_time`  INT(11)," +
                 "	`address_name`  VARCHAR(150)," +
                 "	`base_rate`  VARCHAR(60)," +
                 "	`outtime_rate`  VARCHAR(100)," +
                 "	`outtime_money`  INT(11)," +
                 "	`outtime_time`  DATETIME," +
                 "	`outtime_minute`  INT(11)," +
                 "	`outtime_start_price`  INT(11)," +
                 "	`outtime_start_minute`  INT(11)," +
                 "	`outtime_charge_money`  INT(11)," +
                 "	`outtime_charge_minute`  INT(11)," +
                 "	`outtime_other_order`  VARCHAR(80)," +
                 "	`outtime_paytime`  DATETIME," +
                 "	`outtime_paystate`  INT(11)," +
                 "	`permit_time`  VARCHAR(60)," +
                 "	`is_24hours`  INT(11)," +
                 "	`is_open`  INT(11)," +
                 "	`open_time`  DATETIME," +
                 "	`is_cash`  INT(11)," +
                 "	`park_type`  INT(11)," +
                 "	`scan_time`  DATETIME," +
                 "	`arrive_time`  DATETIME," +
                 "	`temp_money`  BIGINT(20)," +
                 "	`pi_name`  VARCHAR(100)," +
                 "	`is_over`  INT(11)," +
                 "	`pu_id`  BIGINT(20)," +
                 "	`pu_nd`  VARCHAR(100)," +
                 "	`lng`  DOUBLE," +
                 "	`lat`  DOUBLE," +
                 "	`is_expire`  INT(11)," +
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
