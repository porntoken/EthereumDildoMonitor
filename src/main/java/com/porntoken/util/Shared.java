package com.porntoken.util;

/**
 * @author Christopher Gu
 * @product PornToken $PTWO Live Dildo Crowdfund
 * Copyright 2018
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.porntoken.model.ProcessedEthTx;

public class Shared {

	public static Map<String, ProcessedEthTx> processedEthTxMap = new HashMap<String, ProcessedEthTx>();
	
    /**
     * Reads the response from the command. Please note that this works only
     * if the process returns immediately.
     * @param p
     * @return
     * @throws Exception 
     */
    public static String readProcessOutput(Process p) throws Exception{
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String response = "";
        String line;
        while ((line = reader.readLine()) != null) {
            response += line+"\r\n";
        }
        reader.close();
        return response;
    }
}
