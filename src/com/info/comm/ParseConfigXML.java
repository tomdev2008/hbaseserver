package com.info.comm;

//该类用于读取配置文件
import java.io.*;
import java.util.*;

import org.jdom.*;
import org.jdom.input.*;

public class ParseConfigXML {
	private static SAXBuilder builder = new SAXBuilder();
	private static Document doc = null;
	public static HashMap<String, String> hashMap = new HashMap<String, String>();

	private static void init() throws Exception {
		if (doc == null) {
			try {
				// String config = System.getProperty("config");
				// doc = builder.build(new File(config));
				doc = builder.build(new File(
						"./config/config.xml"));
				
				
			} catch (Exception e) {
				throw new Exception(e);
			}
		}
	}

	public static HashMap<String, String> getXmlConfig() throws Exception {
		try {
			init();
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new Exception(e1);
		}
		@SuppressWarnings("unchecked")
		List<Element> ls = doc.getRootElement().getChildren("Parameter");
		Element e;
		String name;
		String value;
		for (Iterator<Element> i = ls.iterator(); i.hasNext();) {
			e = i.next();
			name = e.getAttributeValue("name");
			value = e.getText();
			hashMap.put(name, value);
		}
		return hashMap;
	}

}
