package com.team4.museum.vo;

import java.sql.Date;

public class QnaVO {

	private Integer qseq;
	private String title;
	private String content;
	private Date writedate;
	private String email;
	private String pwd;
	private String phone;
	private String reply;
	private String publicyn;

	public Integer getQseq() {
		return qseq;
	}

	public void setQseq(Integer qseq) {
		this.qseq = qseq;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getWritedate() {
		return writedate;
	}

	public void setWritedate(Date writedate) {
		this.writedate = writedate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public String getPublicyn() {
		return publicyn;
	}

	public void setPublicyn(String publicyn) {
		this.publicyn = publicyn;
	}

	public boolean isPublic() {
		return publicyn.equals("Y");
	}

	public void setPublic(boolean isPublic) {
		publicyn = isPublic ? "Y" : "N";
	}

}
