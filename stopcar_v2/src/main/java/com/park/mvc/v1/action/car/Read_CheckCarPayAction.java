
package com.park.mvc.v1.action.car;

import java.net.URLDecoder;

import org.apache.struts2.convention.annotation.Action;

import com.park.bean.ReturnDataNew;
import com.park.core.Constants;
import com.park.mvc.v1.action.BaseV1Action;
import com.park.util.RequestUtil;

/**
 * 检查某停车场某车牌号是否已经付款
 * @author jingxiaohu
 *
 */
public class Read_CheckCarPayAction extends BaseV1Action {


	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/ 
	private static final long serialVersionUID = -3599663972160625262L;


	/**
	 * 检查某停车场某车牌号是否已经付款
	 * @return
	 */
	@Action(value = "read_checkpay")
	public String read_checkpay(){
		ReturnDataNew returnData = new ReturnDataNew();
		 try{
			//检查是否是合法请求
			if(!checkRequest()){
				returnData.setReturnData(errorcode_param, " illegal request code", null);
				sendResp(returnData);
				return null;
			}
			if(pi_id < 1){
				//停车场主键ID
				returnData.setReturnData(errorcode_param, " pi_id="+pi_id+"  pi_id is smaller than zero", null);
				sendResp(returnData);
				return null;
			}
			if(RequestUtil.checkObjectBlank(orderid)){
				//orderid;//我们的订单号  字符串
				returnData.setReturnData(errorcode_param, " orderid="+orderid+"  orderid is null", null);
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
			
			String sign_str = getSignature(Constants.SYSTEM_KEY, dtype,pi_id);
			if(!sign.equalsIgnoreCase(sign_str)){
				log.error("sign="+sign+"  sign_str="+sign_str);
				returnData.setReturnData(errorcode_param, " sign is not right", null);
				sendResp(returnData);
				return null;
			}
			carBiz.read_checkpay(returnData,pi_id,car_code,area_code,orderid);
			
			sendResp(returnData);
			return null;
			
			} catch (Exception e) {
				log.error("Read_CheckCarPayAction read_checkpay  is error(DEVICE-JAVA)- P",e);
				returnData.setReturnData(errorcode_systerm, "system is error", ""); 
			}
			sendResp(returnData);
			return null; 
	}
	
	/********************接收参数区*************************/
	private long pi_id;//预约停车场主键ID;
	private String car_code;//车牌号
	private String area_code;//省市区区域代码  四川省 成都市 龙泉驿区  510112
	private String orderid;//我们的订单号  字符串 
	/************************get set 方法区****************************/


	public long getPi_id() {
		return pi_id;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
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
