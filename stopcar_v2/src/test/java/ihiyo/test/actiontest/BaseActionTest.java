/**  
* @Title: BaseActionTest.java
* @Package intimes.test.action
* @Description: TODO(用一句话描述该文件做什么)
* @author 敬小虎  
* @date 2015年3月17日 上午9:53:04
* @version V1.0  
*/ 
package ihiyo.test.actiontest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.log4j.Logger;


/**
 * @ClassName: BaseActionTest
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 敬小虎
 * @date 2015年3月17日 上午9:53:04
 *
 */
public class BaseActionTest {
	Logger log = Logger.getLogger(BaseActionTest.class);
//	protected static String BaseUrl = "http://dt.by028.com/Api/v1_test1/";
//	protected static String BaseUrl = "http://smaradio.changhong.com/Api/v1_1/";
//	protected static String BaseUrl = "http://127.0.0.1:8080/smartspider/v1/"; 
//	protected static String BaseUrl = "http://192.168.199.3/test/v1/"; 
//	protected static String BaseUrl = "http://202.98.157.3/javaapp/v1/"; 
	protected static String BaseUrl = "http://smaradio1.changhong.com/javaapp/v1/"; 
	
	
	protected HttpClient HC=new HttpClient();
	public static void main(String[] args) {
//		System.out.println(RandomStringUtils.random(32, true, true));
//		String sign = RequestUtil.MD5("1{\"userid\":178,\"price\":12,\"orderid\":\"456aeacc8732be92cbb1436232968322\",\"time\":\"2015-07-07 09:36:38\",\"cpkeyword\":\"999-androidaibei_178-20150707093901\"}ac09f7fe949a83251ef1433320737087");
//		System.out.println(sign);
		System.out.println("010100031000200803040174".length());
	}
}
