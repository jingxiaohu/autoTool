
package com.park.mvc.v1.action.park;

import java.net.URLDecoder;

import org.apache.struts2.convention.annotation.Action;

import com.park.bean.ReturnDataNew;
import com.park.core.Constants;
import com.park.mvc.v1.action.BaseV1Action;
import com.park.util.RequestUtil;

/**
 * 记录停车场计费规则信息
 * @author jingxiaohu
 *
 */
public class Write_rental_charging_ruleAction extends BaseV1Action {


	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/ 
	private static final long serialVersionUID = -3599663972160625262L;


	/**
	 * 记录停车场计费规则信息
	 * @return
	 */
	@Action(value = "charging_rule")
	public String charging_rule(){
		ReturnDataNew returnData = new ReturnDataNew();
		 try{
			
			//检查是否是合法请求
			if(!checkRequest()){
				returnData.setReturnData(errorcode_param, " illegal request code", null);
				sendResp(returnData);
				return null;
			}
			if(pi_id < 1){
				//场地主键ID
				returnData.setReturnData(errorcode_param, " pi_id="+pi_id+"  pi_id is smaller than zero", null);
				sendResp(returnData);
				return null;
			}
			
			if(start_price < 1){
				//起步价（RMB 单位 分）
				returnData.setReturnData(errorcode_param, " start_price="+start_price+"  start_price is smaller than zero", null);
				sendResp(returnData);
				return null;
			}
			if(start_time < 1){
				//start_time;//起步时长（单位 分钟）
				returnData.setReturnData(errorcode_param, " start_time="+start_time+"  start_time is smaller than zero", null);
				sendResp(returnData);
				return null;
			}
			if(charging < 1){
				//charging;//计费价(RMB  单位分)
				returnData.setReturnData(errorcode_param, " charging="+charging+"  charging is smaller than zero", null);
				sendResp(returnData);
				return null;
			}
			if(charging_time < 1){
				//charging_time;//计费时长（单位 分钟）
				
				returnData.setReturnData(errorcode_param, " charging_time="+charging_time+"  charging_time is smaller than zero", null);
				sendResp(returnData);
				return null;
			}
			/*if(month_price < 1){
				//month_price;//包月价格(单位分)
				returnData.setReturnData(errorcode_param, " month_price="+month_price+"  month_price is smaller than zero", null);
				sendResp(returnData);
				return null;
			}
			if(month_time < 1){
				//month_time;//包月时长(天)
				returnData.setReturnData(errorcode_param, " month_time="+month_time+"  month_time is smaller than zero", null);
				sendResp(returnData);
				return null;
			}
			if(RequestUtil.checkObjectBlank(permit_time)){
				//permit_time;//准入时间段17:00-08:30
				returnData.setReturnData(errorcode_param, " permit_time="+permit_time+"  permit_time is null", null);
				sendResp(returnData);
				return null;
			}*/
			if(RequestUtil.checkObjectBlank(timeout_info)){
				//timeout_info;//超时费率(首停2小时3元，之后1元/小时)
				returnData.setReturnData(errorcode_param, " timeout_info="+timeout_info+"  timeout_info is null", null);
				sendResp(returnData);
				return null;
			}else{
				timeout_info = URLDecoder.decode(timeout_info, Constants.SYSTEM_CHARACTER);
			}
			
			/*if(RequestUtil.checkObjectBlank(car_displacement)){
				//car_displacement;//车辆排量
				returnData.setReturnData(errorcode_param, " car_displacement="+car_displacement+"  car_displacement is null", null);
				sendResp(returnData);
				return null;
			}*/
			if(RequestUtil.checkObjectBlank(time_bucket)){
				//time_bucket;//时间段收费  例如：白天 9：00-12：00 每小时2元
//				returnData.setReturnData(errorcode_param, " time_bucket="+time_bucket+"  time_bucket is null", null);
//				sendResp(returnData);
//				return null;
			}else{
				time_bucket = URLDecoder.decode(time_bucket, Constants.SYSTEM_CHARACTER);
			}
			
			if(!RequestUtil.checkObjectBlank(area_code)){
				//省市县编号 140107
				//避免汉子的问题
				area_code = URLDecoder.decode(area_code, Constants.SYSTEM_CHARACTER);
			}
			
			String sign_str = getSignature(Constants.SYSTEM_KEY, dtype,pi_id,start_price,charging);
			if(!sign.equalsIgnoreCase(sign_str)){
				log.error("sign="+sign+"  sign_str="+sign_str);
				returnData.setReturnData(errorcode_param, " sign is not right", null);
				sendResp(returnData);
				return null;
			}
			parkinfoBiz.record_charging_rule(returnData,pi_id,start_price,start_time,charging,charging_time,month_price,month_time,permit_time,timeout_info,rcr_type,rcr_state,rcr_discount,car_displacement,car_type,car_code_color,is_time_bucket,time_bucket,area_code);
			
			sendResp(returnData);
			return null;
			
			} catch (Exception e) {
				log.error("Write_rental_charging_ruleAction charging_rule  is error(DEVICE-JAVA)- P",e);
				returnData.setReturnData(errorcode_systerm, "system is error", ""); 
			}
			sendResp(returnData);
			return null; 
	}
	
	
	/**
	 * 更新停车场规则
	 * @return
	 */
	@Action(value = "update_charging_rule")
	public String update_charging_rule(){
		ReturnDataNew returnData = new ReturnDataNew();
		 try{
			
			//检查是否是合法请求
			if(!checkRequest()){
				returnData.setReturnData(errorcode_param, " illegal request code", null);
				sendResp(returnData);
				return null;
			}
			if(rcr_id < 1){
				//rcr_id;//规则表主键ID
				returnData.setReturnData(errorcode_param, " rcr_id="+rcr_id+"  rcr_id is smaller zero", null);
				sendResp(returnData);
				return null;
			}
			if(!RequestUtil.checkObjectBlank(timeout_info)){
				timeout_info = URLDecoder.decode(timeout_info, Constants.SYSTEM_CHARACTER);
			}
			
			if(!RequestUtil.checkObjectBlank(time_bucket)){
				time_bucket = URLDecoder.decode(time_bucket, Constants.SYSTEM_CHARACTER);
			}
			
			if(!RequestUtil.checkObjectBlank(area_code)){
				//省市县编号 140107
				//避免汉子的问题
				area_code = URLDecoder.decode(area_code, Constants.SYSTEM_CHARACTER);
			}
			String sign_str = getSignature(Constants.SYSTEM_KEY, dtype,rcr_id);
			if(!sign.equalsIgnoreCase(sign_str)){
				log.error("sign="+sign+"  sign_str="+sign_str);
				returnData.setReturnData(errorcode_param, " sign is not right", null);
				sendResp(returnData);
				return null;
			}
			parkinfoBiz.update_charging_rule(returnData,rcr_id,pi_id,start_price,start_time,charging,charging_time,month_price,month_time,permit_time,timeout_info,rcr_type,rcr_state,rcr_discount,car_displacement,car_type,car_code_color,is_time_bucket,time_bucket,is_default,area_code);
			
			sendResp(returnData);
			return null;
			
			} catch (Exception e) {
				log.error("Write_rental_charging_ruleAction update_charging_rule  is error(DEVICE-JAVA)- P",e);
				returnData.setReturnData(errorcode_systerm, "system is error", ""); 
			}
			sendResp(returnData);
			return null; 
	}
	
	/********************接收参数区*************************/
	private String area_code;//省市县编号 140107
	
	private long pi_id;//场地主键ID
	private int start_price;//起步价（RMB 单位 分）
	private int start_time;//起步时长（单位 分钟）
	private int charging;//计费价(RMB  单位分)
	private int charging_time;//计费时长（单位 分钟）
	private int month_price;//包月价格(单位分)
	private int month_time;//包月时长(天)
	private String permit_time;//准入时间段17:00-08:30
	private String timeout_info;//超时费率(首停2小时3元，之后1元/小时)
	private int rcr_type;//停车类型 0：普通车位停车 1：时间段包月停车
	private int rcr_state;// 是否有效  0：有效 1：无效
	private int rcr_discount;//是否可以使用优费券 : 0： 可以使用 1：无法使用
	private String car_displacement;//车辆排量
	private int car_type;//车牌类型:车牌类型 0：未知车牌:、1：蓝牌小汽车、2：: 黑牌小汽车、3：单排黄牌、4：双排黄牌、 5： 警车车牌、6：武警车牌、7：个性化车牌、8：单 排军车牌、9：双排军车牌、10：使馆车牌、11： 香港进出中国大陆车牌、12：农用车牌、13：教 练车牌、14：澳门进出中国大陆车牌、15：双层 武警车牌、16：武警总队车牌、17：双层武警总 队车牌
	private int car_code_color;//车牌颜色 0：未知、1：蓝色、2：黄色、3：白色、 4：黑色、5：绿色
	private int is_time_bucket;//是否按时间段收费 0:不按时间段收费 1：按时间段收费
	private String time_bucket;//时间段收费  例如：白天 9：00-12：00 每小时2元 
	
	//更新规则的时候使用
	 private long rcr_id;//规则表主键ID
	 private int is_default;//是否是默认规则 0:不是 1：是
	
	/************************get set 方法区****************************/

	public long getPi_id() {
		return pi_id;
	}


	public String getArea_code() {
		return area_code;
	}


	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}


	public int getIs_default() {
		return is_default;
	}


	public void setIs_default(int is_default) {
		this.is_default = is_default;
	}


	public long getRcr_id() {
		return rcr_id;
	}


	public void setRcr_id(long rcr_id) {
		this.rcr_id = rcr_id;
	}


	public void setPi_id(long pi_id) {
		this.pi_id = pi_id;
	}


	public int getStart_price() {
		return start_price;
	}


	public void setStart_price(int start_price) {
		this.start_price = start_price;
	}


	public int getStart_time() {
		return start_time;
	}


	public void setStart_time(int start_time) {
		this.start_time = start_time;
	}


	public int getCharging() {
		return charging;
	}


	public void setCharging(int charging) {
		this.charging = charging;
	}


	public int getCharging_time() {
		return charging_time;
	}


	public void setCharging_time(int charging_time) {
		this.charging_time = charging_time;
	}


	public int getMonth_price() {
		return month_price;
	}


	public void setMonth_price(int month_price) {
		this.month_price = month_price;
	}


	public int getMonth_time() {
		return month_time;
	}


	public void setMonth_time(int month_time) {
		this.month_time = month_time;
	}


	public String getPermit_time() {
		return permit_time;
	}


	public void setPermit_time(String permit_time) {
		this.permit_time = permit_time;
	}


	public String getTimeout_info() {
		return timeout_info;
	}


	public void setTimeout_info(String timeout_info) {
		this.timeout_info = timeout_info;
	}


	public int getRcr_type() {
		return rcr_type;
	}


	public void setRcr_type(int rcr_type) {
		this.rcr_type = rcr_type;
	}


	public int getRcr_state() {
		return rcr_state;
	}


	public void setRcr_state(int rcr_state) {
		this.rcr_state = rcr_state;
	}


	public int getRcr_discount() {
		return rcr_discount;
	}


	public void setRcr_discount(int rcr_discount) {
		this.rcr_discount = rcr_discount;
	}


	public String getCar_displacement() {
		return car_displacement;
	}


	public void setCar_displacement(String car_displacement) {
		this.car_displacement = car_displacement;
	}


	public int getCar_type() {
		return car_type;
	}


	public void setCar_type(int car_type) {
		this.car_type = car_type;
	}




	public int getCar_code_color() {
		return car_code_color;
	}


	public void setCar_code_color(int car_code_color) {
		this.car_code_color = car_code_color;
	}


	public int getIs_time_bucket() {
		return is_time_bucket;
	}


	public void setIs_time_bucket(int is_time_bucket) {
		this.is_time_bucket = is_time_bucket;
	}


	public String getTime_bucket() {
		return time_bucket;
	}


	public void setTime_bucket(String time_bucket) {
		this.time_bucket = time_bucket;
	}


	

	
	
	


	

	
	
	
}
