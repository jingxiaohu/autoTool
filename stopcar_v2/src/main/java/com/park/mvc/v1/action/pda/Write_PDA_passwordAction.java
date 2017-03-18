
package com.park.mvc.v1.action.pda;

import org.apache.struts2.convention.annotation.Action;

import com.park.bean.ReturnDataNew;
import com.park.core.Constants;
import com.park.mvc.v1.action.BaseV1Action;
import com.park.util.RequestUtil;

/**
 * 用户重置PDA密码
 * @author jingxiaohu
 *
 */
public class Write_PDA_passwordAction extends BaseV1Action {


	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/ 
	private static final long serialVersionUID = -3599663972160625263L;

	@Action(value = "change_pass_pda")
	public String change_pass_pda(){
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
		
		if(RequestUtil.checkObjectBlank(vclass)){
			returnData.setReturnData(errorcode_param, " vclass is null", null);
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
		if(RequestUtil.checkObjectBlank(loginname)){
			//帐号
			returnData.setReturnData(errorcode_param, " loginname="+loginname+"  loginname is null", null);
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
		if(password.length() < 5 && password.length() > 20){
			returnData.setReturnData(errorcode_param, "密码必须大于4位小于20位", null);
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
		if(RequestUtil.checkObjectBlank(area_code)){
			//省市县编号 140107
			//避免汉子的问题
			
			returnData.setReturnData(errorcode_param, " area_code="+area_code+"  area_code is null", null);
			sendResp(returnData);
			return null;
		}
		if(RequestUtil.checkObjectBlank(park_type)){
			//park_type;//停车场类型 0：地下室停车场 1：露天停车场 2：露天立体车库停车场
			returnData.setReturnData(errorcode_param, " park_type="+park_type+"  park_type is null", null);
			sendResp(returnData);
			return null;
		}
		parkinfoBiz.change_pass_pda(returnData, dtype,tel,verify_code,verify_list,vclass,password,repassword,area_code,park_type,loginname);
		sendResp(returnData);
		return null;
		
		} catch (Exception e) {
			log.error("change_pass_pda is error  2.21	Read-修改PDA密码(APPSDK-JAVA)- P",e);
			returnData.setReturnData(errorcode_systerm, "system is error", ""); 
		}
		sendResp(returnData);
		return null; 
	}

	
	/********************接收参数区*************************/
	private String loginname;//帐号
	private String tel;//用户手机号码
	public String verify_code;//用户验证码
	private String verify_list;//由发送验证码接口或者重新发送验证码接口返回的verify_list参数的值
	private String password;//用户密码 
	private String repassword;//确认密码
	private String vclass;//固定参数：1：注册 2：重置密码
	private String area_code;//省市县编号 140107  
	private int park_type;//停车场类型 0：地下室停车场 1：露天停车场 2：露天立体车库停车场
	/************************get set 方法区****************************/

	public String getTel() {
		return tel;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getVerify_code() {
		return verify_code;
	}

	public String getArea_code() {
		return area_code;
	}

	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}

	public int getPark_type() {
		return park_type;
	}

	public void setPark_type(int park_type) {
		this.park_type = park_type;
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

	public void setTel(String tel) {
		this.tel = tel;
	}


	
	
	
}
