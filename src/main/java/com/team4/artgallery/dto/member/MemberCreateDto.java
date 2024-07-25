package com.team4.artgallery.dto.member;

import com.team4.artgallery.entity.MemberEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MemberCreateDto(
        @NotBlank(message = "아이디는 필수 입력값입니다.")
        @Size(min = 4, max = 45, message = "아이디는 4자 이상 45자 이하로 입력해주세요.")
        String id,

        @NotBlank(message = "이름은 필수 입력값입니다.")
        @Size(min = 2, max = 45, message = "이름은 2자 이상 45자 이하로 입력해주세요.")
        String name,

        @NotBlank(message = "비밀번호는 필수 입력값입니다.")
        @Size(min = 4, max = 45, message = "비밀번호는 4자 이상 45자 이하로 입력해주세요.")
        String pwd,

        @NotBlank(message = "이메일은 필수 입력값입니다.")
        @Size(min = 4, max = 45, message = "이메일은 4자 이상 45자 이하로 입력해주세요.")
        String email,

        @NotBlank(message = "전화번호는 필수 입력값입니다.")
        @Size(min = 4, max = 45, message = "전화번호는 4자 이상 45자 이하로 입력해주세요.")
        String phone,

        @NotBlank(message = "주소는 필수 입력값입니다.")
        @Size(max = 100, message = "주소는 100자 이하로 입력해주세요.")
        String address
) {

    public MemberEntity toEntity() {
        return MemberEntity.builder()
                .id(id)
                .name(name)
                .pwd(pwd)
                .email(email)
                .phone(phone)
                .address(address)
                .build();
    }

}
