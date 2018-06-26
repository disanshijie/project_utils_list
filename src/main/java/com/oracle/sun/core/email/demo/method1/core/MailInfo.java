package com.oracle.sun.core.email.demo.method1.core;

import java.io.File;
import java.util.List;

import com.oracle.sun.core.email.util.Constant;

/**
 * Created by 草帽boy on 2016/12/8.
 */

public class MailInfo {
    //邮箱服务器 如smtp.163.com
	
    private String acceptHost=Constant.ACCEPT_HOST ;
    private String sendHost=Constant.SEND_HOST ;
    //发件人地址
    private String fromAddress=Constant.FROM_MAIL;
    //发件账户密码
    private String fromCount = Constant.SEND_USER;
    //用户授权码 不是用户名密码 可以自行查看相关邮件服务器怎么查看
    private String fromPassword = Constant.SEND_PWD;
    //头 标题
    private String toHead="";
    //发送主题
    private String subject = Constant.SUBJECT;
    //发送内容
    private String content ;
    //发送附件
    private List<File> files;
    
    //发送地址
    private String toAddress=Constant.TO_MAIL;
   
    //抄送人
    private String ccList ;
    //消息回复邮箱
    private String replayAddress= Constant.REPLAY_MAIL;
    
    private Boolean debug = true;

    public String getReplayAddress() {
        return replayAddress;
    }
    public void setReplayAddress(String replayAddress) {
        this.replayAddress = replayAddress;
    }
    public String getToAddress() {
        return toAddress;
    }
    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
	public Boolean getDebug() {
		return debug;
	}
	public void setDebug(Boolean debug) {
		this.debug = debug;
	}
	public String getAcceptHost() {
		return acceptHost;
	}
	public void setAcceptHost(String acceptHost) {
		this.acceptHost = acceptHost;
	}
	public String getSendHost() {
		return sendHost;
	}
	public void setSendHost(String sendHost) {
		this.sendHost = sendHost;
	}
	public String getFromAddress() {
		return fromAddress;
	}
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	public String getFromCount() {
		return fromCount;
	}
	public void setFromCount(String fromCount) {
		this.fromCount = fromCount;
	}
	public String getFromPassword() {
		return fromPassword;
	}
	public void setFromPassword(String fromPassword) {
		this.fromPassword = fromPassword;
	}
	public String getCcList() {
		return ccList;
	}
	public void setCcList(String ccList) {
		this.ccList = ccList;
	}
	public List<File> getFiles() {
		return files;
	}
	public void setFiles(List<File> files) {
		this.files = files;
	}
	public String getToHead() {
		return toHead;
	}
	public void setToHead(String toHead) {
		this.toHead = toHead;
	}
	
}