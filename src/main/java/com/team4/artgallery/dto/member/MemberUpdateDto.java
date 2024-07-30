package com.team4.artgallery.dto.member;

import com.team4.artgallery.entity.MemberEntity;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MemberUpdateDto(
        @Size(min = 2, max = 45, message = "이름은 2자 이상 45자 이하로 입력해주세요.")
        String name,

        @Pattern(regexp = "^$|^.{4,45}$", message = "비밀번호는 4자 이상 45자 이하로 입력해주세요.")
        String pwd,

        @Size(min = 4, max = 45, message = "이메일은 4자 이상 45자 이하로 입력해주세요.")
        String email,

        @Size(min = 4, max = 45, message = "전화번호는 4자 이상 45자 이하로 입력해주세요.")
        String phone,

        @Size(max = 100, message = "주소는 100자 이하로 입력해주세요.")
        String address
) {

    public MemberEntity toEntity(String id) {
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
