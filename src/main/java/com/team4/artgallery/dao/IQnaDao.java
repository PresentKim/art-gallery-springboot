package com.team4.artgallery.dao;

import com.team4.artgallery.dto.QnaDto;
import com.team4.artgallery.util.Pagination;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface IQnaDao {

    /* ========== CREATE =========== */

    /**
     * 문의글을 작성합니다
     *
     * @param qnaDto 문의글 정보
     * @return 저장된 행의 수
     */
    int createInquiry(QnaDto qnaDto);


    /* ========== READ =========== */

    /**
     * 문의글을 가져옵니다
     *
     * @param qseq 문의글 번호
     * @return 문의글 정보
     */
    QnaDto getInquiry(int qseq);

    /**
     * 전체 문의글 목록을 가져옵니다
     *
     * @param pagination 페이지네이션 정보
     * @return 문의글 목록
     */
    List<QnaDto> getInquiries(Pagination pagination);

    /**
     * 검색된 문의글 목록을 가져옵니다
     *
     * @param filter     검색 조건
     * @param pagination 페이지네이션 정보
     * @return 검색된 문의글 목록
     */
    List<QnaDto> searchInquiries(@Param("filter") Filter filter, @Param("pagination") Pagination pagination);

    /**
     * 전체 문의글 수를 가져옵니다
     *
     * @return 전체 문의글 수
     */
    int countInquiries();

    /**
     * 검색된 문의글 수를 가져옵니다
     *
     * @param filter 검색 조건
     * @return 검색된 문의글 수
     */
    int countSearchInquiries(@Param("filter") Filter filter);


    /* ========== UPDATE =========== */

    /**
     * 문의글을 수정합니다
     *
     * @param qnaDto 문의글 정보
     * @return 수정된 행의 수
     */
    int updateInquiry(QnaDto qnaDto);

    /**
     * 문의글에 답변을 작성합니다
     *
     * @param qseq  문의글 번호
     * @param reply 답변 내용
     * @return 수정된 행의 수
     */
    int updateReply(@Param("qseq") int qseq, @Param("updateReply") String reply);

    /* ========== DELETE =========== */

    /**
     * 문의글을 삭제합니다
     *
     * @param qseq 문의글 번호
     * @return 삭제된 행의 수
     */
    int deleteInquiry(int qseq);

    /**
     * 여러 문의글을 삭제합니다.
     *
     * @param qseqList 문의글 번호 목록
     * @return 삭제된 행의 수
     */
    int deleteInquiries(List<Integer> qseqList);


    /* ========== INNER CLASS =========== */

    @Getter
    @Setter
    @AllArgsConstructor
    class Filter {

        private String replyyn;
        private String search;

        public boolean reply() {
            return "Y".equals(replyyn);
        }

        public boolean hasReply() {
            return replyyn != null && !replyyn.isEmpty();
        }

        public boolean hasSearch() {
            return search != null && !search.isEmpty();
        }

        public boolean isEmpty() {
            return !hasReply() && !hasSearch();
        }

        public String toUrlParam() {
            if (isEmpty()) {
                return "";
            }

            List<String> params = new ArrayList<>();

            if (hasReply()) {
                params.add("replyyn=" + replyyn);
            }

            if (hasSearch()) {
                params.add("search=" + search);
            }

            return String.join("&", params);
        }

    }

}
