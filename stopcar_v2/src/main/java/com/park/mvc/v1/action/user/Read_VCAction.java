
package com.park.mvc.v1.action.user;

import org.apache.struts2.convention.annotation.Action;

import com.park.bean.ReturnDataNew;
import com.park.core.Constants;
import com.park.mvc.v1.action.BaseV1Action;

/**
 * 获取我的账户出入记录
 * @author jingxiaohu
 *
 */
public class Read_VCAction extends BaseV1Action {


	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/ 
	private static final long serialVersionUID = -3599663972160625262L;


	/**
	 * 获取我的账户出入记录
	 * @return
	 */
	@Action(value = "vc_record")
	public String vc_record(){
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
			orderBiz.vc_record(returnData,dtype,ui_id,type,page,size);
			
			sendResp(returnData);
			return null;
			
			} catch (Exception e) {
				log.error("Read_VCAction vc_record  is error(DEVICE-JAVA)- P",e);
				returnData.setReturnData(errorcode_systerm, "system is error", ""); 
			}
			sendResp(returnData);
			return null; 
	}
	
	
	/********************接收参数区*************************/
	private int type;// 获取虚拟币行为类型  0:消耗和充值  1：消耗  2：充值
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


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}



    
	
	
}
