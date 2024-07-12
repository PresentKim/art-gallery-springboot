package com.team4.artgallery.controller.domain.artwork;

import com.team4.artgallery.dto.ArtworkDto;
import com.team4.artgallery.dto.ResponseDto;
import com.team4.artgallery.dto.filter.ArtworkFilter;
import com.team4.artgallery.util.Pagination;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.multipart.MultipartFile;

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
    ArtworkDto getById(
            @Parameter(name = "aseq", description = "예술품 번호", required = true, in = ParameterIn.PATH)
            int aseq
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
    Pagination.Pair<ArtworkDto> getList(
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
    List<ArtworkDto> getRandomList(
            @Parameter(name = "count", description = "예술품 갯수", required = true)
            Integer count
    );


    @Tag(name = "admin", description = "관리자 메서드")
    @Operation(
            summary = "예술품 등록",
            description = "예술품을 등록합니다.",
            method = "POST",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "성공",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)),
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
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "파일 업로드 실패",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)),
                                    @Content(mediaType = "text/html")
                            }
                    )
            }
    )
    ResponseDto create(
            @ParameterObject
            ArtworkDto artworkDto,
            @Parameter(name = "imageFile", description = "이미지 파일", required = true)
            MultipartFile imageFile
    );

    @Tag(name = "admin", description = "관리자 메서드")
    @Operation(
            summary = "예술품 수정",
            description = "예술품을 수정합니다.",
            parameters = {
            },
            method = "PUT",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "예술품 수정 성공",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)),
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
    ResponseDto update(
            @Parameter(name = "aseq", description = "예술품 번호", required = true, in = ParameterIn.PATH)
            String aseq,
            @ParameterObject
            ArtworkDto artworkDto,
            @Parameter(name = "imageFile", description = "이미지 파일")
            MultipartFile imageFile
    );


    @Tag(name = "admin", description = "관리자 메서드")
    @Operation(
            summary = "예술품 삭제",
            description = "예술품을 삭제합니다.",
            parameters = {
                    @Parameter(name = "aseq", description = "예술품 번호", required = true, in = ParameterIn.PATH)
            },
            method = "DELETE",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "예술품 삭제 성공",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)),
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
    void delete(
            @Parameter(name = "aseq", description = "예술품 번호", required = true, in = ParameterIn.PATH)
            Integer aseq
    );


    @Tag(name = "admin", description = "관리자 메서드")
    @Operation(
            summary = "예술품 전시 여부 토글",
            description = "예술품 전시 여부를 토글합니다.",
            parameters = {
                    @Parameter(name = "aseq", description = "예술품 번호", required = true)
            },
            method = "PUT",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "예술품 전시 여부 토글 성공",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)),
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
    Object toggleArtworkDisplay(
            @Parameter(name = "aseq", description = "예술품 번호", required = true, in = ParameterIn.PATH)
            String aseq
    );

}
