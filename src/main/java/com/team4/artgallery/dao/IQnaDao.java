package com.team4.artgallery.dao;

import com.team4.artgallery.aspect.annotation.NotEmptyReturn;
import com.team4.artgallery.controller.exception.NotFoundException;
import com.team4.artgallery.controller.exception.SqlException;
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
     * @throws SqlException 반환 값이 0인 경우 예외 발생 ({@link NotEmptyReturn} 참조)
     */
    @NotEmptyReturn(value = "문의글 정보를 추가하는 중 오류가 발생했습니다.", exception = SqlException.class)
    int createInquiry(QnaDto qnaDto) throws SqlException;


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
     * @throws NotFoundException 반환 값이 0인 경우 예외 발생 ({@link NotEmptyReturn} 참조)
     */
    @NotEmptyReturn(value = "문의글 정보를 찾을 수 없습니다.", exception = NotFoundException.class)
    int updateInquiry(QnaDto qnaDto) throws NotFoundException;

    /**
     * 문의글 답변을 수정합니다.
     *
     * @param qseq  문의글 번호
     * @param reply 답변 내용
     * @return 수정된 행의 수
     * @throws NotFoundException 반환 값이 0인 경우 예외 발생 ({@link NotEmptyReturn} 참조)
     */
    @NotEmptyReturn(value = "문의글 정보를 찾을 수 없습니다.", exception = NotFoundException.class)
    int updateReply(@Param("qseq") int qseq, @Param("updateReply") String reply) throws NotFoundException;

    /* ========== DELETE =========== */

    /**
     * 문의글 정보를 삭제합니다
     *
     * @param qseq 문의글 번호
     * @return 삭제된 행의 수
     * @throws NotFoundException 반환 값이 0인 경우 예외 발생 ({@link NotEmptyReturn} 참조)
     */
    @NotEmptyReturn(value = "문의글 정보를 찾을 수 없습니다.", exception = NotFoundException.class)
    int deleteInquiry(int qseq) throws NotFoundException;

    /**
     * 여러 문의글 정보를 삭제합니다.
     *
     * @param qseqList 문의글 번호 목록
     * @return 삭제된 행의 수
     * @throws NotFoundException 반환 값이 0인 경우 예외 발생 ({@link NotEmptyReturn} 참조)
     */
    @NotEmptyReturn(value = "문의글 정보를 찾을 수 없습니다.", exception = NotFoundException.class)
    int deleteInquiries(List<Integer> qseqList) throws NotFoundException;

}
