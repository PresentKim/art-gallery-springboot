package com.team4.artgallery.controller.domain.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

interface AdminRestControllerDocs {

    @Tag(name = "admin", description = "관리자 메서드")
    @Operation(
            summary = "초기화",
            description = "모든 데이터베이스를 초기화합니다.",
            method = "DELETE",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "데이터베이스 초기화 성공"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "데이터베이스 초기화 실패"
                    )
            }
    )
    void resetDatabase();

}
