package com.team4.artgallery.dto;

import com.team4.artgallery.enums.NoticeCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDto {

    private Integer nseq;
    private String author;

    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 45, message = "제목은 45자 이내로 입력해주세요.")
    private String title;

    /**
     * {@link NoticeCategory#writableValues()}
     */
    @NotBlank(message = "카테고리를 입력해주세요.")
    @Size(max = 45, message = "카테고리는 45자 이내로 입력해주세요.")
    @Pattern(regexp = "^공지사항|이벤트$", message = "카테고리는 공지사항 또는 이벤트 중 하나로 입력해주세요.")
    private String category;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @Null(message = "조회수는 직접 설정할 수 없습니다.")
    private Integer readcount;

    @Null(message = "이미지는 직접 설정할 수 없습니다.")
    private String image;

    @Null(message = "저장된 파일명은 직접 설정할 수 없습니다.")
    private String savefilename;

    @Null(message = "등록일은 직접 설정할 수 없습니다.")
    private Date writedate;
}
