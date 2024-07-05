package com.team4.artgallery.dao;

import com.team4.artgallery.aspect.annotation.NotEmptyReturn;
import com.team4.artgallery.controller.exception.NotFoundException;
import com.team4.artgallery.controller.exception.SqlException;
import com.team4.artgallery.dto.NoticeDto;
import com.team4.artgallery.dto.filter.NoticeFilter;
import com.team4.artgallery.util.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface INoticeDao {

    /* ========== CREATE =========== */

    /**
     * 소식지 정보를 추가합니다.
     *
     * @param noticeDto 소식지 정보
     * @return 추가된 행의 수
     * @throws SqlException 반환 값이 0인 경우 예외 발생 ({@link NotEmptyReturn} 참조)
     */
    @NotEmptyReturn(value = "소식지 정보를 추가하는 중 오류가 발생했습니다.", exception = SqlException.class)
    int createNotice(NoticeDto noticeDto) throws SqlException;


    /* ========== READ =========== */

    /**
     * 소식지 정보를 가져옵니다.
     *
     * @param aseq 소식지 번호
     * @return 소식지 정보
     */
    NoticeDto getNotice(int aseq);

    /**
     * 검색된 소식지 목록을 가져옵니다.
     *
     * @param filter     검색 조건
     * @param pagination 페이지네이션 정보
     * @return 검색된 소식지 목록
     */
    List<NoticeDto> getNotices(@Param("filter") NoticeFilter filter, @Param("pagination") Pagination pagination);

    /**
     * 검색된 소식지 개수를 가져옵니다.
     *
     * @param filter 검색 조건
     * @return 검색된 소식지 개수
     */
    int countNotices(@Param("filter") NoticeFilter filter);


    /* ========== UPDATE =========== */

    /**
     * 소식지 정보를 수정합니다.
     *
     * @param noticeDto 수정할 소식지 정보
     * @return 수정된 행의 수
     * @throws NotFoundException 반환 값이 0인 경우 예외 발생 ({@link NotEmptyReturn} 참조)
     */
    @NotEmptyReturn(value = "소식지 정보를 찾을 수 없습니다.", exception = NotFoundException.class)
    int updateNotice(NoticeDto noticeDto) throws NotFoundException;

    /**
     * 소식지 조회수를 증가시킵니다.
     *
     * @param nseq 소식지 번호
     * @return 수정된 행의 수
     * @throws NotFoundException 반환 값이 0인 경우 예외 발생 ({@link NotEmptyReturn} 참조)
     */
    @NotEmptyReturn(value = "소식지 정보를 찾을 수 없습니다.", exception = NotFoundException.class)
    int increaseReadCount(int nseq) throws NotFoundException;


    /* ========== DELETE =========== */

    /**
     * 소식지 정보를 삭제합니다.
     *
     * @param nseq 소식지 번호
     * @return 삭제된 행의 수
     * @throws NotFoundException 반환 값이 0인 경우 예외 발생 ({@link NotEmptyReturn} 참조)
     */
    @NotEmptyReturn(value = "소식지 정보를 찾을 수 없습니다.", exception = NotFoundException.class)
    int deleteNotice(int nseq) throws NotFoundException;

    /**
     * 여러 소식지 정보를 삭제합니다.
     *
     * @param aseqList 소식지 번호 목록
     * @return 삭제된 행의 수
     * @throws NotFoundException 반환 값이 0인 경우 예외 발생 ({@link NotEmptyReturn} 참조)
     */
    @NotEmptyReturn(value = "소식지 정보를 찾을 수 없습니다.", exception = NotFoundException.class)
    int deleteNotices(List<Integer> aseqList) throws NotFoundException;

}
