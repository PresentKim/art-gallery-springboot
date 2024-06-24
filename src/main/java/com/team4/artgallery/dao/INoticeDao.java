package com.team4.artgallery.dao;

import com.team4.artgallery.dto.NoticeDto;
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
     */
    int create(NoticeDto noticeDto);


    /* ========== READ =========== */

    /**
     * 소식지 정보를 가져옵니다.
     *
     * @param aseq 소식지 번호
     * @return 소식지 정보
     */
    NoticeDto getNotice(int aseq);

    /**
     * 전체 소식지 목록을 가져옵니다.
     *
     * @param pagination 페이지네이션 정보
     * @return 소식지 목록
     */
    default List<NoticeDto> getNotices(Pagination pagination) {
        return getNotices(null, pagination);
    }

    /**
     * 카테고리의 소식지 목록을 가져옵니다.
     *
     * @param category   소식지 카테고리
     * @param pagination 페이지네이션 정보
     * @return 검색된 소식지 목록
     */
    List<NoticeDto> getNotices(@Param("category") String category, @Param("pagination") Pagination pagination);

    /**
     * 전체 소식지 개수를 가져옵니다.
     *
     * @return 전체 소식지 개수
     */
    default int countNotices() {
        return countNotices(null);
    }

    /**
     * 카테고리의 소식지 개수를 가져옵니다.
     *
     * @param category 소식지 카테고리
     * @return 검색된 소식지 개수
     */
    int countNotices(@Param("category") String category);


    /* ========== UPDATE =========== */

    /**
     * 소식지 정보를 수정합니다.
     *
     * @param noticeDto 수정할 소식지 정보
     * @return 수정된 행의 수
     */
    int updateNotice(NoticeDto noticeDto);


    /* ========== DELETE =========== */

    /**
     * 소식지 정보를 삭제합니다.
     *
     * @param aseq 소식지 번호
     * @return 삭제된 행의 수
     */
    int deleteNotice(int aseq);

    /**
     * 여러 소식지 정보를 삭제합니다.
     *
     * @param aseqList 소식지 번호 목록
     * @return 삭제된 행의 수
     */
    int deleteNotices(List<Integer> aseqList);

}
