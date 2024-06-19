package com.team4.artgallery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.sql.Date;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ArtworkDto {

    private Integer aseq;
    private String name;
    private String category;
    private String artist;
    private String year;
    private String material;
    private String size;
    private String displayyn;
    private String content;
    private String image;
    private String savefilename;
    private Date indate;

    public String getFullSavefilename() {
        if (savefilename.startsWith("http")) {
            return savefilename;
        }

        return "/static/image/artwork/" + savefilename;
    }

    public boolean isDisplay() {
        return displayyn.equals("Y");
    }

    public void setDisplay(boolean isDisplay) {
        displayyn = isDisplay ? "Y" : "N";
    }

}
