package com.team4.artgallery.dao;

import com.team4.artgallery.aspect.annotation.NotEmptyReturn;
import com.team4.artgallery.aspect.annotation.NotNullReturn;
import com.team4.artgallery.controller.exception.NotFoundException;
import com.team4.artgallery.controller.exception.SqlException;
import com.team4.artgallery.dto.ArtworkDto;
import com.team4.artgallery.dto.filter.ArtworkFilter;
import com.team4.artgallery.util.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IArtworkDao {

    /* ========== CREATE =========== */

    /**
     * 예술품 정보를 추가합니다.
     *
     * @param artworkDto 예술품 정보
     * @return 추가된 행의 수
     * @throws SqlException 반환 값이 0인 경우 예외 발생 ({@link NotEmptyReturn} 참조)
     */
    @NotEmptyReturn(value = "예술품 정보를 추가하는 중 오류가 발생했습니다.", exception = SqlException.class)
    int createArtwork(ArtworkDto artworkDto) throws SqlException;


    /* ========== READ =========== */

    /**
     * 예술품 정보를 가져옵니다.
     *
     * @param aseq 예술품 번호
     * @return 예술품 정보
     * @throws NotFoundException 반환 값이 null인 경우 예외 발생 ({@link NotNullReturn} 참조)
     */
    @NotNullReturn(value = "예술품 정보를 찾을 수 없습니다.", exception = NotFoundException.class)
    ArtworkDto getArtwork(int aseq);

    /**
     * 검색된 예술품 목록을 가져옵니다.
     *
     * @param filter     검색 조건
     * @param pagination 페이지네이션 정보
     * @return 검색된 예술품 목록
     */
    List<ArtworkDto> getArtworks(@Param("filter") ArtworkFilter filter, @Param("pagination") Pagination pagination);

    /**
     * 랜덤 예술품 목록을 가져옵니다.
     *
     * @param count 가져올 예술품 개수
     * @return 랜덤 예술품 목록
     */
    List<ArtworkDto> getRandomArtworks(int count);

    /**
     * 검색된 예술품 개수를 가져옵니다.
     *
     * @param filter 검색 조건
     * @return 검색된 예술품 개수
     */
    int countArtworks(@Param("filter") ArtworkFilter filter);


    /* ========== UPDATE =========== */

    /**
     * 예술품 정보를 수정합니다.
     *
     * @param artworkDto 수정할 예술품 정보
     * @return 수정된 행의 수
     * @throws NotFoundException 반환 값이 0인 경우 예외 발생 ({@link NotEmptyReturn} 참조)
     */
    @NotEmptyReturn(value = "예술품 정보를 찾을 수 없습니다.", exception = NotFoundException.class)
    int updateArtwork(ArtworkDto artworkDto) throws NotFoundException;

    /**
     * 예술품 전시 여부를 토글합니다.
     *
     * @param aseq 예술품 번호
     * @return 수정된 행의 수
     * @throws NotFoundException 반환 값이 0인 경우 예외 발생 ({@link NotEmptyReturn} 참조)
     */
    @NotEmptyReturn(value = "예술품 정보를 찾을 수 없습니다.", exception = NotFoundException.class)
    int toggleArtworkDisplay(int aseq) throws NotFoundException;


    /* ========== DELETE =========== */

    /**
     * 예술품 정보를 삭제합니다.
     *
     * @param aseq 예술품 번호
     * @return 삭제된 행의 수
     * @throws NotFoundException 반환 값이 0인 경우 예외 발생 ({@link NotEmptyReturn} 참조)
     */
    @NotEmptyReturn(value = "예술품 정보를 찾을 수 없습니다.", exception = NotFoundException.class)
    int deleteArtwork(int aseq) throws NotFoundException;

}
