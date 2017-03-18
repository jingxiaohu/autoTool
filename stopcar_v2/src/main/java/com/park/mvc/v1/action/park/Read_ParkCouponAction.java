
package com.park.mvc.v1.action.park;

import org.apache.struts2.convention.annotation.Action;

import com.park.bean.ReturnDataNew;
import com.park.core.Constants;
import com.park.mvc.v1.action.BaseV1Action;
import com.park.util.RequestUtil;

/**
 * 读取我的优惠券列表
 * @author jingxiaohu
 *
 */
public class Read_ParkCouponAction extends BaseV1Action {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6891425545908564737L;

	/**
	 * 读取我的优惠券列表
	 * @return
	 */
	@Action(value = "read_mycoupon")
	public String 	read_mycoupon(){
	
		ReturnDataNew returnData = new ReturnDataNew();
		 try{
			//检查是否是合法请求
			String ip = getIpAddr(getRequest());
			if(ip.startsWith("192.168") || ip.startsWith("127.0")){  
				ip  =  null;
			}
			if(RequestUtil.checkObjectBlank(sign)){
				returnData.setReturnData(errorcode_param, " sign is null", "");
				sendResp(returnData);
				return null;
			}
			if(ui_id < 1){
				returnData.setReturnData(errorcode_param, " ui_id is smaller than zero", "");
				sendResp(returnData);
				return null;
			}
			String sign_str = getSignature(Constants.SYSTEM_KEY, dtype,ui_id);
			if(!sign.equalsIgnoreCase(sign_str)){
				log.error("sign="+sign+"  sign_str="+sign_str);
				returnData.setReturnData(errorcode_param, " sign is not right", "");
				sendResp(returnData);
				return null;
			}
			
			couponBiz.ReturnreadMycoupon(returnData, dtype,ui_id,page,size);
			sendResp(returnData);
			return null;
			
			} catch (Exception e) {
				log.error("Read_ParkCouponAction.read_mycoupon is error  2.21	Read-读取我的优惠券列表 (APPSDK-JAVA)- P",e);
				returnData.setReturnData(errorcode_systerm, "system is error", ""); 
			}
			sendResp(returnData);
			return null; 
	}
	
	/********************接收参数区*************************/
	public int page  = 1;
	public int size = 20;
	/************************get set 方法区****************************/

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



	
	

	
	
	


	
	
	
}
