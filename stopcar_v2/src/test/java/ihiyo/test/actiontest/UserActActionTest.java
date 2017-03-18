package ihiyo.test.actiontest;

import java.io.File;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.junit.Test;

/**
 *   用户基本信息管理测试
 * @author jingxiaohu
 *
 */
public class UserActActionTest extends BaseActionTest{
	private String apiid="GAPchBsE";
	private String apikey="8rUDzPcvTMr46TmD";
	//从什么设备发出的请求1-收音机2-手机APP 3:web
	private String dtype = "2";
	private String devicemac="d8:47:10:46:02:5e";
	private String userid = "152";
	String url = BaseUrl+"useract.php";
	
	
	/**
	 * 2.7.1. 获取用户自定义音频
	 * @throws Exception 
	 */
	@Test
	public void update_user_media() throws Exception{
		PostMethod post  = new PostMethod(url);
		String Apitype = "update_user_media";
		//分类点播 http://smaradio.changhong.com/Api/v1_1/user.php?apiid=GAPchBsE&apikey=8rUDzPcvTMr46TmD&dtype=2&userid=100&devicemac=d8:47:10:46:02:72&devicetype=family&Apitype=get_user_media&uid=100&device=d8:47:10:46:02:72
		post.addParameter("Apitype", Apitype);
		post.addParameter("username", "152");
		post.addParameter("device", "98:2f:3c:ab:c8:a1");
		String result  = doPost(post);
		//
	}
	
	
	/**
	 * 2.7.1. 收藏音频专辑接口
	 * @throws Exception 
	 */
	@Test
	public void update_favorite() throws Exception{
		PostMethod post  = new PostMethod(url);
		String Apitype = "update_favorite";
		//分类点播
		post.addParameter("Apitype", Apitype);
		post.addParameter("id", "1714");
		post.addParameter("uid", "152");
		post.addParameter("type", "2");//1直播/2点播音频
		post.addParameter("username","");
		post.addParameter("device", "98:2f:3c:ab:c8:a1");
		post.addParameter("action", "add");
		post.addParameter("source", "qingting");
		
		String result  = doPost(post);
		//
	}
	/**
	 * 2.7.2. 获取用户收藏音频专辑接口
	 * @throws Exception 
	 */
	@Test
	public void get_favorite() throws Exception{
		PostMethod post  = new PostMethod(BaseUrl+"user.php");
//		PostMethod post  = new PostMethod(url);
		String Apitype = "get_favorite";
		//分类点播
		post.addParameter("Apitype", Apitype);
//		post.addParameter("id", "1714");
		post.addParameter("uid", "152");
		post.addParameter("type", "0");//1直播/2点播音频（0或者空是所有）
		post.addParameter("device", "d8:47:10:46:02:5e");
		post.addParameter("page","0");
		post.addParameter("pagesize", "30");
		String result  = doPost(post);
		//
	}
	/**
	 * 2.7.5. 获取用户订阅专辑接口
	 * @throws Exception 
	 */
	@Test
	public void get_sub() throws Exception{
//		PostMethod post  = new PostMethod(BaseUrl+"user.php");
		PostMethod post  = new PostMethod(url);
		String Apitype = "get_sub";
		//分类点播
		post.addParameter("Apitype", Apitype);
//		post.addParameter("id", "1714");
		post.addParameter("uid", "152");
		post.addParameter("page","0");
		post.addParameter("pagesize", "30");
		String result  = doPost(post);
		//
	}
	/**
	 * 2.7.7. 用户播放记录提交接口
	 * @throws Exception 
	 */
	@Test
	public void update_play_record() throws Exception{
		PostMethod post  = new PostMethod(url);
		String Apitype = "update_play_record";
		//分类点播
		post.addParameter("Apitype", Apitype);
		post.addParameter("id", "1722");
		post.addParameter("uid", userid);
		post.addParameter("device", "98:2f:3c:ab:c8:a1");
		post.addParameter("type", "2");
		post.addParameter("source", "qingting");
		
		String result  = doPost(post);
		System.out.println(result);
		//
	}
	/**
	 * 2.7.7. 用户播放记录获取接口
	 * @throws Exception 
	 */
	@Test
	public void get_play_record() throws Exception{
//		PostMethod post  = new PostMethod(BaseUrl+"user.php");
		PostMethod post  = new PostMethod(url);
		String Apitype = "get_play_record";
		//分类点播
		post.addParameter("Apitype", Apitype);
		post.addParameter("uid", userid);
		post.addParameter("device", "98:2f:3c:ab:c8:a1");// d8:47:10:46:02:5e
		post.addParameter("type", "2");//1直播/2点播音频/(0或者空为所有)
		post.addParameter("page","1");
		post.addParameter("pagesize", "30");
		
		String result  = doPost(post);
		//
	}
	public String doPost(PostMethod post) throws Exception{
		post.addParameter("apiid", apiid);
		post.addParameter("apikey", apikey);
		post.addParameter("dtype", dtype);
		post.addParameter("userid",userid);
		post.addParameter("devicemac", devicemac);
		int xx = HC.executeMethod(post);
		System.out.println(post.getQueryString());
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
