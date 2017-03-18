
package com.park.mvc.v1.action.order;

import java.net.URLDecoder;

import org.apache.struts2.convention.annotation.Action;

import com.park.bean.ReturnDataNew;
import com.park.core.Constants;
import com.park.mvc.v1.action.BaseV1Action;
import com.park.util.RequestUtil;

/**
 * 用户手动下单 （1：预约下单普通车位  2：下单租赁包月车位 3:用户取消预约订单）
 * @author jingxiaohu
 *
 */
public class Write_makeOrderAction extends BaseV1Action {


	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/ 
	private static final long serialVersionUID = -3599663972160625262L;


	/**
	 * 用户预约下单普通车位（普通车位-用户预约下单）
	 * @return
	 */
	@Action(value = "expect_order")
	public String expect_order(){
		ReturnDataNew returnData = new ReturnDataNew();
		 try{
			//检查是否是合法请求
			if(!checkRequest()){
				returnData.setReturnData(errorcode_param, " illegal request code", null);
				sendResp(returnData);
				return null;
			}
			if(ui_id < 1){
				//用户ID
				returnData.setReturnData(errorcode_param, " ui_id="+ui_id+"  ui_id is smaller than zero", null);
				sendResp(returnData);
				return null;
			}
			
			if(RequestUtil.checkObjectBlank(car_code)){
				//car_code 车牌号
				returnData.setReturnData(errorcode_param, " car_code="+car_code+"  car_code is null", null);
				sendResp(returnData);
				return null;
			}else{
				car_code  = URLDecoder.decode(car_code, Constants.SYSTEM_CHARACTER);
			}
			/*if(RequestUtil.checkObjectBlank(expect_info)){
				//expect_info 预约信息
				returnData.setReturnData(errorcode_param, " expect_info="+expect_info+"  expect_info is null", null);
				sendResp(returnData);
				return null;
			}else{
				expect_info  = URLDecoder.decode(expect_info, Constants.SYSTEM_CHARACTER);
			}*/
			if(expect_money < 1 ){
				//expect_money 预约价格
				returnData.setReturnData(errorcode_param, " expect_money="+expect_money+"  expect_money is smaller than zero", null);
				sendResp(returnData);
				return null;
			}
			if(expect_time < 1){
				//expect_time 预约时间
				returnData.setReturnData(errorcode_param, " expect_time="+expect_time+"  expect_time is smaller than zero", null);
				sendResp(returnData);
				return null;
			}
			
			
			if(RequestUtil.checkObjectBlank(pp_versioncode)){
				//当前APPSDK版本号 （内部升级版本代号）
				returnData.setReturnData(errorcode_param, " pp_versioncode="+pp_versioncode+"  pp_versioncode is null", null);
				sendResp(returnData);
				return null;
			}
			
			if(pi_id < 1){
				//停车场主键ID
				returnData.setReturnData(errorcode_param, " pi_id="+pi_id+"  pi_id is smaller than 1", null);
				sendResp(returnData);
				return null;
			}
			
			if(RequestUtil.checkObjectBlank(area_code)){
				//area_code;省市区区域代码
				returnData.setReturnData(errorcode_param, " area_code="+area_code+"  area_code is null", null);
				sendResp(returnData);
				return null;
			}
			
			String sign_str = getSignature(Constants.SYSTEM_KEY, dtype,ui_id,car_code);
			if(!sign.equalsIgnoreCase(sign_str)){
				log.error("sign="+sign+"  sign_str="+sign_str);
				returnData.setReturnData(errorcode_param, " sign is not right", null);
				sendResp(returnData);
				return null;
			}
			orderBiz.expect_order(returnData,dtype,ui_id,pi_id,pay_type,expect_info,expect_money,expect_time,car_code,pp_versioncode,area_code);
			
			sendResp(returnData);
			return null;
			
			} catch (Exception e) {
				log.error("Write_makeOrderAction expect_order  is error(DEVICE-JAVA)- P",e);
				returnData.setReturnData(errorcode_systerm, "system is error", ""); 
			}
			sendResp(returnData);
			return null; 
	}
	
	
	/**
	 * 下单租赁包月车位(车位租赁)
	 * @return
	 */
	@Action(value = "pay_rent_order")
	public String pay_rent_order(){
		ReturnDataNew returnData = new ReturnDataNew();
		 try{
			//检查是否是合法请求
			if(!checkRequest()){
				returnData.setReturnData(errorcode_param, " illegal request code", null);
				sendResp(returnData);
				return null;
			}
			if(is_24hours==0 && ui_id < 1){
				//用户ID
				returnData.setReturnData(errorcode_param, " ui_id="+ui_id+"  ui_id is smaller than zero", null);
				sendResp(returnData);
				return null;
			}
			
			if(RequestUtil.checkObjectBlank(car_code)){
				//car_code 车牌号
				returnData.setReturnData(errorcode_param, " car_code="+car_code+"  car_code is null", null);
				sendResp(returnData);
				return null;
			}else{
				car_code  = URLDecoder.decode(car_code, Constants.SYSTEM_CHARACTER);
			}
			if(RequestUtil.checkObjectBlank(month_info)){
				//month_info;//包月提示信息
				returnData.setReturnData(errorcode_param, " month_info="+month_info+"  month_info is null", null);
				sendResp(returnData);
				return null;
			}else{
				month_info  = URLDecoder.decode(month_info, Constants.SYSTEM_CHARACTER);
			}
			if(month_num < 1){
				//month_num 预约月数
				returnData.setReturnData(errorcode_param, " month_num="+month_num+"  month_num is smaller than zero", null);
				sendResp(returnData);
				return null;
			}
			
			if(RequestUtil.checkObjectBlank(permit_time)){
				//permit_time;//准入时间段（17:00-08:30）
				returnData.setReturnData(errorcode_param, " permit_time="+permit_time+"  permit_time is null", null);
				sendResp(returnData);
				return null;
			}
			
			
			if(RequestUtil.checkObjectBlank(pp_versioncode)){
				//当前APPSDK版本号 （内部升级版本代号）
				returnData.setReturnData(errorcode_param, " pp_versioncode="+pp_versioncode+"  pp_versioncode is null", null);
				sendResp(returnData);
				return null;
			}
			
			if(pi_id < 1){
				//停车场主键ID
				returnData.setReturnData(errorcode_param, " pi_id="+pi_id+"  pi_id is smaller than 1", null);
				sendResp(returnData);
				return null;
			}
			
			if(RequestUtil.checkObjectBlank(area_code)){
				//area_code;省市区区域代码
				returnData.setReturnData(errorcode_param, " area_code="+area_code+"  area_code is null", null);
				sendResp(returnData);
				return null;
			}
			
			String sign_str = getSignature(Constants.SYSTEM_KEY, dtype,ui_id,car_code);
			if(!sign.equalsIgnoreCase(sign_str)){
				log.error("sign="+sign+"  sign_str="+sign_str);
				returnData.setReturnData(errorcode_param, " sign is not right", null);
				sendResp(returnData);
				return null;
			}
			orderBiz.pay_rent_order(returnData,dtype,ui_id,pi_id,pay_type,month_num,month_info,car_code,pp_versioncode,area_code,upc_id,pay_source,permit_time,is_24hours,orderid);
			
			sendResp(returnData);
			return null;
			
			} catch (Exception e) {
				log.error("Write_makeOrderAction expect_rent_order  is error(DEVICE-JAVA)- P",e);
				returnData.setReturnData(errorcode_systerm, "system is error", ""); 
			}
			sendResp(returnData);
			return null; 
	}
	
	

	
	
	
	/**
	 * 用户普通停车位直接正式付款下单
	 */
	@Action(value = "pay_order")
	public String pay_order(){
		ReturnDataNew returnData = new ReturnDataNew();
		 try{
			//检查是否是合法请求
			if(!checkRequest()){
				returnData.setReturnData(errorcode_param, " illegal request code", null);
				sendResp(returnData);
				return null;
			}
			if(ui_id < 1){
				//用户ID
				returnData.setReturnData(errorcode_param, " ui_id="+ui_id+"  ui_id is smaller than zero", null);
				sendResp(returnData);
				return null;
			}
			
			String sign_str = getSignature(Constants.SYSTEM_KEY, dtype,ui_id,orderid);
			if(!sign.equalsIgnoreCase(sign_str)){
				log.error("sign="+sign+"  sign_str="+sign_str);
				returnData.setReturnData(errorcode_param, " sign is not right", null);
				sendResp(returnData);
				return null;
			}
			orderBiz.pay_order(returnData,dtype,ui_id,orderid,upc_id,pay_source);
			
			sendResp(returnData);
			return null;
			
			} catch (Exception e) {
				log.error("Write_makeOrderAction pay_order  is error(DEVICE-JAVA)- P",e);
				returnData.setReturnData(errorcode_systerm, "system is error", ""); 
			}
			sendResp(returnData);
			return null; 
	}
	
	
	/**
	 * 用户取消下单
	 * @return
	 */
	@Action(value = "cancel_order")
	public String cancel_order(){
		ReturnDataNew returnData = new ReturnDataNew();
		 try{
			//检查是否是合法请求
			if(!checkRequest()){
				returnData.setReturnData(errorcode_param, " illegal request code", null);
				sendResp(returnData);
				return null;
			}
			if(ui_id < 1){
				//用户ID
				returnData.setReturnData(errorcode_param, " ui_id="+ui_id+"  ui_id is smaller than zero", null);
				sendResp(returnData);
				return null;
			}
			
			if(RequestUtil.checkObjectBlank(orderid)){
				//orderid;//我们的订单号  字符串
				returnData.setReturnData(errorcode_param, " orderid="+orderid+"  orderid is null", null);
				sendResp(returnData);
				return null;
			}
			
			if(pi_id < 1){
				//停车场主键ID
				returnData.setReturnData(errorcode_param, " pi_id="+pi_id+"  pi_id is smaller than 1", null);
				sendResp(returnData);
				return null;
			}
			
			if(RequestUtil.checkObjectBlank(area_code)){
				//area_code;省市区区域代码
				returnData.setReturnData(errorcode_param, " area_code="+area_code+"  area_code is null", null);
				sendResp(returnData);
				return null;
			}
			
			String sign_str = getSignature(Constants.SYSTEM_KEY, dtype,ui_id,orderid,type);
			if(!sign.equalsIgnoreCase(sign_str)){
				log.error("sign="+sign+"  sign_str="+sign_str);
				returnData.setReturnData(errorcode_param, " sign is not right", null);
				sendResp(returnData);
				return null;
			}
			orderBiz.cancel_order(returnData,dtype,ui_id,orderid,type,pi_id,area_code);
			
			sendResp(returnData);
			return null;
			
			} catch (Exception e) {
				log.error("Write_makeOrderAction expect_order  is error(DEVICE-JAVA)- P",e);
				returnData.setReturnData(errorcode_systerm, "system is error", ""); 
			}
			sendResp(returnData);
			return null; 
	}
	
	
	
	/**
	 * 用户删除订单
	 * @return
	 */
	@Action(value = "delete_order")
	public String delete_order(){
		ReturnDataNew returnData = new ReturnDataNew();
		 try{
			//检查是否是合法请求
			if(!checkRequest()){
				returnData.setReturnData(errorcode_param, " illegal request code", null);
				sendResp(returnData);
				return null;
			}
			if(ui_id < 1){
				//用户ID
				returnData.setReturnData(errorcode_param, " ui_id="+ui_id+"  ui_id is smaller than zero", null);
				sendResp(returnData);
				return null;
			}
			
			if(RequestUtil.checkObjectBlank(orderid)){
				//orderid;//我们的订单号  字符串
				returnData.setReturnData(errorcode_param, " orderid="+orderid+"  orderid is null", null);
				sendResp(returnData);
				return null;
			}
			
			if(pi_id < 1){
				//停车场主键ID
				returnData.setReturnData(errorcode_param, " pi_id="+pi_id+"  pi_id is smaller than 1", null);
				sendResp(returnData);
				return null;
			}
			
			if(RequestUtil.checkObjectBlank(area_code)){
				//area_code;省市区区域代码
				returnData.setReturnData(errorcode_param, " area_code="+area_code+"  area_code is null", null);
				sendResp(returnData);
				return null;
			}
			
			String sign_str = getSignature(Constants.SYSTEM_KEY, dtype,ui_id,orderid,type);
			if(!sign.equalsIgnoreCase(sign_str)){
				log.error("sign="+sign+"  sign_str="+sign_str);
				returnData.setReturnData(errorcode_param, " sign is not right", null);
				sendResp(returnData);
				return null;
			}
			orderBiz.delete_order(returnData,dtype,ui_id,orderid,type,pi_id,area_code);
			
			sendResp(returnData);
			return null;
			
			} catch (Exception e) {
				log.error("Write_makeOrderAction delete_order  is error(DEVICE-JAVA)- P",e);
				returnData.setReturnData(errorcode_systerm, "system is error", ""); 
			}
			sendResp(returnData);
			return null; 
	}
	
	/********************接收参数区*************************/
	private long pi_id;//预约停车场主键ID
	private int pay_type;//支付类型 0:手动输入密码支付 1：快捷支付（服务器可以请求第三方直接扣款）
	private String car_code;//车牌号
	private String pp_versioncode;//当前APPSDK版本号 （内部升级版本代号）
	private String expect_info;//预约提示信息
	private int expect_money;//预约价格（ 单位分）
	private int expect_time;//预约时间 单位分钟
	private String area_code;//省市区区域代码
	
	//下单租赁包月车位
	private int month_num;//包月租凭月数
	private String month_info;//包月提示信息
	private String permit_time;//准入时间段（17:00-08:30）
	private int is_24hours;//是否是24小时包月  0:不是24小时包月  1：是24小时包月
	
	
	//用户取消预约下单  用户删除订单  或者 用户进行普通停车位下单更新 用户使用的优惠券 应缴金额  计费时间
	private String orderid;//我们的订单号  字符串
	private int type;//0：普通车位  1：租赁车位
	private long upc_id;//用户优惠券表主键ID
	
	//用户进行普通停车位下单更新 用户使用的优惠券 应缴金额  计费时间
	private int pay_source;//支付类型 1:支付宝  2：微信  3：银联  4：钱包 5:龙支付
//	private int discount_money;//抵扣金额
//	private int discount_type;//优惠券类型 0:金额券 1：折扣券
//	private String discount_name;//抵扣优惠券名称
	/************************get set 方法区****************************/

    
	public String getCar_code() {
		return car_code;
	}
	public int getIs_24hours() {
		return is_24hours;
	}


	public void setIs_24hours(int is_24hours) {
		this.is_24hours = is_24hours;
	}




	public String getPermit_time() {
		return permit_time;
	}


	public void setPermit_time(String permit_time) {
		this.permit_time = permit_time;
	}







	public long getUpc_id() {
		return upc_id;
	}


	public void setUpc_id(long upc_id) {
		this.upc_id = upc_id;
	}


	public int getPay_source() {
		return pay_source;
	}


	public void setPay_source(int pay_source) {
		this.pay_source = pay_source;
	}


	public String getOrderid() {
		return orderid;
	}


	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}


	public String getArea_code() {
		return area_code;
	}


	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}


	public long getPi_id() {
		return pi_id;
	}


	public void setPi_id(long pi_id) {
		this.pi_id = pi_id;
	}


	public int getPay_type() {
		return pay_type;
	}


	public void setPay_type(int pay_type) {
		this.pay_type = pay_type;
	}


	public String getExpect_info() {
		return expect_info;
	}


	public void setExpect_info(String expect_info) {
		this.expect_info = expect_info;
	}


	public int getExpect_money() {
		return expect_money;
	}


	public void setExpect_money(int expect_money) {
		this.expect_money = expect_money;
	}


	public int getExpect_time() {
		return expect_time;
	}


	public void setExpect_time(int expect_time) {
		this.expect_time = expect_time;
	}




	public int getMonth_num() {
		return month_num;
	}


	public void setMonth_num(int month_num) {
		this.month_num = month_num;
	}


	public String getMonth_info() {
		return month_info;
	}


	public void setMonth_info(String month_info) {
		this.month_info = month_info;
	}


	public String getPp_versioncode() {
		return pp_versioncode;
	}
	public void setPp_versioncode(String pp_versioncode) {
		this.pp_versioncode = pp_versioncode;
	}
	public void setCar_code(String car_code) {
		this.car_code = car_code;
	}



	



	
	
	
}
