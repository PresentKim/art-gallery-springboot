package com.team4.artgallery.repository;

import com.team4.artgallery.entity.ArtworkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtworkRepository extends JpaRepository<ArtworkEntity, Integer>, JpaSpecificationExecutor<ArtworkEntity> {

    @Query(value = "SELECT * FROM artwork ORDER BY RAND() LIMIT :count", nativeQuery = true)
    List<ArtworkEntity> findRandomArtworks(@Param("count") int count);

}