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
import org.quartz.impl.calendar.WeeklyCalendar;

import com.demo001.job.SimpleJob;

public class CronTriggerWithCalendarDemo {

	public static void main(String[] args) throws SchedulerException {
		//ͨ��SchedulerFactory��ȡһ��������ʵ�� 
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();  		  
		Scheduler scheduler = schedulerFactory.getScheduler(); 
		// ����һ��jobDetail
		JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class).withIdentity("simpleJob", "jobGroup").build();
		//����һ��Calendar
		WeeklyCalendar fridayCalendar = new WeeklyCalendar();
		fridayCalendar.setDayExcluded(6, true);
		//ע��Calendar
		scheduler.addCalendar("fridayCalendar", fridayCalendar, true, true);
		CronTrigger cronTrigger = (CronTrigger) TriggerBuilder.newTrigger()
				.withIdentity("cronTriggeer", "jobTriggerGroup")
				.modifiedByCalendar("fridayCalendar")
				.usingJobData("int", 12)
		        .usingJobData("String", "������")
		        .usingJobData("boolean", true)
		        //ÿ������1��48ִ��һ��
				.withSchedule(CronScheduleBuilder.cronSchedule("0 48  14 * * ?"))
				.build();
		
		scheduler.scheduleJob(jobDetail, cronTrigger);
		//��������
		scheduler.start();
	}

}
