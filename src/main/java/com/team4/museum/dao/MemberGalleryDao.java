package com.team4.museum.dao;

import com.team4.museum.util.Pagination;
import com.team4.museum.vo.MemberGalleryVO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MemberGalleryDao extends BaseDao<MemberGalleryVO> {

    private MemberGalleryDao() {
    }

    private static MemberGalleryDao instance = new MemberGalleryDao();

    public static MemberGalleryDao getInstance() {
        return instance;
    }

    public MemberGalleryVO getMemberGalleryOne(int mseq) {
        return selectOne("SELECT * FROM member_gallery_view WHERE mseq=?", mseq);
    }

    public int insertMemberGallery(MemberGalleryVO mgvo) {
        return update(
                "INSERT INTO member_gallery (author, title, content, image, savefilename) VALUES (?, ?, ?, ?, ?)",
                mgvo.getAuthorId(),
                mgvo.getTitle(),
                mgvo.getContent(),
                mgvo.getImage(),
                mgvo.getSavefilename()
        );
    }

    public void updateMemberGallery(MemberGalleryVO mgvo) {
        update(
                "UPDATE member_gallery SET title=?, content=?, image=?, savefilename=? WHERE mseq=?",
                mgvo.getTitle(),
                mgvo.getContent(),
                mgvo.getImage(),
                mgvo.getSavefilename(),
                mgvo.getMseq()
        );
    }

    public void deleteMemberGallery(int mseq) {
        update("DELETE FROM member_gallery WHERE mseq = ?", mseq);
    }

    public int getGalleryAllCount() {
        return selectInt("SELECT COUNT(*) FROM member_gallery");
    }

    public List<MemberGalleryVO> getAllGallery(Pagination pagination) {
        return select(
                "SELECT * FROM member_gallery_view ORDER BY mseq DESC LIMIT ? OFFSET ?",
                pagination::applyTo
        );
    }

    /**
     * 조회수를 1 증가시킨다
     */
    public void increaseReadCount(int mseq) {
        update("UPDATE member_gallery SET readcount = readcount + 1 WHERE mseq = ?", mseq);
    }

    /* 카운트 메서드 */
    public int getAllCount() {
        return selectInt("SELECT COUNT(*) FROM member_gallery");
    }

    public int getSearchCount(String searchWord) {
        return selectInt(
                "SELECT COUNT(*) FROM member_gallery_view "
                        + " WHERE title LIKE CONCAT('%', ?, '%')"
                        + " OR content LIKE CONCAT('%', ?, '%') "
                        + " OR author_name LIKE CONCAT('%', ?, '%') ",
                searchWord,
                searchWord,
                searchWord
        );
    }

    public List<MemberGalleryVO> searchGallery(Pagination pagination, String searchWord) {
        return select(
                "SELECT * FROM member_gallery_view "
                        + " WHERE title LIKE CONCAT('%', ?, '%')"
                        + " OR content LIKE CONCAT('%', ?, '%') "
                        + " OR author_name LIKE CONCAT('%', ?, '%') "
                        + " ORDER BY mseq DESC LIMIT ? OFFSET ?",
                searchWord,
                searchWord,
                searchWord,
                pagination.getLimit(),
                pagination.getOffset()
        );
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

