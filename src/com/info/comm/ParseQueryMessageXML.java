package com.info.comm;
//解析服务器发过来的查询报文
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class ParseQueryMessageXML {
	public QueryMessageBean readStringXml(String xml) {
		Document doc = null;
		QueryMessageBean clientComdata = new QueryMessageBean();
		try {
			doc = DocumentHelper.parseText(xml);
			Element rootElt = doc.getRootElement(); // 获取根节点
			@SuppressWarnings("unchecked")
			Iterator<Element> iter = rootElt.elementIterator("Header"); // 获取根节点下的子节点head
			while (iter.hasNext()) {
				Element recordEle = (Element) iter.next();
				String msgRef = recordEle.elementTextTrim("msgRef"); // 拿到head节点下的子节点msgRef值
				clientComdata.setMsgRef(msgRef);
				String channel = recordEle.elementTextTrim("channel");
				clientComdata.setChannel(channel);
				String fromNodeId = recordEle.elementTextTrim("fromNodeId");
				clientComdata.setFromNodeId(fromNodeId);
				String toServiceCode = recordEle
						.elementTextTrim("toServiceCode");
				clientComdata.setToServiceCode(toServiceCode);
				String externalReferenceNo = recordEle
						.elementTextTrim("externalReferenceNo");
				clientComdata.setExternalReferenceNo(externalReferenceNo);
				String replyCode = recordEle.elementTextTrim("replyCode");
				clientComdata.setReplyCode(replyCode);
				String replyText = recordEle.elementTextTrim("replyText");
				clientComdata.setReplyText(replyText);
			}

			@SuppressWarnings("unchecked")
			Iterator<Element> iterss = rootElt.elementIterator("body"); // /获取根节点下的子节点body
			while (iterss.hasNext()) {
				Element recordEless = (Element) iterss.next();
				@SuppressWarnings("unchecked")
				Iterator<Element> itersElIterator = recordEless
						.elementIterator("request"); // 获取子节点body下的子节点request
				while (itersElIterator.hasNext()) {
					Element itemEle = (Element) itersElIterator.next();
/*					Iterator<Element> it=itemEle.elementIterator("ACN");
					while(it.hasNext()){
					Element i=(Element)it.next();
					String name=i.getName();
					String value=i.getText();
					System.out.println(name+"  "+value);
					}*/
					String clientNo= itemEle.elementTextTrim("ACN"); // 拿到body下的子节点request下的字节点ACN的值
					clientComdata.setClientNo(clientNo);
					String oitNumber=itemEle.elementTextTrim("OIT");
					clientComdata.setOitNumber(oitNumber);
					String name=itemEle.elementTextTrim("NAM");
					clientComdata.setName(name);
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return clientComdata;
	}
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
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
		ParseQueryMessageXML xml = new ParseQueryMessageXML();
		QueryMessageBean trm = new QueryMessageBean();
		trm = xml.readStringXml(xmlStr);
	//	Method[] method=TransactionResponseMessage.class.getDeclaredMethods();
		System.out.println(trm.getClientNo());
		/*String str="get"+"EMAIL";
		for(int i=0;i<method.length;i++){
			if(str.equals(method[i].getName())){
				System.out.println((String)method[i].invoke(trm));
			}
		}*/
	}
}

