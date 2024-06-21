package com.team4.museum.dao;

import com.team4.artgallery.dto.MemberDto;
import com.team4.artgallery.util.Pagination;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MemberDao extends BaseDao<MemberDto> {

    private MemberDao() {
    }

    private static final MemberDao instance = new MemberDao();

    public static MemberDao getInstance() {
        return instance;
    }

    public List<MemberDto> getMemberList(Pagination pagination) {
        return select("SELECT * FROM member ORDER BY id DESC LIMIT ? OFFSET ?", pagination::applyTo);
    }

    public List<MemberDto> searchMemberList(Pagination pagination, String searchWord) {
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

    protected MemberDto parseDto(ResultSet rs) throws SQLException {
        MemberDto memberDto = new MemberDto();
        memberDto.setId(rs.getString("id"));
        memberDto.setName(rs.getString("name"));
        memberDto.setPwd(rs.getString("pwd"));
        memberDto.setEmail(rs.getString("email"));
        memberDto.setIndate(rs.getDate("indate"));
        memberDto.setPhone(rs.getString("phone"));
        memberDto.setAdminyn(rs.getString("adminyn"));
        return memberDto;
    }

}
