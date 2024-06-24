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

    public GalleryDto getMemberGalleryOne(int gseq) {
        return selectOne("SELECT * FROM gallery_view WHERE gseq=?", gseq);
    }

    public int insertMemberGallery(GalleryDto galleryDto) {
        return update(
                "INSERT INTO gallery (author, title, content, image, savefilename) VALUES (?, ?, ?, ?, ?)",
                galleryDto.getAuthorId(),
                galleryDto.getTitle(),
                galleryDto.getContent(),
                galleryDto.getImage(),
                galleryDto.getSavefilename()
        );
    }

    public void updateMemberGallery(GalleryDto galleryDto) {
        update(
                "UPDATE gallery SET title=?, content=?, image=?, savefilename=? WHERE gseq=?",
                galleryDto.getTitle(),
                galleryDto.getContent(),
                galleryDto.getImage(),
                galleryDto.getSavefilename(),
                galleryDto.getGseq()
        );
    }

    public void deleteMemberGallery(int gseq) {
        update("DELETE FROM gallery WHERE gseq = ?", gseq);
    }

    public int getGalleryAllCount() {
        return selectInt("SELECT COUNT(*) FROM gallery");
    }

    public List<GalleryDto> getAllGallery(Pagination pagination) {
        return select(
                "SELECT * FROM gallery_view ORDER BY gseq DESC LIMIT ? OFFSET ?",
                pagination::applyTo
        );
    }

    /**
     * 조회수를 1 증가시킨다
     */
    public void increaseReadCount(int gseq) {
        update("UPDATE gallery SET readcount = readcount + 1 WHERE gseq = ?", gseq);
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

