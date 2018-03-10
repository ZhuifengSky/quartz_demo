package com.demo002.job;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class InDbJob implements Job{

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		System.out.println("dbJob start"+ new Date());
		String calName = context.getTrigger().getCalendarName();
		System.out.println(calName);
	}

}
