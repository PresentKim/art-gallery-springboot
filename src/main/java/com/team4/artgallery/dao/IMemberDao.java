package com.team4.artgallery.dao;

import com.team4.artgallery.dto.MemberDto;
import com.team4.artgallery.util.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IMemberDao {

    int insertMember(MemberDto dto);

    MemberDto findMember(String memberId);

    List<MemberDto> getMembers(Pagination pagination);

    List<MemberDto> searchMembers(@Param("search") String search, @Param("pagination") Pagination pagination);

    int getAllCount();

    int getSearchCount(@Param("search") String search);

    int updateMember(MemberDto dto);

    int grantAdmin(String memberId);

    int revokeAdmin(String memberId);

    int deleteMember(String memberId);

}
