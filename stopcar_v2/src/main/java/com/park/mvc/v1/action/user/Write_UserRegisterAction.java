
package com.park.mvc.v1.action.user;

import org.apache.struts2.convention.annotation.Action;

import com.park.bean.ReturnDataNew;
import com.park.core.Constants;
import com.park.mvc.v1.action.BaseV1Action;
import com.park.util.RequestUtil;

/**
 * 用户注册
 * @author jingxiaohu
 *
 */
public class Write_UserRegisterAction extends BaseV1Action {


	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/ 
	private static final long serialVersionUID = -3599663972160625263L;

	@Action(value = "reg")
	public String UserRegister(){
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
			returnData.setReturnData(errorcode_param, "请输入正确的电话号码,亲", null);
			sendResp(returnData);
			return null;
		}
		if(RequestUtil.checkObjectBlank(verify_code)){
			returnData.setReturnData(errorcode_param, " verify_code is null", null);
			sendResp(returnData);
			return null;
		}
		if(RequestUtil.checkObjectBlank(verify_list)){
			returnData.setReturnData(errorcode_param, " verify_list is null", null);
			sendResp(returnData);
			return null;
		}
		if(RequestUtil.checkObjectBlank(vclass)){
			returnData.setReturnData(errorcode_param, " vclass is null", null);
			sendResp(returnData);
			return null;
		}
		if(RequestUtil.checkObjectBlank(password)){
			returnData.setReturnData(errorcode_param, " password is null", null);
			sendResp(returnData);
			return null;
		}
		if(RequestUtil.checkObjectBlank(repassword)){
			returnData.setReturnData(errorcode_param, " repassword is null", null);
			sendResp(returnData);
			return null;
		}
		if(password.length() < 6 && password.length() > 16){
			returnData.setReturnData(errorcode_param, "密码必须大于5位小于17位", null);
			sendResp(returnData);
			return null;
		}
		if(!password.equalsIgnoreCase(repassword)){
			//2次密码不一致
			returnData.setReturnData(errorcode_param, " repassword is not equal repassword", null);
			sendResp(returnData);
			return null;
		}
		
		String sign_str = getSignature(Constants.SYSTEM_KEY, dtype,tel,verify_code,verify_list,vclass,password,repassword);
		if(!sign.equalsIgnoreCase(sign_str)){
			log.error("sign="+sign+"  sign_str="+sign_str);
			returnData.setReturnData(errorcode_param, " sign is not right", null);
			sendResp(returnData);
			return null;
		}
		
		userBiz.ReturnUserRegister(returnData, dtype,tel,verify_code,verify_list,vclass,password,repassword);
		sendResp(returnData);
		return null;
		
		} catch (Exception e) {
			log.error("UserRegister is error  2.21	Read-注册 (APPSDK-JAVA)- P",e);
			returnData.setReturnData(errorcode_systerm, "system is error", ""); 
		}
		sendResp(returnData);
		return null; 
	}
	
	
	
	

	
	
	/********************接收参数区*************************/
	public String tel;//用户手机号码
	public String verify_code;//用户验证码
	public String verify_list;//由发送验证码接口或者重新发送验证码接口返回的verify_list参数的值
	public String password;//用户密码 
	public String repassword;//确认密码
	public String vclass;//固定参数：1：注册 2：重置密码
	public String uid;//用户ID
	
	
	
	/************************get set 方法区****************************/

	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getVerify_code() {
		return verify_code;
	}
	public void setVerify_code(String verify_code) {
		this.verify_code = verify_code;
	}
	public String getVerify_list() {
		return verify_list;
	}
	public void setVerify_list(String verify_list) {
		this.verify_list = verify_list;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRepassword() {
		return repassword;
	}
	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
	public String getVclass() {
		return vclass;
	}
	public void setVclass(String vclass) {
		this.vclass = vclass;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	
}
