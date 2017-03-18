
package com.park.mvc.v1.action.user;

import org.apache.struts2.convention.annotation.Action;

import com.park.bean.ReturnDataNew;
import com.park.core.Constants;
import com.park.mvc.v1.action.BaseV1Action;
import com.park.util.RequestUtil;

/**
 * 获取设备 Android IOS 升级对应的包URL
 * @author jingxiaohu
 *
 */
public class Read_VersionAction extends BaseV1Action {


	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/ 
	private static final long serialVersionUID = -3599663972160625262L;

	@Action(value = "gainupgrade")
	public String Read_Version(){
	
	ReturnDataNew returnData = new ReturnDataNew();
	 try{
		log.info("uid="+uid+",version="+version+",versioncode="+versioncode+",dtype="+dtype); 
		if(RequestUtil.checkObjectBlank(sign)){
			returnData.setReturnData(errorcode_param, " sign is null", null);
			sendResp(returnData);
			return null;
		}
		//检查是否是合法请求
		if(RequestUtil.checkObjectBlank(version)){
			returnData.setReturnData(errorcode_param, " version is null", null);
			sendResp(returnData);
			return null;
		}
		if(versioncode < 1){
			returnData.setReturnData(errorcode_param, " versioncode="+versioncode+"  versioncode is not right", null);
			sendResp(returnData);
			return null;
		}
		
		String sign_str = getSignature(Constants.SYSTEM_KEY, dtype,versioncode);
		if(!sign.equalsIgnoreCase(sign_str)){
			log.error("sign="+sign+"  sign_str="+sign_str);
			returnData.setReturnData(errorcode_param, " sign is not right", null);
			sendResp(returnData);
			return null;
		}
		
		versionBiz.ReturnVsersionUpgrade(returnData,version,versioncode,dtype);
		sendResp(returnData);
		return null;
		
		
		
		} catch (Exception e) {
			log.error("Read_VersionAction is error  获取设备 Android IOS 升级对应的包URL (JAVA)- P",e);
			returnData.setReturnData(errorcode_systerm, "system is error", ""); 
		}
		sendResp(returnData);
		return null; 
	}
	
	

	
	
	/********************接收参数区*************************/
	public String version;//版本
	public int versioncode;//版本内部编号
	public String uid;//用户ID
	public String sign;
	/************************get set 方法区****************************/

	public String getVersion() {
		return version;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public int getVersioncode() {
		return versioncode;
	}
	public void setVersioncode(int versioncode) {
		this.versioncode = versioncode;
	}

	

}
