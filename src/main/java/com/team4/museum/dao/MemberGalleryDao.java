package com.team4.museum.dao;

import static com.team4.museum.util.Db.executeSelect;
import static com.team4.museum.util.Db.executeSelectOne;
import static com.team4.museum.util.Db.executeUpdate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.team4.museum.util.Pagination;
import com.team4.museum.vo.MemberGalleryVO;

public class MemberGalleryDao extends BaseDao<MemberGalleryVO>{
	
	private MemberGalleryDao() {
	}

	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	private static MemberGalleryDao instance = new MemberGalleryDao();

	public static MemberGalleryDao getInstance() {
		return instance;
	}

	
	public MemberGalleryVO getMemberGalleryOne(int mseq) {
		return executeSelectOne(
				"SELECT * FROM member_gallery_view WHERE mseq=?",
				pstmt -> pstmt.setInt(1, mseq),
				MemberGalleryDao::extractMemberGalleryVO);
	}

	public int insertMemberGallery(MemberGalleryVO mgvo) {
		return executeUpdate(
				"INSERT INTO member_gallery (author, title, content, image, savefilename) VALUES (?, ?, ?, ?, ?)",
				pstmt -> {
					pstmt.setString(1, mgvo.getAuthorId());
					pstmt.setString(2, mgvo.getTitle());
					pstmt.setString(3, mgvo.getContent());
					pstmt.setString(4, mgvo.getImage());
					pstmt.setString(5, mgvo.getSavefilename());
				});
	}

	public void updateMemberGallery(MemberGalleryVO mgvo) {
		executeUpdate(
				"UPDATE member_gallery SET title=?, content=?, image=?, savefilename=? WHERE mseq=?",
				pstmt -> {
					pstmt.setString(1, mgvo.getTitle());
					pstmt.setString(2, mgvo.getContent());
					pstmt.setString(3, mgvo.getImage());
					pstmt.setString(4, mgvo.getSavefilename());
					pstmt.setInt(5, mgvo.getMseq());
				});
	}
	
	public void updateMemberGalleryWithoutImg(MemberGalleryVO mgvo) {
		executeUpdate(
				"UPDATE member_gallery SET title=?, content=? WHERE mseq=?",
				pstmt -> {
					pstmt.setString(1, mgvo.getTitle());
					pstmt.setString(2, mgvo.getContent());
					pstmt.setInt(3, mgvo.getMseq());
				});
	}

	public void deleteMemberGallery(int mseq) {
		executeUpdate(
				"DELETE FROM member_gallery WHERE mseq=?",
				pstmt -> pstmt.setInt(1, mseq));
	}


	public int getGalleryAllCount() {
		return executeSelectOne(
				"SELECT COUNT(*) AS cnt FROM member_gallery",
				rs -> rs.getInt("cnt"));
	}

	public List<MemberGalleryVO> getAllGallery(Pagination pagination) {
		return executeSelect(
				"SELECT * FROM member_gallery_view ORDER BY mseq DESC LIMIT ? OFFSET ?",
				pagination::applyTo,
				MemberGalleryDao::extractMemberGalleryVO);
	}

	private static MemberGalleryVO extractMemberGalleryVO(ResultSet rs) throws SQLException {
		MemberGalleryVO mgvo = new MemberGalleryVO();
		mgvo.setMseq(rs.getInt("mseq"));
		mgvo.setAuthorId(rs.getString("author_id"));
		mgvo.setAuthorName(rs.getString("author_name"));
		mgvo.setTitle(rs.getString("title"));
		mgvo.setWritedate(rs.getDate("writedate"));
		mgvo.setContent(rs.getString("content"));
		mgvo.setReadcount(rs.getInt("readcount"));
		mgvo.setImage(rs.getString("image"));
		mgvo.setSavefilename(rs.getString("savefilename"));
		return mgvo;
	}

	/** 조회수를 1 증가시킨다 */
	public void increaseReadCount(int mseq) {
		executeUpdate(
				"UPDATE member_gallery SET readcount=readcount+1 WHERE mseq=?",
				pstmt -> pstmt.setInt(1, mseq));
	}

	
	/* 카운트 메서드 */
	public int getAllCount() {
		return executeSelectOne(
				"SELECT COUNT(*) AS cnt FROM member_gallery",
				rs -> rs.getInt("cnt"));
	}

	public int getSearchCount(String searchWord) {
		return selectInt(
				"SELECT COUNT(*) FROM member_gallery_view "
						+ " WHERE title LIKE CONCAT('%', ?, '%')"
						+ " OR content LIKE CONCAT('%', ?, '%') "
						+ " OR author_name LIKE CONCAT('%', ?, '%') ",
				searchWord,
				searchWord,
				searchWord);
	}

	public List<MemberGalleryVO> searchGallery(Pagination pagination, String searchWord) {
		
		return executeSelect(
				"SELECT * FROM member_gallery_view "
						+ " WHERE title LIKE CONCAT('%', ?, '%')"
						+ " OR content LIKE CONCAT('%', ?, '%') "
						+ " OR author_name LIKE CONCAT('%', ?, '%') "
						+ " ORDER BY mseq DESC LIMIT ? OFFSET ?",
				pstmt -> {
					pstmt.setString(1, searchWord);
					pstmt.setString(2, searchWord);
					pstmt.setString(3, searchWord);
					pagination.applyTo(pstmt, 4, 5);
				}, MemberGalleryDao::extractMemberGalleryVO);
	}


	@Override
	protected MemberGalleryVO parseVO(ResultSet rs) throws SQLException {
		MemberGalleryVO mgvo = new MemberGalleryVO();
		mgvo.setMseq(rs.getInt("mseq"));
		mgvo.setAuthorId(rs.getString("author_id"));
		mgvo.setAuthorName(rs.getString("author_name"));
		mgvo.setTitle(rs.getString("title"));
		mgvo.setWritedate(rs.getDate("writedate"));
		mgvo.setContent(rs.getString("content"));
		mgvo.setReadcount(rs.getInt("readcount"));
		mgvo.setImage(rs.getString("image"));
		mgvo.setSavefilename(rs.getString("savefilename"));
		return mgvo;
	}





}

