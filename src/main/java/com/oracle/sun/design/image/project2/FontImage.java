package com.oracle.sun.design.image.project2;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;  

/**
 * 
 * @url 
 * @Description
 * @author admin
 * @date 2018年9月29日
 * @version V1.0
 * @说明
 */
public class FontImage {
	
    public static void main(String[] args) throws Exception {  
       // createImage("请在这里输入文字", new Font("微软雅黑", Font.PLAIN, 32), new File("D:/opt/a.png"), 500, 64);  //OK
        
       //createImage("文字居中",500,40); //OK
        
    	getImage("文字居中",500,40); //OK
        
    }  
    /**
     *  作者--sunbo
     * 返回类型 BufferedImage
     * @param text
     * @param width
     * @param height
     * @return
     * @注
     */
    public static BufferedImage getImage(String text,Integer width, Integer height)  {
    	// 创建BufferedImage对象
    	BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);  
    	try {
	        // 获取Graphics2D  
	        Graphics2D g2d = image.createGraphics();  
	        // 画图  
	        g2d.setBackground(new Color(255,255,255));
	        //g2d.setPaint(new Color(0,0,0));  
	        g2d.setColor(Color.black);
	        g2d.clearRect(0, 0, width, height);  
	        Font font=new Font("宋体",Font.PLAIN,12);
	        g2d.setFont(font);
	        // 抗锯齿
	        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        // 计算文字长度，计算居中的x点坐标
	        FontMetrics fm = g2d.getFontMetrics(font);
	        int textWidth = fm.stringWidth(text);
	        int widthX = (width - textWidth) / 2;
	        
	        //y居中
	        int ascent = fm.getAscent();
	        int descent = fm.getDescent();
	        int y = (height - (ascent + descent)) / 2 + ascent; 
	        
	        // 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。 
	        g2d.drawString(text,widthX,y);
	        // 释放对象  
	        g2d.dispose(); 
    	}catch(Exception e){
    		return null;
    	}
    	return image;
    }
    /**
     * https://blog.csdn.net/w410589502/article/details/72898184?utm_source=copy
     * 返回类型 void
     * @param str
     * @param width
     * @param height
     * @throws Exception
     * @注
     */
    public static void createImage(String text,Integer width, Integer height) throws Exception {  
        // 创建BufferedImage对象
        BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);  
        // 获取Graphics2D  
        Graphics2D g2d = image.createGraphics();  
        // 画图  
        g2d.setBackground(new Color(255,255,255));
        //g2d.setPaint(new Color(0,0,0));  
        g2d.setColor(Color.red);
        g2d.clearRect(0, 0, width, height);  
        Font font=new Font("宋体",Font.PLAIN,32);
        g2d.setFont(font);
        // 抗锯齿
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 计算文字长度，计算居中的x点坐标
        FontMetrics fm = g2d.getFontMetrics(font);
        int textWidth = fm.stringWidth(text);
        int widthX = (width - textWidth) / 2;
        
        //y居中
        int ascent = fm.getAscent();
        int descent = fm.getDescent();
        int y = (height - (ascent + descent)) / 2 + ascent; 
        
        // 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。 
        g2d.drawString(text,widthX,y);
        // 释放对象  
        g2d.dispose();  
        // 保存文件      
        ImageIO.write(image, "jpg", new File("D:/opt/test.jpg"));
    	
    }
    
    /**
     * https://blog.csdn.net/sinat_28505133/article/details/54669111
     * 返回类型 void
     * @param str
     * @param font
     * @param outFile
     * @param width
     * @param height
     * @throws Exception
     * @注	根据str,font的样式以及输出文件目录  
     */
    public static void createImage(String str, Font font, File outFile,
            Integer width, Integer height) throws Exception {  
        // 1.创建一个不带透明色的BufferedImage对象
        BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_BGR);
        
        // 2.创建一个带透明色的BufferedImage对象
        // image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        Graphics g = image.getGraphics();  
        g.setClip(0, 0, width, height);  
        g.setColor(Color.white);  
        g.fillRect(0, 0, width, height);// 先用黑色填充整张图片,也就是背景  
        g.setColor(Color.black);// 在换成黑色  
        g.setFont(font);// 设置画笔字体  
        /** 用于获得垂直居中y */  
        Rectangle clip = g.getClipBounds();  
        FontMetrics fm = g.getFontMetrics(font);  
        int ascent = fm.getAscent();  
        int descent = fm.getDescent();  
        int y = (clip.height - (ascent + descent)) / 2 + ascent;  
        for (int i = 0; i < 6; i++) {// 256 340 0 680  
            g.drawString(str, i * 680, y);// 画出字符串  
        }  
        g.dispose();  
        ImageIO.write(image, "png", outFile);// 输出png图片  
    }  
}  
