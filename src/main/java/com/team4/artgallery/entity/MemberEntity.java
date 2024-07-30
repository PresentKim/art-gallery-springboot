package com.team4.artgallery.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.team4.artgallery.dto.view.Views;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberEntity {

    @Id
    @Column(name = "id", length = 45, nullable = false)
    @Comment("아이디")
    @JsonView({Views.Summary.class, Views.Detail.class})
    private String id;

    @Column(name = "name", length = 45, nullable = false)
    @Comment("이름")
    @JsonView({Views.Summary.class, Views.Detail.class})
    private String name;

    @Column(name = "pwd", length = 45, nullable = false)
    @Comment("비밀번호")
    private String pwd;

    @Column(name = "email", length = 45, nullable = false)
    @Comment("이메일")
    @JsonView({Views.Summary.class, Views.Detail.class})
    private String email;

    @Column(name = "indate", nullable = false)
    @ColumnDefault("NOW()")
    @Comment("가입일")
    @JsonView({Views.Summary.class, Views.Detail.class})
    private LocalDateTime indate;

    @Column(name = "phone", length = 45, nullable = false)
    @Comment("전화번호")
    @JsonView({Views.Summary.class, Views.Detail.class})
    private String phone;

    @Column(name = "adminyn", nullable = false)
    @ColumnDefault("'N'")
    @Comment("관리자 여부")
    private char adminyn;

    @Column(name = "address", length = 100, nullable = false)
    @Comment("주소")
    @JsonView({Views.Summary.class, Views.Detail.class})
    private String address;

    @JsonView({Views.Summary.class, Views.Detail.class})
    public boolean isAdmin() {
        return adminyn == 'Y';
    }

}