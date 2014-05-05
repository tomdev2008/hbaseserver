package com.info.tools;
//该类实现了按客户号，身份证号，客户中文名查询的方法
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Result;

import com.info.comm.HBaseOperation;
import com.info.comm.InsertMessageBean;

public class QueryCIFInfo {
	public InsertMessageBean queryByClientNO(HBaseOperation hbase, String clientNo)
			throws IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		String tableName = "cif";
		Result rs = hbase.getOneRecord(tableName, clientNo);
		KeyValue[] kv = rs.raw();
		InsertMessageBean message=new InsertMessageBean();
		if (kv.length == 0) {
			System.out.println("cif table has no records");
			return null;
		} else {
			Method[] method=InsertMessageBean.class.getDeclaredMethods();
			for (int i = 0; i < kv.length; i++) {
				System.out.print(new String(kv[i].getRow()) + "  "
						+ new String(kv[i].getFamily()) + "  ");
				System.out.print(new String(kv[i].getQualifier()) + "  "
						+ new String(kv[i].getValue()));
				System.out.println();
				String str="set"+new String(kv[i].getQualifier()).toUpperCase();
				for(int j=0;j<method.length;j++){
					if(str.equals(method[j].getName())){
						method[j].invoke(message,new String(kv[i].getValue()));
					}
				}
			}
			return message;
		}
	}

	public InsertMessageBean queryByOitNumber(HBaseOperation hbase, String oitNumber)
			throws IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		String oitTableName = "oitcif";
		Result rs = hbase.getOneRecord(oitTableName, oitNumber);
		KeyValue[] kv = rs.raw();
		if (kv.length == 0) {
			System.out.println("oitcif table has no records");
			return null;
		} else {
			String clientNo = new String(kv[0].getValue());
			return queryByClientNO(hbase, clientNo);
		}
	}

	public List<InsertMessageBean> queryByName(HBaseOperation hbase, String name)
			throws IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		List<InsertMessageBean> messageList=new ArrayList<InsertMessageBean>();
		List<Result> list = hbase.getRecord("namcif", name);
		if (list.size() == 0) {
			System.out.println("namecif table has no records");
			return null;
		} else {
			for (int j = 0; j < list.size(); j++) {
				Result r = list.get(j);
				for (KeyValue kv : r.raw()) {
					messageList.add(queryByClientNO(hbase,new String(kv.getValue())));
				}
			}
			return messageList;
		}
	}
}
