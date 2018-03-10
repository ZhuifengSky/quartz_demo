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
		// ����һ��jobDetail
		JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class).withIdentity("simpleJob", "jobGroup").build();
		CronTrigger cronTrigger = (CronTrigger) TriggerBuilder.newTrigger()
				.withIdentity("cronTriggeer", "jobTriggerGroup")
				.usingJobData("int", 12)
		        .usingJobData("String", "������")
		        .usingJobData("boolean", true)
		        //ÿ������1��48ִ��һ��
				.withSchedule(CronScheduleBuilder.cronSchedule("0 48  13 * * ?"))
				.build();
		//ͨ��SchedulerFactory��ȡһ��������ʵ�� 
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();  		  
		Scheduler scheduler = schedulerFactory.getScheduler(); 
		scheduler.scheduleJob(jobDetail, cronTrigger);
		//��������
		scheduler.start();
	}

}
