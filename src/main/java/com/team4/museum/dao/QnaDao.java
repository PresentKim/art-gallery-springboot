package com.team4.museum.dao;

import com.team4.artgallery.dto.QnaDto;
import com.team4.artgallery.util.Pagination;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class QnaDao extends BaseDao<QnaDto> {

    private QnaDao() {
    }

    private static final QnaDao instance = new QnaDao();

    public static QnaDao getInstance() {
        return instance;
    }

    /**
     * 문의글 목록을 조회한다.
     *
     * @param pagination 페이지 정보
     * @return 문의글 목록
     */
    public List<QnaDto> selectQna(Pagination pagination) {
        return select(
                "SELECT * FROM qna ORDER BY qseq DESC LIMIT ? OFFSET ?",
                pagination::applyTo
        );
    }

    /**
     * 답변 존재 여부에 따른 문의글 목록을 조회한다.
     *
     * @param pagination 페이지 정보
     * @param isReply    답변이 있는지 여부 (Y: 답변 있음, N: 답변 없음)
     * @return 문의글 목록
     */
    public List<QnaDto> selectQna(Pagination pagination, String isReply) {
        String query;
        if (isReply.equals("Y")) {
            query = "SELECT * FROM qna WHERE COALESCE(reply, '') <> '' ORDER BY qseq DESC LIMIT ? OFFSET ?";
        } else {
            query = "SELECT * FROM qna WHERE COALESCE (reply, '') = '' ORDER BY qseq DESC LIMIT ? OFFSET ?";
        }
        return select(query, pagination::applyTo);
    }

    /**
     * 문의글을 조회한다.
     *
     * @param qseq 문의글 번호 (qna sequence)
     * @return 문의글 정보
     */
    public QnaDto getQna(int qseq) {
        return selectOne("SELECT * FROM qna WHERE qseq = ?", qseq);
    }

    /**
     * 문의글을 등록한다.
     *
     * @param qnaDto 문의글 정보 (qna sequence)
     * @return 등록된 문의글 번호
     */
    public int insertQna(QnaDto qnaDto) {
        update(
                "INSERT INTO qna (title, content, email, phone, publicyn, pwd) VALUES (?, ?, ?, ?, ? ,?)",
                qnaDto.getTitle(),
                qnaDto.getContent(),
                qnaDto.getEmail(),
                qnaDto.getPhone(),
                qnaDto.getPublicyn(),
                qnaDto.getPwd()
        );

        return selectInt("SELECT LAST_INSERT_ID()");
    }

    /**
     * 문의글을 수정한다.
     *
     * @param qnaDto 문의글 정보 (qna sequence)
     * @return 수정된 문의글 번호
     */
    public int updateQna(QnaDto qnaDto) {
        int qseq = qnaDto.getQseq();
        update(
                "UPDATE qna SET title = ?, content = ?, email = ?, phone = ?, publicyn = ?, pwd = ? WHERE qseq = ?",
                qnaDto.getTitle(),
                qnaDto.getContent(),
                qnaDto.getEmail(),
                qnaDto.getPhone(),
                qnaDto.getPublicyn(),
                qnaDto.getPwd(),
                qseq
        );

        return qseq;
    }

    /**
     * 문의글을 삭제한다.
     *
     * @param qseq 문의글 번호 (qna sequence)
     */
    public void deleteQna(int qseq) {
        update("DELETE FROM qna WHERE qseq = ?", qseq);
    }

    /**
     * 문의글에 답변을 등록한다.
     *
     * @param qseq  문의글 번호 (qna sequence)
     * @param reply 답변 내용
     */
    public void updateQnaReply(int qseq, String reply) {
        update("UPDATE qna SET reply = ? WHERE qseq = ?", reply, qseq);
    }

    /**
     * 문의글 총 개수를 조회한다.
     *
     * @return 문의글 총 개수
     */
    public int getAllCount() {
        return selectInt("SELECT COUNT(*) FROM qna");
    }

    public int getSearchCount(String searchWord) {
        return selectInt(
                "SELECT COUNT(*) FROM qna "
                        + " WHERE title LIKE CONCAT('%', ?, '%') OR content LIKE CONCAT('%', ?, '%') ",
                searchWord,
                searchWord
        );
    }

    public int getReplyCount() {
        return selectInt("SELECT COUNT(*) FROM qna WHERE COALESCE(reply, '') <> '' ");
    }

    /**
     * 문의글 검색 결과를 조회한다.
     *
     * @param pagination 페이지 정보
     * @param searchWord 검색어
     * @return 문의글 목록
     */
    public List<QnaDto> searchQna(Pagination pagination, String searchWord) {
        return select(
                "SELECT * FROM qna "
                        + " WHERE title LIKE CONCAT('%', ?, '%') OR content LIKE CONCAT('%', ?, '%') "
                        + " ORDER BY qseq DESC LIMIT ? OFFSET ?",
                searchWord,
                searchWord,
                pagination.getLimit(),
                pagination.getOffset()
        );
    }

    /**
     * ResultSet의 데이터를 통해 QnaDto 객체를 생성합니다.
     *
     * @param rs ResultSet 객체
     * @return QnaDto Dto 객체
     */
    protected QnaDto parseDto(ResultSet rs) throws SQLException {
        QnaDto qnaDto = new QnaDto();
        qnaDto.setQseq(rs.getInt("qseq"));
        qnaDto.setTitle(rs.getString("title"));
        qnaDto.setContent(rs.getString("content"));
        qnaDto.setEmail(rs.getString("email"));
        qnaDto.setPwd(rs.getString("pwd"));
        qnaDto.setPhone(rs.getString("phone"));
        qnaDto.setReply(rs.getString("reply"));
        qnaDto.setWritedate(rs.getDate("writedate"));
        qnaDto.setPublicyn(rs.getString("publicyn"));
        return qnaDto;
    }

}
