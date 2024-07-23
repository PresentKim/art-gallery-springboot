package com.team4.artgallery.service;

import com.team4.artgallery.controller.exception.SqlException;
import com.team4.artgallery.dao.IFavoriteDao;
import com.team4.artgallery.dto.FavoriteDto;
import com.team4.artgallery.util.Pagination;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FavoriteService {

    private final IFavoriteDao favoriteDao;

    public FavoriteService(IFavoriteDao favoriteDao) {
        this.favoriteDao = favoriteDao;
    }

    /**
     * 회원의 관심 예술품 목록을 가져옵니다.
     *
     * @param memberId   회원 ID
     * @param pagination 페이지네이션 정보
     * @return 관심 예술품 목록
     */
    public List<FavoriteDto> getFavorites(String memberId, Pagination pagination) {
        return favoriteDao.getFavorites(memberId, pagination.setItemCount(favoriteDao.countFavorites(memberId)));
    }

    /**
     * 주어진 회원 ID와 예술품 번호에 해당하는 관심 예술품 정보가 존재하는지 확인합니다.
     *
     * @param memberId 회원 ID
     * @param aseq     예술품 번호 (artwork sequence)
     * @return 관심 예술품 정보가 존재하면 true, 존재하지 않으면 false
     */
    public Boolean isFavorite(String memberId, int aseq) {
        return favoriteDao.isFavorite(memberId, aseq);
    }

    /**
     * 주어진 회원 ID와 예술품 번호에 해당하는 관심 예술품 정보를 추가합니다.
     *
     * @param memberId 회원 ID
     * @param aseq     예술품 번호 (artwork sequence)
     * @throws SqlException 관심 예술품 정보 추가에 실패한 경우 예외 발생
     */
    public void createFavorite(String memberId, int aseq) throws SqlException {
        favoriteDao.createFavorite(memberId, aseq);
    }

    /**
     * 주어진 회원 ID와 예술품 번호에 해당하는 관심 예술품 정보를 제거합니다.
     *
     * @param memberId 회원 ID
     * @param aseq     예술품 번호 (artwork sequence)
     * @throws SqlException 관심 예술품 정보 제거에 실패한 경우 예외 발생
     */
    public void deleteFavorite(String memberId, int aseq) throws SqlException {
        favoriteDao.deleteFavorite(memberId, aseq);
    }

    /**
     * 주어진 회원 ID와 예술품 번호에 해당하는 관심 예술품 정보가 존재하면 제거, 존재하지 않으면 추가한다.
     *
     * @param memberId 회원 ID
     * @param aseq     예술품 번호 (artwork sequence)
     * @return 관심 예술품을 추가한 경우엔 true, 제거한 경우엔 false
     * @throws SqlException 관심 예술품 정보 변경에 실패한 경우 예외 발생
     */
    public Boolean toggleFavorite(String memberId, int aseq) throws SqlException {
        // 파라미터 맵 생성 (memberId, aseq, result)
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        params.put("aseq", aseq);
        params.put("result", null);

        // 관심 예술품 정보 추가/제거
        favoriteDao.toggleFavorite(params);

        // 결과 반환
        return (boolean) params.get("result");
    }

}
