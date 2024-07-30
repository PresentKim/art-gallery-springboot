package com.team4.artgallery.entity;

import jakarta.persistence.*;
import lombok.Builder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Table(name = "notice")
@DynamicInsert
@DynamicUpdate
@Builder
public record NoticeEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "nseq", nullable = false)
        @Comment("공지사항 번호")
        Integer nseq,

        @ManyToOne
        @JoinColumn(name = "author", nullable = false)
        @Comment("작성자")
        MemberEntity author,

        @Column(name = "title", length = 100, nullable = false)
        @Comment("제목")
        String title,

        @Column(name = "content", nullable = false)
        @Comment("내용")
        String content,

        @Column(name = "category", length = 45, nullable = false)
        @Comment("카테고리")
        String category,

        @Column(name = "read_count", nullable = false)
        @ColumnDefault("0")
        @Comment("조회수")
        Integer readCount,

        @Column(name = "indate", nullable = false)
        @ColumnDefault("NOW()")
        @Comment("등록일")
        LocalDateTime indate
) {

    public NoticeEntity() {
        this(0, null, null, null, null, 0, null);
    }

}