package com.team4.artgallery.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GalleryDto {

    private int gseq;
    private String authorId;
    private String authorName;
    private String title;
    private Date writedate;
    private String content;
    private int readcount;
    private String image;
    private String savefilename;

}
