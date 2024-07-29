package com.team4.artgallery.dto.artwork;

import com.team4.artgallery.dto.enums.ArtworkCategory;
import com.team4.artgallery.entity.ArtworkEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ArtworkUpdateDto {

    @NotBlank(message = "작품명을 입력해주세요.")
    @Size(max = 45, message = "작품명은 45자 이내로 입력해주세요.")
    protected String name;

    /**
     * {@link ArtworkCategory#validValues()}
     */
    @NotBlank(message = "부문을 입력해주세요.")
    @Size(max = 45, message = "부문은 45자 이내로 입력해주세요.")
    @Pattern(regexp = "^회화|드로잉|판화|조각ㆍ설치|사진|공예|디자인|서예$", message = "부문은 회화, 드로잉, 판화, 조각ㆍ설치, 사진, 공예, 디자인, 서예 중 하나로 입력해주세요.")
    protected String category;

    @NotBlank(message = "작가명을 입력해주세요.")
    @Size(max = 45, message = "작가명은 45자 이내로 입력해주세요.")
    protected String artist;

    @NotBlank(message = "제작년도를 입력해주세요.")
    @Size(max = 4, message = "제작년도는 4자 이내로 입력해주세요.")
    protected String year;

    @NotBlank(message = "재료를 입력해주세요.")
    @Size(max = 45, message = "재료는 45자 이내로 입력해주세요.")
    protected String material;

    @NotBlank(message = "규격을 입력해주세요.")
    @Size(max = 45, message = "규격은 45자 이내로 입력해주세요.")
    protected String size;

    @NotBlank(message = "전시상태를 입력해주세요.")
    @Size(max = 1, message = "전시상태는 1자 이내로 입력해주세요.")
    @Pattern(regexp = "^[YN]$", message = "전시상태는 Y 또는 N으로 입력해주세요.")
    protected char displayyn;

    @NotBlank(message = "작품설명을 입력해주세요.")
    protected String content;

    protected MultipartFile imageFile;

    public ArtworkEntity toEntity(Integer aseq, String image, String saveFilename) {
        return ArtworkEntity.builder()
                .aseq(aseq)
                .image(image)
                .saveFileName(saveFilename)
                .name(name)
                .category(category)
                .artist(artist)
                .year(year)
                .material(material)
                .size(size)
                .display(displayyn == 'Y')
                .content(content)
                .build();
    }

}
