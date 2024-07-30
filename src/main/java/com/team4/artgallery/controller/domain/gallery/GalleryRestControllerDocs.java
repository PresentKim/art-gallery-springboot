package com.team4.artgallery.controller.domain.gallery;

import com.team4.artgallery.dto.PageResponse;
import com.team4.artgallery.dto.filter.KeywordFilter;
import com.team4.artgallery.dto.gallery.GalleryCreateDto;
import com.team4.artgallery.dto.gallery.GalleryUpdateDto;
import com.team4.artgallery.entity.GalleryEntity;
import com.team4.artgallery.entity.MemberEntity;
import com.team4.artgallery.util.Pagination;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;

interface GalleryRestControllerDocs {

    @Tag(name = "public", description = "사용자 메서드")
    @Operation(
            summary = "갤러리 상세 조회",
            description = "갤러리 상세 정보를 조회합니다.",
            method = "GET",
            responses = {
                    @ApiResponse(responseCode = "200", description = "갤러리 상세 조회 성공"),
                    @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음")
            }
    )
    GalleryEntity getById(
            @Parameter(name = "gseq", description = "갤러리 번호", required = true, in = ParameterIn.PATH)
            Integer gseq
    );

    @Tag(name = "public", description = "사용자 메서드")
    @Operation(
            summary = "갤러리 목록 조회",
            description = "갤러리 목록을 조회합니다.",
            method = "GET",
            responses = {
                    @ApiResponse(responseCode = "200", description = "갤러리 목록 조회 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            }
    )
    PageResponse<GalleryEntity> getList(
            @ParameterObject
            KeywordFilter filter,
            @ParameterObject
            Pagination pagination
    );

    @Tag(name = "member", description = "회원 메서드")
    @Operation(
            summary = "갤러리 등록",
            description = "갤러리를 등록합니다.",
            method = "POST",
            responses = {
                    @ApiResponse(responseCode = "201", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "500", description = "파일 업로드 실패")
            }
    )
    void create(
            @ParameterObject
            GalleryCreateDto galleryCreateDto,

            @Parameter(hidden = true)
            MemberEntity loginMember
    );

    @Tag(name = "member", description = "회원 메서드")
    @Operation(
            summary = "갤러리 수정",
            description = "갤러리를 수정합니다.",
            method = "PUT",
            responses = {
                    @ApiResponse(responseCode = "201", description = "갤러리 수정 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            }
    )
    void update(
            @Parameter(name = "gseq", description = "갤러리 번호", required = true, in = ParameterIn.PATH)
            String gseq,
            @ParameterObject
            GalleryUpdateDto galleryUpdateDto,

            @Parameter(hidden = true)
            MemberEntity loginMember
    );

    @Tag(name = "admin", description = "관리자 메서드")
    @Operation(
            summary = "갤러리 삭제",
            description = "갤러리를 삭제합니다.",
            method = "DELETE",
            responses = {
                    @ApiResponse(responseCode = "204", description = "갤러리 삭제 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            }
    )
    void delete(
            @Parameter(name = "gseq", description = "갤러리 번호", required = true, in = ParameterIn.PATH)
            String gseq,

            @Parameter(hidden = true)
            MemberEntity loginMember
    );

}
