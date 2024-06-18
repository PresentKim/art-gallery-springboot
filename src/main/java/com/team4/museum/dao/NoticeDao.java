package com.team4.museum.dao;

import com.team4.museum.util.Pagination;
import com.team4.museum.vo.NoticeVO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

final public class NoticeDao extends BaseDao<NoticeVO> {

    private NoticeDao() {
    }

    private static final NoticeDao instance = new NoticeDao();

    public static NoticeDao getInstance() {
        return instance;
    }

    public List<NoticeVO> selectNoticeList(Pagination pagination) {
        return select("SELECT * FROM notice ORDER BY nseq DESC LIMIT ? OFFSET ? ", pagination::applyTo);
    }

    public List<NoticeVO> selectCategoryNotice(String category, Pagination pagination) {
        return select(
                "SELECT * FROM notice WHERE category = ? ORDER BY nseq DESC LIMIT ? OFFSET ?",
                category,
                pagination.getLimit(),
                pagination.getOffset()
        );
    }

    public void insertNotice(NoticeVO nvo) {
        update(
                "INSERT INTO notice (title, author, content, category) VALUES (?, ?, ?, ?)",
                nvo.getTitle(),
                nvo.getAuthor(),
                nvo.getContent(),
                nvo.getCategory()
        );
    }

    public void updateNotice(NoticeVO notice) {
        update(
                "UPDATE notice SET title = ?, author = ?, content = ?, category = ? WHERE nseq = ?",
                notice.getTitle(),
                notice.getAuthor(),
                notice.getContent(),
                notice.getCategory(),
                notice.getNseq()
        );
    }

    public void deleteNotice(int nseq) {
        update("DELETE FROM notice WHERE nseq = ?", nseq);
    }

    public void plusReadCount(int nseq) {
        update("UPDATE notice SET readcount = readcount + 1 WHERE nseq = ?", nseq);
    }

    public NoticeVO getNotice(int nseq) {
        return selectOne("SELECT * FROM notice WHERE nseq = ?", nseq);
    }

    public List<NoticeVO> searchNoticeList(Pagination pagination, String searchWord) {
        return select(
                "SELECT * FROM notice "
                        + " WHERE title LIKE CONCAT('%', ?, '%') OR content LIKE CONCAT('%', ?, '%') "
                        + " ORDER BY nseq DESC LIMIT ? OFFSET ?",
                searchWord,
                searchWord,
                pagination.getLimit(),
                pagination.getOffset()
        );
    }

    /* 카운트 메서드 */
    public int getAllCount() {
        return selectInt("SELECT COUNT(*) FROM notice");
    }

    public int getNoticeCount(String category) {
        return selectInt("SELECT COUNT(*) FROM notice WHERE category = ?", category);
    }

    public int getSearchCount(String searchWord) {
        return selectInt(
                "SELECT COUNT(*) FROM notice "
                        + "WHERE (title LIKE CONCAT('%', ?, '%')"
                        + " OR content LIKE CONCAT('%', ?, '%')) ",
                searchWord,
                searchWord
        );
    }

    public int getCategoryCount(String category) {
        return selectInt("SELECT COUNT(*) FROM notice WHERE category=?", category);
    }

    public List<NoticeVO> getRecentNotice() {
        return select("SELECT * FROM notice ORDER BY writedate DESC LIMIT 6");
    }

    protected NoticeVO parseVO(ResultSet rs) throws SQLException {
        NoticeVO notice = new NoticeVO();
        notice.setNseq(rs.getInt("nseq"));
        notice.setTitle(rs.getString("title"));
        notice.setAuthor(rs.getString("author"));
        notice.setWritedate(rs.getDate("writedate"));
        notice.setContent(rs.getString("content"));
        notice.setReadcount(rs.getInt("readcount"));
        notice.setCategory(rs.getString("category"));
        return notice;
    }
}
