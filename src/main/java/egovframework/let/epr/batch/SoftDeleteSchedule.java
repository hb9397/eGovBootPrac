package egovframework.let.epr.batch;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SoftDeleteSchedule {
	private final JobLauncher jobLauncher;
	private final JobRegistry jobRegistry;

	//@Scheduled(cron = "0 */53 * * * *", zone = "Asia/Seoul")
	@Scheduled(cron = "10 * * * * *", zone = "Asia/Seoul")
	public void runDeleteExcPerRepsJob() throws Exception {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = dateFormat.format(new Date());

		JobParameters jobParameters = new JobParametersBuilder()
			.addString("date", date)
			.toJobParameters();

		log.info("RunDeleteExcPerRepsJob  " + date);

		jobLauncher.run(jobRegistry.getJob("deleteExcPerRepsJob"), jobParameters);
	}
}
