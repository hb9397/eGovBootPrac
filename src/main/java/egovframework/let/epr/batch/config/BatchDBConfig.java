// package egovframework.let.epr.batch.config;
//
// import java.sql.Connection;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.sql.Statement;
//
// import org.springframework.boot.context.properties.ConfigurationProperties;
// import org.springframework.boot.jdbc.DataSourceBuilder;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.scheduling.annotation.Async;
//
// import javax.annotation.PostConstruct;
// import javax.sql.DataSource;
//
// import lombok.extern.slf4j.Slf4j;

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

// @Slf4j
// @Configuration
// public class BatchDBConfig {
//
//     @Bean(name = "batchDataSource")
//     @ConfigurationProperties(prefix = "spring.datasource-batch")
//     public DataSource batchDataSource() {
//         // spring.datasource-meta 하위 값을 기반으로 아래의 DataSourceBuilder 를 Create 한다.
//
//         return DataSourceBuilder.create().build();
//     }
//
//     @Async
//     @PostConstruct
//     public void verifyBatchDBConnection() {
//         try (Connection connection = batchDataSource().getConnection()) {
//             if (connection != null && !connection.isClosed()) {
//                 log.info("BatchDB 연결 성공: {}", connection);
//                 fetchBatchDBTableInfo(connection);
//             }
//         } catch (SQLException e) {
//             log.error("BatchDB 연결 실패", e);
//         }
//     }
//
//     private void fetchBatchDBTableInfo(Connection connection) {
//         String query = "SELECT TABLE_NAME FROM USER_TABLES";
//
//         try (Statement stmt = connection.createStatement();
//              ResultSet rs = stmt.executeQuery(query)) {
//
//             log.info("📌 BatchDB의 모든 테이블 목록:");
//
//             boolean hasTables = false;
//             while (rs.next()) { // ✅ 모든 테이블 출력
//                 hasTables = true;
//                 log.info("📌 - {}", rs.getString("TABLE_NAME"));
//             }
//
//             if (!hasTables) {
//                 log.info("📌 BatchDB에 테이블이 없습니다.");
//             }
//
//         } catch (SQLException e) {
//             log.error("❌ BatchDB 테이블 정보를 가져오는 중 오류 발생", e);
//         }
//     }
// }
