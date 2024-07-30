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
@Table(name = "qna")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qseq", nullable = false)
    @Comment("문의글 번호")
    @JsonView({Views.Summary.class, Views.Detail.class})
    private Integer qseq;

    @Column(name = "title", length = 100, nullable = false)
    @Comment("제목")
    @JsonView({Views.Summary.class, Views.Detail.class})
    private String title;

    @Column(name = "content", nullable = false)
    @Comment("내용")
    @JsonView({Views.Detail.class})
    private String content;

    @Column(name = "reply", length = 9999, nullable = false)
    @Comment("답변")
    @JsonView({Views.Detail.class})
    private String reply;

    @Column(name = "email", length = 45, nullable = false)
    @Comment("이메일")
    @JsonView({Views.Detail.class})
    private String email;

    @Column(name = "phone", length = 45, nullable = false)
    @Comment("전화번호")
    @JsonView({Views.Detail.class})
    private String phone;

    @Column(name = "pwd", length = 45, nullable = false)
    @Comment("비밀번호")
    private String pwd;

    @Column(name = "display", nullable = false)
    @ColumnDefault("0")
    @Comment("공개여부")
    @JsonView({Views.Summary.class, Views.Detail.class})
    private Boolean display;

    @Column(name = "indate", nullable = false)
    @ColumnDefault("NOW()")
    @Comment("등록일")
    @JsonView({Views.Summary.class, Views.Detail.class})
    private LocalDateTime indate;

}