package com.porntoken.quartz;

/**
 * @author Christopher Gu
 * @product PornToken $PTWO Live Dildo Crowdfund
 * Copyright 2018
 */

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.quartz.DisallowConcurrentExecution;

import com.porntoken.services.DildoSchedulerService;

@Component
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class EtherscanPollJob implements Job {
 
    @Autowired
    private DildoSchedulerService dildoSchedulerService;
 
    public void execute(JobExecutionContext context) throws JobExecutionException {
    	dildoSchedulerService.executeEtherscanDildoPollingJob();
    }
}