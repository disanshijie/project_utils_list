package com.oracle.sun.design.general.one;

import java.util.Random;

public class RandomNum {
	  private static char ch[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
		      'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b',
		      'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
		      'x', 'y', 'z', '0', '1' };//最后又重复两个0和1，因为需要凑足数组长度为64
		  
		  public static final char MAJUSCULE[]= {'A', 'B', 'C', 'D', 'E', 'F', 'G',
			      'H', 'J', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'}; //大写字母

		  private static Random random = new Random();

		  //生成指定长度的随机字符串
		  public static synchronized String createRandomString(char[] base,int length) {
			  Random random = new Random();
		        StringBuffer sb = new StringBuffer();  
		        for (int i = 0; i < length; i++) {  
		            int number = random.nextInt(base.length);  
		            sb.append(base[number]);
		        }  
		        return sb.toString();  
		  }
		 
		}
