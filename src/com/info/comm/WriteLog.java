package com.info.comm;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class WriteLog {

	private String fileName = "";
	private String threadName = "";
	private static WriteLog writeLog = null;

	public WriteLog() throws Exception {
		HashMap<String, String> hashMap = ParseConfigXML.hashMap;
		if (hashMap == null) {
			hashMap = ParseConfigXML.getXmlConfig();
		} else if (hashMap != null && hashMap.size() == 0) {
			hashMap = ParseConfigXML.getXmlConfig();
		}
		this.fileName = (String) hashMap.get("logFileDir")
				+ (String) hashMap.get("logFileName");
	}

	public static WriteLog instance() {
		if (writeLog == null) {
			try {
				writeLog = new WriteLog();
			} catch (Exception e) {
				System.out.println("日志异常");
				e.printStackTrace();
			}
		}
		return writeLog;
	}

	/**
	 * 得到本机日期，日期格式为 年年年年-月月-日日 小时：分钟：妙 注意yyyy-MM-dd
	 * HH:mm:ss是区分大小写的，其中HH表示24进制的小时，hh表示12进制的小时
	 */
	public String getLocalTime() {
		SimpleDateFormat simpleFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String currentDate = simpleFormat.format(date);
		return currentDate;
	}

	/**
	 * 将outPutString输出到文件中
	 */
	public boolean writeStringToFile(String outPutString) {
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName,true)));	
			threadName = Thread.currentThread().getName();
			String currentDate = getLocalTime();
			writer.write(currentDate + " [" + threadName + "] "
					+ outPutString + "\n");
			writer.flush();
			writer.close();
			return true;
		} catch (Exception e) {
			System.out.println("fileName = [" + fileName + "]" + e.toString());
			return false;
		}
	}

	/**
	 * 将字符输出到文件中
	 */
	public boolean writeStringToFile(char c) {
		try {
			DataOutputStream out = new DataOutputStream(
					new BufferedOutputStream(new FileOutputStream(fileName,
							true)));
			out.writeBytes(c + "");
			out.close();
			return true;
		} catch (Exception e) {
			System.out.println("fileName = [" + fileName + "]" + e.toString());
			return false;
		}
	}

	/**
	 * 输出buffer中开始位为start到end位的所有字符
	 */
	public void printBuffer(byte[] buff, int start, int end) {
		byte[] tmpBuffer = new byte[end - start];
		System.arraycopy(buff, start, tmpBuffer, 0, end - start);
		writeStringToFile(new String(tmpBuffer));
	}

	public static void main(String[] args) {
	}
}
