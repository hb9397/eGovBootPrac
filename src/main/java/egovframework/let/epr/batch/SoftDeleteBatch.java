package egovframework.let.epr.batch;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.mybatis.spring.batch.builder.MyBatisCursorItemReaderBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import egovframework.let.epr.service.impl.ExcPerRepMngtDAO;
import egovframework.let.epr.service.vo.ExcPerRep;
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
			.start(ExcPerRepMoveToGBStep(sqlSession))
			.next(DeleteExcPerRepsStep(sqlSession))
			.next(DeleteGbExcPerRepsStep(sqlSession))
			.build();
	}

	/*** ExcPerRepMoveToGBStep 시작 ***/
	@Bean
	public Step ExcPerRepMoveToGBStep(
		@Qualifier("sqlSession") SqlSessionFactory sqlSession
	) {
		return stepBuilderFactory.get("excPerRepMoveToGBStep")
			 /**
			  	첫 번째 String → reader()에서 읽어오는 데이터 타입
			    두 번째 processor()를 거친 후 writer()로 넘어가는 데이터 타입
			 **/
			.<ResExcPerRepVO, ResExcPerRepVO>chunk(10)
			.reader(selectExcPerRepDelIsNotNullList(sqlSession))
			.writer(insertGbExcPerRepList(sqlSession))
			.build();
	}

	/** Reader : 특정 테이블에서 작업할 데이터를 가져온다. **/
	/** TB_EXC_REC_REP 에서 DEL_DATE, DEL_ID 가 NULL 이 아닌(softDelete 된) 데이터 조회 **/
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

	/** Writer : 변환한 데이터를 다시 적용 ( 데이터 이동 ) **/
	/** softDelete 된 데이터를 GB_TB_EXC_REC_REP(삭제된 데이터 보관 테이블) 로 이동 **/
	@Bean
	public ItemWriter<ResExcPerRepVO> insertGbExcPerRepList(@Qualifier("sqlSession") SqlSessionFactory sqlSession) {
		return items -> {
			if (!items.isEmpty()) {
				int insertRows = excPerRepMngtDAO.insertGbExcPerRepList(new ArrayList<>(items));
				log.info("Deleted Data Into GB_TB_EXC_PER_REC.EXC_PER_REP rows : " + insertRows);
			}
		};
	}
	/*** ExcPerRepMoveToGBStep 끝 ***/

	/*** DeleteExcPerRepsStep 시작 ***/
	/**
	 * TB_EXC_REC_REP 에서 DEL_DATE, DEL_ID 가 NULL 이 아닌(softDelete 된) 데이터 조회
	 * ExcPerRepMoveToGBStep 에서 이동한 데이터를 조회해서 삭제한다.
	 * 이때, Reader 는 기존 selectExcPerRepDelIsNotNullList 이용
	 **/
	@Bean
	public Step DeleteExcPerRepsStep(
		@Qualifier("sqlSession") SqlSessionFactory sqlSession
	){

		return stepBuilderFactory.get("DeleteExcPerRepsStep")
			.<ResExcPerRepVO, String> chunk(10)
			.reader(selectExcPerRepDelIsNotNullList(sqlSession))
			.processor(getDeletedExcPerRepSeq())
			.writer(deleteExcPerReps())
			.build();
	}

	/** Processor : Reader 에서 추출한 데이터로 변환 및 기타 작업 **/
	/**
	 * TB_EXC_REC_REP 에서 GB_EXC_REC_REP 로 이동해서 TB_EXC_REC_REP 에서 삭제할 데이터를 조회한 결과에서
	 * EXC_PER_REP_SEQ 만 조회해서 추출한다.
	 **/
	 @Bean
	 public ItemProcessor<ResExcPerRepVO, String> getDeletedExcPerRepSeq() {
		return ExcPerRep::getExcPerRepSeq;
		// == return item -> item.getExcPerRepSeq();
	 }

	/** Writer : 변환한 데이터를 다시 적용 ( 여기서는 바로 삭제 ) **/
	/**
	 * Processor 에서 추출한 EXC_PER_REP_SEQ 로
	 * TB_EXC_REC_REP 에서 GB_EXC_REC_REP 로 이동한 TB_EXC_REC_REP 데이터 삭제
	 **/
	@Bean
	public ItemWriter<String> deleteExcPerReps() {
		return items -> {
			if (!items.isEmpty()) {
				int deletedRows = excPerRepMngtDAO.deleteExcPerReps(new ArrayList<>(items));
				log.info("Deleted TB_EXC_PER_REC.EXC_PER_REP_SEQ rows : " + deletedRows);
			}
		};
	}
	/*** DeleteExcPerRepsStep 끝 ***/

	/*** DeleteGbExcPerRepsStep 시작 ***/
	/**
	 * GB_TB_EXC_REC_REP 에서 DEL_DATE 가 현 시점에서 1년 이상 지났다면 실제로 삭제 하기 위해서
	 * DEL_DATE 가 1년 이상인 데이터의 EXC_PER_REP_SEQ 조회 후, Processor 는 생략하고
	 * 바로 writer 에서 삭제 (사용할 컬럼만 조회하고 이를 이용해서 바로 삭제하기 때문에 Processor 생략)
	 **/
	@Bean
	public Step DeleteGbExcPerRepsStep(
		@Qualifier("sqlSession") SqlSessionFactory sqlSession
	){

		return stepBuilderFactory.get("DeleteExcPerRepsStep")
			.<String, String> chunk(10)
			.reader(selectDeletingGbExcPerReps(sqlSession))
			.writer(deletingGbExcPerReps())
			.build();
	}

	/** Reader : 특정 테이블에서 작업할 데이터를 가져온다. **/
	/**
	 * GB_TB_EXC_REC_REP 에서 DEL_DATE 가 1년 이상인 데이터의 PK 조회
	 **/
	@Bean
	public MyBatisCursorItemReader<String> selectDeletingGbExcPerReps(
		@Qualifier("sqlSession") SqlSessionFactory sqlSession
	) {

		return new MyBatisCursorItemReaderBuilder<String>()
			.sqlSessionFactory(sqlSession)
			.queryId("ExcPerRepMngtDAO.selectDeletingGbExcPerReps")
			.build();
	}

	/** Writer : 변환한 데이터를 다시 적용 ( 여기서는 바로 삭제 ) **/
	/**
	 * selectDeletingGbExcPerReps 데이터 삭제
	 **/
	@Bean
	public ItemWriter<String> deletingGbExcPerReps() {
		return items -> {
			if (!items.isEmpty()) {
				int deletedRows = excPerRepMngtDAO.deleteGbExcPerReps(new ArrayList<>(items));
				log.info("Deleted GB_TB_EXC_PER_REC.EXC_PER_REP_SEQ rows : " + deletedRows);
			}
		};
	}
	/*** DeleteExcPerRepsStep 끝 ***/
}
