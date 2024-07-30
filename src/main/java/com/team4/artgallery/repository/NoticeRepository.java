package com.team4.artgallery.repository;

import com.team4.artgallery.entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeEntity, Integer>, JpaSpecificationExecutor<NoticeEntity> {

    @Query(value = "SELECT * FROM notice ORDER BY indate DESC LIMIT :count", nativeQuery = true)
    List<NoticeEntity> findRecentNotices(@Param("count") int count);

    @Modifying
    @Transactional
    @Query(value = "UPDATE notice SET read_count = read_count + 1 WHERE nseq = :nseq", nativeQuery = true)
    void increaseReadCountById(Integer nseq);

}