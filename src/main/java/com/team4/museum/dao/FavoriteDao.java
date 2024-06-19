package com.team4.museum.dao;

import com.team4.artgallery.dto.FavoriteDto;
import com.team4.artgallery.util.Pagination;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

public class FavoriteDao extends BaseDao<FavoriteDto> {

    private FavoriteDao() {
    }

    private static final FavoriteDao instance = new FavoriteDao();

    public static FavoriteDao getInstance() {
        return instance;
    }

    /**
     * 주어진 회원 ID에 해당하는 즐겨찾기 목록을 조회한다.
     *
     * @param memberId   회원 ID
     * @param pagination 페이지 정보
     * @return 즐겨찾기 목록
     */
    public List<FavoriteDto> selectFavorite(String memberId, Pagination pagination) {
        return select(
                "SELECT * FROM favorite_view WHERE member_id = ? ORDER BY aseq DESC LIMIT ? OFFSET ?",
                memberId,
                pagination.getLimit(),
                pagination.getOffset()
        );
    }

    /**
     * 주어진 회원 ID와 작품 번호에 해당하는 즐겨찾기 정보가 존재하면 제거, 존재하지 않으면 추가한다.
     *
     * @param memberId 회원 ID
     * @param aseq     작품 번호 (artwork sequence)
     * @return 즐겨찾기 정보를 추가한 경우엔 true, 제거한 경우엔 false
     */
    public Boolean toggleFavorite(String memberId, int aseq) {
        return call(
                "{CALL toggle_favorite_artwork(?, ?, ?)}",
                cstmt -> {
                    cstmt.setString(1, memberId);
                    cstmt.setInt(2, aseq);
                    cstmt.registerOutParameter(3, Types.BOOLEAN);
                },
                cstmt -> cstmt.getBoolean(3)
        );
    }

    /**
     * 주어진 회원 ID에 해당하는 즐겨찾기 목록의 총 개수를 조회한다.
     *
     * @param memberId 회원 ID
     */
    public int getCount(String memberId) {
        return selectInt("SELECT COUNT(*) FROM favorite_view WHERE member_id = ?", memberId);
    }

    /**
     * 즐겨찾기 정보를 ResultSet에서 추출한다.
     *
     * @param rs ResultSet 객체
     * @return FavoriteDto 즐겨찾기 정보 객체
     */
    protected FavoriteDto parseDto(ResultSet rs) throws SQLException {
        FavoriteDto favoriteDto = FavoriteDto.fromArtwork(ArtworkDao.getInstance().parseDto(rs));
        favoriteDto.setMemberId(rs.getString("member_id"));
        return favoriteDto;
    }

}