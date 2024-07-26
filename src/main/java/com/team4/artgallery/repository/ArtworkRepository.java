package com.team4.artgallery.repository;

import com.team4.artgallery.entity.ArtworkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ArtworkRepository extends JpaRepository<ArtworkEntity, Integer>, JpaSpecificationExecutor<ArtworkEntity> {

    @Query(value = "SELECT * FROM artwork ORDER BY RAND() LIMIT :count", nativeQuery = true)
    List<ArtworkEntity> findRandomArtworks(@Param("count") int count);

    /**
     * 예술품의 공개 여부(char displayyn) 만 업데이트하는 메서드
     */
    @Modifying
    @Transactional
    @Query("update ArtworkEntity a set a.displayyn = :displayyn where a.aseq = :aseq")
    void updateDisplayynByAseq(@Param("aseq") int aseq, @Param("displayyn") char displayyn);

}