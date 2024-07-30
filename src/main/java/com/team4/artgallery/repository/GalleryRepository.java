package com.team4.artgallery.repository;

import com.team4.artgallery.entity.GalleryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GalleryRepository extends JpaRepository<GalleryEntity, Integer>, JpaSpecificationExecutor<GalleryEntity> {

    @Query(value = "UPDATE gallery SET read_count = read_count + 1 WHERE gseq = :gseq", nativeQuery = true)
    void increaseReadCountById(Integer gseq);

}