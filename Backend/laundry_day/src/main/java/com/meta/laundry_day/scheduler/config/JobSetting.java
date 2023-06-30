package com.meta.laundry_day.scheduler.config;

import com.meta.laundry_day.scheduler.run.HankerJobA;
import com.meta.laundry_day.scheduler.run.HankerJobB;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import static org.quartz.JobBuilder.newJob;

@Configuration
public class JobSetting {

    @Autowired
    private Scheduler scheduler;

    @PostConstruct
    public void start(){
        JobDetail jobDetail = buildJobDetail(HankerJobA.class, new HashMap());

        try{
            scheduler.scheduleJob(jobDetail, buildJobTrigger("0 0 0 * * ?"));
        } catch(SchedulerException e){
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void start2(){
        JobDetail jobDetail = buildJobDetail(HankerJobB.class, new HashMap());

        try{
            scheduler.scheduleJob(jobDetail, buildJobTrigger("0 0 22 * * ?"));
        } catch(SchedulerException e){
            e.printStackTrace();
        }
    }

    public Trigger buildJobTrigger(String scheduleExp){
        return TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(scheduleExp)).build();
    }

    public JobDetail buildJobDetail(Class job, Map params){
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAll(params);

        return newJob(job).usingJobData(jobDataMap).build();
    }
}
