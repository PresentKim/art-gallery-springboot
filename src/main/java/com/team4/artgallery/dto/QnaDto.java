package com.team4.artgallery.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QnaDto {

    private Integer qseq;
    private String title;
    private String content;
    private Date writedate;
    private String email;
    private String pwd;
    private String phone;
    private String reply;
    private String publicyn;

    public boolean isDisplay() {
        return publicyn.equals("Y");
    }

    public void setDisplay(boolean display) {
        publicyn = display ? "Y" : "N";
    }

    public boolean hasReply() {
        return reply != null && !reply.isEmpty();
    }

}
