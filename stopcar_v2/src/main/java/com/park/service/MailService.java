package com.park.service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.view.velocity.VelocityConfig;

import com.park.core.Constants;


@Service
public class MailService{
	
	@Autowired
	private JavaMailSenderImpl  mailSender;   
	@Autowired
    private VelocityConfig velocityConfig;
	@Value("${mail.username}")
	private String mailUsername;
	
	private Logger log = Logger.getLogger(Logger.class);
	/**
	 * 发送邮件服务
	 * 
	 * @param model		模板内容的替换值
	 * @param subject	主题
	 * @param vmfile	模板文件名称
	 * @param mailTo	接收人
	 * @param files		附件
	 */
	public boolean sendEmail(final Map<String, Object> model,final String subject, final String vmfile, final String[] mailTo,final String[] files){
		try{
			MimeMessagePreparator preparator = new MimeMessagePreparator() {   
		           //注意MimeMessagePreparator接口只有这一个回调函数   
		         public void prepare(MimeMessage mimeMessage) throws Exception{   
		            MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true,Constants.SYSTEM_CHARACTER);   
		            //这是一个生成Mime邮件简单工具，如果不使用GBK这个，中文会出现乱码   
		            //如果您使用的都是英文，那么可以使用MimeMessageHelper message = new MimeMessageHelper(mimeMessage);   
		            message.setTo(mailTo);//设置接收方的email地址   
		            message.setSubject(subject);//设置邮件主题   
		            message.setFrom(mailUsername);//设置发送方地址   
		            
		            String text = VelocityEngineUtils.mergeTemplateIntoString(velocityConfig.getVelocityEngine(), vmfile,Constants.SYSTEM_CHARACTER, model);   
		            //从模板中加载要发送的内容，vmfile就是模板文件的名字   
		            //注意模板中有中文要加GBK，model中存放的是要替换模板中字段的值   
		            message.setText(text, true);   
		            //将发送的内容赋值给MimeMessageHelper,后面的true表示内容解析成html   
		            //如果您不想解析文本内容，可以使用false或者不添加这项   
		            //添加附件  
		            if(files!=null){
			            FileSystemResource file;
			            for(String s:files){   
			               file = new FileSystemResource(new File(s));//读取附件   
			               message.addAttachment(s, file);//向email中添加附件   
			            }   
		            }
		          }   
		      };   
		      Properties prop  =   new  Properties() ;
		      prop.put( "mail.smtp.auth" ,  "true" ) ;  //  将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
		      prop.put( "mail.smtp.timeout" ,  "25000" ) ; 
		      prop.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		      prop.setProperty("mail.smtp.port", "465");
		      prop.setProperty("mail.smtp.socketFactory.port", "465");
		      mailSender.setJavaMailProperties(prop);  
		      mailSender.send(preparator);//发送邮件   
		      return true;
			}catch (Exception e) {
				log.error("sendEmail is error", e); 
				return false;
			}
	}
	
	/**
	 * 发送注册邮件系统
	* @Title: sendMail
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param @param email
	* @param @return    设定文件
	* @return boolean    返回类型
	* @throws
	 */
	public  boolean sendRegisterMail(String email,String authcode) {
		// 得到服务层
		try {

			Map<String, Object>  map = new HashMap<String, Object>();
			map.put("authcode", authcode);
			String mailTo[] = { email };
			// 发送邮件
			return sendEmail(map, "intimes平台","reg.vm", mailTo, null);
		} catch (Exception e) {
			log.error("发送注册邮件系统错误!", e);
			return false;
		}

	}
	
	
	/**
	 * 发送找回密码邮件系统
	* @Title: sendMail
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param @param email
	* @param @return    设定文件
	* @return boolean    返回类型
	* @throws
	 */
	public boolean sendPassWordMail(String email,String authcode) {
		// 得到服务层
		try {

			Map<String, Object>  map = new HashMap<String, Object>();
			map.put("authcode", authcode);
			String mailTo[] = { email };
			// 发送邮件
			return sendEmail(map, "intimes平台","find_password.vm", mailTo, null);
		} catch (Exception e) {
			log.error("发送找回密码邮件系统错误!", e);
			return false;
		}

	}

}
