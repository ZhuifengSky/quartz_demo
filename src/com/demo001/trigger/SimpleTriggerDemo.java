package com.demo001.trigger;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.demo001.job.SimpleJob;

public class SimpleTriggerDemo {

	public static void main(String[] args) throws SchedulerException {
		// 定义一个jobDetail
		JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class).withIdentity("simpleJob", "jobGroup").build();
		// 定义一个触发规则每5秒触发一次总计3次
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity("simpleTriggeer", "jobTriggerGroup")
				.usingJobData("int", 12)
		        .usingJobData("String", "哈哈哈")
		        .usingJobData("boolean", true)
				.withSchedule(SimpleScheduleBuilder.simpleSchedule()
				.withIntervalInSeconds(5).withRepeatCount(0))
				.build();		
		//通过SchedulerFactory获取一个调度器实例 
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();  		  
		Scheduler scheduler = schedulerFactory.getScheduler(); 
		scheduler.scheduleJob(jobDetail, trigger);
		//启动调度
		scheduler.start();
		
	}

}
