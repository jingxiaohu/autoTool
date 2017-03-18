
package com.park.mvc.v1.action.park;

import java.net.URLDecoder;

import org.apache.struts2.convention.annotation.Action;

import com.park.bean.ReturnDataNew;
import com.park.core.Constants;
import com.park.mvc.v1.action.BaseV1Action;
import com.park.util.RequestUtil;

/**
 * 记录场地出入口设备对应关系信息
 * @author jingxiaohu
 *
 */
public class Write_park_deviceAction extends BaseV1Action {


	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/ 
	private static final long serialVersionUID = -3599663972160625262L;


	/**
	 * 记录场地出入口设备对应关系信息
	 * @return
	 */
	@Action(value = "record_park_device")
	public String record_park_device(){
		ReturnDataNew returnData = new ReturnDataNew();
		 try{
			
			//检查是否是合法请求
			if(!checkRequest()){
				returnData.setReturnData(errorcode_param, " illegal request code", null);
				sendResp(returnData);
				return null;
			}
			if(pi_id < 1){
				//场地主键ID
				returnData.setReturnData(errorcode_param, " pi_id="+pi_id+"  pi_id is smaller than zero", null);
				sendResp(returnData);
				return null;
			}
			
			if(RequestUtil.checkObjectBlank(in_out)){
				//in_out;//出口或者入口 入口：enter  出口：exit
				returnData.setReturnData(errorcode_param, " in_out="+in_out+"  in_out is null", null);
				sendResp(returnData);
				return null;
			}
			if(RequestUtil.checkObjectBlank(in_out_code)){
				//in_out_code;//出入口编号  例如(A出口 B入口)
				returnData.setReturnData(errorcode_param, " in_out_code="+in_out_code+"  in_out_code is null", null);
				sendResp(returnData);
				return null;
			}
			if(RequestUtil.checkObjectBlank(camera)){
				//camera;//摄像头名称
				returnData.setReturnData(errorcode_param, " camera="+camera+"  camera is null", null);
				sendResp(returnData);
				return null;
			}else{
				camera = URLDecoder.decode(camera, Constants.SYSTEM_CHARACTER);
			}
			if(RequestUtil.checkObjectBlank(camera_mac)){
				//camera_mac;//摄像头MAC
				returnData.setReturnData(errorcode_param, " camera_mac="+camera_mac+"  camera_mac is null", null);
				sendResp(returnData);
				return null;
			}
			if(RequestUtil.checkObjectBlank(signo_name)){
				//signo_name;//道闸名称
				returnData.setReturnData(errorcode_param, " signo_name="+signo_name+"  signo_name is null", null);
				sendResp(returnData);
				return null;
			}else{
				signo_name = URLDecoder.decode(signo_name, Constants.SYSTEM_CHARACTER);
			}
			
			/*if(RequestUtil.checkObjectBlank(solid_garage_mac)){
				//solid_garage;//立体车库设备MAC
				returnData.setReturnData(errorcode_param, " solid_garage="+solid_garage_mac+"  solid_garage is null", null);
				sendResp(returnData);
				return null;
			}
			if(RequestUtil.checkObjectBlank(solid_garage_sn)){
				//solid_garage_sn;//立体车库设备编号
				returnData.setReturnData(errorcode_param, " solid_garage_sn="+solid_garage_sn+"  solid_garage_sn is null", null);
				sendResp(returnData);
				return null;
			}*/
			if(!RequestUtil.checkObjectBlank(area_code)){
				//省市县编号 140107
				//避免汉子的问题
				area_code = URLDecoder.decode(area_code, Constants.SYSTEM_CHARACTER);
			}
			String sign_str = getSignature(Constants.SYSTEM_KEY, dtype,pi_id,in_out);
			if(!sign.equalsIgnoreCase(sign_str)){
				log.error("sign="+sign+"  sign_str="+sign_str);
				returnData.setReturnData(errorcode_param, " sign is not right", null);
				sendResp(returnData);
				return null;
			}
			parkinfoBiz.record_park_device(returnData,pi_id,in_out,in_out_code,camera,camera_mac,signo_name,solid_garage_mac,solid_garage_sn,area_code);
			
			sendResp(returnData);
			return null;
			
			} catch (Exception e) {
				log.error("Write_rental_charging_ruleAction charging_rule  is error(DEVICE-JAVA)- P",e);
				returnData.setReturnData(errorcode_systerm, "system is error", ""); 
			}
			sendResp(returnData);
			return null; 
	}
	
	
	/**
	 * 更新场地出入口设备对应关系信息
	 * @return
	 */
	@Action(value = "update_park_device")
	public String update_park_device(){
		ReturnDataNew returnData = new ReturnDataNew();
		 try{
			
			//检查是否是合法请求
			if(!checkRequest()){
				returnData.setReturnData(errorcode_param, " illegal request code", null);
				sendResp(returnData);
				return null;
			}
			if(pd_id < 1){
				//场地出入口管理主键ID
				returnData.setReturnData(errorcode_param, " pd_id="+pd_id+"  pd_id is smaller zero", null);
				sendResp(returnData);
				return null;
			}
			if(!RequestUtil.checkObjectBlank(camera)){
				//camera;//摄像头名称
				camera = URLDecoder.decode(camera, Constants.SYSTEM_CHARACTER);
			}
			if(!RequestUtil.checkObjectBlank(signo_name)){
				signo_name = URLDecoder.decode(signo_name, Constants.SYSTEM_CHARACTER);
			}
			if(!RequestUtil.checkObjectBlank(area_code)){
				//省市县编号 140107
				//避免汉子的问题
				area_code = URLDecoder.decode(area_code, Constants.SYSTEM_CHARACTER);
			}
			
			String sign_str = getSignature(Constants.SYSTEM_KEY, dtype,pd_id);
			if(!sign.equalsIgnoreCase(sign_str)){
				log.error("sign="+sign+"  sign_str="+sign_str);
				returnData.setReturnData(errorcode_param, " sign is not right", null);
				sendResp(returnData);
				return null;
			}
			parkinfoBiz.update_park_device(returnData,pd_id,pi_id,in_out,in_out_code,camera,camera_mac,signo_name,solid_garage_mac,solid_garage_sn,area_code);
			
			sendResp(returnData);
			return null;
			
			} catch (Exception e) {
				log.error("Write_rental_charging_ruleAction update_charging_rule  is error(DEVICE-JAVA)- P",e);
				returnData.setReturnData(errorcode_systerm, "system is error", ""); 
			}
			sendResp(returnData);
			return null; 
	}
	
	/********************接收参数区*************************/
	private String area_code;//省市县编号 140107
	
	private long pi_id;//场地主键ID
	private String  in_out;//出口或者入口 入口：enter  出口：exit
	private String in_out_code;//出入口编号  例如(A出口 B入口)
	private String camera;//摄像头名称
	private String camera_mac;//摄像头MAC
	private String signo_name;//道闸名称
	private String solid_garage_mac;//立体车库设备MAC
	private String solid_garage_sn;//立体车库设备编号
	//更新场地出入口的设备时候使用
	 private long pd_id;//场地出入口管理主键ID
	
	/************************get set 方法区****************************/

	public long getPi_id() {
		return pi_id;
	}



	public String getArea_code() {
		return area_code;
	}


	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}


	public void setPi_id(long pi_id) {
		this.pi_id = pi_id;
	}


	public String getIn_out() {
		return in_out;
	}


	public void setIn_out(String in_out) {
		this.in_out = in_out;
	}


	public String getIn_out_code() {
		return in_out_code;
	}


	public void setIn_out_code(String in_out_code) {
		this.in_out_code = in_out_code;
	}


	public String getCamera() {
		return camera;
	}


	public void setCamera(String camera) {
		this.camera = camera;
	}


	public String getCamera_mac() {
		return camera_mac;
	}


	public void setCamera_mac(String camera_mac) {
		this.camera_mac = camera_mac;
	}


	public String getSigno_name() {
		return signo_name;
	}


	public void setSigno_name(String signo_name) {
		this.signo_name = signo_name;
	}




	public String getSolid_garage_mac() {
		return solid_garage_mac;
	}


	public void setSolid_garage_mac(String solid_garage_mac) {
		this.solid_garage_mac = solid_garage_mac;
	}


	public String getSolid_garage_sn() {
		return solid_garage_sn;
	}


	public void setSolid_garage_sn(String solid_garage_sn) {
		this.solid_garage_sn = solid_garage_sn;
	}


	public long getPd_id() {
		return pd_id;
	}


	public void setPd_id(long pd_id) {
		this.pd_id = pd_id;
	}



	
	
	
}
