package com.oracle.sun.design.sftp.design;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.jcraft.jsch.ChannelSftp;
import com.oracle.sun.design.sftp.design.impl.FileProgressMonitor;
import com.oracle.sun.design.sftp.design.impl.MyProgressMonitor;
/**
 * 
 * @url http://www.cnblogs.com/longyg/archive/2012/06/25/2556576.html
 * @Description 上传至linux上 动态显示上传进度
 * @author admin
 * @date 2018年6月8日
 * @version V1.0
 * @说明
 */
public class SFTPTest {

	public static Map<String, String> sftpDetails = new HashMap<String, String>();
	
    public SFTPChannel getSFTPChannel() {
    	
        return new SFTPChannel();
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
    	/*
        // 设置主机ip，端口，用户名，密码
        sftpDetails.put(SFTPConstants.SFTP_REQ_HOST, "10.9.167.55");
        sftpDetails.put(SFTPConstants.SFTP_REQ_USERNAME, "root");
        sftpDetails.put(SFTPConstants.SFTP_REQ_PASSWORD, "arthur");
        sftpDetails.put(SFTPConstants.SFTP_REQ_PORT, "22");
    	*/
        SFTPTest test = new SFTPTest();
        SFTPChannel channel = test.getSFTPChannel();
        ChannelSftp chSftp = channel.getChannel(sftpDetails, 60000);
        
        test1(chSftp);
        // test2(chSftp); //与test1相比仅仅修改chSftp里的put方法参数 new FileProgressMonitor(fileSize)
        //test3(chSftp); //与test2相比chSftp里的put方法参数 为new MyProgressMonitor()
        
        
        chSftp.quit();
        channel.closeChannel();
    }
    /**
     * 
     * @Description
     * 返回类型 void
     * @param chSftp
     * @throws Exception
     * @注
     */
    public static void test1(ChannelSftp chSftp) throws Exception {
    	
    	String src = "D:\\垃圾箱/比赛默认图.zip"; // 本地文件名
    	String dst = "/usr/games/比赛默认图.zip"; // 目标文件名
    	
    	File file = new File(src);
    	long fileSize = file.length();
    	
    	/*
    	 * 代码段1：采用向put方法返回的输出流中写入数据的方式来传输文件。 需要由程序来决定写入什么样的数据，这里是将本地文件的输入流写入输出流。
    	 * 采用这种方式的好处是，可以自行设定每次写入输出流的数据块大小，
    	 */
    	OutputStream out = chSftp.put(dst, ChannelSftp.OVERWRITE); // 使用OVERWRITE模式
    	byte[] buff = new byte[1024 * 256]; // 设定每次传输的数据块大小为256KB
    	int read;
    	if (out != null) {
    		System.out.println("Start to read input stream");
    		InputStream is = new FileInputStream(src);
    		do {
    			read = is.read(buff, 0, buff.length);
    			if (read > 0) {
    				out.write(buff, 0, read);
    			}
    			out.flush();
    		} while (read >= 0);
    		System.out.println("input stream read done.");
    	}
    	/*
    	 * 代码段2：直接将本地文件名为src的文件上传到目标服务器，目标文件名为dst。（注：使用这个方法时，dst可以是目录，当dst是目录时，上传后的目标文件名将与src文件名相同）
    	 */
    	// chSftp.put(src, dst, ChannelSftp.OVERWRITE); // 代码段2
    	/*
    	 * 代码段3：将本地文件名为src的文件输入流上传到目标服务器，目标文件名为dst。
    	 */
    	// chSftp.put(new FileInputStream(src), dst, ChannelSftp.OVERWRITE); // 代码段3
    	
    }
    
    public static void test2(ChannelSftp chSftp) throws Exception {
        
        String src = "D:\\垃圾箱/APPSTORE宣传图.zip"; // 本地文件名
        String dst = "/usr/games/比赛默认图.zip"; // 目标文件名

        File file = new File(src);
        long fileSize = file.length();
        
        // 代码段1
        OutputStream out = chSftp.put(dst, new FileProgressMonitor(fileSize), ChannelSftp.OVERWRITE); // 使用OVERWRITE模式
        byte[] buff = new byte[1024 * 256]; // 设定每次传输的数据块大小为256KB
        int read;
        if (out != null) {
            System.out.println("Start to read input stream");
            InputStream is = new FileInputStream(src);
            do {
                read = is.read(buff, 0, buff.length);
                if (read > 0) {
                    out.write(buff, 0, read);
                }
                out.flush();
            } while (read >= 0);
            System.out.println("input stream read done.");
        }
        
       // chSftp.put(src, dst, new FileProgressMonitor(fileSize), ChannelSftp.OVERWRITE); // 代码段2
        
        // chSftp.put(new FileInputStream(src), dst, new FileProgressMonitor(fileSize), ChannelSftp.OVERWRITE); // 代码段3
    }
    /**
     * 
     * @Description
     * 返回类型 void
     * @param chSftp
     * @throws Exception
     * @注
     */
    public static void test3(ChannelSftp chSftp) throws Exception {
    	
    	String src = "D:\\垃圾箱/APPSTORE宣传图.zip"; // 本地文件名
    	String dst = "/usr/games/比赛默认图.zip"; // 目标文件名
    	
    	File file = new File(src);
    	long fileSize = file.length();
    	
    	// 代码段1
    	OutputStream out = chSftp.put(dst, new MyProgressMonitor(), ChannelSftp.OVERWRITE); // 使用OVERWRITE模式
    	byte[] buff = new byte[1024 * 256]; // 设定每次传输的数据块大小为256KB
    	int read;
    	if (out != null) {
    		System.out.println("Start to read input stream");
    		InputStream is = new FileInputStream(src);
    		do {
    			read = is.read(buff, 0, buff.length);
    			if (read > 0) {
    				out.write(buff, 0, read);
    			}
    			out.flush();
    		} while (read >= 0);
    		System.out.println("input stream read done.");
    	}
    	
    	// chSftp.put(src, dst, new MyProgressMonitor(), ChannelSftp.OVERWRITE); // 代码段2
    	
    	// chSftp.put(new FileInputStream(src), dst, new MyProgressMonitor(), ChannelSftp.OVERWRITE); // 代码段3
    }
}