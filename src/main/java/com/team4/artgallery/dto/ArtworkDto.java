package com.team4.artgallery.dto;

import com.team4.artgallery.enums.ArtworkCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "작품명을 입력해주세요.")
    @Size(max = 45, message = "작품명은 45자 이내로 입력해주세요.")
    private String name;

    /**
     * {@link ArtworkCategory#validValues()}
     */
    @NotBlank(message = "부문을 입력해주세요.")
    @Size(max = 45, message = "부문은 45자 이내로 입력해주세요.")
    @Pattern(regexp = "^회화|드로잉|판화|조각ㆍ설치|사진|공예|디자인|서예$", message = "부문은 회화, 드로잉, 판화, 조각ㆍ설치, 사진, 공예, 디자인, 서예 중 하나로 입력해주세요.")
    private String category;

    @NotBlank(message = "작가명을 입력해주세요.")
    @Size(max = 45, message = "작가명은 45자 이내로 입력해주세요.")
    private String artist;

    @NotBlank(message = "제작년도를 입력해주세요.")
    @Size(max = 4, message = "제작년도는 4자 이내로 입력해주세요.")
    private String year;

    @NotBlank(message = "재료를 입력해주세요.")
    @Size(max = 45, message = "재료는 45자 이내로 입력해주세요.")
    private String material;

    @NotBlank(message = "규격을 입력해주세요.")
    @Size(max = 45, message = "규격은 45자 이내로 입력해주세요.")
    private String size;

    @NotBlank(message = "전시상태를 입력해주세요.")
    @Size(max = 1, message = "전시상태는 1자 이내로 입력해주세요.")
    @Pattern(regexp = "^[YN]$", message = "전시상태는 Y 또는 N으로 입력해주세요.")
    private String displayyn;

    @NotBlank(message = "작품설명을 입력해주세요.")
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
