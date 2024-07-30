package com.team4.artgallery.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Table(name = "favorite_artwork")
@Comment("예술품")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_id", nullable = false, unique = true)
    @Comment("회원 아이디")
    private MemberEntity member;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "artwork_id", nullable = false, unique = true)
    @Comment("예술품 번호")
    private ArtworkEntity artwork;

    @Column(name = "writedate", nullable = false)
    @ColumnDefault("NOW()")
    @Comment("등록일")
    private LocalDateTime writedate;

}