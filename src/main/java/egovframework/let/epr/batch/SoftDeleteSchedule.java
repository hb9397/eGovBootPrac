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

	/** 매분의 10초 마다 시행 -> 테스트용 **/
	//@Scheduled(cron = "10 * * * * *", zone = "Asia/Seoul")
	/** 새벽 2시마다 시행 **/
	@Scheduled(cron = "0 0 2 * * *", zone = "Asia/Seoul")
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
