package com.info.test;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;

import com.info.comm.HBaseOperation;

public class CreateTables {
	/*private static String qualifier[] = { "ciftype", "nam", "zchnm",
			"zhovsflg", "oit", "otn", "bchflg", "oed", "rescd", "con",
			"zlridtype", "zlridcode", "zlridinvdte", "aph", "hph", "pzip",
			"madl", "cifoff", "nation", "prstate", "zregion", "empcity",
			"ccode", "zccode", "email" };*/
	public static void main(String args[]) throws IOException {
		Configuration conf = new Configuration();
		conf.set("hbase.zookeeper.quorum",
				"192.168.12.18,192.168.2.215,192.168.35.198");
		conf.set("hbase.zookeeper.property.clientPort", "2181");
		HBaseOperation hbase = new HBaseOperation(conf);
		String colFamily[]={"info"};
		//创建客户信息表，行键为客户号，列族为info,列族里面的列为数组qualifier里面的元素
		hbase.createTable("cif", colFamily);
		//创建姓名-客户表，行健为姓名-客户号，列族为info，列族里面有列clientno
		hbase.createTable("namcif", colFamily);
		//创建身份证号-客户表，行键为身份证号，列族为info,列族里面有列clientno
		hbase.createTable("oitcif", colFamily);
		//创建客户号表，该表只有一条数据，记录着最后添加的客户号，行键任意，这里为1000，
		//列族info，里面有列clientno记录着最后添加的客户号
		hbase.createTable("reccif", colFamily);
		hbase.insertRecord("reccif", "1000", "info", "clientno", "10220");
	}
}
