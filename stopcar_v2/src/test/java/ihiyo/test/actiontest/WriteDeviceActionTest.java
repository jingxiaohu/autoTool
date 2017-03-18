package ihiyo.test.actiontest;

import java.io.File;
import java.net.URLEncoder;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.junit.Test;

/**
 *   设备上传状态 环境数据 等等管理
 * @author jingxiaohu
 *
 */
public class WriteDeviceActionTest extends BaseActionTest{
	private String apiid="GAPchBsE";
	private String apikey="8rUDzPcvTMr46TmD";
	//从什么设备发出的请求1-收音机2-手机APP 3:web
	private String devicetype="family";//car family
	private String dtype = "1";
	private String devicemac="d8:47:10:46:02:5e";
	private String userid = "152";
	String url = BaseUrl+"device.php";
	
	
	
	/**
	 * 记录设备开机关机状态
	 * @throws Exception 
	 */
	@Test
	public void shutopen() throws Exception{
		PostMethod post  = new PostMethod(BaseUrl+"shutopen.php");
		post.addParameter("isopen", "0");//关机 0:开机 1：关机
		post.addParameter("versioncode", "14");
		post.addParameter("on_line", "14");
		post.addParameter("act_time", "13465656447");
		post.addParameter("ip", "192.168.1.102");
		post.addParameter("address", URLEncoder.encode("四川.南充", "UTF-8"));
		String result  = doPost(post);
		//{"data":null,"errormsg":"设备状态信息提交成功","errorno":"0"}
	}
	
	
	
	/**
	 * 提交设备状态
	 * @throws Exception 
	 */
	@Test
	public void state() throws Exception{
		PostMethod post  = new PostMethod(url);
		String Apitype = "state";
		post.addParameter("Apitype", Apitype);
		post.addParameter("sn", "");
		post.addParameter("name", "d8:47:10:46:02:6e");
		post.addParameter("wifi", "TP-LINK_674EF0");
		post.addParameter("ip", "192.168.1.102");
		post.addParameter("mac", "d8:47:10:46:02:6e");
		post.addParameter("rmac", "e0:05:c5:67:4e:f0");
		post.addParameter("internet", "1");
		String result  = doPost(post);
		//{"data":null,"errormsg":"设备状态信息提交成功","errorno":"0"}
	}
	/**
	 * 2.3.3. 获取设备状态
	 * @throws Exception 
	 */
	@Test
	public void get_state() throws Exception{
		PostMethod post  = new PostMethod(url);
		String Apitype = "get_state";
		post.addParameter("Apitype", Apitype);
		post.addParameter("name", "d8:47:10:46:02:6e");
		String result  = doPost(post);
		//{"data":{"name":"d8:47:10:46:02:6e","time":"1453100931","wifi":"TP-LINK_674EF0","ip":"192.168.1.102","mac":"d8:47:10:46:02:6e","rmac":"e0:05:c5:67:4e:f0","online":1},"errormsg":"获取数据成功","errorno":"0"}
	}
	
	/**
	 * 2.3.4. 用户环境数据提交
	 * @throws Exception 
	 */
	@Test
	public void update_amb() throws Exception{
		PostMethod post  = new PostMethod(url);
		String Apitype = "update_amb";
		post.addParameter("Apitype", Apitype);
		
		post.addParameter("device", "d8:47:10:46:02:6e");
		post.addParameter("tmp", "12");
		post.addParameter("tmp_out", "35");
		post.addParameter("hum", "23");
		post.addParameter("hum_out", "24");
		post.addParameter("pres", "25");
		post.addParameter("pres_out", "37");
		String result  = doPost(post);
		//{"data":null,"errormsg":"提交环境数据成功","errorno":"0"}
	}
	public String doPost(PostMethod post) throws Exception{
		post.addParameter("apiid", apiid);
		post.addParameter("apikey", apikey);
		post.addParameter("dtype", dtype);
		post.addParameter("userid",userid);
		post.addParameter("devicemac", devicemac);
		post.addParameter("devicetype", devicetype);
		int xx = HC.executeMethod(post);
		System.out.println(xx);
		try {
			String ds = post.getResponseBodyAsString();
			if(ds == null){
				System.out.println("无数据返回");
				return null;
			}else{
				ds = new String(post.getResponseBodyAsString().getBytes("utf-8"), "utf-8");
				System.out.println(ds);
				return ds;
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
		return null; 
	}
	
	public String doPost(PostMethod post,String Apitype,File file,String telephone) throws Exception{
		Part[] parts = {
		    	 //new StringPart("text_content", "大叔大叔大叔大叔的萨达十大萨达十大","utf-8"),
				//new StringPart("status", URLEncoder.encode(status, "utf-8"),
				new StringPart("apiid", apiid),
				new StringPart("apikey", apikey),
				new StringPart("dtype", dtype),
				new StringPart("userid",userid),
				new StringPart("userid",userid),
		    	new StringPart("Apitype", Apitype),
		    	new StringPart("username", telephone),
		        new FilePart("toppic", file)
		    };  
		post.setRequestEntity(new MultipartRequestEntity(parts, post.getParams()));
		int xx = HC.executeMethod(post);
		System.out.println(xx);
		try {
			String ds = post.getResponseBodyAsString();
			if(ds == null){
				System.out.println("无数据返回");
				return null;
			}else{
				ds = new String(post.getResponseBodyAsString().getBytes("utf-8"), "utf-8");
				System.out.println(ds);
				return ds;
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
		return null; 
	}
}
