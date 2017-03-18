
package com.park.mvc.v1.action.car;

import org.apache.struts2.convention.annotation.Action;

import com.park.bean.ReturnDataNew;
import com.park.core.Constants;
import com.park.mvc.v1.action.BaseV1Action;
import com.park.util.RequestUtil;

/**
 * 道闸停车场的更新用户现金支付状态和金额
 * @author jingxiaohu
 *
 */
public class Write_CarSureOutParkAction extends BaseV1Action {


	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/ 
	private static final long serialVersionUID = -3599663972160625262L;
	
	
	/**
	 * 道闸停车场的更新用户现金支付状态和金额
	 * @return
	 */
	@Action(value = "pay_sure")
	public String pay_sure(){
		ReturnDataNew returnData = new ReturnDataNew();
		 try{
			//检查是否是合法请求
			if(!checkRequest()){
				returnData.setReturnData(errorcode_param, " illegal request code", null);
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
			
			String sign_str = getSignature(Constants.SYSTEM_KEY, dtype,orderid,pi_id);
			if(!sign.equalsIgnoreCase(sign_str)){
				log.error("sign="+sign+"  sign_str="+sign_str);
				returnData.setReturnData(errorcode_param, " sign is not right", null);
				sendResp(returnData);
				return null;
			}
			orderBiz.pay_sure(returnData,dtype,orderid,pi_id,area_code,money);
			
			sendResp(returnData);
			return null;
			
			} catch (Exception e) {
				log.error("Write_CarSureOutParkAction pay_sure  is error(DEVICE-JAVA)- P",e);
				returnData.setReturnData(errorcode_systerm, "system is error", ""); 
			}
			sendResp(returnData);
			return null; 
	}
	
	
	
	
	/********************接收参数区*************************/
	private long pi_id;//预约停车场主键ID
	private String car_code;//车牌号
	private int money;//金额（ 单位分）
	private String area_code;//省市区区域代码
	private String orderid;//我们的订单号  字符串
	/************************get set 方法区****************************/

    
	public String getCar_code() {
		return car_code;
	}


	public String getOrderid() {
		return orderid;
	}


	public void setOrderid(String orderid) {
		this.orderid = orderid;
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

	public void setCar_code(String car_code) {
		this.car_code = car_code;
	}


	public int getMoney() {
		return money;
	}


	public void setMoney(int money) {
		this.money = money;
	}



	



	
	
	
}
