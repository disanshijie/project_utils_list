package com.oracle.sun.core.html2Pdf.demo1.method.core;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import com.lowagie.text.DocumentException;
import com.oracle.sun.core.html2Pdf.demo1.method.util.PDFUtil;
import com.oracle.sun.core.html2Pdf.demo1.method.util.Util;

import freemarker.template.TemplateException;

public class Start {

	
	//public static String basePath = System.getProperty("user.dir")+"/com/oracle/sun/core/demo1/method/resource/";
	/*
	 * 生成pdf
	 */
	public static void createPDF(Map<String,Object> root,String fltPath,String fltName,String inputFile,String outputFile) {
		try {
			//创建html
			PDFUtil.htmlTemplate(root,fltPath,fltName,inputFile);
			//生成pdf
			PDFUtil.Html2PDF(new File(inputFile), outputFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * 有水印的
	 */
	public static void createPDFMark(Map<String,Object> root,String fltPath,String fltName,String inputFile,String outputFile) {
		try {
			String tempFile=System.getProperty("user.dir")+"/opt/";
			if(!new File(tempFile).exists()) {
				new File(tempFile).mkdirs();
			}
			
			//创建html
			PDFUtil.htmlTemplate(root,fltPath,fltName,inputFile);
			//生成pdf
			PDFUtil.Html2PDF(new File(inputFile), tempFile+"temp.pdf");
			
			//生成水印 文字
			String text="华美欧huameiou";
            int textWidth=200;
            int textHeight=300;
			PDFUtil.addWaterMark( tempFile+"temp.pdf",  outputFile, text, textWidth,  textHeight);
			
			//生成水印 图片
			String markImagePath="resource/static/img/icon_021_hover.png";
			PDFUtil.addPdfMark( tempFile+"temp.pdf",  outputFile,  markImagePath,textWidth,textHeight);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * 
	 */
	public static void createPDF(String content,String outputFile) {
		
	}
	
}
