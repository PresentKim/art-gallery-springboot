package com.team4.museum.dao;

import com.team4.museum.util.Pagination;
import com.team4.museum.vo.MemberVO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MemberDao extends BaseDao<MemberVO> {

    private MemberDao() {
    }

    private static final MemberDao instance = new MemberDao();

    public static MemberDao getInstance() {
        return instance;
    }

    public MemberVO getMember(String id) {
        return selectOne("SELECT * FROM member WHERE id = ?", id);
    }

    public List<MemberVO> getMemberList(Pagination pagination) {
        return select("SELECT * FROM member ORDER BY id DESC LIMIT ? OFFSET ?", pagination::applyTo);
    }

    public List<MemberVO> searchMemberList(Pagination pagination, String searchWord) {
        return select(
                "SELECT * FROM member "
                        + " WHERE id LIKE CONCAT('%', ?, '%')"
                        + " OR name LIKE CONCAT('%', ?, '%')"
                        + " OR email LIKE CONCAT('%', ?, '%') "
                        + " ORDER BY id DESC LIMIT ? OFFSET ?",
                searchWord,
                searchWord,
                searchWord,
                pagination.getLimit(),
                pagination.getOffset()
        );
    }

    public int insertMember(MemberVO mvo) {
        return update(
                "INSERT INTO member (id, name, pwd, email, phone)"
                        + " VALUES ( ?, ?, ?, ?, ? )",
                mvo.getId(),
                mvo.getName(),
                mvo.getPwd(),
                mvo.getEmail(),
                mvo.getPhone()
        );
    }

    public int updateMember(MemberVO mvo) {
        return update(
                "UPDATE member SET pwd = ?, name = ?, email = ?, phone = ?, adminyn = ? WHERE id = ?",
                mvo.getPwd(),
                mvo.getName(),
                mvo.getEmail(),
                mvo.getPhone(),
                mvo.getAdminyn(),
                mvo.getId()
        );
    }

    public void deleteMember(String id) {
        update("DELETE FROM member WHERE id = ?", id);
    }

    public void adminRightsAction(String memberId, String action) {
        update("UPDATE member SET adminyn=? WHERE id=?", action.equals("grant") ? "Y" : "N", memberId);
    }

    /* 카운트 메서드 =================>*/
    public int getAllCount() {
        return selectInt("SELECT COUNT(*) FROM member");
    }

    public int getSearchCount(String searchWord) {
        return selectInt(
                "SELECT COUNT(*) FROM member"
                        + " WHERE id LIKE CONCAT('%', ?, '%')"
                        + " OR name LIKE CONCAT('%', ?, '%')"
                        + " OR email LIKE CONCAT('%', ?, '%')",
                searchWord,
                searchWord,
                searchWord
        );
    }

    protected MemberVO parseVO(ResultSet rs) throws SQLException {
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

}
