
package com.park.mvc.v1.action.park;

import org.apache.struts2.convention.annotation.Action;

import com.park.bean.ReturnDataNew;
import com.park.core.Constants;
import com.park.mvc.v1.action.BaseV1Action;
import com.park.util.RequestUtil;

/**
 * 通过GPS导航获取 该经纬度范围内的停车场数据列表
 * @author jingxiaohu
 *
 */
public class Read_GpsParkAction extends BaseV1Action {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4815394451510113939L;
	/**
	 * 通过GPS导航获取 该经纬度范围内的停车场数据列表
	 * @return
	 */
	@Action(value = "read_gpspark")
	public String 	read_gpspark(){
	
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
			
			
			if(RequestUtil.checkObjectBlank(area_code)){
				//area_code 省市区区域代码  四川省 成都市 龙泉驿区  510112
				returnData.setReturnData(errorcode_param, " area_code="+area_code+"  area_code is null", null);
				sendResp(returnData);
				return null;
			}
			String sign_str = getSignature(Constants.SYSTEM_KEY, dtype,ui_id);
			if(!sign.equalsIgnoreCase(sign_str)){
				log.error("sign="+sign+"  sign_str="+sign_str);
				returnData.setReturnData(errorcode_param, " sign is not right", "");
				sendResp(returnData);
				return null;
			}
			
			parkinfoBiz.ReturnRead_gpspark(returnData, dtype,ui_id,lng,lat,distance,park_type,type,area_code);
			sendResp(returnData);
			return null;
			
			} catch (Exception e) {
				log.error("Read_GpsParkAction.read_gpspark is error  2.21	Read-通过GPS导航获取 该经纬度范围内的停车场数据列表 (APPSDK-JAVA)- P",e);
				returnData.setReturnData(errorcode_systerm, "system is error", ""); 
			}
			sendResp(returnData);
			return null; 
	}
	
	
	/**
	 * 通过GPS导航获取 该经纬度范围内的停车场数据列表 ---- 车位租赁
	 * @return
	 */
	@Action(value = "read_gpspark_rent")
	public String 	read_gpspark_rent(){
	
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
			
			if(RequestUtil.checkObjectBlank(area_code)){
				//area_code 省市区区域代码  四川省 成都市 龙泉驿区  510112
				returnData.setReturnData(errorcode_param, " area_code="+area_code+"  area_code is null", null);
				sendResp(returnData);
				return null;
			}
			
			String sign_str = getSignature(Constants.SYSTEM_KEY, dtype,ui_id);
			if(!sign.equalsIgnoreCase(sign_str)){
				log.error("sign="+sign+"  sign_str="+sign_str);
				returnData.setReturnData(errorcode_param, " sign is not right", "");
				sendResp(returnData);
				return null;
			}
			
			parkinfoBiz.ReturnRead_gpspark_rent(returnData, dtype,ui_id,lng,lat,distance,type,park_type,area_code);
			sendResp(returnData);
			return null;
			
			} catch (Exception e) {
				log.error("Read_GpsParkAction.read_gpspark_rent is error  2.21	Read-通过GPS导航获取 该经纬度范围内的停车场数据列表--车位租赁 (APPSDK-JAVA)- P",e);
				returnData.setReturnData(errorcode_systerm, "system is error", ""); 
			}
			sendResp(returnData);
			return null; 
	}
	/********************接收参数区*************************/
	private String area_code;//省市区区域代码  四川省 成都市 龙泉驿区  510112
	private double lng;//场地经度
	private double lat;//场地纬度
	private int park_type;//停车场类型 0：地下室停车场 1：露天停车场 2：露天立体车库停车场
	private int distance;//距离 单位米
	
	//租赁 按距离 按价格 排序
	private int type;//0 :按距离 1：按价格
	/************************get set 方法区****************************/
	
	public double getLng() {
		return lng;
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


	public void setLng(double lng) {
		this.lng = lng;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}




	
	

	
	
	


	
	
	
}
