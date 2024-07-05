package com.team4.artgallery.dao;

import com.team4.artgallery.aspect.annotation.QueryApplied;
import com.team4.artgallery.controller.exception.NotFoundException;
import com.team4.artgallery.controller.exception.SqlException;
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
     * @throws SqlException 추가된 행의 수가 0인 경우 예외 발생 ({@link QueryApplied} 참조)
     */
    @QueryApplied("회원 정보를 추가하는 중 오류가 발생했습니다.")
    int createMember(MemberDto dto) throws SqlException;


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
     * @throws SqlException 수정된 행의 수가 0인 경우 예외 발생 ({@link QueryApplied} 참조)
     */
    @QueryApplied("회원 정보를 수정하는 중 오류가 발생했습니다.")
    int updateMember(MemberDto dto) throws SqlException;

    /**
     * 회원의 관리자 권한을 부여합니다.
     *
     * @param memberIds 회원 ID 목록
     * @return 수정된 행의 수
     * @throws NotFoundException 수정된 행의 수가 0인 경우 예외 발생 ({@link QueryApplied} 참조)
     */
    @QueryApplied(value = "회원 정보를 찾을 수 없습니다.", exceptionClass = NotFoundException.class)
    int grantAdminMembers(List<String> memberIds) throws NotFoundException;

    /**
     * 회원의 관리자 권한을 박탈합니다.
     *
     * @param memberIds 회원 ID 목록
     * @return 수정된 행의 수
     * @throws NotFoundException 수정된 행의 수가 0인 경우 예외 발생 ({@link QueryApplied} 참조)
     */
    @QueryApplied(value = "회원 정보를 찾을 수 없습니다.", exceptionClass = NotFoundException.class)
    int revokeAdminMembers(List<String> memberIds) throws NotFoundException;


    /* ========== DELETE =========== */

    /**
     * 회원 정보를 삭제합니다.
     *
     * @param memberId 회원 ID
     * @return 삭제된 행의 수
     * @throws NotFoundException 삭제된 행의 수가 0인 경우 예외 발생 ({@link QueryApplied} 참조)
     */
    @QueryApplied(value = "회원 정보를 찾을 수 없습니다.", exceptionClass = NotFoundException.class)
    int deleteMember(String memberId) throws NotFoundException;

    /**
     * 여러 회원 정보를 삭제합니다.
     *
     * @param memberIdList 회원 ID 목록
     * @return 삭제된 행의 수
     * @throws NotFoundException 삭제된 행의 수가 0인 경우 예외 발생 ({@link QueryApplied} 참조)
     */
    @QueryApplied(value = "회원 정보를 찾을 수 없습니다.", exceptionClass = NotFoundException.class)
    int deleteMembers(List<String> memberIdList) throws NotFoundException;

}
