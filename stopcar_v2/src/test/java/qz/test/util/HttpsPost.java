package qz.test.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyStore;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import sun.misc.BASE64Decoder;

import com.park.util.RequestUtil;
  
public class HttpsPost {  
    /** 
     * 获得KeyStore. 
     * @param keyStorePath 
     *            密钥库路径 
     * @param password 
     *            密码 
     * @return 密钥库 
     * @throws Exception 
     */  
    public static KeyStore getKeyStore(String password, String keyStorePath)  
            throws Exception {  
        // 实例化密钥库  
        KeyStore ks = KeyStore.getInstance("JKS");  
        // 获得密钥库文件流  
        FileInputStream is = new FileInputStream(keyStorePath);  
        // 加载密钥库  
        ks.load(is, password.toCharArray());  
        // 关闭密钥库文件流  
        is.close();  
        return ks;  
    }  
  
    /** 
     * 获得SSLSocketFactory. 
     * @param password 
     *            密码 
     * @param keyStorePath 
     *            密钥库路径 
     * @param trustStorePath 
     *            信任库路径 
     * @return SSLSocketFactory 
     * @throws Exception 
     */  
    public static SSLContext getSSLContext(String password,  
            String keyStorePath, String trustStorePath) throws Exception {  
        // 实例化密钥库  
        KeyManagerFactory keyManagerFactory = KeyManagerFactory  
                .getInstance(KeyManagerFactory.getDefaultAlgorithm());  
        // 获得密钥库  
        KeyStore keyStore = getKeyStore(password, keyStorePath);  
        // 初始化密钥工厂  
        keyManagerFactory.init(keyStore, password.toCharArray());  
  
        // 实例化信任库  
        TrustManagerFactory trustManagerFactory = TrustManagerFactory  
                .getInstance(TrustManagerFactory.getDefaultAlgorithm());  
        // 获得信任库  
        KeyStore trustStore = getKeyStore(password, trustStorePath);  
        // 初始化信任库  
        trustManagerFactory.init(trustStore);  
        // 实例化SSL上下文  
        SSLContext ctx = SSLContext.getInstance("TLS");  
        // 初始化SSL上下文  
        ctx.init(keyManagerFactory.getKeyManagers(),  
                trustManagerFactory.getTrustManagers(), null);  
        // 获得SSLSocketFactory  
        return ctx;  
    }  
  
    /** 
     * 初始化HttpsURLConnection. 
     * @param password 
     *            密码 
     * @param keyStorePath 
     *            密钥库路径 
     * @param trustStorePath 
     *            信任库路径 
     * @throws Exception 
     */  
    public static void initHttpsURLConnection(String password,  
            String keyStorePath, String trustStorePath) throws Exception {  
        // 声明SSL上下文  
        SSLContext sslContext = null;  
        // 实例化主机名验证接口  
//        HostnameVerifier hnv = new MyHostnameVerifier();  
        HostnameVerifier hnv = null;
        try {  
            sslContext = getSSLContext(password, keyStorePath, trustStorePath);  
        } catch (GeneralSecurityException e) {  
            e.printStackTrace();  
        }  
        if (sslContext != null) {  
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext  
                    .getSocketFactory());  
        }  
        HttpsURLConnection.setDefaultHostnameVerifier(hnv);  
    }  
  
    /** 
     * 发送请求. 
     * @param httpsUrl 
     *            请求的地址 
     * @param xmlStr 
     *            请求的数据 
     */  
    public static void post(String httpsUrl, String xmlStr) {  
        HttpsURLConnection urlCon = null;  
        try {  
            urlCon = (HttpsURLConnection) (new URL(httpsUrl)).openConnection();  
            urlCon.setDoInput(true);  
            urlCon.setDoOutput(true);  
            urlCon.setRequestMethod("POST");  
            urlCon.setRequestProperty("Content-Length",  
                    String.valueOf(xmlStr.getBytes().length));  
            urlCon.setUseCaches(false);  
            //设置为gbk可以解决服务器接收时读取的数据中文乱码问题  
            urlCon.getOutputStream().write(xmlStr.getBytes("gbk"));  
            urlCon.getOutputStream().flush();  
            urlCon.getOutputStream().close();  
            BufferedReader in = new BufferedReader(new InputStreamReader(  
                    urlCon.getInputStream()));  
            System.out.println(urlCon.getResponseCode()); 
            String line;  
            while ((line = in.readLine()) != null) {  
                System.out.println("@"+line);  
            }  
        } catch (MalformedURLException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    
    
    
    public static String getBASE64(byte[] b) {
    	  String s = null;
    	  if (b != null) {
    	   s = new sun.misc.BASE64Encoder().encode(b);
    	  }
    	  return s;
    	 }
    	 
    	 public static byte[] getFromBASE64(String s) {
    	  byte[] b = null;
    	  if (s != null) {
    	   BASE64Decoder decoder = new BASE64Decoder();
    	   try {
    	    b = decoder.decodeBuffer(s);
    	    return b;
    	   } catch (Exception e) {
    	    e.printStackTrace();
    	   }
    	  }
    	  return b;
    	 }
    /** 
     * 测试方法. 
     * @param args 
     * @throws Exception 
     */  
    public static void main(String[] args) throws Exception {  
/*        // 密码  
        String password = "123456";  
        // 密钥库  
        String keyStorePath = "tomcat.keystore";  
        // 信任库  
        String trustStorePath = "tomcat.keystore";  
        // 本地起的https服务  
        String httpsUrl = "https://localhost:8443/service/httpsPost";  
        // 传输文本  
        String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><fruitShop><fruits><fruit><kind>萝卜</kind></fruit><fruit><kind>菠萝</kind></fruit></fruits></fruitShop>";  
        HttpsPost.initHttpsURLConnection(password, keyStorePath, trustStorePath);  */
    	
    	/*String ss = "{\"receipt\":{\"original_purchase_date_pst\":\"2013-05-07 19:32:33 America/Los_Angeles\", \"purchase_date_ms\":\"1367980353734\", \"unique_identifier\":\"dfd89af0960fb01e5685f69f863d92a29a884cb0\", \"original_transaction_id\":\"1000000073264300\", \"bvrs\":\"3.0\", \"transaction_id\":\"1000000073264300\", \"quantity\":\"1\", \"unique_vendor_identifier\":\"5EC25391-7E45-412E-9E65-9B72E58846F0\", \"item_id\":\"645790751\", \"product_id\":\"201305070\", \"purchase_date\":\"2013-05-08 02:32:33 Etc/GMT\", \"original_purchase_date\":\"2013-05-08 02:32:33 Etc/GMT\", \"purchase_date_pst\":\"2013-05-07 19:32:33 America/Los_Angeles\", \"bid\":\"com.rumtel.WRADIO\", \"original_purchase_date_ms\":\"1367980353734\"}, \"status\":0}";
    	ss = getBASE64(ss.getBytes());
        // 发起请求  
    	JSONObject obj = new JSONObject();
    	obj.put("receipt-data", ss);
    	
        HttpsPost.post("https://sandbox.itunes.apple.com/verifyReceipt", obj.toString());  */
    	
    	String cvvds = "443969!*#@()@JKFIAUFOIASFJ*@E$Q213412NHIJ)6";
    	
    	System.out.println(RequestUtil.MD5(cvvds));
    	
    	
    	
    }  
}  

