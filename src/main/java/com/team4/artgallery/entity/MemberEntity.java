package com.team4.artgallery.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@DynamicInsert
@DynamicUpdate
@Builder
public record MemberEntity(
        @Id
        @Column(name = "id", length = 45, nullable = false)
        @Comment("아이디")
        String id,

        @Column(name = "name", length = 45, nullable = false)
        @Comment("이름")
        String name,

        @Column(name = "pwd", length = 45, nullable = false)
        @Comment("비밀번호")
        String pwd,

        @Column(name = "email", length = 45, nullable = false)
        @Comment("이메일")
        String email,

        @Column(name = "indate", nullable = false)
        @ColumnDefault("NOW()")
        @Comment("가입일")
        LocalDateTime indate,

        @Column(name = "phone", length = 45, nullable = false)
        @Comment("전화번호")
        String phone,

        @Column(name = "adminyn", nullable = false)
        @ColumnDefault("'N'")
        @Comment("관리자 여부")
        char adminyn,

        @Column(name = "address", length = 100, nullable = false)
        @Comment("주소")
        String address
) {

    public MemberEntity() {
        this(null, null, null, null, null, null, 'N', null);
    }

    public boolean isAdmin() {
        return adminyn == 'Y';
    }

}