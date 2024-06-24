package com.team4.museum.dao;

import com.team4.artgallery.dto.NoticeDto;
import com.team4.artgallery.util.Pagination;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

final public class NoticeDao extends BaseDao<NoticeDto> {

    private NoticeDao() {
    }

    private static final NoticeDao instance = new NoticeDao();

    public static NoticeDao getInstance() {
        return instance;
    }

    public List<NoticeDto> selectNoticeList(Pagination pagination) {
        return select("SELECT * FROM notice ORDER BY nseq DESC LIMIT ? OFFSET ? ", pagination::applyTo);
    }

    public List<NoticeDto> selectCategoryNotice(String category, Pagination pagination) {
        return select(
                "SELECT * FROM notice WHERE category = ? ORDER BY nseq DESC LIMIT ? OFFSET ?",
                category,
                pagination.getLimit(),
                pagination.getOffset()
        );
    }

    public void deleteNotice(int nseq) {
        update("DELETE FROM notice WHERE nseq = ?", nseq);
    }

    public List<NoticeDto> searchNoticeList(Pagination pagination, String searchWord) {
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

    public List<NoticeDto> getRecentNotice() {
        return select("SELECT * FROM notice ORDER BY writedate DESC LIMIT 6");
    }

    protected NoticeDto parseDto(ResultSet rs) throws SQLException {
        NoticeDto notice = new NoticeDto();
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
