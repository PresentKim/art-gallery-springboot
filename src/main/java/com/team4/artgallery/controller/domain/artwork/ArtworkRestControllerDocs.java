package com.team4.artgallery.controller.domain.artwork;

import com.team4.artgallery.dto.PageResponse;
import com.team4.artgallery.dto.artwork.ArtworkCreateDto;
import com.team4.artgallery.dto.artwork.ArtworkUpdateDto;
import com.team4.artgallery.dto.filter.ArtworkFilter;
import com.team4.artgallery.dto.request.DisplayRequest;
import com.team4.artgallery.entity.ArtworkEntity;
import com.team4.artgallery.entity.MemberEntity;
import com.team4.artgallery.util.Pagination;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;

import java.util.List;

interface ArtworkRestControllerDocs {

    @Tag(name = "public", description = "사용자 메서드")
    @Operation(
            summary = "예술품 상세 조회",
            description = "예술품 상세 정보를 조회합니다.",
            method = "GET",
            responses = {
                    @ApiResponse(responseCode = "200", description = "예술품 상세 조회 성공"),
                    @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음")
            }
    )
    ArtworkEntity getById(
            @Parameter(name = "aseq", description = "예술품 번호", required = true, in = ParameterIn.PATH)
            int aseq,

            @Parameter(hidden = true)
            MemberEntity loginMember
    );

    @Tag(name = "public", description = "사용자 메서드")
    @Operation(
            summary = "예술품 목록 조회",
            description = "예술품 목록을 조회합니다.",
            method = "GET",
            responses = {
                    @ApiResponse(responseCode = "200", description = "예술품 목록 조회 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            }
    )
    PageResponse<ArtworkEntity> getList(
            @ParameterObject
            ArtworkFilter filter,
            @ParameterObject
            Pagination pagination
    );


    @Tag(name = "public", description = "사용자 메서드")
    @Operation(
            summary = "예술품 랜덤 목록 조회",
            description = "예술품 랜덤 목록을 조회합니다.",
            method = "GET",
            responses = {
                    @ApiResponse(responseCode = "200", description = "예술품 랜덤 목록 조회 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            }
    )
    List<ArtworkEntity> getRandomList(
            @Parameter(name = "count", description = "예술품 갯수", required = true)
            Integer count
    );


    @Tag(name = "admin", description = "관리자 메서드")
    @Operation(
            summary = "예술품 등록",
            description = "예술품을 등록합니다.",
            method = "POST",
            responses = {
                    @ApiResponse(responseCode = "201", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "500", description = "파일 업로드 실패")
            }
    )
    ArtworkEntity create(
            @ParameterObject
            ArtworkCreateDto artworkCreateDto
    );

    @Tag(name = "admin", description = "관리자 메서드")
    @Operation(
            summary = "예술품 수정",
            description = "예술품을 수정합니다.",
            method = "PUT",
            responses = {
                    @ApiResponse(responseCode = "201", description = "예술품 수정 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            }
    )
    void update(
            @Parameter(name = "aseq", description = "예술품 번호", required = true, in = ParameterIn.PATH)
            String aseq,
            @ParameterObject
            ArtworkUpdateDto artworkUpdateDto,

            @Parameter(hidden = true)
            MemberEntity loginMember
    );


    @Tag(name = "admin", description = "관리자 메서드")
    @Operation(
            summary = "예술품 삭제",
            description = "예술품을 삭제합니다.",
            method = "DELETE",
            responses = {
                    @ApiResponse(responseCode = "204", description = "예술품 삭제 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            }
    )
    void delete(
            @Parameter(name = "aseq", description = "예술품 번호", required = true, in = ParameterIn.PATH)
            String aseq
    );


    @Tag(name = "admin", description = "관리자 메서드")
    @Operation(
            summary = "예술품 전시 여부 수정",
            description = "예술품 전시 여부를 수정합니다.",
            method = "PUT",
            responses = {
                    @ApiResponse(responseCode = "200", description = "예술품 전시 여부 변경 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            }
    )
    void updateDisplay(
            @Parameter(name = "aseq", description = "예술품 번호", required = true, in = ParameterIn.PATH)
            String aseq,
            @ParameterObject
            DisplayRequest request,

            @Parameter(hidden = true)
            MemberEntity loginMember
    );

}
