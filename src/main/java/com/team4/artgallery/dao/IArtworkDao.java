package com.team4.artgallery.dao;

import com.team4.artgallery.dto.ArtworkDto;
import com.team4.artgallery.util.Pagination;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface IArtworkDao {

    int insertArtwork(ArtworkDto artworkDto);

    ArtworkDto findArtwork(int aseq);

    List<ArtworkDto> getArtworks(Pagination pagination);

    List<ArtworkDto> searchArtworks(@Param("filter") ArtworkFilter filter,
                                    @Param("pagination") Pagination pagination);

    int getAllCount();

    int getSearchCount(@Param("filter") ArtworkFilter filter);

    int updateArtwork(ArtworkDto artworkDto);

    int toggleArtworkDisplay(int aseq);

    int deleteArtwork(int aseq);

    @Getter
    @Setter
    @AllArgsConstructor
    class ArtworkFilter {

        private String category;
        private String displayyn;
        private String search;

        public boolean hasCategory() {
            return category != null && !category.isEmpty() && !"전체".equals(category);
        }

        public boolean hasDisplay() {
            return displayyn != null && !displayyn.isEmpty();
        }

        public boolean hasSearch() {
            return search != null && !search.isEmpty();
        }

        public boolean isEmpty() {
            return !hasCategory() && !hasDisplay() && !hasSearch();
        }

        public String toUrlParam(boolean includeDisplay) {
            if (isEmpty()) {
                return "";
            }

            List<String> params = new ArrayList<>();
            if (hasCategory()) {
                params.add("category=" + category);
            }

            if (includeDisplay && hasDisplay()) {
                params.add("displayyn=" + displayyn);
            }

            if (hasSearch()) {
                params.add("search=" + search);
            }

            return String.join("&", params);
        }

    }

}
