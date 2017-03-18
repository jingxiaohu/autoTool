package ihiyo.test.actiontest;

import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;

/**
 *   设备获取基本信息测试
 * @author jingxiaohu
 *
 */
public class TestActionTest extends BaseActionTest{
	@Test
	public void TestAction() throws Exception{
		String url = BaseUrl+"test.php";
		PostMethod post  = new PostMethod(url);
		int xx = HC.executeMethod(post);
		System.out.println(xx);
		
		try {
			String ds = post.getResponseBodyAsString();
			if(ds == null){
				System.out.println("无数据返回");
			}else{
				String nn = new String(post.getResponseBodyAsString().getBytes("utf-8"), "utf-8");
				System.out.println(nn);
				//System.out.println(JSONObject.fromObject(nn).getJSONObject("data").getJSONObject("startup_picture").toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("TestAction is error"+e.getMessage(), e); 
		}finally{
			if(post!=null){
				post.releaseConnection();
				//释放链接
				HC.getHttpConnectionManager().closeIdleConnections(0);
			}
		}
	}
}
