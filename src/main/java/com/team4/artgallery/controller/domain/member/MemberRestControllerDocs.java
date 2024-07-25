package com.team4.artgallery.controller.domain.member;

import com.team4.artgallery.dto.member.MemberCreateDto;
import com.team4.artgallery.dto.member.MemberLoginDto;
import com.team4.artgallery.dto.member.MemberUpdateDto;
import com.team4.artgallery.entity.MemberEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;

interface MemberRestControllerDocs {

    @Tag(name = "public", description = "사용자 메서드")
    @Operation(
            summary = "회원가입",
            description = "회원 가입을 진행합니다.",
            method = "POST",
            responses = {
                    @ApiResponse(responseCode = "201", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "409", description = "이미 사용중인 아이디"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            }
    )
    void create(
            @ParameterObject
            MemberCreateDto memberCreateDto
    );


    @Tag(name = "public", description = "사용자 메서드")
    @Operation(
            summary = "로그인",
            description = "로그인을 진행합니다.",
            method = "POST",
            parameters = {
                    @Parameter(name = "id", description = "아이디", required = true, in = ParameterIn.QUERY),
                    @Parameter(name = "pwd", description = "비밀번호", required = true, in = ParameterIn.QUERY)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "409", description = "이미 로그인 상태"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            }
    )
    void login(
            @Parameter(hidden = true)
            MemberLoginDto memberLoginDto
    );


    @Tag(name = "public", description = "사용자 메서드")
    @Operation(
            summary = "아이디 중복 체크",
            description = "아이디 중복을 체크합니다.",
            method = "GET",
            responses = {
                    @ApiResponse(responseCode = "204", description = "사용 가능한 아이디"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "409", description = "이미 사용중인 아이디"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            }
    )
    ResponseEntity<?> checkIdAvailability(
            @Parameter(name = "id", description = "아이디", required = true, in = ParameterIn.QUERY)
            String id
    );


    @Tag(name = "member", description = "회원 메서드")
    @Operation(
            summary = "회원 탈퇴",
            description = "회원 탈퇴를 진행합니다.",
            method = "DELETE",
            responses = {
                    @ApiResponse(responseCode = "204", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "401", description = "로그인 필요"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            }
    )
    void withdraw(
            @Parameter(name = "pwd", description = "비밀번호", required = true, in = ParameterIn.QUERY)
            String pwd,

            @Parameter(hidden = true)
            MemberEntity loginMember
    );


    @Tag(name = "member", description = "회원 메서드")
    @Operation(
            summary = "로그아웃",
            description = "로그아웃를 진행합니다.",
            method = "POST",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "401", description = "로그인 필요"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            }
    )
    void logout();


    @Tag(name = "member", description = "회원 메서드")
    @Operation(
            summary = "회원 정보 수정",
            description = "회원 정보를 수정합니다.",
            method = "POST",
            responses = {
                    @ApiResponse(responseCode = "201", description = "성공"),
                    @ApiResponse(responseCode = "401", description = "로그인 필요"),
                    @ApiResponse(responseCode = "403", description = "본인의 회원 정보만 수정 가능"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            }
    )
    void update(
            @Parameter(name = "id", description = "회원 아이디", required = true, in = ParameterIn.PATH)
            String id,
            @ParameterObject
            MemberUpdateDto memberUpdateDto,

            @Parameter(hidden = true)
            MemberEntity loginMember
    );


    @Tag(name = "admin", description = "관리자 메서드")
    @Operation(
            summary = "회원 삭제",
            description = "회원 정보를 삭제합니다.",
            method = "DELETE",
            responses = {
                    @ApiResponse(responseCode = "204", description = "회원 정보 삭제 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            }
    )
    void delete(
            @Parameter(name = "id", description = "회원 아이디", required = true, in = ParameterIn.PATH)
            String id
    );


    @Tag(name = "admin", description = "관리자 메서드")
    @Operation(
            summary = "관리자 권한 부여",
            description = "회원에게 관리자 권한을 부여합니다.",
            method = "DELETE",
            responses = {
                    @ApiResponse(responseCode = "200", description = "회원 정보 삭제 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "404", description = "회원 정보 없음")
            }
    )
    void grant(
            @Parameter(name = "id", description = "회원 아이디", required = true, in = ParameterIn.PATH)
            String id
    );


    @Tag(name = "admin", description = "관리자 메서드")
    @Operation(
            summary = "관리자 권한 해제",
            description = "회원의 관리자 권한을 해제합니다.",
            method = "DELETE",
            responses = {
                    @ApiResponse(responseCode = "200", description = "회원 정보 삭제 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "404", description = "회원 정보 없음")
            }
    )
    void revoke(
            @Parameter(name = "id", description = "회원 아이디", required = true, in = ParameterIn.PATH)
            String id
    );


    @Tag(name = "public", description = "사용자 메서드")
    @Operation(
            summary = "회원가입 이메일 발송",
            description = "회원가입을 위한 이메일을 발송합니다.",
            method = "GET",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            }
    )
    Object sendSignUpMail(
            @Parameter(name = "email", description = "이메일", required = true, in = ParameterIn.QUERY)
            String email
    );


    @Tag(name = "public", description = "사용자 메서드")
    @Operation(
            summary = "회원가입 이메일 확인",
            description = "회원가입을 위한 이메일을 확인합니다.",
            method = "POST",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            }
    )
    Object checkSignUpMail(
            @Parameter(name = "email", description = "이메일", required = true, in = ParameterIn.QUERY)
            String email,
            @Parameter(name = "authCode", description = "인증 코드", required = true, in = ParameterIn.QUERY)
            String authCode
    );

}
