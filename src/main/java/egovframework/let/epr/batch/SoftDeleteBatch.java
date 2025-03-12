package egovframework.let.epr.batch;

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
import egovframework.let.epr.service.vo.ResExcPerRepVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * --- JobRegistryBeanPostProcessor ---
 * Spring Batch4 에서는 @EnableBatchProcessing 이 시작 애플리케이션에 선언된다면,
 * JobBuilderFactory, StepBuilderFactory 를 Component 로 등록해서 사용할 수 있다.
 * 이렇게 되면 JobRepository 를 명시적으로 주입받아 사용할 필요가 없게된다.
 *
 * 하지만 Batch5 는 @EnableBatchProcessing 후, JobBuilderFactory, StepBuilderFactory 등은 지원하지 않기에
 * JobBuilder, StepBuilder 를 JobRepository 와 같이 사용하며 transactionManager 를 직접 지정해야 한다.
 **/

/**
 * --- @Qualifier("sqlSession") ---
 * 기존 시도는 다중 DB로,
 * 애플리케이션의 CRUD 를 담당하는 기본 DB DEV_1 스키마 관련 Config 의 Bean을 @Primary, @Bean(name="~") + @Qualifier 로 설정하고,
 * DEV_1 스키마에서 SoftDelete 된 데이터를 삭제 데이터를 일정기간 보관하고 진짜 삭제하는 BATCH_DEV_1 스키마 관련
 * Config 의 Bean 을 @Bean(name="~") + @Qualifier 로 설정해 Batch 작업을 담당하는 해당 클래스에서 @Qualifier("BeanName") 으로
 * 작업별 DB로 접근 스키마를 다르게 하려 했으나 아래와 같은 이유로 실패해 단일 DB 형태로 사용한다.
 * 그래서 @Qualifier("sqlSession") 은 이에 대한 흔적으로 남긴다.

 - 전자정부 프레임워크에서 DAO 를 구현할 때 사용하는 상위 클래스
 - EgovComAbstractDAO, EgovAbstractMapper 등에서 기본 DB로 설정된 SqlSessionFactory 를 @Resource 로 강제하고 있기 때문에
 - 다중 DB로 설정을 변경하려면 해당 상위 개체 부터 커스터마이징해야한다.
 - 그래서 지금은 단일 DB로 진행, 이거는Spring Batch 다중 DB 를 이용할 때, 참고하는 코드 정도로.,.,..,
 - 아니면 시간 나면 위의 상위객체까지 커스텀 해서 사용해보기
 **/

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SoftDeleteBatch {
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	private final ExcPerRepMngtDAO excPerRepMngtDAO;

	// JobRegistry 등록
	@Bean
	public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(
		JobRegistry jobRegistry, ApplicationContext applicationContext) {
		JobRegistryBeanPostProcessor processor = new JobRegistryBeanPostProcessor();
		processor.setJobRegistry(jobRegistry);
		processor.setBeanFactory(applicationContext.getAutowireCapableBeanFactory());
		return processor;
	}

	@Bean(name = "deleteExcPerRepsJob")
	public Job deleteExcPerRepsJob(
		@Qualifier("sqlSession") SqlSessionFactory sqlSession
	) {
		return jobBuilderFactory.get("deleteExcPerRepsJob")
			.start(deleteExcPerRepsStep(sqlSession))
			.build();
	}

	@Bean
	public Step deleteExcPerRepsStep(
		@Qualifier("sqlSession") SqlSessionFactory sqlSession
	) {
		return stepBuilderFactory.get("deleteExcPerRepsStep")
			 /**
			  	첫 번째 String → reader()에서 읽어오는 데이터 타입
			    두 번째 processor()를 거친 후 writer()로 넘어가는 데이터 타입
			 **/
			.<ResExcPerRepVO, ResExcPerRepVO>chunk(10)
			.reader(selectExcPerRepDelIsNotNullList(sqlSession))
			.writer(insertGbExcPerRepList(sqlSession))
			.build();
	}

	/*** Reader : 특정 테이블에서 작업할 데이터를 가져온다. ***/
	@Bean
	public MyBatisCursorItemReader<ResExcPerRepVO> selectExcPerRepDelIsNotNullList(
		@Qualifier("sqlSession") SqlSessionFactory sqlSession
	) {
		/*
		return new MyBatisCursorItemReaderBuilder<String>()
			.queryId("egovframework.let.epr.service.impl.ExcPerRepMngtDAO.selectExcPerRepDelIsNotNullList")
			.build();
		*/
		MyBatisCursorItemReader<ResExcPerRepVO> reader = new MyBatisCursorItemReader<>();
		reader.setSqlSessionFactory(sqlSession);
		reader.setQueryId("ExcPerRepMngtDAO.selectExcPerRepDelIsNotNullList");
		return reader;
	}

	/*** Processor : 가져온 데이터 기반의 작업수행, 그러나 여기서는 단순 삭제로 생략(쿼리로 삭제) ***/
	/***
	@Bean
	public ItemProcessor<예시데이터, 예시데이터> tbExcPerRepInDelDataProcessor() {
		return item -> {
			item.setOrderStatus("PROCESSED"); // 예시로 데이터 변환
			return item;
		};
	}
	 ***/

	/*** Writer : 변환한 데이터를 다시 적용 ( 데이터 이동 ) ***/
	@Bean
	public ItemWriter<ResExcPerRepVO> insertGbExcPerRepList(@Qualifier("sqlSession") SqlSessionFactory sqlSession) {
		return items -> {
			if (!items.isEmpty()) {
				log.info("Deleted TB_EXC_PER_REC.EXC_PER_REP_SEQ rows : " );
			}
		};
	}

	/*** Writer : 변환한 데이터를 다시 적용 ( 여기서는 바로 삭제 ) ***/
	/*@Bean
	public ItemWriter<String> gbTbExcPerRepInDelDataWriter() {
		return items -> {
			if (!items.isEmpty()) {
				int deletedRows = excPerRepMngtDAO.deleteExcPerReps(new ArrayList<>(items));
				log.info("Deleted TB_EXC_PER_REC.EXC_PER_REP_SEQ rows : " + deletedRows);
			}
		};
	}*/

}
