package com.oracle.sun.design.baiduORC.project2;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.json.JSONObject;

import com.baidu.aip.ocr.AipOcr;

public class Sample {
    //设置APPID/AK/SK
    public static final String APP_ID = "11523928";//"你的 App ID";
    public static final String API_KEY ="0NuwOTHHIOOvQh6N1EG5x1cA"; //"你的 Api Key";
    public static final String SECRET_KEY = "BkykLk2M3wNoNZcL11KzL5YjLe7PKufN";//"你的 Secret Key";

    public static AipOcr client =null;
    
    public static void main(String[] args) {
    	 String path = "D:/sunjinchao/项目/草稿/757110826352428027.jpg";
    	 
        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
       // client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
       // client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
       // System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

        // 调用接口
        //JSONObject res = client.basicGeneral(path, new HashMap<String, String>());
        JSONObject res =client.passport(File2byte(new File(path)), new HashMap<String, String>());
        
        System.out.println(res.toString(2));
        
    }
    
	public static byte[] File2byte(File file) {
		byte[] buffer = null;
		try {
			//File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}

    /*
    public void sample(AipOcr client) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("language_type", "CHN_ENG");
        options.put("detect_direction", "true");
        options.put("detect_language", "true");
        options.put("probability", "true");
        
        
        // 参数为本地图片路径
        String image = "test.jpg";
        JSONObject res = client.basicGeneral(image, options);
        System.out.println(res.toString(2));

        // 参数为本地图片二进制数组
        byte[] file = readImageFile(image);
        res = client.basicGeneral(file, options);
        System.out.println(res.toString(2));

        
        // 通用文字识别, 图片参数为远程url图片
        JSONObject res = client.basicGeneralUrl(url, options);
        System.out.println(res.toString(2));

    }
    */
}
