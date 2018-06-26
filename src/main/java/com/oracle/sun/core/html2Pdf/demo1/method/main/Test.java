package com.oracle.sun.core.html2Pdf.demo1.method.main;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.lowagie.text.DocumentException;
import com.oracle.sun.core.html2Pdf.demo1.method.core.Start;
import com.oracle.sun.core.html2Pdf.demo1.method.util.PDFUtil;
import com.oracle.sun.core.html2Pdf.demo1.method.util.Util;

import freemarker.template.TemplateException;

public class Test {

	public static String basePath = Test.class.getResource(".").getPath()+"../";
	
	public static void main(String[] args) {
		//test1();
		//test2();
		test3();
	}
	/*
	 * 基本测试 生成pdf
	 */
	private static void test1() {
		
		System.out.println(Util.getClassResources());
		Map<String,Object> root = new HashMap<String,Object>();
		root.put("name","测试名称李四");
		
		String fltPath = "resource/static/ftl";
		//String fltName = "contract.ftl";
		String fltName = "1234.ftl";
		//String fltName = "orderconfirm.ftl";
		
		//String inputFile = PathUtil.getClasspath()+"templates/static/ftl/temp/"+RandomNum.createRandomString(2)+".html";
		//String inputFile = PathUtil.getClasspath()+"templates/static/ftl/temp/temp.html";
		String inputFile ="D:\\opt\\demo\\temp/temp.html";
		String outputFile ="D:\\opt\\demo\\temp/contract.pdf";
		//System.out.println(inputFile);
		Start.createPDF(root,fltPath,fltName,inputFile,outputFile);
	}
	/*
	 * 生成水印
	 */
	private static void test3() {
		
		System.out.println(Util.getClassResources());
		Map<String,Object> root = new HashMap<String,Object>();
		root.put("name","测试名称李四");
		
		String fltPath = "resource/static/ftl";
		//String fltName = "contract.ftl";
		String fltName = "1234.ftl";
		//String fltName = "orderconfirm.ftl";
		
		//String inputFile = PathUtil.getClasspath()+"templates/static/ftl/temp/"+RandomNum.createRandomString(2)+".html";
		//String inputFile = PathUtil.getClasspath()+"templates/static/ftl/temp/temp.html";
		String inputFile ="D:\\opt\\demo\\temp/temp.html";
		String outputFile ="D:\\opt\\demo\\temp/contract.pdf";
		//System.out.println(inputFile);
		Start.createPDFMark(root,fltPath,fltName,inputFile,outputFile);
	}
	/*
	 * 非标准html文件生成 标准html后转为pdf
	 */
	private static void test2() {
		
		String file = basePath+"resource/static/ftl/errorHtml.ftl";
		String outputFile ="D:\\opt\\demo\\temp/strContent.pdf";
			String content=Util.readTxtFile(file);
			//String content=Util.readFile(new File(file));
			System.out.println(content);
			try {
				PDFUtil.Html2PDF(content,outputFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TemplateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
	}
}
