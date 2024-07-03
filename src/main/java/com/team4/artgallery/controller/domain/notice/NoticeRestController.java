package com.team4.artgallery.controller.domain.notice;

import com.team4.artgallery.aspect.annotation.CheckAdmin;
import com.team4.artgallery.controller.resolver.annotation.LoginMember;
import com.team4.artgallery.dto.MemberDto;
import com.team4.artgallery.dto.NoticeDto;
import com.team4.artgallery.service.NoticeService;
import com.team4.artgallery.service.helper.ResponseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/notice", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class NoticeRestController {

    private final NoticeService noticeService;

    @Delegate
    private final ResponseService responseHelper;

    @CheckAdmin
    @PostMapping("/update")
    public ResponseEntity<?> update(
            @Valid NoticeDto noticeDto,
            @LoginMember MemberDto loginMember
    ) {
        // 작성자를 로그인 정보로부터 가져와서 소식지 정보에 설정
        noticeDto.setAuthor(loginMember.getId());

        // 소식지 수정에 실패한 경우 500 에러 반환
        if (noticeService.updateNotice(noticeDto) == 0) {
            return internalServerError("소식지 작성에 실패했습니다.");
        }

        // 소식지 수정에 성공한 경우 200 성공 반환
        return ok("소식지 수정이 완료되었습니다.", "/notice/" + noticeDto.getNseq());
    }

    @CheckAdmin
    @PostMapping("/write")
    public ResponseEntity<?> write(
            @Valid NoticeDto noticeDto,
            @LoginMember MemberDto loginMember
    ) {
        // 작성자를 로그인 정보로부터 가져와서 소식지 정보에 설정
        noticeDto.setAuthor(loginMember.getId());

        // 소식지 작성에 실패한 경우 500 에러 반환
        if (noticeService.createNotice(noticeDto) == 0) {
            return internalServerError("소식지 작성에 실패했습니다.");
        }

        // 소식지 작성에 성공한 경우 200 성공 반환
        return ok("소식지 작성이 완료되었습니다.", "/notice/" + noticeDto.getNseq());
    }

    @CheckAdmin
    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam(value = "nseq") Integer nseq) {
        // 소식지 삭제 실패 시 오류 결과 반환
        if (noticeService.deleteNotice(nseq) != 1) {
            return badRequest("소식지 삭제에 실패했습니다.");
        }

        // 소식지 삭제 성공 시 성공 결과 반환
        return ok("소식지가 삭제되었습니다.", "/notice");
    }

}
