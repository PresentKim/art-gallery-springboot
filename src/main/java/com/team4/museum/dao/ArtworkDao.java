package com.team4.museum.dao;

import com.team4.museum.util.Pagination;
import com.team4.museum.vo.ArtworkVO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ArtworkDao extends BaseDao<ArtworkVO> {

    private ArtworkDao() {
    }

    private static final ArtworkDao instance = new ArtworkDao();

    public static ArtworkDao getInstance() {
        return instance;
    }

    /* 관리자용 로직 ============================================> */

    /**
     * <관리자용> 예술품 등록
     */
    public int insertArtwork(ArtworkVO avo) {
        return update(
                "INSERT INTO artwork (name, category, artist, year, material, size, displayyn, content, image, savefilename) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                avo.getName(),
                avo.getCategory(),
                avo.getArtist(),
                avo.getYear(),
                avo.getMaterial(),
                avo.getSize(),
                avo.getDisplayyn(),
                avo.getContent(),
                avo.getImage(),
                avo.getSavefilename()
        );
    }

    /**
     * <관리자용> 예술품 삭제
     */
    public int deleteArtwork(Integer aseq) {
        return update("DELETE FROM artwork WHERE aseq = ?", aseq);
    }

    /**
     * <관리자용> 예술품 수정
     */
    public int updateArtwork(ArtworkVO avo) {
        return update(
                "UPDATE artwork SET artist=?, name=?, year=?, material=?, size=?, category=?, displayyn=?, content=?, image=?, savefilename=? "
                        + " WHERE aseq=?",
                avo.getArtist(),
                avo.getName(),
                avo.getYear(),
                avo.getMaterial(),
                avo.getSize(),
                avo.getCategory(),
                avo.getDisplayyn(),
                avo.getContent(),
                avo.getImage(),
                avo.getSavefilename(),
                avo.getAseq()
        );
    }

    /**
     * <관리자용> 예술품 공개여부 전환
     */
    public int displayChangeArtwork(int aseq) {
        ArtworkVO avo = selectOne("SELECT * FROM artwork WHERE aseq = ?", aseq);
        if (avo.isDisplay()) {
            return update("UPDATE artwork SET displayyn='N' WHERE aseq = ?", aseq);
        }

        return update("UPDATE artwork SET displayyn='Y' WHERE aseq = ?", aseq);
    }

    public ArtworkVO get(int aseq) {
        return selectOne("SELECT * FROM artwork WHERE aseq = ?", aseq);
    }

    public List<ArtworkVO> getAll(String category, String displayyn, String searchWord, Pagination pagination) {
        if (category == null)
            category = "";
        if (displayyn == null)
            displayyn = "";
        if (searchWord == null)
            searchWord = "";

        return select(
                "SELECT * FROM artwork "
                        + "WHERE category LIKE CONCAT('%', ?, '%') "
                        + "AND   displayyn LIKE CONCAT('%', ?, '%') "
                        + "AND   (name LIKE CONCAT('%', ?, '%') OR artist LIKE CONCAT('%', ?, '%')) "
                        + "ORDER BY aseq DESC LIMIT ? OFFSET ?",
                category,
                displayyn,
                searchWord,
                searchWord,
                pagination.getLimit(),
                pagination.getOffset()
        );
    }

    public int getCount(String category, String displayyn, String searchWord) {
        if (category == null)
            category = "";
        if (displayyn == null)
            displayyn = "";
        if (searchWord == null)
            searchWord = "";

        return selectInt(
                "SELECT COUNT(*) FROM artwork "
                        + "WHERE category LIKE CONCAT('%', ?, '%') "
                        + "AND   displayyn LIKE CONCAT('%', ?, '%') "
                        + "AND   (name LIKE CONCAT('%', ?, '%') OR artist LIKE CONCAT('%', ?, '%'))",
                category,
                displayyn,
                searchWord,
                searchWord
        );
    }

    public ArtworkVO parseVO(ResultSet rs) throws SQLException {
        ArtworkVO avo = new ArtworkVO();
        avo.setAseq(rs.getInt("aseq"));
        avo.setName(rs.getString("name"));
        avo.setCategory(rs.getString("category"));
        avo.setArtist(rs.getString("artist"));
        avo.setYear(rs.getString("year"));
        avo.setMaterial(rs.getString("material"));
        avo.setSize(rs.getString("size"));
        avo.setDisplayyn(rs.getString("displayyn"));
        avo.setContent(rs.getString("content"));
        avo.setImage(rs.getString("image"));
        avo.setSavefilename(rs.getString("savefilename"));
        avo.setIndate(rs.getDate("indate"));
        return avo;
    }

    public List<ArtworkVO> getRandomList(int limit) {
        return select("SELECT * FROM museum.artwork ORDER BY RAND() LIMIT ?", limit);
    }

}