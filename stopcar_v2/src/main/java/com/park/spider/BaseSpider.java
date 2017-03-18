package com.park.spider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;

/**
 * 处理抓取的BASE
 * @author jingxiaohu
 *
 */
public class BaseSpider {
	public static   Logger log =  Logger.getLogger(BaseSpider.class);
	private static String ERROR_RESP = "HTTP_GET_ERROR";
	private  static HttpClient customerHttpClient;
    
    public static HttpClient getHttpClient() {
        if(null== customerHttpClient) {
            customerHttpClient =new HttpClient();
        }
        return customerHttpClient;
    }
	
	
	public static  String doGet(String url,Map<String, String> header,NameValuePair[] params){
		HttpClient  hc = getHttpClient();
		GetMethod get =  null;
		try{
			/*hc.setConnectionTimeout(20*1000);
			hc.setTimeout(20*1000);*/
			get =  new GetMethod(url);
			if(params!= null){
				get.setQueryString(params); 
			}
			get.setRequestHeader("Connection", "close");  
//			get.addRequestHeader("Content-Type", "application/json;charset=utf-8");
			get.addRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			get.addRequestHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
			if(header!=null){
				for(String h:header.keySet()){
					get.addRequestHeader(h, header.get(h));
				}
			}
			hc.executeMethod(get);
			if(get.getStatusCode() == 200){
				InputStream resStream = get.getResponseBodyAsStream();  
		        BufferedReader br = new BufferedReader(new InputStreamReader(resStream));  
		        StringBuffer resBuffer = new StringBuffer();  
		        String resTemp = "";  
		        while((resTemp = br.readLine()) != null){  
		            resBuffer.append(resTemp);  
		        }  
		        if(br != null){
		        	br.close();
		        }
		        return resBuffer.toString();  
			}else{
				log.error(url+" req error StatusCode:"+get.getStatusCode());
			}
		}catch(Exception e){
			log.error("doGet error", e);
			return ERROR_RESP;
		}finally{
			if(get!=null){
				get.releaseConnection();
				//释放链接
				hc.getHttpConnectionManager().closeIdleConnections(0);
			}
		}
		return ERROR_RESP;
	}

	
	public static  String dopost(String url,Map<String, String> header,NameValuePair[] params){
		HttpClient  hc = getHttpClient();
		PostMethod post =  null; 
		try{
			/*hc.setConnectionTimeout(20*1000);
			hc.setTimeout(20*1000);*/
			post =  new PostMethod(url);
			if(params!= null){
				post.setRequestBody(params);
			}
			//post.setRequestHeader("Connection", "close");  
			//post.addRequestHeader("Content-Type", "application/json;charset=utf-8");
			//post.addRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			//post.addRequestHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
			post.addRequestHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			post.addRequestHeader("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3");
			post.addRequestHeader("Accept-Encoding", "GBK,utf-8;q=0.7,*;q=0.3");
			post.addRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
			post.addRequestHeader("Cache-Control", "max-age=0");
			post.addRequestHeader("Connection", "keep-alive");
			if(header!=null){
				for(String h:header.keySet()){
					post.addRequestHeader(h, header.get(h));
				}
			}
			hc.executeMethod(post);
			if(post.getStatusCode() == 200){
				InputStream resStream = post.getResponseBodyAsStream();  
		        BufferedReader br = new BufferedReader(new InputStreamReader(resStream));  
		        StringBuffer resBuffer = new StringBuffer();  
		        String resTemp = "";  
		        while((resTemp = br.readLine()) != null){  
		            resBuffer.append(resTemp);  
		        }  
		        if(br != null){
		        	br.close();
		        }
		        return resBuffer.toString();  
			}else{
				log.error(url+" req error StatusCode:"+post.getStatusCode());
			}
		}catch(Exception e){
			log.error("doGet error", e);
			return ERROR_RESP;
		}finally{
			if(post!=null){
				post.releaseConnection();
				//释放链接
				hc.getHttpConnectionManager().closeIdleConnections(0);
			}
		}
		return ERROR_RESP;
	}
	
	public static void main(String[] args) throws Exception {
		String url = "http://diantai.ifeng.com/fmapi/fmApi.php?action=getCategoryList";
		String IXFMKEY =  "ifengdiantai";
		String devicename  = "abc112233";
		String rtime = System.currentTimeMillis()+"";
		String source = "1";
		String sign = DigestUtils.md5Hex("devicename="+devicename
				+ "rtime="+rtime+"source="+source + IXFMKEY);
		NameValuePair[] params = new NameValuePair[]{
			 new 	NameValuePair("devicename",devicename),
			 new    NameValuePair("type","1"),
			 new    NameValuePair("rtime",rtime),
			 new    NameValuePair("source",source),
			 new    NameValuePair("sign",sign)
		};
		
		String result = new BaseSpider().dopost(url, null, params);
		System.out.println(StringEscapeUtils.unescapeJava(result)); 
	}
	
	
	public static String MD5(String str){
		return DigestUtils.md5Hex(str);
	}
	
	
	
	 public static String convertStreamToString(InputStream is) {      
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is));      
	        StringBuilder sb = new StringBuilder();      
	       
	        String line = null;      
	        try {      
	            while ((line = reader.readLine()) != null) {  
	                sb.append(line + "\n");      
	            }      
	        } catch (IOException e) {      
	            e.printStackTrace();      
	        } finally {      
	            try {      
	                is.close();      
	            } catch (IOException e) {      
	               e.printStackTrace();      
	            }      
	        }      
	        return sb.toString();      
	    }  
	
	
	public static String convert(String utfString){  
	    StringBuilder sb = new StringBuilder();  
	    int i = -1;  
	    int pos = 0;  
	      
	    while((i=utfString.indexOf("\\u", pos)) != -1){  
	        sb.append(utfString.substring(pos, i));  
	        if(i+5 < utfString.length()){  
	            pos = i+6;  
	            sb.append((char)Integer.parseInt(utfString.substring(i+2, i+6), 16));  
	        }  
	    }  
	      
	    return sb.toString();  
	}  
	
	public static String decodeUnicode(String theString) {      
		   
	    char aChar;      
	   
	     int len = theString.length();      
	   
	    StringBuffer outBuffer = new StringBuffer(len);      
	   
	    for (int x = 0; x < len;) {      
	   
	     aChar = theString.charAt(x++);      
	   
	     if (aChar == '\\') {      
	   
	      aChar = theString.charAt(x++);      
	   
	      if (aChar == 'u') {      
	   
	       // Read the xxxx      
	   
	       int value = 0;      
	   
	       for (int i = 0; i < 4; i++) {      
	   
	        aChar = theString.charAt(x++);      
	   
	        switch (aChar) {      
	   
	        case '0':      
	   
	        case '1':      
	   
	        case '2':      
	   
	        case '3':      
	   
	       case '4':      
	   
	        case '5':      
	   
	         case '6':      
	          case '7':      
	          case '8':      
	          case '9':      
	           value = (value << 4) + aChar - '0';      
	           break;      
	          case 'a':      
	          case 'b':      
	          case 'c':      
	          case 'd':      
	          case 'e':      
	          case 'f':      
	           value = (value << 4) + 10 + aChar - 'a';      
	          break;      
	          case 'A':      
	          case 'B':      
	          case 'C':      
	          case 'D':      
	          case 'E':      
	          case 'F':      
	           value = (value << 4) + 10 + aChar - 'A';      
	           break;      
	          default:      
	           throw new IllegalArgumentException(      
	             "Malformed   \\uxxxx   encoding.");      
	          }      
	   
	        }      
	         outBuffer.append((char) value);      
	        } else {      
	         if (aChar == 't')      
	          aChar = '\t';      
	         else if (aChar == 'r')      
	          aChar = '\r';      
	   
	         else if (aChar == 'n')      
	   
	          aChar = '\n';      
	   
	         else if (aChar == 'f')      
	   
	          aChar = '\f';      
	   
	         outBuffer.append(aChar);      
	   
	        }      
	   
	       } else     
	   
	       outBuffer.append(aChar);      
	   
	      }      
	   
	      return outBuffer.toString();      
	   
	     }     
}
