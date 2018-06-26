package com.oracle.sun.design.email.util;

import java.util.Date;

public class Constant {
	public final static String MAIL = "15314537570@163.com"; // 邮箱格式  
	public final static String SUBJECT = "通知邮件";  
	
	public final static String SEND_HOST = "smtp.163.com"; // 邮箱发送服务器  
	public final static String ACCEPT_HOST = "pop.163.com"; // 邮箱服务器  
	public final static String SEND_USER = "15314537570"; // 用户名  
	public final static String SEND_PWD = "qwe123asd456"; // 密码  
	public final static String FROM_MAIL = "15314537570@163.com";// 发送方邮箱地址  
	
	public final static String TO_MAIL = "18333615368@163.com"; // 接收方邮箱地址  
	public final static String CC_MAIL = SEND_USER + MAIL; // 抄送方邮箱地址  
	public final static String BCC_MAIl = SEND_USER + MAIL; // 密送方邮箱地址  
	
	public final static String REPLAY_MAIL = "2245864259@qq.com"; // 回复接受邮箱地址  
	
	  
	public final static String ENCODE = "UTF-8";  
	public static Date date = new Date();  
}
