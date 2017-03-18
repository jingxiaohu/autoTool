
package com.park.mvc.v1.action.user;

import org.apache.struts2.convention.annotation.Action;

import com.park.bean.ReturnDataNew;
import com.park.core.Constants;
import com.park.mvc.v1.action.BaseV1Action;
import com.park.util.RequestUtil;

/**
 * 用户发送验证码
 * @author jingxiaohu
 *
 */
public class Write_SendCodeAction extends BaseV1Action {


	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/ 
	private static final long serialVersionUID = -3599663972160625263L;

	@Action(value = "sendcode")
	public String UserSendCode(){
	ReturnDataNew returnData = new ReturnDataNew();
	 try{
		//检查是否是合法请求
		String ip = getIpAddr(getRequest());
		if(ip.startsWith("192.168") || ip.startsWith("127.0")){  
			ip  =  null;
		}
		//2.1.4. 用户注册完成
		if(RequestUtil.checkObjectBlank(sign)){
			returnData.setReturnData(errorcode_param, " sign is null", null);
			sendResp(returnData);
			return null;
		}
		if(RequestUtil.checkObjectBlank(tel)){
			returnData.setReturnData(errorcode_param, " tel is null", null);
			sendResp(returnData);
			return null;
		}
		if(!isMobileNO(tel)){
			returnData.setReturnData(errorcode_param, " tel is not a right 电话号码", null);
			sendResp(returnData);
			return null;
		}
		//1：注册 2：重置密码 
		if(RequestUtil.checkObjectBlank(vclass)){
			returnData.setReturnData(errorcode_param, " vclass is null", null);
			sendResp(returnData);
			return null;
		}
		String sign_str = getSignature(Constants.SYSTEM_KEY, dtype,tel,vclass);
		if(!sign.equalsIgnoreCase(sign_str)){
			log.error("sign="+sign+"  sign_str="+sign_str);
			returnData.setReturnData(errorcode_param, " sign is not right", null);
			sendResp(returnData);
			return null;
		}
		
		sMSBiz.ReturnSendSMg(returnData, dtype,tel,vclass);
		sendResp(returnData);
		return null;
		
		} catch (Exception e) {
			log.error("UserSendCode is error  2.21	Read-发送验证码(APPSDK-JAVA)- P",e);
			returnData.setReturnData(errorcode_systerm, "system is error", ""); 
		}
		sendResp(returnData);
		return null; 
	}
	
	/**
	 * 重发验证码
	 * @return
	 */
	@Action(value = "resendcode")
	public String UserReSendCode(){
	ReturnDataNew returnData = new ReturnDataNew();
	 try{
		//检查是否是合法请求
		String ip = getIpAddr(getRequest());
		if(ip.startsWith("192.168") || ip.startsWith("127.0")){  
			ip  =  null;
		}
		//2.1.4. 用户注册完成
		if(RequestUtil.checkObjectBlank(sign)){
			returnData.setReturnData(errorcode_param, " sign is null", null);
			sendResp(returnData);
			return null;
		}
		if(RequestUtil.checkObjectBlank(tel)){
			returnData.setReturnData(errorcode_param, " tel is null", null);
			sendResp(returnData);
			return null;
		}
		if(!isMobileNO(tel)){
			returnData.setReturnData(errorcode_param, " tel is not a right 电话号码", null);
			sendResp(returnData);
			return null;
		}
		//1：注册 2：重置密码 
		if(RequestUtil.checkObjectBlank(vclass)){
			returnData.setReturnData(errorcode_param, " vclass is null", null);
			sendResp(returnData);
			return null;
		}
		if(RequestUtil.checkObjectBlank(verify_list)){
			returnData.setReturnData(errorcode_param, " verify_list is null", null);
			sendResp(returnData);
			return null;
		}
		String sign_str = getSignature(Constants.SYSTEM_KEY, dtype,tel,verify_list,vclass);
		if(!sign.equalsIgnoreCase(sign_str)){
			log.error("sign="+sign+"  sign_str="+sign_str);
			returnData.setReturnData(errorcode_param, " sign is not right", null);
			sendResp(returnData);
			return null;
		}
		
		sMSBiz.ReturnReSendSMg(returnData, dtype,tel,vclass,verify_list);
		sendResp(returnData);
		return null;
		
		} catch (Exception e) {
			log.error("UserSendCode is error  2.21	Read-发送验证码(APPSDK-JAVA)- P",e);
			returnData.setReturnData(errorcode_systerm, "system is error", ""); 
		}
		sendResp(returnData);
		return null; 
	}
	
	
	/**
	 * 找回密码 邮箱
	 * @return
	 */
	/*@Action(value = "findpass_mail")
	public String findpass_mail(){
	ReturnDataNew returnData = new ReturnDataNew();
	 try{
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
		if(RequestUtil.checkObjectBlank(email)){
			returnData.setReturnData(errorcode_param, " email is null", null);
			sendResp(returnData);
			return null;
		}
		if(!isEmail(email)){
			returnData.setReturnData(errorcode_param, " email is not a right email", null);
			sendResp(returnData);
			return null;
		}
		String sign_str = getSignature(Constants.SYSTEM_KEY, dtype,email);
		if(!sign.equalsIgnoreCase(sign_str)){
			log.error("sign="+sign+"  sign_str="+sign_str);
			returnData.setReturnData(errorcode_param, " sign is not right", null);
			sendResp(returnData);
			return null;
		}
		
		sMSBiz.ReturnFindPassWord_mail(returnData, dtype,email);
		sendResp(returnData);
		return null;
		
		} catch (Exception e) {
			log.error("findpass_mail is error  2.21	Read-找回密码 邮箱(APPSDK-JAVA)- P",e);
			returnData.setReturnData(errorcode_systerm, "system is error", ""); 
		}
		sendResp(returnData);
		return null; 
	}*/

	
	
	/********************接收参数区*************************/
	public String tel;//用户手机号码
	public String vclass;//固定参数：1：注册 2：重置密码 3:重置绑定电话号码
	public String verify_list;//md5(tel+code)
	// email邮件地址
	public String email;
	/************************get set 方法区****************************/

	public String getTel() {
		return tel;
	}
	public String getVerify_list() {
		return verify_list;
	}

	public void setVerify_list(String verify_list) {
		this.verify_list = verify_list;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getVclass() {
		return vclass;
	}
	public void setVclass(String vclass) {
		this.vclass = vclass;
	}

	
	

	
	
	


	
	
	
}
