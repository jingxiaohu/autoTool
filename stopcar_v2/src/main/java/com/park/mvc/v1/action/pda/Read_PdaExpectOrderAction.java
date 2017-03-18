
package com.park.mvc.v1.action.pda;

import org.apache.struts2.convention.annotation.Action;

import com.park.bean.ReturnDataNew;
import com.park.core.Constants;
import com.park.mvc.v1.action.BaseV1Action;

/**
 * 获取PDA的预约且未付款的订单
 * @author jingxiaohu
 *
 */
public class Read_PdaExpectOrderAction extends BaseV1Action {


	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/ 
	private static final long serialVersionUID = -3599663972160625262L;
	
	/**
	 * 获取PDA的预约且未付款的订单
	 * @return
	 */
	@Action(value = "pda_expect_order")
	public String pda_expect_order(){
		ReturnDataNew returnData = new ReturnDataNew();
		 try{
			//检查是否是合法请求
			if(!checkRequest()){
				returnData.setReturnData(errorcode_param, " illegal request code", null);
				sendResp(returnData);
				return null;
			}
			String sign_str = getSignature(Constants.SYSTEM_KEY, dtype,pi_id,area_code);
			if(!sign.equalsIgnoreCase(sign_str)){
				log.error("sign="+sign+"  sign_str="+sign_str);
				returnData.setReturnData(errorcode_param, " sign is not right", null);
				sendResp(returnData);
				return null;
			}
			orderBiz.pda_expect_order(returnData,dtype,area_code,pi_id,page,size);
			
			sendResp(returnData);
			return null;
			
			} catch (Exception e) {
				log.error("Read_PdaExpectOrderAction pda_expect_order  is error(DEVICE-JAVA)- P",e);
				returnData.setReturnData(errorcode_systerm, "system is error", ""); 
			}
			sendResp(returnData);
			return null; 
	}
	/********************接收参数区*************************/
	private int type;// 获取订单类型  0:普通停车订单  1：租赁停车订单
	private String area_code;//省市区区域代码  四川省 成都市 龙泉驿区  510112
	private long pi_id;//停车场ID
	public int page  = 1;
	public int size = 20;
	/************************get set 方法区****************************/



	public int getPage() {
		return page;
	}


	public long getPi_id() {
		return pi_id;
	}


	public void setPi_id(long pi_id) {
		this.pi_id = pi_id;
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




	public String getArea_code() {
		return area_code;
	}


	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}
	


    
	
	
}
