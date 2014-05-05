package com.info.comm;
//该类定义了一些操作hbase的方法
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;


public class HBaseOperation {
	private Configuration conf;
	private HBaseAdmin admin;
	public HBaseOperation(Configuration conf)throws IOException{
		this.conf=HBaseConfiguration.create(conf);
		this.admin=new HBaseAdmin(this.conf);
	}
	public HBaseOperation()throws IOException{
		Configuration cnf=new Configuration();
		this.conf=HBaseConfiguration.create(cnf);
		this.admin=new HBaseAdmin(this.conf);
	}
	//创建表
	public void createTable(String tableName,String colFamilies[]) throws IOException{
		if(this.admin.tableExists(tableName)){
			System.out.println("表"+tableName+"已经存在!");
		}else{
			HTableDescriptor dsc=new HTableDescriptor(tableName);
			int len=colFamilies.length;
			for(int i=0;i<len;i++){
				HColumnDescriptor family=new HColumnDescriptor(colFamilies[i]);
				dsc.addFamily(family);
			}
			admin.createTable(dsc);
			System.out.println("成功创建表"+tableName+".");
		}
	}
	//删除表
	public void deleteTable(String tableName) throws IOException{
		if(this.admin.tableExists(tableName)){
			admin.deleteTable(tableName);
			System.out.println("成功删除表"+tableName+".");
		}else{
			System.out.println(tableName+"表不存在.");
		}
	}
	//插入一行记录，指定列以及指定列的值
	public void insertRecord(String tableName,String rowkey,String family,String
			qualifier,String value) throws IOException{
		HTable table=new HTable(this.conf,tableName);
		Put put=new Put(rowkey.getBytes());
		put.add(family.getBytes(),qualifier.getBytes(),value.getBytes());
		table.put(put);
		System.out.println("成功插入一行.");
	}
	//插入客户信息到客户信息表中，值通过数组传进来
	public boolean insertCifRecord(String tableName,String rowkey,String family,String 
			qualifier[],String value[]) throws IOException{
		HTable table=new HTable(this.conf,tableName);
		Put put=new Put(rowkey.getBytes());
		if(qualifier.length!=value.length){
			System.out.println("列和值没有一一对应.");
			return false;
		} else {
			for (int i = 0; i < qualifier.length; i++) {
				put.add(family.getBytes(), qualifier[i].getBytes(),
						value[i].getBytes());
				table.put(put);
			}
			System.out.println("成功插入一行.");
			return true;
		}
	}
    //插入客户信息数据到客户信息表中，值通过类TransactionRequestMessage传进来
	public boolean insertCifRecord(String tableName, String rowkey,
			String family, String qualifier[], InsertMessageBean ccd)
			throws IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		HTable table = new HTable(this.conf, tableName);
		Put put = new Put(rowkey.getBytes());
		Method[] method=InsertMessageBean.class.getDeclaredMethods();
		for (int i = 0; i < qualifier.length; i++) {
			String str="get"+qualifier[i].toUpperCase().toString();
			for(int j=0;j<method.length;j++){
				if(str.equals(method[j].getName())){
					String value=(String)method[j].invoke(ccd);
					put.add(family.getBytes(), qualifier[i].getBytes(),
							value.getBytes());
					table.put(put);
					System.out.println(str+"==="+(String)method[j].invoke(ccd));
				}
			}
		}
		System.out.println("成功插入一行.");
		return true;
	}
	//删除一行记录
	public void deleteRecord(String tableName,String rowkey) throws IOException{
		HTable table=new HTable(this.conf,tableName);
		Delete del =new Delete(rowkey.getBytes());
		table.delete(del);
		System.out.println("成功删除一行.");
	}
	//得到表中指定行键的记录
	public Result getOneRecord(String tableName,String rowkey) throws IOException{
		HTable table=new HTable(this.conf,tableName);
		Get get=new Get(rowkey.getBytes());
		Result rs=table.get(get);
		return rs;
	}
	//得到表中行键以rowkey开头的所有记录
	public List<Result> getRecord(String tableName,String rowkey) throws IOException{
		HTable table=new HTable(this.conf,tableName);
		Scan scan=new Scan();
		//从startrowkey开始扫描
		String startRowkey=rowkey;
		//endrowkey设置扫描结束位置
		String endRowkey=rowkey+"1";
		scan.setStartRow(startRowkey.getBytes());
		scan.setStopRow(endRowkey.getBytes());
		ResultScanner scanner=table.getScanner(scan);
		List<Result> list=new ArrayList<Result>();
		for(Result r:scanner){
			list.add(r);
		}
		scanner.close();
		return list;
	}
	//得到表里面全部的记录
	public List<Result> getAllRecord(String tableName) throws IOException{
		HTable table=new HTable(this.conf,tableName);
		Scan scan=new Scan();
		ResultScanner scanner=table.getScanner(scan);
		List<Result> list=new ArrayList<Result>();
		for(Result r:scanner){
			list.add(r);
		}
		scanner.close();
		return list;
	}
	//得到表里面第一条记录
	public Result getFirstRecord(String tableName) throws IOException{
		HTable table=new HTable(this.conf,tableName);
		Result rs;
		Scan scan=new Scan();
		ResultScanner scanner=table.getScanner(scan);
		rs=scanner.next();
		return rs;
	}
}
