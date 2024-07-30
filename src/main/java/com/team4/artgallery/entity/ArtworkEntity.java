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
@Table(name = "artwork")
@Comment("예술품")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtworkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aseq")
    @Comment("예술품 번호")
    @JsonView({Views.Identifier.class, Views.Summary.class, Views.Detail.class})
    private Integer aseq;

    @Column(name = "name", length = 45, nullable = false)
    @Comment("이름")
    @JsonView({Views.Summary.class, Views.Detail.class})
    private String name;

    @Column(name = "category", length = 45, nullable = false)
    @Comment("카테고리")
    @JsonView({Views.Detail.class})
    private String category;

    @Column(name = "artist", length = 45, nullable = false)
    @Comment("작가")
    @JsonView({Views.Summary.class, Views.Detail.class})
    private String artist;

    @Column(name = "year", length = 4, nullable = false)
    @Comment("제작년도")
    @JsonView({Views.Detail.class})
    private String year;

    @Column(name = "material", length = 45, nullable = false)
    @Comment("재질")
    @JsonView({Views.Detail.class})
    private String material;

    @Column(name = "size", length = 45, nullable = false)
    @Comment("크기")
    @JsonView({Views.Detail.class})
    private String size;

    @Column(name = "content", length = 9999, nullable = false)
    @Comment("설명")
    @JsonView({Views.Detail.class})
    private String content;

    @Column(name = "image", length = 200, nullable = false)
    @Comment("저장된 파일명")
    @JsonView()
    private String imageFileName;

    @Column(name = "display", length = 1, nullable = false)
    @Comment("전시여부")
    @ColumnDefault("1")
    @JsonView({Views.Detail.class})
    private Boolean display;

    @Column(name = "indate", nullable = false)
    @ColumnDefault("NOW()")
    @Comment("등록일")
    @JsonView({Views.Detail.class})
    private LocalDateTime indate;

    /**
     * 사용자가 접근 가능한 이미지 경로를 반환합니다.
     */
    @JsonView({Views.Summary.class, Views.Detail.class})
    public String getImageSrc() {
        if (imageFileName.startsWith("http")) {
            return imageFileName;
        }

        return "/static/image/artwork/" + imageFileName;
    }

}