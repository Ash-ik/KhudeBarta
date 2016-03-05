package com.example.piyalshuvro.khudebarta;

import java.io.Serializable;

public class MsgDetails implements Serializable{
	private String number;
	private String msg;
	private String type;
	private Integer password;
	private String date;

	public MsgDetails(String number, String msg, String type, Integer password, String date) {
		this.number = number;
		this.msg = msg;
		this.type = type;
		this.password = password;
		this.date = date;
		setMsg(msg);
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getPassword() {
		return password;
	}

	public void setPassword(Integer password) {
		this.password = password;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String toString() {
		return "[" + number + " " + msg + " " + type + " " + password + " "
				+ date + "]";
	}

}
