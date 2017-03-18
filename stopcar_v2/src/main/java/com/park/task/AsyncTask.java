//package com.park.task;
//
//import java.util.List;
//
//import org.apache.log4j.Logger;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//
//import com.smart.bean.By_app_categories;
//import com.smart.v1.biz.PhenixRadioBiz;
//import com.smart.v1.biz.QingTingBiz;
//@Service
//public class AsyncTask {
//	static Logger log = Logger.getLogger(AsyncTask.class);
//	/**
//	 * 进行分组执行 --- qingting
//	 * @param list
//	 * @param pagesize
//	 */
//	@Async
//	public   void doInsertIntoDataBase(QingTingBiz qingTingBiz,List<By_app_categories> list,int pagesize,String listname){
//		try {
//			System.out.println("@@"+listname);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			log.error("点播执行doInsertIntoDataBase(List<By_app_categories> list,int pagesize) is error", e); 
//		}
//		
//	}
//	@Async
//	public   void doInsertIntoDataBase_qingting(QingTingBiz qingTingBiz){
//		try {
//			log.info("-------@@doInsertIntoDataBase_qingting@@-----");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			log.error("点播执行doInsertIntoDataBase_qingting(List<By_app_categories> list,int pagesize) is error", e); 
//		}
//		
//	}
//	
//	/**
//	 * 进行分组执行 ---- ifeng
//	 * @param list
//	 * @param pagesize
//	 */
//	@Async
//	public   void doInsertIntoDataBase_ifeng(PhenixRadioBiz phenixRadioBiz){
//		try {
//			log.info("-------@@doInsertIntoDataBase_ifeng@@-----");
//			try {
//	            //让程序暂停100秒，相当于执行一个很耗时的任务
//	            Thread.sleep(1000);
//	        } catch (Exception e) {
//	            // TODO Auto-generated catch block
//	            e.printStackTrace();
//	        }
//			/*if(list != null && list.size() >0){
//				for (By_app_categories by_app_categories : list) {
//					qingTingBiz.getProgram(by_app_categories.getId(),by_app_categories.getC_id()+"",pagesize,listname);
//				}
//			}*/
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			log.error("点播执行doInsertIntoDataBase_ifeng(List<By_app_categories> list,int pagesize) is error", e); 
//		}
//		
//	}
//}
