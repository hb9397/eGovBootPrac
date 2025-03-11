package egovframework.let.epr.batch.metaData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class MetaDBConfig {

    @Bean(name = "metaDataSource")
    @ConfigurationProperties(prefix = "spring.datasource-meta")
    public DataSource metaDBSource() {
        // spring.datasource-meta 하위 값을 기반으로 아래의 DataSourceBuilder 를 Create 한다.

        return DataSourceBuilder.create().build();
    }

    @Bean
    public PlatformTransactionManager metaTransactionManager(@Qualifier("metaDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Async
    @PostConstruct
    public void verifyMetaDBConnection() {
        try (Connection connection = metaDBSource().getConnection()) {
            if (connection != null && !connection.isClosed()) {
                log.info("MetaDB 연결 성공: {}", connection);
                fetchMetaDBTableInfo(connection);
            }
        } catch (SQLException e) {
            log.error("MetaDB 연결 실패", e);
        }
    }

    private void fetchMetaDBTableInfo(Connection connection) {
        String query = "SELECT TABLE_NAME FROM USER_TABLES";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            log.info("📌 MetaDB의 모든 테이블 목록:");

            boolean hasTables = false;
            while (rs.next()) { // ✅ 모든 테이블 출력
                hasTables = true;
                log.info("📌 - {}", rs.getString("TABLE_NAME"));
            }

            if (!hasTables) {
                log.info("📌 MetaDB에 테이블이 없습니다.");
            }

        } catch (SQLException e) {
            log.error("❌ MetaDB 테이블 정보를 가져오는 중 오류 발생", e);
        }
    }
}
