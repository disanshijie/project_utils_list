package com.oracle.sun.design.file.one;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
/**
 * @Description 文件操作基本工具类
 * @author admin
 * @date 2018年6月8日
 * @说明
 */   
public class FileUtil {

    //判断文件/夹是否已经存在
    /**
     * 创建文件最后一个是文件名 <br>
     * 默认不覆盖<br>
     * eg: E:\\360Downloads\\src\\coms\\service\\user.txt  user.txt是文件夹名而非文件名
     * @param file
     * @throws IOException
     */
    public static void createDir(File file) throws IOException{
      if (!file.exists()) {
           file.mkdirs();
         }
    }
    /**
     * 创建文件最后一个是文件名 <br>
     * 默认不覆盖<br>
     * eg: E:\\360Downloads\\src\\coms\\service\\user  user是文件名而非文件夹
     * @param file
     * @throws IOException
     */
    public static void createFile(File file){
     if (!file.exists()) {
          file.getParentFile().mkdirs();
     }
     if(!file.isFile()){
      try {
               file.createNewFile();
          } catch (IOException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
          }
     }
    }
    /**
     * 返回文件类型 (扩展名) 小写
     * @param file  
     * @return	失败返回  ""	
     * eg:"E:\\360Downloads\\src\\com_1\\service\\user.TXT" 返回值:txt
     */
    public static String getFileType(File file){
     if(file.exists() && file.isFile()){
          String file_type = file.getName().substring(file.getName().indexOf(".")+1,file.getName().length());
          return file_type.toLowerCase();
     }
     return null;
    }
    /**
     * 获取文件名称 必须是文件类型
     * @param file      eg:E:\\360Downloads\\src\\com_1\\service\\user.TXT
     * @return  失败返回  null   eg:user
     */
    public static String getFileName(File file){
     if(file.exists() && file.isFile()){
      String name = file.getName().substring(0,file.getName().indexOf("."));
         return name;
     }
     return null;
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param f 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     */
    public static boolean delDir(File f) {
        // 判断是否是一个目录, 不是的话跳过, 直接删除; 如果是一个目录, 先将其内容清空.
        if(f.isDirectory()) {
            // 获取子文件/目录
            File[] subFiles = f.listFiles();
            // 遍历该目录
            for (File subFile : subFiles) {
                // 递归调用删除该文件: 如果这是一个空目录或文件, 一次递归就可删除. 如果这是一个非空目录, 多次
                // 递归清空其内容后再删除
            	boolean success = delDir(subFile);
            	 if (!success) {
                     return false;
                 }
            }
        }
        // 删除空目录或文件
        return f.delete();
    }
    /**
     * @Description 删除此文件夹下的所有内容， 不删除当前这个文件夹
     * 返回类型 void
     * @param f
     * @注
     */
    public static void delDirNoThis(File f) {
    	if(f.isDirectory()) {
    		File[] subFiles = f.listFiles();
    		for (File subFile : subFiles) {
    			delDir(subFile);
    		}
    	}
    }
    
    /**
     * @Description 判断文件是否是某种类型   //TODO 大小写敏感
     * 返回类型 Boolean
     * @param file
     * @param type
     * @return
     * @注
     */
    public static Boolean isFileType(File file,String type){
     if(file.exists() && file.isFile()){
          if(file.getName().endsWith(type))
              return true;
     }
     return false;
    }
  
  	/**
  	 * @Description //获取目录上一级目录 或者获取文件目录  文件本身不存在不影响获得上一级（上一级存在就行）
	 * @param path 目录/文件路径 
	 * @return 如果文件目录或目录的上一级目录不存在 返回null
  	 * 返回类型 String
  	 * @注
  	 */
	 public static String obtainParentPath(String path) {
		File file=new File(path);
		if(file.getParentFile().exists()){
			//dir=file.getParentFile().getAbsolutePath();
			return file.getParent();
			//dir=file.getParentFile().getPath();
		 }else{
		 }
		 return null;
	 }
	
	 
	 /**
	  * 得到文件的大小
	  * @param fileName
	  * @return
	  */
	 public static int getFileSize(String fileName){
	     
	     File file = new File(fileName);
	     FileInputStream fis = null;
	     int size = 0 ;
	     try {
	         fis = new FileInputStream(file);
	         size = fis.available();
	     }catch (Exception e) {
	         e.printStackTrace();
	     }
	     return size ;
	 }
	 
	 /**
	    * 获取文件编码格式
	    * @param fileName
	    * @return
	    * @throws Exception
	    */
	   public static String getFileEncoding(String fileName) throws Exception {
	       BufferedInputStream bin = new BufferedInputStream(new FileInputStream(fileName));
	       int p = (bin.read() << 8) + bin.read();
	       String code = null;
	       switch (p) {
	       case 0xefbb:
	           code = "UTF-8";
	           break;
	       case 0xfffe:
	           code = "Unicode";
	           break;
	       case 0xfeff:
	           code = "UTF-16BE";
	           break;
	       default:
	           code = "GBK";
	       }
	
	       return code;
	   }
	   
	   /**
	    * 改变文件的扩展名 //TODO 待测试
	    * @param fileNM	文件位置 eg：
	    * @param ext	想要改成的扩展名 eg：
	    * @return
	    */
	   public static String changeFileExt(String fileNM, String ext) {
	       int i = fileNM.lastIndexOf('.');
	       if (i >= 0)
	           return (fileNM.substring(0, i) + ext);
	       else
	           return fileNM;
	   }
	   
	   /**
	    * 判断目录是否存在
	    * @param strDir
	    * @return
	    */
	   public static boolean existsDirectory(String strDir) {
	       File file = new File(strDir);
	       return file.exists() && file.isDirectory();
	   }
	   
	   /**
	    * 判断文件是否存在
	    * @param strDir
	    * @return
	    */
	   public static boolean existsFile(String strDir) {
	       File file = new File(strDir);
	       return file.exists();
	   }
	 
}