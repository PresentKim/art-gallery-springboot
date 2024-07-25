package com.team4.artgallery.dto.member;

import jakarta.validation.constraints.NotBlank;

public record MemberLoginDto(
        @NotBlank(message = "아이디는 필수 입력값입니다.")
        String id,

        @NotBlank(message = "비밀번호는 필수 입력값입니다.")
        String pwd
) {
}
