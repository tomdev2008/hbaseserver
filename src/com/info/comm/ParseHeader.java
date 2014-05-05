package com.info.comm;
//解析报文的头部
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class ParseHeader {
	public HeaderBean readStringXml(String xml) {
		Document doc = null;
		HeaderBean header = new HeaderBean();
		try {
			doc = DocumentHelper.parseText(xml);
			Element rootElt = doc.getRootElement(); // 获取根节点
			@SuppressWarnings("unchecked")
			Iterator<Element> iter = rootElt.elementIterator("Header"); // 获取根节点下的子节点head
			while (iter.hasNext()) {
				Element recordEle = (Element) iter.next();
				String msgRef = recordEle.elementTextTrim("msgRef"); // 拿到head节点下的子节点msgRef值
				header.setMsgRef(msgRef);
				String channel = recordEle.elementTextTrim("channel");
				header.setChannel(channel);
				String fromNodeId = recordEle.elementTextTrim("fromNodeId");
				header.setFromNodeId(fromNodeId);
				String toServiceCode = recordEle
						.elementTextTrim("toServiceCode");
				header.setToServiceCode(toServiceCode);
				String externalReferenceNo = recordEle
						.elementTextTrim("externalReferenceNo");
				header.setExternalReferenceNo(externalReferenceNo);
				String replyCode = recordEle.elementTextTrim("replyCode");
				header.setReplyCode(replyCode);
				String replyText = recordEle.elementTextTrim("replyText");
				header.setReplyText(replyText);
			}
		}catch (DocumentException e) {
			e.printStackTrace();
		}
		return header;
	}
	public static void main(String args[]){
		String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<Envelope><Header>" + "<msgRef>报文参考号</msgRef>"
				+ "<channel>渠道号</channel>"
				+ "<fromNodeId>AB-00011</fromNodeId>"
				+ "<toServiceCode>核心交易码</toServiceCode>"
				+ "<externalReferenceNo>交易流水号</externalReferenceNo>"
				+ "<replyCode>响应码</replyCode>" + "<replyText>响应描述</replyText>"
				+ "</Header>" + "<body>" + "<request>"
				+ "<ACN>dddd</ACN>"+"</request>"+"</body>" + "</Envelope>";
		System.out.println(xmlStr);
		ParseHeader parseHeader = new ParseHeader();
		HeaderBean header = new HeaderBean();
		header = parseHeader.readStringXml(xmlStr);
		System.out.println(header.getMsgRef());
	}
}
