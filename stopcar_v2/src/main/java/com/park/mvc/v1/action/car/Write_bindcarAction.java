
package com.park.mvc.v1.action.car;

import java.io.File;
import java.net.URLDecoder;

import org.apache.struts2.convention.annotation.Action;

import com.park.bean.ReturnDataNew;
import com.park.core.Constants;
import com.park.mvc.v1.action.BaseV1Action;
import com.park.util.RequestUtil;

/**
 * 用户添加或者删除绑定车牌号
 * @author jingxiaohu
 *
 */
public class Write_bindcarAction extends BaseV1Action {


	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/ 
	private static final long serialVersionUID = -3599663972160625262L;


	/**
	 * 用户添加绑定车牌号
	 * @return
	 */
	@Action(value = "bindcar")
	public String bindcar(){
		ReturnDataNew returnData = new ReturnDataNew();
		 try{
			//检查是否是合法请求
			if(!checkRequest()){
				returnData.setReturnData(errorcode_param, " illegal request code", null);
				sendResp(returnData);
				return null;
			}
			if(ui_id < 1){
				//用户ID
				returnData.setReturnData(errorcode_param, " ui_id="+ui_id+"  ui_id is smaller than zero", null);
				sendResp(returnData);
				return null;
			}
			
			if(act < 1){
				//用户行为动作
				returnData.setReturnData(errorcode_param, " act="+act+"  act is smaller than zero", null);
				sendResp(returnData);
				return null;
			}
			
			if(RequestUtil.checkObjectBlank(car_code)){
				//car_code 车牌号
				returnData.setReturnData(errorcode_param, " car_code="+car_code+"  car_code is null", null);
				sendResp(returnData);
				return null;
			}else{
				car_code  = URLDecoder.decode(car_code, Constants.SYSTEM_CHARACTER);
			}
			String sign_str = getSignature(Constants.SYSTEM_KEY, dtype,ui_id,car_code,act);
			if(!sign.equalsIgnoreCase(sign_str)){
				log.error("sign="+sign+"  sign_str="+sign_str);
				returnData.setReturnData(errorcode_param, " sign is not right", null);
				sendResp(returnData);
				return null;
			}
			carBiz.bindcar(returnData,ui_id,car_code,act);
			
			sendResp(returnData);
			return null;
			
			} catch (Exception e) {
				log.error("Write_bindcarAction bindcar  is error(DEVICE-JAVA)- P",e);
				returnData.setReturnData(errorcode_systerm, "system is error", ""); 
			}
			sendResp(returnData);
			return null; 
	}
	
	
	
	
	/**
	 * 用户更新车辆信息
	 * @return
	 */
	@Action(value = "update_car")
	public String update_car(){
		ReturnDataNew returnData = new ReturnDataNew();
		 try{
			//检查是否是合法请求
			if(!checkRequest()){
				returnData.setReturnData(errorcode_param, " illegal request code", null);
				sendResp(returnData);
				return null;
			}
			if(ui_id < 1){
				//用户ID
				returnData.setReturnData(errorcode_param, " ui_id="+ui_id+"  ui_id is smaller than one", null);
				sendResp(returnData);
				return null;
			}
			
			if(uc_id < 1){
				//主键ID
				returnData.setReturnData(errorcode_param, " uc_id="+uc_id+"  uc_id is smaller than one", null);
				sendResp(returnData);
				return null;
			}
			
			if(RequestUtil.checkObjectBlank(car_code)){
				//car_code 车牌号
				returnData.setReturnData(errorcode_param, " car_code="+car_code+"  car_code is null", null);
				sendResp(returnData);
				return null;
			}else{
				car_code  = URLDecoder.decode(car_code, Constants.SYSTEM_CHARACTER);
			}
			
			if(!RequestUtil.checkObjectBlank(car_brand)){
				//车辆品牌
				car_brand = URLDecoder.decode(car_brand, Constants.SYSTEM_CHARACTER);
			}
			
			
			String sign_str = getSignature(Constants.SYSTEM_KEY, dtype,ui_id);
			if(!sign.equalsIgnoreCase(sign_str)){
				log.error("sign="+sign+"  sign_str="+sign_str);
				returnData.setReturnData(errorcode_param, " sign is not right", null);
				sendResp(returnData);
				return null;
			}
			carBiz.update_car(returnData,ui_id,car_code,uc_id,lience,lienceFileName,lienceContentType,car_brand,uc_color);
			
			sendResp(returnData);
			return null;
			
			} catch (Exception e) {
				log.error("Write_bindcarAction update_car  is error(DEVICE-JAVA)- P",e);
				returnData.setReturnData(errorcode_systerm, "system is error", ""); 
			}
			sendResp(returnData);
			return null; 
	}
	
	/********************接收参数区*************************/
	private String car_code;//车牌号
	private int act;// 1:添加车牌 2：删除车牌
	
	
	//用户更新车辆信息
	private long uc_id;//表主键ID
	private File lience;//行驶证照片
	private String lienceFileName;//行驶证照片 文件名称
    //提交过来的file的MIME类型
	private String lienceContentType;
	private String car_brand;//车辆品牌
	private int uc_color;//车辆颜色:0 未定  1：黑色 2：白色 3：银色 4：金色 5：红色 6 ：黄色 7：绿色 8：蓝色 9：橙色 10：灰色
	/************************get set 方法区****************************/


	public String getCar_code() {
		return car_code;
	}
	public long getUc_id() {
		return uc_id;
	}




	public void setUc_id(long uc_id) {
		this.uc_id = uc_id;
	}







	public File getLience() {
		return lience;
	}




	public void setLience(File lience) {
		this.lience = lience;
	}




	public String getLienceContentType() {
		return lienceContentType;
	}




	public void setLienceContentType(String lienceContentType) {
		this.lienceContentType = lienceContentType;
	}




	public String getLienceFileName() {
		return lienceFileName;
	}




	public void setLienceFileName(String lienceFileName) {
		this.lienceFileName = lienceFileName;
	}




	public String getCar_brand() {
		return car_brand;
	}




	public void setCar_brand(String car_brand) {
		this.car_brand = car_brand;
	}




	public int getUc_color() {
		return uc_color;
	}




	public void setUc_color(int uc_color) {
		this.uc_color = uc_color;
	}




	public void setCar_code(String car_code) {
		this.car_code = car_code;
	}
	public int getAct() {
		return act;
	}
	public void setAct(int act) {
		this.act = act;
	}



	



	
	
	
}
