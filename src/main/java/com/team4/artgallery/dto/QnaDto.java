package com.team4.artgallery.dto;

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
public class QnaDto {

    private Integer qseq;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Size(max = 45, message = "이메일은 45자 이내로 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(max = 45, message = "비밀번호는 45자 이내로 입력해주세요.")
    private String pwd;

    @NotBlank(message = "휴대폰 번호를 입력해주세요.")
    @Size(max = 45, message = "휴대폰 번호는 45자 이내로 입력해주세요.")
    private String phone;

    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 45, message = "제목은 45자 이내로 입력해주세요.")
    private String title;

    @NotBlank(message = "공개여부를 입력해주세요.")
    @Pattern(regexp = "^[YN]$", message = "공개여부는 Y 또는 N으로 입력해주세요.")
    private String publicyn;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @Null(message = "등록일은 직접 설정할 수 없습니다.")
    private Date writedate;

    @Null(message = "답변은 직접 설정할 수 없습니다.")
    private String reply;

    public boolean isDisplay() {
        return publicyn.equals("Y");
    }

    public void setDisplay(boolean display) {
        publicyn = display ? "Y" : "N";
    }

    public boolean hasReply() {
        return reply != null && !reply.isEmpty();
    }

}
