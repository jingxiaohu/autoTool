package ihiyo.test.actiontest;

import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;

/**
 *   用户注册 
 * @author jingxiaohu
 *
 */
public class WriteUserRegisterActionTest extends BaseActionTest{
	private String apiid="GAPchBsE";
	private String apikey="8rUDzPcvTMr46TmD";
	//从什么设备发出的请求1-收音机2-手机APP
	private String dtype = "2";
	private String userid = "4";
	private String devicemac = "98:2f:3c:ab:c8:a1";
	@Test
	public void vod() throws Exception{
		String url = BaseUrl+"user.php";
		PostMethod post  = new UTF8PostMethod(url);
		post.addParameter("apiid", apiid);
		post.addParameter("apikey", apikey);
		post.addParameter("dtype", dtype);
		post.addParameter("userid", userid);
		post.addParameter("devicemac", devicemac);
		//抓取的来源
		String Apitype = "user_register";//change_pass/user_register
		post.addParameter("Apitype", Apitype);
		post.addParameter("username", "15882345448");
		post.addParameter("verify_code", "894775");
		post.addParameter("verify_list", "2a2WSqW7");
		post.addParameter("password", "123456");
		post.addParameter("repassword", "123456");
		post.addParameter("vclass", "user_register");
		System.out.println(System.currentTimeMillis());
		
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
