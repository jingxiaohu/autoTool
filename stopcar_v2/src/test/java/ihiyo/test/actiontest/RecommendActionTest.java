package ihiyo.test.actiontest;

import java.io.File;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.junit.Test;

/**
 *   推荐模块
 * @author jingxiaohu
 *
 */
public class RecommendActionTest extends BaseActionTest{
	private String apiid="GAPchBsE";
	private String apikey="8rUDzPcvTMr46TmD";
	//从什么设备发出的请求1-收音机2-手机APP 3:web
	private String dtype = "2";
	private String devicemac="d8:47:10:46:02:5e";
	private String userid = "152";
	String url = BaseUrl+"recommend.php";
	/**
	 * 2.7.5. 获取推荐专辑接口
	 * @throws Exception 
	 */
	@Test
	public void vod() throws Exception{
		PostMethod post  = new PostMethod(url);
		String Apitype = "all";
		//分类点播
		post.addParameter("Apitype", Apitype);
		post.addParameter("username", "152");
		post.addParameter("page","0");
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
