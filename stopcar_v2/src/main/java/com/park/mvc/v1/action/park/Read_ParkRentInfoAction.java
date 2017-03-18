
package com.park.mvc.v1.action.park;

import org.apache.struts2.convention.annotation.Action;

import com.park.bean.ReturnDataNew;
import com.park.core.Constants;
import com.park.mvc.v1.action.BaseV1Action;
import com.park.util.RequestUtil;

/**
 * 读取停车场租赁详情
 * @author jingxiaohu
 *
 */
public class Read_ParkRentInfoAction extends BaseV1Action {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6891425545908564737L;

	/**
	 * 读取停车场租赁详情
	 * @return
	 */
	@Action(value = "read_parkrent_info")
	public String 	read_parkrent_info(){
	
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
				returnData.setReturnData(errorcode_param, " ui_id is smaller than one", "");
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
				//area_code 省市区区域代码  四川省 成都市 龙泉驿区  510112
				returnData.setReturnData(errorcode_param, " area_code="+area_code+"  area_code is null", null);
				sendResp(returnData);
				return null;
			}
			String sign_str = getSignature(Constants.SYSTEM_KEY, dtype,ui_id,pi_id);
			if(!sign.equalsIgnoreCase(sign_str)){
				log.error("sign="+sign+"  sign_str="+sign_str);
				returnData.setReturnData(errorcode_param, " sign is not right", "");
				sendResp(returnData);
				return null;
			}
			
			orderBiz.read_parkrent_info(returnData, dtype,ui_id,pi_id,area_code);
			sendResp(returnData);
			return null;
			
			} catch (Exception e) {
				log.error("Read_ParkRentInfoAction.read_parkrent_info is error  2.21	Read-读取停车场租赁详情 (APPSDK-JAVA)- P",e);
				returnData.setReturnData(errorcode_systerm, "system is error", ""); 
			}
			sendResp(returnData);
			return null; 
	}
	
	/********************接收参数区*************************/
	private long pi_id;//停车场主键ID
	private String area_code;//省市区区域代码  四川省 成都市 龙泉驿区  510112

	
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

	public void setPi_id(long pi_id) {
		this.pi_id = pi_id;
	}


	
	

	
	
	


	
	
	
}
