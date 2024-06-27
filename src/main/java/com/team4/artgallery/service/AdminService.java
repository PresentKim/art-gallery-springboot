package com.team4.artgallery.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final DataSource dataSource;

    /**
     * 데이터베이스를 초기화합니다.
     */
    @Transactional(rollbackFor = Exception.class)
    public void resetDatabase() throws Exception {
        // DB 초기화를 위해 SQL 파일을 실행
        executeSqlFile("/database/init.sql");
        executeSqlFile("/database/init_view.sql");
        executeSqlFile("/database/insert_artwork.sql");
        executeSqlFile("/database/insert_member.sql");
        executeSqlFile("/database/insert_gallery.sql");
        executeSqlFile("/database/insert_notice.sql");
        executeSqlFile("/database/insert_qna.sql");
    }

    @Transactional(rollbackFor = Exception.class)
    public void executeSqlFile(String sqlFilePath) throws Exception {
        try ( // try-with-resources 구문을 사용하여 자동으로 close() 메서드를 호출
              Connection conn = dataSource.getConnection();
              BufferedReader reader = new BufferedReader(new InputStreamReader(
                      new ClassPathResource(sqlFilePath).getInputStream()
              ))
        ) {

            StringBuilder sql = new StringBuilder();
            String line;
            String delimiter = ";"; // 기본 구분자

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("DELIMITER ")) {
                    delimiter = line.substring(10).trim();
                    continue;
                }
                sql.append(line).append("\n");
                if (line.endsWith(delimiter)) {
                    executeUpdate(conn, sql.toString(), delimiter);
                    sql.setLength(0);
                }
            }

            if (!sql.isEmpty()) {
                executeUpdate(conn, sql.toString(), delimiter);
            }
        }
    }

    private void executeUpdate(Connection conn, String sqlCommand, String delimiter) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            for (String command : sqlCommand.split(delimiter)) {
                if (!command.trim().isEmpty()) {
                    statement.executeUpdate(command.trim());
                }
            }
        }
    }
}
