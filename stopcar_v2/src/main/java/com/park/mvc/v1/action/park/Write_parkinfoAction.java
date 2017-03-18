
package com.park.mvc.v1.action.park;

import java.net.URLDecoder;

import org.apache.struts2.convention.annotation.Action;

import com.park.bean.ReturnDataNew;
import com.park.core.Constants;
import com.park.mvc.v1.action.BaseV1Action;
import com.park.util.RequestUtil;

/**
 * 记录停车场信息
 * @author jingxiaohu
 *
 */
public class Write_parkinfoAction extends BaseV1Action {


	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/ 
	private static final long serialVersionUID = -3599663972160625262L;


	/**
	 * 记录停车场信息
	 * @return
	 */
	@Action(value = "record_parkinfo")
	public String record_parkinfo(){
		ReturnDataNew returnData = new ReturnDataNew();
		 try{
			
			//检查是否是合法请求
			if(!checkRequest()){
				returnData.setReturnData(errorcode_param, " illegal request code", null);
				sendResp(returnData);
				return null;
			}
			if(RequestUtil.checkObjectBlank(name)){
				//场地名称
				
				returnData.setReturnData(errorcode_param, " name="+name+"  name is null", null);
				sendResp(returnData);
				return null;
			}else{
				name = returnUrlDecode(name);
			}
			
			if(RequestUtil.checkObjectBlank(address_name)){
				//address_name;//停车场地理街道名称
				
				returnData.setReturnData(errorcode_param, " address_name="+address_name+"  address_name is null", null);
				sendResp(returnData);
				return null;
			}else{
				address_name = returnUrlDecode(address_name);
			}
			/*if(lng == 0){
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
			}*/
			if(RequestUtil.checkObjectBlank(linkman_name)){
				//场地联系人姓名
				
//				returnData.setReturnData(errorcode_param, " linkman_name="+linkman_name+"  linkman_name is null", null);
//				sendResp(returnData);
//				return null;
			}else{
				linkman_name = returnUrlDecode(linkman_name);
			}
			
			if(RequestUtil.checkObjectBlank(linkman_tel)){
				//linkman_tel;//联系人电话
//				returnData.setReturnData(errorcode_param, " linkman_tel="+linkman_tel+"  linkman_tel is null", null);
//				sendResp(returnData);
//				return null;
			}else{
				if(!isMobileNO(linkman_tel)){
					//手机号码验证 
					returnData.setReturnData(errorcode_param, " linkman_tel="+linkman_tel+"  linkman_tel is not right telephone", null);
					sendResp(returnData);
					return null;
				}
			}
			
			
			if(RequestUtil.checkObjectBlank(copy_linkman_name)){
				//copy_linkman_name;//备用联系人姓名
				
//				returnData.setReturnData(errorcode_param, " copy_linkman_name="+copy_linkman_name+"  copy_linkman_name is null", null);
//				sendResp(returnData);
//				return null;
			}else{
				copy_linkman_name = returnUrlDecode(copy_linkman_name);
			}
			if(RequestUtil.checkObjectBlank(copy_linkman_tel)){
//				//copy_linkman_tel;//备用联系人手机
//				returnData.setReturnData(errorcode_param, " copy_linkman_tel="+copy_linkman_tel+"  copy_linkman_tel is null", null);
//				sendResp(returnData);
//				return null;
			}else{
				if(!isMobileNO(copy_linkman_tel)){
					//手机号码验证 
					returnData.setReturnData(errorcode_param, " copy_linkman_tel="+copy_linkman_tel+"  copy_linkman_tel is not right telephone", null);
					sendResp(returnData);
					return null;
				}
			}
			if(RequestUtil.checkObjectBlank(pi_phone)){
				//pi_phone;//场地座机号码
//				returnData.setReturnData(errorcode_param, " pi_phone="+pi_phone+"  pi_phone is null", null);
//				sendResp(returnData);
//				return null;
			}
			if(RequestUtil.checkObjectBlank(department)){
				//department;//负责单位
				
//				returnData.setReturnData(errorcode_param, " department="+department+"  department is null", null);
//				sendResp(returnData);
//				return null;
			}else{
				department = returnUrlDecode(department);
			}
			
//			if(carport_total < 1){
//				//carport_total;//场地车位总数
//				returnData.setReturnData(errorcode_param, " carport_total="+carport_total+"  carport_total is smaller zero", null);
//				sendResp(returnData);
//				return null;
//			}
//			if(enter_num < 1){
//				//enter_num;//场地入口数量
//				returnData.setReturnData(errorcode_param, " enter_num="+enter_num+"  enter_num is smaller zero", null);
//				sendResp(returnData);
//				return null;
//			}
//			if(exit_num < 1){
//				//exit_num;//场地出口数量
//				returnData.setReturnData(errorcode_param, " exit_num="+exit_num+"  exit_num is smaller zero", null);
//				sendResp(returnData);
//				return null;
//			}
//			if(hlc_enter_num < 1){
//				//hlc_enter_num;//场地入口道闸数量
//				returnData.setReturnData(errorcode_param, " hlc_enter_num="+hlc_enter_num+"  hlc_enter_num is smaller zero", null);
//				sendResp(returnData);
//				return null;
//			}
//			if(hlc_exit_num < 1){
//				//hlc_exit_num;//场地出口道闸数量
//				returnData.setReturnData(errorcode_param, " hlc_exit_num="+hlc_exit_num+"  hlc_exit_num is smaller zero", null);
//				sendResp(returnData);
//				return null;
//			}
//			
//			
//			
//			if(enter_camera_num < 1){
//				//enter_camera_num;//场地入口摄像头数量
//				returnData.setReturnData(errorcode_param, " enter_camera_num="+enter_camera_num+"  enter_camera_num is smaller zero", null);
//				sendResp(returnData);
//				return null;
//			}
//			if(exit_camera_num < 1){
//				//exit_camera_num;//场地出口摄像头数量
//				returnData.setReturnData(errorcode_param, " exit_camera_num="+exit_camera_num+"  exit_camera_num is smaller zero", null);
//				sendResp(returnData);
//				return null;
//			}
			
			if(RequestUtil.checkObjectBlank(camera_info)){
				//camera_info;//场地摄像头信息
				
//				returnData.setReturnData(errorcode_param, " camera_info="+camera_info+"  camera_info is null", null);
//				sendResp(returnData);
//				return null;
			}else{
				camera_info = URLDecoder.decode(camera_info, Constants.SYSTEM_CHARACTER);
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
			
			
			
			String sign_str = getSignature(Constants.SYSTEM_KEY, dtype,park_type);
			if(!sign.equalsIgnoreCase(sign_str)){
				log.error("sign="+sign+"  sign_str="+sign_str);
				returnData.setReturnData(errorcode_param, " sign is not right", null);
				sendResp(returnData);
				return null;
			}
			parkinfoBiz.record_parkinfo(returnData,name,address_name,lng,lat,linkman_name,linkman_tel,copy_linkman_name
					,copy_linkman_tel,pi_phone,department,enter_num,exit_num,hlc_enter_num,hlc_exit_num,enter_camera_num,exit_camera_num
					,camera_info,park_type,area_code,expect_money,allow_revoke_time,allow_expect_time,is_expect,carport_yet,carport_space,carport_total,moth_car_num,moth_car_num_space,time_car_num,time_car_num_space,expect_car_num);
			
			sendResp(returnData);
			return null;
			
			} catch (Exception e) {
				log.error("Write_parkinfoAction record_parkinfo  is error(DEVICE-JAVA)- P",e);
				returnData.setReturnData(errorcode_systerm, "system is error", ""); 
			}
			sendResp(returnData);
			return null; 
	}
	
	
	/**
	 * 记录停车场信息
	 * @return
	 */
	@Action(value = "update_parkinfo")
	public String update_parkinfo(){
		ReturnDataNew returnData = new ReturnDataNew();
		 try{
			
			//检查是否是合法请求
			if(!checkRequest()){
				returnData.setReturnData(errorcode_param, " illegal request code", null);
				sendResp(returnData);
				return null;
			}
			
			if(pi_id < 1){
				//pi_id;//场地主键ID
				returnData.setReturnData(errorcode_param, " pi_id="+pi_id+"  pi_id is smaller zero", null);
				sendResp(returnData);
				return null;
			}
			
			if(!RequestUtil.checkObjectBlank(name)){
				name = returnUrlDecode(name);
			}
			
			if(!RequestUtil.checkObjectBlank(address_name)){
				address_name = returnUrlDecode(address_name);
			}
			if(!RequestUtil.checkObjectBlank(linkman_name)){
				linkman_name = returnUrlDecode(linkman_name);
			}
			
			if(!RequestUtil.checkObjectBlank(linkman_tel)){
				if(!isMobileNO(linkman_tel)){
					//手机号码验证 
					returnData.setReturnData(errorcode_param, " linkman_tel="+linkman_tel+"  linkman_tel is not right telephone", null);
					sendResp(returnData);
					return null;
				}
			}
			if(!RequestUtil.checkObjectBlank(copy_linkman_name)){
				copy_linkman_name = returnUrlDecode(copy_linkman_name);
			}
			if(!RequestUtil.checkObjectBlank(copy_linkman_tel)){
				if(!isMobileNO(copy_linkman_tel)){
					//手机号码验证 
					returnData.setReturnData(errorcode_param, " copy_linkman_tel="+copy_linkman_tel+"  copy_linkman_tel is not right telephone", null);
					sendResp(returnData);
					return null;
				}
			}
			if(!RequestUtil.checkObjectBlank(department)){
				department = returnUrlDecode(department);
			}
			if(!RequestUtil.checkObjectBlank(camera_info)){
				camera_info = URLDecoder.decode(camera_info, Constants.SYSTEM_CHARACTER);
			}
			
			if(!RequestUtil.checkObjectBlank(area_code)){
				//省市县编号 140107
				//避免汉子的问题
				area_code = URLDecoder.decode(area_code, Constants.SYSTEM_CHARACTER);
			}
			
			String sign_str = getSignature(Constants.SYSTEM_KEY, dtype,pi_id);
			if(!sign.equalsIgnoreCase(sign_str)){
				log.error("sign="+sign+"  sign_str="+sign_str);
				returnData.setReturnData(errorcode_param, " sign is not right", null);
				sendResp(returnData);
				return null;
			}
			parkinfoBiz.update_parkinfo(returnData,pi_id,name,address_name,lng,lat,linkman_name,linkman_tel,copy_linkman_name
					,copy_linkman_tel,pi_phone,department,enter_num,exit_num,hlc_enter_num,hlc_exit_num,enter_camera_num,exit_camera_num
					,camera_info,park_type,area_code,expect_money,allow_revoke_time,allow_expect_time,is_expect,carport_yet,carport_space,carport_total,moth_car_num,moth_car_num_space,time_car_num,time_car_num_space,expect_car_num);
			
			sendResp(returnData);
			return null;
			
			} catch (Exception e) {
				log.error("Write_parkinfoAction update_parkinfo  is error(DEVICE-JAVA)- P",e);
				returnData.setReturnData(errorcode_systerm, "system is error", ""); 
			}
			sendResp(returnData);
			return null; 
	}
	
	/********************接收参数区*************************/
	private String name;//场地名称
	private String address_name;//停车场地理街道名称
	private double lng; //地理经度
	private double lat;//地理纬度
	private String linkman_name;//场地联系人姓名
	private String linkman_tel;//联系人电话
	private String copy_linkman_name;//备用联系人姓名
	private String copy_linkman_tel;//备用联系人手机
	private String pi_phone;//场地座机号码
	private String department;//负责单位 
	private int enter_num;//场地入口数量
	private int exit_num;//场地出口数量
	private int hlc_enter_num;//场地入口道闸数量
	private int hlc_exit_num;//场地出口道闸数量
	private int enter_camera_num;//场地入口摄像头数量
	private int exit_camera_num;//场地出口摄像头数量
	private String camera_info;//场地摄像头信息
	private String park_type;//停车场类型 0：道闸停车场 1：占道车场 2：露天立体车库停车场
	private String area_code;//省市县编号 140107  
	
	private int is_expect;//是否开启预约 0:不开启 1：开启
	private int  expect_money;//每小时预约费用（单位分）   默认值 就是 起步价
	private int  allow_revoke_time;//允许预约撤单时间(单位分钟)----*暂时不开启
	private int  allow_expect_time;//允许预约时间最大时长(单位分钟)----*暂时不开启 
	
	private Integer carport_yet;//INT    场地已停车位
	private Integer carport_space;//INT    场地空车位个数
	private Integer carport_total;//INT    场地总车位个数
	private Integer moth_car_num;//INT    租赁离线包月车位总个数
	private Integer moth_car_num_space;//INT    租赁离线剩余车位数
	private Integer time_car_num;//INT    时间段包月车位总数
	private Integer time_car_num_space;//INT    时间段包月车位剩余个数
	private Integer expect_car_num;//INT    已预约车辆数
	//更新场地的信息
	private long pi_id;//场地主键ID

	/************************get set 方法区****************************/
	
	
	public String getName() {
		return name;
	}
	public int getCarport_yet() {
		return carport_yet;
	}


	public void setCarport_yet(int carport_yet) {
		this.carport_yet = carport_yet;
	}


	public int getCarport_space() {
		return carport_space;
	}


	public void setCarport_space(int carport_space) {
		this.carport_space = carport_space;
	}


	public int getMoth_car_num_space() {
		return moth_car_num_space;
	}


	public void setMoth_car_num_space(int moth_car_num_space) {
		this.moth_car_num_space = moth_car_num_space;
	}


	public int getTime_car_num() {
		return time_car_num;
	}


	public void setTime_car_num(int time_car_num) {
		this.time_car_num = time_car_num;
	}


	public int getTime_car_num_space() {
		return time_car_num_space;
	}


	public void setTime_car_num_space(int time_car_num_space) {
		this.time_car_num_space = time_car_num_space;
	}


	public int getExpect_car_num() {
		return expect_car_num;
	}


	public void setExpect_car_num(int expect_car_num) {
		this.expect_car_num = expect_car_num;
	}


	public int getIs_expect() {
		return is_expect;
	}


	public void setIs_expect(int is_expect) {
		this.is_expect = is_expect;
	}


	public int getMoth_car_num() {
		return moth_car_num;
	}


	public void setMoth_car_num(int moth_car_num) {
		this.moth_car_num = moth_car_num;
	}




	public int getExpect_money() {
		return expect_money;
	}


	public void setExpect_money(int expect_money) {
		this.expect_money = expect_money;
	}


	public int getAllow_revoke_time() {
		return allow_revoke_time;
	}


	public void setAllow_revoke_time(int allow_revoke_time) {
		this.allow_revoke_time = allow_revoke_time;
	}


	public int getAllow_expect_time() {
		return allow_expect_time;
	}


	public void setAllow_expect_time(int allow_expect_time) {
		this.allow_expect_time = allow_expect_time;
	}


	public long getPi_id() {
		return pi_id;
	}


	public void setPi_id(long pi_id) {
		this.pi_id = pi_id;
	}


	public void setName(String name) {
		this.name = name;
	}
	public String getAddress_name() {
		return address_name;
	}
	public void setAddress_name(String address_name) {
		this.address_name = address_name;
	}
	public String getLinkman_name() {
		return linkman_name;
	}
	public void setLinkman_name(String linkman_name) {
		this.linkman_name = linkman_name;
	}
	public String getLinkman_tel() {
		return linkman_tel;
	}
	public void setLinkman_tel(String linkman_tel) {
		this.linkman_tel = linkman_tel;
	}
	public String getCopy_linkman_name() {
		return copy_linkman_name;
	}
	public void setCopy_linkman_name(String copy_linkman_name) {
		this.copy_linkman_name = copy_linkman_name;
	}
	public String getCopy_linkman_tel() {
		return copy_linkman_tel;
	}
	public void setCopy_linkman_tel(String copy_linkman_tel) {
		this.copy_linkman_tel = copy_linkman_tel;
	}
	public String getPi_phone() {
		return pi_phone;
	}
	public void setPi_phone(String pi_phone) {
		this.pi_phone = pi_phone;
	}
	public int getCarport_total() {
		return carport_total;
	}
	public void setCarport_total(int carport_total) {
		this.carport_total = carport_total;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public int getEnter_num() {
		return enter_num;
	}
	public void setEnter_num(int enter_num) {
		this.enter_num = enter_num;
	}
	public int getExit_num() {
		return exit_num;
	}
	public void setExit_num(int exit_num) {
		this.exit_num = exit_num;
	}
	public int getHlc_enter_num() {
		return hlc_enter_num;
	}
	public void setHlc_enter_num(int hlc_enter_num) {
		this.hlc_enter_num = hlc_enter_num;
	}
	public int getHlc_exit_num() {
		return hlc_exit_num;
	}
	public void setHlc_exit_num(int hlc_exit_num) {
		this.hlc_exit_num = hlc_exit_num;
	}
	public int getEnter_camera_num() {
		return enter_camera_num;
	}
	public void setEnter_camera_num(int enter_camera_num) {
		this.enter_camera_num = enter_camera_num;
	}
	public int getExit_camera_num() {
		return exit_camera_num;
	}
	public void setExit_camera_num(int exit_camera_num) {
		this.exit_camera_num = exit_camera_num;
	}
	public String getCamera_info() {
		return camera_info;
	}
	public void setCamera_info(String camera_info) {
		this.camera_info = camera_info;
	}
	
	public String getPark_type() {
		return park_type;
	}


	public void setPark_type(String park_type) {
		this.park_type = park_type;
	}




	public String getArea_code() {
		return area_code;
	}


	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}


	public double getLng() {
		return lng;
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
	
	


	

	
	
	
}
