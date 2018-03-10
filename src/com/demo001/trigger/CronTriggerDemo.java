package com.demo001.trigger;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.demo001.job.SimpleJob;

public class CronTriggerDemo {

	public static void main(String[] args) throws SchedulerException {
		// 定义一个jobDetail
		JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class).withIdentity("simpleJob", "jobGroup").build();
		CronTrigger cronTrigger = (CronTrigger) TriggerBuilder.newTrigger()
				.withIdentity("cronTriggeer", "jobTriggerGroup")
				.usingJobData("int", 12)
		        .usingJobData("String", "哈哈哈")
		        .usingJobData("boolean", true)
		        //每天下午1点48执行一次
				.withSchedule(CronScheduleBuilder.cronSchedule("0 48  13 * * ?"))
				.build();
		//通过SchedulerFactory获取一个调度器实例 
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();  		  
		Scheduler scheduler = schedulerFactory.getScheduler(); 
		scheduler.scheduleJob(jobDetail, cronTrigger);
		//启动调度
		scheduler.start();
	}

}
