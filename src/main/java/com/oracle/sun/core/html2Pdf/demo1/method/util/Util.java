package com.oracle.sun.core.html2Pdf.demo1.method.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class Util {

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
	//从html中提取纯文本  
	public static String stripHT(String strHtml) {  
	     String txtcontent = strHtml.replaceAll("</?[^>]+>", ""); //剔出<html>的标签    
	     txtcontent = txtcontent.replaceAll("<a>\\s*|\t|\r|\n</a>", "");//去除字符串中的空格,回车,换行符,制表符    
	     return txtcontent;
	}  
		
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
    public static String getClassResources() {
        String path = String.valueOf(Thread.currentThread().getContextClassLoader().getResource("")).replaceAll("file:/", "").replaceAll("%20", " ").trim();
        if(path.indexOf(":") != 1) {
            path = File.separator + path;
        }
        return path;
    }
    
    /**
     * 功能：Java读取txt文件的内容
     * 步骤：1：先获得文件句柄
     * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
     * 3：读取到输入流后，需要读取生成字节流
     * 4：一行一行的输出。readline()。
     * 备注：需要考虑的是异常情况
     * @param filePath
     */
    public static String readTxtFile(String filePath){
    	StringBuffer sb=new StringBuffer("");
        try {
                String encoding="utf-8";
                File file=new File(filePath);
                if(file.isFile() && file.exists()){ //判断文件是否存在
                    InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file),encoding);//考虑到编码格式
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    while((lineTxt = bufferedReader.readLine()) != null){
                    	sb.append(lineTxt);
                    }
                    read.close();
        }else{
            System.out.println("找不到指定的文件");
        }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return sb.toString();
    }
    
    public static String readFile(File file) {
    	if(!file.exists()) {
    		return "";
    	}
    	StringBuffer sb=new StringBuffer("");
    	Reader re=null;
    		try {
				re=new FileReader(file);
				char[] b=new char[1];
	    		while(re.read(b)>-1) {
	    			sb.append(b);
	    		}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	return sb.toString();
    	
    }
}
