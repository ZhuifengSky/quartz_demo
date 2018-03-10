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
		//通过SchedulerFactory获取一个调度器实例 
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();  		  
		Scheduler scheduler = schedulerFactory.getScheduler(); 
		// 定义一个jobDetail
		JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class).withIdentity("simpleJob", "jobGroup").build();
		//定义一个Calendar
		WeeklyCalendar fridayCalendar = new WeeklyCalendar();
		fridayCalendar.setDayExcluded(6, true);
		//注册Calendar
		scheduler.addCalendar("fridayCalendar", fridayCalendar, true, true);
		CronTrigger cronTrigger = (CronTrigger) TriggerBuilder.newTrigger()
				.withIdentity("cronTriggeer", "jobTriggerGroup")
				.modifiedByCalendar("fridayCalendar")
				.usingJobData("int", 12)
		        .usingJobData("String", "哈哈哈")
		        .usingJobData("boolean", true)
		        //每天下午1点48执行一次
				.withSchedule(CronScheduleBuilder.cronSchedule("0 48  14 * * ?"))
				.build();
		
		scheduler.scheduleJob(jobDetail, cronTrigger);
		//启动调度
		scheduler.start();
	}

}
