
package com.park.mvc.v1.action.user;

import org.apache.struts2.convention.annotation.Action;

import com.park.DataSource.DynamicDataSourceHolder;
import com.park.bean.ReturnDataNew;
import com.park.core.Constants;
import com.park.mvc.v1.action.BaseV1Action;
import com.park.util.RequestUtil;

/**
 * 读取我的信息
 * @author jingxiaohu
 *
 */
public class Read_userinfoAction extends BaseV1Action {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6891425545908564737L;

	/**
	 * 获取我的基本信息
	 * @return
	 */
	@Action(value = "read_myinfo")
	public String 	Read_myinfo(){
	
		ReturnDataNew returnData = new ReturnDataNew();
		 try{
			 System.out.println(DynamicDataSourceHolder.getDataSourceKey());
			//检查是否是合法请求
			String ip = getIpAddr(getRequest());
			if(ip.startsWith("192.168") || ip.startsWith("127.0")){  
				ip  =  null;
			}
			if(RequestUtil.checkObjectBlank(sign)){
				returnData.setReturnData(errorcode_param, " sign is null", null);
				sendResp(returnData);
				return null;
			}
			if(ui_id < 1){
				returnData.setReturnData(errorcode_param, " ui_id is smaller than zero", null);
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
			
			userBiz.ReturnReadMyInfo(returnData, dtype,ui_id);
			sendResp(returnData);
			return null;
			
			} catch (Exception e) {
				log.error("Read_userinfoAction.ReturnReadMyInfo is error  2.21	Read-获取我的基本信息 (APPSDK-JAVA)- P",e);
				returnData.setReturnData(errorcode_systerm, "system is error", ""); 
			}
			sendResp(returnData);
			return null; 
	}
	
	/********************接收参数区*************************/
	
	/************************get set 方法区****************************/



	
	

	
	
	


	
	
	
}
