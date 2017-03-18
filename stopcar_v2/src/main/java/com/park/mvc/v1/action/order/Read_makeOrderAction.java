
package com.park.mvc.v1.action.order;

import java.net.URLDecoder;

import org.apache.struts2.convention.annotation.Action;

import com.park.bean.ReturnDataNew;
import com.park.core.Constants;
import com.park.mvc.v1.action.BaseV1Action;
import com.park.util.RequestUtil;

/**
 * 准备用户手动下单的数据 （1：读取预约下单普通车位  2：读取下单租赁包月车位  3:  读取用户停车缴费读取订单）
 * @author jingxiaohu
 *
 */
public class Read_makeOrderAction extends BaseV1Action {


	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/ 
	private static final long serialVersionUID = -3599663972160625262L;


	/**
	 * 读取用户预约下单普通车位 需要的订单准备数据
	 * @return
	 */
	@Action(value = "read_expect_order")
	public String read_expect_order(){
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
			if(pi_id < 1){
				//用户ID
				returnData.setReturnData(errorcode_param, " pi_id="+pi_id+"  pi_id is smaller than zero", null);
				sendResp(returnData);
				return null;
			}
			if(RequestUtil.checkObjectBlank(area_code)){
				//area_code 省市区区域代码  四川省 成都市 龙泉驿区  510112
				returnData.setReturnData(errorcode_param, " area_code="+area_code+"  area_code is null", null);
				sendResp(returnData);
				return null;
			}
			String sign_str = getSignature(Constants.SYSTEM_KEY, dtype,ui_id,pi_id);
			if(!sign.equalsIgnoreCase(sign_str)){
				log.error("sign="+sign+"  sign_str="+sign_str);
				returnData.setReturnData(errorcode_param, " sign is not right", null);
				sendResp(returnData);
				return null;
			}
			parkinfoBiz.read_expect_order(returnData,dtype,ui_id,pi_id,area_code);
			
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
	 * 读取用户停车场租赁规则信息(车位租赁)详情页
	 * @return
	 */
	@Action(value = "read_rent_order")
	public String read_rent_order(){
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
			if(pi_id < 1){
				//用户ID
				returnData.setReturnData(errorcode_param, " pi_id="+pi_id+"  pi_id is smaller than zero", null);
				sendResp(returnData);
				return null;
			}
			if(RequestUtil.checkObjectBlank(area_code)){
				//area_code 省市区区域代码  四川省 成都市 龙泉驿区  510112
				returnData.setReturnData(errorcode_param, " area_code="+area_code+"  area_code is null", null);
				sendResp(returnData);
				return null;
			}
			String sign_str = getSignature(Constants.SYSTEM_KEY, dtype,ui_id,pi_id);
			if(!sign.equalsIgnoreCase(sign_str)){
				log.error("sign="+sign+"  sign_str="+sign_str);
				returnData.setReturnData(errorcode_param, " sign is not right", null);
				sendResp(returnData);
				return null;
			}
			parkinfoBiz.read_rent_order(returnData,dtype,ui_id,pi_id,area_code);
			
			sendResp(returnData);
			return null;
			
			} catch (Exception e) {
				log.error("Write_makeOrderAction read_rent_order  is error(DEVICE-JAVA)- P",e);
				returnData.setReturnData(errorcode_systerm, "system is error", ""); 
			}
			sendResp(returnData);
			return null; 
	}
	
	
	/**
	 * 用户停车缴费读取订单
	 */
	@Action(value = "read_pay_order")
	public String read_pay_order(){
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
				if(pi_id < 1){
					//停车场主键ID
					returnData.setReturnData(errorcode_param, " pi_id="+pi_id+"  pi_id is smaller than zero", null);
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
				if(RequestUtil.checkObjectBlank(area_code)){
					//area_code 省市区区域代码  四川省 成都市 龙泉驿区  510112
					returnData.setReturnData(errorcode_param, " area_code="+area_code+"  area_code is null", null);
					sendResp(returnData);
					return null;
				}
				
				
				String sign_str = getSignature(Constants.SYSTEM_KEY, dtype,ui_id,pi_id);
				if(!sign.equalsIgnoreCase(sign_str)){
					log.error("sign="+sign+"  sign_str="+sign_str);
					returnData.setReturnData(errorcode_param, " sign is not right", null);
					sendResp(returnData);
					return null;
				}
				orderBiz.read_pay_order(returnData,dtype,ui_id,pi_id,car_code,area_code);
				
				sendResp(returnData);
				return null;
				
				} catch (Exception e) {
					log.error("Read_makeOrderAction read_pay_order  is error(DEVICE-JAVA)- P",e);
					returnData.setReturnData(errorcode_systerm, "system is error", ""); 
				}
				sendResp(returnData);
				return null; 
		
	}
	
	
	/********************接收参数区*************************/
	private long pi_id;//预约停车场主键ID
	private String car_code;//车牌号
	private String area_code;//省市区区域代码  四川省 成都市 龙泉驿区  510112
	/************************get set 方法区****************************/
	public long getPi_id() {
		return pi_id;
	}
	public void setPi_id(long pi_id) {
		this.pi_id = pi_id;
	}


	public String getCar_code() {
		return car_code;
	}


	public void setCar_code(String car_code) {
		this.car_code = car_code;
	}


	public String getArea_code() {
		return area_code;
	}


	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}
	


    
	
	
}
