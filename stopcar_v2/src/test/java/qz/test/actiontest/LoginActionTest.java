package qz.test.actiontest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import com.park.core.Constants;
import com.park.util.RequestUtil;

/**
 * 2.2	用户登录（java）- P by jxh
 * @author 敬小虎
 *
 */
public class LoginActionTest {
	public static void doPost(String url) throws Exception{
		HttpClient HC=new HttpClient();
	    PostMethod post  = new PostMethod(url);
		post.addParameter("loginname", "251878350@qq.com"); //账号
		post.addParameter("password", "111111");  //密码
		String m =  RequestUtil.MD5(Constants.SYSTEM_KEY);
		post.addParameter("m", m);  
		HC.executeMethod(post);
		try {
			String ds = post.getResponseBodyAsString();
			if(ds == null){
				System.out.println("无数据返回");
			}else{
				System.out.println(new String(post.getResponseBodyAsString().getBytes("iso-8859-1"), "utf-8"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws Exception { 
		String url = ActionTestUtil.BaseUrl+"login.do";
		doPost(url);
	}
}
