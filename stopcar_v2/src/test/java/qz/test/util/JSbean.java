package qz.test.util;

import java.lang.reflect.Field;

public class JSbean {
	
	public static String returnST(String str){
		StringBuffer sb = new StringBuffer();
		char[] xx = str.toCharArray();
		for (char char1 : xx) {
			if(Character.isUpperCase(char1)){ 
				sb.append("_");
				sb.append(Character.toLowerCase(char1));
			}else{
				sb.append(char1);
			}
			
		}
		return sb.toString();
	}
	/**
	 * 处理 JS 中的编辑表
	 * @param class_bin
	 */
	public static void PrintClassFiled(Class class_bin){
		Field[]  xx = class_bin.getDeclaredFields();
		System.out.println("该表的字段个数："+xx.length);
		for (Field field : xx) {
			if("serialVersionUID".equalsIgnoreCase(field.getName()))
			   continue;
			/*System.out.println("h.find('input[name=\""
			   +UserInfo.class.getSimpleName().toLowerCase()+"."
					+field.getName()+"\"]').val(d."
			   +returnST(field.getName())+");");*/
		}
	}
	/**
	 * 处理EditPage
	 * @param args
	 */
	public static void PrintEditPage(Class class_bin){
		/**
		 * <div class="info_item">
		<div class="item_title">软件名称</div>
		<div class="item_value"><input name="hotsoftware.hsName"  id= "hsName" value=""  class="input" /></div>
	    </div>
		 */
		Field[]  xx = class_bin.getDeclaredFields();
		for (Field field : xx) {
			/*System.out.println("<div class=\"info_item\">");
			System.out.println("	<div class=\"item_title\"></div>");
			System.out.println("	<div class=\"item_value\"><input name=\""+UserInfo.class.getSimpleName().toLowerCase()+"."
					+field.getName()+
					"\"  id= \""+field.getName()+"\" value=\"\"  class=\"input\" /></div>");
			System.out.println("</div>");*/
		}
		
		
	}
	
	
	public static void main(String[] args) {
		//PrintClassFiled(UserInfo.class);
		//PrintEditPage(UserInfo.class);
		
	}

}
