package com.porntoken.services;

import java.util.LinkedList;
import java.util.NoSuchElementException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.porntoken.dao.ProcessedEthTxDao;
import com.porntoken.model.DildoQueuePojo;
import com.porntoken.model.LovenseApiResponse;
import com.porntoken.model.ProcessedEthTx;
import com.porntoken.util.Shared;

@Service("dildoBuzzerService")
public class DildoBuzzerServiceImpl implements DildoBuzzerService {

	protected Logger logger = Logger.getLogger(this.getClass());
	
	protected static LinkedList<ProcessedEthTx> dildoActionQueue = new LinkedList<ProcessedEthTx>();
	
	@Autowired
	private ProcessedEthTxDao processedEthTxDao;
	
	@Autowired
	private HttpActionService httpActionService;
	
	@Value( "${lovense.connect.local.url}" )
	private String lovenseConnectLocalUrl;
	
	@Value( "${lovense.connect.local.port}" )
	private Integer lovenseConnectLocalPort;
	
	@Value( "${lovense.connect.device.id}" )
	private String lovenseConnectDeviceId;
	
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
		
		startDildoThread(dildoQueuePojo, processedEthTx);
		
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

	private String getLocalApiUrl() {
		
    	String url = "https://" + lovenseConnectLocalUrl + ":" + lovenseConnectLocalPort.toString();

    	return url;
	}
	
	private boolean dildoCommand(String txId, String command, Integer level) {
		
		String url = getLocalApiUrl() + "/" + command + "?v=" + level.toString() + "&t=" + lovenseConnectDeviceId;
		
    	String content = "";
    	
    	try {
			content = httpActionService.getHttpGet(url);
		} catch (Exception e) {
			logger.error("Could not execute the Lovense  txId: " + txId + " command: " + command + " level: " + level.toString() + " deviceId: " + lovenseConnectDeviceId, e);
			return false;
		}
    	
    	LovenseApiResponse lovenseApiResponse = null;
    	
    	try {
    		Gson gson = new Gson();
        	lovenseApiResponse = gson.fromJson(content, LovenseApiResponse.class);
		} catch (Exception e) {
			logger.error("Could parse the response from the local Lovense server  txId: " + txId + " content: " + content + " command: " + command + " level: " + level.toString() + " deviceId: " + lovenseConnectDeviceId, e);
			return false;
		}
    	
    	if (StringUtils.isEmpty(lovenseApiResponse.getType()) || !lovenseApiResponse.getType().equalsIgnoreCase("ok")
    			|| lovenseApiResponse.getCode() == null || lovenseApiResponse.getCode().intValue() != 200) {
			logger.error("Invalid HTTP response code from Lovense server txId: " + txId + " type: " + lovenseApiResponse.getType() + " code: " + lovenseApiResponse.getCode() 
							+ " content: " + content + " command: " + command + " level: " + level.toString() + " deviceId: " + lovenseConnectDeviceId);
			return false;
    	}
		
		return true;
	}
	
	private boolean resetDildo(String txId) {

		dildoCommand(txId, "Vibrate", 0);
		dildoCommand(txId, "Rotate", 0);
		
		return true;
	}
	
	private synchronized boolean execDildoAction(DildoQueuePojo dildoQueuePojo, ProcessedEthTx processedEthTx) {
		
		for (String command : dildoQueuePojo.getCommands()) {
			dildoCommand(dildoQueuePojo.getTxId(), command, dildoQueuePojo.getLevel());
		}

    	try {
    		Thread.sleep(dildoQueuePojo.getDurationSeconds() * 1000);
		} catch (Exception e) {
			logger.error("Could sleep dildo thread txId: " + dildoQueuePojo.getTxId() + " deviceId: " + lovenseConnectDeviceId, e);
			return false;
		}
		
    	resetDildo(dildoQueuePojo.getTxId());
        
    	processedEthTx.setSucceeded(true);
        markAsProcessed(processedEthTx);
        
		return true;
	}
	
	private boolean startDildoThread(DildoQueuePojo dildoQueuePojo, ProcessedEthTx processedEthTx) {
		
		Thread t = new Thread(new Runnable() {
	         @Override
	         public void run() {
	        	 execDildoAction(dildoQueuePojo, processedEthTx);
	         }
		});
		
    	try {
    		t.start();
		} catch (Exception e) {
			logger.error("Could start dildo thread txId: " + dildoQueuePojo.getTxId() + " deviceId: " + lovenseConnectDeviceId, e);
			return false;
		}
		
		return true;
	}
	
	private DildoQueuePojo getDildoQueuePojo(ProcessedEthTx processedEthTx) {
		
		DildoQueuePojo dildoQueuePojo = new DildoQueuePojo();
		
		Integer tokenAmount = processedEthTx.getTokenAmount();
		
		logger.info("tokenAmount: " + tokenAmount);
		
		dildoQueuePojo.setTxId(processedEthTx.getTxId());
		dildoQueuePojo.setTokens(tokenAmount);

		if (tokenAmount >= 1000000) {
			// 1M+ = Vibe/Twist for 1 hour
			// maximum vibration & rotation
			dildoQueuePojo.getCommands().add("Vibrate");
			dildoQueuePojo.getCommands().add("Rotate");
			dildoQueuePojo.setLevel(20);
			dildoQueuePojo.setDurationSeconds(60 * 60);
		} else if (tokenAmount >= 100000 && tokenAmount < 1000000) {
			// Vibe/Twist for 30 minutes
			// high vibration & rotation
			dildoQueuePojo.getCommands().add("Vibrate");
			dildoQueuePojo.getCommands().add("Rotate");
			dildoQueuePojo.setLevel(16);
			dildoQueuePojo.setDurationSeconds(30 * 60);
		} else if (tokenAmount >= 10000 && tokenAmount < 100000) {
			// Vibe/Twist for 5 minutes
			// medium vibration & rotation
			dildoQueuePojo.getCommands().add("Vibrate");
			dildoQueuePojo.getCommands().add("Rotate");
			dildoQueuePojo.setLevel(12);
			dildoQueuePojo.setDurationSeconds(5 * 60);
		} else if (tokenAmount >= 5000 && tokenAmount < 10000) {
			// Vibe/Twist for 1 minute
			// medium vibration & rotation
			dildoQueuePojo.getCommands().add("Vibrate");
			dildoQueuePojo.getCommands().add("Rotate");
			dildoQueuePojo.setDurationSeconds(60);
			dildoQueuePojo.setLevel(12);
		} else if (tokenAmount >= 1000 && tokenAmount < 5000) {
			// Vibe/Twist for 5 secs 
			// medium-low vibration & rotation
			dildoQueuePojo.getCommands().add("Vibrate");
			dildoQueuePojo.getCommands().add("Rotate");
			dildoQueuePojo.setDurationSeconds(5);
			dildoQueuePojo.setLevel(9);
		} else if (tokenAmount >= 101 && tokenAmount < 1000) {
			// Twist for 5 secs
			// low rotation
			dildoQueuePojo.getCommands().add("Rotate");
			dildoQueuePojo.setLevel(5);
			dildoQueuePojo.setDurationSeconds(5);
		} else if (tokenAmount >= 1 && tokenAmount <= 100) {
			// Vibe for 5 secs
			// low vibration
			dildoQueuePojo.getCommands().add("Vibrate");
			dildoQueuePojo.setLevel(5);
			dildoQueuePojo.setDurationSeconds(5);
		}
		
		return dildoQueuePojo;
	}
}
