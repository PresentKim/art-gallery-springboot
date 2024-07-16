package com.team4.artgallery.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.team4.artgallery.dto.enums.ArtworkCategory;
import com.team4.artgallery.dto.view.Views;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Schema(name = "ArtworkDto", description = "예술품 정보")
public class ArtworkDto {

    @Getter
    @Setter
    @Schema(description = "예술품 번호", example = "1", hidden = true)
    @JsonView({Views.Identifier.class, Views.Summary.class, Views.Detail.class})
    private Integer aseq;

    @Getter
    @Setter
    @NotBlank(message = "작품명을 입력해주세요.")
    @Size(max = 45, message = "작품명은 45자 이내로 입력해주세요.")
    @Schema(description = "작품명", example = "풍경화")
    @JsonView({Views.Summary.class, Views.Detail.class})
    private String name;

    /**
     * {@link ArtworkCategory#validValues()}
     */
    @Getter
    @Setter
    @NotBlank(message = "부문을 입력해주세요.")
    @Size(max = 45, message = "부문은 45자 이내로 입력해주세요.")
    @Pattern(regexp = "^회화|드로잉|판화|조각ㆍ설치|사진|공예|디자인|서예$", message = "부문은 회화, 드로잉, 판화, 조각ㆍ설치, 사진, 공예, 디자인, 서예 중 하나로 입력해주세요.")
    @Schema(description = "부문", example = "회화")
    @JsonView({Views.Detail.class})
    private String category;

    @Getter
    @Setter
    @NotBlank(message = "작가명을 입력해주세요.")
    @Size(max = 45, message = "작가명은 45자 이내로 입력해주세요.")
    @Schema(description = "작가명", example = "홍길동")
    @JsonView({Views.Summary.class, Views.Detail.class})
    private String artist;

    @Getter
    @Setter
    @NotBlank(message = "제작년도를 입력해주세요.")
    @Size(max = 4, message = "제작년도는 4자 이내로 입력해주세요.")
    @Schema(description = "제작년도", example = "2021")
    @JsonView({Views.Summary.class, Views.Detail.class})
    private String year;

    @Getter
    @Setter
    @NotBlank(message = "재료를 입력해주세요.")
    @Size(max = 45, message = "재료는 45자 이내로 입력해주세요.")
    @Schema(description = "재료", example = "캔버스")
    @JsonView({Views.Detail.class})
    private String material;

    @Getter
    @Setter
    @NotBlank(message = "규격을 입력해주세요.")
    @Size(max = 45, message = "규격은 45자 이내로 입력해주세요.")
    @Schema(description = "규격", example = "100x100")
    @JsonView({Views.Detail.class})
    private String size;

    @Getter
    @Setter
    @NotBlank(message = "전시상태를 입력해주세요.")
    @Size(max = 1, message = "전시상태는 1자 이내로 입력해주세요.")
    @Pattern(regexp = "^[YN]$", message = "전시상태는 Y 또는 N으로 입력해주세요.")
    @Schema(description = "전시상태", example = "Y")
    @JsonView({Views.Detail.class})
    private String displayyn;

    @Getter
    @Setter
    @NotBlank(message = "작품설명을 입력해주세요.")
    @Schema(description = "작품설명", example = "풍경화")
    @JsonView({Views.Detail.class})
    private String content;

    @Getter
    @Setter
    @Null(message = "이미지는 직접 설정할 수 없습니다.")
    @Schema(description = "이미지", example = "image.jpg", hidden = true)
    private String image;

    @Getter
    @Setter
    @Null(message = "저장된 파일명은 직접 설정할 수 없습니다.")
    @Schema(description = "저장된 파일명", example = "image.jpg", hidden = true)
    private String savefilename;

    @Getter
    @Null(message = "등록일은 직접 설정할 수 없습니다.")
    @Schema(description = "등록일", example = "2021-01-01")
    @Hidden
    private Date indate;

    /**
     * 사용자가 접근 가능한 이미지 경로를 반환합니다.
     */
    @JsonView({Views.Summary.class, Views.Detail.class})
    public String getImageSrc() {
        if (savefilename.startsWith("http")) {
            return savefilename;
        }

        return "/static/image/artwork/" + savefilename;
    }

    @Hidden
    public boolean isDisplay() {
        return displayyn.equals("Y");
    }

    @Hidden
    public void setDisplay(boolean isDisplay) {
        displayyn = isDisplay ? "Y" : "N";
    }

}
