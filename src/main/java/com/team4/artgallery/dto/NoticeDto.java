package com.team4.artgallery.dto;

import com.team4.artgallery.dto.enums.NoticeCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

public class NoticeDto {

    @Getter
    @Setter
    private Integer nseq;

    @Getter
    @Setter
    private String author;

    @Getter
    @Setter
    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 45, message = "제목은 45자 이내로 입력해주세요.")
    private String title;

    /**
     * {@link NoticeCategory#writableValues()}
     */
    @Getter
    @Setter
    @NotBlank(message = "카테고리를 입력해주세요.")
    @Size(max = 45, message = "카테고리는 45자 이내로 입력해주세요.")
    @Pattern(regexp = "^공지사항|이벤트$", message = "카테고리는 공지사항 또는 이벤트 중 하나로 입력해주세요.")
    private String category;

    @Getter
    @Setter
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @Getter
    @Setter
    @Null(message = "조회수는 직접 설정할 수 없습니다.")
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
    @Null(message = "등록일은 직접 설정할 수 없습니다.")
    private Date writedate;
}
