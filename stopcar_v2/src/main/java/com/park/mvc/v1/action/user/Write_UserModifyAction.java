
package com.park.mvc.v1.action.user;

import java.io.File;
import java.net.URLDecoder;

import org.apache.struts2.convention.annotation.Action;

import com.park.bean.ReturnDataNew;
import com.park.core.Constants;
import com.park.mvc.v1.action.BaseV1Action;
import com.park.util.RequestUtil;

/**
 * 用户重置密码 用户修改绑定手机号码   用户修改头像  用户修改昵称
 * @author jingxiaohu
 *
 */
public class Write_UserModifyAction extends BaseV1Action {


	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/ 
	private static final long serialVersionUID = -3599663972160625263L;

	@Action(value = "change_pass")
	public String modifyPassword(){
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
		
		userBiz.ReturnUserModifyPassword(returnData, dtype,tel,verify_code,verify_list,vclass,password,repassword);
		sendResp(returnData);
		return null;
		
		} catch (Exception e) {
			log.error("modifyPassword is error  2.21	Read-修改密码(APPSDK-JAVA)- P",e);
			returnData.setReturnData(errorcode_systerm, "system is error", ""); 
		}
		sendResp(returnData);
		return null; 
	}

	//修改绑定手机号
	@Action(value = "change_tel")
	public String change_tel(){
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
		String sign_str = getSignature(Constants.SYSTEM_KEY, dtype,tel,password,ui_id);
		if(!sign.equalsIgnoreCase(sign_str)){
			log.error("sign="+sign+"  sign_str="+sign_str);
			returnData.setReturnData(errorcode_param, " sign is not right", null);
			sendResp(returnData);
			return null;
		}
		
		userBiz.ReturnUserUpdateTel(returnData, dtype,tel,password,ui_id);
		sendResp(returnData);
		return null;
		
		} catch (Exception e) {
			log.error("change_tel is error  修改绑定手机号码 (APPSDK-JAVA)- P",e);
			returnData.setReturnData(errorcode_systerm, "system is error", ""); 
		}
		sendResp(returnData);
		return null; 
	}
	
	
	//修改用户基本信息
	@Action(value = "change_userinfo")
	public String change_userinfo(){
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
		if(!RequestUtil.checkObjectBlank(nickname)){
			//避免汉子的问题
			nickname = URLDecoder.decode(nickname, Constants.SYSTEM_CHARACTER);
			if(nickname.length() > 20){
				returnData.setReturnData(errorcode_param, " nickname'length must be  less than 20", "");
				sendResp(returnData);
				return null;
			}
		}
		if(!RequestUtil.checkObjectBlank(email)){
			if(!isEmail(email)){
				returnData.setReturnData(errorcode_param, "邮箱格式不正确","");
				sendResp(returnData);
				return null;
			}
			
		}
		
		String sign_str = getSignature(Constants.SYSTEM_KEY, dtype,ui_id);
		if(!sign.equalsIgnoreCase(sign_str)){
			log.error("sign="+sign+"  sign_str="+sign_str);
			returnData.setReturnData(errorcode_param, " sign is not right", "");
			sendResp(returnData);
			return null;
		}
		
		userBiz.change_userinfo(returnData, dtype,ui_id,avatar,avatarFileName,nickname,sex,name,driving_licence,email,ui_autopay,pay_source);
		sendResp(returnData);
		return null;
		
		} catch (Exception e) {
			log.error("change_userinfo is error  修改用户基本信息(APPSDK-JAVA)- P",e);
			returnData.setReturnData(errorcode_systerm, "system is error", ""); 
		}
		sendResp(returnData);
		return null; 
	}
	
	/********************接收参数区*************************/
	private String tel;//用户手机号码
	public String verify_code;//用户验证码
	private String verify_list;//由发送验证码接口或者重新发送验证码接口返回的verify_list参数的值
	private String password;//用户密码 
	private String repassword;//确认密码
	private String vclass;//固定参数：1：注册 2：重置密码
	private String nickname;//昵称
	private  String sex;//用户性别 : male 男   women 女   no  未知
	private String name;//用户姓名
	private String driving_licence;//用户驾驶证
	private String email;//用户邮箱
	//用户是否自动支付 开关
	private String ui_autopay;//是否自动支付 是否自动支付 ：0 ：不是 1：是
	private int pay_source;//支付类型1:支付宝 2：微信 3：银联 4：钱包
	
	
	
	//用户头像
	public File avatar;
    //提交过来的file的名字
	public String avatarFileName;
    //提交过来的file的MIME类型
	public String avatarContentType;
	
	/************************get set 方法区****************************/

	public String getTel() {
		return tel;
	}
	public String getUi_autopay() {
		return ui_autopay;
	}

	public void setUi_autopay(String ui_autopay) {
		this.ui_autopay = ui_autopay;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDriving_licence() {
		return driving_licence;
	}

	public void setDriving_licence(String driving_licence) {
		this.driving_licence = driving_licence;
	}

	public String getSex() {
		return sex;
	}


	public void setSex(String sex) {
		this.sex = sex;
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
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public File getAvatar() {
		return avatar;
	}
	public void setAvatar(File avatar) {
		this.avatar = avatar;
	}
	public String getAvatarFileName() {
		return avatarFileName;
	}
	public void setAvatarFileName(String avatarFileName) {
		this.avatarFileName = avatarFileName;
	}
	public String getAvatarContentType() {
		return avatarContentType;
	}
	public void setAvatarContentType(String avatarContentType) {
		this.avatarContentType = avatarContentType;
	}

	public int getPay_source() {
		return pay_source;
	}

	public void setPay_source(int pay_source) {
		this.pay_source = pay_source;
	}
	
	

	
	
	


	
	
	
}
