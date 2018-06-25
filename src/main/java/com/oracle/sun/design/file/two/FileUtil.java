package com.oracle.sun.design.file.two;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class FileUtil {
	
	 /**
     * 查找文件夹下是否有"Impl" 文件夹,如果没有 创建该文件夹;如果有返回改子文件夹
     * @param targetDir 文件夹
     * @param dirName 子文件夹的名称
     * @return 子文件夹路径
     */
    public static File createDirIfnone(File targetDir,String dirName ){
      // 检查impl目录是否村子啊，若不存在则创建该目录
        File dir = null;
        for( File temp : targetDir.listFiles()){
            if( temp != null && dirName.equals(temp.getName())){
                dir = temp;
                break;
            }
        }
        if( dir == null ){
            dir = new File(targetDir,dirName);
            dir.mkdir();
        }
        return dir;
    }

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
 	* @Description:将文件或着文件夹复制到另一个文件夹中去
 	* @author sun
 	* @date  2018年6月8日 上午11:10:27
 	* @param sourceFile	源，文件夹或者文件
 	* @param dest	目标路径，必须是文件夹；如果source是文件则自动创建此文件
 	* @throws IOException
 	 */
     protected static void copyFolder(File sourceFile, File dest) throws IOException{
 		File destFile=new File(dest,sourceFile.getName());
 		
 		if(sourceFile.isFile()) {
 			destFile.createNewFile();
 			copyFileByStreams(sourceFile,destFile);
 		}else if(sourceFile.isDirectory()) {
 			destFile.mkdir();
 			File[] fileArray = sourceFile.listFiles();
 			for (File file : fileArray) {
 				/*如果source是文件夹，那么dest只能是文件夹
 				 * 如果source是文件，那么dest最好是文件
 				 */
 				copyFolder(file,destFile);
 			}
 		}
 	}
 	/**
 	* @Description:将文件复制到另一个文件或着文件夹下的内容复制到另一个文件夹中去
 	* @date  2018年6月8日 上午11:45:04
 	* @param sourceFile	源，文件夹或者文件
 	* @param destFile	目标，文件夹或者文件
 	* @throws IOException
 	* @注	如果source是文件，dest必须要是文件
 	 */
 	protected static void copyFiles(File sourceFile, File destFile) throws IOException{
 		if(sourceFile.isFile()) {
 			destFile.createNewFile();
 			copyFileByStreams(sourceFile,destFile);
 		}else if(sourceFile.isDirectory()) {
 			destFile.mkdir();
 			File[] fileArray = sourceFile.listFiles();
 			for (File file2 : fileArray) {
 				copyFiles(file2,new File(destFile+file2.getName()));
 			}
 		}
 	}
 	
 	  /**
 	   *	//TODO 
	    * 拷贝文件到指定的路径 (使用了自定义的CopyFileUtil工具类) 
	    * @param fileURL
	    * @param targetFile
	    */
	   public void copy(String fileURL ,String targetFile){
	       URLConnection urlconn = null ;
	       try {
	           URL url = new URL(fileURL);
	           urlconn = url.openConnection();
	           InputStream in = urlconn.getInputStream();
	
	           File newfile = new File(targetFile);        
	           FileOutputStream fos = new FileOutputStream(newfile);
	         //  CopyFileUtil.copy(in, fos);//使用了自定义的FileUtil工具类
	       } catch (MalformedURLException e) {
	           e.printStackTrace();
	       } catch (IOException e) {
	           e.printStackTrace();
	       } 
	   }
}
