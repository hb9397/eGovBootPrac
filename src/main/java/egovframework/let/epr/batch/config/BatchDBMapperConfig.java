// package egovframework.let.epr.batch.config;
//
// import java.io.IOException;
//
// import javax.annotation.PostConstruct;
// import javax.sql.DataSource;
//
// import org.apache.ibatis.session.SqlSessionFactory;
// import org.mybatis.spring.SqlSessionFactoryBean;
// import org.mybatis.spring.SqlSessionTemplate;
// import org.springframework.beans.factory.annotation.Qualifier;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.Lazy;
// import org.springframework.context.annotation.PropertySource;
// import org.springframework.context.annotation.PropertySources;
// import org.springframework.core.env.Environment;
// import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
// import org.springframework.jdbc.datasource.DataSourceTransactionManager;
// import org.springframework.jdbc.support.lob.DefaultLobHandler;
// import org.springframework.transaction.PlatformTransactionManager;
//
// import lombok.RequiredArgsConstructor;

/**
 * --- 사용안함 ---
 * 전자정부 프레임워크에서 DAO 를 구현할 때 사용하는 상위 클래스
 * EgovComAbstractDAO, EgovAbstractMapper 등에서 기본 DB로 설정된
 * SqlSessionFactory 를 @Resource 로 강제하고 있기 때문에 다중 DB로 설정을 변경
 * 하려면 해당 상위 개체 부터 커스터마이징해야한다.
 *
 * 그래서 지금은 단일 DB로 진행, 이거는 Spring Batch 다중 DB 를 이용할 때,
 * 참고하는 코드 정도로.,.,..,
 * 아니면 시간 나면 위의 상위객체까지 커스텀 해서 사용해보기
 * --- 사용안함 ---
 **/

// @RequiredArgsConstructor
// @Configuration
// @PropertySources({
// 	@PropertySource("classpath:/application.yaml")
// })
// public class BatchDBMapperConfig {
//
// 	private final Environment env;
//
// 	private String dbType;
//
// 	@PostConstruct
// 	void init() {
// 		dbType = env.getProperty("Globals.DbType");
// 	}
//
// 	@Bean
// 	@Lazy
// 	public DefaultLobHandler batchLobHandler() {
// 		return new DefaultLobHandler();
// 	}
//
// 	@Bean(name = {"batchSqlSession", "egov.batchSqlSession"})
// 	public SqlSessionFactory batchSqlSession(@Qualifier("batchDataSource") DataSource dataSource) throws Exception {
// 		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
// 		sqlSessionFactoryBean.setDataSource(dataSource);
//
// 		PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
//
// 		sqlSessionFactoryBean.setConfigLocation(
// 			pathMatchingResourcePatternResolver
// 				.getResource("classpath:/egovframework/mapper/config/mapper-config.xml"));
//
// 		try {
// 			sqlSessionFactoryBean.setMapperLocations(
// 				pathMatchingResourcePatternResolver
// 					.getResources("classpath:/egovframework/mapper/batch/**/*_" + dbType + ".xml"));
// 		} catch (IOException e) {
// 			// TODO Exception 처리 필요
// 		}
//
// 		return sqlSessionFactoryBean.getObject();
// 	}
//
// 	@Bean(name = "batchSqlSessionTemplate")
// 	public SqlSessionTemplate egovBatchSqlSessionTemplate(@Qualifier("batchSqlSession") SqlSessionFactory sqlSession) {
// 		SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSession);
// 		return sqlSessionTemplate;
// 	}
//
// 	@Bean(name = "batchTransactionManager")
// 	public PlatformTransactionManager egovBatchTransactionManager(@Qualifier("batchDataSource") DataSource dataSource) {
// 		return new DataSourceTransactionManager(dataSource);
// 	}
// }
