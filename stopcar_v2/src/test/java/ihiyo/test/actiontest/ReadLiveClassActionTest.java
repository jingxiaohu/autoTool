package ihiyo.test.actiontest;

import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;

/**
 *   直播节目管理测试
 * @author jingxiaohu
 *
 */
public class ReadLiveClassActionTest extends BaseActionTest{
	private String apiid="GAPchBsE";
	private String apikey="8rUDzPcvTMr46TmD";
	//从什么设备发出的请求1-收音机2-手机APP
	private String dtype = "1";
	@Test
	public void live() throws Exception{ 
		String url = BaseUrl+"live.php";
		PostMethod post  = new PostMethod(url);
		GetMethod post2 = null;
		post.addParameter("apiid", apiid);
		post.addParameter("apikey", apikey);
		post.addParameter("dtype", dtype);
		post.addParameter("userid","49");
		post.addParameter("device", "d8:47:10:45:fc:9f");
		String Apitype = "live_type";//live_type//categories//channels_list//channel_info//program_list
		//分类点播
		post.addParameter("Apitype", Apitype);
		if("live_type".equalsIgnoreCase(Apitype)){
			System.out.println(URLDecoder.decode("%E5%9B%9B%E5%B7%9D", "utf-8"));
//			post  = new UTF8PostMethod(url);

			/*Part[] parts = {
					new StringPart("province", "四川"),
					new StringPart("apiid", apiid),
					new StringPart("apikey", apikey),
					new StringPart("dtype", dtype),
					new StringPart("userid","49"),
					new StringPart("device", "d8:47:10:45:fc:9f"),
					new StringPart("Apitype", Apitype)
			};*/
//			post.setRequestEntity(new MultipartRequestEntity(parts,post.getParams()));
			 post2  = new GetMethod(url);
			NameValuePair[] xx = new NameValuePair[7];
			xx[0]=new NameValuePair("apiid", apiid);
			xx[1]=new NameValuePair("apikey", apikey);
			xx[2]=new NameValuePair("dtype", dtype);
			xx[3]=new NameValuePair("userid","49");
			xx[4]=new NameValuePair("device", "d8:47:10:45:fc:9f");
			xx[5]=new NameValuePair("province",URLEncoder.encode("北京", "UTF-8"));
			xx[6]=new NameValuePair("Apitype", Apitype);
//			post.setRequestBody(xx);
			post2.setQueryString(xx);
			
			
//			post.addParameter("province",URLEncoder.encode("四川", "UTF-8"));
		}else if ("categories".equalsIgnoreCase(Apitype)){
			//我们自己的分类表主键
			post.addParameter("id", "63");
		}else if("channels_list".equalsIgnoreCase(Apitype)){
			post.addParameter("id", "79");
		}else if("channel_info".equalsIgnoreCase(Apitype)){
			//获取直播节目
			post.addParameter("id", "1");
		}else if("program_list".equalsIgnoreCase(Apitype)){
			//获取某个直播节目的节目单
			post.addParameter("id", "1722");
			post.addParameter("day","5");
		}
		int xx = HC.executeMethod(post2);
		System.out.println(xx);
		try {
			String ds = post2.getResponseBodyAsString();
			if(ds == null){
				System.out.println("无数据返回");
			}else{
				System.out.println(new String(post2.getResponseBodyAsString().getBytes("utf-8"), "utf-8"));
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
	
	
	 public static class UTF8PostMethod extends PostMethod{  
	        public UTF8PostMethod(String url){  
	            super(url);  
	        }  
	        @Override  
	        public String getRequestCharSet() {  
	            //return super.getRequestCharSet();  
	            return "utf-8";  
	        }  
	    }    
}
