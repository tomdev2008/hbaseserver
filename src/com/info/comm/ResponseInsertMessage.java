package com.info.comm;
//用于生成给客户端的返回插入报文
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class ResponseInsertMessage {
	private List<String> fruit=new ArrayList<String>();
	public void initList(){
		fruit.add("Header");
		fruit.add("body");
	}
	public byte[] BuildXML(String clientNo,String oitNumber,String name){
		initList();
		byte[] buff = null;
		Element root=new Element("Envelope");
		Document doc=new Document(root);
		for(int i=0;i<fruit.size();i++){
			Element elements=new Element(fruit.get(i));
			if(i==0){
				elements.addContent(new Element("msgRef").setText("报文参考号"));
				elements.addContent(new Element("channel").setText("渠道号"));
				elements.addContent(new Element("fromNodeId").setText("AB-00011"));
				elements.addContent(new Element("toServiceCode").setText("核心交易码"));
				elements.addContent(new Element("externalReferenceNo").setText("交易流水号"));
				elements.addContent(new Element("replyCode").setText("响应码"));
				elements.addContent(new Element("replyText").setText("响应描述"));

			}else if(i==1){
				Element e=new Element("response");
				e.addContent(new Element("ACN").setText(clientNo));  //客户号
				e.addContent(new Element("OIT").setText(oitNumber));  //身份证号
				e.addContent(new Element("NAM").setText(name));   //客户名
				elements.addContent(e);
			}
			root.addContent(elements);
		}
		XMLOutputter XMLOut=new XMLOutputter(FormatXML());
		ByteArrayOutputStream bout=new ByteArrayOutputStream();
		try {
			XMLOut.output(doc, new FileOutputStream("d:\\response.xml"));
			XMLOut.output(doc, bout);
			buff=bout.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buff;
	}
	public Format FormatXML(){
		Format format=Format.getCompactFormat();
		format.setEncoding("utf-8");
		format.setIndent(" ");
		return format;
	}
	public static void main(String args[]) throws IOException{
		ResponseInsertMessage writer=new ResponseInsertMessage();
		writer.BuildXML("11111","default","default");
	}

}
