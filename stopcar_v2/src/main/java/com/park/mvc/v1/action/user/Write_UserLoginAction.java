
package com.park.mvc.v1.action.user;

import org.apache.struts2.convention.annotation.Action;

import com.park.bean.ReturnDataNew;
import com.park.core.Constants;
import com.park.mvc.v1.action.BaseV1Action;
import com.park.util.RequestUtil;

/**
 * 用户登录  和 外部第三方用户系统授权登录 user_external
 * @author jingxiaohu
 *
 */
public class Write_UserLoginAction extends BaseV1Action {


	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/ 
	private static final long serialVersionUID = -3599663972160625263L;

	@Action(value = "login")
	public String UserLogin(){
	ReturnDataNew returnData = new ReturnDataNew();
	 try{
		//检查是否是合法请求
		String ip = getIpAddr(getRequest());
		if(ip.startsWith("192.168") || ip.startsWith("127.0")){  
			ip  =  null;
		}
		//2.1.4. 用户登录完成
		if(RequestUtil.checkObjectBlank(sign)){
			returnData.setReturnData(errorcode_param, " sign is null", null);
			sendResp(returnData);
			return null;
		}
			//普通登陆
			//用户登录
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
			if(RequestUtil.checkObjectBlank(password)){
				returnData.setReturnData(errorcode_param, " password is null", null);
				sendResp(returnData);
				return null;
			}
			if(password.length() < 5 && password.length() > 20){
				returnData.setReturnData(errorcode_param, "密码必须大于4位小于20位", null);
				sendResp(returnData);
				return null;
			}
			
			String sign_str = getSignature(Constants.SYSTEM_KEY, dtype,tel,password);
			if(!sign.equalsIgnoreCase(sign_str)){
				log.error("sign="+sign+"  sign_str="+sign_str);
				returnData.setReturnData(errorcode_param, " sign is not right", null);
				sendResp(returnData);
				return null;
			}
		
		userBiz.ReturnUserLogin(returnData, dtype,tel,password,ip);
		sendResp(returnData);
		return null;
		
		} catch (Exception e) {
			log.error("UserLogin is error  2.21	Read-登录 (APPSDK-JAVA)- P",e);
			returnData.setReturnData(errorcode_systerm, "system is error", ""); 
		}
		sendResp(returnData);
		return null; 
	}
	
	
	
	/**
	 * 第三方
	 * @return
	 */
	/*@Action(value = "external_login")
	public String user_external_login(){
	ReturnDataNew returnData = new ReturnDataNew();
	 try{
		//检查是否是合法请求
		String ip = getIpAddr(getRequest());
		if(ip.startsWith("192.168") || ip.startsWith("127.0")){  
			ip  =  null;
		}
		//2.1.4. 用户登录完成
		if(RequestUtil.checkObjectBlank(sign)){
			returnData.setReturnData(errorcode_param, " sign is null", null);
			sendResp(returnData);
			return null;
		}
		
		//用户登录
		if(RequestUtil.checkObjectBlank(up_type)){
			returnData.setReturnData(errorcode_param, " up_type is null", null);
			sendResp(returnData);
			return null;
		}
		if(RequestUtil.checkObjectBlank(up_key)){
			returnData.setReturnData(errorcode_param, " up_key is null", null);
			sendResp(returnData);
			return null;
		}
		if(RequestUtil.checkObjectBlank(up_token)){
			returnData.setReturnData(errorcode_param, " up_token is null", null);
			sendResp(returnData);
			return null;
		}
		
		String sign_str = getSignature(Constants.SYSTEM_KEY, dtype,up_type,up_key,up_token);
		if(!sign.equalsIgnoreCase(sign_str)){
			log.error("sign="+sign+"  sign_str="+sign_str);
			returnData.setReturnData(errorcode_param, " sign is not right", null);
			sendResp(returnData);
			return null;
		}
		
		userBiz.ReturnExternalUserLogin(returnData, dtype,up_type,up_key,up_token,imei,tel_version,item,avtar,nickname,sex,ip);
		sendResp(returnData);
		return null;
		
		} catch (Exception e) {
			log.error("external_login is error  2.21	Read-外部登录 (APPSDK-JAVA)- P",e);
			returnData.setReturnData(errorcode_systerm, "system is error", ""); 
		}
		sendResp(returnData);
		return null; 
	}*/
	

	
	
	/********************接收参数区*************************/
	public String tel;//用户手机号码
	public String password;//用户密码 
	public String uid;//用户ID
	public String avtar;//头像 
	public String nickname;//昵称
	public String sex;//性别
	//第三方登录
	public String up_type;//用户账户类型 0本地用户 1新浪账户 2腾讯账户 3人人账户 4开心账户 5天涯账户 6FACEBOOK',
	public String up_token;//外部TOKEN
	public String up_key;//外部KEY
	/************************get set 方法区****************************/

	public String getTel() {
		return tel;
	}
	public String getAvtar() {
		return avtar;
	}



	public void setAvtar(String avtar) {
		this.avtar = avtar;
	}



	public String getNickname() {
		return nickname;
	}



	public void setNickname(String nickname) {
		this.nickname = nickname;
	}



	public String getSex() {
		return sex;
	}



	public void setSex(String sex) {
		this.sex = sex;
	}






	public String getUp_token() {
		return up_token;
	}



	public void setUp_token(String up_token) {
		this.up_token = up_token;
	}



	public String getUp_key() {
		return up_key;
	}



	public void setUp_key(String up_key) {
		this.up_key = up_key;
	}



	public String getUid() {
		return uid;
	}



	public void setUid(String uid) {
		this.uid = uid;
	}



	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getUp_type() {
		return up_type;
	}
	public void setUp_type(String up_type) {
		this.up_type = up_type;
	}

	
}
