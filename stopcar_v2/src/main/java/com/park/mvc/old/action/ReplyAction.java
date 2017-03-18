
package com.park.mvc.old.action;

import org.apache.struts2.convention.annotation.Action;

import com.park.core.BaseAction;

/**
 * 
* @ClassName: ReplyAction
* @Description: TODO(2.8	回复用户充钱通知成功（APPSDK-JAVA）- P)
* @author 敬小虎
* @date 2015年3月9日 下午4:10:55
*
 */
public class ReplyAction extends BaseAction {


	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/ 
	private static final long serialVersionUID = -3599663972160625262L;


	/**
	 * 
	* @Title: Reply
	* @Description: TODO(2.8	回复用户充钱通知成功（APPSDK-JAVA）- P)
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	@Action(value = "reply")
	public String Reply(){
		//return SUCCESS;return ERROR;
		try {
			if(state == 1 && jsondata != null){
				sendResp("success");
				return null; 
			}
			
		} catch (Exception e) {
			log.error("HitDownAction_HitDown is error 2.8	用户点击下载记录（APPSDK-JAVA）- P",e);
			sendResp("error");
		}
		sendResp("error");
		return null; 
	}
	
	

	
	
	/********************接收参数区*************************/
/*	private String userid;
	private double price;
	private String orderid;
	private String time;*/
	private int state;
	private String jsondata;


	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}



	/**
	 * @param state the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}



	/**
	 * @return the jsondata
	 */
	public String getJsondata() {
		return jsondata;
	}



	/**
	 * @param jsondata the jsondata to set
	 */
	public void setJsondata(String jsondata) {
		this.jsondata = jsondata;
	}
	
//	public String m;//MD5散列(loginname+ password+ auth_code  + imei +reg_type +key）




	
	
	/************************get set 方法区****************************/
	
}
