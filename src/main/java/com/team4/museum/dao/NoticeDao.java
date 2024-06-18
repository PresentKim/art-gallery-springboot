package com.team4.museum.dao;

import static com.team4.museum.util.Db.executeSelect;
import static com.team4.museum.util.Db.executeSelectOne;
import static com.team4.museum.util.Db.executeUpdate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.team4.museum.util.Db;
import com.team4.museum.util.Pagination;
import com.team4.museum.vo.NoticeVO;

final public class NoticeDao {

	private NoticeDao() {
	}

	private static final NoticeDao instance = new NoticeDao();

	public static NoticeDao getInstance() {
		return instance;
	}

	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	public List<NoticeVO> selectNoticeList(Pagination pagination) {
		return executeSelect(
				"SELECT * FROM notice ORDER BY nseq DESC LIMIT ? OFFSET ? ",
				pagination::applyTo,
				NoticeDao::extractNoticeVO);
	}

	// ORDER BY nseq DESC
	/*
	 * public NoticeVO selectNotice(int nseq) { return executeSelectOne(
	 * "SELECT * FROM notice WHERE nseq = ?", pstmt -> pstmt.setInt(1, nseq),
	 * NoticeDAO::extractNoticeVO); }
	 */

	public List<NoticeVO> selectCategoryNotice(String category, Pagination pagination) {
		return executeSelect(
				"SELECT * FROM notice WHERE category = ? ORDER BY nseq DESC LIMIT ? OFFSET ?",
				pstmt -> {
					pstmt.setString(1, category);
					pagination.applyTo(pstmt, 2, 3);
				},
				NoticeDao::extractNoticeVO);
	}

	public int insertNotice(NoticeVO nvo) {
		return executeUpdate(
				"INSERT INTO notice (title, author, content, category) VALUES (?, ?, ?, ?)",
				pstmt -> {
					pstmt.setString(1, nvo.getTitle());
					pstmt.setString(2, nvo.getAuthor());
					pstmt.setString(3, nvo.getContent());
					pstmt.setString(4, nvo.getCategory());
				});
	}

	public int updateNotice(NoticeVO notice) {
		return executeUpdate(
				"UPDATE notice SET title = ?, author = ?, content = ?, category = ? WHERE nseq = ?",
				pstmt -> {
					pstmt.setString(1, notice.getTitle());
					pstmt.setString(2, notice.getAuthor());
					pstmt.setString(3, notice.getContent());
					pstmt.setString(4, notice.getCategory());
					pstmt.setInt(5, notice.getNseq());
				});
	}

	public int deleteNotice(int nseq) {
		return executeUpdate(
				"DELETE FROM notice WHERE nseq = ?",
				pstmt -> pstmt.setInt(1, nseq));
	}

	public void plusReadCount(int nseq) {
		con = Db.getConnection();
		String sql = "UPDATE notice SET readcount=readcount+1 WHERE nseq=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, nseq);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Db.close(con, pstmt, rs);
		}
	}

	public NoticeVO getNotice(int nseq) {
		NoticeVO nvo = null;
		con = Db.getConnection();
		String sql = "SELECT * FROM notice WHERE nseq=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, nseq);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				nvo = extractNoticeVO(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Db.close(con, pstmt, rs);
		}
		return nvo;
	}

	public List<NoticeVO> searchNoticeList(Pagination pagination, String searchWord) {
		return executeSelect("SELECT * FROM notice "
				+ " WHERE title LIKE CONCAT('%', ?, '%') OR content LIKE CONCAT('%', ?, '%') "
				+ " ORDER BY nseq DESC LIMIT ? OFFSET ?",
				pstmt -> {
					pstmt.setString(1, searchWord);
					pstmt.setString(2, searchWord);
					pagination.applyTo(pstmt, 3, 4);
				},
				NoticeDao::extractNoticeVO);
	}

	private static NoticeVO extractNoticeVO(ResultSet rs) throws SQLException {
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
	
	/* 카운트 메서드 */
	public int getAllCount() {
		return executeSelectOne(
				"SELECT COUNT(*) AS cnt FROM notice",
				rs -> rs.getInt("cnt"));
	}

	
	public int getNoticeCount(String category) {
		return executeSelectOne(
				"SELECT COUNT(*) AS cnt FROM notice WHERE category = ?",
				pstmt -> pstmt.setString(1, category),
				rs -> rs.getInt("cnt"));
	}

	public int getSearchCount(String searchWord) {
		return executeSelectOne("SELECT COUNT(*) as cnt FROM notice "
				+ "WHERE (title LIKE CONCAT('%', ?, '%') OR content LIKE CONCAT('%', ?, '%')) ",
				pstmt->{
					pstmt.setString(1, searchWord);
					pstmt.setString(2, searchWord);
				},rs -> rs.getInt("cnt"));
	}

	public int getCategoryCount(String category) {
		return executeSelectOne("SELECT COUNT(*) as cnt FROM notice WHERE category=?",
				pstmt->	pstmt.setString(1, category),
				rs -> rs.getInt("cnt"));
	}
	
	public List<NoticeVO> getRecentNotice() {
	      return executeSelect("SELECT * FROM notice ORDER BY writedate DESC LIMIT 6",
	            NoticeDao::extractNoticeVO);
	   }
}
