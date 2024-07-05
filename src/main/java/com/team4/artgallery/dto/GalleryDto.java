package com.team4.artgallery.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GalleryDto {

    private Integer gseq;

    private String authorId;

    private String authorName;

    @NotBlank(message = "작품명을 입력해주세요.")
    @Size(max = 45, message = "작품명은 45자 이내로 입력해주세요.")
    private String title;

    @NotBlank(message = "작품설명을 입력해주세요.")
    private String content;

    @Null(message = "조회수는 직접 설정할 수 없습니다.")
    private Integer readcount;

    @Null(message = "이미지는 직접 설정할 수 없습니다.")
    private String image;

    @Null(message = "저장된 파일명은 직접 설정할 수 없습니다.")
    private String savefilename;

    @Null(message = "등록일은 직접 설정할 수 없습니다.")
    private Date writedate;

    public String getFullSavefilename() {
        if (savefilename.startsWith("http")) {
            return savefilename;
        }

        return "/static/image/gallery/" + savefilename;
    }

}
