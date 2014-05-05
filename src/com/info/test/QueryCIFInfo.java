package com.info.test;
//该类实现了按客户号，身份证号，客户中文名查询的方法
import java.io.IOException;
import java.util.List;

import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Result;

import com.info.comm.HBaseOperation;

public class QueryCIFInfo {
	public void queryByClientNO(HBaseOperation hbase, String clientNo)
			throws IOException {
		String tableName = "cif";
		Result rs = hbase.getOneRecord(tableName, clientNo);
		KeyValue[] kv = rs.raw();
		if (kv.length == 0) {
			System.out.println("cif table has no records");
		} else {
			for (int i = 0; i < kv.length; i++) {
				System.out.print(new String(kv[i].getRow()) + "  "
						+ new String(kv[i].getFamily()) + "  ");
				System.out.print(new String(kv[i].getQualifier()) + "  "
						+ new String(kv[i].getValue()));
				System.out.println();
			}
		}
	}

	public void queryByOitNumber(HBaseOperation hbase, String oitNumber)
			throws IOException {
		String oitTableName = "oitcif";
		Result rs = hbase.getOneRecord(oitTableName, oitNumber);
		KeyValue[] kv = rs.raw();
		if (kv.length == 0) {
			System.out.println("oitcif table has no records");
		} else {
			String clientNo = new String(kv[0].getValue());
			queryByClientNO(hbase, clientNo);
		}
	}

	public void queryByName(HBaseOperation hbase, String name)
			throws IOException {
		List<Result> list = hbase.getRecord("namcif", name);
		if (list.size() == 0) {
			System.out.println("namecif table has no records");
		} else {
			for (int j = 0; j < list.size(); j++) {
				Result r = list.get(j);
				for (KeyValue kv : r.raw()) {
					queryByClientNO(hbase,new String(kv.getValue()));
				}
			}
		}
	}
}
