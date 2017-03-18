
package com.park.mvc.v1.action;

import org.apache.struts2.convention.annotation.Action;

import com.park.bean.ReturnDataNew;

/**
 * 获取设备 Android IOS 基本信息---获取设备的一些初始化配置信息 例如是否有SD卡等等
 * @author jingxiaohu
 *
 */
public class TestAction extends BaseV1Action {


	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/ 
	private static final long serialVersionUID = -3599663972160625262L;

	@Action(value = "test")
	public String test(){
	
	ReturnDataNew returnData = new ReturnDataNew();
	 try{
		//testBiz.doTest();
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
	public int type;// 1:Android 2：ios 3:设备
	/************************get set 方法区****************************/

	public int getType() {
		return type;
	}





	public void setType(int type) {
		this.type = type;
	}


	

}
