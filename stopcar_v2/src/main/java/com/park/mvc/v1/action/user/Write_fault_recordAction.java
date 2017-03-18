
package com.park.mvc.v1.action.user;

import java.net.URLDecoder;

import org.apache.struts2.convention.annotation.Action;

import com.park.bean.ReturnDataNew;
import com.park.core.Constants;
import com.park.mvc.v1.action.BaseV1Action;
import com.park.util.RequestUtil;

/**
 * 故障上报记录
 * @author jingxiaohu
 *
 */
public class Write_fault_recordAction extends BaseV1Action {


	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/ 
	private static final long serialVersionUID = -3599663972160625262L;


	/**
	 * 故障上报记录
	 * @return
	 */
	@Action(value = "record_fault_record")
	public String record_fault_record(){
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
			
			if(pd_id < 1){
				//pd_id;//出入口设备主键ID
				returnData.setReturnData(errorcode_param, " pd_id="+pd_id+"  pd_id is smaller than zero", null);
				sendResp(returnData);
				return null;
			}
			if(RequestUtil.checkObjectBlank(fr_desc)){
				//fr_desc;//故障简述
				returnData.setReturnData(errorcode_param, " fr_desc="+fr_desc+"  fr_desc is null", null);
				sendResp(returnData);
				return null;
			}else{
				fr_desc = URLDecoder.decode(fr_desc, Constants.SYSTEM_CHARACTER);
			}
			if(!RequestUtil.checkObjectBlank(area_code)){
				//省市县编号 140107
				//避免汉子的问题
				area_code = URLDecoder.decode(area_code, Constants.SYSTEM_CHARACTER);
			}
			String sign_str = getSignature(Constants.SYSTEM_KEY, dtype,pi_id,pd_id);
			if(!sign.equalsIgnoreCase(sign_str)){
				log.error("sign="+sign+"  sign_str="+sign_str);
				returnData.setReturnData(errorcode_param, " sign is not right", null);
				sendResp(returnData);
				return null;
			}
			parkinfoBiz.record_fault_record(returnData,pi_id,pd_id,fr_type,fr_desc,area_code);
			
			sendResp(returnData);
			return null;
			
			} catch (Exception e) {
				log.error("Write_fault_recordAction record_fault_record  is error(DEVICE-JAVA)- P",e);
				returnData.setReturnData(errorcode_systerm, "system is error", ""); 
			}
			sendResp(returnData);
			return null; 
	}
	
	/********************接收参数区*************************/
	private long pi_id;//场地主键ID
	private long pd_id;//出入口设备主键ID
	private int  fr_type;//故障类型 0:摄像头故障 1：开闸设备故障
	private  String fr_desc;//故障简述
	private String area_code;//省市县编号 140107
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



	public long getPd_id() {
		return pd_id;
	}



	public void setPd_id(long pd_id) {
		this.pd_id = pd_id;
	}



	public int getFr_type() {
		return fr_type;
	}



	public void setFr_type(int fr_type) {
		this.fr_type = fr_type;
	}



	public String getFr_desc() {
		return fr_desc;
	}



	public void setFr_desc(String fr_desc) {
		this.fr_desc = fr_desc;
	}


	
}
