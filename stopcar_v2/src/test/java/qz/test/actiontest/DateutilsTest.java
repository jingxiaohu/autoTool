/**  
* @Title: DateutilsTest.java
* @Package qz.test.actiontest
* @Description: TODO(用一句话描述该文件做什么)
* @author 敬小虎  
* @date 2015年3月13日 下午1:31:27
* @version V1.0  
*/ 
package qz.test.actiontest;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

/**
 * @ClassName: DateutilsTest
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 敬小虎
 * @date 2015年3月13日 下午1:31:27
 *
 */
public class DateutilsTest {
	   /**
     * 在当前日期的基础上加上两天
     */
    @Test
    public void test122() {
        Date date = new Date();
        System.out.println(DateUtils.addDays(date, 2).toString());
        System.out.println(DateUtils.addHours(date, 2).toString());
    }
 
    /**
     * 保留整数分钟
     */
    @Test
    public void test21() {
        Date date = new Date();
        System.out.println(DateUtils.truncate(date, Calendar.MINUTE));
    }
 
    /**
     * 把时间字符串格式化为日期
     * 
     * @throws ParseException
     */
    @Test
    public void test21sd12() throws ParseException {
        System.out.println(DateUtils.parseDate("2014-10-12 11:11:12",
                "yyyy-MM-dd HH:mm:ss"));
    }
 
    /**
     * 设置年份
     * 
     * @throws ParseException
     */
    @Test
    public void test21sd1212() {
        Date date = new Date();
        System.out.println(DateUtils.setYears(date, 2019));
        System.out.println(DateUtils.setMonths(date, 11));
        System.out.println(DateUtils.setDays(date, 11));
    }
 
    /**
     * 
     * @throws ParseException
     */
    @Test
    public void test12122111() {
        Date date = new Date();
        // Thu Oct 16 11:00:00 CST 2014
        // Thu Oct 16 12:00:00 CST 2014
        // Thu Oct 16 11:00:00 CST 2014
        System.out.println(DateUtils.round(date, Calendar.HOUR));
        System.out.println(DateUtils.ceiling(date, Calendar.HOUR));
        System.out.println(DateUtils.truncate(date, Calendar.HOUR));
    }
}
