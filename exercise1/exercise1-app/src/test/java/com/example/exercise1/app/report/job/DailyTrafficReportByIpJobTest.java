package com.example.exercise1.app.report.job;

import com.example.exercise1.app.report.TrafficReportByIpService;
import com.example.exercise1.app.report.TrafficReportController;
import com.example.exercise1.app.request.RequestFactory;

public class DailyTrafficReportByIpJobTest {

	public void test() throws Exception {
		DailyTrafficReportByIpJobConf configuration = new DailyTrafficReportByIpJobConf.Builder().build();
		TrafficReportController controller = new TrafficReportController(new TrafficReportByIpService(RequestFactory.buildRepository()));
		DailyTrafficReportByIpJob service = new DailyTrafficReportByIpJob(controller, configuration);
		service.start();
		
		service.stop();
	}
}
