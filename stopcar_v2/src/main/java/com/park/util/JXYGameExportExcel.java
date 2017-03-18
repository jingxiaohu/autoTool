package com.park.util;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * 操作Excel表格的功能类
 */
public class JXYGameExportExcel {
    private HSSFWorkbook wb = null;
    private HSSFSheet sheet = null;
    public HSSFRow hdRow = null;
    public HSSFDataFormat format = null;
    
    public static String[] cell_name = new String[]{"用户ID","成功兑换1Q币"};
    
    
    /**
     * 导出数据
     * @throws Exception 
     */
    public  void exportExce(List<Map<String,Object>> rowvalues,HttpServletResponse response,String xmlname) throws Exception{ 
    	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd-HH:mm");
    	 String fileName = xmlname+sf.format(new Date())+".xls";  
         fileName = new String(fileName.getBytes("GBK"), "iso8859-1");  
         response.reset();  
         response.setHeader("Content-Disposition", "attachment;filename="  + fileName);// 指定下载的文件名  
         response.setContentType("application/vnd.ms-excel;charset=gb2312");  
         response.setHeader("Pragma", "no-cache");  
         response.setHeader("Cache-Control", "no-cache");  
         response.setDateHeader("Expires", 0);  
         BufferedOutputStream bufferedOutPut = null;
		try {
			OutputStream output = response.getOutputStream();  
			 bufferedOutPut = new BufferedOutputStream(output);  
			//创建
			wb = new HSSFWorkbook();
			//设置文件头名称
			createSheet(xmlname);
			//设置数据列
			addHeader(true);
			//给行里添加数据
			addRow( rowvalues);
			//导出数据
			  wb.write(bufferedOutPut);
			  bufferedOutPut.flush();  
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(bufferedOutPut != null){
				bufferedOutPut.close();  
			}
		} 
          
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * 创建sheet名称
     * @param sheetName
     */
    public void createSheet(String sheetName) {
    	sheet = wb.createSheet(sheetName);
    	format = wb.createDataFormat();
    	hdRow = sheet.createRow(0);
    	sheet.setDefaultRowHeightInPoints(120);
    	sheet.setDefaultColumnWidth(12);
    	}
    
    
	/**
	 * 用户提现表头数据
	 * 
	 * @throws Exception
	 */
	public void addCashAdvanceHeader(boolean isFilter) throws Exception {
		// 设置字体
		HSSFFont workFont = wb.createFont();
		workFont.setFontName("微软雅黑");
		workFont.setFontHeightInPoints((short) 14);
		workFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 表头样式及背景色
		HSSFCellStyle hdStyle = wb.createCellStyle();
		hdStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		hdStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		hdStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		hdStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		hdStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		hdStyle.setRightBorderColor(HSSFColor.BLACK.index);
		hdStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		hdStyle.setTopBorderColor(HSSFColor.BLACK.index);
		hdStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		hdStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		hdStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		hdStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		hdStyle.setFont(workFont);

		String[] title = cell_name;
		HSSFRow dtRow = sheet.createRow((1));
		if (isFilter == true) {
			for (int i = 0; i < title.length; i++) {
				HSSFCell cell1 = hdRow.createCell(i);
				HSSFRichTextString value = new HSSFRichTextString(title[i]);
				cell1.setCellValue(value);
				cell1.setCellStyle(hdStyle);
			}
		} else {
			for (int i = 0; i < title.length; i++) {
				HSSFCell cell2 = dtRow.createCell(i);
				HSSFRichTextString value2 = new HSSFRichTextString(title[i]);
				cell2.setCellValue(value2);
			}
		}
	}
    
    
    
	/**
	 * 表头数据
	 * 
	 * @throws Exception
	 */
	public void addHeader(boolean isFilter) throws Exception {
		// 设置字体
		HSSFFont workFont = wb.createFont();
		workFont.setFontName("微软雅黑");
		workFont.setFontHeightInPoints((short) 14);
		workFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
		// 表头样式及背景色
		HSSFCellStyle hdStyle = wb.createCellStyle();
		hdStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		hdStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		hdStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		hdStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		hdStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		hdStyle.setRightBorderColor(HSSFColor.BLACK.index);
		hdStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		hdStyle.setTopBorderColor(HSSFColor.BLACK.index);
		hdStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		hdStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		hdStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		hdStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		hdStyle.setFont(workFont);

		String[] title = cell_name;
		HSSFRow dtRow = sheet.createRow((1));
		if (isFilter == true) {
			for (int i = 0; i < title.length; i++) {
				HSSFCell cell1 = hdRow.createCell(i);
				HSSFRichTextString value = new HSSFRichTextString(title[i]);
				cell1.setCellValue(value);
				cell1.setCellStyle(hdStyle);
			}
		} else {
			for (int i = 0; i < title.length; i++) {
				HSSFCell cell2 = dtRow.createCell(i);
				HSSFRichTextString value2 = new HSSFRichTextString(title[i]);
				cell2.setCellValue(value2);
			}
		}
	}
	/**
	* 数据的导入
	*/
	public void addRow(List<Map<String,Object>> rowvalues) {
		
		for (int i = 0; i < rowvalues.size(); i++) {
			Map<String,Object> userExchange = rowvalues.get(i);
			//创建行
			HSSFRow dtRow = sheet.createRow((i + 2));
			for (int j = 0; j <cell_name.length; j++) {
				Object cell_data = null;
				if(j == 0){
					cell_data = userExchange.get("ui_id").toString();//用户ID
				}else if(j == 1){
					cell_data = userExchange.get("status").toString();//成功兑换1Q币
				}
				//创建行单元
				createCell( cell_data, dtRow,j,i);
			}
		}

	}
	
	
	
    /**
     * 创建单元
     */
    public void createCell(Object cell_data,HSSFRow dtRow,int colum_num,int row_num){
		HSSFCell cell = dtRow.createCell(colum_num);
/*		if (cell_data instanceof String) {
			cell.setCellValue(new HSSFRichTextString((String) cell_data));
		} else if (cell_data instanceof Double) {
			HSSFCellStyle dtStyle = wb.createCellStyle();
			dtStyle.setDataFormat(format.getFormat("yyyy/MM/dd"));
			cell.setCellValue((Double) cell_data);
		} else if (cell_data instanceof Integer) {
			cell.setCellValue(Double.valueOf(String.valueOf(cell_data)));
		} else if (cell_data instanceof Date) {
			cell.setCellValue((Date) cell_data);
		} else if (cell_data instanceof Boolean) {
			cell.setCellValue((Boolean) cell_data);
		} else if(cell_data instanceof Long){
			cell.setCellValue((Long) cell_data);
		}else {
			cell.setCellValue(new HSSFRichTextString(cell_data.toString()));
		}*/
		if(cell_data == null){
			cell.setCellValue(new HSSFRichTextString(""));
		}else{
			cell.setCellValue(new HSSFRichTextString(cell_data.toString()));	
		}
		
		// 正文格式
		HSSFCellStyle dtStyle = wb.createCellStyle();
		dtStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		dtStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		dtStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		dtStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		dtStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		dtStyle.setRightBorderColor(HSSFColor.BLACK.index);
		dtStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		dtStyle.setTopBorderColor(HSSFColor.BLACK.index);
		dtStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		// 背景颜色
		if (row_num % 2 != 0)
			dtStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		dtStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cell.setCellStyle(dtStyle);
    }
    
}