package com.team4.artgallery.dto.gallery;

import com.team4.artgallery.aspect.annotation.NotEmptyMultipartFile;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class GalleryCreateDto extends GalleryUpdateDto {

    @NotEmptyMultipartFile(message = "이미지 파일을 선택해주세요.")
    protected MultipartFile imageFile;

}
