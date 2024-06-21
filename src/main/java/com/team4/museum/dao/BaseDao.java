package com.team4.museum.dao;

import com.team4.museum.util.Db;
import com.team4.museum.util.Db.CallParameterSetter;
import com.team4.museum.util.Db.CallResultMapper;
import com.team4.museum.util.Db.ParameterSetter;
import com.team4.museum.util.Db.ResultMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class BaseDao<T> {

    /**
     * 결과행이 없는 SQL 쿼리를 실행하고 영향을 받은 행의 수를 반환합니다.
     */
    public int update(String query) {
        return Db.executeUpdate(query);
    }

    /**
     * 결과행이 없는 SQL 쿼리를 실행하고 영향을 받은 행의 수를 반환합니다.
     *
     * @param paramSetter SQL 쿼리를 준비하는 람다식
     */
    public int update(String query, ParameterSetter paramSetter) {
        return Db.executeUpdate(query, paramSetter);
    }

    /**
     * 결과행이 없는 SQL 쿼리를 실행하고 영향을 받은 행의 수를 반환합니다.
     *
     * @param params SQL 쿼리에 전달할 파라미터 배열
     */
    public int update(String query, Object... params) {
        return Db.executeUpdate(query, prepareSetterFrom(params));
    }

    /**
     * SQL 쿼리를 실행하고 결과를 반환합니다.
     */
    public List<T> select(String query) {
        return Db.executeSelect(query, this::parseDto);
    }

    /**
     * SQL 쿼리를 실행하고 결과를 반환합니다.
     *
     * @param params SQL 쿼리에 전달할 파라미터 배열
     */
    public List<T> select(String query, Object... params) {
        return Db.executeSelect(query, prepareSetterFrom(params), this::parseDto);
    }

    /**
     * SQL 쿼리를 실행하고 결과를 반환합니다.
     *
     * @param paramSetter SQL 쿼리를 준비하는 람다식
     */
    public List<T> select(String query, ParameterSetter paramSetter) {
        return Db.executeSelect(query, paramSetter, this::parseDto);
    }

    /**
     * SQL 쿼리를 실행하고 결과를 반환합니다.
     *
     * @param paramSetter  SQL 쿼리를 준비하는 람다식
     * @param resultMapper SQL 쿼리 결과를 매핑하는 람다식
     */
    public <R> List<R> select(String query, ParameterSetter paramSetter, ResultMapper<R> resultMapper) {
        return Db.executeSelect(query, paramSetter, resultMapper);
    }

    /**
     * SQL 쿼리를 실행하고 결과 중 첫 번째 행을 반환합니다.
     */
    public int selectInt(String query) {
        return selectInt(query, prepareSetterFrom());
    }

    /**
     * SQL 쿼리를 실행하고 결과 중 첫 번째 행을 반환합니다.
     *
     * @param params SQL 쿼리에 전달할 파라미터 배열
     */
    public int selectInt(String query, Object... params) {
        return selectInt(query, prepareSetterFrom(params));
    }

    /**
     * SQL 쿼리를 실행하고 결과 중 첫 번째 행을 반환합니다.
     *
     * @param paramSetter SQL 쿼리를 준비하는 람다식
     */
    public int selectInt(String query, ParameterSetter paramSetter) {
        Integer result = Db.executeSelectOne(query, paramSetter, this::parseInt);
        return result == null ? 0 : result;
    }

    /**
     * SQL 쿼리를 실행하고 결과 중 첫 번째 행을 반환합니다.
     *
     * @param param SQL 쿼리에 전달할 단일 파라미터, 혹은 파라미터 배열
     */
    public T selectOne(String query, Object param) {
        return Db.executeSelectOne(query, prepareSetterFrom(param), this::parseDto);
    }

    /**
     * SQL 프로시저 쿼리를 실행하고 결과를 반환합니다.
     *
     * @param paramSetter  SQL 쿼리를 준비하는 람다식
     * @param resultMapper 프로시저 결과를 추출하는 람다식
     */
    public <R> R call(String query, CallParameterSetter paramSetter, CallResultMapper<R> resultMapper) {
        return Db.executeCall(query, paramSetter, resultMapper);
    }

    private static ParameterSetter prepareSetterFrom() {
        return pstmt -> {
        };
    }

    private static ParameterSetter prepareSetterFrom(Object param) {
        return pstmt -> pstmt.setObject(1, param);
    }

    private static ParameterSetter prepareSetterFrom(Object[] params) {
        return pstmt -> {
            int i = 1;
            for (Object param : params) {
                pstmt.setObject(i++, param);
            }
        };
    }

    /**
     * ResultSet의 데이터를 통해 정수를 반환합니다.
     *
     * @param rs ResultSet 객체
     * @return int 첫번쨰 칼럼의 정수 값
     */
    protected int parseInt(ResultSet rs) throws SQLException {
        return rs.getInt(1);
    }

    /**
     * ResultSet의 데이터를 통해 Dto 객체를 생성합니다.
     *
     * @param rs ResultSet 객체
     * @return T Dto 객체
     */
    abstract protected T parseDto(ResultSet rs) throws SQLException;

}
