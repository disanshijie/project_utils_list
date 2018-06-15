package com.oracle.sun.design.sftp.design.impl;

import com.jcraft.jsch.SftpProgressMonitor;

/**
 * @url
 * @Description 
 * @author admin
 * @date 2018年6月8日
 * @version V1.0
 * @说明
 */
public class MyProgressMonitor implements SftpProgressMonitor {
    private long transfered;

    /**
     * 当每次传输了一个数据块后，调用count方法，count方法的参数为这一次传输的数据块大小。
     */
    public boolean count(long count) {
        transfered = transfered + count;
        System.out.println("Currently transferred total size: " + transfered + " bytes");
        return true;
    }

    /**
     * 当传输结束时，调用end方法。
     */
    public void end() {
        System.out.println("Transferring done.");
    }

    /**
     * 当文件开始传输时，调用init方法。
     */
    public void init(int op, String src, String dest, long max) {
        System.out.println("Transferring begin.");
    }
}
