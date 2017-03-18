
package com.park.mvc.v1.action.order;

import java.net.URLDecoder;

import org.apache.struts2.convention.annotation.Action;

import com.park.bean.ReturnDataNew;
import com.park.core.Constants;
import com.park.mvc.v1.action.BaseV1Action;
import com.park.util.RequestUtil;

/**
 * 用户反馈
 * @author jingxiaohu
 *
 */
public class Write_feedbackAction extends BaseV1Action {


	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/ 
	private static final long serialVersionUID = -3599663972160625262L;


	/**
	 * 用户反馈
	 * @return
	 */
	@Action(value = "feedback")
	public String feedback(){
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
			
			if(RequestUtil.checkObjectBlank(content)){
				//反馈信息content
				returnData.setReturnData(errorcode_param, " content="+content+"  content is null", null);
				sendResp(returnData);
				return null;
			}else{
				content  = URLDecoder.decode(content, Constants.SYSTEM_CHARACTER);
			}
			String sign_str = getSignature(Constants.SYSTEM_KEY, dtype,ui_id);
			if(!sign.equalsIgnoreCase(sign_str)){
				log.error("sign="+sign+"  sign_str="+sign_str);
				returnData.setReturnData(errorcode_param, " sign is not right", null);
				sendResp(returnData);
				return null;
			}
			userBiz.feedback(returnData,ui_id,content);
			
			sendResp(returnData);
			return null;
			
			} catch (Exception e) {
				log.error("Write_feedbackAction feedback  is error(DEVICE-JAVA)- P",e);
				returnData.setReturnData(errorcode_systerm, "system is error", ""); 
			}
			sendResp(returnData);
			return null; 
	}
	
	/********************接收参数区*************************/
	private String content;//反馈信息
	/************************get set 方法区****************************/


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}





	



	
	
	
}
