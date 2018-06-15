package com.oracle.sun.design.sftp.design;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpATTRS;
import com.oracle.sun.design.sftp.design.impl.FileProgressMonitor;
/**
 * 
 * @url
 * @Description	从Linux上下载到本地
 * @author admin
 * @date 2018年6月11日
 * @version V1.0
 * @说明
 */
public class SFTPTestGet {

    public SFTPChannel getSFTPChannel() {
        return new SFTPChannel();
    }

    public static void main(String[] args) throws Exception {
    	SFTPTestGet test = new SFTPTestGet();
    	/*
        Map<String, String> sftpDetails = new HashMap<String, String>();
        // 设置主机ip，端口，用户名，密码
        sftpDetails.put(SFTPConstants.SFTP_REQ_HOST, "10.9.167.55");
        sftpDetails.put(SFTPConstants.SFTP_REQ_USERNAME, "root");
        sftpDetails.put(SFTPConstants.SFTP_REQ_PASSWORD, "arthur");
        sftpDetails.put(SFTPConstants.SFTP_REQ_PORT, "22");
        */
        SFTPChannel channel = test.getSFTPChannel();
        ChannelSftp chSftp = channel.getChannel(null, 60000);
        
        String filename = "/usr/games/比赛默认图.zip";
        SftpATTRS attr = chSftp.stat(filename);
        long fileSize = attr.getSize();
        
        String dst = "D:\\opt\\比赛默认图.zip";
        OutputStream out = new FileOutputStream(dst);
        try {
            /*
             * 代码段1：直接将目标服务器上文件名为src的文件下载到本地，本地文件名为dst。
             * （注：使用这个方法时，dst可以是目录，若dst为目录，则下载到本地的文件名将与src文件名相同）
             */
            //chSftp.get(filename, dst, new FileProgressMonitor(fileSize)); // 代码段1
            /*
             * 代码段2：将目标服务器上文件名为src的文件下载到本地的一个输出流对象，该输出流为一个文件输出流
             */
            // chSftp.get(filename, out, new FileProgressMonitor(fileSize)); // 代码段2
            
            /**
             * 代码段3：采用读取get方法返回的输入流数据的方式来下载文件。
             * 这个示例中，将读取的数据写入到了本地的一个文件中。采用这种方式，可以由应用程序设定每次读取输入流的数据块大小，也就是每次传输的数据块大小
             * 
             */
            InputStream is = chSftp.get(filename, new FileProgressMonitor(fileSize));
            byte[] buff = new byte[1024 * 2];
            int read;
            if (is != null) {
                System.out.println("Start to read input stream");
                do {
                    read = is.read(buff, 0, buff.length);
                    if (read > 0) {
                        out.write(buff, 0, read);
                    }
                    out.flush();
                } while (read >= 0);
                System.out.println("input stream read done.");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            chSftp.quit();
            channel.closeChannel();
        }
    }
}