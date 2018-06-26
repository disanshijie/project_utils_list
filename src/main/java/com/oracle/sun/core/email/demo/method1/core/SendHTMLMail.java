package com.oracle.sun.core.email.demo.method1.core;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * 类描述：示例如何根据html模板发送html邮件
 * @author xiezd
 *
 */
public class SendHTMLMail {
	public final static String TO_MAIL = "18333615368@163.com"; // 接收方邮箱地址  
	
	public static void main(String[] args) {
		SendHTMLMail st=new SendHTMLMail();
		st.send();
	}
	
    public void send(){
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            //获取模板html文档
            document = reader.read(SendHTMLMail.class.getResource("../design/project3/pageTemplet.html").getPath());
            Element root = document.getRootElement();
            //分别获取id为name、message、time的节点。
            Element name = getNodes(root,"id","name");
            Element message = getNodes(root,"id","message");
            Element time = getNodes(root, "id", "time");

            //设置收件人姓名，通知信息、当前时间
            Calendar calendar = Calendar.getInstance();
            time.setText(calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DATE));
            name.setText("小明");
            //随便写的
            message.setText("因为您、我有缘，所以才能共结一个圆。为进一步优化教育环境，"
                    + "加强家校互动，共同促进学生的成长和进步，本着家校共育的精神，我校决定召开高一家长会，"
                    + "希望你在百忙中抽出时间，拨冗光临，对孩子在家和在校的表现与各班主任进行深入的面对面交流，"
                    + "针对每个孩子的不同特点，与老师共同商讨教育孩子的策略，最大程度的促进您孩子的进步。");

            //保存到临时文件
            FileWriter fwriter = new FileWriter("d:/temp.html");
            XMLWriter writer = new XMLWriter(fwriter);
            writer.write(document);
            writer.flush();
            //读取临时文件，并把html数据写入到字符串str中，通过邮箱工具发送
            FileReader in = new FileReader("d:/temp.html");
            char[] buff = new char[1024*10];
            in.read(buff);
            String str = new String(buff);
            System.out.println(str.toString());
            
            //发送
            MailInfo info=new MailInfo();
            info.setContent(str);
            SendEmail.sendMail(info);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     /**
     * 方法描述：递归遍历子节点，根据属性名和属性值，找到对应属性名和属性值的那个子孙节点。
     * @param node 要进行子节点遍历的节点
     * @param attrName 属性名
     * @param attrValue 属性值
     * @return 返回对应的节点或null
     */
    public Element getNodes(Element node, String attrName, String attrValue) {  

            final List<Attribute> listAttr = node.attributes();// 当前节点的所有属性  
            for (final Attribute attr : listAttr) {// 遍历当前节点的所有属性  
                final String name = attr.getName();// 属性名称  
                final String value = attr.getValue();// 属性的值  
                System.out.println("属性名称：" + name + "---->属性值：" + value);
                if(attrName.equals(name) && attrValue.equals(value)){
                    return node;
                }
            }  
            // 递归遍历当前节点所有的子节点  
            final List<Element> listElement = node.elements();// 所有一级子节点的list  
            for (Element e : listElement) {// 遍历所有一级子节点  
                Element temp = getNodes(e,attrName,attrValue);
                // 递归
                if(temp != null){
                    return temp;
                };  
            }  

            return null;
        }  

}
