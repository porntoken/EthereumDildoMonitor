package com.porntoken;

/**
 * @author Christopher Gu
 * @product PornToken $PTWO Live Dildo Crowdfund
 * Copyright 2018
 */

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.porntoken.services.DildoBuzzerService;
 
@Component
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent>{
 
	protected Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private DildoBuzzerService dildoBuzzerService;
	
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
    	
		logger.info("APPLICATION STARTING: Ethereum Dildo Monitor");
		
		/*
		 * Load the processed transactions before the app starts
		 */
		
		logger.info("LOADING PROCESSED TRANSACTIONS FROM THE DATABASE");
		
		boolean result = dildoBuzzerService.loadProcessedTransactionsFromDatabase();
		
		if (result == true) {
			logger.info("LOADING PROCESSED TRANSACTIONS SUCCESS");
		} else {
			logger.info("LOADING PROCESSED TRANSACTIONS FAILED");
		}
		
    }
}