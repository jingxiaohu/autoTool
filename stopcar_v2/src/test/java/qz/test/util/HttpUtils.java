package qz.test.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HttpUtils {
	public static  Log logger = LogFactory.getLog(HttpUtils.class);

	/**
	 * ִ��httpmethod ����
	 * @param method
	 */
	public static void executeHttpClient(org.apache.commons.httpclient.HttpMethod method){
		HttpClient httpclient = new HttpClient(new HttpClientParams(),new SimpleHttpConnectionManager(true));
		try {
			httpclient.executeMethod(method);
		} catch (Exception e) {
			logger.equals(e);
		}
	}
	
	/**
	 * ������֤
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		String check = "^([a-z0-9A-Z]+_*?)+[a-z0-9A-Z]@([a-z0-9A-Z]+?\\.)+[a-zA-Z]{2,}$";
		if (!email.matches(check)) {
			return false;
		}
		return true;
	}
	
	/**
	 * �ؼ��ֹ���
	 * @param content
	 * @return
	 */
	public static boolean checkSpecification(String content){
		String check = "[^������]+[^�����]";
		if (!content.matches(check)) {
			return false;
		}
		return true;
	}
	
	/**
	 * �����ַ����
	 * @param str
	 * @return
	 */
	public static boolean checkSpecialCharacter(String str){
		String regex = "[^%!@#/$:?;{}(~`)_|=+&]{1,}";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		if(m.matches()){
			return true;
		}
		return false;
	}
	
	/**
	   * 
	   * @param ins
	   * @param charset
	   * @return
	   * @throws IOException
	   * ins û�йرգ����ƺ�
	   */
	  public static String getStream2String(InputStream ins, String charset) throws IOException {
	    String line = null;

	    StringBuilder sb = new StringBuilder();
	    BufferedReader isr = new BufferedReader(new InputStreamReader(ins, charset));

	    while ((line = isr.readLine()) != null) {
	      sb.append(line);
	    }
	    line = sb.toString();

	    return line;
	  }


	
}
