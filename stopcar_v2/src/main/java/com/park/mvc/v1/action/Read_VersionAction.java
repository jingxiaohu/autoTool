//
//package com.park.mvc.v1.action;
//
//import org.apache.struts2.convention.annotation.Action;
//
//import com.park.bean.ReturnDataNew;
//import com.park.util.RequestUtil;
//
///**
// * 获取设备 Android IOS 升级对应的包URL
// * @author jingxiaohu
// *
// */
//public class Read_VersionAction extends BaseV1Action {
//
//
//	/**
//	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
//	*/ 
//	private static final long serialVersionUID = -3599663972160625262L;
//
//	@Action(value = "gainupgrade")
//	public String Read_Version(){
//	
//	ReturnDataNew returnData = new ReturnDataNew();
//	 try{
//		log.info("devicemac="+devicemac+",userid="+userid+",mac="+mac+",version="+version+",versioncode="+versioncode+",type="+type); 
//		//检查是否是合法请求
//		if(!checkRequest()){
//			returnData.setReturnData(errorcode_param, " illegal request code", null);
//			sendResp(returnData);
//			return null;
//		}
//		if(RequestUtil.checkObjectBlank(version)){
//			returnData.setReturnData(errorcode_param, " version is null", null);
//			sendResp(returnData);
//			return null;
//		}
//		if(versioncode < 1){
//			returnData.setReturnData(errorcode_param, " versioncode="+versioncode+"  versioncode is not right", null);
//			sendResp(returnData);
//			return null;
//		}
//		if(type < 1){
//			returnData.setReturnData(errorcode_param, " type="+type+"  type is not right", null);
//			sendResp(returnData);
//			return null;
//		}
//		
//		versionBiz.ReturnVsersionUpgrade(returnData,devicemac,userid,mac,version,versioncode,type);
//		sendResp(returnData);
//		return null;
//		
//		
//		
//		} catch (Exception e) {
//			log.error("Read_VersionAction is error  获取设备 Android IOS 升级对应的包URL (JAVA)- P",e);
//			returnData.setReturnData(errorcode_systerm, "system is error", ""); 
//		}
//		sendResp(returnData);
//		return null; 
//	}
//	
//	
//
//	
//	
//	/********************接收参数区*************************/
//	public String mac;//设备MAC地址
//	public String version;//版本
//	public int versioncode;//版本内部编号
//	public int type;// 1:Android 2：ios 3:设备
//	/************************get set 方法区****************************/
//
//	public String getMac() {
//		return mac;
//	}
//	public void setMac(String mac) {
//		this.mac = mac;
//	}
//	public String getVersion() {
//		return version;
//	}
//	public void setVersion(String version) {
//		this.version = version;
//	}
//	public int getVersioncode() {
//		return versioncode;
//	}
//	public void setVersioncode(int versioncode) {
//		this.versioncode = versioncode;
//	}
//	public int getType() {
//		return type;
//	}
//	public void setType(int type) {
//		this.type = type;
//	}
//
//	
//
//}
