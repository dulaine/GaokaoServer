package com.baizhou.data.gamemodel;

public class WXSessionModel {

	private String openid;
	private String session_key;
	private String unionid;
	private int errcode;
	private String errmsg;

	
	public String getSession_key() {
		return session_key;
	}
	public void setSession_key(String session_key) {
		this.session_key = session_key;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
    public String getUnionid() {
        return unionid;
    }
    public void setUnionid(String unionid) {
        this.unionid = openid;
    }
    public int getErrcode() {
        return errcode;
    }
    public void setErrcode(int errcode) { this.errcode = errcode; }
    public String getErrmsg() {
        return errmsg;
    }
    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
