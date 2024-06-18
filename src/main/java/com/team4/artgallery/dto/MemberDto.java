package com.team4.artgallery.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    @NotBlank(message = "아이디는 필수 입력값입니다.")
    @Size(min = 4, max = 45, message = "아이디는 4자 이상 45자 이하로 입력해주세요.")
    private String id;

    @NotBlank(message = "이름은 필수 입력값입니다.")
    @Size(min = 2, max = 45, message = "이름은 2자 이상 45자 이하로 입력해주세요.")
    private String name;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Size(min = 4, max = 45, message = "비밀번호는 4자 이상 45자 이하로 입력해주세요.")
    private String pwd;

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Size(min = 4, max = 45, message = "이메일은 4자 이상 45자 이하로 입력해주세요.")
    private String email;

    @NotBlank(message = "전화번호는 필수 입력값입니다.")
    @Size(min = 4, max = 45, message = "전화번호는 4자 이상 45자 이하로 입력해주세요.")
    private String phone;

    private Date indate;

    private String adminyn;

    public boolean isAdmin() {
        return adminyn.equals("Y");
    }

    public void setAdmin(boolean isAdmin) {
        adminyn = isAdmin ? "Y" : "N";
    }

}
