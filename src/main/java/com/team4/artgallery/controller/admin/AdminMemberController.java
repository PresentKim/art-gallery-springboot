package com.team4.artgallery.controller.admin;

import com.team4.artgallery.annotation.CheckAdmin;
import com.team4.artgallery.dto.MemberDto;
import com.team4.artgallery.service.MemberService;
import com.team4.artgallery.util.Pagination;
import com.team4.artgallery.util.ajax.ResponseHelper;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/member")
@CheckAdmin
@RequiredArgsConstructor
public class AdminMemberController {

    private final MemberService memberService;

    @Delegate
    private final ResponseHelper responseHelper;

    @GetMapping({"", "/"})
    public String list(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            Model model
    ) {
        // 검색 조건이 있을 경우 검색 결과를, 없을 경우 전체 갤러리 목록을 가져옵니다.
        Pagination.Pair<MemberDto> pair = memberService.getOrSearchMembers(page, search);
        model.addAttribute("search", search);
        model.addAttribute("pagination", pair.pagination());
        model.addAttribute("memberList", pair.list());
        return "admin/adminMemberList";
    }

    @PostMapping("/grant")
    public ResponseEntity<?> grant(@RequestParam(value = "memberIds", required = false) List<String> memberIds) {
        // memberIds 값이 없는 경우 요청 거부 결과 반환
        if (memberIds == null) {
            return badRequest("회원을 선택해주세요");
        }

        // 관리자 권한 부여에 실패한 경우 실패 결과 반환
        if (memberService.grantAdminMembers(memberIds) == 0) {
            return badRequest("관리자 권한 부여에 실패했습니다");
        }

        // 관리자 권한 부여에 성공한 경우 성공 결과 반환 (페이지 새로고침)
        // TODO: 새고로침 없이 HTML 요소를 변경하는 방법으로 수정
        return ok("관리자 권한을 부여했습니다", ":reload");
    }

    @PostMapping("/revoke")
    public ResponseEntity<?> revoke(@RequestParam(value = "memberIds", required = false) List<String> memberIds) {
        // memberIds 값이 없는 경우 요청 거부 결과 반환
        if (memberIds == null) {
            return badRequest("회원을 선택해주세요");
        }

        // 관리자 권한 제거에 실패한 경우 실패 결과 반환
        if (memberService.revokeAdminMembers(memberIds) == 0) {
            return badRequest("관리자 권한 제거에 실패했습니다");
        }

        // 관리자 권한 제거에 성공한 경우 성공 결과 반환 (페이지 새로고침)
        // TODO: 새고로침 없이 HTML 요소를 변경하는 방법으로 수정
        return ok("관리자 권한을 제거했습니다", ":reload");
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam(value = "memberIds", required = false) List<String> memberIds) {
        // memberIds 값이 없는 경우 요청 거부 결과 반환
        if (memberIds == null) {
            return badRequest("회원을 선택해주세요");
        }

        // 회원 정보 제거에 실패한 경우 실패 결과 반환
        if (memberService.deleteMembers(memberIds) == 0) {
            return badRequest("회원 정보 제거에 실패했습니다");
        }

        // 회원 정보 제거에 성공한 경우 성공 결과 반환 (페이지 새로고침)
        // TODO: 새고로침 없이 HTML 요소를 변경하는 방법으로 수정
        return ok("회원 정보를 제거했습니다", ":reload");
    }

}
