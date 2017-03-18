
package com.park.mvc.v1.action.order;

import org.apache.struts2.convention.annotation.Action;

import com.park.bean.ReturnDataNew;
import com.park.core.Constants;
import com.park.mvc.v1.action.BaseV1Action;

/**
 * 获取我的订单 （1：临时停车订单  2： 租赁订单）
 * @author jingxiaohu
 *
 */
public class Read_MyOrderAction extends BaseV1Action {


	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/ 
	private static final long serialVersionUID = -3599663972160625262L;


	/**
	 * 获取我的停车订单
	 * @return
	 */
	@Action(value = "my_order")
	public String my_order(){
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
			String sign_str = getSignature(Constants.SYSTEM_KEY, dtype,ui_id);
			if(!sign.equalsIgnoreCase(sign_str)){
				log.error("sign="+sign+"  sign_str="+sign_str);
				returnData.setReturnData(errorcode_param, " sign is not right", null);
				sendResp(returnData);
				return null;
			}
			orderBiz.my_order(returnData,dtype,ui_id,type,car_code,area_code,page,size);
			
			sendResp(returnData);
			return null;
			
			} catch (Exception e) {
				log.error("Read_MyOrderAction my_order  is error(DEVICE-JAVA)- P",e);
				returnData.setReturnData(errorcode_systerm, "system is error", ""); 
			}
			sendResp(returnData);
			return null; 
	}
	
	
	/********************接收参数区*************************/
	private int type;// 获取订单类型  0:普通停车订单  1：租赁停车订单
	private String car_code;//车牌号
	private String area_code;//省市区区域代码  四川省 成都市 龙泉驿区  510112
	public int page  = 1;
	public int size = 20;
	/************************get set 方法区****************************/

	public String getCar_code() {
		return car_code;
	}


	public int getPage() {
		return page;
	}


	public void setPage(int page) {
		this.page = page;
	}


	public int getSize() {
		return size;
	}


	public void setSize(int size) {
		this.size = size;
	}


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
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
