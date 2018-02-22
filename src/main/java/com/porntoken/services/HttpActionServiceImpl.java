package com.porntoken.services;

import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service("httpActionService")
public class HttpActionServiceImpl implements HttpActionService {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	public String getHttpGet(String url) throws Exception {
		
		HttpClient httpclient;
	    HttpGet httpGet;
	    
	    String result = null;
	    
		try {

			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs, String authType) {
				}

				public void checkServerTrusted(X509Certificate[] certs, String authType) {
				}

			} };
			

			SSLContext sslcontext = SSLContext.getInstance("SSL");
			sslcontext.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext);
			httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			
		    httpGet = new HttpGet(url);

		    HttpResponse response = httpclient.execute(httpGet);
		    
		    HttpEntity entity = response.getEntity();
		    if (entity != null) {
		        result = IOUtils.toString(entity.getContent());
		    }
		} catch (Exception e) {
			logger.error("HTTP GET FAILED: " + url, e);
		}

	    return result;
		
	}
}
