package com.porntoken.services;

/**
 * @author Christopher Gu
 * @product PornToken $PTWO Live Dildo Crowdfund
 * Copyright 2018
 */

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.porntoken.model.EthTx;
import com.porntoken.model.EtherScanApiResponse;

@Service("dildoSchedulerService")
public class DildoSchedulerServiceImpl implements DildoSchedulerService {

    protected Logger logger = Logger.getLogger(this.getClass());
    
	@Autowired
	private HttpActionService httpActionService;
	
	@Autowired
	private TransactionProcessorService transactionProcessorService;
	
	@Autowired
	private DildoBuzzerService dildoBuzzerService;
	
	@Value( "${porntoken.v2.etherscan.api.url}" )
	private String etherscanApiUrl;
	
	@Value( "${porntoken.v2.etherscan.api.key}" )
	private String etherscanApiKey;
	
	@Override
    public boolean executeEtherscanDildoPollingJob() {

    	String url = etherscanApiUrl + etherscanApiKey;
    	
    	String content = "";
    	
    	try {
			content = httpActionService.getHttpGet(url);
		} catch (Exception e) {
			logger.error("Could not get the API JSON from Etherscan", e);
			return false;
		}
    	
    	Gson gson = new Gson();
    	EtherScanApiResponse etherScanApiResponse = gson.fromJson(content, EtherScanApiResponse.class);
    	
    	logger.debug("ETHERSCAN RAW RESPONSE: " + etherScanApiResponse.getResult());

    	logger.info("FOUND " + etherScanApiResponse.getResult().size() + " RECORDS FROM ETHERSCAN");
    	
    	if (etherScanApiResponse.getResult().size() > 0) {
    		
    		for (EthTx ethTx : etherScanApiResponse.getResult()) {
    			transactionProcessorService.processTransaction(ethTx);
    		}
    	}
    	
    	boolean hasNext = true;
    	
    	do {
    		hasNext = dildoBuzzerService.processNextQueue();
    	} while (hasNext);
    	
    	return true;
    }
}
