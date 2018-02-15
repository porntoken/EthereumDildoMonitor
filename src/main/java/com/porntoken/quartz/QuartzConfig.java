package com.porntoken.quartz;

/**
 * @author Christopher Gu
 * @product PornToken $PTWO Live Dildo Crowdfund
 * Copyright 2018
 */

import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import com.porntoken.quartz.AutowiringSpringBeanJobFactory;

@Configuration
public class QuartzConfig {

	@Autowired 
	private ApplicationContext applicationContext;
	
	@Value( "${porntoken.v2.etherscan.poll.interval.seconds}" )
	private Long etherscanPollIntervalSeconds;
	
	@Bean
	public JobDetailFactoryBean jobDetail() {
	    JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
	    jobDetailFactory.setJobClass(EtherscanPollJob.class);
	    jobDetailFactory.setDescription("Invoke Etherscan Poll Dildo Service");
	    jobDetailFactory.setDurability(true);
	    return jobDetailFactory;
	}
	
	@Bean
	public SimpleTriggerFactoryBean trigger(JobDetail job) {
	    SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
	    trigger.setJobDetail(job);
	    // execute every 15 seconds, it won't execute concurrently
	    trigger.setRepeatInterval(etherscanPollIntervalSeconds * 1000L);
	    trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
	    return trigger;
	}
	
	@Bean
	public SchedulerFactoryBean scheduler(Trigger trigger, JobDetail job) {
	    SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
	    schedulerFactory.setConfigLocation(new ClassPathResource("quartz.properties"));
	    schedulerFactory.setJobFactory(springBeanJobFactory());
	    JobDetail[] jobs = new JobDetail[1];
	    jobs[0] = job;
	    schedulerFactory.setJobDetails(jobs);
	    Trigger[] triggers = new Trigger[1];
	    triggers[0] = trigger;
	    schedulerFactory.setTriggers(triggers);
	    return schedulerFactory;
	}
	
	@Bean
	public SpringBeanJobFactory springBeanJobFactory() {
		AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
	    jobFactory.setApplicationContext(applicationContext);
	    return jobFactory;
	}
	
}
