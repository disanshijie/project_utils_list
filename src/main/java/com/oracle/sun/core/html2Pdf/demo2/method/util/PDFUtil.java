package com.oracle.sun.core.html2Pdf.demo2.method.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class PDFUtil {
	String basePath = getClassResources();
	
	/**
	 * @Description 将div p tr 标签换行
	 * 返回类型 String
	 * @param str
	 * @return
	 * @注
	 */
	public static String formatHtml(String str) {
		String sb="";
		String[] temp1=str.split("</div>");
		for (String string : temp1) {
			String[] temp2=string.split("</p>");
			for (String string2 : temp2) {
				String[] temp3=string2.split("</tr>");
				for (String string3 : temp3) {
					sb +="<p>"+stripHT(string3)+"</p>";
				}
			}
		}
		return sb;
	}
	
	/**	
	 * 从html中提取纯文本  
	 * @Description 格式化 html格式的 字符串 去掉所有的 标签号
	 * 返回类型 String
	 * @param str	html字符串
	 * @return
	 * @注
	 */
	public static String stripHT(String strHtml) {  
	     String txtcontent = strHtml.replaceAll("</?[^>]+>", ""); //剔出<html>的标签    
	     txtcontent = txtcontent.replaceAll("<a>\\s*|\t|\r|\n</a>", "");//去除字符串中的空格,回车,换行符,制表符    
	     return txtcontent;
	}  
	/*
	 * 删除文件	
	 */
	public static void delFile(String str) {
		File file=new File(str);
		if(file.isFile()) {
			file.deleteOnExit();
			
		}
	}
	/*
	 * 创建文件
	 */
	public static void mkFile(String str) throws IOException {
		File file=new File(str);
        if (!file.exists()) {
        	File dir=file.getParentFile();
        	if(!dir.exists()) {
        		dir.mkdirs();
        	}
        	file.createNewFile();
        } 
	}
	/*
	 *  //TODO
	 *  获取路径　
	 */
    public static String getClassResources() {
        String path = String.valueOf(Thread.currentThread().getContextClassLoader().getResource("")).replaceAll("file:/", "").replaceAll("%20", " ").trim();
        if(path.indexOf(":") != 1) {
            path = File.separator + path;
        }
        return path;
    }
    
    public static void createPDF(Map<String,Object> root,String fltPath,String fltName,String inputFile,String outputFile) throws IOException, TemplateException{
    	delFile(inputFile);
    	delFile(outputFile);
    	mkFile(inputFile);
    	
    	String basePath = getClassResources();
	    String fontPath=basePath+"templates/static/font/SIMSUN.TTC";
        /* 创建配置 */  
        Configuration cfg = new Configuration();  
        /* 指定模板存放的路径 */  
        cfg.setDirectoryForTemplateLoading(new File(basePath + fltPath));  
        cfg.setDefaultEncoding("UTF-8");
        /* 从上面指定的模板目录中加载对应的模板文件 */  
        Template temp = cfg.getTemplate(fltName);  
  
        File file = new File(inputFile);
        Writer out = new BufferedWriter(new OutputStreamWriter(  
                new FileOutputStream(file), "utf-8"));
        temp.process(root, out);
        out.flush();
        
        String url = new File(inputFile).toURI().toURL().toString();  
        OutputStream os = new FileOutputStream(outputFile);
        ITextRenderer renderer = new ITextRenderer();  
        renderer.setDocument(url);
        // 解决中文问题  
        ITextFontResolver fontResolver = renderer.getFontResolver();  
        try {  
            fontResolver.addFont(fontPath,  
                    BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);  
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
  
        renderer.layout();  
        try {  
            renderer.createPDF(os);
        } catch (DocumentException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        os.close();  
	}
	
    /*
     * frame文件在demo1->resource中 
     */
	public static void main(String[] args) throws IOException, TemplateException {
		
		System.out.println();
		System.out.println(getClassResources());
		String fltPath = "templates/static/ftl";
		Map<String,Object> root = new HashMap<String,Object>();
		root.put("NAME","李四");
		//String fltName = "contract.ftl";
		String fltName = "1234.ftl";
		//String fltName = "orderconfirm.ftl";
		
		//String inputFile = PathUtil.getClasspath()+"templates/static/ftl/temp/"+RandomNum.createRandomString(2)+".html";
		//String inputFile = PathUtil.getClasspath()+"templates/static/ftl/temp/temp.html";
		String inputFile ="D:\\opt\\demo\\temp/temp.html";
		String outputFile ="D:\\opt\\demo\\temp/contract.pdf";
		//System.out.println(inputFile);
		PDFUtil.createPDF(root,fltPath,fltName,inputFile,outputFile);

	}
}
