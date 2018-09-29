package com.oracle.sun.design.http.project1;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
 
public class TestReadImgWithUrlOrPath {
	//public static Log log=LogFactory.getLog(TestReadImgWithUrlOrPath.class);
 
	public static void main(String s[]) throws IOException
	{
		String urlOrPath="C:\\Users\\files\\Pictures\\kaola.jpg";
		
		//String urlOrPath="http://pic4.nipic.com/20091217/3885730_124701000519_2.jpg";
		System.out.println(urlOrPath);
		System.out.println(readImg(urlOrPath));
		
	}
	/*
	 * 读取远程和本地文件图片
	 */
	public static String readImg(String urlOrPath){
        InputStream in = null;
        try {
          byte[] b ;
       //加载https途径的图片（要避开信任证书的验证）
         if(urlOrPath.toLowerCase().startsWith("https")){
            b=HttpsUtils.doGet(urlOrPath);
         }else if(urlOrPath.toLowerCase().startsWith("http")){ 
          //加载http途径的图片
            	URL url = new URL(urlOrPath);
    			in = url.openStream();    			           	
            }else{ //加载本地路径的图片
                File file = new File(urlOrPath);
                if(!file.isFile() || !file.exists() || !file.canRead()){
                   // log.info("图片不存在或文件错误");
                    return "error";
                }                
                in = new FileInputStream(file);
            }
            b = getByte(in); //调用方法，得到输出流的字节数组
			return base64ToStr(b);    //调用方法，为防止异常 ，得到编码后的结果
 
        } catch (Exception e) {
        	//log.error("读取图片发生异常:"+ e);
        	return "error";
        }
    }
	
	public static byte[] getByte(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();		
		try {
			byte[] buf=new byte[1024]; //缓存数组
			while(in.read(buf)!=-1){ //读取输入流中的数据放入缓存，如果读取完则循环条件为false;
				out.write(buf); //将缓存数组中的数据写入out输出流，如果需要写到文件，使用输出流的其他方法
				}
			out.flush();
			return out.toByteArray();	//将输出流的结果转换为字节数组的形式返回	（先执行finally再执行return	）
		} finally{
			if(in!=null){
					in.close();
			}
			if(out!=null){
				out.close();
			}			
		}
	}
	
	/*
	 * 编码
	 * Base64被定义为：Base64内容传送编码被设计用来把任意序列的8位字节描述为一种不易被人直接识别的形式
	 */
	public static String base64ToStr(byte[] bytes) throws IOException {
		String content = "";
		content = new BASE64Encoder().encode(bytes);
		return content.trim().replaceAll("\n", "").replaceAll("\r", ""); //消除回车和换行
	}
	/*
	 * 解码
	 */
	public static byte[] strToBase64(String content) throws IOException {
		if (null == content) {
			return null;
		}
		return new BASE64Decoder().decodeBuffer(content.trim());
	}
 
}
