package com.info.comm;
//客户端发过来的插入数据库报文
public class InsertMessageBean {
	String msgRef = "";
	String channel ="";
	String fromNodeId ="";
	String toServiceCode;
	String externalReferenceNo;
	String replyCode;
	String replyText;
	String CIFTYPE = "";
	String NAM = "" ;
	String ZCHNM = "" ;
	String ZHOVSFLG;
	String OIT;
	String OTN;
	String BCHFLG;
	String OED;
	String RESCD;
	String CON;
	String ZLRIDTYPE;
	String ZLRIDCODE;
	String ZLRIDINVDTE;
	String APH;
	String HPH;
	String PZIP;
	String MAD1;
	String CIFOFF;
	String NATION;
	String PRSTAGE;
	String EMPCITY;
	String CCODE;
	String ZCCODE;
	String EMAIL;
	String ZREGION;
	public String getZREGION() {
		return ZREGION;
	}
	public void setZREGION(String zREGION) {
		ZREGION = zREGION;
	}
	public String getMsgRef() {
		return msgRef;
	}
	public String getChannel() {
		return channel;
	}
	public String getFromNodeId() {
		return fromNodeId;
	}
	public String getToServiceCode() {
		return toServiceCode;
	}
	public String getExternalReferenceNo() {
		return externalReferenceNo;
	}
	public String getReplyCode() {
		return replyCode;
	}
	public String getReplyText() {
		return replyText;
	}
	public String getCIFTYPE() {
		return CIFTYPE;
	}
	public String getNAM() {
		return NAM;
	}
	public String getZCHNM() {
		return ZCHNM;
	}
	public String getZHOVSFLG() {
		return ZHOVSFLG;
	}
	public String getOIT() {
		return OIT;
	}
	public String getOTN() {
		return OTN;
	}
	public String getBCHFLG() {
		return BCHFLG;
	}
	public String getOED() {
		return OED;
	}
	public String getRESCD() {
		return RESCD;
	}
	public String getCON() {
		return CON;
	}
	public String getZLRIDTYPE() {
		return ZLRIDTYPE;
	}
	public String getZLRIDCODE() {
		return ZLRIDCODE;
	}
	public String getZLRIDINVDTE() {
		return ZLRIDINVDTE;
	}
	public String getAPH() {
		return APH;
	}
	public String getHPH() {
		return HPH;
	}
	public String getPZIP() {
		return PZIP;
	}
	public String getMAD1() {
		return MAD1;
	}
	public String getCIFOFF() {
		return CIFOFF;
	}
	public String getNATION() {
		return NATION;
	}
	public String getPRSTAGE() {
		return PRSTAGE;
	}
	public String getEMPCITY() {
		return EMPCITY;
	}
	public String getCCODE() {
		return CCODE;
	}
	public String getZCCODE() {
		return ZCCODE;
	}
	public String getEMAIL() {
		return EMAIL;
	}
	public void setMsgRef(String msgRef) {
		this.msgRef = msgRef;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public void setFromNodeId(String fromNodeId) {
		this.fromNodeId = fromNodeId;
	}
	public void setToServiceCode(String toServiceCode) {
		this.toServiceCode = toServiceCode;
	}
	public void setExternalReferenceNo(String externalReferenceNo) {
		this.externalReferenceNo = externalReferenceNo;
	}
	public void setReplyCode(String replyCode) {
		this.replyCode = replyCode;
	}
	public void setReplyText(String replyText) {
		this.replyText = replyText;
	}
	public void setCIFTYPE(String cIFTYPE) {
		CIFTYPE = cIFTYPE;
	}
	public void setNAM(String nAM) {
		NAM = nAM;
	}
	public void setZCHNM(String zCHNM) {
		ZCHNM = zCHNM;
	}
	public void setZHOVSFLG(String zHOVSFLG) {
		ZHOVSFLG = zHOVSFLG;
	}
	public void setOIT(String oIT) {
		OIT = oIT;
	}
	public void setOTN(String oTN) {
		OTN = oTN;
	}
	public void setBCHFLG(String bCHFLG) {
		BCHFLG = bCHFLG;
	}
	public void setOED(String oED) {
		OED = oED;
	}
	public void setRESCD(String rESCD) {
		RESCD = rESCD;
	}
	public void setCON(String cON) {
		CON = cON;
	}
	public void setZLRIDTYPE(String zLRIDTYPE) {
		ZLRIDTYPE = zLRIDTYPE;
	}
	public void setZLRIDCODE(String zLRIDCODE) {
		ZLRIDCODE = zLRIDCODE;
	}
	public void setZLRIDINVDTE(String zLRIDINVDTE) {
		ZLRIDINVDTE = zLRIDINVDTE;
	}
	public void setAPH(String aPH) {
		APH = aPH;
	}
	public void setHPH(String hPH) {
		HPH = hPH;
	}
	public void setPZIP(String pZIP) {
		PZIP = pZIP;
	}
	public void setMAD1(String mAD1) {
		MAD1 = mAD1;
	}
	public void setCIFOFF(String cIFOFF) {
		CIFOFF = cIFOFF;
	}
	public void setNATION(String nATION) {
		NATION = nATION;
	}
	public void setPRSTAGE(String pRSTAGE) {
		PRSTAGE = pRSTAGE;
	}
	public void setEMPCITY(String eMPCITY) {
		EMPCITY = eMPCITY;
	}
	public void setCCODE(String cCODE) {
		CCODE = cCODE;
	}
	public void setZCCODE(String zCCODE) {
		ZCCODE = zCCODE;
	}
	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}
}