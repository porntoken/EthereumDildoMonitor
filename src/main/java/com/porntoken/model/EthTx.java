package com.porntoken.model;

/**
 * @author Christopher Gu
 * @product PornToken $PTWO Live Dildo Crowdfund
 * Copyright 2018
 */

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class EthTx implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@SerializedName("blockNumber")
	protected String blockNumber;
	
	@SerializedName("timeStamp")
	protected String timeStamp;
	
	@SerializedName("hash")
	protected String hash;
	
	@SerializedName("nonce")
	protected String nonce;
	
	@SerializedName("blockHash")
	protected String blockHash;
	
	@SerializedName("transactionIndex")
	protected String transactionIndex;
	
	@SerializedName("from")
	protected String from;
	
	@SerializedName("to")
	protected String to;
	
	@SerializedName("value")
	protected String value;
	
	@SerializedName("gas")
	protected String gas;
	
	@SerializedName("gasPrice")
	protected String gasPrice;
	
	@SerializedName("isError")
	protected String isError;
	
	@SerializedName("txreceipt_status")
	protected String txreceiptStatus;
	
	@SerializedName("input")
	protected String input;
	
	@SerializedName("contractAddress")
	protected String contractAddress;
	
	@SerializedName("cumulativeGasUsed")
	protected String cumulativeGasUsed;
	
	@SerializedName("gasUsed")
	protected String gasUsed;
	
	@SerializedName("confirmations")
	protected String confirmations;

	public String getBlockNumber() {
		return blockNumber;
	}

	public void setBlockNumber(String blockNumber) {
		this.blockNumber = blockNumber;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public String getBlockHash() {
		return blockHash;
	}

	public void setBlockHash(String blockHash) {
		this.blockHash = blockHash;
	}

	public String getTransactionIndex() {
		return transactionIndex;
	}

	public void setTransactionIndex(String transactionIndex) {
		this.transactionIndex = transactionIndex;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getGas() {
		return gas;
	}

	public void setGas(String gas) {
		this.gas = gas;
	}

	public String getGasPrice() {
		return gasPrice;
	}

	public void setGasPrice(String gasPrice) {
		this.gasPrice = gasPrice;
	}

	public String getIsError() {
		return isError;
	}

	public void setIsError(String isError) {
		this.isError = isError;
	}

	public String getTxreceiptStatus() {
		return txreceiptStatus;
	}

	public void setTxreceiptStatus(String txreceiptStatus) {
		this.txreceiptStatus = txreceiptStatus;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getContractAddress() {
		return contractAddress;
	}

	public void setContractAddress(String contractAddress) {
		this.contractAddress = contractAddress;
	}

	public String getCumulativeGasUsed() {
		return cumulativeGasUsed;
	}

	public void setCumulativeGasUsed(String cumulativeGasUsed) {
		this.cumulativeGasUsed = cumulativeGasUsed;
	}

	public String getGasUsed() {
		return gasUsed;
	}

	public void setGasUsed(String gasUsed) {
		this.gasUsed = gasUsed;
	}

	public String getConfirmations() {
		return confirmations;
	}

	public void setConfirmations(String confirmations) {
		this.confirmations = confirmations;
	}
}
