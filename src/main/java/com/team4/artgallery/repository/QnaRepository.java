package com.team4.artgallery.repository;

import com.team4.artgallery.entity.QnaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface QnaRepository extends JpaRepository<QnaEntity, Integer>, JpaSpecificationExecutor<QnaEntity> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE QnaEntity q SET q.reply = :reply WHERE q.qseq = :qseq")
    void updateReplyById(Integer qseq, String reply);

}