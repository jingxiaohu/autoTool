
package com.park.v1.biz;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.park.Transaction.BaseTransaction;
import com.park.bean.App_version;
import com.park.bean.ReturnDataNew;
import com.park.util.RequestUtil;
import com.park.v1.model.Version;

/**
 * 获取设备 Android IOS 升级对应的包URL
 * @author jingxiaohu
 *
 */
@Service
public class VersionBiz extends BaseBiz{

	@Resource(name="baseTransaction")
	protected BaseTransaction baseTransaction;

	
	/**
	 * 获取设备 Android IOS 升级对应的包URL
	 * @param returnData
	 * @param devicemac
	 * @param userid
	 * @param mac
	 * @param version
	 * @param versioncode
	 * @param dtype
	 */
	public void ReturnVsersionUpgrade(ReturnDataNew returnData,String version,int versioncode, int dtype) {
		// TODO Auto-generated method stub
		try {
			if(version.indexOf("v") == -1){
				version = "v"+version;
			}
			Version versionmodel = new Version();
			//如果晚上10点---第二天的早上8点 这之间不进行版本升级
			/*if(isUpgrade()){
				//如果为true 表示不能升级
				returnData.setReturnData(errorcode_success, null, versionmodel); 
				return;
			}*/
			//dtype //从什么设备发出的请求  0:android 1:ios 2: web 3:PC
			if(dtype == 0){ 
				versionmodel.setType("android");
				//从app_version表里面获取数据
				String sql = "select * from app_version where cav_version_code> ? order by cav_version_code desc limit 1";
				App_version app_version = getMySelfService().queryUniqueT(sql,App_version.class ,versioncode);
				if(app_version == null){
					returnData.setReturnData(errorcode_success, null, versionmodel); 
					return;
				}else{
					//否有更新  1 –是 0 –否
					versionmodel.setUpdate(1);
					//type 1:Android 2：ios 3:设备  android/ios/wifi/fm/bt/device
					if(dtype == 0){
						versionmodel.setUrl(app_version.getAndroid_url());
						if(RequestUtil.checkObjectBlank(app_version.getAndroid_url())){
							//如果Android手机端不需要进行APP升级 只是设备端需要进行升级则进行设置版本代码为0
							app_version.setCav_version_code(0);//不进行升级
							versionmodel.setUpdate(0);//不更新
							
						}
					}else if(dtype == 1){
						versionmodel.setUrl(app_version.getIos_url());
						if(RequestUtil.checkObjectBlank(app_version.getIos_url())){
							//如果Android手机端不需要进行APP升级 只是设备端需要进行升级则进行设置版本代码为0
							app_version.setCav_version_code(0);//不进行升级
							versionmodel.setUpdate(0);//不更新
						}
					}
					//最新版本
					versionmodel.setVersion(app_version.getCav_version());
					//最新版本号
					versionmodel.setVersioncode(app_version.getCav_version_code());
					//更新内容
					versionmodel.setContent(app_version.getContent());
					//MD5校验
					versionmodel.setMd5(app_version.getCav_md5());
					returnData.setReturnData(errorcode_success, null, versionmodel); 
					return;
				}
			}else if(dtype == 1){
				versionmodel.setType("ios");
			}else if(dtype == 2){
				versionmodel.setType("web");
			}
			
					
			returnData.setReturnData(errorcode_success, null, versionmodel); 
			return;
				
				
			
			
		} catch (Exception e) {
			log.error("VersionBiz ReturnVsersionUpgrade is error", e);
			returnData.setReturnData(errorcode_systerm, "system is error", null); 
		}
	}
	
	/********************************************下面是返回当前版本是否是全镜像还是增量版本*****************************************************/
	/**
	 * 如果晚上10点---第二天的早上8点 这之间不进行版本升级
	 * @return
	 */
	private boolean isUpgrade() {
		// TODO Auto-generated method stub
		Date date = new Date();
		
		Calendar cl = Calendar.getInstance(Locale.CHINESE);
		int year = cl.get(Calendar.YEAR);     //获取年份
		int month = cl.get(Calendar.MONTH);     //获取月份
		int day = cl.get(Calendar.DATE);     //获取日期
		cl.set(year, month, day, 22, 0,0);
//		System.out.println(sf.format(cl.getTime()));
		
		
		Calendar cl2 = Calendar.getInstance(Locale.CHINESE);
		int year2 = cl2.get(Calendar.YEAR);     //获取年份
		int month2 = cl2.get(Calendar.MONTH);     //获取月份
		int day2 = cl2.get(Calendar.DATE);     //获取日期
		cl2.set(year2, month2, day2, 8, 0,0);
//		System.out.println(sf.format(cl2.getTime()));
		
		long timenn = date.getTime();
		if(timenn >= cl.getTime().getTime() || timenn <= cl2.getTime().getTime()){
			return true;
		}
		
		return false;
	}
	/*@Test
	public void test(){
		boolean flag = isUpgrade();
		System.out.println(flag);
	}*/
}
