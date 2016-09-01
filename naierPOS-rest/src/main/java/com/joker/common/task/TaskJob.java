package com.joker.common.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("taskjob")
public class TaskJob {

	@Scheduled(cron = "0 0/5 * * * ?")  
    public void job1() {  
        System.out.println("任务进行中。。。");  
    }  
}
