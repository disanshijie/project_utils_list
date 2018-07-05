package com.oracle.sun.core.stream.one;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class StreamUtil {

    /**
    * @Description 流的形式复制文件
    * 返回类型 void
    * @param srcFile	源文件
	 * @param newFile	目标文件
    * @throws IOException
    * @注
    */
    protected static void copyFileByStreams(File srcFile, File newFile) throws IOException {
		InputStream input = null;    
	    OutputStream output = null;  
	    try {
	    	 //复制文件到指定位置
	    	 input = new BufferedInputStream(new FileInputStream(srcFile));
	    	 output = new BufferedOutputStream(new FileOutputStream(newFile));
	         byte[] b = new byte[1024];
	         Integer len = 0;
	         while((len = input.read(b)) != -1) {
	        	output.write(b, 0, len);
	         }
	    } catch (Exception e1) {
			e1.printStackTrace();
		}finally {
			try {
				input.close();
				output.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
	    }
	}
    
    /**
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 * 
	 * @param fileName
	 *            文件名
	 */
	public static List<String> readFileByLines(String fileName) {
		File file = new File(fileName);
		List<String> str=new ArrayList<String>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String temp = null;
			// 一次读入一行，直到读入null为文件结束
			while ((temp = reader.readLine()) != null) {
				str.add(temp);
				// 显示行号
				//System.out.println("line " + line + ": " + temp);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					
				}
			}
		}
		return str;
	}
	
	public static void writeFileString(){
		
	}
}
