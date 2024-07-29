package com.team4.artgallery.service;

import com.team4.artgallery.controller.exception.SqlException;
import com.team4.artgallery.entity.ArtworkEntity;
import com.team4.artgallery.entity.FavoriteEntity;
import com.team4.artgallery.repository.FavoriteRepository;
import com.team4.artgallery.util.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    /**
     * 회원의 관심 예술품 목록을 가져옵니다.
     *
     * @param memberId   회원 ID
     * @param pagination 페이지네이션 정보
     * @return 관심 예술품 목록
     */
    public Page<ArtworkEntity> getFavoriteArtworks(String memberId, Pagination pagination) {
        return favoriteRepository.findAllByMemberId(memberId, pagination.toPageable()).map(FavoriteEntity::artwork);
    }

    /**
     * 주어진 회원 ID와 예술품 번호에 해당하는 관심 예술품 정보가 존재하는지 확인합니다.
     *
     * @param memberId 회원 ID
     * @param aseq     예술품 번호 (artwork sequence)
     * @return 관심 예술품 정보가 존재하면 true, 존재하지 않으면 false
     */
    public Boolean isFavorite(String memberId, int aseq) {
        return favoriteRepository.existsByMemberIdAndArtworkAseq(memberId, aseq);
    }

    /**
     * 주어진 회원 ID와 예술품 번호에 해당하는 관심 예술품 정보를 추가합니다.
     *
     * @param memberId 회원 ID
     * @param aseq     예술품 번호 (artwork sequence)
     * @throws SqlException 관심 예술품 정보 추가에 실패한 경우 예외 발생
     */
    public void createFavorite(String memberId, int aseq) throws SqlException {
        favoriteRepository.createByMemberIdAndArtworkAseq(memberId, aseq);
    }

    /**
     * 주어진 회원 ID와 예술품 번호에 해당하는 관심 예술품 정보를 제거합니다.
     *
     * @param memberId 회원 ID
     * @param aseq     예술품 번호 (artwork sequence)
     * @throws SqlException 관심 예술품 정보 제거에 실패한 경우 예외 발생
     */
    public void deleteFavorite(String memberId, int aseq) throws SqlException {
        favoriteRepository.deleteByMemberIdAndArtworkAseq(memberId, aseq);
    }

}
