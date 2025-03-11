package egovframework.let.epr.batch;

import java.util.ArrayList;


import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import egovframework.let.epr.service.impl.ExcPerRepMngtDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Spring Batch4 에서는 @EnableBatchProcessing 이 시작 애플리케이션에 선언된다면,
 * JobBuilderFactory, StepBuilderFactory 를 Component 로 등록해서 사용할 수 있다.
 * 이렇게 되면 JobRepository 를 명시적으로 주입받아 사용할 필요가 없게된다.
 *
 * 하지만 Batch5 는 @EnableBatchProcessing 후, JobBuilderFactory, StepBuilderFactory 등은 지원하지 않기에
 * JobBuilder, StepBuilder 를 JobRepository 와 같이 사용하며 transactionManager 를 직접 지정해야 한다.
 **/

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SoftDeleteBatch {
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	private final ExcPerRepMngtDAO excPerRepMngtDAO;

	@Bean(name = "deleteExcPerRepsJob")
	public Job deleteExcPerRepsJob(@Qualifier("sqlSession") SqlSessionFactory sqlSession) {
		return jobBuilderFactory.get("deleteExcPerRepsJob")
			.start(deleteExcPerRepsStep(sqlSession))
			.build();
	}

	@Bean
	public Step deleteExcPerRepsStep(@Qualifier("sqlSession") SqlSessionFactory sqlSession) {
		return stepBuilderFactory.get("deleteExcPerRepsStep")
			 /**
			  	첫 번째 String → reader()에서 읽어오는 데이터 타입
			    두 번째 processor()를 거친 후 writer()로 넘어가는 데이터 타입
			 **/
			.<String, String>chunk(10)
			.reader(reader(sqlSession))
			.writer(writer())
			.build();
	}

	/*** Reader : 특정 테이블에서 작업할 데이터를 가져온다. ***/
	@Bean
	public MyBatisCursorItemReader<String> reader(@Qualifier("sqlSession") SqlSessionFactory sqlSession) {
		/*
		return new MyBatisCursorItemReaderBuilder<String>()
			.queryId("egovframework.let.epr.service.impl.ExcPerRepMngtDAO.selectExcPerRepDelIsNotNullList")
			.build();
		*/
		MyBatisCursorItemReader<String> reader = new MyBatisCursorItemReader<>();
		reader.setSqlSessionFactory(sqlSession);
		reader.setQueryId("ExcPerRepMngtDAO.selectExcPerRepDelIsNotNullList");
		return reader;
	}

	/*** Processor : 가져온 데이터 기반의 작업수행, 그러나 여기서는 단순 삭제로 생략(쿼리로 삭제) ***/
	/***
	@Bean
	public ItemProcessor<예시데이터, 예시데이터> processor() {
		return item -> {
			item.setOrderStatus("PROCESSED"); // 예시로 데이터 변환
			return item;
		};
	}
	 ***/

	/*** Writer : 변환한 데이터를 다시 적용 ( 여기서는 바로 삭제 ) ***/
	@Bean
	public ItemWriter<String> writer() {
		return items -> {
			if (!items.isEmpty()) {
				int deletedRows = excPerRepMngtDAO.deleteExcPerReps(new ArrayList<>(items));
				log.info("Deleted TB_EXC_PER_REC.EXC_PER_REP_SEQ rows : " + deletedRows);
			}
		};
	}

	// JobRegistry 등록
	@Bean
	public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(
		JobRegistry jobRegistry, ApplicationContext applicationContext) {
		JobRegistryBeanPostProcessor processor = new JobRegistryBeanPostProcessor();
		processor.setJobRegistry(jobRegistry);
		processor.setBeanFactory(applicationContext.getAutowireCapableBeanFactory());
		return processor;
	}
}
