package com.team4.artgallery.entity;

import jakarta.persistence.*;
import lombok.Builder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Table(name = "qna")
@DynamicInsert
@DynamicUpdate
@Builder
public record QnaEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "qseq", nullable = false)
        @Comment("문의글 번호")
        Integer qseq,

        @Column(name = "title", length = 100, nullable = false)
        @Comment("제목")
        String title,

        @Column(name = "content", nullable = false)
        @Comment("내용")
        String content,

        @Column(name = "reply", nullable = false)
        @Comment("답변")
        String reply,

        @Column(name = "email", length = 45, nullable = false)
        @Comment("이메일")
        String email,

        @Column(name = "phone", length = 45, nullable = false)
        @Comment("전화번호")
        String phone,

        @Column(name = "pwd", length = 45, nullable = false)
        @Comment("비밀번호")
        String pwd,

        @Column(name = "display", nullable = false)
        @ColumnDefault("0")
        @Comment("공개여부")
        Boolean display,

        @Column(name = "indate", nullable = false)
        @ColumnDefault("NOW()")
        @Comment("등록일")
        LocalDateTime indate
) {

    public QnaEntity() {
        this(null, null, null, null, null, null, null, null, null);
    }

}