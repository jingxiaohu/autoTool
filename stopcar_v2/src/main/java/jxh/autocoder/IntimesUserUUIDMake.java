///**  
//* @Title: IntimesUserUUIDMake.java
//* @Package jxh.autocoder
//* @Description: TODO(用一句话描述该文件做什么)
//* @author 敬小虎  
//* @date 2015年3月11日 下午4:06:01
//* @version V1.0  
//*/ 
//package jxh.autocoder;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import com.park.bean.Intimes_no;
//import com.park.dao.Intimes_noDao;
//
///**
// * 生成intimes平台用户对外号码
// * @ClassName: IntimesUserUUIDMake
// * @Description: TODO(这里用一句话描述这个类的作用)
// * @author 敬小虎
// * @date 2015年3月11日 下午4:06:01
// *
// */
//@Repository(value="intimesUserUUIDMake")
//public class IntimesUserUUIDMake {
//	@Autowired
//	private Intimes_noDao intimes_noDao;
//	public  void doIntimes_no() throws Exception{
//		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date date = new Date();
//		int startno = 1000000;
//		int count = 0;//批次
//		int spaceNo = 1000000;//间隔
//		List<Intimes_no> list = new ArrayList<Intimes_no>();
//		for (int i = startno; i <= 10000000; i++) {
//			Intimes_no intimes_no = new Intimes_no();
//			intimes_no.setIn_myid(String.valueOf(i));
//			intimes_no.setIn_state(0);
//			intimes_no.setIn_time(date.getTime());
//			intimes_no.setIn_time_str(sf.format(date));
//			list.add(intimes_no);
//			
//			//每10次执行一次数据库
//			if(i>startno && i% spaceNo == 0){
//				int xx[] = intimes_noDao.insert(list);
//				if(xx != null && xx.length == spaceNo){
//					count++;
//					System.out.println("执行第"+count+"批"+spaceNo+"条数据已经入库......"); 
//				}
//				list.removeAll(list);
//			}
//		}
//
//	}
//	
//	
//
//}
