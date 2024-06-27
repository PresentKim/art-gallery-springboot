package com.team4.artgallery.dao;

import com.team4.artgallery.dto.MemberDto;
import com.team4.artgallery.dto.filter.KeywordFilter;
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
     * 검색된 회원 목록을 가져옵니다.
     *
     * @param filter     검색 필터
     * @param pagination 페이지네이션 정보
     * @return 회원 목록
     */
    List<MemberDto> getMembers(@Param("filter") KeywordFilter filter, @Param("pagination") Pagination pagination);

    /**
     * 검색된 회원 수를 가져옵니다.
     *
     * @param filter 검색 필터
     * @return 검색된 회원 수
     */
    int countMembers(@Param("filter") KeywordFilter filter);


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
