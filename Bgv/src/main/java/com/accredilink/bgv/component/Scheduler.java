package com.accredilink.bgv.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.accredilink.bgv.service.BgCheckService;
import com.accredilink.bgv.util.ResponseObject;

@Component
public class Scheduler {

	@Autowired
	private BgCheckService bgCheckService;

	 //@Scheduled(cron = "${scheduling.job.cron}")
	public void cronJobSearch() {
		//ResponseObject obj = bgCheckService.submitEmployeeBg();
		//System.out.println(obj.getMessage());
	}
	
	
}
