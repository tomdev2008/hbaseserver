package com.info.comm;
//用于生成给客户端的返回查询报文
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

public class ResponseQueryMessage {
	private List<String> fruit=new ArrayList<String>();
	public void initList(){
		fruit.add("Header");
		fruit.add("body");
	}
	public byte[] BuildXML(InsertMessageBean imb){
		initList();
		byte[] buff = null;
		Element root=new Element("Envelope");
		Document doc=new Document(root);
		for(int i=0;i<fruit.size();i++){
			Element elements=new Element(fruit.get(i));
			if(i==0){
				elements.addContent(new Element("msgRef").setText("10000"));
				elements.addContent(new Element("channel").setText("渠道号"));
				elements.addContent(new Element("fromNodeId").setText("AB-00011"));
				elements.addContent(new Element("toServiceCode").setText("核心交易码"));
				elements.addContent(new Element("externalReferenceNo").setText("交易流水号"));
				elements.addContent(new Element("replyCode").setText("响应码"));
				elements.addContent(new Element("replyText").setText("响应描述"));
			}else if(i==1){
				Element e=new Element("response");
				e.addContent(new Element("CIFTYPE").setText(imb.getCIFTYPE()));
				e.addContent(new Element("NAM").setText(imb.getNAM()));
				e.addContent(new Element("ZCHNM").setText(imb.getZCHNM()));
				e.addContent(new Element("ZHOVSFLG").setText(imb.getZHOVSFLG()));
				e.addContent(new Element("OIT").setText(imb.getOIT()));
				e.addContent(new Element("OTN").setText(imb.getOTN()));
				e.addContent(new Element("BCHFLG").setText(imb.getBCHFLG()));
				e.addContent(new Element("OED").setText(imb.getOED()));
				e.addContent(new Element("RESCD").setText(imb.getRESCD()));
				e.addContent(new Element("CON").setText(imb.getCON()));
				e.addContent(new Element("ZLRIDTYPE").setText(imb.getZLRIDTYPE()));
				e.addContent(new Element("ZLRIDCODE").setText(imb.getZLRIDCODE()));
				e.addContent(new Element("ZLRIDINVDTE").setText(imb.getZLRIDINVDTE()));
				e.addContent(new Element("APH").setText(imb.getAPH()));
				e.addContent(new Element("HPH").setText(imb.getHPH()));
				e.addContent(new Element("PZIP").setText(imb.getPZIP()));
				e.addContent(new Element("MAD1").setText(imb.getMAD1()));
				e.addContent(new Element("CIFOFF").setText(imb.getCIFOFF()));
				e.addContent(new Element("NATION").setText(imb.getNATION()));
				e.addContent(new Element("PRSTATE").setText(imb.getPRSTAGE()));
				e.addContent(new Element("ZREGION").setText(imb.getZREGION()));
				e.addContent(new Element("EMPCITY").setText(imb.getEMPCITY()));
				e.addContent(new Element("CCODE").setText(imb.getCCODE()));
				e.addContent(new Element("ZCCODE").setText(imb.getZCCODE()));
				e.addContent(new Element("EMAIL").setText(imb.getEMAIL()));
				elements.addContent(e);
			}
			root.addContent(elements);
		}
		XMLOutputter XMLOut=new XMLOutputter(FormatXML());
		ByteArrayOutputStream bout=new ByteArrayOutputStream();
		try {
			XMLOut.output(doc, new FileOutputStream("d:\\request.xml"));
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
	//	ResponseQueryMessage writer=new ResponseQueryMessage();
		/*byte[] b=writer.BuildXML();
		System.out.println(new String(b));*/
	}

}
