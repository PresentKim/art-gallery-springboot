package com.team4.artgallery.repository;

import com.team4.artgallery.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, String>, JpaSpecificationExecutor<MemberEntity> {

    /**
     * 회원의 관리자 여부(char adminyn) 만 업데이트하는 메서드
     */
    @Modifying
    @Transactional
    @Query("update MemberEntity m set m.adminyn = :adminyn where m.id = :id")
    void updateAdminynById(@Param("id") String id, @Param("adminyn") char adminyn);

}