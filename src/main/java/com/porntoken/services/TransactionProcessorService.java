package com.porntoken.services;

/**
 * @author Christopher Gu
 * @product PornToken $PTWO Live Dildo Crowdfund
 * Copyright 2018
 */

import com.porntoken.model.EthTx;

public interface TransactionProcessorService {
	
	public boolean processTransaction(EthTx ethTx);
	
}
