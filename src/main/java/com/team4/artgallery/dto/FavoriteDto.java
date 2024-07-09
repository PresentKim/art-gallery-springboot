package com.team4.artgallery.dto;

import lombok.Getter;
import lombok.Setter;

public class FavoriteDto extends ArtworkDto {

    @Getter
    @Setter
    private String memberId;

}
