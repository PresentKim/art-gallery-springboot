package com.team4.artgallery.repository;

import com.team4.artgallery.entity.QnaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface QnaRepository extends JpaRepository<QnaEntity, Integer>, JpaSpecificationExecutor<QnaEntity> {

}