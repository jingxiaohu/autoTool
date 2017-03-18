package qz.test.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * 
* @ClassName: TestSpring
* @Description: TODO(这里用一句话描述这个类的作用)
* @author 敬小虎
* @date 2015年3月11日 下午4:38:00
*
 */
public class TestSpring {
	public static void main(String[] args) throws Exception { 
		    //读取spring配置文件
		   //ApplicationContext context = new ClassPathXmlApplicationContext("beanConfig.xml");   

		  //E:\\jxh\\workspace\\radio_admin\\web\\WEB-INF\\config\\spring-test.xml
		  //D:\\android\\AndroidProject\\intimes_app\\webapp\\WEB-INF\\config\\spring\\spring-test.xml
		  ApplicationContext ctx = new FileSystemXmlApplicationContext("file:D:\\Users\\jingxiaohu\\workspace\\smartspider\\webapp\\WEB-INF\\config\\spring\\spring-test.xml");   
		 // IntimesUserUUIDMake intimesUserUUIDMake = (IntimesUserUUIDMake)ctx.getBean("intimesUserUUIDMake"); 
		 // intimesUserUUIDMake.doIntimes_no();
		  //Update360CPS  update360CPS = (Update360CPS)ctx.getBean("update360CPS");
		  //update360CPS.doAnalysis(2014, 0); 
//		  QingTingBiz testBiz =  (QingTingBiz)ctx.getBean("qingTingBiz");
//		  testBiz.doCategoryBianLi();
//		  PhenixRadioBiz phenixRadioBiz = (PhenixRadioBiz)ctx.getBean("phenixRadioBiz");
//		  phenixRadioBiz.doCategoryBianLi();
	}

}
