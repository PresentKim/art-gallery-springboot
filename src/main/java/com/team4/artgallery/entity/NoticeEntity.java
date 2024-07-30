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
@Table(name = "notice")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nseq", nullable = false)
    @Comment("공지사항 번호")
    @JsonView({Views.Identifier.class, Views.Summary.class, Views.Detail.class})
    private Integer nseq;

    @ManyToOne
    @JoinColumn(name = "author", nullable = false)
    @Comment("작성자")
    @JsonView({Views.Summary.class, Views.Detail.class})
    private MemberEntity author;

    @Column(name = "title", length = 100, nullable = false)
    @Comment("제목")
    @JsonView({Views.Summary.class, Views.Detail.class})
    private String title;

    @Column(name = "content", length = 9999, nullable = false)
    @Comment("내용")
    @JsonView({Views.Summary.class, Views.Detail.class})
    private String content;

    @Column(name = "category", length = 45, nullable = false)
    @Comment("카테고리")
    @JsonView({Views.Summary.class, Views.Detail.class})
    private String category;

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

}