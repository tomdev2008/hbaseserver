package com.info.socketserver;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Result;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.info.comm.HBaseOperation;
import com.info.comm.HeaderBean;
import com.info.comm.Hex;
import com.info.comm.InsertMessageBean;
import com.info.comm.ParseHeader;
import com.info.comm.ParseInsertMessageXML;
import com.info.comm.ParseQueryMessageXML;
import com.info.comm.QueryMessageBean;
import com.info.comm.ResponseInsertMessage;
import com.info.comm.ResponseQueryMessage;
import com.info.tools.QueryCIFInfo;

public class SocketSeverThread extends Thread{
	private static String qualifier[]={"ciftype","nam","zchnm","zhovsflg","oit",
	    "otn", "bchflg","oed","rescd","con","zlridtype","zlridcode","zlridinvdte",
	    "aph","hph","pzip","madl","cifoff","nation","prstate","zregion","empcity",
	    "ccode","zccode","email"};
	private static final Logger logger = Logger.getLogger(SocketSeverThread.class);
	private Socket socket = null;
	private int length = 0;
	private static final int BUF_SIZE = 4096;
	private Configuration conf=new Configuration();
	private HBaseOperation hbase;
	public InsertMessageBean insertMessage =new InsertMessageBean();
	public QueryMessageBean queryMessage=new QueryMessageBean();
	public HeaderBean header=new HeaderBean();

	public SocketSeverThread(Socket socket) throws IOException  {
		super("SocketSeverThread");
		this.socket = socket;
		conf.set("hbase.zookeeper.quorum", "192.168.12.18,192.168.2.215,192.168.35.198");
		conf.set("hbase.zookeeper.property.clientPort","2181");
		try {
			hbase=new HBaseOperation(conf);
		} catch (IOException e) {
			e.printStackTrace();
		}
		PropertyConfigurator.configure("./config/log4j.properties");
	}
	//该方法用来产生唯一的客户号
	public int generateClientNo(HBaseOperation hbase) throws IOException{
		int lastClientNo;
		Result rs=hbase.getFirstRecord("reccif");
		KeyValue[] kvarray=rs.raw();
		KeyValue kv=kvarray[0];
		//得到上一次插入的客户号
		lastClientNo=Integer.parseInt(new String(kv.getValue()));
		lastClientNo++;
		System.out.println(lastClientNo);
		return lastClientNo;
	}
	//该方法用来检查身份证号是否存在，如果存在则返回false，如果不存在则返回true
	public boolean checkOTN(HBaseOperation hbase,String otn) throws IOException{
		Result rs=hbase.getOneRecord("oitcif", otn);
		KeyValue[] kv=rs.raw();
		if(kv.length!=0){
			return false;
		}else{
			return true;
		}
	}
	public void run(){
		//定义两个输入输出流，注意是字节流，而不是字符流
		DataInputStream in = null;
		DataOutputStream out = null;
		try {	
			//活动socket的字节输入输出流
			in=new DataInputStream(socket.getInputStream());
			out=new DataOutputStream(socket.getOutputStream());
		    byte[] datain = new byte[BUF_SIZE]; 
		    //收取报文
		    while ((length = in.read(datain))>0) {	
		    	//解析报文头部，看看是插入报文还是查询报文
		    	ParseHeader parseHeader=new ParseHeader();
		    	header=parseHeader.readStringXml(new String(datain,0,length));
		    	System.out.println(header.getMsgRef());
		    	//如果报文参考号是20000，说明是查询报文,如果是10000，则为插入报文
		    	if(header.getMsgRef().equals("20000")){
		    		//把报文写入到日志里面
		    		logger.info("\n收到查询报文：\n\n"+Hex.dump(datain,0,length));
		    		System.out.println ("收到查询报文:["+new String(datain,0,length)+"]");
		    		ParseQueryMessageXML xml=new ParseQueryMessageXML();
		    		queryMessage =xml.readStringXml(new String(datain,0,length));
		    		String clientNo=queryMessage.getClientNo();
		    		String oitNumber=queryMessage.getOitNumber();
		    		String name=queryMessage.getName();
		    		if(!clientNo.equals("default")){
		    			//按客户号查询
		    			System.out.println("按客户号查询"+clientNo);
		    			InsertMessageBean message=new QueryCIFInfo().queryByClientNO(hbase, clientNo);
		    			byte[] response=new ResponseQueryMessage().BuildXML(message);
			    		out.write(response);
			    		logger.info("\n返回查询结果应答报文：\n\n"
								+ Hex.dump(response, 0,
										response.length));
			    		System.out.println("已经返回查询结果报文");	
		    			
		    		}else if(!oitNumber.equals("default")){
		    			//按身份证号查询
		    			System.out.println("按身份证号查询="+oitNumber);
		    			InsertMessageBean message=new QueryCIFInfo().queryByOitNumber(hbase, oitNumber);
		    			byte[] response=new ResponseQueryMessage().BuildXML(message);
			    		out.write(response);
			    		logger.info("\n返回查询结果应答报文：\n\n"
								+ Hex.dump(response, 0,
										response.length));
			    		System.out.println("已经返回查询结果报文");	
		    			
		    		}else if(!name.equals("default")){
		    			//按用户名称查询
		    			System.out.println("按名字查询="+name);
		    			List<InsertMessageBean> messageList=new QueryCIFInfo().queryByName(hbase, name);
		    			//先把查询的结果数量发给客户端
		    			String len=Integer.toString(messageList.size());
		    			out.write(len.getBytes());
		    			//发送查询结果报文
		    			for(int i=0;i<messageList.size();i++){
		    				byte[] response=new ResponseQueryMessage().BuildXML(messageList.get(i));
		    				out.write(response);
				    		logger.info("\n返回查询结果应答报文：\n\n"
									+ Hex.dump(response, 0,
											response.length));
		    			}
		    			System.out.println("已经返回查询结果报文");	
		    		}
				} else if (header.getMsgRef().equals("10000")) {
					// 把报文写入到日志里面
					logger.info("\n收到插入报文：\n\n" + Hex.dump(datain, 0, length));
					System.out.println("收到插入报文:["
							+ new String(datain, 0, length) + "]");
					// 进行报文拆解操作
					ParseInsertMessageXML xml = new ParseInsertMessageXML();
					insertMessage = xml.readStringXml(new String(datain, 0,
							length));
					// 检查身份证号是否存在，如果不存在则插入数据，如果存在则返回错误信息给客户端
					if (checkOTN(hbase, insertMessage.getOTN())) {
						// 得到身份证号
						String oitNumber = insertMessage.getOTN();
						// 得到客户中文名
						String name = insertMessage.getZCHNM();
						// 生成客户号
						String clientNo = Integer
								.toString(generateClientNo(hbase));
						// 生产namcif的rowkey，格式为name-clientno
						String nameClientNo = name + "-" + clientNo;
						// 把数据插入到hbase中
						hbase.insertCifRecord("cif", clientNo, "info",
								qualifier, insertMessage);
						// 身份证号作为行键，info:clientno作为列族和列，值为生成的客户号
						hbase.insertRecord("oitcif", oitNumber, "info",
								"clientno", clientNo);
						// 新插入的客户号作为最新的值，该表只有一条数据，表示最后增加的客户号是多少，行键可以任选,这里为1000
						hbase.insertRecord("reccif", "1000", "info",
								"clientno", clientNo);
						// 把nameclientno插入到表namcif中，这样我们就可以根据客户名字查询客户信息了
						hbase.insertRecord("namcif", nameClientNo, "info",
								"clientno", clientNo);
						// 生成返回客户端的报文
						byte[] response = new ResponseInsertMessage()
								.BuildXML(clientNo,"default","default");
						logger.info("\n发出应答报文：\n\n"
								+ Hex.dump(response, 0,
										response.length));
						out.write(response);
						System.out.println("已经返回插入结果报文");
					} else {
						System.out.println("身份证号已经存在.");
						// 身份证号已经存在，发出提示信息
						byte[] response = new ResponseInsertMessage()
								.BuildXML("身份证号已经存在","default","default");
						logger.info("\n发出应答报文：\n"+ Hex.dump(response, 0,response.length));
						out.write(response);
						System.out.println("已经返回插入结果报文");
					}
				}
			}
		} catch (Exception ioe) {
			logger.info("\nSocket is closed or read timeout" + ioe.toString());
			ioe.printStackTrace();
		} finally {
			try{
				logger.info("\n开始关闭socket连接");
				if (out!=null)	out.close();
				if (in!=null)	in.close();
				if (socket!=null)	socket.close();
			}catch (Exception ioe){
				logger.info("\n关闭socket连接异常"+ioe.toString());
				ioe.printStackTrace();	
			}
		} 
	}
}
