package com.team4.artgallery.controller.domain.qna;

import com.team4.artgallery.dto.QnaDto;
import com.team4.artgallery.dto.ResponseDto;
import com.team4.artgallery.dto.filter.QnaFilter;
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

interface QnaRestControllerDocs {

    @Tag(name = "public", description = "사용자 메서드")
    @Operation(
            summary = "문의글 상세 조회",
            description = "문의글 상세 정보를 조회합니다.",
            method = "GET",
            responses = {
                    @ApiResponse(responseCode = "200", description = "문의글 상세 조회 성공"),
                    @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음")
            }
    )
    QnaDto getById(
            @Parameter(name = "qseq", description = "문의글 번호", required = true, in = ParameterIn.PATH)
            String qseq
    );

    @Tag(name = "public", description = "사용자 메서드")
    @Operation(
            summary = "문의글 목록 조회",
            description = "문의글 목록을 조회합니다.",
            method = "GET",
            responses = {
                    @ApiResponse(responseCode = "200", description = "문의글 목록 조회 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            }
    )
    List<QnaDto> getList(
            @ParameterObject
            QnaFilter filter,
            @ParameterObject
            Pagination pagination
    );


    @Tag(name = "admin", description = "관리자 메서드")
    @Operation(
            summary = "문의글 등록",
            description = "문의글을 등록합니다.",
            method = "POST",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "성공",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = QnaDto.class)),
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
    QnaDto create(
            @ParameterObject
            QnaDto artworkDto
    );

    @Tag(name = "admin", description = "관리자 메서드")
    @Operation(
            summary = "문의글 수정",
            description = "문의글을 수정합니다.",
            method = "PUT",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "문의글 수정 성공"
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
            @Parameter(name = "qseq", description = "문의글 번호", required = true, in = ParameterIn.PATH)
            String qseq,
            @ParameterObject
            QnaDto artworkDto
    );

    @Tag(name = "admin", description = "관리자 메서드")
    @Operation(
            summary = "문의글 답변 수정",
            description = "문의글 답변을 수정합니다.",
            method = "PUT",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "문의글 수정 성공"
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
    void updateReply(
            @Parameter(name = "qseq", description = "문의글 번호", required = true, in = ParameterIn.PATH)
            String qseq,
            @Parameter(name = "reply", description = "문의글 답변", required = true, in = ParameterIn.QUERY)
            String reply
    );


    @Tag(name = "admin", description = "관리자 메서드")
    @Operation(
            summary = "문의글 삭제",
            description = "문의글을 삭제합니다.",
            method = "DELETE",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "문의글 삭제 성공"
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
            @Parameter(name = "qseq", description = "문의글 번호", required = true, in = ParameterIn.PATH)
            String qseq
    );

}
