package qz.test.actiontest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import com.park.core.Constants;
import com.park.util.RequestUtil;

/**
 * 2.5	获取用户任务记录- P  by jxh
 * @author 敬小虎
 *
 */
public class ReadGainUserTaskActionTest {
	
	public static void doPost(String url,long ui_id) throws Exception{
		HttpClient HC=new HttpClient();
	    PostMethod post  = new PostMethod(url);
	    post.addParameter("ui_id", String.valueOf(ui_id)); //用户ID
		String m =  RequestUtil.MD5(String.valueOf(ui_id)+Constants.SYSTEM_KEY);
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
		String url = ActionTestUtil.BaseUrl+"gainusertask.do";
		long ui_id=5;
		doPost(url,ui_id);
	}
}


