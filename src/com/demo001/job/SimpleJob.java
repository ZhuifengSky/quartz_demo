package com.demo001.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map.Entry;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

/**
 * ¼òµ¥µÄjobÀà 
 *
 */
public class SimpleJob implements Job{

	@Override
	public void execute(JobExecutionContext jobContext) throws JobExecutionException {
		 System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
	        .format(new Date())+"Hello Quartz!");
		 /*JobKey jobKey = jobContext.getJobDetail().getKey();
         JobDataMap dataMap = jobContext.getJobDetail().getJobDataMap();
         int num = dataMap.getInt("int");
         String str = dataMap.getString("String");
	     boolean boo = dataMap.getBoolean("boolean");
         System.err.println(num+"==="+num);*/
         JobKey jobTriggerKey = jobContext.getTrigger().getJobKey();
         JobDataMap triggerDataMap = jobContext.getTrigger().getJobDataMap();
         Iterator<Entry<String, Object>> it = triggerDataMap.entrySet().iterator();  
         System.out.println(jobTriggerKey);
         while (it.hasNext()) {  
          Entry<String, Object> entry = it.next();  
          Object key = entry.getKey();  
          Object value = entry.getValue();  
          System.out.println("key=" + key + " value=" + value);
         }
	}

}
