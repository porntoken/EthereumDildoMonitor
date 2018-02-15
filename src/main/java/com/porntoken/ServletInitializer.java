package com.porntoken;

/**
 * @author Christopher Gu
 * @product PornToken $PTWO Live Dildo Crowdfund
 * Copyright 2018
 */

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		
		return application.sources(EthereumDildoMonitorApplication.class);
	}

}
