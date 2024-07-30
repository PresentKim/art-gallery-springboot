package com.team4.artgallery.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.team4.artgallery.dto.view.Views;
import jakarta.persistence.*;
import lombok.*;
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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GalleryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gseq", nullable = false)
    @Comment("갤러리 번호")
    @JsonView({Views.Summary.class, Views.Detail.class})
    private Integer gseq;

    @ManyToOne
    @JoinColumn(name = "author", nullable = false)
    @Comment("작성자")
    @JsonView({Views.Summary.class, Views.Detail.class})
    private MemberEntity author;

    @Column(name = "title", nullable = false, length = 100)
    @Comment("제목")
    @JsonView({Views.Summary.class, Views.Detail.class})
    private String title;

    @Column(name = "content", length = 9999, nullable = false)
    @Comment("설명")
    @JsonView({Views.Detail.class})
    private String content;

    @Column(name = "image", length = 200, nullable = false)
    @Comment("저장된 파일명")
    private String imageFileName;

    @Column(name = "read_count", nullable = false)
    @ColumnDefault("0")
    @Comment("조회수")
    @JsonView({Views.Summary.class, Views.Detail.class})
    private Integer readCount;

    @Column(name = "indate", nullable = false)
    @ColumnDefault("NOW()")
    @Comment("등록일")
    @JsonView({Views.Summary.class, Views.Detail.class})
    private LocalDateTime indate;

    /**
     * 사용자가 접근 가능한 이미지 경로를 반환합니다.
     */
    @JsonView({Views.Summary.class, Views.Detail.class})
    public String getImageSrc() {
        if (imageFileName.startsWith("http")) {
            return imageFileName;
        }

        return "/static/image/gallery/" + imageFileName;
    }

}