package com.team4.artgallery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteDto extends ArtworkDto {

    private String memberId;

    public static FavoriteDto fromArtwork(ArtworkDto artwork) {
        return FavoriteDto.builder()
                .aseq(artwork.getAseq())
                .name(artwork.getName())
                .category(artwork.getCategory())
                .artist(artwork.getArtist())
                .year(artwork.getYear())
                .material(artwork.getMaterial())
                .size(artwork.getSize())
                .displayyn(artwork.getDisplayyn())
                .content(artwork.getContent())
                .image(artwork.getImage())
                .savefilename(artwork.getSavefilename())
                .build();
    }

}
