package ihiyo.test.actiontest;

import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;

/**
 *   版本升级管理测试
 * @author jingxiaohu
 *
 */
public class ReadVersionActionTest extends BaseActionTest{
	private String apiid="GAPchBsE";
	private String apikey="8rUDzPcvTMr46TmD";
	//从什么设备发出的请求1-收音机2-手机APP
	private String dtype = "2";
	@Test
	public void version() throws Exception{
		String url = BaseUrl+"gainupgrade.php";
		PostMethod post  = new PostMethod(url);
		post.addParameter("apiid", apiid);
		post.addParameter("apikey", apikey);
		post.addParameter("dtype", dtype);
		post.addParameter("userid","9");
		post.addParameter("devicemac", "d8:47:10:46:02:72");//d8:47:10:46:02:5e  44:33:4c:b7:f4:da
		String type = "2";//1:Android 2：ios 3:设备
		String version = "v1.1";
		String versioncode = "13";
		post.addParameter("type", type);
		post.addParameter("version", version);
		post.addParameter("versioncode", versioncode);
		
		int xx = HC.executeMethod(post);
		System.out.println(xx);
		try {
			String ds = post.getResponseBodyAsString();
			if(ds == null){
				System.out.println("无数据返回");
			}else{
				System.out.println(new String(post.getResponseBodyAsString().getBytes("utf-8"), "utf-8"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("checkLoginname is error"+e.getMessage(), e); 
		}finally{
			if(post!=null){
				post.releaseConnection();
				//释放链接
				HC.getHttpConnectionManager().closeIdleConnections(0);
			}
		}
	}
}
