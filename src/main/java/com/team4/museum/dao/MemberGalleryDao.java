package com.team4.museum.dao;

import com.team4.artgallery.dto.GalleryDto;
import com.team4.artgallery.util.Pagination;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MemberGalleryDao extends BaseDao<GalleryDto> {

    private MemberGalleryDao() {
    }

    private static MemberGalleryDao instance = new MemberGalleryDao();

    public static MemberGalleryDao getInstance() {
        return instance;
    }

    public void deleteMemberGallery(int gseq) {
        update("DELETE FROM gallery WHERE gseq = ?", gseq);
    }

    public List<GalleryDto> getAllGallery(Pagination pagination) {
        return select(
                "SELECT * FROM gallery_view ORDER BY gseq DESC LIMIT ? OFFSET ?",
                pagination::applyTo
        );
    }

    /* 카운트 메서드 */
    public int getAllCount() {
        return selectInt("SELECT COUNT(*) FROM gallery");
    }

    public int getSearchCount(String searchWord) {
        return selectInt(
                "SELECT COUNT(*) FROM gallery_view "
                        + " WHERE title LIKE CONCAT('%', ?, '%')"
                        + " OR content LIKE CONCAT('%', ?, '%') "
                        + " OR author_name LIKE CONCAT('%', ?, '%') ",
                searchWord,
                searchWord,
                searchWord
        );
    }

    public List<GalleryDto> searchGallery(Pagination pagination, String searchWord) {
        return select(
                "SELECT * FROM gallery_view "
                        + " WHERE title LIKE CONCAT('%', ?, '%')"
                        + " OR content LIKE CONCAT('%', ?, '%') "
                        + " OR author_name LIKE CONCAT('%', ?, '%') "
                        + " ORDER BY gseq DESC LIMIT ? OFFSET ?",
                searchWord,
                searchWord,
                searchWord,
                pagination.getLimit(),
                pagination.getOffset()
        );
    }

    @Override
    protected GalleryDto parseDto(ResultSet rs) throws SQLException {
        GalleryDto galleryDto = new GalleryDto();
        galleryDto.setGseq(rs.getInt("gseq"));
        galleryDto.setAuthorId(rs.getString("author_id"));
        galleryDto.setAuthorName(rs.getString("author_name"));
        galleryDto.setTitle(rs.getString("title"));
        galleryDto.setWritedate(rs.getDate("writedate"));
        galleryDto.setContent(rs.getString("content"));
        galleryDto.setReadcount(rs.getInt("readcount"));
        galleryDto.setImage(rs.getString("image"));
        galleryDto.setSavefilename(rs.getString("savefilename"));
        return galleryDto;
    }

}

