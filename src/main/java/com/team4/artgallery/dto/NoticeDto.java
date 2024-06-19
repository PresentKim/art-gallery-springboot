package com.team4.artgallery.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDto {

    private Integer nseq;
    private String title;
    private String author;
    private Date writedate;
    private String content;
    private Integer readcount;
    private String category;
    private String image;
    private String savefilename;

}
