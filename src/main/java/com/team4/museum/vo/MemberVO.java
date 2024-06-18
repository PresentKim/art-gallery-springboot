package com.team4.museum.vo;

import java.sql.Date;

public class MemberVO {
	private String id;
	private String name;
	private String pwd;
	private String email;
	private Date indate;
	private String phone;
	private String adminyn;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getIndate() {
		return indate;
	}

	public void setIndate(Date indate) {
		this.indate = indate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAdminyn() {
		return adminyn;
	}

	public void setAdminyn(String adminyn) {
		this.adminyn = adminyn;
	}

	public boolean isAdmin() {
		return adminyn.equals("Y");
	}

	public void setAdmin(boolean isAdmin) {
		adminyn = isAdmin ? "Y" : "N";
	}

}
