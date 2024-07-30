package com.team4.artgallery.repository;

import com.team4.artgallery.entity.FavoriteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Integer>, JpaSpecificationExecutor<FavoriteEntity> {

    Page<FavoriteEntity> findAllByMemberId(String memberId, Pageable pageable);

    Boolean existsByMemberIdAndArtworkAseq(String memberId, int artworkAseq);

    @Modifying
    @Transactional
    @Query(value = "SELECT * FROM favorite_artwork WHERE member_id = :memberId AND artwork_id = :artworkAseq", nativeQuery = true)
    void createByMemberIdAndArtworkAseq(String memberId, int artworkAseq);

    @Modifying
    @Transactional
    void deleteByMemberIdAndArtworkAseq(String memberId, int artworkAseq);

}