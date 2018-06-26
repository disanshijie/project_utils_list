package com.oracle.sun.core.html2Pdf.demo1.method.util;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.PrettyXmlSerializer;
import org.htmlcleaner.TagNode;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.BaseColor;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class PDFUtil {

	//public static String basePath = Util.getClassResources();
	//public static String basePath = "";
	public static String basePath = PDFUtil.class.getResource(".").getPath()+"../";
	/**
	 * 
	* @Title: htmlTemplate
	* @Description: htl模板 赋值 生成html
	* @author sun
	* @date  2018年5月28日 上午10:42:58
	* @param root
	* @param fltPath
	* @param fltName
	* @param inputFile
	* @throws IOException
	* @throws TemplateException
	 */
	public static void htmlTemplate(Map<String,Object> root,String fltPath,String fltName,String inputFile) throws IOException, TemplateException {
		Util.delFile(inputFile);
    	Util.mkFile(inputFile);
	    
    	//STEP1:使用freemarker 将模版页面生成要转的html页面  
        /* 创建配置 */  
        Configuration cfg = new Configuration();
        /* 指定模板存放的路径 */  
        cfg.setDirectoryForTemplateLoading(new File(basePath + fltPath));  //方式一：使用绝对路径设置模版路径  
        //cfg.setClassForTemplateLoading(this.getClass(), "template");//方式二：使用所在类的相对路径设置模版路径  方法非静态
        cfg.setDefaultEncoding("UTF-8");
        // cfg.setObjectWrapper(new DefaultObjectWrapper());  
  
        /* 从上面指定的模板目录中加载对应的模板文件 */  
        // contractTemplate  
        Template temp = cfg.getTemplate(fltName);   //获取或创建一个模版  
        /* 创建数据模型 */  
        // Map latest = new HashMap();  
        // root.put("latestProduct", latest);  
        // latest.put("name", "green mouse");  
        /* 将生成的内容写入hello .html中 */  
  
        /*String file1 = outPath + "admin/ftl/contract/contractTemplate.html"; */
        File file = new File(inputFile);
        System.out.println(file);
        
        // Writer out = new FileWriter(file);  
        Writer out = new BufferedWriter(new OutputStreamWriter(  
                new FileOutputStream(file), "utf-8"));
        // Writer out = new OutputStreamWriter(System.out);
        temp.process(root, out);
        out.flush();
	}
	/**
	 * 
	* @Title: createPDF
	* @Description: html文件转化为pdf文件
	* @author sun
	* @date  2018年5月28日 上午10:41:37
	* @param inputFile
	* @param outputFile
	* @throws IOException
	* @throws TemplateException
	 */
	public static void Html2PDF(File inputFile,String outputFile) throws IOException, TemplateException{
		if(!inputFile.exists()) {
			return ;
		}
		
		String fontPath=basePath+"resource/static/font/SIMSUN.TTC";
        
		//STEP2:使用itext将html页面转成PDF文件  参数 生成的html文件 inputFile
        String url = inputFile.toURI().toURL().toString();  
        //System.out.println(url);
        /*String outputFile = outPath + "admin/ftl/contract/contractTemplate.pdf"; */ 
        OutputStream os = new FileOutputStream(outputFile);
  
        ITextRenderer renderer = new ITextRenderer();
        // PDFEncryption pdfEncryption = new
        // PDFEncryption(null,null,PdfWriter.ALLOW_PRINTING);
        // renderer.setPDFEncryption(pdfEncryption); //只有打印权限的
        renderer.setDocument(url);
        // 解决中文问题  
        ITextFontResolver fontResolver = renderer.getFontResolver();
        try {  
            fontResolver.addFont(fontPath,BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED); 
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
  
        // 解决图片的相对路径问题，要加载的图片在html中
        //<img src='D:/xxxx.jpg'/>
        //renderer.getSharedContext().setBaseURL("file:/D:");
        //renderer.getSharedContext().setBaseURL("file:///D:");
        
        renderer.layout();  
        try {  
            renderer.createPDF(os);
            /*
            OutputStream outfile = new FileOutputStream(new File(outputFile));
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, outfile);
            writer.setEncryption(USER_PASS.getBytes(), OWNER_PASS.getBytes(),
                    PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_128);
            document.open();
            document.close();
            outfile.close();
            */
        } catch (DocumentException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        System.out.println("转换成功！");  
        os.close();  
	}
	/**
	 *  http://gaojunwei.iteye.com/blog/1996749
	 *  测试可以 有table时格式有问题
	* @Title: Html2PDF
	* @Description: 标准化html的内容 输出为pdf
	* @author sun
	* @date  2018年5月28日 上午10:56:47
	* @param str
	* @param outputFile
	* @throws IOException
	* @throws TemplateException
	 * @throws DocumentException 
	 */
	public static void Html2PDF(String str,String outputFile) throws IOException, TemplateException, DocumentException{
		String fontPath=basePath+"resource/static/font/SIMSUN.TTC";
		
		   // Create a buffer to hold the cleaned up HTML 将不严格的html编写方式转为严谨的html编码方式  
	     ByteArrayOutputStream out = new ByteArrayOutputStream();  
	       
	     // Clean up the HTML to be well formed  
	     HtmlCleaner cleaner = new HtmlCleaner();  
	     CleanerProperties props = cleaner.getProperties();  
	       
	     TagNode node = cleaner.clean(str);
	     // Instead of writing to System.out we now write to the ByteArray buffer  
	     new PrettyXmlSerializer(props).writeXmlToStream(node, out);  
	  
	     // Create the PDF
	     ITextRenderer renderer = new ITextRenderer();  
	     renderer.setDocumentFromString(new String(out.toByteArray()));
	    
	     //BaseFont fontResolver = renderer.getFontResolver();  
	     ITextFontResolver fontResolver = renderer.getFontResolver();   
	     //解决中文不显示问题  
	     //fontResolver.addFont("C:/Windows/Fonts/arialuni.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);    
	     fontResolver.addFont(fontPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);    
	     //设置资源路径 如图片样式文件  
	     //renderer.getSharedContext().setBaseURL("C:\\htmltopdf\\");  
	     renderer.layout();
	     //OutputStream outputStream = new FileOutputStream(".\\towork\\HTMLasPDF.pdf");  
	     OutputStream outputStream = new FileOutputStream(outputFile);  
	     renderer.createPDF(outputStream);
	  
	     // Finishing up  
	     renderer.finishPDF();  
	     out.flush();  
	     out.close();  
		
	}
	/**
     * https://blog.csdn.net/u012228718/article/details/40706499
     * https://blog.csdn.net/u012228718/article/details/40985577
     * 【功能描述：添加图片和文字水印】 【功能详细描述：功能详细描述】
     * @param srcFile 待加水印文件
     * @param destFile 加水印后存放地址
     * @param text 加水印的文本内容
     * @param textWidth 文字横坐标
     * @param textHeight 文字纵坐标
     * @throws Exception
     * 
     */
    public static void addWaterMark(String srcFile, String destFile, String text, int textWidth, int textHeight) throws Exception {
        // 待加水印的文件
        PdfReader reader = new PdfReader(srcFile);
        // 加完水印的文件
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(destFile));
        int total = reader.getNumberOfPages() + 1;
        PdfContentByte content;
        // 设置字体
        BaseFont font = BaseFont.createFont();
        // 循环对每页插入水印
        for (int i = 1; i < total; i++) {
            // 水印的起始
            content = stamper.getUnderContent(i);
            // 开始
            content.beginText();
            // 设置颜色 默认为蓝色
            //content.setColorFill(BaseColor.BLUE);
             content.setColorFill(Color.GRAY);
            // 设置字体及字号
            content.setFontAndSize(font, 38);
            // 设置起始位置
            // content.setTextMatrix(400, 880);
            content.setTextMatrix(textWidth, textHeight);
            // 开始写入水印
            content.showTextAligned(Element.ALIGN_LEFT, text, textWidth,textHeight, 45); //倾斜45
            /*
            //多个水印
            for(int m = 0 ; m < 100; m ++){
                for(int n = 0 ; n < 20 ; n ++ ){
                	content.showTextAligned(Element.ALIGN_CENTER, phrase,pageWidth * 0.2f * n, pageHeight * 0.08f * m, 45);
                }
            }
            */
            content.endText();
        }
        stamper.close();
    }
    /**
     * https://www.cnblogs.com/dreamzhiya/p/5171326.html
     * 【功能描述：添加图片和文字水印】 【功能详细描述：功能详细描述】
     * @param InPdfFile 待加水印文件
     * @param outPdfFile 加水印后存放地址
     * @param markImagePath 加水印的图片地址
     * @param xCoord 图片横坐标
     * @param yCoord 图片纵坐标
     * @throws Exception
     * 
     */
	public static void addPdfMark(String InPdfFile, String outPdfFile, String markImagePath,int xCoord,int yCoord) throws Exception {
        
		//String markImagePath=basePath+"resource/static/img/icon_021_hover.png";
		PdfReader reader = new PdfReader(InPdfFile, "PDF".getBytes());
        PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(outPdfFile));
        
        Image img = Image.getInstance(basePath+markImagePath);// 插入水印
        
        img.setAbsolutePosition(xCoord, yCoord);
        
        int total = reader.getNumberOfPages() + 1;
        if (img != null) {
        for(int i = 1; i <= total; i++) {
            PdfContentByte content = stamp.getUnderContent(i);
            content.addImage(img);
            //多个水印
            /*
            for(int m = 0 ; m < 100; m ++){
                for(int n = 0 ; n < 4 ; n ++ ){
                	content.addImage(pageWidth * 0.3f * n , pageHeight * 0.05f * m);
                }
            }
            */
            /*
            PdfGState gs = new PdfGState();
            gs.setFillOpacity(0.8f);// 设置透明度为0.2
            gs.setStrokeOpacity(0.8f);
            gs.setOverPrintStroking(true);
            content.setGState(gs);
            */
        }
        
        stamp.close();// 关闭
        }
	}
}
