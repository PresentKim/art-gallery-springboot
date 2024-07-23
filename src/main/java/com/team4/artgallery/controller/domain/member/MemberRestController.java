package com.team4.artgallery.controller.domain.member;

import com.team4.artgallery.aspect.annotation.CheckAdmin;
import com.team4.artgallery.aspect.annotation.CheckLogin;
import com.team4.artgallery.controller.exception.*;
import com.team4.artgallery.controller.resolver.annotation.LoginMember;
import com.team4.artgallery.dto.EmailMessage;
import com.team4.artgallery.dto.MemberDto;
import com.team4.artgallery.service.MemberService;
import com.team4.artgallery.service.helper.EmailService;
import com.team4.artgallery.util.Assert;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/members", produces = MediaType.APPLICATION_JSON_VALUE)
public class MemberRestController implements MemberRestControllerDocs {

    private final MemberService memberService;
    private final EmailService emailService;

    public MemberRestController(MemberService memberService, EmailService emailService) {
        this.memberService = memberService;
        this.emailService = emailService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(
            @Validated(MemberDto.OnJoin.class)
            MemberDto memberDto
    ) throws ConflictException, SqlException {
        Assert.isFalse(memberService.isMember(memberDto.getId()), "이미 사용중인 아이디입니다.", ConflictException::new);

        memberService.createMember(memberDto);
    }

    @CheckLogin
    @DeleteMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void withdraw(
            @Valid
            @NotBlank(message = "비밀번호를 입력해주세요")
            @RequestParam(name = "pwd")
            String pwd,

            @LoginMember
            MemberDto loginMember
    ) throws BadRequestException, UnauthorizedException, SqlException {
        memberService.withdraw(pwd, loginMember);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public void login(
            @Validated(MemberDto.OnLogin.class)
            MemberDto loginForm
    ) throws ConflictException, BadRequestException, UnauthorizedException {
        memberService.login(loginForm.getId(), loginForm.getPwd());
    }

    @CheckLogin
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout() throws UnauthorizedException {
        memberService.logout();
    }

    @CheckLogin
    @PostMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void update(
            @PathVariable("id")
            String id,
            @Validated(MemberDto.OnUpdate.class)
            MemberDto memberDto,

            @LoginMember
            MemberDto loginMember
    ) throws SqlException, ForbiddenException {
        if (!loginMember.getId().equals(id)) {
            throw new ForbiddenException("본인의 회원 정보만 수정할 수 있습니다.");
        }

        memberDto.setId(id);

        // 비밀번호가 입력되지 않았다면 기존 비밀번호로 설정
        if (memberDto.getPwd() == null || memberDto.getPwd().isEmpty()) {
            memberDto.setPwd(loginMember.getPwd());
        }

        memberService.updateMember(memberDto);
        memberService.setLoginMember(memberService.getMember(id));
    }

    @GetMapping("check-id")
    public ResponseEntity<?> checkIdAvailability(
            @Valid
            @NotBlank(message = "아이디를 입력해주세요")
            @RequestParam("id")
            String id
    ) {
        if (memberService.isMember(id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 사용중인 아이디입니다.");
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @CheckAdmin
    @PutMapping("{id}/grant")
    @ResponseStatus(HttpStatus.OK)
    public void grant(
            @PathVariable("id")
            String id
    ) throws NotFoundException {
        memberService.grantAdmin(id);
    }

    @CheckAdmin
    @PutMapping("{id}/revoke")
    @ResponseStatus(HttpStatus.OK)
    public void revoke(
            @PathVariable("id")
            String id
    ) throws NotFoundException {
        memberService.revokeAdmin(id);
    }

    @CheckAdmin
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable("id")
            String id
    ) throws NotFoundException {
        memberService.deleteMember(id);
    }

    @GetMapping("/mail/signup")
    @ResponseStatus(HttpStatus.OK)
    public Object sendSignUpMail(
            @RequestParam(name = "email")
            String email
    ) throws InternalServerErrorException {
        try {
            emailService.sendMail(new EmailMessage(email, "[예술품갤러리] 회원가입을 위한 이메일입니다", "인증번호 : "));
            return "인증번호가 발송되었습니다";
        } catch (MessagingException e) {
            throw new InternalServerErrorException("이메일 발송 중 오류가 발생하였습니다");
        }
    }

    @PostMapping("/mail/signup")
    @ResponseStatus(HttpStatus.OK)
    public Object checkSignUpMail(
            @RequestParam(name = "email")
            String email,
            @RequestParam(name = "authCode")
            String authCode
    ) {
        emailService.checkAuthCode(email, authCode);
        return "인증에 성공하였습니다";
    }

}
