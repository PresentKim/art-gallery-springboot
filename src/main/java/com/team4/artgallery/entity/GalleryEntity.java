package com.team4.artgallery.entity;

import jakarta.persistence.*;
import lombok.Builder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Table(name = "gallery")
@Comment("갤러리")
@DynamicInsert
@DynamicUpdate
@Builder
public record GalleryEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "gseq", nullable = false)
        @Comment("갤러리 번호")
        Integer gseq,

        @ManyToOne
        @JoinColumn(name = "author", nullable = false)
        @Comment("작성자")
        MemberEntity author,

        @Column(name = "title", nullable = false, length = 100)
        @Comment("제목")
        String title,

        @Column(name = "content", nullable = false)
        @Comment("설명")
        String content,

        @Column(name = "image", length = 200, nullable = false)
        @Comment("저장된 파일명")
        String imageFileName,

        @Column(name = "read_count", nullable = false)
        @ColumnDefault("0")
        @Comment("조회수")
        Integer readCount,

        @Column(name = "indate", nullable = false)
        @ColumnDefault("NOW()")
        @Comment("등록일")
        LocalDateTime indate
) {

    /**
     * 사용자가 접근 가능한 이미지 경로를 반환합니다.
     */
    public String getImageSrc() {
        if (imageFileName.startsWith("http")) {
            return imageFileName;
        }

        return "/static/image/gallery/" + imageFileName;
    }

}