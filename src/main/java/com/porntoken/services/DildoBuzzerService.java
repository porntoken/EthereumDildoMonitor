package com.porntoken.services;

/**
 * @author Christopher Gu
 * @product PornToken $PTWO Live Dildo Crowdfund
 * Copyright 2018
 */

import com.porntoken.model.ProcessedEthTx;

public interface DildoBuzzerService {
	
	public boolean loadProcessedTransactionsFromDatabase();
	
	public boolean markAsProcessed(ProcessedEthTx processedEthTx);
	
	public boolean queueDildoBuzz(ProcessedEthTx processedEthTx);
	
	public boolean processNextQueue();
}
