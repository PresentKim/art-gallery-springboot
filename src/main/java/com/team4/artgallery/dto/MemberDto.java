package com.team4.artgallery.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.team4.artgallery.dto.view.Views;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

public class MemberDto {

    @Getter
    @Setter
    @NotBlank(groups = OnLogin.class, message = "아이디는 필수 입력값입니다.")
    @Size(groups = OnLogin.class, min = 4, max = 45, message = "아이디는 4자 이상 45자 이하로 입력해주세요.")
    @JsonView({Views.Identifier.class, Views.Summary.class, Views.Detail.class})
    private String id;

    @Getter
    @Setter
    @NotBlank(groups = OnUpdate.class, message = "이름은 필수 입력값입니다.")
    @Size(groups = OnUpdate.class, min = 2, max = 45, message = "이름은 2자 이상 45자 이하로 입력해주세요.")
    @JsonView({Views.Summary.class, Views.Detail.class})
    private String name;

    @Getter
    @Setter
    @NotBlank(groups = OnLogin.class, message = "비밀번호는 필수 입력값입니다.")
    @Size(groups = OnLogin.class, min = 4, max = 45, message = "비밀번호는 4자 이상 45자 이하로 입력해주세요.")
    private String pwd;

    @Getter
    @Setter
    @NotBlank(groups = OnUpdate.class, message = "이메일은 필수 입력값입니다.")
    @Size(groups = OnUpdate.class, min = 4, max = 45, message = "이메일은 4자 이상 45자 이하로 입력해주세요.")
    @JsonView({Views.Summary.class, Views.Detail.class})
    private String email;

    @Getter
    @Setter
    @NotBlank(groups = OnUpdate.class, message = "전화번호는 필수 입력값입니다.")
    @Size(groups = OnUpdate.class, min = 4, max = 45, message = "전화번호는 4자 이상 45자 이하로 입력해주세요.")
    @JsonView({Views.Summary.class, Views.Detail.class})
    private String phone;

    @Getter
    @Setter
    @NotBlank(groups = OnUpdate.class, message = "주소는 필수 입력값입니다.")
    @Size(groups = OnUpdate.class, max = 100, message = "주소는 100자 이하로 입력해주세요.")
    @JsonView({Views.Detail.class})
    private String address;

    @Getter
    @Setter
    @Null(message = "관리자 여부는 직접 설정할 수 없습니다.")
    private String adminyn;

    @Getter
    @Null(message = "가입일은 직접 설정할 수 없습니다.")
    @JsonView({Views.Summary.class, Views.Detail.class})
    private Date indate;

    @JsonView({Views.Summary.class, Views.Detail.class})
    public boolean isAdmin() {
        return adminyn.equals("Y");
    }

    public void setAdmin(boolean isAdmin) {
        adminyn = isAdmin ? "Y" : "N";
    }


    // 그룹 클래스

    /**
     * 회원 가입 요청 시 사용하는 그룹
     * <p>
     * 필수 요소 : {@link #id}, {@link #name}, {@link #pwd}, {@link #email}, {@link #phone}, {@link #address}
     */
    public interface OnJoin {
    }

    /**
     * 정보 수정 요청 시 사용하는 그룹
     * <p>
     * 필수 요소 : {@link #name}, {@link #email}, {@link #phone}, {@link #address}
     */
    public interface OnUpdate extends OnJoin {
    }

    /**
     * 로그인 요청 시 사용하는 그룹
     * <p>
     * 필수 요소 : {@link #id}, {@link #pwd}
     */
    public interface OnLogin extends OnJoin {
    }

}
