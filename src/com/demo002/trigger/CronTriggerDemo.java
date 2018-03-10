package com.demo002.trigger;

import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.WeeklyCalendar;

import com.demo002.job.InDbJob;

public class CronTriggerDemo {

	public static void startScheduler(){
		    try{//ͨ��SchedulerFactory��ȡһ��������ʵ�� 
				SchedulerFactory schedulerFactory = new StdSchedulerFactory();  		  
				Scheduler scheduler = schedulerFactory.getScheduler(); 
				// ����һ��jobDetail
				JobDetail jobDetail = JobBuilder.newJob(InDbJob.class).withIdentity("simpleJob", "jobGroup").build();
				//����һ��Calendar
				WeeklyCalendar fridayCalendar = new WeeklyCalendar();
				fridayCalendar.setDayExcluded(2, true);
				//ע��Calendar
				scheduler.addCalendar("fridayCalendar", fridayCalendar, true, true);
				CronTrigger cronTrigger = (CronTrigger) TriggerBuilder.newTrigger()
						.withIdentity("cronTriggeer", "jobTriggerGroup")
						.modifiedByCalendar("fridayCalendar")
						.usingJobData("int", 12)
				        .usingJobData("String", "������")
				        .usingJobData("boolean", true)
				        //ÿ������1��48ִ��һ��
						.withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * * ?"))
						.build();
				
				scheduler.scheduleJob(jobDetail, cronTrigger);
				//��������
				scheduler.start();
				 try {
	                    Thread.sleep(60000);
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }

	                //�رյ�����
	                scheduler.shutdown();
		    }catch(SchedulerException e){
		    	 e.printStackTrace();
		    }
	}

	
	/**
	 * ����ִ��
	 */
    public static void resumeScheduler() {
        try {
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = schedulerFactory.getScheduler();
            JobKey jobKey = new JobKey("simpleJob", "jobGroup");
            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
            if(triggers.size() > 0){
                for (Trigger tg : triggers) {
                    // ���������ж�
                    if ((tg instanceof CronTrigger) || (tg instanceof SimpleTrigger)) {
                        // �ָ�job����
                        scheduler.resumeJob(jobKey);
                    }
                }
                scheduler.start();
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //�رյ�����
                scheduler.shutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    
    
    public static void main(String[] args) {
    	//startScheduler();
    	resumeScheduler();
	}
}
