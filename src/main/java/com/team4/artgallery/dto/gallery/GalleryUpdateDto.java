package com.team4.artgallery.dto.gallery;

import com.team4.artgallery.entity.GalleryEntity;
import com.team4.artgallery.entity.MemberEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class GalleryUpdateDto {

    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 45, message = "제목은 45자 이내로 입력해주세요.")
    protected String title;

    @NotBlank(message = "설명을 입력해주세요.")
    protected String content;

    protected MultipartFile imageFile;

    public GalleryEntity toEntity(Integer gseq, String imageFileName, MemberEntity loginMember) {
        return GalleryEntity.builder()
                .author(loginMember)
                .gseq(gseq)
                .imageFileName(imageFileName)
                .title(title)
                .content(content)
                .build();
    }

}
