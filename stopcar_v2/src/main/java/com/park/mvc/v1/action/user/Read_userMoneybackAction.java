
package com.park.mvc.v1.action.user;

import java.net.URLDecoder;

import org.apache.struts2.convention.annotation.Action;

import com.park.bean.ReturnDataNew;
import com.park.core.Constants;
import com.park.mvc.v1.action.BaseV1Action;
import com.park.util.RequestUtil;

/**
 * 获取用户申诉退费审核结果
 * @author jingxiaohu
 *
 */
public class Read_userMoneybackAction extends BaseV1Action {


	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/ 
	private static final long serialVersionUID = -3599663972160625262L;


	/**
	 * 获取用户申诉退费审核结果
	 * @return
	 */
	@Action(value = "read_usermoneyback")
	public String read_usermoneyback(){
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
			if(RequestUtil.checkObjectBlank(order_id)){
				// pp_id;//停车下订单表主键ID
				returnData.setReturnData(errorcode_param, " order_id="+order_id+"  order_id is null", null);
				sendResp(returnData);
				return null;
			}
			
			String sign_str = getSignature(Constants.SYSTEM_KEY, dtype,ui_id,order_id);
			if(!sign.equalsIgnoreCase(sign_str)){
				log.error("sign="+sign+"  sign_str="+sign_str);
				returnData.setReturnData(errorcode_param, " sign is not right", null);
				sendResp(returnData);
				return null;
			}
			
			carBiz.read_usermoneyback(returnData,ui_id,order_id,is_rent);
			
			sendResp(returnData);
			return null;
			
			} catch (Exception e) {
				log.error("Read_userMoneybackAction read_usermoneyback  is error(DEVICE-JAVA)- P",e);
				returnData.setReturnData(errorcode_systerm, "system is error", ""); 
			}
			sendResp(returnData);
			return null; 
	}
	
	/********************接收参数区*************************/
	private String order_id;//停车下订单表主键ID
	private int is_rent;//是否是租赁订单 0：临停订单  1：预约临停订单  2：租赁包月订单
	/************************get set 方法区****************************/


	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public int getIs_rent() {
		return is_rent;
	}
	public void setIs_rent(int is_rent) {
		this.is_rent = is_rent;
	}


	
}
