package ihiyo.test.actiontest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;

import redis.clients.jedis.Jedis;

import com.park.core.Constants;

/**
 * ihiyo 机器人模拟注册登录
* @ClassName: IhiyoActionTest
* @Description: TODO(这里用一句话描述这个类的作用)
* @author 敬小虎
* @date 2015年4月28日 上午10:29:32
*
 */
public class IhiyoActionTest extends BaseActionTest{
	@Test
	public void tt() throws Exception{ 
		for (int i = 0; i < 1000; i++) {
			checkLoginname("15882345446");	
		}
		
	}
	//1．用户注册-检查帐号是否有效
	public void checkLoginname(String username) throws Exception{
		String url = BaseUrl+"/register/isValidUsername";
		PostMethod post  = new PostMethod(url);
		post.addParameter("username", username);
		HC.executeMethod(post);
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
	
	
	//2．用户注册-获取验证码 
	//3．用户注册-校验验证码 
	//4．用户注册-检查昵称是否有效 
	//5．用户注册-提交注册信息 
	//6．登录验证 
	//7．注销登录 
	//8．查看他人资料√
	//9．查看自己资料
	//10．修改个人资料
	
	
	
	
	
	
	
	
	public  void doPost(String url) throws Exception{
		
	    PostMethod post  = new PostMethod(url);
	    String loginname = "15882345446";
	    String password = "111111";
	    String auth_code = "310173";
	    String sex  = "1";//性别 0：未指定 1：男 2：女
	    String flag = "0";//注册来源0 :Android (默认)1: Iphone2: web3:PC
	    String appname = "test_app";
	    String reg_type = "1";//1: 手机号 2邮箱
	    String apk_str = "";//渠道id_游戏id
	    String imei = "";
	    
		post.addParameter("loginname", loginname); //账号
		post.addParameter("password", password);  //密码
		post.addParameter("auth_code",auth_code);//短信验证码
		post.addParameter("sex", sex);//性别 0：未指定 1：男 2：女
		post.addParameter("flag", flag);//注册来源0 :Android (默认)1: Iphone2: web3:PC
		post.addParameter("appname", appname);
		post.addParameter("reg_type", reg_type); //1: 手机号 2邮箱
		post.addParameter("apk_str", apk_str);//渠道id_游戏id
		post.addParameter("imei", imei);
		post.addParameter("m", DigestUtils.md5Hex(loginname+ password+ auth_code  + imei +reg_type +Constants.SYSTEM_KEY));//MD5散列(loginname+ password+ auth_code  + imei +reg_type +key）
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
	@Test
	public  void main() throws Exception { 
		String url = IhiyoActionTest.BaseUrl+"reg.do";
		doPost(url);
	}
	@Test
	public void test1Normal() {
	    Jedis jedis = new Jedis("27.152.28.200",6379);
	    //权限认证
	    //jedis.auth("admin"); 
	    long start = System.currentTimeMillis();
	    long end = System.currentTimeMillis();
	    System.out.println("Simple SET: " + ((end - start)/1000.0) + " seconds");
	    long xx = jedis.del("appsdk_info_1");
	    System.out.println(xx);
	    jedis.disconnect();
	}
	@Test
	public void dd(){
		String sql = "asdasd";
		System.out.println(sql.split(",")[0].toString());
		
	}
}
