package com.oracle.sun.design.email.design.project4;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.oracle.sun.design.email.util.Constant;

public class Test {

	public static void main(String[] args) throws FileNotFoundException {
		//MailUtilTest();
		MailUtilCopy1Test();
	}
	//测试通过
	public static void MailUtilTest() {
		String content="<a href='http://120.27.18.84:9090/index.do'>点击进入</a>";
		String fileName="";
		InputStream is=null;
		String ccList="";
		MailUtil mu=new MailUtil(Constant.SEND_HOST,Constant.FROM_MAIL,Constant.SEND_PWD);
		mu.sendMail("测试", Constant.TO_MAIL, content, fileName, is, ccList);

	}
	//测试通过
	public static void MailUtilTest2() throws FileNotFoundException {
		String content="<a href='http://120.27.18.84:9090/index.do'>点击进入</a>";
		String fileName="525FO确认单.xlsx";
		String path="D:/sunjinchao/data/WeChat/WeChat Files/wxid_aj2dr5redjio22/Files/【平安银行】华美欧确认单模板-525FO白立民海三.xlsx";
		InputStream is = new BufferedInputStream(new FileInputStream(path));
		//InputStream in=Test.class.getResourceAsStream("");
		String ccList="";
		MailUtil mu=new MailUtil(Constant.SEND_HOST,Constant.FROM_MAIL,Constant.SEND_PWD);
		mu.sendMail("测试", Constant.TO_MAIL, content, fileName, is, ccList);
		
	}
	public static void MailUtilCopy1Test() throws FileNotFoundException {
		String content="<a href='http://120.27.18.84:9090/index.do'>点击进入</a>";
		List<File> files=new ArrayList<File>();
		String path="D:/测试文件";
		File fs=new File(path);
		for (File file : fs.listFiles()) {
			if(file.isFile()) {
				files.add(file);
			}
		}
		//InputStream in=Test.class.getResourceAsStream("");
		String ccList="";
		MailUtilCopy1 mu=new MailUtilCopy1(Constant.SEND_HOST,Constant.FROM_MAIL,Constant.SEND_PWD);
		mu.sendMail("测试", Constant.TO_MAIL, content, files, ccList);
	}
}
