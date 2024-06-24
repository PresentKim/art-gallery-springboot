package com.team4.artgallery.dao;

import com.team4.artgallery.dto.MemberDto;
import com.team4.artgallery.util.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IMemberDao {

    /* ========== CREATE =========== */

    /**
     * 회원 정보를 저장합니다.
     *
     * @param dto 회원 정보
     * @return 저장된 행의 수
     */
    int createMember(MemberDto dto);


    /* ========== READ =========== */

    /**
     * 회원 정보를 가져옵니다.
     *
     * @param memberId 회원 ID
     * @return 회원 정보
     */
    MemberDto getMember(String memberId);

    /**
     * 전체 회원 목록을 가져옵니다.
     *
     * @param pagination 페이지네이션 정보
     * @return 회원 목록
     */
    List<MemberDto> getMembers(Pagination pagination);

    /**
     * 검색된 회원 목록을 가져옵니다.
     *
     * @param search     검색어
     * @param pagination 페이지네이션 정보
     * @return 회원 목록
     */
    List<MemberDto> searchMembers(@Param("search") String search, @Param("pagination") Pagination pagination);

    /**
     * 전체 회원 수를 가져옵니다.
     *
     * @return 전체 회원 수
     */
    int countMembers();

    /**
     * 검색된 회원 수를 가져옵니다.
     *
     * @param search 검색어
     * @return 검색된 회원 수
     */
    int countSearchMembers(@Param("search") String search);


    /* ========== UPDATE =========== */

    /**
     * 회원 정보를 수정합니다.
     *
     * @param dto 회원 정보
     * @return 수정된 행의 수
     */
    int updateMember(MemberDto dto);

    /**
     * 회원의 관리자 권한을 부여합니다.
     *
     * @param memberIds 회원 ID 목록
     * @return 수정된 행의 수
     */
    int grantAdminMembers(List<String> memberIds);

    /**
     * 회원의 관리자 권한을 박탈합니다.
     *
     * @param memberIds 회원 ID 목록
     * @return 수정된 행의 수
     */
    int revokeAdminMembers(List<String> memberIds);


    /* ========== DELETE =========== */

    /**
     * 회원 정보를 삭제합니다.
     *
     * @param memberId 회원 ID
     * @return 삭제된 행의 수
     */
    int deleteMember(String memberId);

    /**
     * 여러 회원 정보를 삭제합니다.
     *
     * @param memberIdList 회원 ID 목록
     * @return 삭제된 행의 수
     */
    int deleteMembers(List<String> memberIdList);

}
