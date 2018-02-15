package com.porntoken.model;

/**
 * @author Christopher Gu
 * @product PornToken $PTWO Live Dildo Crowdfund
 * Copyright 2018
 */

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Cacheable
@Table(name = "ProcessedEthTx")
public class ProcessedEthTx implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	@Column(name = "ProcessedEthTxId", unique = true, nullable = false)
    protected Integer processedEthTxId;
	
	@Column(name = "TxId", unique = false)
    protected String txId;
	
	@Column(name = "Succeeded", unique = false)
    protected Boolean succeeded;
	
	@Column(name = "Processed", unique = false)
    protected Boolean processed;
	
	@Column(name = "FromAddress", unique = false)
    protected String fromAddress;
	
	@Column(name = "ToAddress", unique = false)
    protected String toAddress;
	
	@Column(name = "EthAmount", unique = false)
    protected Double ethAmount;
	
	// just take int value, no decimals
	@Column(name = "TokenAmount", unique = false)
    protected Integer tokenAmount;
	
	@Column(name = "DateCreated", unique = false)
    protected Date dateCreated;
	
	@Column(name = "DateUpdated", unique = false)
    protected Date dateUpdated;

	public Integer getProcessedEthTxId() {
		return processedEthTxId;
	}

	public void setProcessedEthTxId(Integer processedEthTxId) {
		this.processedEthTxId = processedEthTxId;
	}

	public String getTxId() {
		return txId;
	}

	public void setTxId(String txId) {
		this.txId = txId;
	}

	public Boolean getSucceeded() {
		return succeeded;
	}

	public void setSucceeded(Boolean succeeded) {
		this.succeeded = succeeded;
	}

	public Boolean getProcessed() {
		return processed;
	}

	public void setProcessed(Boolean processed) {
		this.processed = processed;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public Double getEthAmount() {
		return ethAmount;
	}

	public void setEthAmount(Double ethAmount) {
		this.ethAmount = ethAmount;
	}

	public Integer getTokenAmount() {
		return tokenAmount;
	}

	public void setTokenAmount(Integer tokenAmount) {
		this.tokenAmount = tokenAmount;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}
}
