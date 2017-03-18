package ihiyo.test.actiontest;

import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;

/**
 *   更新用户音效
 * @author jingxiaohu
 *
 */
public class WriteUserEffectActionTest extends BaseActionTest{
	private String apiid="GAPchBsE";
	private String apikey="8rUDzPcvTMr46TmD";
	//从什么设备发出的请求1-收音机2-手机APP
	private String dtype = "2";
	private String userid = "152";
	private String devicemac = "d8:47:10:46:02:5e";
	@Test
	public void update_effect() throws Exception{
		String url = BaseUrl+"update_effect.php";
		PostMethod post  = new UTF8PostMethod(url);
		post.addParameter("apiid", apiid);
		post.addParameter("apikey", apikey);
		post.addParameter("dtype", dtype);
		post.addParameter("userid", userid);
		post.addParameter("devicemac", devicemac);
		//抓取的来源
		post.addParameter("effect", "3");
		post.addParameter("type", "2");//0：WiFi 1:fm 2:BT蓝牙
		post.addParameter("own_pid","5656");
		
		
		int xx = HC.executeMethod(post);
		System.out.println("*请求状态code="+xx); 
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
	
	
	@Test
	public void read_effect() throws Exception{
		String url = BaseUrl+"read_effect.php";
		PostMethod post  = new UTF8PostMethod(url);
		post.addParameter("apiid", apiid);
		post.addParameter("apikey", apikey);
		post.addParameter("dtype", dtype);
		post.addParameter("userid", userid);
		post.addParameter("devicemac", devicemac);
		
		
		int xx = HC.executeMethod(post);
		System.out.println("*请求状态code="+xx); 
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
	 //Inner class for UTF-8 support   
    public static class  UTF8PostMethod  extends  PostMethod {   
        public  UTF8PostMethod ( String url ){   
            super ( url ) ;   
        }   
        @Override   
        public  String getRequestCharSet () {   
            //return super.getRequestCharSet();   
            return  "UTF-8" ;   
        }   
    }   
}
