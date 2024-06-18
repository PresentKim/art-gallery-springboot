package com.team4.museum.dao;

import static com.team4.museum.util.Db.executeSelect;
import static com.team4.museum.util.Db.executeSelectOne;
import static com.team4.museum.util.Db.executeUpdate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.team4.museum.util.Pagination;
import com.team4.museum.vo.MemberVO;

public class MemberDao {

	private MemberDao() {
	}

	private static final MemberDao instance = new MemberDao();

	public static MemberDao getInstance() {
		return instance;
	}

	public MemberVO getMember(String id) {
		return executeSelectOne(
				"SELECT * FROM member WHERE id = ?",
				pstmt -> pstmt.setString(1, id),
				MemberDao::extractMemberVO);
	}

	public List<MemberVO> getMemberList(Pagination pagination) {
		return executeSelect(
				"SELECT * FROM member ORDER BY id DESC LIMIT ? OFFSET ?",
				pagination::applyTo,
				MemberDao::extractMemberVO);
	}

	public List<MemberVO> searchMemberList(Pagination pagination, String searchWord) {
		return executeSelect(
				"SELECT * FROM member "
						+ " WHERE id LIKE CONCAT('%', ?, '%') OR name LIKE CONCAT('%', ?, '%') OR email LIKE CONCAT('%', ?, '%') "
						+ " ORDER BY id DESC LIMIT ? OFFSET ?",
				pstmt -> {
					pstmt.setString(1, searchWord);
					pstmt.setString(2, searchWord);
					pstmt.setString(3, searchWord);
					pagination.applyTo(pstmt, 4, 5);
				},
				MemberDao::extractMemberVO);
	}

	public int insertMember(MemberVO mvo) {
		return executeUpdate(
				"INSERT INTO member (id, name, pwd, email, phone)" + " VALUES ( ?, ?, ?, ?, ? )",
				pstmt -> {
					pstmt.setString(1, mvo.getId());
					pstmt.setString(2, mvo.getName());
					pstmt.setString(3, mvo.getPwd());
					pstmt.setString(4, mvo.getEmail());
					pstmt.setString(5, mvo.getPhone());
				});
	}

	public int updateMember(MemberVO mvo) {
		return executeUpdate(
				"UPDATE member SET pwd = ?, name = ?, email = ?, phone = ?, adminyn = ? WHERE id = ?",
				pstmt -> {
					pstmt.setString(1, mvo.getPwd());
					pstmt.setString(2, mvo.getName());
					pstmt.setString(3, mvo.getEmail());
					pstmt.setString(4, mvo.getPhone());
					pstmt.setString(5, mvo.getAdminyn());
					pstmt.setString(6, mvo.getId());
				});
	}

	public void deleteMember(String id) {
		executeUpdate("DELETE FROM member WHERE id = ?", pstmt -> pstmt.setString(1, id));
	}


	public void adminRightsAction(String memberId, String action) {
		executeUpdate(
				"UPDATE member SET adminyn=? WHERE id=?",
				pstmt -> {
					pstmt.setString(1, action.equals("grant") ? "Y" : "N");
					pstmt.setString(2, memberId);
				});
	}
	
	/* 카운트 메서드 =================>*/
	public int getAllCount() {
		return executeSelectOne(
				"SELECT COUNT(*) as cnt FROM member",
				rs -> rs.getInt("cnt"));
	}

	private static MemberVO extractMemberVO(ResultSet rs) throws SQLException {
		MemberVO mvo = new MemberVO();
		mvo.setId(rs.getString("id"));
		mvo.setName(rs.getString("name"));
		mvo.setPwd(rs.getString("pwd"));
		mvo.setEmail(rs.getString("email"));
		mvo.setIndate(rs.getDate("indate"));
		mvo.setPhone(rs.getString("phone"));
		mvo.setAdminyn(rs.getString("adminyn"));
		return mvo;
	}

	public int getSearchCount(String searchWord) {
		return executeSelectOne("SELECT COUNT(*) as cnt FROM member "
				+ "WHERE id LIKE CONCAT('%', ?, '%') "
				+ "OR name LIKE CONCAT('%', ?, '%') "
				+ "OR email LIKE CONCAT('%', ?, '%') ",
				pstmt->{
					pstmt.setString(1, searchWord);
					pstmt.setString(2, searchWord);
					pstmt.setString(3, searchWord);
				},rs -> rs.getInt("cnt"));
	}

}
