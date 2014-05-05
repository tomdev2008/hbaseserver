package com.info.comm;
//报文头部
public class HeaderBean {
	String msgRef = "";
	String channel ="";
	String fromNodeId ="";
	String toServiceCode;
	String externalReferenceNo;
	String replyCode;
	String replyText;
	public String getMsgRef() {
		return msgRef;
	}
	public void setMsgRef(String msgRef) {
		this.msgRef = msgRef;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getFromNodeId() {
		return fromNodeId;
	}
	public void setFromNodeId(String fromNodeId) {
		this.fromNodeId = fromNodeId;
	}
	public String getToServiceCode() {
		return toServiceCode;
	}
	public void setToServiceCode(String toServiceCode) {
		this.toServiceCode = toServiceCode;
	}
	public String getExternalReferenceNo() {
		return externalReferenceNo;
	}
	public void setExternalReferenceNo(String externalReferenceNo) {
		this.externalReferenceNo = externalReferenceNo;
	}
	public String getReplyCode() {
		return replyCode;
	}
	public void setReplyCode(String replyCode) {
		this.replyCode = replyCode;
	}
	public String getReplyText() {
		return replyText;
	}
	public void setReplyText(String replyText) {
		this.replyText = replyText;
	}

}
