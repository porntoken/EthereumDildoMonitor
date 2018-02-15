package com.porntoken.model;

/**
 * @author Christopher Gu
 * @product PornToken $PTWO Live Dildo Crowdfund
 * Copyright 2018
 */

public class DildoQueuePojo {

	public String txId;
	
	public Integer tokens;
	
	public String command;
	
	public Integer level;
	
	public Integer durationSeconds;

	
	public String getTxId() {
		return txId;
	}

	public void setTxId(String txId) {
		this.txId = txId;
	}

	public Integer getTokens() {
		return tokens;
	}

	public void setTokens(Integer tokens) {
		this.tokens = tokens;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getDurationSeconds() {
		return durationSeconds;
	}

	public void setDurationSeconds(Integer durationSeconds) {
		this.durationSeconds = durationSeconds;
	}
}
