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

    /**
     * 회원 가입 및 정보 수정 요청 시 사용하는 그룹
     * <p>
     * 필수 요소 : {@link #id}, {@link #name}, {@link #pwd}, {@link #email}, {@link #phone}
     * <p>
     * 포함 그룹 : {@link OnLogin}
     */
    public static final Class<?> FORM_GROUP = OnForm.class;

    /**
     * 로그인 요청 시 사용하는 그룹
     * <p>
     * 필수 요소 : {@link #id}, {@link #pwd}
     */
    public static final Class<?> LOGIN_GROUP = OnLogin.class;

    /**
     * 회원 아이디
     */
    @NotBlank(groups = LOGIN_GROUP, message = "아이디는 필수 입력값입니다.")
    @Size(groups = LOGIN_GROUP, min = 4, max = 45, message = "아이디는 4자 이상 45자 이하로 입력해주세요.")
    private String id;

    @NotBlank(groups = FORM_GROUP, message = "이름은 필수 입력값입니다.")
    @Size(groups = FORM_GROUP, min = 2, max = 45, message = "이름은 2자 이상 45자 이하로 입력해주세요.")
    private String name;

    @NotBlank(groups = LOGIN_GROUP, message = "비밀번호는 필수 입력값입니다.")
    @Size(groups = LOGIN_GROUP, min = 4, max = 45, message = "비밀번호는 4자 이상 45자 이하로 입력해주세요.")
    private String pwd;

    @NotBlank(groups = FORM_GROUP, message = "이메일은 필수 입력값입니다.")
    @Size(groups = FORM_GROUP, min = 4, max = 45, message = "이메일은 4자 이상 45자 이하로 입력해주세요.")
    private String email;

    @NotBlank(groups = FORM_GROUP, message = "전화번호는 필수 입력값입니다.")
    @Size(groups = FORM_GROUP, min = 4, max = 45, message = "전화번호는 4자 이상 45자 이하로 입력해주세요.")
    private String phone;

    private Date indate;

    private String adminyn;

    public boolean isAdmin() {
        return adminyn.equals("Y");
    }

    public void setAdmin(boolean isAdmin) {
        adminyn = isAdmin ? "Y" : "N";
    }


    // 그룹 클래스

    private interface OnForm extends OnLogin {
    }

    private interface OnLogin {
    }

}
