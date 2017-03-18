package com.park.action.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import jp.sourceforge.qrcode.QRCodeDecoder;

import org.apache.log4j.Logger;

import com.park.core.Constants;
import com.swetake.util.Qrcode;

/**
 * 
 * Title:二维码相关操作工具类 
 * Description:描述 
 * Copyright: Copyright (c) 2013
 * Company: rumtel Technology Chengdu Co. Ltd.
 * @author 敬小虎
 * @version 1.0 2014-2-28
 */
public final class TwoDimensionCodeTool {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	private TwoDimensionCodeTool(){
		
	}
	
	private static TwoDimensionCodeTool ins = new TwoDimensionCodeTool();
	
	public static TwoDimensionCodeTool getInstance(){
		return ins;
	}
	
	private char codeRate='M';//容错率
	private int codeSize=7;//二维码尺寸
	private String logoPath = null;//LOGO路径 设置之后生成的二维码会居中显示LOGO
	
	
	public void setCodeSize(int codeSize) {
		this.codeSize = codeSize;
	}

	public void setCodeRate(char codeRate) {
		this.codeRate = codeRate;
	}

	/**
	 * 设置LOGO图片路径
	 * @param logoPath
	 */
	public void setLogPath(String logoPath){
		this.logoPath = logoPath;
	}
	

	/**
	 * 生成二维码(QRCode)图片
	 * 
	 * @param content 存储内容
	 *            
	 * @param imgPath 图片路径
	 *           
	 */
	public void encoderQRCode(String content, String imgPath) {
		encoderQRCode(content, imgPath, "png", 7);
	}

	/**
	 * 生成二维码(QRCode)图片
	 * 
	 * @param content 存储内容
	 *           
	 * @param output 输出流
	 *           
	 */
	public void encoderQRCode(String content, OutputStream output) {
		encoderQRCode(content, output, "png", codeSize);
	}

	/**
	 * 生成二维码(QRCode)图片
	 * 
	 * @param content 存储内容
	 *            
	 * @param imgPath 图片路径
	 *           
	 * @param imgType 图片类型
	 *           
	 */
	public void encoderQRCode(String content, String imgPath, String imgType) {
		encoderQRCode(content, imgPath, imgType, codeSize);
	}

	/**
	 * 生成二维码(QRCode)图片
	 * 
	 * @param content 存储内容
	 *            
	 * @param output 输出流
	 *           
	 * @param imgType  图片类型
	 *           
	 */
	public void encoderQRCode(String content, OutputStream output,
			String imgType) {
		encoderQRCode(content, output, imgType, codeSize);
	}

	/**
	 * 生成二维码(QRCode)图片
	 * 
	 * @param content 存储内容
	 *            
	 * @param imgPath 图片路径
	 *            
	 * @param imgType 图片类型
	 *            
	 * @param size 二维码尺寸
	 *            
	 */
	public void encoderQRCode(String content, String imgPath, String imgType,
			int size) {
		try {
			BufferedImage bufImg = qRCodeCommon(content, imgType, size);
			File imgFile = new File(imgPath);
			System.out.println(imgFile.getAbsolutePath());
			if(imgFile != null && !imgFile.exists()){ 
				imgFile.mkdirs();
			}
			// 生成二维码QRCode图片
			ImageIO.write(bufImg, imgType, imgFile);
		} catch (Exception e) {
			log.error("生成二维码图片错误", e);
		}
	}

	/**
	 * 生成二维码(QRCode)图片
	 * 
	 * @param content 存储内容
	 *            
	 * @param output 输出流
	 *            
	 * @param imgType 图片类型
	 *            
	 * @param size 二维码尺寸
	 *            
	 */
	public void encoderQRCode(String content, OutputStream output,
			String imgType, int size) {
		try {
			BufferedImage bufImg = qRCodeCommon(content, imgType, size);
			// 生成二维码QRCode图片
			ImageIO.write(bufImg, imgType, output);
		} catch (Exception e) {
			log.error("生成二维码图片错误", e);
		}
	}

	/**
	 * 生成二维码(QRCode)图片的公共方法
	 * 
	 * @param content 存储内容
	 *            
	 * @param imgType  图片类型
	 *           
	 * @param size 二维码尺寸
	 *           
	 * @return
	 */
	private BufferedImage qRCodeCommon(String content, String imgType, int size) {
		BufferedImage bufImg = null;
		BufferedImage logo = null;
		int logoWidth=30;//LOGO宽  
		int logoHight=30;//LOGO高
		try {
			//Logo图片高宽获取
			if(logoPath!=null&&!"".equals(logoPath)){
				logo = ImageIO.read(new File(logoPath));
				//如果有LOGO 那么则设置它的宽度
				logoWidth = logo.getWidth();
				logoHight = logo.getHeight();
			}
			Qrcode qrcodeHandler = new Qrcode();
			// 设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小
			qrcodeHandler.setQrcodeErrorCorrect(codeRate);
			qrcodeHandler.setQrcodeEncodeMode('B');
			// 设置设置二维码尺寸，取值范围1-40，值越大尺寸越大，可存储的信息越大
			qrcodeHandler.setQrcodeVersion(size);
			// 获得内容的字节数组，设置编码格式
			byte[] contentBytes = content.getBytes("utf-8");
			// 图片尺寸
			int imgSize = 67 + 12 * (size - 1);
			bufImg = new BufferedImage(imgSize, imgSize,BufferedImage.TYPE_INT_RGB);
			Graphics2D gs = bufImg.createGraphics();
			// 设置背景颜色
			gs.setBackground(Color.WHITE);
			gs.clearRect(0, 0, imgSize, imgSize);
			// 设定图像颜色> BLACK
			gs.setColor(Color.BLACK);
			
			//获得LOGO的范围坐标 
			int logoStart_W=(imgSize-logoWidth)/2; 
			int logoEnd_W=logoStart_W+logoWidth;
			 
			int logoStart_H=(imgSize-logoHight)/2;
			int logoEnd_H=logoStart_H+logoHight;
			System.out.println(logoStart_W+" "+logoEnd_W);
			System.out.println(logoStart_H+" "+logoEnd_H);
			// 设置偏移量，不设置可能导致解析出错
			int pixoff = 1;
			int pos = 3;
			// 输出内容> 二维码 800字节上限
			if (contentBytes.length > 0 && contentBytes.length < 120) {
				boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
				System.out.println("contentBytes==内容实际字节数==="+contentBytes.length); 
				System.out.println("codeOut.length=实点和虚点一共=="+codeOut.length); 
				//如果
				int count = 0;
				for (int i = 0; i < codeOut.length; i++) {
					for (int j = 0; j < codeOut.length; j++) {
						int x = j * pos + pixoff;
						int y =  i * pos + pixoff;
						if (codeOut[j][i]) {
							//如果位于LOGO区域 则不填充数据
							if((x>=logoStart_W&&x<=logoEnd_W)&&(y>=logoStart_H&&y<=logoEnd_H)){
								continue;
							}
							gs.fillRect(x,y, pos, pos);
						}
					}
				}
				System.out.println("count==="+count); 
			} else {
				System.out.println("QRCode content bytes length = "
						+ contentBytes.length + " not in [0, 120]."); 
				throw new Exception("QRCode content bytes length = "
						+ contentBytes.length + " not in [0, 120].");
			}
			
			/**添加LOGO*/
			if(logo != null){
				// logo宽高
				int width = logo.getWidth();
				int height = logo.getHeight();
				System.out.println("width=="+width);
				System.out.println("height=="+height); 
				// logo位置居中显示
				gs.drawImage(logo, logoStart_W, logoStart_W, width, height, null);//画到到画板上
			}
			
			gs.dispose();
			bufImg.flush();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("生成二维码图片底层错误", e);
		}
		return bufImg;
	}

	/**
	 * 解析二维码（QRCode）
	 * 
	 * @param imgPath 图片路径
	 *            
	 * @return
	 */
	public String decoderQRCode(String imgPath) {
		// QRCode 二维码图片的文件
		File imageFile = new File(imgPath);
		BufferedImage bufImg = null;
		String content = null;
		try {
			bufImg = ImageIO.read(imageFile);
			QRCodeDecoder decoder = new QRCodeDecoder();
			content = new String(decoder.decode(new TwoDimensionCodeImage(bufImg)), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("解析二维码图片错误", e);
		} 
		return content;
	}

	/**
	 * 解析二维码（QRCode）
	 * 
	 * @param input 输入流
	 *            
	 * @return
	 */
	public String decoderQRCode(InputStream input) {
		BufferedImage bufImg = null;
		String content = null;
		try {
			bufImg = ImageIO.read(input);
			QRCodeDecoder decoder = new QRCodeDecoder();
			content = new String(decoder.decode(new TwoDimensionCodeImage(
					bufImg)), "utf-8");
		} catch (IOException e) {
			log.error("解析二维码图片错误", e);
		} 
		return content;
	}
	
	
	/******************************生成二维码*************************************/
	public void makeCodeImg(String content,String ui_loginname,String imgBasePath,String logo_path,long nanotime) throws Exception{
		if(content != null && imgBasePath!= null && logo_path != null){
			//设置二维码排错率
			TwoDimensionCodeTool.getInstance().setCodeRate('H');	
			//设置二维码尺寸
			TwoDimensionCodeTool.getInstance().setCodeSize(5);	
			//设置Logo图片
			TwoDimensionCodeTool.getInstance().setLogPath(logo_path);
			//设置生成路径
			imgBasePath += Constants.SYSTEM_QrCode_path+File.separator+ui_loginname+".jpg";
			//生成二维码图片
			TwoDimensionCodeTool.getInstance().encoderQRCode(content,imgBasePath,"jpg");	
		}
	}
	public static void main(String[] args) throws Exception { 
		//www.qz.com?r=用户的账号
		TwoDimensionCodeTool.getInstance().makeCodeImg("www.qz.com?r=用户的账号","jingxiaohu","d:"+File.separator,"d://qz.jpg",System.nanoTime());
		String imgPath = "D:\\QzQrCode\\jingxiaohu.jpg";
		String decoderContent = TwoDimensionCodeTool.getInstance().decoderQRCode(imgPath);
		System.out.println("解析结果如下：");
		System.out.println(decoderContent);
	}
}
