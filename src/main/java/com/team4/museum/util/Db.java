package com.team4.museum.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Db {

    /**
     * 데이터베이스 연결을 위한 Connection 객체를 반환합니다.
     * <p>
     * 반환된 Connection 객체를 사용한 후에는 반드시 close() 메서드를 호출하여 자원을 반환해야 합니다.
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Context initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/MysqlDB/museum");
            conn = ds.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void executeSqlFile(String sqlFilePath) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            connection.setAutoCommit(false); // 자동 커밋 비활성화

            // SQL 파일 읽기
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(sqlFilePath);
            if (inputStream == null) {
                throw new IllegalArgumentException("SQL file not found: " + sqlFilePath);
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
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
                        String sqlCommand = sql.toString();
                        if (!sqlCommand.trim().isEmpty()) {
                            addBatch(statement, sqlCommand, delimiter);
                        }
                        sql.setLength(0); // StringBuilder 초기화
                    }
                }

                // 남은 마지막 쿼리 실행
                if (sql.length() > 0) {
                    addBatch(statement, sql.toString(), delimiter);
                }

                // 배치 실행
                statement.executeBatch();
                connection.commit();
                System.out.println("SQL file executed successfully : " + sqlFilePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addBatch(Statement statement, String sqlCommand, String delimiter) throws SQLException {
        for (String command : sqlCommand.split(delimiter)) {
            if (!command.trim().isEmpty()) {
                statement.addBatch(command.trim());
            }
        }
    }

    /**
     * SQL 쿼리를 실행하고 영향을 받은 행의 수를 반환합니다.
     */
    public static int executeUpdate(String query) {
        return executeUpdate(query, pstmt -> {
        });
    }

    /**
     * SQL 쿼리를 실행하고 영향을 받은 행의 수를 반환합니다.
     *
     * @param paramSetter SQL 쿼리를 준비하는 람다식
     */
    public static int executeUpdate(String query, ParameterSetter paramSetter) {
        int result = 0;

        try ( // try-with-resources
              Connection conn = getConnection();
              PreparedStatement pstmt = conn.prepareStatement(query)) {

            paramSetter.accept(pstmt);
            result = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * SQL 쿼리를 실행하고 결과를 반환합니다.
     *
     * @param resultMapper SQL 쿼리 결과를 매핑하는 람다식
     */
    public static <T> List<T> executeSelect(String query, ResultMapper<T> resultMapper) {
        return executeSelect(query, pstmt -> {
        }, resultMapper);
    }

    /**
     * SQL 쿼리를 실행하고 결과를 반환합니다.
     *
     * @param paramSetter  SQL 쿼리를 준비하는 람다식
     * @param resultMapper SQL 쿼리 결과를 매핑하는 람다식
     */
    public static <T> List<T> executeSelect(String query, ParameterSetter paramSetter, ResultMapper<T> resultMapper) {
        List<T> list = new ArrayList<>();

        try ( // try-with-resources
              Connection conn = getConnection();
              PreparedStatement pstmt = prepareStatement(conn, query, paramSetter);
              ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                list.add(resultMapper.apply(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * SQL 쿼리를 실행하고 결과 중 첫 번째 행을 반환합니다.
     *
     * @param paramSetter  SQL 쿼리를 준비하는 람다식
     * @param resultMapper SQL 쿼리 결과를 매핑하는 람다식
     */
    public static <T> T executeSelectOne(String query, ParameterSetter paramSetter, ResultMapper<T> resultMapper) {
        List<T> list = executeSelect(query, paramSetter, resultMapper);
        return list.isEmpty() ? null : list.get(0);
    }

    /**
     * SQL 프로시저 쿼리를 실행하고 결과를 반환합니다.
     *
     * @param resultMapper 프로시저 결과를 추출하는 람다식
     */
    public static <T> T executeCall(String query, CallParameterSetter paramSetter, CallResultMapper<T> resultMapper) {
        T result = null;

        try ( // try-with-resources
              Connection conn = getConnection();
              CallableStatement pstmt = conn.prepareCall(query)) {

            paramSetter.accept(pstmt);
            pstmt.execute();
            result = resultMapper.extract(pstmt);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Connection 객체와 query 문자열로 생성된 PreparedStatement 객체에 대해 파라미터 설정을 수행합니다.
     *
     * @param paramSetter SQL 쿼리를 준비하는 람다식
     * @return 준비된 PreparedStatement 객체
     * @throws SQLException
     */
    private static PreparedStatement prepareStatement(Connection conn, String query, ParameterSetter paramSetter)
            throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(query);
        paramSetter.accept(pstmt);
        return pstmt;
    }

    /**
     * {@link java.util.function.Consumer}의 역할을 수행
     * <p>
     * {@link java.sql.PreparedStatement}를 처리하는 메서드에 대한 명시적인 의미를 부여하고,
     * {@link java.sql.SQLException}을 던질 수 있도록 하기 위해 별도로 정의한 함수형 인터페이스입니다.
     */
    @FunctionalInterface
    public interface ParameterSetter {
        void accept(PreparedStatement pstmt) throws SQLException;
    }

    /**
     * {@link java.util.function.Function}의 역할을 수행
     * <p>
     * {@link java.sql.ResultSet}를 처리하고 결과를 반환하는 메서드에 대한 명시적인 의미를 부여하고,
     * {@link java.sql.SQLException}을 던질 수 있도록 하기 위해 별도로 정의한 함수형 인터페이스입니다.
     */
    @FunctionalInterface
    public interface ResultMapper<T> {
        T apply(ResultSet rs) throws SQLException;
    }

    /**
     * {@link java.util.function.Consumer}의 역할을 수행
     * <p>
     * {@link java.sql.CallableStatement}를 처리하는 메서드에 대한 명시적인 의미를 부여하고,
     * {@link java.sql.SQLException}을 던질 수 있도록 하기 위해 별도로 정의한 함수형 인터페이스입니다.
     */
    @FunctionalInterface
    public interface CallParameterSetter {
        void accept(CallableStatement cstmt) throws SQLException;
    }

    /**
     * {@link java.util.function.Function}의 역할을 수행
     * <p>
     * {@link java.sql.CallableStatement}를 처리하고 결과를 반환하는 메서드에 대한 명시적인 의미를 부여하고,
     * {@link java.sql.SQLException}을 던질 수 있도록 하기 위해 별도로 정의한 함수형 인터페이스입니다.
     */
    @FunctionalInterface
    public interface CallResultMapper<T> {
        T extract(CallableStatement cstmt) throws SQLException;
    }

}
