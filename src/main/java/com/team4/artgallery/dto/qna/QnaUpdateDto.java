package com.team4.artgallery.dto.qna;

import com.team4.artgallery.entity.QnaEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QnaUpdateDto {

    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 45, message = "제목은 45자 이내로 입력해주세요.")
    protected String title;

    @NotBlank(message = "설명을 입력해주세요.")
    protected String content;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Size(max = 45, message = "이메일은 45자 이내로 입력해주세요.")
    protected String email;

    @NotBlank(message = "전화번호를 입력해주세요.")
    @Size(max = 45, message = "전화번호는 45자 이내로 입력해주세요.")
    protected String phone;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(max = 45, message = "비밀번호는 45자 이내로 입력해주세요.")
    protected String pwd;

    @NotBlank(message = "공개 여부를 입력해주세요.")
    @Size(max = 1, message = "공개 여부를 1자 이내로 입력해주세요.")
    @Pattern(regexp = "^[YN]$", message = "공개 여부는 Y 또는 N으로 입력해주세요.")
    protected char displayyn;

    public QnaEntity toEntity(Integer qseq) {
        return QnaEntity.builder()
                .qseq(qseq)
                .title(title)
                .content(content)
                .email(email)
                .phone(phone)
                .pwd(pwd)
                .display(displayyn == 'Y')
                .build();
    }

}
