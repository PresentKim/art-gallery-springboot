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

    public boolean isPublic() {
        return publicyn.equals("Y");
    }

    public void setPublic(boolean isPublic) {
        publicyn = isPublic ? "Y" : "N";
    }

}
