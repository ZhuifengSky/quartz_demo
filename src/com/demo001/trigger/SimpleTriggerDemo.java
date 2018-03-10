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
		// ����һ��jobDetail
		JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class).withIdentity("simpleJob", "jobGroup").build();
		// ����һ����������ÿ5�봥��һ���ܼ�3��
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity("simpleTriggeer", "jobTriggerGroup")
				.usingJobData("int", 12)
		        .usingJobData("String", "������")
		        .usingJobData("boolean", true)
				.withSchedule(SimpleScheduleBuilder.simpleSchedule()
				.withIntervalInSeconds(5).withRepeatCount(0))
				.build();		
		//ͨ��SchedulerFactory��ȡһ��������ʵ�� 
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();  		  
		Scheduler scheduler = schedulerFactory.getScheduler(); 
		scheduler.scheduleJob(jobDetail, trigger);
		//��������
		scheduler.start();
		
	}

}
