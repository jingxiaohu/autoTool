
package com.park.mvc.v1.action.pda;

import org.apache.struts2.convention.annotation.Action;

import com.park.bean.ReturnDataNew;
import com.park.core.Constants;
import com.park.mvc.v1.action.BaseV1Action;
import com.park.util.RequestUtil;

/**
 * 露天停车场PDA帐号信息和经纬度 MAC 的初始化
 * @author jingxiaohu
 *
 */
public class Write_PDA_initAction extends BaseV1Action {


	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/ 
	private static final long serialVersionUID = -3599663972160625262L;


	/**
	 * 露天停车场PDA帐号信息和经纬度 MAC 的初始化
	 * @return
	 */
	@Action(value = "init_pda")
	public String init_pda(){
		ReturnDataNew returnData = new ReturnDataNew();
		 try{
			
			//检查是否是合法请求
			if(!checkRequest()){
				returnData.setReturnData(errorcode_param, " illegal request code", null);
				sendResp(returnData);
				return null;
			}
			if(lng == 0){
				//lng; //地理经度
				returnData.setReturnData(errorcode_param, " lng="+lng+"  lng is zero", null);
				sendResp(returnData);
				return null;
			}
			if(lat == 0){
				//lat;//地理纬度
				returnData.setReturnData(errorcode_param, " lat="+lat+"  lat is zero", null);
				sendResp(returnData);
				return null;
			}
			if(RequestUtil.checkObjectBlank(loginname)){
				//帐号
				returnData.setReturnData(errorcode_param, " loginname="+loginname+"  loginname is null", null);
				sendResp(returnData);
				return null;
			}
			
			if(RequestUtil.checkObjectBlank(password)){
				//密码
				returnData.setReturnData(errorcode_param, " password="+password+"  password is null", null);
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
			String sign_str = getSignature(Constants.SYSTEM_KEY, dtype,park_type,loginname,password);
			if(!sign.equalsIgnoreCase(sign_str)){
				log.error("sign="+sign+"  sign_str="+sign_str);
				returnData.setReturnData(errorcode_param, " sign is not right", null);
				sendResp(returnData);
				return null;
			}
			parkinfoBiz.init_pda(returnData,lng,lat,loginname,password,mac,park_type,area_code);
			
			sendResp(returnData);
			return null;
			
			} catch (Exception e) {
				log.error("Write_PDA_initAction init_pda  is error(DEVICE-JAVA)- P",e);
				returnData.setReturnData(errorcode_systerm, "system is error", ""); 
			}
			sendResp(returnData);
			return null; 
	}
	
	
	
	/********************接收参数区*************************/
	private double lng; //地理经度
	private double lat;//地理纬度
	private String mac;//pda mac
	private String loginname;//露天停车场的分配的帐号
	private String password;//露天停车场的密码
	private String area_code;//省市县编号 140107  
	private int park_type;//停车场类型 0：地下室停车场 1：露天停车场 2：露天立体车库停车场
	/************************get set 方法区****************************/


	public double getLng() {
		return lng;
	}
	public int getPark_type() {
		return park_type;
	}
	public void setPark_type(int park_type) {
		this.park_type = park_type;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getArea_code() {
		return area_code;
	}
	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}
	
	
	
	


	

	
	
	
}
