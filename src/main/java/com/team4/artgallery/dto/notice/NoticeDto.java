package com.team4.artgallery.dto.notice;

import com.team4.artgallery.dto.enums.NoticeCategory;
import com.team4.artgallery.entity.MemberEntity;
import com.team4.artgallery.entity.NoticeEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeDto {

    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 45, message = "제목은 45자 이내로 입력해주세요.")
    protected String title;

    @NotBlank(message = "설명을 입력해주세요.")
    protected String content;

    /**
     * {@link NoticeCategory#writableValues()}
     */
    @NotBlank(message = "카테고리를 입력해주세요.")
    @Size(max = 45, message = "카테고리는 45자 이내로 입력해주세요.")
    @Pattern(regexp = "^공지사항|이벤트$", message = "카테고리는 공지사항 또는 이벤트 중 하나로 입력해주세요.")
    protected String category;

    public NoticeEntity toEntity(Integer nseq, MemberEntity author) {
        return NoticeEntity.builder()
                .nseq(nseq)
                .author(author)
                .title(title)
                .content(content)
                .category(category)
                .build();
    }

}
