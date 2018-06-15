package com.oracle.sun;

import java.io.File;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ){
    	
    	String des="pt/tocat-7.0/webapps\\51Cruise.txt";
    	
    	File file=new File(des);
    	
    	String parentPath=file.getParent();
    	String name=file.getName();
    	
    	System.out.println(parentPath);
    	System.out.println(name);
    	
    }
}
