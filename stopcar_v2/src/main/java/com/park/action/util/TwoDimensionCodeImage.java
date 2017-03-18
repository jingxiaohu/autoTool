package com.park.action.util;

import java.awt.image.BufferedImage;

import jp.sourceforge.qrcode.data.QRCodeImage;

/**
 * 
 * Title:二维码图片类
 * Description:描述 
 * Copyright: Copyright (c) 2013
 * Company: rumtel Technology Chengdu Co. Ltd.
 * @author 敬小虎
 * @version 1.0 2014-2-28
 */
public class TwoDimensionCodeImage implements QRCodeImage {

	BufferedImage bufImg = null;

	public TwoDimensionCodeImage(BufferedImage bufImg) {
		this.bufImg = bufImg;
	}

	@Override
	public int getHeight() {
		return bufImg.getHeight();
	}

	@Override
	public int getPixel(int x, int y) {
		return bufImg.getRGB(x, y);
	}

	@Override
	public int getWidth() {
		return bufImg.getWidth();
	}

}
