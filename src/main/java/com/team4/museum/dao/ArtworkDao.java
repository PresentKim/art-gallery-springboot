package com.team4.museum.dao;

import com.team4.artgallery.dto.ArtworkDto;
import com.team4.artgallery.util.Pagination;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ArtworkDao extends BaseDao<ArtworkDto> {

    private ArtworkDao() {
    }

    private static final ArtworkDao instance = new ArtworkDao();

    public static ArtworkDao getInstance() {
        return instance;
    }

    /* 관리자용 로직 ============================================> */


    /**
     * <관리자용> 예술품 삭제
     */
    public int deleteArtwork(Integer aseq) {
        return update("DELETE FROM artwork WHERE aseq = ?", aseq);
    }

    public List<ArtworkDto> getAll(String category, String displayyn, String searchWord, Pagination pagination) {
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

    public ArtworkDto parseDto(ResultSet rs) throws SQLException {
        ArtworkDto artworkDto = new ArtworkDto();
        artworkDto.setAseq(rs.getInt("aseq"));
        artworkDto.setName(rs.getString("name"));
        artworkDto.setCategory(rs.getString("category"));
        artworkDto.setArtist(rs.getString("artist"));
        artworkDto.setYear(rs.getString("year"));
        artworkDto.setMaterial(rs.getString("material"));
        artworkDto.setSize(rs.getString("size"));
        artworkDto.setDisplayyn(rs.getString("displayyn"));
        artworkDto.setContent(rs.getString("content"));
        artworkDto.setImage(rs.getString("image"));
        artworkDto.setSavefilename(rs.getString("savefilename"));
        artworkDto.setIndate(rs.getDate("indate"));
        return artworkDto;
    }

    public List<ArtworkDto> getRandomList(int limit) {
        return select("SELECT * FROM museum.artwork ORDER BY RAND() LIMIT ?", limit);
    }

}