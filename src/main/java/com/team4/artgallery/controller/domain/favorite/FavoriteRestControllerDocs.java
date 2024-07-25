package com.team4.artgallery.controller.domain.favorite;

import com.team4.artgallery.dto.FavoriteDto;
import com.team4.artgallery.entity.MemberEntity;
import com.team4.artgallery.util.Pagination;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;

import java.util.List;

interface FavoriteRestControllerDocs {

    @Tag(name = "member", description = "회원 메서드")
    @Operation(
            summary = "관심 예술품 여부 조회",
            description = "예술품을 관심 예술품으로 등록했는지 여부를 조회합니다.",
            method = "GET",
            responses = {
                    @ApiResponse(responseCode = "200", description = "관심 예술품으로 등록됨"),
                    @ApiResponse(responseCode = "204", description = "관심 예술품으로 등록되지 않음"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "401", description = "로그인 필요"),
                    @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음")
            }
    )
    ResponseEntity<?> getById(
            @Parameter(name = "aseq", description = "예술품 번호", required = true, in = ParameterIn.PATH)
            String aseq,

            @Parameter(hidden = true)
            MemberEntity loginMember
    );

    @Tag(name = "member", description = "회원 메서드")
    @Operation(
            summary = "관심 예술품 목록 조회",
            description = "로그인한 사용자의 관심 예술품 목록을 조회합니다.",
            method = "GET",
            responses = {
                    @ApiResponse(responseCode = "200", description = "예술품 목록 조회 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "401", description = "로그인 필요"),
                    @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음")
            }
    )
    List<FavoriteDto> getList(
            @ParameterObject
            Pagination pagination,

            @Parameter(hidden = true)
            MemberEntity loginMember
    );

    @Tag(name = "member", description = "회원 메서드")
    @Operation(
            summary = "관심 예술품 등록",
            description = "관심 예술품을 등록합니다.",
            method = "POST",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "성공"
                    ),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "401", description = "로그인 필요"),
                    @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음")
            }
    )
    void create(
            @Parameter(name = "aseq", description = "예술품 번호", required = true, in = ParameterIn.PATH)
            String aseq,

            @Parameter(hidden = true)
            MemberEntity loginMember
    );


    @Tag(name = "member", description = "회원 메서드")
    @Operation(
            summary = "관심 예술품 해제",
            description = "관심 예술품을 해제합니다.",
            method = "DELETE",
            responses = {
                    @ApiResponse(responseCode = "200", description = "관심 예술품 해제 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "401", description = "로그인 필요"),
                    @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음")
            }
    )
    void delete(
            @Parameter(name = "aseq", description = "예술품 번호", required = true, in = ParameterIn.PATH)
            String aseq,

            @Parameter(hidden = true)
            MemberEntity loginMember
    );

}
