package com.team4.artgallery.controller.domain.notice;

import com.team4.artgallery.dto.NoticeDto;
import com.team4.artgallery.dto.ResponseDto;
import com.team4.artgallery.dto.filter.NoticeFilter;
import com.team4.artgallery.entity.MemberEntity;
import com.team4.artgallery.util.Pagination;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;

import java.util.List;

interface NoticeRestControllerDocs {

    @Tag(name = "public", description = "사용자 메서드")
    @Operation(
            summary = "소식지 상세 조회",
            description = "소식지 상세 정보를 조회합니다.",
            method = "GET",
            responses = {
                    @ApiResponse(responseCode = "200", description = "소식지 상세 조회 성공"),
                    @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음")
            }
    )
    NoticeDto getById(
            @Parameter(name = "nseq", description = "소식지 번호", required = true, in = ParameterIn.PATH)
            String nseq
    );

    @Tag(name = "public", description = "사용자 메서드")
    @Operation(
            summary = "소식지 목록 조회",
            description = "소식지 목록을 조회합니다.",
            method = "GET",
            responses = {
                    @ApiResponse(responseCode = "200", description = "소식지 목록 조회 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            }
    )
    List<NoticeDto> getList(
            @ParameterObject
            NoticeFilter filter,
            @ParameterObject
            Pagination pagination
    );


    @Tag(name = "public", description = "사용자 메서드")
    @Operation(
            summary = "소식지 최근 목록 조회",
            description = "소식지 최근 목록을 조회합니다.",
            method = "GET",
            responses = {
                    @ApiResponse(responseCode = "200", description = "소식지 최근 목록 조회 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            }
    )
    List<NoticeDto> getRecentList(
            @Parameter(name = "count", description = "소식지 갯수", required = true)
            Integer count
    );


    @Tag(name = "admin", description = "관리자 메서드")
    @Operation(
            summary = "소식지 등록",
            description = "소식지를 등록합니다.",
            method = "POST",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "성공",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = NoticeDto.class)),
                                    @Content(mediaType = "text/html")
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 요청",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)),
                                    @Content(mediaType = "text/html")
                            }
                    )
            }
    )
    NoticeDto create(
            @ParameterObject
            NoticeDto artworkDto,

            @Parameter(hidden = true)
            MemberEntity loginMember
    );

    @Tag(name = "admin", description = "관리자 메서드")
    @Operation(
            summary = "소식지 수정",
            description = "소식지를 수정합니다.",
            method = "PUT",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "소식지 수정 성공"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 요청",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)),
                                    @Content(mediaType = "text/html")
                            }
                    )
            }
    )
    void update(
            @Parameter(name = "nseq", description = "소식지 번호", required = true, in = ParameterIn.PATH)
            String nseq,
            @ParameterObject
            NoticeDto artworkDto,

            @Parameter(hidden = true)
            MemberEntity loginMember
    );


    @Tag(name = "admin", description = "관리자 메서드")
    @Operation(
            summary = "소식지 삭제",
            description = "소식지를 삭제합니다.",
            method = "DELETE",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "소식지 삭제 성공"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 요청",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)),
                                    @Content(mediaType = "text/html")
                            }
                    )
            }
    )
    void delete(
            @Parameter(name = "nseq", description = "소식지 번호", required = true, in = ParameterIn.PATH)
            String nseq
    );

}
