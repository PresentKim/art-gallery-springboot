package com.team4.artgallery.dao;

import com.team4.artgallery.aspect.annotation.QueryApplied;
import com.team4.artgallery.dto.QnaDto;
import com.team4.artgallery.dto.filter.QnaFilter;
import com.team4.artgallery.util.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IQnaDao {

    /* ========== CREATE =========== */

    /**
     * 문의글 정보를 추가합니다.
     *
     * @param qnaDto 문의글 정보
     * @return 저장된 행의 수
     */
    @QueryApplied("문의글 정보를 추가하는 중 오류가 발생했습니다.")
    int createInquiry(QnaDto qnaDto);


    /* ========== READ =========== */

    /**
     * 문의글 정보를 가져옵니다
     *
     * @param qseq 문의글 번호
     * @return 문의글 정보
     */
    QnaDto getInquiry(int qseq);

    /**
     * 검색된 문의글 목록을 가져옵니다
     *
     * @param filter     검색 조건
     * @param pagination 페이지네이션 정보
     * @return 검색된 문의글 목록
     */
    List<QnaDto> getInquiries(@Param("filter") QnaFilter filter, @Param("pagination") Pagination pagination);

    /**
     * 검색된 문의글 수를 가져옵니다
     *
     * @param filter 검색 조건
     * @return 검색된 문의글 수
     */
    int countInquiries(@Param("filter") QnaFilter filter);


    /* ========== UPDATE =========== */

    /**
     * 문의글 정보를 수정합니다.
     *
     * @param qnaDto 문의글 정보
     * @return 수정된 행의 수
     */
    @QueryApplied("문의글 정보를 수정하는 중 오류가 발생했습니다.")
    int updateInquiry(QnaDto qnaDto);

    /**
     * 문의글 답변을 수정합니다.
     *
     * @param qseq  문의글 번호
     * @param reply 답변 내용
     * @return 수정된 행의 수
     */
    @QueryApplied("문의글 답변을 수정하는 중 오류가 발생했습니다.")
    int updateReply(@Param("qseq") int qseq, @Param("updateReply") String reply);

    /* ========== DELETE =========== */

    /**
     * 문의글 정보를 삭제합니다
     *
     * @param qseq 문의글 번호
     * @return 삭제된 행의 수
     */
    @QueryApplied("문의글 정보를 삭체하는 중 오류가 발생했습니다.")
    int deleteInquiry(int qseq);

    /**
     * 여러 문의글 정보를 삭제합니다.
     *
     * @param qseqList 문의글 번호 목록
     * @return 삭제된 행의 수
     */
    @QueryApplied("문의글 정보를 삭체하는 중 오류가 발생했습니다.")
    int deleteInquiries(List<Integer> qseqList);

}
