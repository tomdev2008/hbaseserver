package com.info.test;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;

import com.info.comm.HBaseOperation;

public class QueryByName {

	public static void main(String[] args) throws IOException {
		Configuration conf = new Configuration();
		conf.set("hbase.zookeeper.quorum",
				"192.168.12.18,192.168.2.215,192.168.35.198");
		conf.set("hbase.zookeeper.property.clientPort", "2181");
		HBaseOperation hbase = new HBaseOperation(conf);
		String name="张三";
		new QueryCIFInfo().queryByName(hbase, name);
	}

}
