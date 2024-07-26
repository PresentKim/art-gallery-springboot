package com.team4.artgallery.dto.artwork;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ArtworkCreateDto extends ArtworkUpdateDto {

    @NotBlank(message = "이미지 파일을 선택해주세요.")
    protected MultipartFile imageFile;

}
