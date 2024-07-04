package com.team4.artgallery.dao;

import com.team4.artgallery.aspect.annotation.QueryApplied;
import com.team4.artgallery.controller.exception.SqlException;
import com.team4.artgallery.dto.FavoriteDto;
import com.team4.artgallery.util.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface IFavoriteDao {

    /* ========== READ =========== */

    /**
     * 주어진 회원 ID와 예술품 번호에 해당하는 관심 예술품 정보가 존재하는지 확인한다.
     *
     * @param memberId 회원 ID
     * @param aseq     예술품 번호 (artwork sequence)
     * @return 관심 예술품 정보가 존재하면 true, 존재하지 않으면 false
     */
    Boolean isFavorite(@Param("memberId") String memberId, @Param("aseq") int aseq);

    /**
     * 주어진 회원 ID에 해당하는 관심 예술품 목록을 조회한다.
     *
     * @param memberId   회원 ID
     * @param pagination 페이지 정보
     * @return 관심 예술품 목록
     */
    List<FavoriteDto> getFavorites(@Param("memberId") String memberId, @Param("pagination") Pagination pagination);

    /**
     * 주어진 회원 ID에 해당하는 관심 예술품 목록의 총 개수를 조회한다.
     *
     * @param memberId 회원 ID
     * @return 관심 예술품 목록의 총 개수
     */
    int countFavorites(String memberId);


    /* ========== CALL PROCEDURE =========== */

    /**
     * 주어진 회원 ID와 예술품 번호에 해당하는 관심 예술품 정보가 존재하면 제거, 존재하지 않으면 추가한다.
     *
     * @param params 파라미터 맵 (memberId, aseq, result)
     * @return 수정된 행의 수
     * @throws SqlException 쿼리 결과 값이 0인 경우 예외 발생 ({@link QueryApplied} 참조)
     */
    @QueryApplied("관심 예술품 정보를 변경하는 중 오류가 발생했습니다.")
    int toggleFavorite(Map<String, Object> params);

}
