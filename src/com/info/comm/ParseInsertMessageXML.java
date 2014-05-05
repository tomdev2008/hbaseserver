package com.info.comm;
//解析客户端发过来的插入数据库报文
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class ParseInsertMessageXML {
	public InsertMessageBean readStringXml(String xml) {
		Document doc = null;
		InsertMessageBean clientComdata = new InsertMessageBean();
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
					String CIFTYPE = itemEle.elementTextTrim("CIFTYPE"); // 拿到body下的子节点request下的字节点CIFTYPE的值
					clientComdata.setCIFTYPE(CIFTYPE);
					String NAM = itemEle.elementTextTrim("NAM");
					clientComdata.setNAM(NAM);
					String ZCHNM = itemEle.elementTextTrim("ZCHNM");
					clientComdata.setZCHNM(ZCHNM);
					String ZHOVSFLG = itemEle.elementTextTrim("ZHOVSFLG");
					clientComdata.setZHOVSFLG(ZHOVSFLG);
					String OIT = itemEle.elementTextTrim("OIT");
					clientComdata.setOIT(OIT);
					String OTN = itemEle.elementTextTrim("OTN");
					clientComdata.setOTN(OTN);
					String BCHFLG = itemEle.elementTextTrim("BCHFLG");
					clientComdata.setBCHFLG(BCHFLG);
					String OED = itemEle.elementTextTrim("OED");
					clientComdata.setOED(OED);
					String RESCD = itemEle.elementTextTrim("RESCD");
					clientComdata.setRESCD(RESCD);
					String CON = itemEle.elementTextTrim("CON");
					clientComdata.setCON(CON);
					String ZLRIDTYPE = itemEle.elementTextTrim("ZLRIDTYPE");
					clientComdata.setZLRIDTYPE(ZLRIDTYPE);
					String ZLRIDCODE = itemEle.elementTextTrim("ZLRIDCODE");
					clientComdata.setZLRIDCODE(ZLRIDCODE);
					String ZLRIDINVDTE = itemEle.elementTextTrim("ZLRIDINVDTE");
					clientComdata.setZLRIDINVDTE(ZLRIDINVDTE);
					String APH = itemEle.elementTextTrim("APH");
					clientComdata.setAPH(APH);
					String HPH = itemEle.elementTextTrim("HPH");
					clientComdata.setHPH(HPH);
					String PZIP = itemEle.elementTextTrim("PZIP");
					clientComdata.setPZIP(PZIP);
					String MAD1 = itemEle.elementTextTrim("MAD1");
					clientComdata.setMAD1(MAD1);
					String CIFOFF = itemEle.elementTextTrim("CIFOFF");
					clientComdata.setCIFOFF(CIFOFF);
					String NATION = itemEle.elementTextTrim("NATION");
					clientComdata.setNATION(NATION);
					String PRSTATE = itemEle.elementTextTrim("PRSTATE");
					clientComdata.setPRSTAGE(PRSTATE);
					String ZREGION = itemEle.elementTextTrim("ZREGION");
					clientComdata.setZREGION(ZREGION);
					String EMPCITY = itemEle.elementTextTrim("EMPCITY");
					clientComdata.setEMPCITY(EMPCITY);
					String CCODE = itemEle.elementTextTrim("CCODE");
					clientComdata.setCCODE(CCODE);
					String ZCCODE = itemEle.elementTextTrim("ZCCODE");
					clientComdata.setZCCODE(ZCCODE);
					String EMAIL = itemEle.elementTextTrim("EMAIL");
					clientComdata.setEMAIL(EMAIL);
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
//		String xmlString = "<?xml version=\"1.0\" encoding=\"gbk\"?><Envelope>"
//				+ "<Header>"
//				+ "<msgRef>dom4j解析一个例子</msgRef>"
//				+ "<channel>yangrong</channel>"
//				+ "<fromNodeId>123456</fromNodeId>"
//				+ "</Header>"
//				+ "<body><request><CIFTYPE>111</CIFTYPE><EMAIL>123@qq.com</EMAIL></request></body></Envelope>";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<Envelope><Header>" + "<msgRef>报文参考号</msgRef>"
				+ "<channel>渠道号</channel>"
				+ "<fromNodeId>AB-00011</fromNodeId>"
				+ "<toServiceCode>核心交易码</toServiceCode>"
				+ "<externalReferenceNo>交易流水号</externalReferenceNo>"
				+ "<replyCode>响应码</replyCode>" + "<replyText>响应描述</replyText>"
				+ "</Header>" + "<body>" + "<request>"
				+ "<CIFTYPE>个人</CIFTYPE>" + "<NAM>Li Jun</NAM>"
				+ "<ZCHNM>李俊</ZCHNM>" + "<ZHOVSFLG>0</ZHOVSFLG>"
				+ "<OIT>证件类型</OIT>" + "<OTN>360512188902041566</OTN>"
				+ "<BCHFLG>批量建立标志</BCHFLG>" + "<OED>证件失效日期</OED>"
				+ "<RESCD>居民状态</RESCD>" + "<CON>法人代表名称</CON>"
				+ "<ZLRIDTYPE>法人代表证件类型</ZLRIDTYPE>"
				+ "<ZLRIDCODE>法人代表证件号码</ZLRIDCODE>"
				+ "<ZLRIDINVDTE>法人代表证件有效期</ZLRIDINVDTE>" + "<APH>移动手机</APH>"
				+ "<HPH>固定电话</HPH>" + "<PZIP>邮政编码</PZIP>" + "<MAD1>联系地址</MAD1>"
				+ "<CIFOFF>客户经理</CIFOFF>" + "<NATION>国籍/企业运营国</NATION>"
				+ "<PRSTATE>省/州</PRSTATE>" + "<ZREGION>城市</ZREGION>"
				+ "<EMPCITY>县/区</EMPCITY>" + "<CCODE>客户级别</CCODE>"
				+ "<ZCCODE>客户级别</ZCCODE>" + "<EMAIL>企业电子邮件</EMAIL>"
				+ "</request>" + "</body>" + "</Envelope>";
		System.out.println(xmlStr);
		ParseInsertMessageXML xml = new ParseInsertMessageXML();
		InsertMessageBean ccd = new InsertMessageBean();
		ccd = xml.readStringXml(xmlStr);
		Method[] method=InsertMessageBean.class.getDeclaredMethods();
		String str="get"+"EMAIL";
		for(int i=0;i<method.length;i++){
			if(str.equals(method[i].getName())){
				System.out.println((String)method[i].invoke(ccd));
			}
		}
	}
}

