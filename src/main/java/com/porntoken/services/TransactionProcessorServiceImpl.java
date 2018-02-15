package com.porntoken.services;

/**
 * @author Christopher Gu
 * @product PornToken $PTWO Live Dildo Crowdfund
 * Copyright 2018
 */

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.porntoken.model.EthTx;
import com.porntoken.model.ProcessedEthTx;
import com.porntoken.util.Shared;

@Service("transactionProcessorService")
public class TransactionProcessorServiceImpl implements TransactionProcessorService {

	protected Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private DildoBuzzerService dildoBuzzerService;
	
	@Value( "${porntoken.v2.contract.address}" )
	private String contractAddress;
	
	@Override
	public boolean processTransaction(EthTx ethTx) {
		
		// was this transaction already processed?
		if (Shared.processedEthTxMap.get(ethTx.getHash()) != null
					|| StringUtils.isEmpty(ethTx.getHash())
					|| StringUtils.isEmpty(ethTx.getTo())) return false;
		
		// do we have enough confirmations yet?
		int confs = 0;
		
		try {
			confs = Integer.parseInt(ethTx.getConfirmations());
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		
		// we want at least 8 confirmations
		if (confs < 8) return false;

		logger.info("PROCESSING TRANSACTION: " + ethTx.getHash());
		
		boolean isError = (!ethTx.getTo().equals(contractAddress)
							|| ethTx.getIsError().equals("1")
							// max input is "0x"
							// don't credit hack attempts on other contract methods
							|| ethTx.getInput().length() > 2);
			
		// get eth value
		
		Double ethValD = 0D;
		Integer tokens = 0;
		
		if (!StringUtils.isEmpty(ethTx.getValue()) && ethTx.getValue().length() > 1) { 
		
			try {
				ethValD = Double.parseDouble(ethTx.getValue());

				// 1 PTWO = 0.00001337 ETH
				// 13370000000000
				Double tokensD = ethValD / 13370000000000L;
				
				logger.debug("tokensD: " + tokensD);
				
				// don't round, just take the int value
				tokens = tokensD.intValue();
				
				logger.debug("tokens: " + tokens);
				
		    	BigDecimal big = BigDecimal.valueOf(ethValD.doubleValue());
		    	// 18 decimal must be represented as a positive number with no decimals
		    	big = big.divide(BigDecimal.valueOf(1000000000000000000L));
		    	
		    	// loss of precision, but we may later want to store the full BigDecimal value, so leave it
		    	ethValD = big.doubleValue();
			} catch (Exception e) {
				logger.error(e);
				isError = true;
			}
		}

		if (tokens <= 0) isError = true;

		// copy to processed dildo queue object
		
		ProcessedEthTx processedEthTx = new ProcessedEthTx();
		processedEthTx.setTxId(ethTx.getHash());
		processedEthTx.setSucceeded(false);
		processedEthTx.setProcessed(false);
		processedEthTx.setFromAddress(ethTx.getFrom());
		processedEthTx.setToAddress(ethTx.getTo());
		processedEthTx.setEthAmount(ethValD);
		processedEthTx.setTokenAmount(tokens);
		processedEthTx.setDateCreated(new Date());
		processedEthTx.setDateUpdated(new Date());
		
		Shared.processedEthTxMap.put(processedEthTx.getTxId(), processedEthTx);
		
		if (!isError) {
			logger.info("ADDING TRANSACTION TO THE QUEUE: " + ethTx.getHash());
			dildoBuzzerService.queueDildoBuzz(processedEthTx);
			return true;
		} else {
			// mark as processed so we don't process it again
			logger.info("MARKING TRANSACTION AS VOID: " + ethTx.getHash());
			dildoBuzzerService.markAsProcessed(processedEthTx);
			return false;
		}
	}
}
