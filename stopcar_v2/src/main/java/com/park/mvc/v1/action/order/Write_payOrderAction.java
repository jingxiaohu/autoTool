
package com.park.mvc.v1.action.order;

import org.apache.struts2.convention.annotation.Action;

import com.park.bean.ReturnDataNew;
import com.park.core.Constants;
import com.park.mvc.v1.action.BaseV1Action;

/**
 * 扫描到车辆出库扣费
 * @author jingxiaohu
 *
 */
public class Write_payOrderAction extends BaseV1Action {


	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/ 
	private static final long serialVersionUID = -3599663972160625262L;

	/**
	 *  扫描到车辆出库扣费
	 */
	@Action(value = "payment")
	public String payment(){
		ReturnDataNew returnData = new ReturnDataNew();
		 try{
			//检查是否是合法请求
			if(!checkRequest()){
				returnData.setReturnData(errorcode_param, " illegal request code", null);
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
			orderBiz.payment(returnData,dtype,orderid,is_rent,pi_id,car_code,area_code);
			
			sendResp(returnData);
			return null;
			
			} catch (Exception e) {
				log.error("Write_makeOrderAction pay_order  is error(DEVICE-JAVA)- P",e);
				returnData.setReturnData(errorcode_systerm, "system is error", ""); 
			}
			sendResp(returnData);
			return null; 
	}
	
	
	
	
	/********************接收参数区*************************/
	private long pi_id;//预约停车场主键ID
	private String car_code;//车牌号
	private String area_code;//省市区区域代码
	private String orderid;//我们的订单号  字符串
	private int is_rent;//是否是租赁车辆 0:不是 1：是
	/************************get set 方法区****************************/

    
	public String getCar_code() {
		return car_code;
	}
	public int getIs_rent() {
		return is_rent;
	}
	public void setIs_rent(int is_rent) {
		this.is_rent = is_rent;
	}
	public long getPi_id() {
		return pi_id;
	}
	public void setPi_id(long pi_id) {
		this.pi_id = pi_id;
	}
	public String getArea_code() {
		return area_code;
	}
	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public void setCar_code(String car_code) {
		this.car_code = car_code;
	}

	
	
	
}
