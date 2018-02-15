package com.porntoken.services;

/**
 * @author Christopher Gu
 * @product PornToken $PTWO Live Dildo Crowdfund
 * Copyright 2018
 */

import java.io.File;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.porntoken.dao.ProcessedEthTxDao;
import com.porntoken.model.DildoQueuePojo;
import com.porntoken.model.ProcessedEthTx;
import com.porntoken.util.Shared;

@Service("dildoBuzzerService")
public class DildoBuzzerServiceImpl implements DildoBuzzerService {

	protected Logger logger = Logger.getLogger(this.getClass());
	
	protected static LinkedList<ProcessedEthTx> dildoActionQueue = new LinkedList<ProcessedEthTx>();
	
	@Autowired
	private ProcessedEthTxDao processedEthTxDao;
	
	@Override
	public boolean loadProcessedTransactionsFromDatabase() {
		
		Iterable<ProcessedEthTx> processedEthTxList = processedEthTxDao.findAll();
		
		for (ProcessedEthTx processedEthTx : processedEthTxList) {
			
			Shared.processedEthTxMap.put(processedEthTx.getTxId(), processedEthTx);
		}
		
		return false;
	}
	
	@Override
	public boolean queueDildoBuzz(ProcessedEthTx processedEthTx) {
		dildoActionQueue.addLast(processedEthTx);
		return false;
	}
	
	@Override
	public boolean processNextQueue() {
		
		ProcessedEthTx processedEthTx = null;
		
		try {
			processedEthTx = dildoActionQueue.pop();
		} catch (NoSuchElementException nse) {
			logger.info("No more Dildo actions in Queue");
			return false;
		}
		
		if (processedEthTx == null) {
			logger.info("No more Dildo actions in Queue");
			return false;
		}
		
		DildoQueuePojo dildoQueuePojo = getDildoQueuePojo(processedEthTx);
		
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("python/dildo_girl.py").getFile());
		
		String filePath = file.getAbsolutePath();
		
		// python dildo_girl.py command level duration_secs
		
        Runtime rt = Runtime.getRuntime();
        String[] commandAndArguments = {"python", filePath, dildoQueuePojo.getCommand(), dildoQueuePojo.getLevel().toString(), dildoQueuePojo.getDurationSeconds().toString()};
        try {
        	
        	logger.info("RUNNING COMMAND: python " + filePath + " " + dildoQueuePojo.getCommand() + " " + dildoQueuePojo.getLevel().toString() + " " + dildoQueuePojo.getDurationSeconds().toString());
        	
            Process p = rt.exec(commandAndArguments);
            // logger.info(Shared.readProcessOutput(p));
            p.waitFor();
            if (p.exitValue() == 0) {
	            processedEthTx.setSucceeded(true);
	            logger.info("Dildo Process Succeeded with Exit Value: " + p.exitValue());
            } else {
            	processedEthTx.setSucceeded(false);
            	logger.error("Dildo Process Failed with Exit Value: " + p.exitValue());
            }
        }catch(Exception e) {
        	processedEthTx.setSucceeded(false);
        	logger.error("Dildo Process Failed", e);
        }
        
        markAsProcessed(processedEthTx);
        
		return true;
	}
	
	@Override
	public boolean markAsProcessed(ProcessedEthTx processedEthTx) {
		
		boolean result = true;
		
		try {
			// mark as processed so we don't process it again
			processedEthTx.setProcessed(true);
			processedEthTxDao.save(processedEthTx);
		} catch (Exception e) {
			logger.error("Could not mark TxId: " + processedEthTx.getTxId() + " as processed");
			result = false;
		}
		
		return result;
		
	}
	
	protected DildoQueuePojo getDildoQueuePojo(ProcessedEthTx processedEthTx) {
		
		DildoQueuePojo dildoQueuePojo = new DildoQueuePojo();
		
		Integer tokenAmount = processedEthTx.getTokenAmount();
		
		logger.info("tokenAmount: " + tokenAmount);
		
		dildoQueuePojo.setTxId(processedEthTx.getTxId());
		dildoQueuePojo.setTokens(tokenAmount);

		if (tokenAmount >= 1000000) {
			// 1M+ = Vibe/Twist for 30 minutes
			dildoQueuePojo.setCommand("blitz");
			dildoQueuePojo.setLevel(255);
			dildoQueuePojo.setDurationSeconds(30 * 60);
		} else if (tokenAmount >= 100000 && tokenAmount < 1000000) {
			// Vibe/Twist for 15 minutes
			dildoQueuePojo.setCommand("blitz");
			dildoQueuePojo.setLevel(200);
			dildoQueuePojo.setDurationSeconds(15 * 60);
		} else if (tokenAmount >= 10000 && tokenAmount < 100000) {
			// Vibe/Twist for 1 minute
			dildoQueuePojo.setCommand("blitz");
			dildoQueuePojo.setLevel(150);
			dildoQueuePojo.setDurationSeconds(60);
		} else if (tokenAmount >= 5000 && tokenAmount < 10000) {
			// Vibe/Twist for 10 sec 
			dildoQueuePojo.setCommand("blitz");
			dildoQueuePojo.setDurationSeconds(10);
			dildoQueuePojo.setLevel(150);
		} else if (tokenAmount >= 1000 && tokenAmount < 5000) {
			// Vibe/Twist for 1 sec 
			dildoQueuePojo.setCommand("blitz");
			dildoQueuePojo.setDurationSeconds(1);
			dildoQueuePojo.setLevel(100);
		} else if (tokenAmount >= 101 && tokenAmount < 1000) {
			// Twist for 1 sec 
			dildoQueuePojo.setCommand("rotate");
			dildoQueuePojo.setLevel(50);
			dildoQueuePojo.setDurationSeconds(1);
		} else if (tokenAmount >= 1 && tokenAmount <= 100) {
			// Vibe for 1 sec
			dildoQueuePojo.setCommand("vibrate");
			dildoQueuePojo.setLevel(50);
			dildoQueuePojo.setDurationSeconds(1);
		}
		
		return dildoQueuePojo;
	}
}
