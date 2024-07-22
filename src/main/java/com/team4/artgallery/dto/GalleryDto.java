package com.team4.artgallery.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.team4.artgallery.dto.view.Views;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

public class GalleryDto {

    @Getter
    @Setter
    @JsonView({Views.Identifier.class, Views.Summary.class, Views.Detail.class})
    private Integer gseq;

    @Getter
    @Setter
    @JsonView({Views.Summary.class, Views.Detail.class})
    private String authorId;

    @Getter
    @Setter
    @JsonView({Views.Summary.class, Views.Detail.class})
    private String authorName;

    @Getter
    @Setter
    @NotBlank(message = "작품명을 입력해주세요.")
    @Size(max = 45, message = "작품명은 45자 이내로 입력해주세요.")
    @JsonView({Views.Summary.class, Views.Detail.class})
    private String title;

    @Getter
    @Setter
    @NotBlank(message = "작품설명을 입력해주세요.")
    @JsonView({Views.Detail.class})
    private String content;

    @Getter
    @Setter
    @Null(message = "조회수는 직접 설정할 수 없습니다.")
    @JsonView({Views.Summary.class, Views.Detail.class})
    private Integer readcount;

    @Getter
    @Setter
    @Null(message = "이미지는 직접 설정할 수 없습니다.")
    private String image;

    @Getter
    @Setter
    @Null(message = "저장된 파일명은 직접 설정할 수 없습니다.")
    private String savefilename;

    @Getter
    @Setter
    @Null(message = "등록일은 직접 설정할 수 없습니다.")
    private Date writedate;

    /**
     * 사용자가 접근 가능한 이미지 경로를 반환합니다.
     */
    @JsonView({Views.Summary.class, Views.Detail.class})
    public String getImageSrc() {
        if (savefilename.startsWith("http")) {
            return savefilename;
        }

        return "/static/image/gallery/" + savefilename;
    }

}
