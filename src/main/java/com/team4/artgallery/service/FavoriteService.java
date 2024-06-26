package com.team4.artgallery.service;

import com.team4.artgallery.dao.IFavoriteDao;
import com.team4.artgallery.dto.FavoriteDto;
import com.team4.artgallery.util.Pagination;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    @Delegate
    private final IFavoriteDao favoriteDao;

    /**
     * 회원의 관심 예술품 목록을 가져옵니다.
     *
     * @param memberId 회원 ID
     * @param page     페이지 번호
     * @return 관심 예술품 목록과 페이지네이션 정보
     */
    public Pagination.Pair<FavoriteDto> getFavorites(String memberId, int page) {
        Pagination pagination = new Pagination()
                .setCurrentPage(page)
                .setItemCount(countFavorites(memberId))
                .setUrlTemplate("/mypage/favorite?page=%d");
        return pagination.pair(getFavorites(memberId, pagination));
    }

    /**
     * 주어진 회원 ID와 예술품 번호에 해당하는 관심 예술품 정보가 존재하면 제거, 존재하지 않으면 추가한다.
     *
     * @param memberId 회원 ID
     * @param aseq     예술품 번호 (artwork sequence)
     * @return 관심 예술품을 추가한 경우엔 true, 제거한 경우엔 false
     */
    public Boolean toggleFavorite(String memberId, int aseq) {
        // 파라미터 맵 생성 (memberId, aseq, result)
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        params.put("aseq", aseq);
        params.put("result", null);

        // 관심 예술품 정보 추가/제거
        System.out.println(toggleFavorite(params));

        // 결과 반환
        return (boolean) params.get("result");
    }

}
