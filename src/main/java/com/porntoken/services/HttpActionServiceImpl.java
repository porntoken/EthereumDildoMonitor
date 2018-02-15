package com.porntoken.services;

/**
 * @author Christopher Gu
 * @product PornToken $PTWO Live Dildo Crowdfund
 * Copyright 2018
 */

import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("httpActionService")
public class HttpActionServiceImpl implements HttpActionService {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	public String getHttpGet(String url) throws Exception {
		
		HttpClient httpclient;
	    HttpGet httpGet;
	    httpclient = new DefaultHttpClient();
	    httpGet = new HttpGet(url);

	    HttpResponse response = httpclient.execute(httpGet);

	    String result = null;
	    
	    HttpEntity entity = response.getEntity();
	    if (entity != null) {
	        result = IOUtils.toString(entity.getContent());
	    }
	    
	    return result;
		
	}
}
