package com.porntoken.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class LovenseApiResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@SerializedName("type")
	String type;
	
	@SerializedName("code")
	Integer code;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
}
