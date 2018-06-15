package com.oracle.sun.design.sftp.design;
/**
 * @url
 * @Description 没有用 哈哈哈
 * @author admin
 * @date 2018年6月8日
 * @version V1.0
 * @说明
 */
public interface SftpProgressMonitor{

  public static final int PUT=0;
  public static final int GET=1;
  void init(int op, String src, String dest, long max);
  boolean count(long count);
  void end();
  
}
