
package com.park.v1.biz;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.park.DataSource.DynamicDataSourceHolder;
import com.park.DataSource.TargetDataSource;
import com.park.Transaction.BaseTransaction;
import com.park.bean.Car_in_out;
import com.park.bean.Fault_record;
import com.park.bean.Park_device;
import com.park.bean.Park_info;
import com.park.bean.Pay_month_park;
import com.park.bean.Pay_park;
import com.park.bean.Rental_charging_rule;
import com.park.bean.ReturnDataNew;
import com.park.bean.Sms_validate;
import com.park.bean.User_carcode;
import com.park.util.RequestUtil;
import com.park.v1.biz.common.ParkInfoPB;
import com.park.v1.biz.common.PayParkPB;

/**
 * 处理停车场信息的业务逻辑管理类
 * @author jingxiaohu
 *
 */
@Service
public class ParkinfoBiz extends BaseBiz{

	@Resource(name="baseTransaction")
	protected BaseTransaction baseTransaction;
	@Autowired
	protected CarBiz carBiz;
	@Autowired
	protected ParkInfoPB parkInfoPB;
	@Autowired
	protected PayParkPB payParkPB;
	
//	String str_filed = "pi_id,area_code,pi_name,address_name,lng,lat,linkman_name,linkman_tel,copy_linkman_name,copy_linkman_tel,pi_phone,carport_space,carport_total,department,carport_yet,enter_num,exit_num,hlc_enter_num,hlc_exit_num,enter_camera_num,exit_camera_num,camera_info,ctime,utime,park_type,time_car_num,moth_car_num,expect_money,allow_revoke_time,allow_expect_time,timeout_info,rent_info,note";
	
	/**
	 * 记录停车场信息
	 * @param returnData
	 * @param name
	 * @param address_name
	 * @param lng
	 * @param lat
	 * @param linkman_name
	 * @param linkman_tel
	 * @param copy_linkman_name
	 * @param copy_linkman_tel
	 * @param pi_phone
	 * @param department
	 * @param carport_total
	 * @param enter_num
	 * @param exit_num
	 * @param hlc_enter_num
	 * @param hlc_exit_num
	 * @param enter_camera_num
	 * @param exit_camera_num
	 * @param camera_info
	 * @param park_type
	 * @param area_code
	 */
	public void record_parkinfo(ReturnDataNew returnData, String name,
			String address_name, double lng, double lat, String linkman_name,
			String linkman_tel, String copy_linkman_name,
			String copy_linkman_tel, String pi_phone, String department,
			int enter_num, int exit_num, int hlc_enter_num,
			int hlc_exit_num, int enter_camera_num, int exit_camera_num,
			String camera_info, String park_type, String area_code,int expect_money,int allow_revoke_time,int allow_expect_time,int is_expect
			,Integer carport_yet,Integer carport_space,Integer carport_total,Integer moth_car_num,Integer moth_car_num_space,Integer time_car_num,Integer time_car_num_space,Integer expect_car_num) {
		// TODO Auto-generated method stub
        try {
        	//首先判断是否已经进行了录入  如果数据库中没有该停车场的数据则进行插入  否则不予处理
        	Park_info park_info =  queryByNameT(name,address_name,area_code);
        	if(park_info != null){
        		//该停车场已经录入过了
        		returnData.setReturnData(errorcode_data, "该停车场已经录入过了", park_info,"1"); 
    			return;
        	}
        	//入库操作
        	park_info = new Park_info();
        	Date date = new Date();
        	park_info.setPi_name(name);
        	park_info.setAddress_name(address_name);
        	park_info.setCamera_info(camera_info);
        	park_info.setCopy_linkman_name(copy_linkman_name);
        	park_info.setCopy_linkman_tel(copy_linkman_tel);
        	park_info.setCtime(date);
        	park_info.setDepartment(department);
        	park_info.setEnter_camera_num(enter_camera_num);
        	park_info.setEnter_num(enter_num);
        	park_info.setExit_camera_num(exit_camera_num);
        	park_info.setExit_num(exit_num);
        	park_info.setHlc_enter_num(hlc_enter_num);
        	park_info.setHlc_exit_num(hlc_exit_num);
        	park_info.setLat(lat);
        	park_info.setLinkman_name(linkman_name);
        	park_info.setLinkman_tel(linkman_tel);
        	park_info.setLng(lng);
        	park_info.setPark_type(Integer.parseInt(park_type));
        	park_info.setPi_phone(pi_phone);
        	park_info.setUtime(date);
        	park_info.setArea_code(area_code);
        	
        	park_info.setAllow_expect_time(allow_expect_time);
        	park_info.setAllow_revoke_time(allow_revoke_time);
        	park_info.setExpect_money(expect_money);
        	park_info.setMoth_car_num(moth_car_num);
        	park_info.setIs_expect(is_expect);
        	
        	
		    if(carport_total != null && carport_total.intValue() > 0){
			  park_info.setCarport_total(carport_total.intValue());
			}  
			if(carport_space != null && carport_space.intValue() > 0){
			   park_info.setCarport_space(carport_space.intValue());
			}
			if(carport_yet != null && carport_yet.intValue() > 0){
				park_info.setCarport_yet(carport_yet.intValue());
			}
			if(moth_car_num != null && moth_car_num.intValue() > 0){
			   park_info.setMoth_car_num(moth_car_num.intValue());
			}
			if(moth_car_num_space != null && moth_car_num_space.intValue() > 0){
			   park_info.setMoth_car_num_space(moth_car_num_space.intValue());
			}
			if(time_car_num != null && time_car_num.intValue() > 0){
				park_info.setTime_car_num(time_car_num.intValue());
			}
			
			if(time_car_num_space != null && time_car_num_space.intValue() > 0){
			   park_info.setTime_car_num_space(time_car_num_space.intValue()); 
			}
        	
        	int id = park_infoDao.insert(park_info,ReturnParkTableName(area_code));
        	park_info.setPi_id(id);
			returnData.setReturnData(errorcode_success, null, park_info); 
			return;
			
		} catch (Exception e) {
			log.error("ParkinfoBiz record_parkinfo is error", e);
			returnData.setReturnData(errorcode_systerm, "system is error", null); 
		}
	}
	/**
	 * 更新场地信息
	 * @param returnData
	 * @param name
	 * @param address_name
	 * @param lng
	 * @param lat
	 * @param linkman_name
	 * @param linkman_tel
	 * @param copy_linkman_name
	 * @param copy_linkman_tel
	 * @param pi_phone
	 * @param department
	 * @param carport_total
	 * @param enter_num
	 * @param exit_num
	 * @param hlc_enter_num
	 * @param hlc_exit_num
	 * @param enter_camera_num
	 * @param exit_camera_num
	 * @param camera_info
	 * @param park_type
	 * @param cityname
	 */
	public void update_parkinfo(ReturnDataNew returnData, long pi_id,String name,
			String address_name, double lng, double lat, String linkman_name,
			String linkman_tel, String copy_linkman_name, String copy_linkman_tel,
			String pi_phone, String department,  int enter_num,
			int exit_num, int hlc_enter_num, int hlc_exit_num,
			int enter_camera_num, int exit_camera_num, String camera_info,
			String park_type, String area_code,int expect_money,int allow_revoke_time,int allow_expect_time,int is_expect
			,Integer carport_yet,Integer carport_space,Integer carport_total,Integer moth_car_num,Integer moth_car_num_space,Integer time_car_num,Integer time_car_num_space,Integer expect_car_num) {
		// TODO Auto-generated method stub
        try {
        	//获取该场地的信息
        	Park_info park_info = park_infoDao.selectByKey(pi_id,ReturnParkTableName(area_code));
        	if(park_info == null ){
        		returnData.setReturnData(errorcode_data, "该停车场不存在", ""); 
    			return;
        	}
        	//入库操作
        	Date date = new Date();
        	if(!RequestUtil.checkObjectBlank(name)){
        		park_info.setPi_name(name);
        	}
            if(!RequestUtil.checkObjectBlank(address_name)){
            	park_info.setAddress_name(address_name);
        	}
            if(!RequestUtil.checkObjectBlank(camera_info)){
            	park_info.setCamera_info(camera_info);
        	}
            if(carport_total > 0){
            	park_info.setCarport_total(carport_total);
        	}
            if(!RequestUtil.checkObjectBlank(copy_linkman_name)){
            	park_info.setCopy_linkman_name(copy_linkman_name);
        	}
            if(!RequestUtil.checkObjectBlank(copy_linkman_tel)){
            	park_info.setCopy_linkman_tel(copy_linkman_tel);
        	}
            if(!RequestUtil.checkObjectBlank(department)){
            	park_info.setDepartment(department);
        	}
            if(enter_camera_num >0){
            	park_info.setEnter_camera_num(enter_camera_num);
        	}
            if(enter_num > 0){
            	park_info.setEnter_num(enter_num);
        	}
            if(exit_camera_num >0){
            	park_info.setExit_camera_num(exit_camera_num);
            }
            if(exit_num > 0){
            	park_info.setExit_num(exit_num);
            }
            if(hlc_enter_num > 0){
            	park_info.setHlc_enter_num(hlc_enter_num);
            }
            if(hlc_exit_num > 0){
            	park_info.setHlc_exit_num(hlc_exit_num);
            }
            if(lat > 0){
            	park_info.setLat(lat);
        	}
            if(!RequestUtil.checkObjectBlank(linkman_name)){
            	park_info.setLinkman_name(linkman_name);
        	}
            if(!RequestUtil.checkObjectBlank(linkman_tel)){
            	park_info.setLinkman_tel(linkman_tel);
        	}
            if(lng > 0){
            	park_info.setLng(lng);
        	}
            if(!RequestUtil.checkObjectBlank(park_type)){
            	park_info.setPark_type(Integer.parseInt(park_type));
            }
            if(!RequestUtil.checkObjectBlank(pi_phone)){
            	park_info.setPi_phone(pi_phone);
        	}
            if(!RequestUtil.checkObjectBlank(area_code)){
            	park_info.setArea_code(area_code);
        	}
            if(allow_expect_time > 0){
            	park_info.setAllow_expect_time(allow_expect_time);
            }
        	if(allow_revoke_time > 0){
        		park_info.setAllow_revoke_time(allow_revoke_time);
        	}
        	
        	if(expect_money > 0){
        		park_info.setExpect_money(expect_money);
        	}
        	
        	if(moth_car_num > 0){
        		park_info.setMoth_car_num(moth_car_num);
        	}
        	
        	park_info.setIs_expect(is_expect);
            
            
		    if(carport_total != null && carport_total.intValue() > 0){
			  park_info.setCarport_total(carport_total.intValue());
			}  
			if(carport_space != null && carport_space.intValue() > 0){
			   park_info.setCarport_space(carport_space.intValue());
			}
			if(carport_yet != null && carport_yet.intValue() > 0){
				park_info.setCarport_yet(carport_yet.intValue());
			}
			if(moth_car_num != null && moth_car_num.intValue() > 0){
			   park_info.setMoth_car_num(moth_car_num.intValue());
			}
			if(moth_car_num_space != null && moth_car_num_space.intValue() > 0){
			   park_info.setMoth_car_num_space(moth_car_num_space.intValue());
			}
			if(time_car_num != null && time_car_num.intValue() > 0){
				park_info.setTime_car_num(time_car_num.intValue());
			}
			
			if(time_car_num_space != null && time_car_num_space.intValue() > 0){
			   park_info.setTime_car_num_space(time_car_num_space.intValue()); 
			}
        	park_info.setUtime(date);
        	int count = park_infoDao.updateByKey(park_info,ReturnParkTableName(area_code));
        	if(count > 0){
        		returnData.setReturnData(errorcode_success, "更新成功", park_info); 
				return;
        	}else{
        		//更新失败
				returnData.setReturnData(errorcode_data, "更新失败", ""); 
				return;
        	}
			
		} catch (Exception e) {
			log.error("ParkinfoBiz record_parkinfo is error", e);
			returnData.setReturnData(errorcode_systerm, "system is error", null); 
		}
	}
	
	
	/**
	 * 记录停车场计费规则信息	
	 * @param returnData
	 * @param pi_id
	 * @param start_price
	 * @param start_time
	 * @param charging
	 * @param charging_time
	 * @param month_price
	 * @param month_time
	 * @param permit_time
	 * @param timeout_info
	 * @param rcr_type
	 * @param rcr_state
	 * @param rcr_discount
	 * @param car_displacement
	 * @param car_type
	 * @param car_code_color
	 * @param is_time_bucket
	 * @param time_bucket
	 */
	public void record_charging_rule(ReturnDataNew returnData, long pi_id,
			int start_price, int start_time, int charging, int charging_time,
			int month_price, int month_time, String permit_time,
			String timeout_info, int rcr_type, int rcr_state, int rcr_discount,
			String car_displacement, int car_type, int car_code_color,
			int is_time_bucket, String time_bucket,String area_code) {
		// TODO Auto-generated method stub rental_charging_rule
        try {
        	//首先判断是否已经进行了录入  如果数据库中没有该停车场的数据则不进行插入  否则不予处理
        	//获取该场地的信息
        	Park_info park_info = park_infoDao.selectByKey(pi_id,ReturnParkTableName(area_code));
        	if(park_info == null ){
        		returnData.setReturnData(errorcode_data, "该停车场不存在", ""); 
    			return;
        	}
        	//检查该项规则是否之前已经录入过
        	Rental_charging_rule charging_rule =  queryChargeRuleByMd5( pi_id , start_price, start_time , charging ,  charging_time, rcr_type, rcr_discount, car_type, car_code_color, is_time_bucket);
        	if(charging_rule != null){
        		returnData.setReturnData(errorcode_data, "该停车场的该规则已经存在", charging_rule); 
    			return;
        	}
        	
        	//入库操作
        	Date date = new Date();
        	charging_rule =  new Rental_charging_rule();
        	charging_rule.setCar_displacement(car_displacement);
        	charging_rule.setCar_code_color(car_code_color);
        	charging_rule.setCar_type(car_type);
        	charging_rule.setCharging(charging);
        	charging_rule.setCharging_time(charging_time);
        	charging_rule.setCtime(date);
        	charging_rule.setIs_time_bucket(is_time_bucket);
        	charging_rule.setMonth_price(month_price);
        	charging_rule.setMonth_time(month_time);
        	charging_rule.setPermit_time(permit_time);
        	charging_rule.setPi_id(pi_id);
        	charging_rule.setRcr_discount(rcr_discount);
        	charging_rule.setRcr_state(rcr_state);
        	charging_rule.setRcr_type(rcr_type);
        	charging_rule.setStart_price(start_price);
        	charging_rule.setStart_time(start_time);
        	charging_rule.setTime_bucket(time_bucket);
        	charging_rule.setTimeout_info(timeout_info);
        	charging_rule.setUtime(date);
        	charging_rule.setArea_code(area_code);
        	int id = rental_charging_ruleDao.insert(charging_rule);
        	if(id < 1){
        		returnData.setReturnData(errorcode_data, "插入场地计费规则失败", ""); 
    			return;
        	}else{
        		//普通规则  租赁规则  rcr_type 停车类型 0：普通车位停车 1：时间段包月停车
    			if(rcr_type == 0){
    				//每次插入计费规则成功都去检查下 停车场数据表中 是否有 计费规则初始信息 如果没有则添加 如果有则不处理
            		if(RequestUtil.checkObjectBlank(park_info.getTimeout_info())){
            			//为空 则添加 首停2小时5元，之后每小时2元
            			String str = "首停%s小时%s元，之后每小时%s元";
            			str = String.format(str, (start_time*1.0)/60,(start_price*1.0)/100,(charging*1.0)/100);
            			park_info.setTimeout_info(str);
            			//更新停车场首基本停车规则信息
            			park_infoDao.updateByKey(park_info,ReturnParkTableName(area_code));
            		}
            		//补全停车场蓝牌小汽车的起步价
            		if(park_info.getMoney() < 1 && car_code_color == 1){
            			park_info.setMoney(start_price);
            			//更新停车场蓝牌小汽车起步价
            			park_infoDao.updateByKey(park_info,ReturnParkTableName(area_code));
            		}
            		
            		
    			}else{
    				if(RequestUtil.checkObjectBlank(park_info.getRent_info())){
	            		//时间段包月停车 准入时段 18:00-08:00，300元/月
	    				String str = "准入时段 %s，%s元/月";
	    				str = String.format(str, permit_time,(month_price*1.0)/100);
	    				park_info.setRent_info(str);
	    				park_info.setMonth_price(month_price);
	    				park_info.setIs_rent(1);
	    				
	        			//更新停车场首基本停车规则信息
	        			park_infoDao.updateByKey(park_info,ReturnParkTableName(area_code));
    				}
    			}
        	}
        	charging_rule.setRcr_id(id);
			returnData.setReturnData(errorcode_success, null, charging_rule); 
			return;
			
		} catch (Exception e) {
			log.error("ParkinfoBiz record_charging_rule is error", e);
			returnData.setReturnData(errorcode_systerm, "system is error", null); 
		}
	}
	
	/**
	 * 更新停车场规则
	 * @param returnData
	 * @param pi_id
	 * @param start_price
	 * @param start_time
	 * @param charging
	 * @param charging_time
	 * @param month_price
	 * @param month_time
	 * @param permit_time
	 * @param timeout_info
	 * @param rcr_type
	 * @param rcr_state
	 * @param rcr_discount
	 * @param car_displacement
	 * @param car_type
	 * @param car_code_color
	 * @param is_time_bucket
	 * @param time_bucket
	 */
	public void update_charging_rule(ReturnDataNew returnData,long rcr_id ,long pi_id,
			int start_price, int start_time, int charging, int charging_time,
			int month_price, int month_time, String permit_time,
			String timeout_info, int rcr_type, int rcr_state, int rcr_discount,
			String car_displacement, int car_type, int car_code_color,
			int is_time_bucket, String time_bucket,int is_default,String area_code) {
		// TODO Auto-generated method stub

		 try {
				//首先获取该条规则
				Rental_charging_rule charging_rule  =  rental_charging_ruleDao.selectByKey(rcr_id);
	        	if(charging_rule == null ){
	        		returnData.setReturnData(errorcode_data, "该停车场的该条规则不存在", ""); 
	    			return;
	        	}
	        	//更新操作
	        	Date date = new Date();
	        	charging_rule.setCar_displacement(car_displacement);
	        	charging_rule.setCar_code_color(car_code_color);
	        	charging_rule.setCar_type(car_type);
	        	charging_rule.setCharging(charging);
	        	charging_rule.setCharging_time(charging_time);
	        	charging_rule.setCtime(date);
	        	charging_rule.setIs_time_bucket(is_time_bucket);
	        	charging_rule.setMonth_price(month_price);
	        	charging_rule.setMonth_time(month_time);
	        	charging_rule.setPermit_time(permit_time);
	        	charging_rule.setPi_id(pi_id);
	        	charging_rule.setRcr_discount(rcr_discount);
	        	charging_rule.setRcr_state(rcr_state);
	        	charging_rule.setRcr_type(rcr_type);
	        	charging_rule.setStart_price(start_price);
	        	charging_rule.setStart_time(start_time);
	        	charging_rule.setTime_bucket(time_bucket);
	        	charging_rule.setTimeout_info(timeout_info);
	        	charging_rule.setUtime(date);
	        	charging_rule.setIs_default(is_default);
				String md5str = DigestUtils.md5Hex(String.valueOf(pi_id)+String.valueOf(start_price)+String.valueOf(start_time)+String.valueOf(charging)+String.valueOf(charging_time)
						+String.valueOf(rcr_type)+String.valueOf(rcr_discount)+String.valueOf(car_type)+String.valueOf(car_code_color)+String.valueOf(is_time_bucket));
	        	charging_rule.setRcr_md5(md5str);
	        	charging_rule.setArea_code(area_code);
	        	
	        	int count = rental_charging_ruleDao.updateByKey(charging_rule);
	        	if(count > 0){
	        		//更新场地规则默认信息
	        		if(is_default == 1){
	        			Park_info park_info = park_infoDao.selectByKey(pi_id,ReturnParkTableName(area_code));
	        			
	        			//普通规则  租赁规则  rcr_type 停车类型 0：普通车位停车 1：时间段包月停车
	        			if(rcr_type == 0){
	        				//普通车位停车
	        				//为空 则添加 首停2小时5元，之后每小时2元
	            			String str = "首停%s小时%s元，之后每小时%s元";
	            			str = String.format(str, (start_time*1.0)/60,(start_price*1.0)/100,(charging*1.0)/100);
	            			park_info.setTimeout_info(str);
	            			//更新停车场首基本停车规则信息
	            			park_infoDao.updateByKey(park_info,ReturnParkTableName(area_code));
	        			}else{
	        				//时间段包月停车 准入时段 18:00-08:00，300元/月
	        				String str = "准入时段 %s，%s元/月";
	        				str = String.format(str, permit_time,(month_price*1.0)/100);
	        				park_info.setRent_info(str);
	            			//更新停车场首基本停车规则信息
	            			park_infoDao.updateByKey(park_info,ReturnParkTableName(area_code));
	        			}
	        		}	        		
	        		returnData.setReturnData(errorcode_success, "更新成功", charging_rule); 
					return;
	        	}else{
	        		//更新失败
					returnData.setReturnData(errorcode_data, "更新失败", ""); 
					return;
	        	}
	        	
				
			} catch (Exception e) {
				log.error("ParkinfoBiz record_charging_rule is error", e);
				returnData.setReturnData(errorcode_systerm, "system is error", null); 
			}
	}
	
	
	/**
	 * 记录场地出入口设备对应关系信息
	 * @param returnData
	 * @param pi_id
	 * @param in_out
	 * @param in_out_code
	 * @param camera
	 * @param camera_mac
	 * @param signo_name
	 * @param solid_garage
	 * @param solid_garage_sn
	 */
	public void record_park_device(ReturnDataNew returnData, long pi_id,
			String in_out, String in_out_code, String camera,
			String camera_mac, String signo_name, String solid_garage_mac,
			String solid_garage_sn,String area_code) {
		// TODO Auto-generated method stub
        try {
        	//首先判断是否已经进行了录入  如果数据库中没有该停车场的数据则不进行插入  否则不予处理
        	//获取该场地的信息
        	Park_info park_info = park_infoDao.selectByKey(pi_id,ReturnParkTableName(area_code));
        	if(park_info == null ){
        		returnData.setReturnData(errorcode_data, "该停车场不存在", ""); 
    			return;
        	}
        	//检查该项规则是否之前已经录入过
        	Park_device park_device  = queryPark_deviceByMd5( pi_id , in_out, in_out_code, camera_mac, signo_name, solid_garage_mac,area_code);
        	if(park_device != null){
        		returnData.setReturnData(errorcode_data, "该停车场的该出入口设备记录已经存在", ""); 
    			return;
        	}
        	
        	//入库操作
        	Date date = new Date();
        	park_device =  new Park_device();
        	park_device.setCamera(camera);
        	park_device.setCamera_mac(camera_mac);
        	park_device.setCtime(date);
        	park_device.setIn_out(in_out);
        	park_device.setIn_out_code(in_out_code);
        	String md5str = DigestUtils.md5Hex(pi_id+in_out+in_out_code+camera_mac+signo_name+solid_garage_mac+area_code);
        	park_device.setPd_md5(md5str);
        	park_device.setPi_id(pi_id);
        	park_device.setSigno_name(signo_name);
        	park_device.setSolid_garage_mac(solid_garage_mac);
        	park_device.setSolid_garage_sn(solid_garage_sn);
        	park_device.setUtime(date);
        	park_device.setArea_code(area_code);
        	
        	int id = park_deviceDao.insert(park_device);
        	if(id < 1){
        		returnData.setReturnData(errorcode_data, "插入场地出入口设备关系失败", ""); 
    			return;
        	}
        	park_device.setPd_id(id);
			returnData.setReturnData(errorcode_success, null, park_device); 
			return;
			
		} catch (Exception e) {
			log.error("ParkinfoBiz record_park_device is error", e);
			returnData.setReturnData(errorcode_systerm, "system is error", null); 
		}
		
	}
	
	
	
	/**
	 * 更新场地出入口设备对应关系信息
	 * @param returnData
	 * @param pi_id
	 * @param in_out
	 * @param in_out_code
	 * @param camera
	 * @param camera_mac
	 * @param signo_name
	 * @param solid_garage_mac
	 * @param solid_garage_sn
	 */
	public void update_park_device(ReturnDataNew returnData,long pd_id, long pi_id,
			String in_out, String in_out_code, String camera,
			String camera_mac, String signo_name, String solid_garage_mac,
			String solid_garage_sn,String area_code) {
		// TODO Auto-generated method stub
        try {
        	//首先判断是否已经进行了录入  如果数据库中没有该停车场的数据则不进行插入  否则不予处理
        	//获取该场地的信息
        	Park_device park_device = park_deviceDao.selectByKey(pd_id);
        	if(park_device == null ){
        		returnData.setReturnData(errorcode_data, "该停车场该出入口的设备不存在", ""); 
    			return;
        	}
        	//更新操作
        	Date date = new Date();
        	park_device.setCamera(camera);
        	park_device.setCamera_mac(camera_mac);
        	park_device.setCtime(date);
        	park_device.setIn_out(in_out);
        	park_device.setIn_out_code(in_out_code);
        	String md5str = DigestUtils.md5Hex(pi_id+in_out+in_out_code+camera_mac+signo_name+solid_garage_mac+area_code);
        	park_device.setPd_md5(md5str);
        	park_device.setPi_id(pi_id);
        	park_device.setSigno_name(signo_name);
        	park_device.setSolid_garage_mac(solid_garage_mac);
        	park_device.setSolid_garage_sn(solid_garage_sn);
        	park_device.setUtime(date);
        	park_device.setArea_code(area_code);
        	
        	int count = park_deviceDao.updateByKey(park_device);
        	if(count < 1){
        		returnData.setReturnData(errorcode_data, "更新场地出入口设备关系失败", ""); 
    			return;
        	} 
			returnData.setReturnData(errorcode_success, null, park_device); 
			return;
			
		} catch (Exception e) {
			log.error("ParkinfoBiz update_park_device is error", e);
			returnData.setReturnData(errorcode_systerm, "system is error", null); 
		}
	}
	
	/**
	 * 车辆入库出库记录
	 * @param returnData
	 * @param pi_id
	 * @param ui_id
	 * @param car_code
	 * @param is_enter
	 * @param in_out
	 * @param in_out_code
	 */
	public void record_car_in_out(ReturnDataNew returnData, long pi_id,long pd_id, String car_code, int is_enter, String in_out,
			String in_out_code,int car_type,int car_code_color,String area_code,int out_type,int is_local_month) {
		// TODO Auto-generated method stub
        try {
        	//首先判断是否已经进行了录入  如果数据库中没有该停车场的数据则不进行插入  否则不予处理
        	//获取该场地的信息
        	Park_info park_info = park_infoDao.selectByKey(pi_id,ReturnParkTableName(area_code));
        	if(park_info == null ){
        		returnData.setReturnData(errorcode_data, "该停车场不存在", "1"); 
    			return;
        	}
        	//用户ID 通过车牌号获取用户ID 
        	long ui_id = 0;
			User_carcode user_carcode = carBiz.queryUserCarBycode(car_code);
			if(user_carcode != null){
				ui_id = user_carcode.getUi_id();
			}
        	//入库或者出库操作
        	Date date = new Date();
        	Car_in_out car_in_out =  new Car_in_out();
        	car_in_out.setCtime(date);
        	car_in_out.setIn_out(in_out);
        	car_in_out.setIn_out_code(in_out_code);
        	car_in_out.setPi_id(pi_id);
        	car_in_out.setUtime(date);
        	car_in_out.setCar_code(car_code);
        	car_in_out.setIs_enter(is_enter);
        	//获取是该停车场的 出入口设备信息表的主键ID
        	car_in_out.setPd_id(pd_id);
        	car_in_out.setUi_id(ui_id);
        	car_in_out.setUtime(date);
        	car_in_out.setCar_type(car_type);
        	car_in_out.setCar_code_color(car_code_color);
        	//属于哪个省市区代码
        	car_in_out.setArea_code(park_info.getArea_code()); 
        	//出库类型 0:正常出库 1：现金支付出库 2：异常出库
        	car_in_out.setOut_type(out_type);
        	//是否是道闸本地包月车辆 0:不是 1：是
        	car_in_out.setIs_local_month(is_local_month);
        	
        	if(is_local_month == 0){
        		int type = 1;//type 1:临停 2：租赁  3：预约  4：取消预约
        		//不是本地包月道闸停车
            	/**
            	 * 检查是否是该停车场的租赁车辆
            	 */
            	Pay_month_park pay_month_park = payParkPB.queryRentOrder(pi_id,car_code,area_code,ui_id,date);
            	if(pay_month_park != null){
            		type = 2;
            		
            		car_in_out.setOrder_id(pay_month_park.getMy_order());
            		car_in_out.setIs_rent(1);
            		car_in_out.setRent_remain_time(pay_month_park.getEnd_time().getTime()-date.getTime());
            		if(is_enter == 0){
            			//入库
            			pay_month_park.setArrive_time(date);
            			int count = pay_month_parkDao.updateByKey(pay_month_park);
                    	if(count != 1){
                    		//更新失败
                    		returnData.setReturnData(errorcode_data, "更新租赁分时间段包月当天入库时间失败", "2"); 
            				return;
                    	}
            		}
            	}
            	
             	//这里更新车辆出入库的数量变化
            	parkInfoPB.upCarNum(park_info, is_enter,type);
            	
        	}

        	int id = car_in_outDao.insert(car_in_out);
        	if(id < 1){
        		returnData.setReturnData(errorcode_data, "车辆入库出库记录失败", "3"); 
    			return;
        	}
        	//设置主键ID
        	car_in_out.setCio_id(id);
   
        	
        	if(is_local_month == 0){
        		//不是   道闸停车本地包月
        		try {
            		//事物处理  该车辆出入的细节
            		baseTransaction.getCarTransaction().doCar_In_Out(car_in_out, park_info,returnData);
                	//更新出库记录---把订单号更新
                	car_in_outDao.updateByKey(car_in_out);
        		} catch (Exception e) {
    				// TODO Auto-generated catch block
    				log.error("空闲车位数量变更 错误",e); 
    			}
        	}

            //返回结果
			returnData.setReturnData(errorcode_success, null, car_in_out); 
			return;
			
		} catch (Exception e) {
			log.error("ParkinfoBiz record_car_in_out is error", e);
			returnData.setReturnData(errorcode_systerm, "system is error", null); 
		}
	}
	
	
	/**
	 * 故障上报记录
	 * @param returnData
	 * @param pi_id
	 * @param pd_id
	 * @param fr_type
	 * @param fr_desc
	 */
	public void record_fault_record(ReturnDataNew returnData, long pi_id,
			long pd_id, int fr_type, String fr_desc,String area_code) {
		// TODO Auto-generated method stub
        try {
        	//首先判断是否已经进行了录入  如果数据库中没有该停车场的数据则不进行插入  否则不予处理
        	//获取该场地的信息
        	Park_info park_info = park_infoDao.selectByKey(pi_id,ReturnParkTableName(area_code));
        	if(park_info == null ){
        		returnData.setReturnData(errorcode_data, "该停车场不存在", ""); 
    			return;
        	}
        	//入库操作
        	Date date = new Date();
        	Fault_record fault_record =  new Fault_record();
        	fault_record.setCtime(date);
        	fault_record.setPi_id(pi_id);
        	//获取是该停车场的 出入口设备信息表的主键ID
        	fault_record.setPd_id(pd_id);
        	fault_record.setUtime(date);
        	fault_record.setFr_desc(fr_desc);
        	fault_record.setFr_type(fr_type);
        	fault_record.setArea_code(area_code);
        	int id = fault_recordDao.insert(fault_record);
        	if(id < 1){
        		returnData.setReturnData(errorcode_data, "故障上报记录失败", ""); 
    			return;
        	}
        	fault_record.setFr_id(id);
			returnData.setReturnData(errorcode_success, null, fault_record); 
			return;
			
		} catch (Exception e) {
			log.error("ParkinfoBiz record_fault_record is error", e);
			returnData.setReturnData(errorcode_systerm, "system is error", null); 
		}
	}

	
    /**
     * 通过GPS导航获取 该经纬度范围内的停车场数据列表
     * @param returnData
     * @param dtype
     * @param ui_id
     * @param lng
     * @param lat
     */
	@TargetDataSource(value = DynamicDataSourceHolder.SLAVE)
	public void ReturnRead_gpspark(ReturnDataNew returnData, int dtype,
			long ui_id, double lng, double lat,int distance ,int park_type,int type,String area_code ) {
		// TODO Auto-generated method stub Distance(double long1, double lat1, double long2,double lat2)
		try {
			if(distance == 0){
				distance = 500;//米
			}
			//首先根据经纬度从数据库中筛选出  范围在 上下不超过0.1 的停车场数据
			String sql = "";
			if(type == 0){
				sql = "SELECT *,ROUND(6378.138*2*ASIN(SQRT(POW(SIN((?*PI()/180-lat*PI()/180)/2),2)+COS(?*PI()/180)*COS(lat*PI()/180)*POW(SIN((?*PI()/180-lng*PI()/180)/2),2)))*1000) AS juli "
						//+ " FROM "+ReturnParkTableName(area_code)+" having juli < ? and park_type=? "
						+ " FROM "+ReturnParkTableName(area_code)+"   having juli < ? and pi_state=1  "
						+ " ORDER BY juli ASC LIMIT 200";
			}else{
				sql = "SELECT *,ROUND(6378.138*2*ASIN(SQRT(POW(SIN((?*PI()/180-lat*PI()/180)/2),2)+COS(?*PI()/180)*COS(lat*PI()/180)*POW(SIN((?*PI()/180-lng*PI()/180)/2),2)))*1000) AS juli "
						//+ " FROM "+ReturnParkTableName(area_code)+" having juli < ? and park_type=? "
						+ " FROM "+ReturnParkTableName(area_code)+"   having juli < ?  and pi_state=1  "
						+ " ORDER BY month_price ASC LIMIT 200";
			}
//			List<Park_info> list = getMySelfService().queryListT(sql, Park_info.class, lat,lat,lng,distance,park_type);
			List<Park_info> list = getMySelfService().queryListT(sql, Park_info.class, lat,lat,lng,distance);
			 //返回结果
			returnData.setReturnData(errorcode_success, "查询附近的停车场信息成功", list); 
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("ParkinfoBiz.ReturnRead_gpspark is error"+e.getMessage(), e);
		}
	}
    
    /**
     * 读取用户预约下单租赁包月车位(车位租赁)详情页
     * @param returnData
     * @param dtype
     * @param ui_id
     * @param pi_id
     * @param pay_type
     */
	public void read_rent_order(ReturnDataNew returnData, int dtype,
			long ui_id, long pi_id,String area_code) {
		// TODO Auto-generated method stub
		try {
			//获取 该停车场规则详细
			int rcr_type = 1;//停车类型 0：普通车位停车 1：时间段包月停车
			Rental_charging_rule  charging_rule  = queryChargeRule(pi_id,rcr_type,1,area_code);
			//返回结果
			returnData.setReturnData(errorcode_success, "查询停车场租赁规则信息成功", charging_rule); 
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("ParkinfoBiz.read_rent_order is error"+e.getMessage(), e);
		}
	}
    /**
     * 读取用户预约下单普通车位 需要的订单准备数据(普通车位停车)详情页
     * @param returnData
     * @param dtype
     * @param ui_id
     * @param pi_id
     */
	@TargetDataSource(value = DynamicDataSourceHolder.SLAVE)
	public void read_expect_order(ReturnDataNew returnData, int dtype,
			long ui_id, long pi_id,String area_code) {
		// TODO Auto-generated method stub
		try {
			//获取 该停车场规则详细
			int rcr_type = 0;//停车类型 0：普通车位停车 1：时间段包月停车
			Rental_charging_rule  charging_rule  = queryChargeRule(pi_id,rcr_type,1,area_code);
			if(charging_rule == null){
				returnData.setReturnData(errorcode_data, "很抱歉亲！，该停车场已关闭预约功能。", "","1"); 
				return;
			}
			//返回结果
			returnData.setReturnData(errorcode_success, "停车场收费规则信息成功", charging_rule); 
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("ParkinfoBiz.ReturnRead_gpspark is error"+e.getMessage(), e);
		}
	}
	
	
	
    /**
     * 通过GPS导航获取 该经纬度范围内的停车场数据列表 ---- 车位租赁
     * @param returnData
     * @param dtype
     * @param ui_id
     * @param lng
     * @param lat
     * @param distance
     * @param type  0 :按距离 1：按价格
     */
	public void ReturnRead_gpspark_rent(ReturnDataNew returnData, int dtype,
			long ui_id, double lng, double lat, int distance,int type,int park_type,String area_code ) {
		// TODO Auto-generated method stub
		try {
			if(distance == 0){
				distance = 500;//米
			}
			//首先根据经纬度从数据库中筛选出  范围在 上下不超过0.1 的停车场数据
			String sql = "";
			if(type == 0){
				sql = "SELECT *,ROUND(6378.138*2*ASIN(SQRT(POW(SIN((?*PI()/180-lat*PI()/180)/2),2)+COS(?*PI()/180)*COS(lat*PI()/180)*POW(SIN((?*PI()/180-lng*PI()/180)/2),2)))*1000) AS juli FROM "
			+ReturnParkTableName(area_code)+" having juli < ? and park_type=? and is_rent=1 and month_price>0 and pi_state=1 "
						+ " ORDER BY juli ASC LIMIT 200";
			}else{
				sql = "SELECT *,ROUND(6378.138*2*ASIN(SQRT(POW(SIN((?*PI()/180-lat*PI()/180)/2),2)+COS(?*PI()/180)*COS(lat*PI()/180)*POW(SIN((?*PI()/180-lng*PI()/180)/2),2)))*1000) AS juli FROM "
			+ReturnParkTableName(area_code)+" having juli < ? and park_type=? and is_rent=1 and month_price>0 and pi_state=1  "
						+ " ORDER BY month_price ASC LIMIT 200";
			}
			List<Park_info> list = getMySelfService().queryListT(sql, Park_info.class, lat,lat,lng,distance,park_type);
			 //返回结果
			returnData.setReturnData(errorcode_success, "查询附近的租赁停车场信息成功", list); 
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("ParkinfoBiz.ReturnRead_gpspark is error"+e.getMessage(), e);
		}
	}
	/**
	 * 开闸   
	 * @param returnData
	 * @param pi_id
	 * @param car_code
	 * @param car_type
	 * @param car_code_color
	 * @param area_code
	 * @param is_open
	 * @param is_cash
	 */
	public void open_signo(ReturnDataNew returnData, long pi_id, String car_code,
			int car_type, int car_code_color, String area_code,
			int is_cash) {
		// TODO Auto-generated method stub
        try {
        	//首先判断是否已经进行了录入  如果数据库中没有该停车场的数据则不进行插入  否则不予处理
        	//获取该场地的信息
        	Park_info park_info = park_infoDao.selectByKey(pi_id,ReturnParkTableName(area_code));
        	if(park_info == null ){
        		returnData.setReturnData(errorcode_data, "该停车场不存在", ""); 
    			return;
        	}
			/**
			 * 检查该车牌号 该次订单是否已经支付 如果已经在线支付了 那么就只记录该次订单的车辆已经开闸出库和开闸时间
			 * 如果没有在线支付 那么就是现金支付 则更改支付状态为已经支付 
			 */
			//获取订单信息
			String sql = "select *  from pay_park where pi_id = ? and area_code=? and car_code=?    and is_del=0 and cancel_state=0 order by  ctime desc limit 1";
			Pay_park pay_park = getMySelfService().queryUniqueT(sql, Pay_park.class, pi_id,area_code,car_code);
			
			if(pay_park == null){
				returnData.setReturnData(errorcode_data, "没有该车辆的订单信息", "","1"); 
    			return;
			}
			
			if(pay_park.getPp_state() == 1){
				//已经在线支付
				//更新开闸时间和开闸状态
				pay_park.setOpen_time(new Date());
				pay_park.setIs_open(1);//是否开闸 0:未开闸 1：已开闸
				pay_park.setIs_cash(is_cash);
				pay_park.setIs_over(1);//订单是否完成(0:没有完成1：完成)
			}else{
				//更新开闸时间和开闸状态
				pay_park.setOpen_time(new Date());
				pay_park.setIs_open(1);//是否开闸 0:未开闸 1：已开闸
				pay_park.setIs_over(1);//订单是否完成(0:没有完成1：完成)
				pay_park.setPp_state(1);//支付状态 0:未支付 1：已经支付
				pay_park.setIs_cash(is_cash);//是否现金支付 0：在线支付  1：现金支付
			}
			int count = pay_parkDao.updateByKey(pay_park);
			if(count < 1){
				//更新失败
				returnData.setReturnData(errorcode_data, "更新该车辆该次订单的开闸记录信息失败", "","2"); 
    			return;
			}

            //返回结果
			returnData.setReturnData(errorcode_success, "更新该车辆该次订单的开闸记录信息成功", ""); 
			return;
			
		} catch (Exception e) {
			log.error("ParkinfoBiz open_signo is error", e);
			returnData.setReturnData(errorcode_systerm, "system is error", null); 
		}
	}
	
	   /**
	    * 露天停车场PDA帐号信息和经纬度 MAC 的初始化
	    * @param returnData
	    * @param lng
	    * @param lat
	    * @param loginname
	    * @param password
	    * @param mac
	    * @param park_type
	    * @param area_code
	    */
		public void init_pda(ReturnDataNew returnData, double lng, double lat,
				String loginname, String password, String mac, int park_type,
				String area_code) {
			// TODO Auto-generated method stub
	        try {
	        	//获取该场地的信息
	        	Park_info park_info =  QueryByLoginName( area_code, loginname, password,park_type);
	        	if(park_info == null ){
	        		returnData.setReturnData(errorcode_data, "该停车场不存在", ""); 
	    			return;
	        	}
	        	//初始化操作
	        	Date date = new Date();
	            if(lat > 0){
	            	if(park_info.getLat() > 0){
	            		
	            	}else{
	            		park_info.setLat(lat);
	            	}
	            	
	        	}
	            if(lng > 0){
	            	if(park_info.getLng() > 0){
	            		
	            	}else{
	            		park_info.setLng(lng);
	            	}
	            	
	        	}
	            //设置MAC
	            String macs = park_info.getMac();
	            if(RequestUtil.checkObjectBlank(macs)){
	            	park_info.setMac(mac);
	            }else{
	            	/*String[] mac_arry = macs.split(",");
	            	if(mac_arry != null && mac_arry.length > 0){
	            		StringBuffer sb = new StringBuffer();
	            		for (String ma : mac_arry) {  
	            			sb.append(ma).append(",");
						}
	            		park_info.setMac(sb.append(mac).toString()); 
	            	}*/
	            	if(!macs.equalsIgnoreCase(mac)){
	            		returnData.setReturnData(errorcode_data, "PDA不匹配", ""); 
						return;
	            	}
	            }
	        	park_info.setUtime(date);
	        	int count = park_infoDao.updateByKey(park_info,ReturnParkTableName(area_code));
	        	if(count > 0){
	        		returnData.setReturnData(errorcode_success, "初始化成功", park_info); 
					return;
	        	}else{
	        		//更新失败
					returnData.setReturnData(errorcode_data, "初始化失败", ""); 
					return;
	        	}
				
			} catch (Exception e) {
				log.error("ParkinfoBiz init_pda is error", e);
				returnData.setReturnData(errorcode_systerm, "system is error", null); 
			}
		}
		
		
	   /**
	    * 用户重置PDA密码
	    * @param returnData
	    * @param dtype
	    * @param tel
	    * @param verify_code
	    * @param verify_list
	    * @param vclass
	    * @param password
	    * @param repassword
	    */
		public void change_pass_pda(ReturnDataNew returnData, int dtype, String tel,
				String verify_code, String verify_list, String vclass, String password,
				String repassword,String area_code,int park_type,String loginname) {
			// TODO Auto-generated method stub
	        try {
	        	//获取该场地的信息
	        	Park_info park_info =  QueryByLoginLoginName( area_code, loginname,park_type);
	        	if(park_info == null ){
	        		returnData.setReturnData(errorcode_data, "该停车场不存在", ""); 
	    			return;
	        	}
	        	
				//从什么设备发出的请求1-收音机2-手机APP 3-WEB
				//进行验证码的验证
				String sql = "SELECT v_code,v_time FROM sms_validate where v_tel = ? and v_list = ? and v_class = ?  limit 1";
				Sms_validate bsv = getMySelfService().queryUniqueT(sql, Sms_validate.class, park_info.getLinkman_tel(),verify_list,vclass);
				if(bsv == null){
					returnData.setReturnData(errorcode_data, "验证错误", null); 
					return ;
				}
				//检查时间是否过期
				long time = Long.parseLong(bsv.getV_time()+"000");
				if(System.currentTimeMillis() - time > 6*60*1000){
					returnData.setReturnData(errorcode_data, "验证码过期", null); 
					return ;
				}
	        	//初始化操作
	        	Date date = new Date();
	        	park_info.setUtime(date);
	        	park_info.setPassword(password);
	        	int count = park_infoDao.updateByKey(park_info,ReturnParkTableName(area_code));
	        	if(count > 0){
	        		returnData.setReturnData(errorcode_success, "重置密码成功", park_info); 
					return;
	        	}else{
	        		//更新失败
					returnData.setReturnData(errorcode_data, "重置密码失败", ""); 
					return;
	        	}
				
			} catch (Exception e) {
				log.error("ParkinfoBiz init_pda is error", e);
				returnData.setReturnData(errorcode_systerm, "system is error", null); 
			}
		}
		
		
		/**
		 * 读取停车场计费规则信息   
		 * @param returnData
		 * @param pi_id
		 * @param area_code
		 */
		@TargetDataSource(value = DynamicDataSourceHolder.SLAVE)
		public void read_charging_rule(ReturnDataNew returnData, long pi_id,
				String area_code) {
			// TODO Auto-generated method stub
			try {
				//首先获取该停车场规则
				List<Rental_charging_rule> list = queryChargeRule( pi_id, area_code);
				if(list == null || list.size() == 0){
					returnData.setReturnData(errorcode_data, "该停车场还没有制定规则", ""); 
	    			return;
				}
				
        		returnData.setReturnData(errorcode_success, "读取停车场计费规则信息成功", list); 
				return;
			} catch (Exception e) {
				log.error("ParkinfoBiz read_charging_rule is error", e);
				returnData.setReturnData(errorcode_systerm, "system is error", null); 
			}
		}
	/**************************分离出来的方法*****************************/
   /**
    * 通过停车场名字查询出停车场信息
    * @param type : true 精确查找  false 模糊查询
    * @param name
    * @return
    */
	public List<Park_info> queryByName(boolean type,String name,String address_name,String area_code){
		List<Park_info> list = null;
		try {
			if(type){
				//精确查询
				String sql = "select *  from "+ReturnParkTableName(area_code)+" where pi_name=? and  address_name=?";
				list = getMySelfService().queryListT(sql, Park_info.class, name,address_name);
			}else{
				//模糊查询
				String sql = "select *  from "+ReturnParkTableName(area_code)+" where pi_name like '%?%' and  address_name like '%?%'";
				list = getMySelfService().queryListT(sql, Park_info.class, name,address_name);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("queryByName is error"+e.getMessage(), e); 
		}
		return list;
	}
    
	   /**
	    * 通过停车场名字查询出停车场信息
	    * @param name
	    * @return
	    */
		public Park_info queryByNameT(String name,String address_name,String area_code){
			try {
					//精确查询
					String sql = "select *  from "+ReturnParkTableName(area_code)+" where pi_name=? and  address_name=?";
					return getMySelfService().queryUniqueT(sql, Park_info.class, name,address_name);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("queryByNameT is error"+e.getMessage(), e); 
			}
			return null;
		}
	
	
	/**
	 * 通过参数校验 检查该规则是否已经录入过
	 * @param pi_id
	 * @param start_price
	 * @param start_time
	 * @param charging
	 * @param charging_time
	 * @param rcr_type
	 * @param rcr_discount
	 * @param car_type
	 * @param car_code_color
	 * @param is_time_bucket
	 * @return
	 */
	public Rental_charging_rule queryChargeRuleByMd5(long pi_id ,int start_price,int start_time ,int charging ,int  charging_time,int rcr_type,int rcr_discount,int car_type,int car_code_color,int is_time_bucket){
		try {
			String md5str = DigestUtils.md5Hex(String.valueOf(pi_id)+String.valueOf(start_price)+String.valueOf(start_time)+String.valueOf(charging)+String.valueOf(charging_time)
					+String.valueOf(rcr_type)+String.valueOf(rcr_discount)+String.valueOf(car_type)+String.valueOf(car_code_color)+String.valueOf(is_time_bucket));
			String sql = "select *  from  rental_charging_rule where pi_id=? and  rcr_md5=?";
			Rental_charging_rule cr = getMySelfService().queryUniqueT(sql, Rental_charging_rule.class,pi_id,md5str);
			return cr;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("queryChargeRuleByMd5 is error "+e.getMessage(), e);
		}
		return null; 
	}
	
	
	/**
	 * 查询场地规则数据
	 * @param pi_id
	 * @param rcr_type ：停车类型 0：普通车位停车 1：时间段包月停车
	 * @param car_type 车牌类型 0：未知车牌:、1：蓝牌小汽车、2：: 黑牌小汽车、3：单排黄牌、4：双排黄牌、 5： 警车车牌、6：武警车牌、7：个性化车牌、8：单 排军车牌、9：双排军车牌、10：使馆车牌、11： 香港进出中国大陆车牌、12：农用车牌、13：教 练车牌、14：澳门进出中国大陆车牌、15：双层 武警车牌、16：武警总队车牌、17：双层武警总 队车牌
	 * @param car_code_color 车牌颜色 0：未知、1：蓝色、2：黄色、3：白色、 4：黑色、5：绿色
	 * @return
	 */
	public Rental_charging_rule queryChargeRule(long pi_id,int rcr_type,int car_type,String area_code){
		try {
			String sql = "select *  from  rental_charging_rule where pi_id=? and area_code=? and rcr_state=0 and rcr_type=? and car_code_color=1 and car_type=? limit 1";
			Rental_charging_rule cr = getMySelfService().queryUniqueT(sql, Rental_charging_rule.class,pi_id,area_code,rcr_type,car_type);
			return cr; 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("queryRentChargeRuleByPiId is error "+e.getMessage(), e);
		}
		return null; 
	}
	
	public Park_device queryPark_deviceByMd5(long pi_id ,String in_out,String in_out_code,String camera_mac,String signo_name,String solid_garage_mac,String area_code){
		try {
			String md5str = DigestUtils.md5Hex(pi_id+in_out+in_out_code+camera_mac+signo_name+solid_garage_mac+area_code);
			String sql = "select *  from  park_device where pi_id=? and area_code=? and  pd_md5=?";
			Park_device cr = getMySelfService().queryUniqueT(sql, Park_device.class,pi_id,area_code,md5str);
			return cr;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("queryPark_deviceByMd5 is error "+e.getMessage(), e);
		}
		return null; 
	}
	

    
    
    
   /**
    * 获取用户下单
    * @param ui_id 用户ID
    * @param pi_id 车库表主键ID
    * @param car_code 车牌号
    * @param order_type 下单类型 0: 普通下单  1：预约下单
    * @return
    */
   public Pay_park 	QueryPayPark(long ui_id,long pi_id,String car_code,int order_type){
	    //入库之前先检查该用户对该车辆进行该停车场预约下单没有   如果已经进行了预约下单则直接更新对应得数据
		try {
			Pay_park pay_park = null;
			String sql = "select *  from pay_park where pi_id = ? and car_code=? and ui_id=? and pp_state=0 and  order_type=?  and is_del=0 order by  ctime desc limit 1";
			pay_park = getMySelfService().queryUniqueT(sql, Pay_park.class, pi_id,car_code,ui_id,order_type);
			return pay_park;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("	QueryPayPark(long ui_id,long pi_id,String car_code,int order_type) is error"+e.getMessage(), e); 
		}
		return null;
   }
   


   /**
    * 通过露天停车场的帐号和密码 区域编码获取该停车场信息
    */
   public Park_info  QueryByLoginName(String area_code,String loginname,String password,int park_type){
	   try {
			//精确查询
			String sql = "select *  from "+ReturnParkTableName(area_code)+" where park_type=? and loginname=? and  password=? limit 1";
			return getMySelfService().queryUniqueT(sql, Park_info.class,park_type, loginname,password);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		log.error("queryByNameT is error"+e.getMessage(), e); 
	}
	return null;
   }

   /**
    * 通过露天停车场的帐号和密码 区域编码获取该停车场信息
    */
   public Park_info  QueryByLoginLoginName(String area_code,String loginname,int park_type){
	   try {
			//精确查询
			String sql = "select *  from "+ReturnParkTableName(area_code)+" where park_type=? and loginname=?  limit 1";
			return getMySelfService().queryUniqueT(sql, Park_info.class,park_type, loginname);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		log.error("queryByNameT is error"+e.getMessage(), e); 
	}
	return null;
   }

   
	/**
	 * 查询场地规则数据
	 * @param pi_id
	 * @return
	 */
	public List<Rental_charging_rule> queryChargeRule(long pi_id,String area_code){
		try {
			String sql = "select *  from  rental_charging_rule where pi_id=? and area_code=?";
			List<Rental_charging_rule> cr = getMySelfService().queryListT(sql, Rental_charging_rule.class, pi_id,area_code);
			return cr;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("queryRentChargeRuleByPiId is error "+e.getMessage(), e);
		}
		return null; 
	}




}
