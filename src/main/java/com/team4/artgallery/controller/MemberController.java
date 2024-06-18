package com.team4.artgallery.controller;

import com.team4.artgallery.dto.MemberDto;
import com.team4.artgallery.service.MemberService;
import com.team4.artgallery.util.UrlUtil;
import com.team4.artgallery.util.ajax.ResponseBody;
import com.team4.artgallery.util.ajax.ResponseHelper;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MemberController {

    private final MemberService memberService;

    @Delegate
    private final ResponseHelper responseHelper;

    @GetMapping("/login")
    public String login(
            @RequestParam(value = "returnUrl", defaultValue = "/") String returnUrl,
            HttpSession session,
            Model model
    ) {
        // 이미 로그인 상태라면 returnUrl 으로 리다이렉트
        if (memberService.isLogin(session)) {
            return "redirect:" + returnUrl;
        }

        // 로그인 페이지로 이동 (returnUrl 전달)
        model.addAttribute("returnUrl", returnUrl);
        return "member/loginForm";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam(value = "returnUrl", defaultValue = "/") String returnUrl,
            @Valid @ModelAttribute MemberService.LoginForm loginForm,
            BindingResult bindingResult,
            HttpSession session
    ) {
        // 입력 값 검증 실패 시 에러 결과 반환
        if (bindingResult.hasErrors()) {
            return badRequest(bindingResult);
        }

        // 이미 로그인 상태라면 에러 결과와 함께 returnUrl 로 리다이렉트
        if (memberService.isLogin(session)) {
            return badRequest("이미 로그인 상태입니다.", returnUrl);
        }

        // 로그인 실패 시 에러 결과 반환
        if (!memberService.login(session, loginForm.getId(), loginForm.getPwd())) {
            return badRequest("ID 혹은 비밀번호가 일치하지 않습니다.");
        }

        // 로그인 성공 시 성공 결과와 돌아갈 URL 반환
        return ok("로그인에 성공하였습니다", returnUrl);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @RequestParam(value = "returnUrl", defaultValue = "/") String returnUrl,
            HttpSession session
    ) {
        // 로그아웃 실패 시 에러 결과 반환
        if (!memberService.logout(session)) {
            return badRequest("이미 로그인 상태가 아닙니다");
        }

        // 로그아웃 성공 시 성공 결과와 돌아갈 URL 반환
        return ok("로그아웃에 성공하였습니다", returnUrl);
    }

    @GetMapping("/contract")
    public String contract(
            @RequestParam(value = "returnUrl", defaultValue = "/") String returnUrl,
            HttpSession session,
            Model model
    ) {
        // 이미 로그인 상태라면 returnUrl 로 리다이렉트
        if (memberService.isLogin(session)) {
            return "redirect:" + returnUrl;
        }

        // 이용약관 페이지로 이동 (returnUrl 전달)
        model.addAttribute("returnUrl", returnUrl);
        return "member/contract";
    }

    @GetMapping("/join")
    public String join(
            @RequestParam(value = "returnUrl", defaultValue = "/") String returnUrl,
            Model model,
            HttpSession session
    ) {
        // 이미 로그인 상태라면 returnUrl 로 리다이렉트
        if (memberService.isLogin(session)) {
            return "redirect:" + returnUrl;
        }

        model.addAttribute("returnUrl", returnUrl);
        return "member/joinForm";
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(
            @RequestParam(value = "returnUrl", defaultValue = "/") String returnUrl,
            @Valid @ModelAttribute MemberDto memberDto,
            BindingResult bindingResult
    ) {
        // 입력 값 검증 실패 시 에러 결과 반환
        if (bindingResult.hasErrors()) {
            return badRequest(bindingResult);
        }

        // 이미 사용중인 아이디라면 에러 결과 반환
        if (memberService.isMember(memberDto.getId())) {
            return badRequest("이미 사용중인 아이디입니다.");
        }

        // 회원가입 실패 시 에러 결과 반환
        if (memberService.insertMember(memberDto) != 1) {
            return badRequest("회원가입에 실패하였습니다.");
        }

        // 회원가입 성공 시 성공 결과와 돌아갈 URL 반환 (로그인 페이지로 이동)
        return ok("회원가입에 성공하였습니다.", "/member/login?returnUrl=" + UrlUtil.encode(returnUrl));
    }

    @PostMapping("/idCheck")
    public ResponseEntity<?> idCheck(@Valid @NotNull @RequestParam(name = "id") String id) {
        // 이미 사용중인 아이디라면 에러 결과 반환
        if (memberService.isMember(id)) {
            return badRequest("이미 사용중인 아이디입니다.");
        }

        // 사용 가능한 아이디라면 성공 결과 반환
        return ok("사용 가능한 아이디입니다.");
    }

    @GetMapping("/mypage")
    public String mypage(HttpSession session) {
        // 로그인 상태가 아니라면 로그인 페이지로 리다이렉트
        if (!memberService.isLogin(session)) {
            return memberService.redirectToLogin("/member/mypage");
        }

        // 마이페이지로 이동
        return "member/mypage/index";
    }

    @GetMapping("/mypage/edit")
    public String mypageEdit(HttpSession session) {
        // 로그인 상태가 아니라면 로그인 페이지로 리다이렉트
        if (!memberService.isLogin(session)) {
            return memberService.redirectToLogin("/member/mypage/edit");
        }

        // 회원 정보 수정 페이지로 이동
        return "member/mypage/mypageEditForm";
    }

    @PostMapping("/mypage/edit")
    public ResponseEntity<?> edit(
            @Valid @ModelAttribute MemberDto memberDto,
            BindingResult bindingResult,
            HttpSession session
    ) {
        // 입력 값 검증 실패 시 에러 결과 반환
        if (bindingResult.hasErrors()) {
            return badRequest(bindingResult);
        }

        // 로그인 상태가 아니라면 에러 결과 반환
        MemberDto sessionMemberDto = memberService.getLoginMember(session);
        if (sessionMemberDto == null) {
            return badRequest("로그인이 필요합니다.");
        }

        // 로그인 회원의 ID 를 수정할 수 없도록 설정
        memberDto.setId(sessionMemberDto.getId());

        // 회원 정보 수정 실패 시 에러 결과 반환
        if (memberService.updateMember(memberDto) != 1) {
            return badRequest("회원정보 수정에 실패하였습니다.");
        }

        // 회원 정보 수정 성공 시 성공 결과 반환
        memberService.setLoginMember(session, memberDto);
        return ResponseEntity.ok(new ResponseBody("회원정보 수정에 성공하였습니다.", "/member/mypage"));
    }

    @GetMapping("/withdraw")
    public String withdraw(
            @RequestParam(value = "returnUrl", defaultValue = "/") String returnUrl,
            HttpSession session,
            Model model
    ) {
        // 로그인 상태가 아니라면 로그인 페이지로 리다이렉트
        if (!memberService.isLogin(session)) {
            return memberService.redirectToLogin("/member/withdraw");
        }

        // 회원 탈퇴 페이지로 이동 (returnUrl 전달)
        model.addAttribute("returnUrl", returnUrl);
        return "member/mypage/withdrawForm";
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(
            @Valid @NotNull @RequestParam(name = "pwd") String pwd,
            HttpSession session
    ) {
        // 로그인 상태가 아니라면 에러 결과 반환
        MemberDto memberDto = memberService.getLoginMember(session);
        if (memberDto == null) {
            return ok("로그인이 필요합니다", "/member/login?returnUrl=" + UrlUtil.encode("/member/withdraw"));
        }

        // 비밀번호가 일치하지 않다면 에러 결과 반환
        if (!memberDto.getPwd().equals(pwd)) {
            return badRequest("비밀번호가 일치하지 않습니다.");
        }

        // 회원 탈퇴 실패 시 에러 결과 반환
        if (memberService.deleteMember(memberDto.getId()) != 1) {
            return badRequest("회원 탈퇴에 실패하였습니다.");
        }

        // 로그아웃 실패 시 에러 결과 반환
        if (!memberService.logout(session)) {
            return badRequest("로그아웃에 실패하였습니다.");
        }

        // 회원 탈퇴 성공 시 성공 결과와 돌아갈 URL 반환
        return ok("회원 탈퇴에 성공하였습니다.", "/");
    }

    @GetMapping("/mypage/favorite")
    public String favorite(HttpSession session) {
        // 로그인 상태가 아니라면 로그인 페이지로 리다이렉트
        if (!memberService.isLogin(session)) {
            return memberService.redirectToLogin("/member/mypage/favorite");
        }

        // 관심 예술품 페이지로 이동
        return "member/mypage/mypageFavoriteList";
    }

}
