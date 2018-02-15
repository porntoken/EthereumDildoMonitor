package com.porntoken.model;

/**
 * @author Christopher Gu
 * @product PornToken $PTWO Live Dildo Crowdfund
 * Copyright 2018
 */

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class EtherScanApiResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@SerializedName("status")
	String status;
	
	@SerializedName("message")
	String message;
	
	@SerializedName("result")
	protected List<EthTx> result;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<EthTx> getResult() {
		return result;
	}

	public void setResult(List<EthTx> result) {
		this.result = result;
	}
}